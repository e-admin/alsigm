/**
 * ConsultaExpedientesWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.ConsultaExpedientesWebService;

public class ConsultaExpedientesWebServiceServiceTestCase extends junit.framework.TestCase {
    public ConsultaExpedientesWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testConsultaExpedientesWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1ConsultaExpedientesWebServiceConsultarExpedientesNIF() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes value = null;
        value = binding.consultarExpedientesNIF(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test2ConsultaExpedientesWebServiceConsultarExpedientes() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes value = null;
        value = binding.consultarExpedientes(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test3ConsultaExpedientesWebServiceObtenerURLAportacionExpedientes() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url value = null;
        value = binding.obtenerURLAportacionExpedientes();
        // TBD - validate results
    }

    public void test4ConsultaExpedientesWebServiceObtenerURLNotificacionExpedientes() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url value = null;
        value = binding.obtenerURLNotificacionExpedientes();
        // TBD - validate results
    }

    public void test5ConsultaExpedientesWebServiceObtenerURLPagoTasas() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Url value = null;
        value = binding.obtenerURLPagoTasas();
        // TBD - validate results
    }

    public void test6ConsultaExpedientesWebServiceObtenerDetalle() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente value = null;
        value = binding.obtenerDetalle(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test7ConsultaExpedientesWebServiceObtenerHistoricoExpediente() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente value = null;
        value = binding.obtenerHistoricoExpediente(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test8ConsultaExpedientesWebServiceObtenerHitoEstado() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente value = null;
        value = binding.obtenerHitoEstado(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test9ConsultaExpedientesWebServiceObtenerFicherosHito() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito value = null;
        value = binding.obtenerFicherosHito(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test10ConsultaExpedientesWebServiceObtenerFicherosHitos() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHitos value = null;
        value = binding.obtenerFicherosHitos(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitosExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test11ConsultaExpedientesWebServiceNuevoExpediente() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.nuevoExpediente(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test12ConsultaExpedientesWebServiceEliminarExpediente() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.eliminarExpediente(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test13ConsultaExpedientesWebServiceNuevoInteresado() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.nuevoInteresado(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test14ConsultaExpedientesWebServiceEliminarInteresado() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.eliminarInteresado(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Interesado(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test15ConsultaExpedientesWebServiceEliminarInteresadoExpediente() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.eliminarInteresadoExpediente(new java.lang.String(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test16ConsultaExpedientesWebServiceEstablecerHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.establecerHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Historico(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test17ConsultaExpedientesWebServiceEliminarHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.eliminarHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test18ConsultaExpedientesWebServiceNuevoHitoHistorico() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.nuevoHitoHistorico(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test19ConsultaExpedientesWebServiceEliminarHitoHistorico() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.eliminarHitoHistorico(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test20ConsultaExpedientesWebServiceCargarDocumento() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento value = null;
        value = binding.cargarDocumento(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test21ConsultaExpedientesWebServiceRecogerDocumento() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento value = null;
        value = binding.recogerDocumento(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.InfoDocumento(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test22ConsultaExpedientesWebServiceBusquedaExpedientes() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expedientes value = null;
        value = binding.busquedaExpedientes(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.CriterioBusquedaExpedientes(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test23ConsultaExpedientesWebServiceAnexarFicherosHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.anexarFicherosHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.FicherosHito(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test24ConsultaExpedientesWebServiceRecogerNotificaciones() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.NotificacionesPendientes value = null;
        value = binding.recogerNotificaciones(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test25ConsultaExpedientesWebServiceAltaSolicitudSubsanacion() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.altaSolicitudSubsanacion(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanacion(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test26ConsultaExpedientesWebServiceObtenerSubsanacionesHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones value = null;
        value = binding.obtenerSubsanacionesHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test27ConsultaExpedientesWebServiceObtenerSubsanacionesHito() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Subsanaciones value = null;
        value = binding.obtenerSubsanacionesHito(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test28ConsultaExpedientesWebServiceAltaSolicitudPago() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.altaSolicitudPago(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pago(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test29ConsultaExpedientesWebServiceObtenerPagosHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos value = null;
        value = binding.obtenerPagosHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test30ConsultaExpedientesWebServiceObtenerPagosHito() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Pagos value = null;
        value = binding.obtenerPagosHito(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test31ConsultaExpedientesWebServiceAltaNotificacion() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoServicio value = null;
        value = binding.altaNotificacion(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificacion(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test32ConsultaExpedientesWebServiceObtenerNotificionesHitoActual() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones value = null;
        value = binding.obtenerNotificionesHitoActual(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test33ConsultaExpedientesWebServiceObtenerNotificionesHito() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Notificaciones value = null;
        value = binding.obtenerNotificionesHito(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.HitoExpediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test34ConsultaExpedientesWebServiceExistenNotificaciones() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico value = null;
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad entidad = new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad();
        entidad.setIdentificador("001"); entidad.setNombre("Entidad 001");
        value = binding.existenNotificaciones(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), entidad);
        // TBD - validate results
    }

    public void test35ConsultaExpedientesWebServiceExistenPagos() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico value = null;
        value = binding.existenPagos(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test36ConsultaExpedientesWebServiceExistenSubsanaciones() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico value = null;
        value = binding.existenSubsanaciones(new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Expediente(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test37ConsultaExpedientesWebServiceActualizarEstadoLocalGIS() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico value = null;
        value = binding.actualizarEstadoLocalGIS(new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

    public void test38ConsultaExpedientesWebServicePublicarExpedienteLocalGIS() throws Exception {
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.ConsultaExpedientesWebServiceServiceLocator().getConsultaExpedientesWebService();
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
        es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.RetornoLogico value = null;
        value = binding.publicarExpedienteLocalGIS(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.ConsultaExpedientesWebService.Entidad());
        // TBD - validate results
    }

}
