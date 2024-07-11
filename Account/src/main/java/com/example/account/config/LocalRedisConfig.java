package com.example.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalRedisConfig {

    // 이 클래스에서는 내장 Redis 설정을 제거합니다.
    // Mac에 설치된 Redis를 사용하므로 별도의 내장 Redis 설정은 필요하지 않습니다.
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    // 필요한 경우 추가 설정을 수행할 수 있습니다.
}
