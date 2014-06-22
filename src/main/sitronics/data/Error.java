package main.sitronics.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;


public class Error {
    public String code;
    public String message;

    public Error() {
        super();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * @param response
     * @param cd
     * @param msg
     * @throws ServletException
     * @throws IOException
     */
    public void throwError(HttpServletResponse response, String cd,
                           String msg) throws ServletException, IOException {
        this.code = cd;
        this.message = msg;
        response.getWriter().print("{\"status\": \"-1\", \"message\": \"" +
                                   this.message + "\", \"code\": \"" +
                                   this.code + "\"}");
        response.setStatus(400);

        //throw new IOException("Bad configuration");
    }

}
