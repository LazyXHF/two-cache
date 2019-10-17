package cn.xu.cache.controller;

import cn.xu.cache.aop.CacheInterface;
import cn.xu.cache.entity.User;
import cn.xu.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;

/**
 * @author ~许小桀
 * @date 2019/10/17 15:46
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;



    @RequestMapping(name = "user",method = RequestMethod.GET)
    public User getUserById(String id){
        return userService.getUserById(id);
    }
}
