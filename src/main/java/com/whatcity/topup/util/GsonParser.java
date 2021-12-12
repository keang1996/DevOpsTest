package com.whatcity.topup.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class GsonParser {

  private JsonParser parser;

  public List<String> parseUserInfoList(String stringToParse) {
    parser = new JsonParser();
    JsonElement jsonTree = parser.parse(stringToParse);
    JsonObject obj = jsonTree.getAsJsonObject();
    obj = obj.getAsJsonObject("response");
    JsonArray info = obj.getAsJsonArray("players");
    obj = info.get(0).getAsJsonObject();

    List<String> userInfo = new ArrayList<>();
    userInfo.add(obj.get("personaname").getAsString());
    userInfo.add(obj.get("avatar").getAsString());
    userInfo.add(obj.get("steamid").getAsString());

    return userInfo;
  }
}
