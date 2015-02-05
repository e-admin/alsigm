package ieci.tecdoc.sgm.certificacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import ieci.tecdoc.sgm.certificacion.bean.Usuario;
import ieci.tecdoc.sgm.certificacion.bean.pago.Liquidacion;
import ieci.tecdoc.sgm.certificacion.bean.pago.Pago;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.manager.CertificacionPagoManager;
import ieci.tecdoc.sgm.certificacion.pdf.CertificacionPDF;
import ieci.tecdoc.sgm.certificacion.pdf.LectorPropiedadesPDF;
import ieci.tecdoc.sgm.certificacion.pdf.RetornoPdf;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosPropiedades;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.xml.GeneradorXML;
import ieci.tecdoc.sgm.certificacion.xml.bean.Campo;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosComunes;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosEspecificos;
import ieci.tecdoc.sgm.certificacion.xml.filler.GeneradorXMLPago;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

public class Test {

	public static void main(String[] args) {
			
			/***************************** GENERACION XML ***************************************/
			Campo campo1 = new Campo (Defs.TAG_XML_ENTIDAD, "Entidad de Prueba", null);
			
			ArrayList campos1 = new ArrayList();
			Campo campo2a = new Campo(Defs.TAG_XML_NOMBRE, "Juan", null);
			Campo campo2b = new Campo(Defs.TAG_XML_APELLIDOS, "Romero Espada", null);
			Campo campo2c = new Campo(Defs.TAG_XML_ID, "52432356P", null);
			campos1.add(campo2a);
			campos1.add(campo2b);
			campos1.add(campo2c);
			
			Campo campo2 = new Campo ("Usuario", null, campos1);
			
			Campo campo3 = new Campo (Defs.TAG_XML_DOMICILIO, "Calle Bravo Murillo, 4, 4B", null);
			
			ArrayList datosComunesArray = new ArrayList();
			datosComunesArray.add(campo1);
			datosComunesArray.add(campo2);
			datosComunesArray.add(campo3);
			
			DatosComunes datosComunes = new DatosComunes(datosComunesArray);
			
			Pago pago = new Pago();
			pago.setIdTasa("12"); 
			pago.setIdEntidadEmisora("000001");
			pago.setImporte("101.90");
			pago.setFecha("12-09-2007");
			pago.setHora("10:12:32");
			Pago pago1 = new Pago();
			pago1.setIdTasa("14"); 
			pago1.setIdEntidadEmisora("000002");
			pago1.setImporte("221.30");
			pago1.setFecha("14-09-2007");
			pago1.setHora("13:07:12");			
			Liquidacion liq1 = new Liquidacion();
			liq1.setInicioPeriodo("12-08-2007 10:00:00");
			liq1.setFinPeriodo("16-09-2007 14:00:00");
			Liquidacion liq2 = new Liquidacion();
			liq2.setInicioPeriodo("09-10-2007 8:00:00");
			liq2.setFinPeriodo("21-10-2007 15:00:00");
			pago.setLiquidacion(liq1);
			pago1.setLiquidacion(liq2);
			Pago[] pagos = new Pago[12];
			pagos[0] = pago;
			pagos[1] = pago1;
			pagos[2] = pago; pagos[3] = pago1; pagos[4] = pago; pagos[5] = pago1; pagos[6] = pago; 
			pagos[7] = pago1; pagos[8] = pago; pagos[9] = pago1; 	pagos[10] = pago; pagos[11] = pago1;
			Entidad entidad = new Entidad();
			entidad.setIdentificador("PRUEBA");
			entidad.setNombre("Ayuntamiento de PRUEBA");
			
			Usuario usuario = new Usuario("Juan", "Romero López", "53243698P", "918372987", "", "Calle Bravo Murillo, 4, 4B fsf sdf afsdf afdsf safadsfsa fdfsafdsafsa", "Colmenar Viejo", "Madrid", "28134", "es_ES"); 
			RetornoPdf retorno = null;
			try{
				CertificacionPagoManager oManager = LocalizadorManager.getManagerCertificacionPago();
				 retorno = oManager.GenerarCertificacion(entidad, pagos, usuario);
			}catch(CertificacionException e){
				e.printStackTrace();
			}
			
			try{
				FileOutputStream fos = new FileOutputStream(new File("c:\\PRUEBA.pdf"));
				fos.write(retorno.getContenido());
				fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/*pagos[2] = pago; pagos[3] = pago1; pagos[4] = pago; pagos[5] = pago1; pagos[6] = pago; 
			pagos[7] = pago1; pagos[8] = pago; pagos[9] = pago1; 	pagos[10] = pago; pagos[11] = pago1;*/
			
			/*
			String xml = GeneradorXMLPago.GenerarXML(datosComunes, pagos, true);
			System.out.println(xml);
		
			Properties propiedades = new Properties();
			try{
				propiedades.load(new FileInputStream(new File("C:\\workspaceSIGEM\\PRUEBA.properties")));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			DatosPropiedades datosPropiedades = LectorPropiedadesPDF.LeerPropiedades(propiedades);
			byte[] bytes = CertificacionPDF.GenerarCertificacion(datosPropiedades, xml);
			try{
				FileOutputStream fos = new FileOutputStream(new File("c:\\PRUEBA.pdf"));
				fos.write(bytes);
				fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}*/
	}

}
