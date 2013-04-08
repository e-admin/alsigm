/**
 * EstructuraOrganizativaWebServiceServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public class EstructuraOrganizativaWebServiceServiceTestCase extends junit.framework.TestCase {
    public EstructuraOrganizativaWebServiceServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testEstructuraOrganizativaWebServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1EstructuraOrganizativaWebServiceGetUsuariosDepartamento() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        // value = binding.getUsuariosDepartamento(0, new java.lang.String());
        int idDepartamento = 3;
        String idEntidad = "001";
        value = binding.getUsuariosDepartamento(idDepartamento, idEntidad);
        // TBD - validate results
		assertNotNull("No hay usuarios en el departamento " + idDepartamento
				+ " de la entidad " + idEntidad, value.getUsuarios().getItem(0)
				.getName());
    }

    public void test2EstructuraOrganizativaWebServiceGetUsuarioPorId() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario value = null;
        // value = binding.getUsuarioPorId(0, new java.lang.String());
        int idUsuario = 6;
        String idEntidad = "001";
        value = binding.getUsuarioPorId(idUsuario, idEntidad);
        // TBD - validate results
		assertNotNull("No hay usuario " + idUsuario + " en la entidad "
				+ idEntidad, value.getName());
    }

    public void test3EstructuraOrganizativaWebServiceGetUsuarioPorNombre() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario value = null;
        // value = binding.getUsuarioPorNombre(new java.lang.String(), new java.lang.String());
        String nombreUsuario = "sigem";
        String idEntidad = "001";
        value = binding.getUsuarioPorNombre(nombreUsuario, idEntidad);
        // TBD - validate results
		assertNotNull("No hay usuarios con el nombre" + nombreUsuario
				+ " en la entidad " + idEntidad, value.getName());
    }

    public void test4EstructuraOrganizativaWebServiceGetUsuariosAplicacion() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        // value = binding.getUsuariosAplicacion(0, true, true, true, new java.lang.String());
        int idAplicacion = 5;
        String idEntidad = "001";
        value = binding.getUsuariosAplicacion(idAplicacion, true, true, true, idEntidad);
        // TBD - validate results
		assertNotNull("No hay usuarios en la aplicación " + idAplicacion
				+ " en la entidad " + idEntidad, value.getUsuarios().getItem(0)
				.getName());
    }

    public void test5EstructuraOrganizativaWebServiceGetUsuariosGrupo() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        // value = binding.getUsuariosGrupo(0, new java.lang.String());
        int idGrupo = 3;
        String idEntidad = "001";
        value = binding.getUsuariosGrupo(3, idEntidad);
        // TBD - validate results
		assertNotNull("No hay usuarios en el grupo " + idGrupo
				+ " de la entidad " + idEntidad, value.getUsuarios().getItem(0)
				.getName());
    }

    public void test6EstructuraOrganizativaWebServiceGetDepartamentosPorPadre() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos value = null;
        // value = binding.getDepartamentosPorPadre(0, new java.lang.String());
        int idDeptPadre = 3;
        String idEntidad = "001";
        value = binding.getDepartamentosPorPadre(idDeptPadre, idEntidad);
        // TBD - validate results
		assertNotNull("No hay departamentos hijos para el departamento padre "
				+ idDeptPadre + " de la entidad " + idEntidad, value
				.getDepartamentosLista().getDepartamentos().getItem(0)
				.getName());
    }

    public void test7EstructuraOrganizativaWebServiceGetDepartamentos() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos value = null;
        //value = binding.getDepartamentos(new java.lang.String());
        String idEntidad = "001";
        value = binding.getDepartamentos(idEntidad);
        // TBD - validate results
		assertNotNull("No hay departamentos en la entidad " + idEntidad, value
				.getDepartamentosLista().getDepartamentos().getItem(0)
				.getName());
    }

    public void test8EstructuraOrganizativaWebServiceGetGrupos() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos value = null;
        // value = binding.getGrupos(new java.lang.String());
        String idEntidad = "001";
        value = binding.getGrupos(idEntidad);
        // TBD - validate results
		assertNotNull("No hay grupos en la entidad " + idEntidad, value
				.getGruposLista().getGrupos().getItem(0).getName());
    }

    public void test9EstructuraOrganizativaWebServiceGetGrupo() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo value = null;
        // value = binding.getGrupo(0, new java.lang.String());
        int idGrupo = 3;
        String idEntidad = "001";
        value = binding.getGrupo(idGrupo, idEntidad);
        // TBD - validate results
		assertNotNull(
				"No hay grupo " + idGrupo + " en la entidad " + idEntidad,
				value.getName());
    }

    public void test10EstructuraOrganizativaWebServiceGetDepartamento() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento value = null;
        // value = binding.getDepartamento(0, new java.lang.String());
        int idDepartamento = 5;
        String idEntidad = "001";
        value = binding.getDepartamento(idDepartamento, idEntidad);
        // TBD - validate results
        assertNotNull("No hay departamento " + idDepartamento
				+ " en la entidad " + idEntidad, value.getName());
    }

    public void test11EstructuraOrganizativaWebServiceCrearArchivador() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero value = null;
        value = binding.crearArchivador(new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Archive(), new java.lang.String());
        // TBD - validate results
    }

    public void test12EstructuraOrganizativaWebServiceEditarDepartamentoCompleto() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio value = null;
        //value = binding.editarDepartamentoCompleto(new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento(), new java.lang.String());
        int idDepartamento = 5;
        String idEntidad = "001";
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento departamento = binding.getDepartamento(idDepartamento, idEntidad);
        value = binding.editarDepartamentoCompleto(departamento, idEntidad);
        // TBD - validate results
        assertNotNull("Editando departamento " + idDepartamento
				+ " en la entidad " + idEntidad, value.getReturnCode());
    }

    public void test13EstructuraOrganizativaWebServiceEditarUsuario() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio value = null;
        //value = binding.editarUsuario(new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario(), new java.lang.String());
        int idUsuario = 6;
        String idEntidad = "001";
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario usuario = binding.getUsuarioPorId(idUsuario, idEntidad);
        value = binding.editarUsuario(usuario, idEntidad);
        // TBD - validate results
        assertNotNull("Editando usuario " + idUsuario
				+ " en la entidad " + idEntidad, value.getReturnCode());
    }

    public void test14EstructuraOrganizativaWebServiceEliminarArchivador() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio value = null;
        //value = binding.eliminarArchivador(0, 0, new java.lang.String());        
        int idUsuario = 1;
        int idArchivador = 1;
        String idEntidad = "001";
        value = binding.eliminarArchivador(idUsuario, idArchivador, idEntidad);
        // TBD - validate results
        assertNotNull("Eliminando archivador " + idArchivador + " del usuario " + idUsuario
				+ " en la entidad " + idEntidad, value.getReturnCode());
    }

    public void test15EstructuraOrganizativaWebServiceGetListas() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas value = null;
    	// value = binding.getListas(new java.lang.String());
        String idEntidad = "001";
        value = binding.getListas(idEntidad);
        // TBD - validate results
        assertNotNull("No hay listas en la entidad " + idEntidad, value
				.getListas().getItem(0).getName());
    }

    public void test16EstructuraOrganizativaWebServiceGetUsuarios() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        // value = binding.getUsuarios(0, new int[0], true, new java.lang.String());
        int aplicacion = 5;
        int idsUser[] = new int[]{0,1,6}; 
        boolean superusers = true;
        String idEntidad = "001";
        value = binding.getUsuarios(aplicacion, idsUser, superusers, idEntidad);
        // TBD - validate results
        assertNotNull("No hay usuarios en la entidad " + idEntidad, value
				.getUsuarios().getItem(0).getName());
    }

    public void test17EstructuraOrganizativaWebServiceGetUsuariosAsociation() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        //value = binding.getUsuariosAsociation(0, 0, true, new java.lang.String());
        int aplicacion = 5;
        int deptId = 3; 
        boolean superusers = true;
        String idEntidad = "001";
        value = binding.getUsuariosAsociation(aplicacion, deptId, superusers, idEntidad);
        // TBD - validate results
        assertNotNull("No hay usuarios asociación en la entidad " + idEntidad, value
				.getUsuarios().getItem(0).getName());
    }

    public void test18EstructuraOrganizativaWebServiceGetUsuariosYaAsociados() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        //value = binding.getUsuariosYaAsociados(new int[0], 0, new java.lang.String());
        int idsUser[] = new int[]{0,1,6};
        int idOfic = 3; 
        String idEntidad = "001";
        value = binding.getUsuariosYaAsociados(idsUser, idOfic, idEntidad);
        // TBD - validate results
        assertNotNull("No hay usuarios ya asociados en la entidad " + idEntidad, value
				.getUsuarios().getItem(0).getName());
    }

    public void test19EstructuraOrganizativaWebServiceGetUsuariosAplicacionPorDepartamento() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios value = null;
        //value = binding.getUsuariosAplicacionPorDepartamento(0, new int[0], true, true, true, new java.lang.String());
        int aplicacion = 5;
        int deptId[] = new int[]{3,5};
        boolean sinPermisos = true;
        boolean users = true;
        boolean superusuarios = true;
        String idEntidad = "001";
        value = binding.getUsuariosAplicacionPorDepartamento(aplicacion, deptId, sinPermisos, users, superusuarios, idEntidad);
        // TBD - validate results
        assertNotNull("No hay usuarios por departamento en la entidad " + idEntidad, value
				.getUsuarios().getItem(0).getName());
    }

    public void test20EstructuraOrganizativaWebServiceCrearGrupoLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap value = null;
        value = binding.crearGrupoLdap(new java.lang.String(), new java.lang.String(), 0, new java.lang.String());
        // TBD - validate results
    }

    public void test21EstructuraOrganizativaWebServiceGetGrupoLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap value = null;
        //value = binding.getGrupoLdap(new java.lang.String(), new java.lang.String());
        String ldapguid = "ffdc0c415c8c054fb3fd05cc72dcffdb";
        String idEntidad = "001";
        value = binding.getGrupoLdap(ldapguid, idEntidad);
        // TBD - validate results
        assertNotNull("No Existe Grupo Ldap con GUID " + ldapguid +" en la entidad " + idEntidad,
				value.get_fullname());
    }

    public void test22EstructuraOrganizativaWebServiceCrearUsuarioLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap value = null;
        value = binding.crearUsuarioLdap(new java.lang.String(), new java.lang.String(), 0, new java.lang.String());
        // TBD - validate results
    }

    public void test23EstructuraOrganizativaWebServiceGetUsuarioLdapByGuid() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap value = null;
        //value = binding.getUsuarioLdapByGuid(new java.lang.String(), new java.lang.String());        
        String ldapguid = "dc9ee54f29f46243a9e8b67d88c92e87";
        String idEntidad = "001";
        value = binding.getUsuarioLdapByGuid(ldapguid, idEntidad);
        // TBD - validate results
        assertNotNull("No Existe usuario Ldap con GUID " + ldapguid +" en la entidad " + idEntidad,
				value.get_ldapfullname());
    }

    public void test24EstructuraOrganizativaWebServiceGetUsuariosLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap value = null;
        //value = binding.getUsuarioLdap(0, new java.lang.String());
        String idEntidad = "001";
        value = binding.getUsuariosLdap(idEntidad);
        // TBD - validate results
        assertNotNull("obteniendo usuarios Ldap de la entidad " + idEntidad,
				value.getUsuariosLdap().getItem(0).get_ldapfullname());
    }

    public void test25EstructuraOrganizativaWebServiceGetUsuarioLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap value = null;
        //value = binding.getUsuarioLdap(0, new java.lang.String());
        int idUserLdap = 11;
        String idEntidad = "001";
        value = binding.getUsuarioLdap(idUserLdap, idEntidad);
        // TBD - validate results
        assertNotNull("No Existe usuario Ldap con ID " + idUserLdap +" en la entidad " + idEntidad,
				value.get_ldapfullname());
    }

    public void test26EstructuraOrganizativaWebServiceGetUsuariosLdapYaAsociados() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap value = null;
        //value = binding.getUsuariosLdapYaAsociados(new int[0], 0, new java.lang.String());
        int idsUserLdap[] = new int[]{11, 14};
        int idOfic = 3;
        String idEntidad = "001";
        value = binding.getUsuariosLdapYaAsociados(idsUserLdap, idOfic, idEntidad);
        // TBD - validate results
        assertNotNull("No Existen usuarios Ldap asociados a la oficina " + idOfic 
        	+" en la entidad " + idEntidad,	value.getUsuariosLdap().getItem(0).get_ldapfullname());
    }

    public void test27EstructuraOrganizativaWebServiceEditarUsuarioLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio value = null;
        //value = binding.editarUsuarioLdap(new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap(), new java.lang.String());
        int idUserLdap = 11;
        String idEntidad = "001";
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap userLdap = binding.getUsuarioLdap(idUserLdap, idEntidad);
        value = binding.editarUsuarioLdap(userLdap, idEntidad);
        // TBD - validate results
        assertNotNull("No se pudo editar usuario Ldap " + idUserLdap 
        	+" en la entidad " + idEntidad,	value.getReturnCode());
    }

    public void test28EstructuraOrganizativaWebServiceEliminarUsuarioLdap() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio value = null;
     	//value = binding.eliminarUsuarioLdap(0, new java.lang.String());
        int idUserLdap = 11;
        String idEntidad = "001";
        value = binding.eliminarUsuarioLdap(idUserLdap, idEntidad);
        // TBD - validate results
        assertNotNull("Eliminando usuario Ldap " + idUserLdap 
        	+" en la entidad " + idEntidad,	value.getReturnCode());
    }

    public void test29EstructuraOrganizativaWebServiceGetUsuarioLdapBasicById() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena value = null;
        //value = binding.getUsuarioLdapBasicById(0, new java.lang.String());
        int idUserLdap = 14;
        String idEntidad = "001";
        value = binding.getUsuarioLdapBasicById(idUserLdap, idEntidad);
        // TBD - validate results
        assertNotNull("No se pudo obtener el usuario Ldap " + idUserLdap 
        	+" en la entidad " + idEntidad,	value);
    }

    public void test30EstructuraOrganizativaWebServiceGetDepartamentoLite() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento value = null;
        //value = binding.getDepartamentoLite(0, new java.lang.String());
        int idDept = 7;
        String idEntidad = "001";
        value = binding.getDepartamentoLite(idDept, idEntidad);
        // TBD - validate results
        assertNotNull("Obteniendo departamento " + idDept 
        	+" en la entidad " + idEntidad,	value.getName());
    }

    public void test31EstructuraOrganizativaWebServiceGetGrupoLdapById() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap value = null;
        //value = binding.getGrupoLdapById(0, new java.lang.String());
        int idGrupo = 2;
        String idEntidad = "001";
        value = binding.getGrupoLdapById(idGrupo, idEntidad);
        // TBD - validate results
        assertNotNull("Obteniendo grupo Ldap " + idGrupo 
        	+" en la entidad " + idEntidad,	value.get_fullname());
    }

    public void test32EstructuraOrganizativaWebServiceGetUsuariosLdapAplicacionPorDepartamento() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap value = null;
        //value = binding.getUsuariosLdapAplicacionPorDepartamento(0, new int[0], true, true, true, new java.lang.String());
        int aplicacion = 5;
        int idsUser[] = new int[]{1,3};
        boolean superusers = true;
        String idEntidad = "001";
        value = binding.getUsuariosLdapAplicacion(aplicacion, idsUser, superusers, idEntidad);
        // TBD - validate results
        assertNotNull("Obteniendo usuarios Ldap aplicacion en la entidad " + idEntidad,	
        		value.getUsuariosLdap().getItem(0).get_ldapfullname());
    }

    public void test33EstructuraOrganizativaWebServiceGetUsuariosLdapAplicacion() throws Exception {
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub binding;
        try {
            binding = (es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceSoapBindingStub)
                          new es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.EstructuraOrganizativaWebServiceServiceLocator().getEstructuraOrganizativaWebService();
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
        es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap value = null;
        //value = binding.getUsuariosLdapAplicacion(0, new int[0], true, new java.lang.String());
        int aplicacion = 5;
        int deptId[] = new int[]{5};
        boolean sinPermisos = true;
        boolean usuarios = true;
        boolean superusuarios = true;
        String idEntidad = "001";
        value = binding.getUsuariosLdapAplicacionPorDepartamento(aplicacion, deptId, sinPermisos, usuarios, superusuarios, idEntidad);
        // TBD - validate results
        assertNotNull("Obteniendo usuarios Ldap por departamento en la entidad " + idEntidad,	
        		value.getUsuariosLdap().getItem(0).get_ldapfullname());
    }

}
