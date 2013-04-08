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
import org.notariado.ancert.xml.plusvalias.liquidacion.DireccionType;
import org.notariado.ancert.xml.plusvalias.liquidacion.NotarioType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ObjectFactory;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACION;
import org.notariado.ancert.xml.plusvalias.liquidacion.ProtocoloType;
import org.notariado.ancert.xml.plusvalias.liquidacion.SujetoType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.DATOSBANCARIOSType;
import org.notariado.ancert.xml.plusvalias.liquidacion.ORDENPAGOType.PARAMETROSPAGOType.IMPORTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.ADQUIRENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.ESCRITURAType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.INMUEBLEType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.TRANSMITENTESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType.INMUEBLEType.DIRECCIONType;
import org.notariado.ancert.xml.plusvalias.liquidacion.SujetoType.DATOSPERSONALESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.TRANSMISIONESANTERIORESType;
import org.notariado.ancert.xml.plusvalias.liquidacion.TransmitenteType.TRANSMISIONESANTERIORESType.TRANSMISIONType;

/**
 * @author angel_castro@ieci.es - 21/07/2010
 */
public class TestPagoRequest extends TestCase{

	public static final Logger logger = Logger.getLogger(TestPagoRequest.class);
	
	public void setUp() throws Exception {
	}

	public void testMarshallCalculoRequest() {
		ObjectFactory factory = new ObjectFactory();
		try {
			CabeceraType cabecera = factory.createCabeceraType();
			cabecera.setIDCOMUNICACION(1);
			cabecera.setAPLICACION("aplicacion");
			cabecera.setFECHA("21/10/2010");
			cabecera.setHORA("09:09");
			cabecera.setOPERACION("1");
			cabecera.setEMISOR("1");
			cabecera.setRECEPTOR("1");

			NotarioType notario = factory.createNotarioType();
			notario.setCODIGO("1223");
			notario.setNOMBRE("yo");
			notario.setAPELLIDO1("mismo");
			
			ProtocoloType protocolo = factory.createProtocoloType();
			protocolo.setNUMERO(123);
			protocolo.setFECHAAUTORIZACION("21/01/2010");
			
			ActoJuridicoType actoJuridico = factory.createActoJuridicoType();
			actoJuridico.setCODIGO("12");
			actoJuridico.setDESCRIPCION("descripcion");
			
			DATOSPERSONALESType datosPersonales = factory.createSujetoTypeDATOSPERSONALESType();
			datosPersonales.setTIPODOCUMENTO("1");
			datosPersonales.setNUMERODOCUMENTO("30969974Z");
			datosPersonales.setAPELLIDO1RAZONSOCIAL("castro");
			
			DireccionType domicilio = factory.createDireccionType();
			domicilio.setVIA("montero");
			domicilio.setINEPROVINCIA("cordoba");
			domicilio.setINEMUNICIPIO("cordoba");
			domicilio.setTIPOVIA("calle");
			domicilio.setCODIGOPOSTAL("14001");
			
			SujetoType sujeto = factory.createSujetoType();
			sujeto.setIDENTIFICADOR(0);
			sujeto.setDATOSPERSONALES(datosPersonales);
			sujeto.setDOMICILIO(domicilio);
			
			TRANSMISIONType transmision = factory.createTransmitenteTypeTRANSMISIONESANTERIORESTypeTRANSMISIONType();
			transmision.setFECHA("01/01/2001");
			transmision.setPORCENTAJE("25");
			
			TRANSMISIONESANTERIORESType transmisionesAnteriores = factory.createTransmitenteTypeTRANSMISIONESANTERIORESType();
			transmisionesAnteriores.getTRANSMISION().add(transmision);
			
			TransmitenteType transmitiente = factory.createTransmitenteType(); 
			transmitiente.setDATOSBASICOS(sujeto);
			transmitiente.setPORCENTAJETRANSMITIDO("100");
			transmitiente.setCLASEDERECHO("1");
			transmitiente.setTRANSMISIONESANTERIORES(transmisionesAnteriores);
			
			TRANSMITENTESType transmitientes = factory.createPAGOLIQUIDACIONTypeREQUESTTypeTRANSMITENTESType();
			transmitientes.getTRANSMITENTE().add(transmitiente);
			
			AdquirenteType adquiriente = factory.createAdquirenteType();
			adquiriente.setDATOSBASICOS(sujeto);
			adquiriente.setPORCENTAJEADQUIRIDO("50");
			adquiriente.setCLASEDERECHO("1");
			
			ADQUIRENTESType adquirientes = factory.createPAGOLIQUIDACIONTypeREQUESTTypeADQUIRENTESType();
			adquirientes.getADQUIRENTE().add(adquiriente);
			
			DIRECCIONType direccion = factory.createPAGOLIQUIDACIONTypeREQUESTTypeINMUEBLETypeDIRECCIONType();
			direccion.setINEPROVINCIA("Cordoba");
			direccion.setINEMUNICIPIO("Cordoba");
			
			INMUEBLEType inmueble = factory.createPAGOLIQUIDACIONTypeREQUESTTypeINMUEBLEType();
			inmueble.setREFERENCIACATASTRAL("232323235354");
			inmueble.setDIRECCION(direccion);

			DATOSBANCARIOSType datosBancarios = factory.createORDENPAGOTypePARAMETROSPAGOTypeDATOSBANCARIOSType();
			datosBancarios.setCUENTA("1");
			datosBancarios.setDC("02");
			datosBancarios.setENTIDAD("23232");
			datosBancarios.setNIFTITULAR("121212121");
			datosBancarios.setNOMBRETITULAR("yo mismo");
			datosBancarios.setOFICINA("121");
			
			IMPORTESType importesPago = factory.createORDENPAGOTypePARAMETROSPAGOTypeIMPORTESType();
			importesPago.setIDSUJETO(0);
			importesPago.setIMPORTE("123");
			
			PARAMETROSPAGOType parametrosPago = factory.createORDENPAGOTypePARAMETROSPAGOType();
			parametrosPago.setID("1");
			parametrosPago.getIMPORTES().add(importesPago);
//			parametrosPago.setDATOSBANCARIOS(datosBancarios);
			
			ORDENPAGOType ordenPago = factory.createORDENPAGOType();
			ordenPago.setPARAMETROSPAGO(parametrosPago);
			
			ESCRITURAType escritura = factory.createPAGOLIQUIDACIONTypeREQUESTTypeESCRITURAType();
			escritura.setTAMANO(0);
			escritura.setDIGEST("digest");
			escritura.setNOMBRE("nombre");
			escritura.setMIMETYPE("mime");
			
			REQUESTType request = factory.createPAGOLIQUIDACIONTypeREQUESTType();
			request.setESCRITURA(escritura);
			//request.setORDENPAGO(ordenPago);
			request.setINMUEBLE(inmueble);
			request.setADQUIRENTES(adquirientes);
			request.setTRANSMITENTES(transmitientes);
			request.setACTOJURIDICO(actoJuridico);
			request.setPROTOCOLO(protocolo);
			request.setNOTARIOAUTORIZANTE(notario);
			request.setNOTARIOTITULAR(notario);
			request.setCABECERA(cabecera);
			
			PAGOLIQUIDACION liquidacion = factory.createPAGOLIQUIDACION();
			liquidacion.setREQUEST(request);
			
			JAXBContext jaxbContext = JAXBContext.newInstance("org.notariado.ancert.xml.plusvalias.liquidacion");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			FileOutputStream fos = new FileOutputStream("target/test-classes/testPagoRequest.xml");
			marshaller.marshal(liquidacion, System.out);
			marshaller.marshal(liquidacion, fos);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
		 catch (FileNotFoundException e) {
			 logger.error(e.getMessage(), e);
		}
	}
	
	public void testUnmarshallCalculoRequest() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("org.notariado.ancert.xml.plusvalias.liquidacion");
			Unmarshaller Unmarshaller = jaxbContext.createUnmarshaller();
			Unmarshaller.unmarshal(new StreamSource(new File("target/test-classes/testPagoRequest.xml")));
			logger.debug("Correcto");
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
