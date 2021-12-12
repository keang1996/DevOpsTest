package com.whatcity.topup.steam.service;

import com.whatcity.topup.steam.model.User;
import com.whatcity.topup.steam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User findUserByTokenId(String id) {
    return userRepository.findById(id).orElse(new User());
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }
}
