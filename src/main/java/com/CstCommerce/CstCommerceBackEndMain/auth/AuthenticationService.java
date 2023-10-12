package com.CstCommerce.CstCommerceBackEndMain.auth;

import com.CstCommerce.CstCommerceBackEndMain.entity.token.RoleType;
import com.CstCommerce.CstCommerceBackEndMain.entity.token.Token;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Basket;
import com.CstCommerce.CstCommerceBackEndMain.entity.user.Users;
import com.CstCommerce.CstCommerceBackEndMain.payload.request.LogInRequest;
import com.CstCommerce.CstCommerceBackEndMain.payload.request.SignUpRequest;
import com.CstCommerce.CstCommerceBackEndMain.payload.response.AuthenticationResponse;
import com.CstCommerce.CstCommerceBackEndMain.repository.BasketRepository;
import com.CstCommerce.CstCommerceBackEndMain.repository.TokenRepository;
import com.CstCommerce.CstCommerceBackEndMain.repository.UserRepository;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.JwtUtils;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.service.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final BasketRepository basketRepository;

  private final JwtUtils jwtUtils;

  private final TokenRepository tokenRepository;

  private final PasswordEncoder passwordEncoder;

  public AuthenticationResponse logIn(LogInRequest logInRequest) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    logInRequest.getUserEmail(),
                    logInRequest.getPassword()
            )
    );
    var user = userRepository.findByEmail(logInRequest.getUserEmail()).orElseThrow();
    UserDetailsImpl userDetails = new UserDetailsImpl(user);
    var jwtToken = jwtUtils.generateToken(userDetails);
    var refreshToken = jwtUtils.generateRefreshToken(userDetails);
    revokeAllUserTokens(userDetails);
    saveUserToken(userDetails, jwtToken);
    return new AuthenticationResponse(
            jwtToken,
            refreshToken
    );
  }

  private void saveUserToken(UserDetailsImpl userDetails, String jwtToken) {
    var token = Token.builder()
            .users(userDetails.getUsers())
            .token(jwtToken)
            .roleType(RoleType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserDetailsImpl userDetails) {
    var validUserToken = tokenRepository.findAllValidTokenByUser(userDetails.getUsers().getId());
    if (validUserToken.isEmpty())
      return;
    validUserToken.forEach(
            token -> {
              token.setExpired(true);
              token.setRevoked(true);
            }
    );
    tokenRepository.saveAll(validUserToken);
  }

  public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail()))
      return null;
    Users users = new Users(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            signUpRequest.getSdt(),
            signUpRequest.getRole()
    );
    UserDetailsImpl userDetails = new UserDetailsImpl(users);
    Basket basket = new Basket();
    basket.setUsers(users);
    userRepository.save(users);
    basketRepository.save(basket);
    var jwtToken = jwtUtils.generateToken(userDetails);
    var refreshToken = jwtUtils.generateRefreshToken(userDetails);
    saveUserToken(userDetails, jwtToken);
    return new AuthenticationResponse(
            jwtToken,
            refreshToken
    );
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")){
      return;
    }
    final String refreshToken = authHeader.substring(7);
    final String userEmail = jwtUtils.extractUserEmail(refreshToken);
    if (userEmail != null){
      var user = this.userRepository.findByEmail(refreshToken).orElseThrow();
      UserDetailsImpl userDetails = new UserDetailsImpl(user);
      if (jwtUtils.isTokenValid(refreshToken, userDetails)){
        var accessToken = jwtUtils.generateToken(userDetails);
        revokeAllUserTokens(userDetails);
        saveUserToken(userDetails, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
