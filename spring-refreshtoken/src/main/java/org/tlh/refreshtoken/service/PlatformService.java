package org.tlh.refreshtoken.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tlh.refreshtoken.annotation.RefreshToken;
import org.tlh.refreshtoken.entity.AuthInfo;

@Service
public class PlatformService {

    private static Logger logger = LoggerFactory.getLogger(PlatformService.class);

    public AuthInfo login() {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setExpiredTime(60);
        return authInfo;
    }

    public AuthInfo refreshToken(AuthInfo old){
        logger.debug("重新刷新token");
        AuthInfo authInfo = new AuthInfo();
        authInfo.setExpiredTime(120);
        return authInfo;
    }

    @RefreshToken
    public boolean addDevice(AuthInfo authInfo) {
        logger.debug("addDevice--->"+authInfo.getExpiredTime() + "");
        return true;
    }

    @RefreshToken
    public boolean addDevice(String name,AuthInfo authInfo) {
        logger.debug("addDevice--->"+authInfo.getExpiredTime() + " name:"+name);
        return true;
    }

    public boolean delete(AuthInfo authInfo){
        logger.debug("addDevice--->"+authInfo.getExpiredTime() + "");
        return true;
    }

}
