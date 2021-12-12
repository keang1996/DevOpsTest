package com.whatcity.topup.controller;

import com.whatcity.topup.steam.model.User;
import java.security.Principal;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

  @GetMapping("/contact")
  public String linkPage(Principal principal, Model model) {
    if (principal != null) {
      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
      User user = (User) token.getPrincipal();
      model.addAttribute("user", user);
    }
    final String page = "contact/contact";
    return page;
  }
}
