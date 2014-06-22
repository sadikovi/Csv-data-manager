package main.sitronics.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

/*this class defines configuration file in server directory*/

public class Configuration {
    String biServerURL = "";
    String biConfigFileName = "";
    String biConfigFilePath = "";
    List<ImportForm> content = new ArrayList<ImportForm>();
    
    private void setBIConfigFilePath(String path) {
        this.biConfigFilePath = path;    
    }
    
    private void setBIConfigFileName(String name) {
        if (name == null || name.length() < 0) {
            this.biConfigFileName = "csv_load_configuration";
        } else {
            this.biConfigFileName = name;
        }
    }
    
    private void setBIServerURL(String path) {
        try {
            DOMParser dom = new DOMParser(path);
            Document xml = dom.getDOM();
            this.biServerURL = dom.getBIServerURL(xml);
        } catch (Exception e) {
            e.printStackTrace();
            this.biServerURL = "";
        }
    }
    
    private List<ImportForm> getContentFromXML(String path) {
        List<ImportForm> importData = new ArrayList<ImportForm>();
        DOMParser dom =  new DOMParser(path);;
        Document xml = null;
        
        try {
            xml = dom.getDOM();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        List<String> ids = dom.getID(xml);
        for (int i=0;i<ids.size();i++) {
            //create form for every instance in file
            ImportForm form = new ImportForm();
            form.fill(dom, xml, ids.get(i));
            importData.add(form);
            form = null;
        }
        
        return importData;
    }
    
    private static Configuration curInstance = new Configuration();
    
    public static Configuration getInstance() {
        return curInstance;    
    }
    
    public void loadConfiguration(String xmlPath) {
        this.setBIConfigFileName("csv_load_configuration");
        this.setBIServerURL(xmlPath);
        this.setBIConfigFilePath(xmlPath);
        this.content = getContentFromXML(xmlPath);
    }
    
    public String checkIDByFilter(String filter) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).filterName == filter) {
                return this.content.get(i).id;
            }
        }
        
        return "";
    }
    
    public String getSeparator(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return this.content.get(i).separator;
            }
        }
        return "";
    }
    
    public int getColumnsCount(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return this.content.get(i).columnsCount;
            }
        }
        return -1;
    }
    
    public int getStartRow(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return this.content.get(i).startRow;
            }
        }
        return -1;
    }
    
    public String getBeforeLoad(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return (String)this.content.get(i).load.get("beforeLoad");
            }
        }
        return "";
    }
    
    public String getAfterLoad(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return (String)this.content.get(i).load.get("afterLoad");
            }
        }
        return "";
    }
    
    public String getLoad(String loadFormat) {
        for (int i=0; i<this.content.size(); i++) {
            if (this.content.get(i).id.equals(loadFormat)) {
                return (String)this.content.get(i).load.get("load");
            }
        }
        return "";
    }
    
    public Configuration() {
        super();
    }
    
    public Configuration(String path) {
        super();
        this.biConfigFilePath = path;
    }
    
    public boolean checkParametes(String import_id) {
        if (
            this.getColumnsCount(import_id) != -1 
            && this.getStartRow(import_id) != -1 
            && this.getSeparator(import_id).length() > 0 
            /*&& this.getBeforeLoad(import_id).length() > 0*/
            && this.getLoad(import_id).length() > 0
            /*&& this.getAfterLoad(import_id).length() > 0*/
        ) {
            return true;
        }
        
        return false;
    }
}
