package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.PersonManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DuMkA on 02.03.2015.
 */
@AjaxPath("/ajax/getPerson")
public class GetPerson implements AjaxController {
    private static final WebLogger logger = new WebLogger(GetPerson.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {
        int userId = HttpUtil.getIntValue(request, "id", -1);
        response.json("result", PersonManager.getInstance().getPerson(userId));
    }
}