package com.nisum.user.api.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class TokenJwtProperties {

    private String issuer;

    private Map<TokenType, TokenProperties> types;

    public enum TokenType {
        USER,
        SECURITY
    }

    @Getter
    @Setter
    public static class TokenProperties {

        private String secret;

        private Long expiredTime;

    }
}

