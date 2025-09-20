package com.ct.squad.spend.sense.commons.config;

import com.ct.squad.spend.sense.commons.http.AgentService;
import com.ct.squad.spend.sense.commons.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class AgentClientConfig {

    private final AppProperties appProperties;

    @Bean
    public AgentService agentService(WebClient.Builder webClientBuilder) {
        WebClient webClient = WebClient.builder()
                .baseUrl(appProperties.getAgentServiceUrl())
                .build();

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build();

        return proxyFactory.createClient(AgentService.class);
    }

}
