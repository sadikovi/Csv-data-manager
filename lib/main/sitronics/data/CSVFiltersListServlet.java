package main.sitronics.data;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import main.sitronics.data.AuthUtil;


public class CSVFiltersListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE ="text/html; charset=windows-1251";

    /**Process the HTTP doGet request.
     */
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        doGet(request, response);
    }

    /**Process the HTTP doPost request.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Error error = new Error();
        
        String serverUrl = null;
        if (Configuration.getInstance().biServerURL != null && Configuration.getInstance().biServerURL.length() > 0) {
            serverUrl = Configuration.getInstance().biServerURL;
        } else {
            serverUrl = request.getScheme()+ "://" + request.getServerName() + ":" + request.getServerPort();
        }
        
        String id = AuthUtil.getUserID(request);
        
        if (id == null || !AuthUtil.isAuthenticated(request, serverUrl)) {
            error.throwError(response, "001", "Unauthorised user tried to access csv import utility.");
        } else if (Configuration.getInstance() == null) {
            error.throwError(response, "002", "Configuration file is not available.");
        } else {
            String[] userRoles = AuthUtil.getUserRoles(request, serverUrl).split(";");
            
            String JSONFiltersList = "[ ";
            List<String> items = new ArrayList<String>();
            
            for (String role : userRoles) {
                for (int i=0; i<Configuration.getInstance().content.size(); i++) {
                    if (Configuration.getInstance().content.get(i).roles.contains(role)) {
                        if (!items.contains(Configuration.getInstance().content.get(i).filterName)) {
                            items.add(Configuration.getInstance().content.get(i).filterName);
                        }
                    }
                }
                
            }

            for (int i=0;i<items.size();i++) {
                if (i == items.size() - 1) {
                    JSONFiltersList += "{ " + 
                                       "\"filter\"" + " : " + "\"" + items.get(i) + "\"" + ", " + 
                                       "\"filter_id\"" + " : " + "\"" + Configuration.getInstance().checkIDByFilter(items.get(i)) + "\""  + 
                                       " }";
                } else {
                    JSONFiltersList += "{ " + 
                                       "\"filter\"" + " : " + "\"" + items.get(i) + "\"" + ", " + 
                                       "\"filter_id\"" + " : " + "\"" + Configuration.getInstance().checkIDByFilter(items.get(i)) + "\""  + 
                                       " }, ";
                }
            }
            JSONFiltersList += " ]";

            response.getWriter().print(JSONFiltersList);
        }
    }
}
