package com.whatcity.topup.steam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {

  @Id
  @Column(name = "steam_id")
  private String steamId;

  @Column(name = "steam_name")
  private String steamName;

  @Column(name = "avartar")
  private String avartar;

  @Transient
  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList("ROLE_USER");
  }

  @Transient
  @JsonIgnore
  @Override
  public String getPassword() {
    return "";
  }

  @Transient
  @JsonIgnore
  @Override
  public String getUsername() {
    return steamId;
  }

  @Transient
  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Transient
  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Transient
  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Transient
  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return false;
  }
}
