package com.gsdd.dw2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cloud check operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/state")
public class CloudStateController {

  @Value("${service.welcome.message}")
  private String welcomeMessage;

  @GetMapping("/welcome")
  public String getWelcomeMsg() {
    return welcomeMessage;
  }
}
