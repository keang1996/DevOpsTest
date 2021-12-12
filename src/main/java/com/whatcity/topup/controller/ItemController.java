package com.whatcity.topup.controller;

import com.whatcity.topup.model.dto.IProductDTO;
import com.whatcity.topup.service.OrderPaymentService;
import com.whatcity.topup.service.ProductService;
import com.whatcity.topup.steam.model.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

  @Autowired
  OrderPaymentService orderPaymentService;

  @Autowired
  ProductService productService;

  @GetMapping("/gachapon")
  public String linkPageGachapon(Principal principal, Model model, @RequestParam String page) {
    final String redirectPage = "item/item";
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      Long offset = (Long.parseLong(page) - 1) * 4;
      List<IProductDTO> productList = productService
        .findAllByProductStatusGachapon(offset);
      BigDecimal pageTotal = BigDecimal.valueOf(1);
      if (productList.size() > 0) {
        BigDecimal bigDecimalCount = BigDecimal.valueOf(productList.get(0).getCount());
        pageTotal = bigDecimalCount.divide(BigDecimal.valueOf(4), RoundingMode.UP);
      }
      model.addAttribute("user", user);
      model.addAttribute("products", productList);
      model.addAttribute("titleItem", "กาชาปอง");
      model.addAttribute("titleItemEn", "gachapon");
      model.addAttribute("total", pageTotal);
      model.addAttribute("size", productList.size());
    }
    return redirectPage;
  }

  @GetMapping("/vip")
  public String linkPageVip(Principal principal, Model model, @RequestParam String page) {
    final String redirectPage = "item/item";
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      Long offset = (Long.parseLong(page) - 1) * 4;
      List<IProductDTO> productList = productService
        .findAllByProductStatusVip(offset);
      BigDecimal pageTotal = BigDecimal.valueOf(1);
      if (productList.size() > 0) {
        BigDecimal bigDecimalCount = BigDecimal.valueOf(productList.get(0).getCount());
        pageTotal = bigDecimalCount.divide(BigDecimal.valueOf(4), RoundingMode.UP);
      }
      model.addAttribute("user", user);
      model.addAttribute("products", productList);
      model.addAttribute("titleItem", "วีไอพี");
      model.addAttribute("titleItemEn", "vip");
      model.addAttribute("total", pageTotal);
      model.addAttribute("size", productList.size());
    }
    return redirectPage;
  }

  @GetMapping("/whiteList")
  public String linkPageWhiteList(Principal principal, Model model, @RequestParam String page) {
    final String redirectPage = "item/item";
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      Long offset = (Long.parseLong(page) - 1) * 4;
      List<IProductDTO> productList = productService
        .findAllByProductStatusWhiteList(offset);
      BigDecimal pageTotal = BigDecimal.valueOf(1);
      if (productList.size() > 0) {
        BigDecimal bigDecimalCount = BigDecimal.valueOf(productList.get(0).getCount());
        pageTotal = bigDecimalCount.divide(BigDecimal.valueOf(4), RoundingMode.UP);
      }
      model.addAttribute("user", user);
      model.addAttribute("products", productList);
      model.addAttribute("titleItem", "ไวท์ลิสต์");
      model.addAttribute("titleItemEn", "whiteList");
      model.addAttribute("total", pageTotal);
      model.addAttribute("size", productList.size());
    }
    return redirectPage;
  }
}
