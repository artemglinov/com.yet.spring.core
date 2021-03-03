package loggers;

import beans.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component("combinedEventLogger")
public class CombinedEventLogger implements EventLogger {

    private Collection<EventLogger> loggers;


//    private ConsoleEventLogger consoleEventLogger;
//
//    private FileEventLogger fileEventLogger;

//    public CombinedEventLogger(Collection<EventLogger> loggers) {
//        this.loggers = loggers;
//    }

    @Autowired
    public CombinedEventLogger(ConsoleEventLogger consoleEventLogger, FileEventLogger fileEventLogger) {
        this.loggers = Arrays.asList(consoleEventLogger, fileEventLogger);
    }



    public Collection<EventLogger> getLoggers() {
        return Collections.unmodifiableCollection(loggers);
    }

    public void setLoggers(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }
}
