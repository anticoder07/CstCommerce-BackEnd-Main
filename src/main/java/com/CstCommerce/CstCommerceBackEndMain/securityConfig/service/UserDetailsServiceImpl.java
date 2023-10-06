package com.CstCommerce.CstCommerceBackEndMain.securityConfig.service;

import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users = userRepository
            .findByEmail(username)
            .orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + username)
            );
    UserDetailsImpl userDetails = new UserDetailsImpl(users);
    return userDetails;
  }
}
