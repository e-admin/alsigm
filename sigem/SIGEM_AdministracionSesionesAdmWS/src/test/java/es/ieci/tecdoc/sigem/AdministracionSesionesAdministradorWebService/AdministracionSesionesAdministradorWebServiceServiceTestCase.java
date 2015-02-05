/**
 * AdministracionSesionesAdministradorWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService;

public class AdministracionSesionesAdministradorWebServiceServiceTestCase extends junit.framework.TestCase {
    public AdministracionSesionesAdministradorWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAdministracionSesionesAdministradorWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AdministracionSesionesAdministradorWebServiceNuevaSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        // value = binding.nuevaSesion(new java.lang.String(), 0);
        String usuario = "sergio";
        int tipoUsuario = 1;
        value = binding.nuevaSesion(usuario, tipoUsuario);
        // TBD - validate results
		assertNotNull("No se generó la sesión", value);
		if (value != null)
			System.out.println(value);
    }

    public void test2AdministracionSesionesAdministradorWebServiceNuevaSesionEntidad() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        // value = binding.nuevaSesionEntidad(new java.lang.String(), new java.lang.String());
        String key = "sergio_ND72C30008D011DE8000C2A0E3D5521E";
        String idEntidad = "002";
        value = binding.nuevaSesionEntidad(key, idEntidad);
        // TBD - validate results
        assertNotNull("No se genero la sesión", value);
        if(value != null) System.out.println(value);
    }

    public void test3AdministracionSesionesAdministradorWebServiceValidarSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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

    public void test4AdministracionSesionesAdministradorWebServiceValidarSesionEntidad() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        // value = binding.validarSesionEntidad(new java.lang.String(), new java.lang.String());
        String key = "sergio_ND72C30008D011DE8000C2A0E3D5521E_002";
        String idAplicacion = "1";
        value = binding.validarSesionEntidad(key, idAplicacion);
        // TBD - validate results
        assertNotNull("No se validó la sesión", Boolean.valueOf(value));
        System.out.println(Boolean.valueOf(value).toString());
    }

    public void test5AdministracionSesionesAdministradorWebServiceCaducarSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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

    public void test6AdministracionSesionesAdministradorWebServiceCaducarSesionEntidad() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        binding.caducarSesionEntidad(new java.lang.String());
        // TBD - validate results
    }

    public void test7AdministracionSesionesAdministradorWebServiceObtenerSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.Sesion value = null;
        value = binding.obtenerSesion(new java.lang.String());
        // TBD - validate results
    }

    public void test8AdministracionSesionesAdministradorWebServiceObtenerSesionEntidad() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.Sesion value = null;
        value = binding.obtenerSesionEntidad(new java.lang.String());
        // TBD - validate results
    }

    public void test9AdministracionSesionesAdministradorWebServiceModificarDatosSesion() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionSesionesAdministradorWebService.AdministracionSesionesAdministradorWebServiceServiceLocator().getAdministracionSesionesAdministradorWebService();
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
