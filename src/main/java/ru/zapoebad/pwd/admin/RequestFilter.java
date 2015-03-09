package ru.zapoebad.pwd.admin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RequestFilter implements Filter {

    private Map<String, String> links = new HashMap<String, String>();

    public static final Pattern STATIC_CONTEXT_PATTERN = Pattern.compile("[.][a-z]*$");
    private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final String ENCODING_INIT_PARAM_NAME = "encoding";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        links.put("/", "/pages/events.jsp?");
        links.put("/events", "/pages/events.jsp?");
        links.put("/event", "/pages/event.jsp?");
        links.put("/editEvent", "/pages/editEvent.jsp?");

        links.put("/places", "/pages/places.jsp?");
        links.put("/place", "/pages/place.jsp?");
        links.put("/editPlace", "/pages/editPlace.jsp?");

        links.put("/person", "/pages/person.jsp?");
        links.put("/crew", "/pages/crew.jsp?");
        links.put("/crewList", "/pages/crewList.jsp?");
        links.put("/people", "/pages/people.jsp?");
        links.put("/editPerson", "/pages/editPerson.jsp?");
        links.put("/editCrew", "/pages/editCrew.jsp?");

        // ideas
        // searching
    }

    public static String getPath(ServletRequest request) {
        return ((HttpServletRequest) request).getRequestURI();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = null;

        /*
        String path = httpRequest.getPathInfo();
        if (path == null) {
            path = httpRequest.getServletPath();
        }
        */

        ///
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE)) {
            request.setCharacterEncoding("UTF-8");
        }

        String path = getPath(request);

        Matcher staticContextMatcher = STATIC_CONTEXT_PATTERN.matcher(path);

        if (staticContextMatcher.find() || path.startsWith("/ajax")) {
            chain.doFilter(request, response);
            return;
        }

        ///

        // TODO как то не секурно
        if (request.getParameter("logIn") != null) {
            ((HttpServletRequest) request).getSession().setAttribute("loggedUserId", request.getParameter("logIn"));
        }

        // кое какая корявая проверка на валидность
        boolean badRequest = false;
        if (path != null && path.contains("editPerson")) {
            String userId = request.getParameter("id");
            String logged = (String)((HttpServletRequest) request).getSession().getAttribute("loggedUserId");
            if (userId != null && !userId.equalsIgnoreCase(logged)) {
                // TODO maybe need real redirect
                badRequest = true;
            }
        }

        String realPath;

        if (links.get(path) == null || badRequest) {
            path = "/";
        }
        realPath = links.get(path);

        ((HttpServletRequest) request).getSession().setAttribute("workingPage", path);

        if (((HttpServletRequest) request).getQueryString() != null) {
            realPath += "?" + ((HttpServletRequest) request).getQueryString();
        }

        dispatcher = request.getRequestDispatcher(realPath);
        System.out.println("Requested = " + path + ", working = " + realPath);

        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
