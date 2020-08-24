package com.webank.oracle.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * properties of event register.
 */
@Data
@Configuration
@ConfigurationProperties("event")
public class EventRegisterProperties {
   List<EventRegister> eventRegisters;
}
