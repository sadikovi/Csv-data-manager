package main.sitronics.data;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import oracle.bi.web.soap.SAWSessionService;

public class ServiceParams {
    public ServiceParams() {
        super();
    }
    
    private static String BI_SERVER_URL = "http://msk-02orabi2.tsretail.ru:9704";
    
    /**
     * Method returns server address for wsdl as URL type.
     * @param biServerURL
     * @return URL wsdlLocation
     */
    public static URL getWSDLLocationURL(String biServerURL)  {
        String wsdlLocation = null;
        if (biServerURL == null || biServerURL.length() <= 0) {
            wsdlLocation = BI_SERVER_URL + "/analytics/" + "saw.dll/wsdl/v6";
        } else {
            wsdlLocation = biServerURL + "/analytics/" + "saw.dll/wsdl/v6";
        }
        
        URL wsdlLocationURL = SAWSessionService.class.getResource(wsdlLocation);
        try {
            if (wsdlLocationURL == null) {
              URL baseUrl = new File(".").toURL();
              wsdlLocationURL = new URL(baseUrl, wsdlLocation);
            }
        } catch (MalformedURLException e) {
            //Failed to create wsdlLocationURL
            e.printStackTrace();
        }
        
        return wsdlLocationURL;
    }

    /**
     * Method returns QName for bi server.
     * @param service
     * @return
     */
    public static QName getQName(String service)  {
        /*try services 
         * MetadataService
         * SAWSessionService
         */
        QName qName = new QName("urn://oracle.bi.webservices/v6", service);
        
        return qName;
    }
}


    