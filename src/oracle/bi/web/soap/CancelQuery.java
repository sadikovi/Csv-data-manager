
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
 *         &lt;element name="queryID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = { "queryID", "sessionID" })
@XmlRootElement(name = "cancelQuery")
public class CancelQuery {

    @XmlElement(required = true)
    protected String queryID;
    @XmlElement(required = true, nillable = true)
    protected String sessionID;

    /**
     * Gets the value of the queryID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getQueryID() {
        return queryID;
    }

    /**
     * Sets the value of the queryID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setQueryID(String value) {
        this.queryID = value;
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
