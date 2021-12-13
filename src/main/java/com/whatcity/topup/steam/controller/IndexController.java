package com.whatcity.topup.steam.controller;

import com.whatcity.topup.model.dto.IAdminNews;
import com.whatcity.topup.model.table.AdminNews;
import com.whatcity.topup.service.NewsService;
import com.whatcity.topup.steam.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

  private final UserService userService;

  private final NewsService newsService;

  @Autowired
  public IndexController(UserService userService,
    NewsService newsService) {
    this.userService = userService;
    this.newsService = newsService;
  }

  @GetMapping("/")
  public String indexController(@RequestParam(value = "fail", required = false) String fail,
    Model model, Principal principal) {
    if (principal != null) {
      model.addAttribute("user", userService.findUserByTokenId(principal.getName()));
    }
    if (fail != null) {
      model.addAttribute("msg", "Failed to login through Steam");
    }
    List<IAdminNews> adminNews = newsService
      .getNewsAvaliableByNewsCategoryId(Long.parseLong("20"));
    if (adminNews != null) {
      model.addAttribute("item", adminNews);
    }
    List<AdminNews> latestNews = newsService.getLatestNews();
    if (latestNews != null) {
      model.addAttribute("latestNews", latestNews);
    }
    List<IAdminNews> mainEventCover = newsService.getMainEventCoverImage();
    if (mainEventCover != null) {
      model.addAttribute("mainEventCover", mainEventCover);
      model.addAttribute("mainEventCoverSize", mainEventCover.size());
    }
    return "index";
  }
}
