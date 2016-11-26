
package com.cdyne.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ReturnIndicator complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété responseText.
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
     * Définit la valeur de la propriété responseText.
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
     * Obtient la valeur de la propriété responseCode.
     * 
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Définit la valeur de la propriété responseCode.
     * 
     */
    public void setResponseCode(int value) {
        this.responseCode = value;
    }

    /**
     * Obtient la valeur de la propriété lastMailServer.
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
     * Définit la valeur de la propriété lastMailServer.
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
     * Obtient la valeur de la propriété goodEmail.
     * 
     */
    public boolean isGoodEmail() {
        return goodEmail;
    }

    /**
     * Définit la valeur de la propriété goodEmail.
     * 
     */
    public void setGoodEmail(boolean value) {
        this.goodEmail = value;
    }

}
