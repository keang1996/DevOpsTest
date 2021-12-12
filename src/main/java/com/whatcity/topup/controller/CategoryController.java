package com.whatcity.topup.controller;

import com.whatcity.topup.model.dto.IAdminNews;
import com.whatcity.topup.model.table.AdminNews;
import com.whatcity.topup.service.CategoryService;
import com.whatcity.topup.service.NewsService;
import com.whatcity.topup.steam.model.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

  private final CategoryService categoryService;
  private final NewsService newsService;

  public CategoryController(CategoryService categoryService, NewsService newsService) {
    this.categoryService = categoryService;
    this.newsService = newsService;
  }

  @GetMapping("/news")
  public String linkPage(Principal principal, Model model) {
    if (principal != null) {
      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
      User user = (User) token.getPrincipal();
      model.addAttribute("user", user);
    }

    List<IAdminNews> adminNews = newsService.findNews();

    Map<Object, List<IAdminNews>> filterCatagory = adminNews.stream()
      .collect(Collectors.groupingBy(w -> w.getNewsCategoryId()));

    HashMap<String, List<IAdminNews>> map = new HashMap<>();

    filterCatagory.forEach((key, val) -> {
      Long catagoryId = Long.parseLong(key.toString());
      List<IAdminNews> adminNewsList = adminNews.stream()
        .filter(c -> c.getNewsCategoryId() == catagoryId).collect(Collectors.toList());
      map.put(catagoryId.toString(), adminNewsList);
    });

    model.addAttribute("itemShow", map);
    return "news/news";
  }

  @GetMapping("/content/{contentId}")
  public String linkDetailPage(@PathVariable("contentId") String contentId,
    Principal principal, Model model) {
    String page = "redirect:/news";
    if (principal != null) {
      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
      User user = (User) token.getPrincipal();
      model.addAttribute("user", user);
    }
    AdminNews adminNews = newsService.findNewsById(Long.parseLong(contentId));
    if (adminNews != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa");
      page = "news-detail/news-detail";
      model.addAttribute("content", adminNews.getDescription());
      model.addAttribute("topic", adminNews.getTopic());
      model.addAttribute("coverImage", adminNews.getCoverImage());
      model.addAttribute("date", sdf.format(adminNews.getCreateDate()));
      model.addAttribute("cateId", adminNews.getNewsCategoryId());

      return page;
    }
    return page;
  }

//  @GetMapping("/news/{newsCategoryId}")
//  public String linkPage(@PathVariable("newsCategoryId") String newsCategoryId, Principal principal,
//    Model model) {
//    if (principal != null) {
//      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
//      User user = (User) token.getPrincipal();
//      model.addAttribute("user", user);
//    }
//    List<IAdminNews> adminNews = newsService
//      .getNewsAvaliableByNewsCategoryId(Long.parseLong(newsCategoryId));
//    if (adminNews != null) {
//      IAdminNews adminNewsLimit = adminNews.stream().limit(1).collect(Collectors.toList())
//        .iterator().next();
//      model.addAttribute("item", adminNews);
//      model.addAttribute("cateName", adminNews.get(0).getNewsCategory());
//      model.addAttribute("limitItem", adminNewsLimit);
//      return "news-category/news-category";
//    }
//    return "redirect:/news";
//  }

  @GetMapping("/news/{newsCategoryId}")
  public String linkPage(@PathVariable("newsCategoryId") String newsCategoryId,
    @RequestParam String page,
    Principal principal,
    Model model) {
    if (principal != null) {
      OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) principal;
      User user = (User) token.getPrincipal();
      model.addAttribute("user", user);
    }
    Long itemPerPage = 6L;
    Long offset = (Long.parseLong(page) - 1) * itemPerPage;
    List<IAdminNews> adminNewsLimit = newsService
      .getNewsAvaliableByNewsCategoryIdLimit(Long.parseLong(newsCategoryId), offset, itemPerPage);
    BigDecimal bigDecimalCount = BigDecimal.valueOf(adminNewsLimit.get(0).getCount());
    BigDecimal pageTotal = bigDecimalCount.divide(BigDecimal.valueOf(itemPerPage), RoundingMode.UP);
    if (adminNewsLimit != null) {
      IAdminNews adminNewsLatest = adminNewsLimit.stream().limit(1).collect(Collectors.toList())
        .iterator().next();
      model.addAttribute("item", adminNewsLimit);
      model.addAttribute("cateName", adminNewsLimit.get(0).getNewsCategory());
      model.addAttribute("cateId", adminNewsLimit.get(0).getNewsCategoryId());
      model.addAttribute("limitItem", adminNewsLatest);
      model.addAttribute("totalPage", pageTotal.longValue());
      return "news-category/news-category";
    }
    return "redirect:/news";
  }
}
