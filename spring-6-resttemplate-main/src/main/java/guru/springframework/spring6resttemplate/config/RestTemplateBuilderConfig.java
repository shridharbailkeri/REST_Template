package guru.springframework.spring6resttemplate.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RestTemplateBuilderConfig {

    // use spring expression language to get that property
    // go to app.properties file and add rest.template.rootUrl=http://localhost:8080
    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        assert rootUrl != null;
        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());
        // so what this configurer does is it takes the new instance and then configures
        // with the spring boot defaults
        // now for setting default uri we want to set up default base path for api calls
        // to this we use builder factory



        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(rootUrl);

        return builder.uriTemplateHandler(uriBuilderFactory);

        // its important to do this was because u might want to just set the property there
        // but this creates a new instance and returns it back

    }
}
