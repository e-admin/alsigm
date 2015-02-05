package ieci.tecdoc.sgm.admsistema.proceso.configuracion;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class EditarTest extends TestCase {

	private static final String[] PARAMETROS_POSTGRESQL = new String[] {
			"999", //((ConfiguracionXMLForm)form).getIdEntidad(),
			"localhost", //((ConfiguracionXMLForm)form).getDireccionBaseDatos(),
			"5432", //((ConfiguracionXMLForm)form).getPuertoBaseDatos(),
			"postgres", //((ConfiguracionXMLForm)form).getUsuarioBaseDatos(),
			"postgres", //((ConfiguracionXMLForm)form).getPasswordBaseDatos(),
			"", //((ConfiguracionXMLForm)form).getInstancia(),
			Defs.PLUGIN_BASE_DATOS_POSTGRES, //((ConfiguracionXMLForm)form).getTipoBase(),
			"", //((ConfiguracionXMLForm)form).getUsuarioBaseDatosAD(),
			"", //((ConfiguracionXMLForm)form).getPasswordBaseDatosAD(),
			"", //((ConfiguracionXMLForm)form).getUsuarioBaseDatosGE(),
			"", //((ConfiguracionXMLForm)form).getPasswordBaseDatosGE(),
			"", //((ConfiguracionXMLForm)form).getUsuarioBaseDatosRP(),
			"", //((ConfiguracionXMLForm)form).getPasswordBaseDatosRP(),
			"", //((ConfiguracionXMLForm)form).getUsuarioBaseDatosTE(),
			"" //((ConfiguracionXMLForm)form).getPasswordBaseDatosTE()
	};

	public void testEditarTomcat5() throws Exception {
		

    	String resultado = doTest(PARAMETROS_POSTGRESQL, "server_tomcat5.xml", Defs.SERVIDOR_TOMCAT_5);
		Assert.assertNotNull("Resultado nulo", resultado);
		
    	System.out.println("Resultado:\n" + resultado);
	}

	public void testEditarTomcat6() throws Exception {
		

    	String resultado = doTest(PARAMETROS_POSTGRESQL, "server_tomcat6.xml", Defs.SERVIDOR_TOMCAT_6);
		Assert.assertNotNull("Resultado nulo", resultado);
		
    	System.out.println("Resultado:\n" + resultado);
	}

	public void testEditarTomcat7() throws Exception {
		

    	String resultado = doTest(PARAMETROS_POSTGRESQL, "server_tomcat7.xml", Defs.SERVIDOR_TOMCAT_7);
		Assert.assertNotNull("Resultado nulo", resultado);
		
    	System.out.println("Resultado:\n" + resultado);
	}

	protected String doTest(String[] parametros, String resource, String tipoServidor) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
		return Editar.editar(parametros, IOUtils.toByteArray(is), tipoServidor);
	}
}
