package ru.zapoebad.pwd.objects;

import ru.zapoebad.pwd.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuMkA on 19.02.2015.
 */
public class Crew extends Origin {

    private List<Integer> participants = new ArrayList<Integer>();

    private int owner;

    public Crew() {

    }

    public Crew(int id, String name, int owner, String desc, String text) {
        setId(id);
        setName(name);
        setOwner(owner);
        setDesc(desc);
        setText(text);
        setAvatarPath(Constants.AVATAR_DUMMY);
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
