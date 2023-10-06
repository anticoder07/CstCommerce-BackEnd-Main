package com.CstCommerce.CstCommerceBackEndMain.service.Auto;

import com.CstCommerce.CstCommerceBackEndMain.event.Expires;
import com.CstCommerce.CstCommerceBackEndMain.repository.ShopRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class AutoServiceImpl implements AutoService {
  private final ShopRepository shopRepository;

  private final Expires expires;

  @Override
  @Transactional
  @Scheduled(fixedRate = 86400000)
  public void autoScamPayload() throws JsonProcessingException {
//    shopRepository.alterNotificationExpired("deposit due", Instant.now().minusSeconds(), );
    expires.notification("expires");
  }
}
