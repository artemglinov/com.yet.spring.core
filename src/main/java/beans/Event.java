package beans;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

@Component("event")
public class Event {

    private final int id;
    private String msg;
    private final Date date;
    private final DateFormat df;


    public Event(Date date, DateFormat df) {
        Random random = new Random();
        this.id = random.nextInt(10000);
        this.date = date;
        this.df = df;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "beans.Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(date) +
                '}';
    }
}
