package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import ru.zapoebad.pwd.managers.PersonManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dumka on 20.02.15.
 */
@AjaxPath("/ajax/getPeople")
public class GetPeople implements AjaxController {
    private static final WebLogger logger = new WebLogger(GetPeople.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {
        response.json("result", PersonManager.getInstance().getPeople());
    }
}
