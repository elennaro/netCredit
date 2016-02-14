package art.alex.config;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(90000000);
        factory.setMaxRequestSize(100000000);
        factory.setFileSizeThreshold(100000000);
//        factory.setLocation(env.getRequiredProperty("com.in2circle.image.processor.uploadtmp"));

        return factory.createMultipartConfig();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry vcr) {
        vcr.addViewController("/").setViewName("home");
        vcr.addViewController("/home").setViewName("home");
        vcr.addViewController("/login").setViewName("login");
        vcr.addViewController("/profile").setViewName("profile");
    }
}
