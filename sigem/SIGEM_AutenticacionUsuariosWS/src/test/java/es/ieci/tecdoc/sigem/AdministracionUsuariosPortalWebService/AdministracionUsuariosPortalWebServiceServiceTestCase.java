/**
 * AdministracionUsuariosPortalWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService;

public class AdministracionUsuariosPortalWebServiceServiceTestCase extends junit.framework.TestCase {
    public AdministracionUsuariosPortalWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testAdministracionUsuariosPortalWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1AdministracionUsuariosPortalWebServiceCrearUsuario() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio value = null;
        value = binding.crearUsuario(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        // TBD - validate results
    }

    public void test2AdministracionUsuariosPortalWebServiceEliminarUsuario() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio value = null;
        value = binding.eliminarUsuario(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        // TBD - validate results
    }

    public void test3AdministracionUsuariosPortalWebServiceBuscarUsuarios() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.ListaUsuarios value = null;
        // value = binding.buscarUsuarios(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.CriterioBusquedaUsuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        CriterioBusquedaUsuario anUsuario = new CriterioBusquedaUsuario();
        anUsuario.setUser("sergio");
        Entidad anEntidad = new Entidad();
        anEntidad.setIdentificador("002");
        value = binding.buscarUsuarios(anUsuario, anEntidad);
        // TBD - validate results
        assertNotNull("No se encontró el usuario", value);
        if(value != null) System.out.println(value.getUsers().getItem(0).getName());
    }

    public void test4AdministracionUsuariosPortalWebServiceAutenticarUsuario() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario value = null;
        value = binding.autenticarUsuario(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        // TBD - validate results
    }

    public void test5AdministracionUsuariosPortalWebServiceObtenerUsuario() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario value = null;
        value = binding.obtenerUsuario(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        // TBD - validate results
    }

    public void test6AdministracionUsuariosPortalWebServiceActualizarUsuario() throws Exception {
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.AdministracionUsuariosPortalWebServiceServiceLocator().getAdministracionUsuariosPortalWebService();
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
        es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.RetornoServicio value = null;
        value = binding.actualizarUsuario(new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Usuario(), new es.ieci.tecdoc.sigem.AdministracionUsuariosPortalWebService.Entidad());
        // TBD - validate results
    }

}
