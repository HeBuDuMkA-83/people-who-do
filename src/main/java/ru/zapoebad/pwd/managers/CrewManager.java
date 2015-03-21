package ru.zapoebad.pwd.managers;

import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.zapoebad.pwd.admin.ApplInitServlet;
import ru.zapoebad.pwd.objects.Crew;
import ru.zapoebad.pwd.objects.Person;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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

        File dir = new File(ApplInitServlet.getDataPath() + "crew");
        if (dir.exists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                for (File file : dir.listFiles()) {
                    try {
                        Crew crew = objectMapper.readValue(file, Crew.class);
                        crewList.add(crew);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        logger.debug("Loaded crews = " + crewList.size());

        /*
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
        */

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

    public int getNewId() {
        int max = -1;
        for (Crew pers : crewList) {
            if (pers.getId() > max)
                max = pers.getId();
        }
        return max + 1;
    }

    public void saveCrew(Crew crew) {

        for (Iterator<Crew> it = crewList.iterator(); it.hasNext(); ) {
            Crew p = it.next();

            if (p.getId() == crew.getId()) {
                it.remove();
                break;
            }
        }
        crewList.add(crew);

        saveCrewToFile(crew);
    }

    private void saveCrewToFile(Crew crew) {

        File file = new File(ApplInitServlet.getDataPath() + "crew" + File.separator + "crew" + crew.getId() + ".json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            file.createNewFile();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

            objectMapper.writeValue(file, crew);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void processEdit(HttpServletRequest request) {
        int crewId = HttpUtil.getIntValue(request, "crewId", -1);

        Crew crew = CrewManager.getInstance().getCrew(crewId);

        String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");

        if (loggedUserId == null) { // не залогинен
            return;
        }

        if (crew != null) {

            if (!loggedUserId.equalsIgnoreCase(crew.getOwner() + "")) {

                // залогинен но не совпадает с владельцем
                return;
            }

        } else {
            // add new
            crewId = CrewManager.getInstance().getNewId();

            crew = new Crew();
            crew.setId(crewId);
            crew.setOwner(Integer.parseInt(loggedUserId));
        }

        // saving

        crew.setName(HttpUtil.getValue(request, "name", ""));
        crew.setDesc(HttpUtil.getValue(request, "desc", ""));
        crew.setText(HttpUtil.getValue(request, "text", ""));

        CrewManager.getInstance().saveCrew(crew);
    }
}
