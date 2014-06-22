package main.sitronics.data;

import java.io.IOException;

import java.io.InputStream;

import java.nio.charset.Charset;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class CSVImportServlet extends HttpServlet {
    @SuppressWarnings("compatibility:-2553235768614307786")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1251";
    
    private boolean isEmpty(String str) {
        return (str == null || str.length() <= 0);
    }
    

    /**Process the HTTP doGet request.
     * Actually GET request's forbidden here
     * So, a error will be occured
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        //doPost(request, response);
        Error e = new Error();
        e.throwError(response, "004", "GET request's forbidden. Try to use POST request");
    }

    /**Process the HTTP doPost request.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        //initialize error instance
        Error error = new Error();
        /**
         * use third level check parameters
         * 1. username is valid (if it's not then error'd be thrown)
         * 2. filename is not empty (if it's empty then error'd be thrown)
         * 3. file is not empty and gets the .csv format (if it didn't fit the reqierements then error'd be thrown)
         */
        String serverUrl = null;
        if (Configuration.getInstance().biServerURL != null && Configuration.getInstance().biServerURL.length() > 0) {
            serverUrl = Configuration.getInstance().biServerURL;
        } else {
            serverUrl = request.getScheme()+ "://" + request.getServerName() + ":" + request.getServerPort();
        }
        
        //check user
        String username = getUser(request, response, serverUrl);
        
        if (username == null || username.equals("")) {
            error.throwError(response, "001", "Unauthorised user tried to access csv import utility.");
        } else {
            //check the file and filename
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = null;

                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }

                String filename = null;
                String loadFormat = null;
                InputStream csvInput = null;

                for (FileItem item : items) {
                    if (item.getFieldName().equals("csvImportName")) {
                        filename = new String(item.get());
                    }
                    if (item.getFieldName().equals("csvImportFile") && item.getContentType().equals("text/csv")) {
                        csvInput = item.getInputStream();
                    }
                    if (item.getFieldName().equals("csvImportFormat")) {
                        loadFormat = new String(item.get());
                    }
                }
                factory = null;
                upload = null;
                items = null;
                
                //check the filename, csv file and loadformat are correct
                if (filename == null || filename.equals("")) {
                    error.throwError(response, "002", "Filename is required");
                } else if (csvInput == null) {
                    error.throwError(response, "002", "CSV file is required");
                } else if (loadFormat == null || loadFormat.equals("")) {
                    error.throwError(response, "002", "Load format is required");
                } else {
                    //get parameters
                    String separator = Configuration.getInstance().getSeparator(loadFormat);
                    String charset = "ISO-8859-1";//domparser.getCharsetString(domparser.getDOM(), loadFormat);
                    int columnsCount = Configuration.getInstance().getColumnsCount(loadFormat);
                    int startRow = Configuration.getInstance().getStartRow(loadFormat);
                    
                    //check parameters
                    if (!Configuration.getInstance().checkParametes(loadFormat)) {
                        throw new IOException("Invalid parameters in configuration file");
                    }
                    
                    CSVReader reader = new CSVReader(csvInput, separator, Charset.forName(charset), columnsCount, startRow);
                    
                    List<String[]> res = new ArrayList<String[]>();
                    res = reader.parseCSVData(response);
                    
                    DBConnection db = new DBConnection();
                    Connection conn = db.getConnetion();
                    //Connection conn = db.getDirectConnection();
                    try {
                        //a. call procedure before load
                        String beforeLoadProc = Configuration.getInstance().getBeforeLoad(loadFormat);
                        if (beforeLoadProc != null && beforeLoadProc.length() > 0) {
                            CallableStatement statementBL;
                            statementBL = conn.prepareCall("{ CALL " + beforeLoadProc + " }");
                            statementBL.execute();
                            statementBL.close();
                            statementBL = null;
                        }
                        
                        //b. call main load procedure
                        String proc = Configuration.getInstance().getLoad(loadFormat);
                        if (res.size() <= reader.CSV_MAX_LOAD_PACK) {
                            CallableStatement statement1;
                            statement1 = conn.prepareCall("{ CALL " + proc + " }");
                            statement1.setString(1, reader.getSQLStatement(res, filename, username));
                            statement1.execute();
                            conn.commit();
                            statement1.close();
                            statement1 = null;
                        } else if (res.size() > reader.CSV_MAX_LOAD_PACK) {
                            int steps = (int)Math.ceil(res.size()/reader.CSV_MAX_LOAD_PACK) + 1;
                            CallableStatement statement2 = null;
                            for (int a=0; a<steps;a++) {
                                statement2 = conn.prepareCall("{ CALL " + proc + " }");
                                statement2.setString(1, reader.getSQLStatement(res, filename, username, a));
                                statement2.execute();    
                            }
                            conn.commit();
                            statement2.close();
                            statement2 = null;
                        }
                        
                        //c. call procedure after load
                        String afterLoadProc = Configuration.getInstance().getAfterLoad(loadFormat);
                        if (afterLoadProc != null && afterLoadProc.length() > 0) {
                            CallableStatement statementAL = null;
                            statementAL = conn.prepareCall("{ CALL " + afterLoadProc + " }");
                            statementAL.execute();
                            statementAL.close();
                            statementAL = null;
                        }
                        
                        response.getWriter().print("{\"status\": \"0\", \"message\": \"" + "success" + "\", \"code\": \"" + "100" + "\"}");
                    } catch (SQLException e) {
                        conn.rollback();
                        
                        e.printStackTrace();
                        error.throwError(response, "008", e.getMessage());
                    }
                    reader = null;
                    res = null;
                    conn = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                error.throwError(response, "008", e.getMessage());
            }
        }
    }

    private String getUser(HttpServletRequest request, HttpServletResponse response, String biServerURL) throws IOException {
        String userName = AuthUtil.getAuthenticatedUsername(request, biServerURL);

        return userName;
    }

    private String testConnection() throws SQLException {
        return "String";
    }
}
