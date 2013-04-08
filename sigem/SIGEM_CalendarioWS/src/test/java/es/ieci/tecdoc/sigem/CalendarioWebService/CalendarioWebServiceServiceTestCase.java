 /**
 * CalendarioWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CalendarioWebService;

public class CalendarioWebServiceServiceTestCase extends junit.framework.TestCase {
    public CalendarioWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testCalendarioWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebServiceServiceLocator().getCalendarioWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1CatalogoTramitesWebServiceQuery() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CalendarioWebServiceServiceLocator().getCalendarioWebService();
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
        //es.ieci.tecdoc.sigem.CalendarioWebService.Tramites value = null;
        //value = binding.query(new es.ieci.tecdoc.sigem.CalendarioWebService.TramiteConsulta(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    /*public void test2CatalogoTramitesWebServiceGetDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Documento value = null;
        value = binding.getDocument(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test3CatalogoTramitesWebServiceGetDocuments() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Documentos value = null;
        value = binding.getDocuments(new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test4CatalogoTramitesWebServiceUpdateDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateDocument(new es.ieci.tecdoc.sigem.CalendarioWebService.Documento(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test5CatalogoTramitesWebServiceAddProcedure() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addProcedure(new es.ieci.tecdoc.sigem.CalendarioWebService.Tramite(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test6CatalogoTramitesWebServiceGetProcedure() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Tramite value = null;
        value = binding.getProcedure(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.RetornoLogico(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test7CatalogoTramitesWebServiceDeleteProcedure() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteProcedure(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test8CatalogoTramitesWebServiceUpdateProcedure() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateProcedure(new es.ieci.tecdoc.sigem.CalendarioWebService.Tramite(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test9CatalogoTramitesWebServiceIsDocumentReferenced() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoLogico value = null;
        value = binding.isDocumentReferenced(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test10CatalogoTramitesWebServiceAddProcedureDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addProcedureDocument(new es.ieci.tecdoc.sigem.CalendarioWebService.DocumentoTramite(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test11CatalogoTramitesWebServiceDeleteProcedureDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteProcedureDocument(new es.ieci.tecdoc.sigem.CalendarioWebService.DocumentoTramite(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test12CatalogoTramitesWebServiceAddDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addDocument(new es.ieci.tecdoc.sigem.CalendarioWebService.Documento(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test13CatalogoTramitesWebServiceDeleteDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteDocument(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test14CatalogoTramitesWebServiceGetProcedureDocuments() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Documentos value = null;
        value = binding.getProcedureDocuments(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test15CatalogoTramitesWebServiceGetProcedureDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.DocumentoTramite value = null;
        value = binding.getProcedureDocument(new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test16CatalogoTramitesWebServiceGetProcedureDocumentByCode() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.DocumentoTramite value = null;
        value = binding.getProcedureDocumentByCode(new java.lang.String(), new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test17CatalogoTramitesWebServiceUpdateProcedureDocument() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateProcedureDocument(new es.ieci.tecdoc.sigem.CalendarioWebService.DocumentoTramite(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test18CatalogoTramitesWebServiceGetProcedures() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Tramites value = null;
        value = binding.getProcedures(new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test19CatalogoTramitesWebServiceGetDocumentfromCode() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Documento value = null;
        value = binding.getDocumentfromCode(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test20CatalogoTramitesWebServiceGetAddressee() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.OrganoDestinatario value = null;
        value = binding.getAddressee(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test21CatalogoTramitesWebServiceAddAddressee() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addAddressee(new es.ieci.tecdoc.sigem.CalendarioWebService.OrganoDestinatario(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test22CatalogoTramitesWebServiceDeleteAddressee() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteAddressee(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test23CatalogoTramitesWebServiceUpdateAddressee() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateAddressee(new es.ieci.tecdoc.sigem.CalendarioWebService.OrganoDestinatario(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test24CatalogoTramitesWebServiceGetAddressees() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.OrganosDestinatarios value = null;
        value = binding.getAddressees(new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test25CatalogoTramitesWebServiceGetHook() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Conector value = null;
        value = binding.getHook(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test26CatalogoTramitesWebServiceAddHook() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addHook(new es.ieci.tecdoc.sigem.CalendarioWebService.Conector(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test27CatalogoTramitesWebServiceDeleteHook() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteHook(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test28CatalogoTramitesWebServiceUpdateHook() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateHook(new es.ieci.tecdoc.sigem.CalendarioWebService.Conector(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test29CatalogoTramitesWebServiceGetHooks() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Conectores value = null;
        value = binding.getHooks(new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test30CatalogoTramitesWebServiceGetHooksByType() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.Conectores value = null;
        value = binding.getHooksByType(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test31CatalogoTramitesWebServiceGetHookType() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.TipoConector value = null;
        value = binding.getHookType(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test32CatalogoTramitesWebServiceAddHookType() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addHookType(new es.ieci.tecdoc.sigem.CalendarioWebService.TipoConector(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test33CatalogoTramitesWebServiceDeleteHookType() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteHookType(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test34CatalogoTramitesWebServiceUpdateHookType() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateHookType(new es.ieci.tecdoc.sigem.CalendarioWebService.TipoConector(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test35CatalogoTramitesWebServiceGetHookTypes() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.TiposConectores value = null;
        value = binding.getHookTypes(new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test36CatalogoTramitesWebServiceGetAuthHooks() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.ConectoresAutenticacion value = null;
        value = binding.getAuthHooks(new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test37CatalogoTramitesWebServiceAddAuthHooks() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.addAuthHooks(new es.ieci.tecdoc.sigem.CalendarioWebService.ConectorAutenticacion(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test38CatalogoTramitesWebServiceDeleteAuthHooks() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.deleteAuthHooks(new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test39CatalogoTramitesWebServiceUpdateAuthHooks() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.RetornoServicio value = null;
        value = binding.updateAuthHooks(new es.ieci.tecdoc.sigem.CalendarioWebService.ConectorAutenticacion(), new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }

    public void test40CatalogoTramitesWebServiceGetAuthHook() throws Exception {
        es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.CalendarioWebService.CatalogoTramitesWebServiceServiceLocator().getCatalogoTramitesWebService();
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
        es.ieci.tecdoc.sigem.CalendarioWebService.ConectorAutenticacion value = null;
        value = binding.getAuthHook(new java.lang.String(), new java.lang.String(), new es.ieci.tecdoc.sigem.CalendarioWebService.Entidad());
        // TBD - validate results
    }*/

}
