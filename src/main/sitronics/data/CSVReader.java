package main.sitronics.data;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class CSVReader {
    String separator = ";";
    private static String rowSeparator = "###";
    private static String delimeter = "\\A";
    private Charset charset = Charset.forName("ISO-8859-1");
    private InputStream csvFile = null;
    private int startRow = 0;
    private int columnsCount = 0;

    private boolean isInitialized = false;
    static int CSV_MAX_LOAD_PACK = 10000;

    //initialize methods for CSVReader
    public CSVReader(InputStream file, String separator, Charset charset,
                     int columnsCount, int startRow) {
        super();
        this.csvFile = file;
        this.separator = separator;
        this.charset = charset;
        this.columnsCount = columnsCount;
        this.startRow = startRow;
        isInitialized = true;
    }

    public CSVReader(InputStream file, String separator, String charset,
                     int columnsCount) {
        this.csvFile = file;
        this.separator = separator;
        this.charset = Charset.forName(charset);
        this.columnsCount = columnsCount;
        isInitialized = true;
    }

    public CSVReader(InputStream file, String separator, int columnsCount) {
        this.csvFile = file;
        this.separator = separator;
        this.columnsCount = columnsCount;
        isInitialized = true;
    }

    public CSVReader(InputStream file, int columnsCount) {
        this.csvFile = file;
        this.columnsCount = columnsCount;
        isInitialized = true;
    }

    public CSVReader(InputStream file, String separator) {
        this.csvFile = file;
        this.separator = separator;
        isInitialized = true;
    }

    //check consistency of CSVReader
    private String isCSVReaderConsistent() {
        if (this.csvFile == null) {
            return ("CSV file is empty");
        } else if (this.separator == null || this.separator.equals("")) {
            return ("CSV separator is empty");
        } else if (this.charset == null) {
            return ("Charset for CSV is empty");
        } else if (this.columnsCount <= 0) {
            return ("Invalid columns number");
        } else if (this.startRow < 0) {
            return ("Invalid start row");
        } else {
            return "success";
        }
    }

    //parse method for CSVReader
    public List<String[]> parseCSVData(HttpServletResponse response) throws ServletException,
                                                                            IOException {
        //here we suggest that the end of the line is equal of "\r\n", so we replace it with rowSeparator
        List<String[]> res = new ArrayList<String[]>();
        String str = new Scanner(this.csvFile).useDelimiter(delimeter).next().replace("\r\n", rowSeparator);
        if (this.columnsCount <= 0) {
            this.columnsCount = str.split(rowSeparator)[0].split(separator).length;
        }
        if (isCSVReaderConsistent().equals("success")) {
            String[] rows = str.split(rowSeparator);
            for (int i = this.startRow; i < rows.length; i++) {
                String[] elements = new String[this.columnsCount + 3];
                
                for (int j = 0; j < rows[i].split(separator).length; j++) {
                    elements[j] = rows[i].split(separator)[j];
                }
                res.add(elements);
            }
        } else {
            Error e = new Error();
            e.throwError(response, "003", isCSVReaderConsistent());
        }

        return res;
    }

    public String getSQLStatement(List<String[]> res, String filename, String username) {
        String bufRes = "WITH OBIEECSVIMPORT1 AS (";  
        
        for (String[] item : res) {
            String row = "SELECT ";
            
            for (int k = 0; k < item.length; k++) {
                String bufItem = item[k];
                
                if (bufItem == null) {
                    bufItem = item[k];
                } else {
                    bufItem = "'" + item[k] + "'";
                }
                if (k < item.length - 3) {
                    row += bufItem + " AS " + "CSV" + k + ", ";
                } else if (k == item.length - 3) {
                    row += "'" + filename + "'" + " AS " + "CSV" + k + ", ";
                } else if (k == item.length - 2) {
                    row += "'" + username + "'" + " AS " + "CSV" + k + ", ";
                } else if (k == item.length - 1) {
                    row += "SYSDATE" + " AS " + "CSV" + k;
                }
                
                bufItem = null;
            }
            row += " FROM DUAL";
            if (res.indexOf(item) < res.size() - 1) {
                bufRes += row + " UNION ALL ";
            } else {
                bufRes += row + ") SELECT * FROM OBIEECSVIMPORT1";
            }
            row = null;
        }
        return bufRes;
    }
    
    public String getSQLStatement(List<String[]> res, String filename, String username, int step) {
        int pFrom = step * CSV_MAX_LOAD_PACK;
        int pTo = (step + 1) * CSV_MAX_LOAD_PACK;
        
        if (pTo > res.size()) {
            pTo = res.size();
        }
        if (res.get(pFrom) != null && res.get(pFrom).length >= 0 ) {
            String bufRes = "WITH OBIEECSVIMPORT1 AS (";  
            
            for (int p=pFrom; p<pTo; p++) {
                String[] item = res.get(p);
                String row = "SELECT ";
                
                for (int k = 0; k < item.length; k++) {
                    String bufItem = item[k];
                    
                    if (bufItem == null) {
                        bufItem = item[k];
                    } else {
                        bufItem = "'" + item[k] + "'";
                    }
                    if (k < item.length - 3) {
                        row += bufItem + " AS " + "CSV" + k + ", ";
                    } else if (k == item.length - 3) {
                        row += "'" + filename + "'" + " AS " + "CSV" + k + ", ";
                    } else if (k == item.length - 2) {
                        row += "'" + username + "'" + " AS " + "CSV" + k + ", ";
                    } else if (k == item.length - 1) {
                        row += "SYSDATE" + " AS " + "CSV" + k;
                    }
                    
                    bufItem = null;
                }
                row += " FROM DUAL";
                if (res.indexOf(item) < pTo - 1) {
                    bufRes += row + " UNION ALL ";
                } else {
                    bufRes += row + ") SELECT * FROM OBIEECSVIMPORT1";
                }
                
                row = null;
            }
            return bufRes;
        }
        return "";
    }
    
    /*
     * @depricated
     */
    /*
    public String getSQLCSVFromRequest(HttpServletResponse response, String filename, String username) 
                                                                    throws ServletException, IOException {
        List<String[]> res = new ArrayList<String[]>();
        res = parseCSVData(response);
        return getSQLStatement(res, filename, username);
    }
    */
}
