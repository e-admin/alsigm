package ieci.tecdoc.sgm.pe.struts.receipt;

import java.io.IOException;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;

public class ReceiptFillerAL3001 implements ReceiptFiller{

	private static final String XPATH_LIQUIDACION 	= "LIQUIDACION";
	private static final String XPATH_ID_ENTIDAD 	= "ID_ENTIDAD";
	private static final String XPATH_REFERENCIA 	= "REFERENCIA";
	private static final String XPATH_IMPORTE		= "IMPORTE";
	private static final String XPATH_TASA = "LIQUIDACION/TASA";
	private static final String XPATH_NOMBRE_TASA	= "NOMBRE_TASA";
	private static final String XPATH_DATOS_ESPECIFICOS = "DATOS_ESPECIFICOS";
	private static final String XPATH_DETALLE = "DETALLE";	
	private static final String XPATH_NOMBRE = "NOMBRE";
	private static final String XPATH_NRC = "NRC";
	private static final String XPATH_CCC = "CCC";
	private static final String XPATH_MODELO_TASA =		"MODELO_TASA";	
	private static final String XPATH_FECHA_PAGO =		"FECHA_PAGO";	

	private static final String MEDIOPAGO = "MEDIO_PAGO";	
	private static final String FIELD_ID_ENTIDAD	= "ID_ENTIDAD";
	private static final String FIELD_REFERENCIA	= "REFERENCIA";	
	private static final String FIELD_IMPORTE		= "IMPORTE";	
	private static final String FIELD_NOMBRE_TASA	= "NOMBRE_TASA";
	private static final String FIELD_DETALLE		= "TEXTO";
	private static final String FIELD_NOMBRE		= "NOMBRE";
	private static final String FIELD_NRC			= "NRC";
	private static final String FIELD_CCC_ENTIDAD	= "ENTIDAD";
	private static final String FIELD_CCC_OFICINA 		= "OFICINA";
	private static final String FIELD_CCC_DIGITOCONTROL = "DC";
	private static final String FIELD_CCC_CUENTA 	= "CUENTA";
	private static final String FIELD_MODELO			= "MODELO";
	private static final String FIELD_FECHA_PAGO	= "FECHAPAGO";	
	
	public void fillReceipt(AcroFields poFormFlds, XmlDocument poDoc) throws IOException, DocumentException{
		XmlElement pago = null;
		XmlElement liquidacionData = null;
		XmlElement elem = null;
		XmlElement tasa = null;
		
		pago = poDoc.getRootElement();
		liquidacionData = pago.getDescendantElement(XPATH_LIQUIDACION);
		tasa = pago.getDescendantElement(XPATH_TASA);

		elem = pago.getChildElement(MEDIOPAGO);
		poFormFlds.setField(MEDIOPAGO + elem.getValue(), "X");
		
		if("1".equals(elem.getValue())){
			// Pago ccc
			elem = pago.getChildElement(XPATH_CCC);
			String cAux =  elem.getValue();
			poFormFlds.setField(FIELD_CCC_ENTIDAD, cAux.substring(0, 4));
			poFormFlds.setField(FIELD_CCC_OFICINA, cAux.substring(4, 8));
			poFormFlds.setField(FIELD_CCC_DIGITOCONTROL, cAux.substring(8, 10));
			poFormFlds.setField(FIELD_CCC_CUENTA, cAux.substring(10));
		}else if("2".equals(elem.getValue())){
			// Pago tarjeta
		}
		
		elem = liquidacionData.getChildElement(XPATH_ID_ENTIDAD);
		poFormFlds.setField(FIELD_ID_ENTIDAD, elem.getValue());

		elem = liquidacionData.getChildElement(XPATH_REFERENCIA);
		poFormFlds.setField(FIELD_REFERENCIA, elem.getValue());

		elem = liquidacionData.getChildElement(XPATH_IMPORTE);
		poFormFlds.setField(FIELD_IMPORTE, elem.getValue());

		elem = tasa.getChildElement(XPATH_NOMBRE_TASA);
		poFormFlds.setField(FIELD_NOMBRE_TASA, elem.getValue());

		elem = liquidacionData.getDescendantElement(XPATH_DATOS_ESPECIFICOS);
		elem = elem.getChildElement(XPATH_DETALLE);
		if(elem != null){
			poFormFlds.setField(FIELD_DETALLE, elem.getValue());			
		}

		elem = liquidacionData.getChildElement(XPATH_NOMBRE);
		poFormFlds.setField(FIELD_NOMBRE, elem.getValue());

		elem = liquidacionData.getChildElement(XPATH_NRC);
		poFormFlds.setField(FIELD_NRC, elem.getValue());
		
		elem = liquidacionData.getChildElement(XPATH_FECHA_PAGO);
		poFormFlds.setField(FIELD_FECHA_PAGO, elem.getValue());
		
		elem = tasa.getChildElement(XPATH_MODELO_TASA);
		poFormFlds.setField(FIELD_MODELO, elem.getValue());

	}
}
