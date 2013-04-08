/**
 * GeoLocalizacionWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.GeoLocalizacionWebService;

public class GeoLocalizacionWebServiceServiceTestCase extends junit.framework.TestCase {
    public GeoLocalizacionWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testGeoLocalizacionWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1GeoLocalizacionWebServiceVerPlanoPorCoordenadas() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano value = null;
        value = binding.verPlanoPorCoordenadas(new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoCoordenadas());
        // TBD - validate results
    }

    public void test2GeoLocalizacionWebServiceVerPlanoPorReferenciaCatastral() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano value = null;
        value = binding.verPlanoPorReferenciaCatastral(new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoReferenciaCatastral());
        // TBD - validate results
    }

    public void test3GeoLocalizacionWebServiceVerPlanoPorIdVia() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano value = null;
        value = binding.verPlanoPorIdVia(new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoVia());
        // TBD - validate results
    }

    public void test4GeoLocalizacionWebServiceVerPlanoPorIdNumeroPolicia() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.URLsPlano value = null;
        value = binding.verPlanoPorIdNumeroPolicia(new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.PeticionPlanoPortal());
        // TBD - validate results
    }

    public void test5GeoLocalizacionWebServiceVerPlanosPublicados() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Mapas value = null;
        value = binding.verPlanosPublicados(0);
        // TBD - validate results
    }

    public void test6GeoLocalizacionWebServiceValidarVia() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Vias value = null;
        value = binding.validarVia(new java.lang.String(), 0);
        // TBD - validate results
    }

    public void test7GeoLocalizacionWebServiceValidarPortal() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portales value = null;
        value = binding.validarPortal(0, new java.lang.String());
        // TBD - validate results
    }

    public void test8GeoLocalizacionWebServiceObtenerPortal() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Portal value = null;
        value = binding.obtenerPortal(0);
        // TBD - validate results
    }

    public void test9GeoLocalizacionWebServiceValidarDireccionPostal() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.RetornoLogico value = null;
        value = binding.validarDireccionPostal(new java.lang.String(), new java.lang.String(), new java.lang.String(), 0);
        // TBD - validate results
    }

    public void test10GeoLocalizacionWebServiceValidarDireccionPostalCompleta() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Via value = null;
        value = binding.validarDireccionPostalCompleta(new java.lang.String(), new java.lang.String(), new java.lang.String(), 0);
        // TBD - validate results
    }

    public void test11GeoLocalizacionWebServiceObtenerProvincias() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Provincias value = null;
        value = binding.obtenerProvincias();
        // TBD - validate results
    }

    public void test12GeoLocalizacionWebServiceObtenerMunicipios() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.Municipios value = null;
        value = binding.obtenerMunicipios(0);
        // TBD - validate results
    }

    public void test13GeoLocalizacionWebServiceObtenerTiposDeVia() throws Exception {
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.GeoLocalizacionWebService.GeoLocalizacionWebServiceServiceLocator().getGeoLocalizacionWebService();
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
        es.ieci.tecdoc.sigem.GeoLocalizacionWebService.TiposVia value = null;
        value = binding.obtenerTiposDeVia();
        // TBD - validate results
    }

}
