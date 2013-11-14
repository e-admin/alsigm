package es.ieci.tecdoc.isicres.api.intercambioregistral.business.util;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InteresadoExReg;

public class InteresadoUtilsTest extends TestCase{

	public void testPruebasCadena(){

		InteresadoExReg interesado = new InteresadoExReg();

		interesado.setNombreInteresado("NombreInteresado12345678901234");
		interesado.setPrimerApellidoInteresado("PrimerApellidoInteresado123456");
		interesado.setSegundoApellidoInteresado("SegundoApellidoInteresado12345");
		interesado.setCodigoMunicipioInteresado("33498");
		interesado.setCodigoProvinciaInteresado("33");
		interesado.setCorreoElectronicoInteresado("correode160CaracteresParaverSiRompeLaAplicacionEsNecarioQueSeRealiceEstaPruebaEnSICRES30123456789012345678901234567890123456789012345678901234567890@hotmail.com");
		interesado.setDocumentoIdentificacionInteresado("32887953T");
		interesado
				.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.getTipoDocumentoIdentificacion("N"));
		interesado
				.setDireccionInteresado("C/AsturconCon 160CaracteresParaComprobarelcorrectofuncionamientodeSicresRespectoalavisualizaciondelinteresado12345678901234567890123456789012345678 90123456789012");
		interesado
				.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
		interesado.setTelefonoInteresado("98510101001001201201");
		interesado.setDireccionElectronicaHabilitadaInteresado("Dir. Electrónica Habilitada Interesado");

		//Representante
		interesado.setNombreRepresentante("Antonio");
		interesado.setPrimerApellidoRepresentante("Perez");
		interesado.setSegundoApellidoRepresentante("Suarez");
		interesado.setCodigoMunicipioRepresentante("33498");
		interesado.setCodigoProvinciaRepresentante("33");
		interesado.setCodigoPaisRepresentante("34");
		interesado.setCodigoPostalRepresentante("33960");
		interesado.setCorreoElectronicoRepresentante("antonioperez@hotmail.com");
		interesado.setDocumentoIdentificacionRepresentante("00000000TT");
		interesado
				.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
		interesado.setDireccionRepresentante("C/RepurconCon160CaracteresParaComprobarelcorrectofuncionamientodeSicresRespectoalavisualizaciondelinteresado1234567890123456789012345678901234567890123456789012");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.COMPARECENCIA_ELECTRONICA);
		interesado.setRazonSocialRepresentante("Razón social Representante");
		interesado.setTelefonoRepresentante("985000000");
		interesado.setObservaciones("Observaciones del interesado principal");
		interesado.setDireccionElectronicaHabilitadaRepresentante("Dir. Electrónica Habilitada Representante");

		List<String> datos = (new InteresadoUtils()).getDatosInteresadoArray(interesado);

		for(Iterator<String> it = (Iterator<String>) datos.iterator(); it.hasNext();){
			String cadena = it.next();
			System.out.println("Longitud cadena: " + cadena.length() + " " + cadena);
			//comprobamos que toda cadena debe ser menor a 95 caracteres
			Assert.assertTrue(cadena.length()<=95);
		}
	}
}
