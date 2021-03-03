package loggers;

import beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component("fileEventLogger")
public class FileEventLogger implements EventLogger{

    private String filename;

    private File file;

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public void logEvent(Event event)  {
        try {
            FileUtils.writeStringToFile(file, event.getMsg(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    public void init() throws IOException {
        this.file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file " + filename);
        } else if (!file.exists()) {
            file.createNewFile();
        }
    }
}
