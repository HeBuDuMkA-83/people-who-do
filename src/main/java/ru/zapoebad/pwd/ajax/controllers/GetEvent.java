package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.EventManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DuMkA on 15.03.2015.
 */
@AjaxPath("/ajax/getEvent")
public class GetEvent implements AjaxController {
    private static final WebLogger logger = new WebLogger(GetEvent.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {
        int eventId = HttpUtil.getIntValue(request, "eventId", -1);
        response.json("result", EventManager.getInstance().getEvent(eventId));
    }
}