import beans.Client;
import beans.Event;
import loggers.CombinedEventLogger;
import loggers.ConsoleEventLogger;
import loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Import(LoggersConfig.class)
@PropertySource(value = {"classpath:client.properties"})
public class AppConfig {

    @Value("${id}")
    private String id;

    @Value("${name}")
    private String fullName;

    @Autowired
    CombinedEventLogger combinedEventLogger;

    @Autowired
    ConsoleEventLogger consoleEventLogger;

    private Client client;

    @Autowired
    @Qualifier("cacheFileEventLogger")
    private EventLogger eventLogger;

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event(new Date(), java.text.DateFormat.getDateInstance());
    }

    @Bean
    public Client client() {
        this.client = new Client(id, fullName);
        return client;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
                new PropertySourcesPlaceholderConfigurer();
//        propertySourcesPlaceholderConfigurer.setLocation(new PathResource("client.properties"));
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
//        propertySourcesPlaceholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);

        return propertySourcesPlaceholderConfigurer;

    }

//    @Bean
//    public static PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
//                new PropertyPlaceholderConfigurer();
////        propertySourcesPlaceholderConfigurer.setLocation(new PathResource("classpath:client.properties"));
//        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
//        propertySourcesPlaceholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
//
//        return propertySourcesPlaceholderConfigurer;
//
//    }

    @Bean
    public App app() {
        Map<EventType, EventLogger> loggers = new HashMap<>();
        loggers.put(EventType.INFO, consoleEventLogger);
        loggers.put(EventType.ERROR, combinedEventLogger);

        return new App(client, eventLogger, loggers);
    }
}
