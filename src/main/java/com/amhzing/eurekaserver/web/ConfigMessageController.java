package com.amhzing.eurekaserver.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigMessageController {

    @Value("${config.message}")
    private String configMessage;

    @RequestMapping("/config-message")
    public String configMessage() {
        return this.configMessage;
    }
}
