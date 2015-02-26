package ru.zapoebad.pwd.ajax;

import java.io.IOException;

//import kc.upf.sec.session.DataSession;
//import AppListener;
import com.dart.webadmin.ajax.AjaxServlet;
import com.dart.webadmin.ajax.UserMessageException;
import com.dart.webadmin.utils.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by dumka on 20.02.15.
 */
public class PortalAjaxServlet extends AjaxServlet {
    @Override
    public void getSessionAndValid(HttpServletRequest request, HttpServletResponse response) throws UserMessageException {
        /*
    	String cook = HttpUtil.getCookieValue(request, "USER_SESSION_ID");

    	if(cook != null) {
        	DataSession session = AppListener.getUpfBean().core.userSession.getSessionById(cook);

            if (session == null || !session.validSession()) {
                throw new UserMessageException("Недостаточно прав доступа");
            }
            request.setAttribute("session", session);
    	} else {
    		try {
				response.sendRedirect("/");
			} catch (IOException e) {
				throw new UserMessageException(e.getLocalizedMessage());
			}
    	}
        */
    }

	@Override
	public void getSessionAndValid(HttpServletRequest request) throws UserMessageException {
        /*
		String cook = HttpUtil.getCookieValue(request, "USER_SESSION_ID");
    	DataSession session = AppListener.getUpfBean().core.userSession.getSessionById(cook);

        if (session == null || !session.validSession()) {
            throw new UserMessageException("Недостаточно прав доступа");
        }
        request.setAttribute("session", session);
        */
	}
}