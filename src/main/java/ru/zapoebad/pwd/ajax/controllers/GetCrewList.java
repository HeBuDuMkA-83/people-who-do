package ru.zapoebad.pwd.ajax.controllers;

import com.dart.webadmin.ajax.AjaxController;
import com.dart.webadmin.ajax.AjaxPath;
import com.dart.webadmin.ajax.AjaxResponse;
import com.dart.webadmin.logger.WebLogger;
import com.dart.webadmin.utils.HttpUtil;
import ru.zapoebad.pwd.managers.CrewManager;
import ru.zapoebad.pwd.managers.PersonManager;
import ru.zapoebad.pwd.objects.Crew;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makerspace on 21.02.2015.
 */
@AjaxPath("/ajax/getCrewList")
public class GetCrewList implements AjaxController {

    private static final WebLogger logger = new WebLogger(GetCrewList.class);

    @Override
    public void execute(HttpServletRequest request, AjaxResponse response) throws Exception {

        int userId = HttpUtil.getIntValue(request, "userId", -1);
        if (userId > -1) {
            List<Crew> list = new ArrayList<Crew>();
            for (Crew crew : CrewManager.getInstance().getCrewList()) {
                for (Integer pers : crew.getParticipants()) {
                    if (pers != null && pers == userId) {
                        list.add(crew);
                    }
                }
            }
            response.json("result", list);
        } else {
            response.json("result", CrewManager.getInstance().getCrewList());
        }
    }
}