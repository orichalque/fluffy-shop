
package com.cdyne.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ReturnIndicator complex type.
 * 
 * <p>Le fragment de schema suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="ReturnIndicator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponseCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LastMailServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GoodEmail" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnIndicator", propOrder = {
    "responseText",
    "responseCode",
    "lastMailServer",
    "goodEmail"
})
public class ReturnIndicator {

    @XmlElement(name = "ResponseText")
    protected String responseText;
    @XmlElement(name = "ResponseCode")
    protected int responseCode;
    @XmlElement(name = "LastMailServer")
    protected String lastMailServer;
    @XmlElement(name = "GoodEmail")
    protected boolean goodEmail;

    /**
     * Obtient la valeur de la propriete responseText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * Definit la valeur de la propriete responseText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseText(String value) {
        this.responseText = value;
    }

    /**
     * Obtient la valeur de la propriete responseCode.
     * 
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Definit la valeur de la propriete responseCode.
     * 
     */
    public void setResponseCode(int value) {
        this.responseCode = value;
    }

    /**
     * Obtient la valeur de la propriete lastMailServer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastMailServer() {
        return lastMailServer;
    }

    /**
     * Definit la valeur de la propriete lastMailServer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastMailServer(String value) {
        this.lastMailServer = value;
    }

    /**
     * Obtient la valeur de la propriete goodEmail.
     * 
     */
    public boolean isGoodEmail() {
        return goodEmail;
    }

    /**
     * Definit la valeur de la propriete goodEmail.
     * 
     */
    public void setGoodEmail(boolean value) {
        this.goodEmail = value;
    }

}
