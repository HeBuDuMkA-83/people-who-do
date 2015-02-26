package ru.zapoebad.pwd.objects;

import java.util.Date;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Event extends Origin {

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
