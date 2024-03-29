package ru.zapoebad.pwd.objects;

import ru.zapoebad.pwd.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Event extends Origin {

    private List<Integer> participants = new ArrayList<Integer>();

    private Date date;
    private int owner;

    public Event() {

    }

    public Event(int id, String name, int owner, String desc, String text) {
        setId(id);
        setName(name);
        setOwner(owner);
        setDate(new Date());
        setDesc(desc);
        setText(text);
        setAvatarPath(Constants.AVATAR_DUMMY);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
