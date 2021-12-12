package com.whatcity.topup.steam.service;

import com.whatcity.topup.exception.ServerException;
import com.whatcity.topup.steam.model.User;
import com.whatcity.topup.steam.model.properties.SteamProperties;
import com.whatcity.topup.util.GsonParser;
import com.whatcity.topup.util.HttpClientSteam;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

public class CustomerDetailService implements
  AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

  @Autowired
  UserService userService;

  @Autowired
  SteamProperties steamProperties;

  @Override
  public UserDetails loadUserDetails(
    OpenIDAuthenticationToken token) throws UsernameNotFoundException {
    String[] tokenSplit = token.getName().split("/");
    int length = tokenSplit.length;
    User user = userService.findUserByTokenId(tokenSplit[length - 1]);
    try {
      List<String> userSteamSummaries = getUserSteamSummaries(tokenSplit[length - 1]);
      String steamName = userSteamSummaries.get(0);
      String avartar = userSteamSummaries.get(1);
      String steamId = userSteamSummaries.get(2);
      if (null == user.getSteamId() || !steamName.equals(user.getSteamName()) || !avartar
        .equals(user.getAvartar())) {
        user.setSteamName(steamName);
        user.setAvartar(avartar);
        user.setSteamId(steamId);
        user = userService.saveUser(user);
      }
    } catch (ServerException e) {
      e.printStackTrace();
    }
    return user;
  }

  public List<String> getUserSteamSummaries(String steamId) throws ServerException {
    List<String> userInfo = new ArrayList<>();
    String steamKey = steamProperties.getKey();
    String steamUrl = steamProperties.getUrl()
      .concat("/ISteamUser/GetPlayerSummaries/v0002/?key=")
      .concat(steamKey)
      .concat("&steamids=")
      .concat(steamId);
    String gameString = new HttpClientSteam(steamUrl).getAll();
    userInfo = new GsonParser().parseUserInfoList(gameString);
    return
      userInfo;
  }
}
