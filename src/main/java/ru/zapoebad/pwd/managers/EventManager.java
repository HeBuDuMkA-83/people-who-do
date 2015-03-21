package ru.zapoebad.pwd.managers;

import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.zapoebad.pwd.admin.ApplInitServlet;
import ru.zapoebad.pwd.objects.Crew;
import ru.zapoebad.pwd.objects.Event;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DuMkA on 09.03.2015.
 */
public class EventManager {

    private static final WebLogger logger = new WebLogger(EventManager.class);

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

        File dir = new File(ApplInitServlet.getDataPath() + "event");
        if (dir.exists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                for (File file : dir.listFiles()) {
                    try {
                        Event event = objectMapper.readValue(file, Event.class);
                        events.add(event);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        logger.debug("Loaded events = " + events.size());

        /*
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
        */

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

    public int getNewId() {
        int max = -1;
        for (Event pers : events) {
            if (pers.getId() > max)
                max = pers.getId();
        }
        return max + 1;
    }

    public void saveEvent(Event event) {

        for (Iterator<Event> it = events.iterator(); it.hasNext(); ) {
            Event p = it.next();

            if (p.getId() == event.getId()) {
                it.remove();
                break;
            }
        }
        events.add(event);

        saveEventToFile(event);
    }

    private void saveEventToFile(Event event) {

        File file = new File(ApplInitServlet.getDataPath() + "event" + File.separator + "event" + event.getId() + ".json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            file.createNewFile();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

            objectMapper.writeValue(file, event);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void processEdit(HttpServletRequest request) {
        int eventId = HttpUtil.getIntValue(request, "eventId", -1);

        Event event = EventManager.getInstance().getEvent(eventId);

        String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");

        if (loggedUserId == null) { // не залогинен
            return;
        }

        if (event != null) {

            if (!loggedUserId.equalsIgnoreCase(event.getOwner() + "")) {

                // залогинен но не совпадает с владельцем
                return;
            }

        } else {
            // add new
            eventId = EventManager.getInstance().getNewId();

            event = new Event();
            event.setId(eventId);
            event.setOwner(Integer.parseInt(loggedUserId));
        }

        // saving

        event.setName(HttpUtil.getValue(request, "name", ""));
        event.setDesc(HttpUtil.getValue(request, "desc", ""));
        event.setText(HttpUtil.getValue(request, "text", ""));

        EventManager.getInstance().saveEvent(event);
    }
}
