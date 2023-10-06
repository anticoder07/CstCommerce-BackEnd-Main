package com.CstCommerce.CstCommerceBackEndMain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Expires {
  @Autowired
  ApplicationEventPublisher applicationEventPublisher;

  public void notification(String guestName){
    applicationEventPublisher.publishEvent(new ShopExpiresEvent(this, "expires"));
  }
}
