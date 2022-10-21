package com.cis.springboot;

import com.cis.springboot.security.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public  abstract class BaseController {
   public AppUser getCurrentUser(){
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       return (AppUser) authentication.getPrincipal();
   }
}
