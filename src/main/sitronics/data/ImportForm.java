package main.sitronics.data;

import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

public class ImportForm {
    /*this is representation for each import form in configuration file*/
    String id = null;
    String filterName = null;
    List<String> roles = null;
    int columnsCount = 0;
    int startRow = 0;
    String separator = null;
    HashMap load = new HashMap();
    String charset = null;

    private void setLoadKeys() {
        load.put("afterLoad", "");
        load.put("load", "");
        load.put("beforeLoad", "");
    }
    
    public ImportForm() {
        super();
    }
    
    public void fill(DOMParser dom, Document xml, String csvId) {
        id = csvId;
        filterName = dom.getFilterNameById(xml, csvId);
        roles = dom.getRolesById(xml, csvId);
        columnsCount = dom.getColumnsCount(xml, csvId);
        startRow = dom.getStartRow(xml, csvId);
        separator = dom.getSeparator(xml, csvId);
        load.put("afterLoad", dom.getAfterLoadProcedure(xml, csvId));
        load.put("load", dom.getLoadProcedure(xml, csvId));
        load.put("beforeLoad", dom.getBeforeLoadProcedure(xml, csvId));
    }
}
