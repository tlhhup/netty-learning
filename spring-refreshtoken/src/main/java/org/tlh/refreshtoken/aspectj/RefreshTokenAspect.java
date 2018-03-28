package org.tlh.refreshtoken.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.tlh.refreshtoken.App;
import org.tlh.refreshtoken.entity.AuthInfo;
import org.tlh.refreshtoken.service.PlatformService;
import org.tlh.refreshtoken.task.RefreshTokenTask;

@Aspect
public class RefreshTokenAspect {

    private PlatformService platformService;

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    //定于切点
    @Pointcut("@annotation(org.tlh.refreshtoken.annotation.RefreshToken)")
    public void refreshTokenPointCut(){

    }

    //定义切面
    @Around("refreshTokenPointCut()")
    public Object refreshToken(ProceedingJoinPoint joinPoint){
        try {
            Object[] args = joinPoint.getArgs();//获取参数

            RefreshTokenTask refreshTokenTask = App.tasks.poll();//该方法不会阻塞
            if(refreshTokenTask!=null){//该token过期了
                AuthInfo authInfo = this.platformService.refreshToken((AuthInfo) args[0]);
                args[0]=authInfo;

                //重新更新数据
                App.tasks.remove(args[0]);

                App.tasks.add(new RefreshTokenTask(authInfo.getExpiredTime()));

                App.authInfo=authInfo;
            }
            Object result = joinPoint.proceed(args);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
