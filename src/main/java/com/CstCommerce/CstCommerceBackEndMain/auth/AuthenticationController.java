package com.CstCommerce.CstCommerceBackEndMain.auth;

import com.CstCommerce.CstCommerceBackEndMain.exception.EmailIsExist;
import com.CstCommerce.CstCommerceBackEndMain.payload.request.LogInRequest;
import com.CstCommerce.CstCommerceBackEndMain.payload.request.SignUpRequest;
import com.CstCommerce.CstCommerceBackEndMain.payload.response.AuthenticationResponse;
import com.CstCommerce.CstCommerceBackEndMain.payload.response.ResponseHandler;
import com.CstCommerce.CstCommerceBackEndMain.securityConfig.LogOutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  private final LogOutService logOutService;


  @PostMapping("/log-in")
  public ResponseEntity<Object> logIn(@Valid @RequestBody LogInRequest logInRequest) {
    try {
      return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, authenticationService.logIn(logInRequest));
    } catch (Exception e) {
      return ResponseHandler.generateErrorResponse(e);
    }
  }

  @PostMapping("/sign-up")
  public ResponseEntity<Object> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    try {
      AuthenticationResponse check = authenticationService.signUp(signUpRequest);
      if (check == null)
        return ResponseHandler.generateErrorResponse(new EmailIsExist("Email is exist!"));
      return ResponseHandler.generateResponse(ResponseHandler.MESSAGE_SUCCESS, HttpStatus.OK, check);
    } catch (Exception e) {
      return ResponseHandler.generateErrorResponse(e);
    }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws java.io.IOException {
    authenticationService.refreshToken(request, response);
  }

  @GetMapping("/log-out")
  public void logOut(HttpServletRequest request, HttpServletResponse response) {
    logOutService.logout(request, response, null);
  }

}
