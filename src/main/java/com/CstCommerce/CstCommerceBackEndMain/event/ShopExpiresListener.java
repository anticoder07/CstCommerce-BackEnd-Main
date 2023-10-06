package com.CstCommerce.CstCommerceBackEndMain.event;

import com.CstCommerce.CstCommerceBackEndMain.securityConfig.NotificationSseEmitterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopExpiresListener {
  @Autowired
  private NotificationSseEmitterManager emitterManager;

  @EventListener
  public void expires(ShopExpiresEvent shopExpiresEvent){
    String message = "huong dep trai"; // Thông báo bạn muốn gửi

    emitterManager.sendNotification(message);
  }
}
