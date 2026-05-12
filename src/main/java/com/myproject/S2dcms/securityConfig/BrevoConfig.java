package com.myproject.S2dcms.securityConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sibApi.TransactionalEmailsApi;
import sendinblue.ApiClient;
import sendinblue.auth.ApiKeyAuth;

@Configuration
public class BrevoConfig {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Bean
    public TransactionalEmailsApi transactionalEmailsApi() {

        ApiClient apiClient = new ApiClient();

        ApiKeyAuth apiKeyAuth =
                (ApiKeyAuth) apiClient.getAuthentication("api-key");

        apiKeyAuth.setApiKey(apiKey);

        return new TransactionalEmailsApi(apiClient);
    }
}
