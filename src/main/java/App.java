import beans.Client;
import beans.Event;
import loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("app")
public class App {

    private Client client;
    private EventLogger eventLogger;
    private final Map<EventType, EventLogger> loggers;

    @Autowired
    public App( Client client,   EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {

//        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.register(AppConfig.class, LoggersConfig.class);

        ctx.refresh();

        App app = (App) ctx.getBean("app");

        Event event = (Event) ctx.getBean("event");

        String msg = "0";

//        app.logEvent(EventType.valueOf("ERROR"), event, msg);

        app.logEvent(null, event, msg);

        ctx.close();
    }

    private void logEvent(EventType type, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);


        EventLogger logger = loggers.get(type);

        if (type == null) {
            logger = eventLogger;
        }

        logger.logEvent(event);

    }
}
