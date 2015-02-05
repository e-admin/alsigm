/**
 * AdministracionSesionesBackOfficeWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService;

public class AdministracionSesionesBackOfficeWebServiceServiceTestCase extends junit.framework.TestCase {
    public AdministracionSesionesBackOfficeWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAdministracionSesionesBackOfficeWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AdministracionSesionesBackOfficeWebServiceNuevaSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
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
        java.lang.String value = null;
        // value = binding.nuevaSesion(new java.lang.String(), new java.lang.String());
        String usuario = "administrador";
        String idEntidad = "002";
        value = binding.nuevaSesion(usuario, idEntidad);
        // TBD - validate results
        assertNull("No se creó la sesión", value);
        if(value != null) System.out.println(value);
    }

    public void test2AdministracionSesionesBackOfficeWebServiceValidarSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
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
        boolean value = false;
        value = binding.validarSesion(new java.lang.String());
        // TBD - validate results
    }

    public void test3AdministracionSesionesBackOfficeWebServiceCaducarSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
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
        binding.caducarSesion(new java.lang.String());
        // TBD - validate results
    }

    public void test4AdministracionSesionesBackOfficeWebServiceObtenerSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
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
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.Sesion value = null;
        value = binding.obtenerSesion(new java.lang.String());
        // TBD - validate results
    }

    public void test5AdministracionSesionesBackOfficeWebServiceModificarDatosSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesBackOfficeWebService.AdministracionSesionesBackOfficeWebServiceServiceLocator().getAdministracionSesionesBackOfficeWebService();
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
        boolean value = false;
        value = binding.modificarDatosSesion(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

}
