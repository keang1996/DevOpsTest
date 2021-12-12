package com.whatcity.topup.controller;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.whatcity.topup.model.firebase.NotifyModel;
import com.whatcity.topup.model.payload.PaymentDetail;
import com.whatcity.topup.model.table.Trade;
import com.whatcity.topup.model.table.WhatCityCommon;
import com.whatcity.topup.service.FirebaseService;
import com.whatcity.topup.service.PaymentService;
import com.whatcity.topup.service.TradeService;
import com.whatcity.topup.service.WhatCityCommonService;
import com.whatcity.topup.steam.model.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PaymentController {

  @Autowired
  FirebaseService firebaseService;

  @Autowired
  TradeService tradeService;

  @Autowired
  WhatCityCommonService whatCityCommonService;

  @Autowired
  PaymentService paymentService;


  @GetMapping(value = {"/payment/{orderRef}"})
  public String linkPage(Principal principal, Model model,
    @PathVariable("orderRef") String orderRef) {
    final String page = "payment/payment";
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      model.addAttribute("user", user);
      PaymentDetail paymentDetail = new PaymentDetail();
      if (!"".equals(orderRef) && orderRef != null) {
        paymentDetail.setOrderRef(orderRef);
      }
      model.addAttribute("paymentDetail", paymentDetail);
      model.addAttribute("success", false);
    }
    return page;
  }

  @GetMapping(value = {"/successPage/{orderRef}"})
  public String successPage(Principal principal, Model model,
    @PathVariable("orderRef") String orderRef) {
    final String page = "payment/payment";
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      model.addAttribute("user", user);
      model.addAttribute("success", true);
      model.addAttribute("order", paymentService.getPaymentProductOrder(orderRef));
      model.addAttribute("messageSuccess", "แจ้งการชำระเงิน สำเร็จ กรุณารอตรวจสอบรายการ");
    }
    return page;
  }

  @PostMapping("/savePaymentDetail")
  public String savePaymentDetail(@ModelAttribute("paymentDetail") PaymentDetail paymentDetail,
    @RequestParam("file") MultipartFile file,
    Principal principal, Model model)
    throws FirebaseMessagingException, ExecutionException, InterruptedException {
    final String page = "payment/payment";
    String redirect = "";
    Boolean validate = true;
    Trade trade = null;
    if (principal != null) {
      OpenIDAuthenticationToken principalToken = (OpenIDAuthenticationToken) principal;
      User user = (User) principalToken.getPrincipal();
      model.addAttribute("user", user);

      if (file.isEmpty()) {
        model.addAttribute("messageFile", "โปรดเลือกไฟล์ที่ต้องการอัพโหลด");
        validate = false;
      } else {
        final String extension = FilenameUtils
          .getExtension(StringUtils.cleanPath(file.getOriginalFilename()));
        if (!"jpg".equals(extension) && !"jpeg".equals(extension) && !"pdf".equals(extension)
          && !"png".equals(extension)) {
          model.addAttribute("messageFile", "โปรดเลือกไฟล์นามสกุล jpg,jpeg ,pdf,png");
          validate = false;
        } else if (file.getSize() > 5242880L) {
          model.addAttribute("messageFile", "โปรดเลือกไฟล์ขนาดไม่เกิน 5 MB.");
          validate = false;
        }
      }

      if ("".equals(paymentDetail.getOrderRef())) {
        model.addAttribute("messageOrderRef", "โปรดกรอกเลขคำสั่งซื้อ");
        validate = false;
      } else {
        trade = tradeService.queryTradeByOrderId(paymentDetail.getOrderRef());
        if (trade == null || !paymentDetail.getOrderRef().equals(trade.getOrderId())) {
          model.addAttribute("messageOrderRef", "เลขคำสั่งซื้อนี้ไม่มีในระบบ");
          validate = false;
        }

        if(trade != null && paymentDetail.getOrderRef().equals(trade.getOrderId()) && "2".equals(trade.getStatus())){
          model.addAttribute("messageOrderRef", "เลขคำสั่งซื้อนี้มีการแนบสลิปไปแลัว");
          validate = false;
        }
      }

      if ("".equals(paymentDetail.getDate())) {
        model.addAttribute("messageDate", "โปรดเลือกวันที่โอน");
        validate = false;
      }
      if ("".equals(paymentDetail.getTime())) {
        model.addAttribute("messageTime", "โปรดเลือกเวลาที่โอน");
        validate = false;
      }

      if (!validate) {
        model.addAttribute("success", false);
        return page;
      }

      //สร้าง folder
      // final String directoryPath = createFolder();
      // final File dir = new File(directoryPath);

      // final String fileName = getDateStr() + user.getSteamId() + paymentDetail.getOrderRef();

//            if(dir.isDirectory()){
//                final Boolean uploadFile = uploadFile(fileName,directoryPath,file);
//                if(!uploadFile){
//                    model.addAttribute("messageFile", "อัพโหลด ไฟล์ ไม่สำเร็จกรุณาลองใหม่อีกครั้ง");
//                    model.addAttribute("success", false);
//                    return page;
//                }
//            }

      if (trade != null) {
        try {
          final String dateStr = paymentDetail.getDate();
          final String timeStr = paymentDetail.getTime();
          Date date = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH")).parse(dateStr);
          Date time = new SimpleDateFormat("HH:mm", new Locale("th", "TH")).parse(timeStr);
          String receipt = fileToBase64(file);

          trade.setTradeDate(date);
          trade.setTradeTime(time);
          trade.setReceipt(receipt);
          trade.setProveStatus(false);
          trade.setStatus("2");
          trade.setUpdateBy(user.getSteamId());
          trade.setUpdateDate(new Date());
          tradeService.save(trade);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      redirect = "redirect:successPage/" + paymentDetail.getOrderRef();

      NotifyModel notifyModel = new NotifyModel();
      notifyModel.setOrderId(paymentDetail.getOrderRef());
      firebaseService.sendNotification(paymentDetail.getOrderRef(),
        "eHZx08wbmNmsywdHvlAkvA:APA91bHHQURAGaiTomknMLO1G6ic7aT4fyKDac08wgi8tkuV14_wefjG7L2iKVHwzBIG0iV4Z0ZhwnXiREdrBMHOQJ8rm7L4kmV0nN9HmlE1UVcxwGsDSNrxPL2Td_Ya3HpbcMqh1tsB");
      firebaseService.saveNotification(notifyModel);

      saveFirebase(paymentDetail.getOrderRef());

    }

    return redirect;
  }

  private String getDateStr() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    String dateStr = dateFormat.format(new Date());
    return dateStr;
  }

  private String createFolder() {
    final WhatCityCommon whatCityCommon = whatCityCommonService
      .findByIdItemName("upload_file_path");
    final String uploadDir = whatCityCommon.getItemValue();
    final String dateStr = getDateStr();
    final String directoryPath = uploadDir + dateStr;
    File dir = new File(directoryPath);
    if (!dir.isDirectory()) {
      dir.mkdir();
    }
    return directoryPath;
  }

  private Boolean uploadFile(String fileName, String directoryPath, MultipartFile file) {
    Boolean check = false;
    try {
      final String extension = FilenameUtils
        .getExtension(StringUtils.cleanPath(file.getOriginalFilename()));
      final String fileFullName = fileName + "." + extension;
      Path path = Paths.get(directoryPath + "/" + fileFullName + ".");
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      File checkFile = new File(directoryPath + "/" + fileFullName);
      if (checkFile.isFile()) {
        check = true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return check;
  }

  private static String fileToBase64(MultipartFile multiFile) {
    try {
      final String extension = FilenameUtils
        .getExtension(StringUtils.cleanPath(multiFile.getOriginalFilename()));
      byte[] fileContent = multiFile.getBytes();
      final String imageBase64 =
        "data:image/" + extension + ";base64," + Base64.getEncoder().encodeToString(fileContent);
      return imageBase64;
    } catch (IOException e) {
      throw new IllegalStateException("could not read file", e);
    }
  }

  private static String encodeFileToBase64(String fileName, String directoryPath,
    MultipartFile multiFile) {
    try {
      final String extension = FilenameUtils
        .getExtension(StringUtils.cleanPath(multiFile.getOriginalFilename()));
      final String fileFullName = fileName + "." + extension;
      File file = new File(directoryPath + "/" + fileFullName);
      if (file.isFile()) {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        final String imageBase64 =
          "data:image/" + extension + ";base64," + Base64.getEncoder().encodeToString(fileContent);
        return imageBase64;
      }
    } catch (IOException e) {
      throw new IllegalStateException("could not read file", e);
    }
    return null;
  }

  private void saveFirebase(String orderRef) {
    Firestore firestore = FirestoreClient.getFirestore();
    DocumentReference docRef = firestore.collection("whatcity_notify").document(orderRef);

    Map<String, Object> data = new HashMap<>();
    data.put("createDateTime", "");
    data.put("id", "");
    data.put("message", "");
    data.put("orderId", orderRef);
    docRef.set(data);

  }
}
