package com.CstCommerce.CstCommerceBackEndMain.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ShopExpiresEvent extends ApplicationEvent {
  private String guestName;

  public String getGuestName() {
    return guestName;
  }

  public void setGuestName(String guestName) {
    this.guestName = guestName;
  }

  public ShopExpiresEvent(Object source) {
    super(source);
  }

  public ShopExpiresEvent(Object source, Clock clock) {
    super(source, clock);
  }

  public ShopExpiresEvent(Object source, String guestName) {
    super(source);
  }
}
