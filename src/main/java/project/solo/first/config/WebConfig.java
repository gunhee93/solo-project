package project.solo.first.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowedHeaders("Access-Control-Allow-Origin",
                        "*",
                        "Access-Control-Allow-Methods",
                        "GET, POST, PATCH, DELETE",
                        "Access-Control-Allow-Headers",
                        "Origin, Content-Type, Accept, X-Requested-With")
                .allowCredentials(true)
                .maxAge(3000);
    }
}
