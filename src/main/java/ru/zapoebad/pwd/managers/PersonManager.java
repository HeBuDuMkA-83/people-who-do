package ru.zapoebad.pwd.managers;

import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.JsonUtil;
import ru.zapoebad.pwd.admin.ApplInitServlet;
import ru.zapoebad.pwd.objects.Person;

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
        return max;
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

        File file = new File(ApplInitServlet.getApplPath() + "data" + File.separator + "person" + File.separator + "person" + person.getId() + ".json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            file.createNewFile();

            FileWriter writer = new FileWriter(file);

            JsonUtil.encode(person, writer);
            writer.flush();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
