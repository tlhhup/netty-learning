package org.tlh.refreshtoken.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlh.refreshtoken.App;
import org.tlh.refreshtoken.entity.AuthInfo;
import org.tlh.refreshtoken.service.PlatformService;
import org.tlh.refreshtoken.task.RefreshTokenTask;

@Aspect
public class RefreshTokenAspect {

    private static final Logger LOGGER= LoggerFactory.getLogger(RefreshTokenAspect.class);

    private PlatformService platformService;

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    /**
     * args(参数类型列表):用于匹配输出参数的数据类型，参数类型为全限定名
     * args属于动态切入点，这种切入点开销非常大，非特殊情况最好不要使用，最好还是通过execution表达式处理-->execution(* *..*.*(org.tlh.refreshtoken.entity.AuthInfo,..))
     */
    //定于切点,添加了RefreshToken和方法参数列表第一参数类型为AuthInfo的才处理
    @Pointcut("@annotation(org.tlh.refreshtoken.annotation.RefreshToken) && args(org.tlh.refreshtoken.entity.AuthInfo,..)")
    public void refreshTokenPointCut(){

    }

    //定义切面
    @Around("refreshTokenPointCut()")
    public Object refreshToken(ProceedingJoinPoint joinPoint){
        LOGGER.debug("refreshTokenAspect begin");
        try {
            Object[] args = joinPoint.getArgs();//获取参数

            RefreshTokenTask refreshTokenTask = App.tasks.poll();//该方法不会阻塞
            if(refreshTokenTask!=null){//该token过期了
                LOGGER.debug("refreshToken");
                AuthInfo authInfo = this.platformService.refreshToken((AuthInfo) args[0]);
                args[0]=authInfo;

                //重新更新数据
                App.tasks.remove(args[0]);

                App.tasks.add(new RefreshTokenTask(authInfo.getExpiredTime()));

                App.authInfo=authInfo;

                LOGGER.debug("refreshToken success");
            }
            Object result = joinPoint.proceed(args);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
