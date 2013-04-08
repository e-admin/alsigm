/**
 * AntivirusWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AntivirusWebService;

public class AntivirusWebServiceServiceTestCase extends junit.framework.TestCase {
    public AntivirusWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAntivirusWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceServiceLocator().getAntivirusWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AntivirusWebServiceComprobarFicheroContenido() throws Exception {
        es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceServiceLocator().getAntivirusWebService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        es.ieci.tecdoc.sigem.AntivirusWebService.RetornoBooleano value = null;
        value = binding.comprobarFicheroContenido(new es.ieci.tecdoc.sigem.AntivirusWebService.RetornoArrayByte(), true);
        // TBD - validate results
    }

    public void test2AntivirusWebServiceComprobarFicheroRuta() throws Exception {
        es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AntivirusWebService.AntivirusWebServiceServiceLocator().getAntivirusWebService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        es.ieci.tecdoc.sigem.AntivirusWebService.RetornoBooleano value = null;
        value = binding.comprobarFicheroRuta(new java.lang.String(), true);
        // TBD - validate results
    }

}
