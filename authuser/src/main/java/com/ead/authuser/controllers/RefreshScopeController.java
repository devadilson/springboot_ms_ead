package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RefreshScope
public class RefreshScopeController {

  @Value("${ead.authuser.refreshscope.name}")
  private String name;

  @RequestMapping("/refreshscope")
  String refreshscope() {
    return this.name;
  }
}
