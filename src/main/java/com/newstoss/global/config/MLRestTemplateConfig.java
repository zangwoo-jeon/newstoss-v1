package com.newstoss.global.config;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class MLRestTemplateConfig {

    @Bean(name = "mlRestTemplate")
    public RestTemplate mlRestTemplate() {
        int maxConnTotal = 200;
        int maxConnPerRoute = 100;

        // 타임아웃 설정
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setSocketTimeout(Timeout.ofSeconds(10))
                .setConnectTimeout(Timeout.ofSeconds(5))
                .build();

        // 커넥션 풀 설정
        PoolingHttpClientConnectionManager connManager =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setMaxConnTotal(maxConnTotal)
                        .setMaxConnPerRoute(maxConnPerRoute)
                        .setDefaultConnectionConfig(connectionConfig)
                        .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .evictIdleConnections(TimeValue.ofSeconds(30)) // 커넥션 유휴 제거
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
