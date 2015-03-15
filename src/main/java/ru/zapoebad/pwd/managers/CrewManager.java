package ru.zapoebad.pwd.managers;

import com.dart.webadmin.logger.WebLogger;
import ru.zapoebad.pwd.objects.Crew;
import ru.zapoebad.pwd.objects.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makerspace on 21.02.2015.
 */
public class CrewManager {

    private static final WebLogger logger = new WebLogger(CrewManager.class);

    private static volatile CrewManager instance;

    private List<Crew> crewList = new ArrayList<Crew>();

    public static CrewManager getInstance() {
        CrewManager localInstance = instance;
        if (localInstance == null) {
            synchronized (CrewManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CrewManager();
                }
            }
        }
        return localInstance;
    }

    private CrewManager() {
        init();
    }

    private void init() {
        //TODO доставать из бд

        Crew p = new Crew(1, "Портал тех,кто делает", 1, "разработка портала", "текст");
        List<Integer> part = new ArrayList<Integer>();
        part.add(1);
        part.add(2);
        p.setParticipants(part);
        crewList.add(p);

        p = new Crew(2, "Горшок с автополивом", 5, "разработка гаджета", "проект для конкурса Гаджетстрой в Мейкерспейсе");
        part = new ArrayList<Integer>();
        part.add(1);
        part.add(4);
        part.add(5);
        p.setParticipants(part);
        crewList.add(p);

    }

    public List<Crew> getCrewList() {
        return crewList;
    }

    public Crew getCrew(int id) {
        if (crewList == null) return null;

        for (Crew crew : crewList) {
            if (crew.getId() == id) {
                return crew;
            }
        }
        return null;
    }
}
