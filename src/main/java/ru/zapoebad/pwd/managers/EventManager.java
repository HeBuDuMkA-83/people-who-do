package ru.zapoebad.pwd.managers;

import ru.zapoebad.pwd.objects.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuMkA on 09.03.2015.
 */
public class EventManager {

    private static volatile EventManager instance;

    private List<Event> events = new ArrayList<Event>();

    public static EventManager getInstance() {
        EventManager localInstance = instance;
        if (localInstance == null) {
            synchronized (EventManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EventManager();
                }
            }
        }
        return localInstance;
    }

    private EventManager() {
        init();
    }

    private void init() {

        Event p = new Event(1, "«Чего хочет Пермь»", 1, "Хакатон", " Участники обсудят и разработают идеи социальных и IT-проектов, которые реально могут изменить город и жизнь горожан. Кем бы вы ни были: городским активистом, блогером, журналистом, дизайнером, программистом — вы можете собрать команду и при помощи менторов реализовать свою идею, сделать город удобным и красивым. Победитель хакатона получит право на доработку проекта в размере 60 тыс. рублей.");
        List<Integer> part = new ArrayList<Integer>();
        part.add(1);
        part.add(2);
        p.setParticipants(part);
        events.add(p);

        p = new Event(2, "Гаджетстрой", 6, "разработка гаджетов", "Гаджетстрой - это конкурс по созданию умных девайсов, которые сделают нашу жизнь лучше. \n" +
                "В рамках нашей программы команды по 5 человек за 3 недели смогут сделать прототипы собственных умных устройств, продумать план их продвижения, и дальнейшего развития проекта.");
        part = new ArrayList<Integer>();
        part.add(1);
        part.add(4);
        part.add(5);
        p.setParticipants(part);
        events.add(p);

    }

    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent(int id) {
        if (events == null) return null;

        for (Event ev : events) {
            if (ev.getId() == id) {
                return ev;
            }
        }
        return null;
    }
}
