package ru.zapoebad.pwd.managers;

import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import com.dart.webadmin.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.zapoebad.pwd.admin.ApplInitServlet;
import ru.zapoebad.pwd.objects.Person;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dumka on 19.02.15.
 */
public class PersonManager {

    private static final WebLogger logger = new WebLogger(PersonManager.class);

    private static volatile PersonManager instance;

    private List<Person> people = new ArrayList<Person>();

    public static PersonManager getInstance() {
        PersonManager localInstance = instance;
        if (localInstance == null) {
            synchronized (PersonManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PersonManager();
                }
            }
        }
        return localInstance;
    }

    private PersonManager() {
        init();
    }

    private void init() {
        //TODO доставать из бд

        File dir = new File(ApplInitServlet.getDataPath() + "person");
        if (dir.exists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                for (File file : dir.listFiles()) {
                    try {
                        Person person = objectMapper.readValue(file, Person.class);
                        people.add(person);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        logger.debug("Loaded persons = " + people.size());

        /*
        Person p = new Person(1, "Дмитрий", "Плюснин", "автор проекта", "тут текст", "/id1195447");
        people.add(p);
        p = new Person(2, "Наталья", "Плюснина", "управление делами проекта", "и тут текст", "/churyukaeva");
        people.add(p);
        p = new Person(3, "Иван", "Созинов", "мимо проходил", "тоже текст", "/vault");
        people.add(p);
        p = new Person(4, "Данил", "Борщов", "электронщик", "текст", "/");
        people.add(p);
        p = new Person(5, "Михаил", "Гайворонский", "инженер", "текст", "/");
        people.add(p);
        p = new Person(6, "Семен", "Тараканов", "мейкер", "текст", "/");
        people.add(p);
        */

    }

    public List<Person> getPeople() {
        return people;
    }

    public Person getPerson(int id) {
        if (people == null) return null;

        for (Person person : people) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public int getNewId() {
        int max = -1;
        for (Person pers : people) {
            if (pers.getId() > max)
                max = pers.getId();
        }
        return max + 1;
    }

    public void savePerson(Person person) {

        for (Iterator<Person> it = people.iterator(); it.hasNext(); ) {
            Person p = it.next();

            if (p.getId() == person.getId()) {
                it.remove();
                break;
            }
        }
        people.add(person);

        savePersonToFile(person);
    }

    private void savePersonToFile(Person person) {

        File file = new File(ApplInitServlet.getDataPath() + "person" + File.separator + "person" + person.getId() + ".json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            file.createNewFile();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

            objectMapper.writeValue(file, person);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void processEdit(HttpServletRequest request) {
        int personId = HttpUtil.getIntValue(request, "personId", -1);

        Person person = PersonManager.getInstance().getPerson(personId);

        if (person != null) {

            String loggedUserId = (String)request.getSession().getAttribute("loggedUserId");

            if (loggedUserId == null || !loggedUserId.equalsIgnoreCase(personId + "")) {

                // не залогинен или залогинен но не совпадает с редактируемым
                return;
            }

        } else {
            // add new
            personId = PersonManager.getInstance().getNewId();

            // logging new user
            ((HttpServletRequest) request).getSession().setAttribute("loggedUserId", personId + "");

            person = new Person();
            person.setId(personId);
        }

        // saving

        person.setName(HttpUtil.getValue(request, "name", ""));
        person.setLastName(HttpUtil.getValue(request, "lastName", ""));
        person.setNick(HttpUtil.getValue(request, "nick", ""));
        person.setDesc(HttpUtil.getValue(request, "desc", ""));
        person.setVkAccount(HttpUtil.getValue(request, "vk", ""));
        person.setText(HttpUtil.getValue(request, "text", ""));

        PersonManager.getInstance().savePerson(person);
    }
}
