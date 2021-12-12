package com.whatcity.topup.controller;

import com.whatcity.topup.steam.model.User;
import com.whatcity.topup.steam.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PersonInfoController {

  private final UserService userService;

  @Autowired
  public PersonInfoController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/personInfo/{id}")
  public String linkPage(@PathVariable(name = "id") String id, Principal principal, Model model) {
    if (principal != null) {
      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
      User user = (User) token.getPrincipal();
      model.addAttribute("user", user);
    }
    final String page = "personInfo/personInfo";
    return page;
  }

}
