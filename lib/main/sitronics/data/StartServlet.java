package main.sitronics.data;

import javax.servlet.*;
import javax.servlet.http.*;

public class StartServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1251";
    private static String CONFIGURATION_PATH = "";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String configFilePath = config.getInitParameter("configFilePath");;

        if (configFilePath == null || configFilePath.equals("")) {
            configFilePath = getServletContext().getRealPath("WEB-INF/csv_load_configuration.xml");
        }
        
        this.CONFIGURATION_PATH = configFilePath;
        Configuration.getInstance().loadConfiguration(configFilePath);
    }
}
