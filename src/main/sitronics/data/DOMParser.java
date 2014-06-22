package main.sitronics.data;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class DOMParser {
    /*
     * variables that relate to tags used in xml template
     * if you change the template you should replace them with new values
     * 
    */
    //certain bi server url
    private static final String BI_SERVER_URL_NODE = "biServerUrl";
    //import tag
    private static final String BI_IMPORT_NODE = "import";
    //load tag - which indicates procedure have to be initialized
    private static final String BI_LOAD_NODE = "load";
    //charset tag
    private static final String BI_CHARSET_NODE = "charset";
    
    /*
     * variables that represent attributes for "import" tag
     * if you change the attributes names you should make changes here below
     * 
    */
    //filter name
    private static final String BI_FILTERNAME_ATTR = "filterName";
    //id - must be unique
    private static final String BI_ID_ATTR = "id";
    //roles which allow particular users to run template
    private static final String BI_ROLES_ATTR = "roles";
    //columns count of template
    private static final String BI_COLUMNSCOUNT_ATTR = "columnsCount";
    //start row for template it will be downloaded with
    private static final String BI_STARTROW_ATTR = "startRow";
    //separator for values in a row
    private static final String BI_SEPARATOR_ATTR = "separator";
    
    /*
     * variables that represent attributes for "load" tag
     * if you change the attributes names make sure you made changes here below
     */
    //before load procedure
    private static final String BI_BEFORE_LOAD_ATTR = "beforeload";
    //after load procedure
    private static final String BI_AFTER_LOAD_ATTR = "afterload";
    
    
    String XMLPath = "";


    public DOMParser() {
        super();
    }

    public DOMParser(String path) {
        super();
        this.XMLPath = path;
    }

    public void setPath(String path) {
        this.XMLPath = path;
    }

    public String getPath() {
        return this.XMLPath;
    }

    /**
     * Method for returning DOM structure of XML downloaded.
     * DOM is represented as a Document instance.
     * @return Document dom
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Document getDOM() throws FileNotFoundException, IOException {
        //get xml from ref and transorm it into xmlstring
        InputStream is = new FileInputStream(this.XMLPath);
        Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        String xmlstring = s.hasNext() ? s.next() : "";
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document domTree = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            domTree = db.parse(new ByteArrayInputStream(xmlstring.getBytes()));
        } catch (ParserConfigurationException pcE) {
            pcE.printStackTrace();
        } catch (SAXException saxE) {
            saxE.printStackTrace();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        is.close();
        return domTree;
    }

    /**
     * Method returns String which contains current bi server url.
     * @param dom
     * @return String url
     */
    public String getBIServerURL(Document dom) {
        Element root = dom.getDocumentElement();
        String url = root.getElementsByTagName(BI_SERVER_URL_NODE).item(0).getTextContent();
        if (url.equals("") || url == null) {
            return "";
        } else {
            return url;
        }
    }

    /**
     * Out method returns record id for paticular filter name,
     * (actually it's better if filter name is unique also as an id).
     * If there is no matches then null string will be returned.
     * @param dom
     * @param filterName
     * @return String id
     */
    public String getIDByFilterName(Document dom, String filterName) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Node element = null;
        Attr filter = null;
        Attr id = null;

        for (int i = 0; i < elements.getLength(); i++) {
            element = elements.item(i);
            filter = (Attr)element.getAttributes().getNamedItem(BI_FILTERNAME_ATTR);
            id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (filter.getValue().equals(filterName)) {
                return id.getValue();
            }
        }

        return "";
    }
    
    
    public List<String> getID(Document dom) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        
        List<String> arr = new ArrayList<String>();
        for (int i=0; i<elements.getLength(); i++) {
            Attr id = (Attr)elements.item(i).getAttributes().getNamedItem(BI_ID_ATTR);
            arr.add(id.getValue());
            id = null;
        }

        return arr;
    }

    /**
     * In method returns columns count for particular record id.
     * If there is not matches then "-1" value will be returned.
     * @param dom
     * @param id
     * @return Integer value
     */
    public int getColumnsCount(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                try {
                    return Integer.parseInt(((Attr)element.getAttributes().getNamedItem(BI_COLUMNSCOUNT_ATTR)).getValue());
                } catch (Exception e) {
                    return -1;
                }
            }
        }

        return -1;
    }


    /**
     * In method returns start row which can be used as the start point for downloading a template.
     * If there is no matches then "-1" value will be returned.
     * @param dom
     * @param id
     * @return Integer value
     */
    public int getStartRow(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                try {
                    return Integer.parseInt(((Attr)element.getAttributes().getNamedItem(BI_STARTROW_ATTR)).getValue());
                } catch (Exception e) {
                    return -1;
                }
            }
        }

        return -1;
    }

    /**
     * In method returns string which represents the separator.
     * Separator distingushes values in a row.
     * If there is no matches then the null string will be returned.
     * @param dom
     * @param id
     * @return String separator
     */
    public String getSeparator(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                try {
                    return ((Attr)element.getAttributes().getNamedItem(BI_SEPARATOR_ATTR)).getValue();
                } catch (Exception e) {
                    return "";
                }

            }
        }

        return "";
    }

    /**
     * In method returns charset for csv file, notice that it charset will be returned as string.
     * To use Charset instance you need to use "Charset.forName(String)".
     * If there is no matches then null string will be returned.
     * @param dom
     * @param id
     * @return String charset
     */
    public String getCharsetString(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                for (int j=0;j<element.getChildNodes().getLength();j++) {
                    if (element.getChildNodes().item(j).getNodeName().equals(BI_CHARSET_NODE)) {
                        try {
                            return element.getChildNodes().item(j).getTextContent();
                        } catch (Exception e) {
                            return "";
                        }
                    }
                }
            }
        }

        return "";
    }

    /**
     * In method returns name for load procedure that's going to use for download.
     * If there is no matches then null string will be returned.
     * @param dom
     * @param id
     * @return String procedure
     */
    public String getLoadProcedure(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                for (int j=0;j<element.getChildNodes().getLength();j++) {
                    if (element.getChildNodes().item(j).getNodeName().equals(BI_LOAD_NODE)) {
                        try {
                            return element.getChildNodes().item(j).getTextContent();
                        } catch (Exception e) {
                            return "";
                        }
                    }
                }
            }
        }

        return "";
    }

    /**
     * In method returns name for load procedure that will be run before main load.
     * If there is no matches then null string will be returned.
     * @param dom
     * @param id
     * @return String procedure before load
     */
    public String getBeforeLoadProcedure(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                for (int j=0;j<element.getChildNodes().getLength();j++) {
                    try {
                        if (element.getChildNodes().item(j).getNodeName().equals(BI_LOAD_NODE)) {
                            return ((Attr)element.getChildNodes().item(j).getAttributes().getNamedItem(BI_BEFORE_LOAD_ATTR)).getValue();
                        }
                    } catch (Exception e) {
                        return "";
                    }
                }
            }
        }
        
        return "";
    }

    /**
     * In method returns name for load procedure that will be run after main load.
     * If there is no matches then null string will be returned.
     * @param dom
     * @param id
     * @return String procedure after load
     */
    public String getAfterLoadProcedure(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                for (int j=0;j<element.getChildNodes().getLength();j++) {
                    try {
                        if (element.getChildNodes().item(j).getNodeName().equals(BI_LOAD_NODE)) {
                            return ((Attr)element.getChildNodes().item(j).getAttributes().getNamedItem(BI_AFTER_LOAD_ATTR)).getValue();
                        }
                    } catch (Exception e) {
                        return "";
                    }
                }
            }
        }
        
        return "";
    }
    
    
    public List<String> getRolesById(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        List<String> roleList = new ArrayList<String>();
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                for ( String role : ((Attr)element.getAttributes().getNamedItem(BI_ROLES_ATTR)).getValue().split(";")) {
                    roleList.add(role);
                }
            }
        }

        return roleList;
    }
    
    
    public String getFilterNameById(Document dom, String id) {
        Element root = dom.getDocumentElement();
        NodeList elements = root.getElementsByTagName(BI_IMPORT_NODE);
        Attr attr_id = null;
        Node element = null;
        
        for (int i=0;i<elements.getLength();i++) {
            element = elements.item(i);
            attr_id = (Attr)element.getAttributes().getNamedItem(BI_ID_ATTR);
            if (attr_id.getValue().equals(id)) {
                return ((Attr)element.getAttributes().getNamedItem(BI_FILTERNAME_ATTR)).getValue();
            }
        }

        return "";
    }
    
    //get all available filter names for user with a particular roles list

    /**
     * Out method returns filters list for particular user role.
     * Notice that if user's got several roles for different templates they all will be returned.
     * @param dom
     * @param role
     * @return List String filters
     */
    public List<String> getFilterList(Document dom, String role) {
        String[] roles = null;
        Attr importRoles = null;
        Attr filterName = null;
        List<String> filters = new ArrayList<String>();
        
        Element root = dom.getDocumentElement();
        NodeList imports = root.getElementsByTagName(BI_IMPORT_NODE);
        Node elem = null;
        for (int i = 0; i < imports.getLength(); i++) {
            elem = imports.item(i);
            importRoles = (Attr)elem.getAttributes().getNamedItem(BI_ROLES_ATTR);
            filterName = (Attr)elem.getAttributes().getNamedItem(BI_FILTERNAME_ATTR);
            roles = importRoles.getValue().split(";");
            for (String temp : roles) {
                if (temp.equals(role)) {
                    filters.add(filterName.getValue());
                }
            }
        }

        return filters;
    }
    
}
