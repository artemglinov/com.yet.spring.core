package loggers;

import beans.Event;
import org.springframework.stereotype.Component;

@Component("ConsoleEventLogger")
public class ConsoleEventLogger implements EventLogger {

    public ConsoleEventLogger() {
    }

    public void logEvent(Event event) {
        System.out.println(event);
    }


}
