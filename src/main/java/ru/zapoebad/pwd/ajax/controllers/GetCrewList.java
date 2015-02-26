package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import ru.zapoebad.pwd.managers.CrewManager;
import ru.zapoebad.pwd.managers.PersonManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Makerspace on 21.02.2015.
 */
@AjaxPath("/ajax/getCrewList")
public class GetCrewList implements AjaxController {

    private static final WebLogger logger = new WebLogger(GetCrewList.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {
        response.json("result", CrewManager.getInstance().getCrewList());
    }
}