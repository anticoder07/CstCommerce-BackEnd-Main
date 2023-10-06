package com.CstCommerce.CstCommerceBackEndMain.securityConfig;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationSseEmitterManager {
  private final List<SseEmitter> emitters = new ArrayList<>();

  public void addEmitter(SseEmitter emitter) {
    emitters.add(emitter);
  }

  public void removeEmitter(SseEmitter emitter) {
    emitters.remove(emitter);
  }

  public void sendNotification(String message) {
    List<SseEmitter> deadEmitters = new ArrayList<>();
    emitters.forEach(emitter -> {
      try {
        emitter.send(SseEmitter.event()
                .data(message)
                .name("notification"));
      } catch (IOException e) {
        deadEmitters.add(emitter);
      }
    });

    emitters.removeAll(deadEmitters);
  }
}
