package ru.zapoebad.pwd.admin;

import com.dart.webadmin.ajax.AnnotationEngine;
import org.apache.log4j.Logger;
import ru.zapoebad.pwd.managers.CrewManager;
import ru.zapoebad.pwd.managers.EventManager;
import ru.zapoebad.pwd.managers.PersonManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.sql.Connection;
import java.util.Properties;


public class ApplInitServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ApplInitServlet.class);

    private static String applPath;
    private static String dataPath;
    private static Properties properties;
    private static boolean local;

    public static String getApplPath() {
        return applPath;
    }

    public static String getDataPath() {
        return dataPath;
    }

    public static String getSiteShotUrl() {
        return properties.getProperty("site.shorturl");
    }

    public static Properties getProperties() {
        return properties;
    }

    public static boolean isLocal() {
        return local;
    }

    @Override
    public void destroy() {

        super.destroy();
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            applPath = servletConfig.getServletContext().getRealPath("/");
            if (!applPath.endsWith(File.separator))
                applPath += File.separator;
            logger.info("start service. ApplPath: " + applPath);

            String propPath = applPath + "WEB-INF" + File.separator + servletConfig.getInitParameter("property_file_name");
            logger.info("start service. loading propeties file: " + propPath);
            properties = new Properties();
            InputStream propsInput = new FileInputStream(propPath);
            properties.load(propsInput);
            propsInput.close();
            logger.info("start service. properties file loaded. ");
            ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
            PrintStream tmpPrintStream = new PrintStream(tmpOut);
            properties.list(tmpPrintStream);
            logger.info(new String(tmpOut.toByteArray()));

            local = properties.getProperty("app.local", "false").equalsIgnoreCase("true") ;

            dataPath = getProperties().getProperty("data.path", applPath + File.separator + "data");
            if (!dataPath.endsWith(File.separator)) {
                dataPath += File.separator;
            }

            AnnotationEngine.scan("ru.zapoebad.pwd.ajax.controllers");

            PersonManager.getInstance();
            CrewManager.getInstance();
            EventManager.getInstance();

            logger.debug("service started");

        } catch (Throwable tr) {
            logger.error("start service ERROR", tr);
        }
    }
}
