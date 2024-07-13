package nightclub.web.nightclub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {

    @Bean("offersRestClient")
    public RestClient offersRestClient(MenuItemApiConfig menuItemApiConfig) {
        return RestClient
                .builder()
                .baseUrl(menuItemApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
