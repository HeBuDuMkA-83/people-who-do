package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.CrewManager;
import ru.zapoebad.pwd.managers.EventManager;
import ru.zapoebad.pwd.managers.PersonManager;
import ru.zapoebad.pwd.objects.Crew;
import ru.zapoebad.pwd.objects.Event;
import ru.zapoebad.pwd.objects.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dumka on 20.02.15.
 */
@AjaxPath("/ajax/getPeople")
public class GetPeople implements AjaxController {
    private static final WebLogger logger = new WebLogger(GetPeople.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {

        int crewId = HttpUtil.getIntValue(request, "crewId", -1);
        int eventId = HttpUtil.getIntValue(request, "eventId", -1);
        if (crewId > -1) {
            List<Person> list = new ArrayList<Person>();
            Crew crew = CrewManager.getInstance().getCrew(crewId);
            if (crew != null) {
                for (Integer id : crew.getParticipants()) {
                    Person pers = PersonManager.getInstance().getPerson(id);
                    if (pers != null) {
                        list.add(pers);
                    }
                }
            }
            response.json("result", list);

        } else if (eventId > - 1) {
            List<Person> list = new ArrayList<Person>();
            Event event = EventManager.getInstance().getEvent(eventId);
            if (event != null) {
                for (Integer id : event.getParticipants()) {
                    Person pers = PersonManager.getInstance().getPerson(id);
                    if (pers != null) {
                        list.add(pers);
                    }
                }
            }
            response.json("result", list);

        } else {
            response.json("result", PersonManager.getInstance().getPeople());
        }
    }
}
