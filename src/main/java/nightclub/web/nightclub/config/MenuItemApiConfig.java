package nightclub.web.nightclub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "menuitem.api")
public class MenuItemApiConfig {
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public MenuItemApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}