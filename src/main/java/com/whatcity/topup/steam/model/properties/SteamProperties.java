package com.whatcity.topup.steam.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "steam.api")
@Configuration("steamProperties")
@Data
public class SteamProperties {
  private String key;
  private String url;
}
