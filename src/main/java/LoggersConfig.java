import loggers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:client.properties"})
public class LoggersConfig {

    @Value("${filename}")
    private String filename;

    @Value("${cacheSize}")
    private int cacheSize;

    @Autowired
    private ConsoleEventLogger consoleEventLogger;
    @Autowired
    private FileEventLogger fileEventLogger;


    @Bean
    public EventLogger eventLogger() {
        return cacheFileEventLogger();
    }

    @Bean
    public ConsoleEventLogger consoleEventLogger() {
//        this.consoleEventLogger = new ConsoleEventLogger();
        return new ConsoleEventLogger();
    }

    @Bean
    public FileEventLogger fileEventLogger() {
//        this.fileEventLogger = new FileEventLogger();
        return new FileEventLogger(filename);
    }

    @Bean
    public CacheFileEventLogger cacheFileEventLogger() {
        return new CacheFileEventLogger(filename, cacheSize);
    }

//    @Bean
//    public static PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
//                new PropertyPlaceholderConfigurer();
//        propertySourcesPlaceholderConfigurer.setLocation(new PathResource("client.properties"));
//        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
//        propertySourcesPlaceholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
//
//        return propertySourcesPlaceholderConfigurer;
//
//    }

    @Bean
    public CombinedEventLogger combinedEventLogger() {
        return new CombinedEventLogger(consoleEventLogger, fileEventLogger);
    }
}
