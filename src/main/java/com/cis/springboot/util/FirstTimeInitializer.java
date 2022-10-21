package com.cis.springboot.util;

import com.cis.springboot.security.AppUser;
import com.cis.springboot.security.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitializer implements CommandLineRunner {

    private final Log logger= LogFactory.getLog(FirstTimeInitializer.class);
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        //check if i have users
        //if no user exist >> create some users
        if(userService.findAll().isEmpty())
        {
         logger.info("No users accounts found >> create some users");
            AppUser appUser=new AppUser("medo@gmail.com","password","medo");
            userService.save(appUser);
        }
    }
}
