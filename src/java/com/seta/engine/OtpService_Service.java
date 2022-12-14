
package com.seta.engine;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OtpService", targetNamespace = "http://engine.seta.com/", wsdlLocation = "http://172.31.224.28:8230/OtpWs/OtpService?wsdl")
public class OtpService_Service
    extends Service
{

    private final static URL OTPSERVICE_WSDL_LOCATION;
    private final static WebServiceException OTPSERVICE_EXCEPTION;
    private final static QName OTPSERVICE_QNAME = new QName("http://engine.seta.com/", "OtpService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://172.31.224.28:8230/OtpWs/OtpService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        OTPSERVICE_WSDL_LOCATION = url;
        OTPSERVICE_EXCEPTION = e;
    }

    public OtpService_Service() {
        super(__getWsdlLocation(), OTPSERVICE_QNAME);
    }

    public OtpService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), OTPSERVICE_QNAME, features);
    }

    public OtpService_Service(URL wsdlLocation) {
        super(wsdlLocation, OTPSERVICE_QNAME);
    }

    public OtpService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, OTPSERVICE_QNAME, features);
    }

    public OtpService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OtpService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OtpService
     */
    @WebEndpoint(name = "OtpServicePort")
    public OtpService getOtpServicePort() {
        return super.getPort(new QName("http://engine.seta.com/", "OtpServicePort"), OtpService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OtpService
     */
    @WebEndpoint(name = "OtpServicePort")
    public OtpService getOtpServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://engine.seta.com/", "OtpServicePort"), OtpService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (OTPSERVICE_EXCEPTION!= null) {
            throw OTPSERVICE_EXCEPTION;
        }
        return OTPSERVICE_WSDL_LOCATION;
    }

}
