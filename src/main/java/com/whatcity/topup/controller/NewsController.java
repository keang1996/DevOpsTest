//package com.whatcity.topup.controller;
//
//import com.whatcity.topup.model.table.AdminNews;
//import com.whatcity.topup.service.NewsService;
//import com.whatcity.topup.steam.model.User;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.security.Principal;
//import java.util.List;
//import java.util.UUID;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.io.FileUtils;
//import org.springframework.security.openid.OpenIDAuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class NewsController {
//
//    private final NewsService newsService;
//
//    public NewsController(NewsService newsService) {
//        this.newsService = newsService;
//    }
//
//    @GetMapping("/news")
//    public String linkPage(Principal principal, Model model) {
//        if (principal != null) {
//            OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
//            User user = (User) token.getPrincipal();
//            model.addAttribute("user", user);
//        }
//        List<AdminNews> adminNews = newsService.findNewsAll();
//        model.addAttribute("newsItem",adminNews);
//        final String page = "news/news";
//        return page;
//    }
//
//    @GetMapping("/content/{contentId}")
//    public String linkDetailPage(@PathVariable("contentId") String contentId, Principal principal, Model model) throws IOException {
//        if (principal != null) {
//            OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
//            User user = (User) token.getPrincipal();
//            model.addAttribute("user", user);
//        }
//        AdminNews adminNews = newsService.findNewsById(Long.parseLong(contentId));
//        if (adminNews != null) {
//            model.addAttribute("content", adminNews.getDescription());
//            model.addAttribute("topic", adminNews.getTopic());
//            model.addAttribute("coverImage", adminNews.getCover_image());
//
//            final String page = "news-detail/news-detail";
//            return page;
//        }
//        return "redirect:/news";
//    }
//}
