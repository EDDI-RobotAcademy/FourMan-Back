package fourman.backend.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")

                .allowedOrigins("http://127.0.0.1:8887",
                                "http://localhost:8887",
                                "http://13.209.30.13")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
        //"http://ec2-13-209-30-13.ap-northeast-2.compute.amazonaws.com"

    }
}