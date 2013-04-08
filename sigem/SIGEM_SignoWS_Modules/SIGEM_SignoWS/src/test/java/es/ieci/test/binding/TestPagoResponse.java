package es.ieci.test.binding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.ActoJuridicoType;
import org.notariado.ancert.xml.plusvalias.liquidacion.AdquirenteType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CabeceraType;
import org.notariado.ancert.xml.plusvalias.liquidacion.DireccionInmuebleType;
import org.notariado.ancert.xml.plusvalias.liquidacion.DireccionType;
import org.notariado.ancert.xml.plusvalias.liquidacion.NotarioType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ObjectFactory;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACION;
import org.notariado.ancert.xml.plusvalias.liquidacion.ProtocoloType;
import org.notariado.ancert.xml.plusvalias.liquidacion.SujetoType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.ADQUIRENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.INMUEBLEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.RESULTADOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType.TRANSMITENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.SujetoType.DATOSPERSONALESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.TRANSMISIONESANTERIORESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.TRANSMISIONESANTERIORESType.TRANSMISIONType;

/**
 * @author angel_castro@ieci.es - 21/07/2010
 */
public class TestPagoResponse extends TestCase{

	public static final Logger logger = Logger.getLogger(TestPagoResponse.class);
	
	public void setUp() throws Exception {
	}

	public void testMarshallPagoResponse() {
		ObjectFactory factoria = new ObjectFactory();
		try {
			CabeceraType cabecera = factoria.createCabeceraType();
			cabecera.setIDCOMUNICACION(1);
			cabecera.setAPLICACION("aplicacion");
			cabecera.setFECHA("21/10/2010");
			cabecera.setHORA("09:09");
			cabecera.setOPERACION("1");
			cabecera.setEMISOR("1");
			cabecera.setRECEPTOR("1");

			NotarioType notario = factoria.createNotarioType();
			notario.setCODIGO("1223");
			notario.setNOMBRE("yo");
			notario.setAPELLIDO1("mismo");
			
			ProtocoloType protocolo = factoria.createProtocoloType();
			protocolo.setNUMERO(123);
			protocolo.setFECHAAUTORIZACION("21/01/2010");
			
			ActoJuridicoType actoJuridico = factoria.createActoJuridicoType();
			actoJuridico.setCODIGO("12");
			actoJuridico.setDESCRIPCION("descripcion");
			
			DATOSPERSONALESType datosPersonales = factoria.createSujetoTypeDATOSPERSONALESType();
			datosPersonales.setTIPODOCUMENTO("1");
			datosPersonales.setNUMERODOCUMENTO("30969974Z");
			datosPersonales.setAPELLIDO1RAZONSOCIAL("castro");
			
			DireccionType domicilio = factoria.createDireccionType();
			domicilio.setVIA("montero");
			domicilio.setINEPROVINCIA("cordoba");
			domicilio.setINEMUNICIPIO("cordoba");
			domicilio.setTIPOVIA("calle");
			domicilio.setCODIGOPOSTAL("14001");
			
			SujetoType sujeto = factoria.createSujetoType();
			sujeto.setIDENTIFICADOR(0);
			sujeto.setDATOSPERSONALES(datosPersonales);
			sujeto.setDOMICILIO(domicilio);
			
			TRANSMISIONType transmision = factoria.createTransmitenteTypeTRANSMISIONESANTERIORESTypeTRANSMISIONType();
			transmision.setFECHA("01/01/2001");
			transmision.setPORCENTAJE("25");
			
			TRANSMISIONESANTERIORESType transmisionesAnteriores = factoria.createTransmitenteTypeTRANSMISIONESANTERIORESType();
			transmisionesAnteriores.getTRANSMISION().add(transmision);
			
			TransmitenteType transmitiente = factoria.createTransmitenteType(); 
			transmitiente.setDATOSBASICOS(sujeto);
			transmitiente.setPORCENTAJETRANSMITIDO("100");
			transmitiente.setCLASEDERECHO("1");
			transmitiente.setTRANSMISIONESANTERIORES(transmisionesAnteriores);
			
			TRANSMITENTESType transmitientes = factoria.createPAGOLIQUIDACIONTypeREPLYTypeTRANSMITENTESType();
			transmitientes.getTRANSMITENTE().add(transmitiente);
			
			AdquirenteType adquiriente = factoria.createAdquirenteType();
			adquiriente.setDATOSBASICOS(sujeto);
			adquiriente.setPORCENTAJEADQUIRIDO("50");
			adquiriente.setCLASEDERECHO("1");
			
			ADQUIRENTESType adquirientes = factoria.createPAGOLIQUIDACIONTypeREPLYTypeADQUIRENTESType();
			adquirientes.getADQUIRENTE().add(adquiriente);
			
			DireccionInmuebleType direccion = factoria.createDireccionInmuebleType();
			direccion.setINEPROVINCIA("Cordoba");
			direccion.setINEMUNICIPIO("Cordoba");
			
			INMUEBLEType inmueble = factoria.createPAGOLIQUIDACIONTypeREPLYTypeINMUEBLEType();
			inmueble.setREFERENCIACATASTRAL("232323235354");
			inmueble.setDIRECCION(direccion);
			inmueble.setSUPERFICIEINMUEBLE("233");
			
			RESULTADOType resultado = factoria.createPAGOLIQUIDACIONTypeREPLYTypeRESULTADOType();
			resultado.setRETORNO(false);

			REPLYType response = factoria.createPAGOLIQUIDACIONTypeREPLYType();			
			response.setRESULTADO(resultado);
			//response.setINMUEBLE(inmueble);
			//response.setADQUIRENTES(adquirientes);
			//response.setTRANSMITENTES(transmitientes);
			//response.setACTOJURIDICO(actoJuridico);
			//response.setPROTOCOLO(protocolo);
			//response.setNOTARIOAUTORIZANTE(notario);
			//response.setNOTARIOTITULAR(notario);
			response.setCABECERA(cabecera);

			PAGOLIQUIDACION liquidacion = factoria.createPAGOLIQUIDACION();
			liquidacion.setREPLY(response);
			
			JAXBContext jaxbContext = JAXBContext.newInstance("org.notariado.ancert.xml.plusvalias.liquidacion");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			FileOutputStream fos = new FileOutputStream("target/test-classes/testPagoResponse.xml");
			marshaller.marshal(liquidacion, System.out);
			marshaller.marshal(liquidacion, fos);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
		 catch (FileNotFoundException e) {
			 logger.error(e.getMessage(), e);
		}
	}
	
	public void testUnmarshallPagoResponse() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("org.notariado.ancert.xml.plusvalias.liquidacion");
			Unmarshaller Unmarshaller = jaxbContext.createUnmarshaller();
			Unmarshaller.unmarshal(new StreamSource(new File("target/test-classes/testPagoResponse.xml")));
			logger.debug("Correcto");
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
