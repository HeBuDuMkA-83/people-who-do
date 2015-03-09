package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.CrewManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DuMkA on 08.03.2015.
 */
@AjaxPath("/ajax/getCrew")
public class GetCrew implements AjaxController {
    private static final WebLogger logger = new WebLogger(GetCrewList.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {
        int crewId = HttpUtil.getIntValue(request, "id", -1);
        response.json("result", CrewManager.getInstance().getCrew(crewId));
    }
}
