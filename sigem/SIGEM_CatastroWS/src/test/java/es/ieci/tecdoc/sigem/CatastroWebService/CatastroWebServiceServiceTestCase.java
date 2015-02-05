/**
 * CatastroWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatastroWebService;

public class CatastroWebServiceServiceTestCase extends junit.framework.TestCase {
    public CatastroWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCatastroWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceServiceLocator().getCatastroWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CatastroWebServiceValidarReferenciaCatastral() throws Exception {
        es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceServiceLocator().getCatastroWebService();
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
        es.ieci.tecdoc.sigem.CatastroWebService.RetornoLogico value = null;
        value = binding.validarReferenciaCatastral(new es.ieci.tecdoc.sigem.CatastroWebService.RetornoCadena());
        // TBD - validate results
    }

    public void test2CatastroWebServiceConsultarCatastro() throws Exception {
        es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CatastroWebService.CatastroWebServiceServiceLocator().getCatastroWebService();
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
        es.ieci.tecdoc.sigem.CatastroWebService.Parcelas value = null;
        value = binding.consultarCatastro(new es.ieci.tecdoc.sigem.CatastroWebService.RetornoCadena());
        // TBD - validate results
    }

}
