
package oracle.bi.web.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "pageID", "sessionID" })
@XmlRootElement(name = "getCommonBodyHtml")
public class GetCommonBodyHtml {

    @XmlElement(required = true)
    protected String pageID;
    @XmlElement(required = true, nillable = true)
    protected String sessionID;

    /**
     * Gets the value of the pageID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPageID() {
        return pageID;
    }

    /**
     * Sets the value of the pageID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPageID(String value) {
        this.pageID = value;
    }

    /**
     * Gets the value of the sessionID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSessionID(String value) {
        this.sessionID = value;
    }

}
