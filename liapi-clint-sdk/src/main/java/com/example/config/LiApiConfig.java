package com.example.config;

import com.example.client.NameClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("liapi.clint")
@ComponentScan
public class LiApiConfig {
      private String accessKey;
      private String secretKey;
      @Bean
      public NameClient liApiClient(){
            return new NameClient(accessKey,secretKey);
      }
}
