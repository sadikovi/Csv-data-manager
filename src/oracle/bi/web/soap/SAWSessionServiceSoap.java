package oracle.bi.web.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 130224.1947.04102)

@WebService(wsdlLocation =
            "http://msk-02-orabits.tsretail.ru:9704/analytics/saw.dll/wsdl/v6",
            targetNamespace = "urn://oracle.bi.webservices/v6",
            name = "SAWSessionServiceSoap")
@XmlSeeAlso( { oracle.bi.web.soap.ObjectFactory.class })
public interface SAWSessionServiceSoap {
    @WebMethod(action = "#logon")
    @Action(input = "#logon",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/logonResponse")
    @ResponseWrapper(localName = "logonResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.LogonResult")
    @RequestWrapper(localName = "logon",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.Logon")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6",
               name = "sessionID")
    public String logon(@WebParam(targetNamespace =
                                  "urn://oracle.bi.webservices/v6",
                                  name = "name")
        String name,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "password")
        String password);

    @WebMethod(action = "#logonex")
    @Action(input = "#logonex",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/logonexResponse")
    @ResponseWrapper(localName = "logonexResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.LogonexResult")
    @RequestWrapper(localName = "logonex",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.Logonex")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6")
    public oracle.bi.web.soap.AuthResult logonex(@WebParam(targetNamespace =
                                                           "urn://oracle.bi.webservices/v6",
                                                           name = "name")
        String name,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "password")
        String password,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "sessionparams")
        oracle.bi.web.soap.SAWSessionParameters sessionparams);

    @WebMethod(action = "#logoff")
    @Action(input = "#logoff",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/logoffResponse")
    @ResponseWrapper(localName = "logoffResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.LogoffResult")
    @RequestWrapper(localName = "logoff",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.Logoff")
    public void logoff(@WebParam(targetNamespace =
                                 "urn://oracle.bi.webservices/v6",
                                 name = "sessionID")
        String sessionID);

    @WebMethod(action = "#keepAlive")
    @Action(input = "#keepAlive",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/keepAliveResponse")
    @ResponseWrapper(localName = "keepAliveResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.KeepAliveResult")
    @RequestWrapper(localName = "keepAlive",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.KeepAlive")
    public void keepAlive(@WebParam(targetNamespace =
                                    "urn://oracle.bi.webservices/v6",
                                    name = "sessionID")
        List<String> sessionID);

    @WebMethod(action = "#getCurUser")
    @Action(input = "#getCurUser",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/getCurUserResponse")
    @ResponseWrapper(localName = "getCurUserResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.GetCurUserResult")
    @RequestWrapper(localName = "getCurUser",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.GetCurUser")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6")
    public String getCurUser(@WebParam(targetNamespace =
                                       "urn://oracle.bi.webservices/v6",
                                       name = "sessionID")
        String sessionID);

    @WebMethod(action = "#getSessionEnvironment")
    @Action(input = "#getSessionEnvironment",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/getSessionEnvironmentResponse")
    @ResponseWrapper(localName = "getSessionEnvironmentResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className =
                     "oracle.bi.web.soap.GetSessionEnvironmentResult")
    @RequestWrapper(localName = "getSessionEnvironment",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.GetSessionEnvironment")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6")
    public oracle.bi.web.soap.SessionEnvironment getSessionEnvironment(@WebParam(targetNamespace =
                                                                                 "urn://oracle.bi.webservices/v6",
                                                                                 name =
                                                                                 "sessionID")
        String sessionID);

    @WebMethod(action = "#getSessionVariables")
    @Action(input = "#getSessionVariables",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/getSessionVariablesResponse")
    @ResponseWrapper(localName = "getSessionVariablesResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className =
                     "oracle.bi.web.soap.GetSessionVariablesResult")
    @RequestWrapper(localName = "getSessionVariables",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.GetSessionVariables")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6")
    public List<String> getSessionVariables(@WebParam(targetNamespace =
                                                      "urn://oracle.bi.webservices/v6",
                                                      name = "names")
        List<String> names,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "sessionID")
        String sessionID);

    @WebMethod(action = "#impersonate")
    @Action(input = "#impersonate",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/impersonateResponse")
    @ResponseWrapper(localName = "impersonateResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.ImpersonateResult")
    @RequestWrapper(localName = "impersonate",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.Impersonate")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6",
               name = "sessionID")
    public String impersonate(@WebParam(targetNamespace =
                                        "urn://oracle.bi.webservices/v6",
                                        name = "name")
        String name,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "password")
        String password,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "impersonateID")
        String impersonateID);

    @WebMethod(action = "#impersonateex")
    @Action(input = "#impersonateex",
            output = "urn://oracle.bi.webservices/v6/SAWSessionServiceSoap/impersonateexResponse")
    @ResponseWrapper(localName = "impersonateexResult",
                     targetNamespace = "urn://oracle.bi.webservices/v6",
                     className = "oracle.bi.web.soap.ImpersonateexResult")
    @RequestWrapper(localName = "impersonateex",
                    targetNamespace = "urn://oracle.bi.webservices/v6",
                    className = "oracle.bi.web.soap.Impersonateex")
    @WebResult(targetNamespace = "urn://oracle.bi.webservices/v6")
    public oracle.bi.web.soap.AuthResult impersonateex(@WebParam(targetNamespace =
                                                                 "urn://oracle.bi.webservices/v6",
                                                                 name = "name")
        String name,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "password")
        String password,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "impersonateID")
        String impersonateID,
        @WebParam(targetNamespace = "urn://oracle.bi.webservices/v6",
                  name = "sessionparams")
        oracle.bi.web.soap.SAWSessionParameters sessionparams);
}