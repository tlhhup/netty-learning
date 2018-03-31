package org.tlh.refreshtoken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tlh.refreshtoken.App;
import org.tlh.refreshtoken.service.PlatformService;

@RestController
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @RequestMapping("/addDevice")
    public String addDevice(){
        this.platformService.addDevice(App.authInfo);
        return "ok";
    }

    @RequestMapping("/addDevice1")
    public String addDevice1(){
        this.platformService.addDevice("test",App.authInfo);
        return "ok";
    }

    @RequestMapping("/delete")
    public String delete(){
        this.platformService.delete(App.authInfo);
        return "ok";
    }

}
