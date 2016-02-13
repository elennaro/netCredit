package art.alex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry vcr) {
        vcr.addViewController("/").setViewName("home");
        vcr.addViewController("/home").setViewName("home");
        vcr.addViewController("/login").setViewName("login");
    }
}
