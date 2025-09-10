package sn.unchk.gestiontransfert.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.unchk.gestiontransfert.web.filter.ResponseFormattingFilter;

@Configuration
public class FilterConfig {

    @Bean
    public Filter responseFormattingFilter() {
        return new ResponseFormattingFilter();
    }
}
