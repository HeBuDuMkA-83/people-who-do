package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.PersonManager;
import ru.zapoebad.pwd.objects.Person;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DuMkA on 15.03.2015.
 */
@AjaxPath("/ajax/savePerson")
public class SavePerson implements AjaxController {
    private static final WebLogger logger = new WebLogger(SavePerson.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {

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
        person.setDesc(HttpUtil.getValue(request, "desc", ""));
        person.setVkAccount(HttpUtil.getValue(request, "vk", ""));
        person.setText(HttpUtil.getValue(request, "text", ""));

        PersonManager.getInstance().savePerson(person);


        //response.json("result", "saved");
    }
}