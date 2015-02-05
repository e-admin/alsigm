package es.ieci.tecdoc.isicres.document.connector.vo;

import junit.framework.TestCase;

public class ISicresBasicDatosEspecificosVOTest extends TestCase {

	public void testToXml(){
		
		ISicresBasicDatosEspecificosVO datosEspecificos= new ISicresBasicDatosEspecificosVO();
		
		ISicresBasicDatosEspecificosValueVO value1= new ISicresBasicDatosEspecificosValueVO();
		
		value1.setId("1");
		value1.setName("nombre1");
		value1.setType("tipo1");
		value1.setValue("Valor1");				
		datosEspecificos.getValues().put("value1",value1);
		
		ISicresBasicDatosEspecificosValueVO value2= new ISicresBasicDatosEspecificosValueVO();
		value2.setId("2");
		value2.setName("nombre2");
		value2.setType("tipo2");
		value2.setValue("Valor2");				
		datosEspecificos.getValues().put("value2",value2);
		
		String xml=datosEspecificos.toXml();
		
		assertNotNull(xml);
		
		ISicresBasicDatosEspecificosVO datosEspecificosFromXml= new ISicresBasicDatosEspecificosVO();
		
		datosEspecificosFromXml.fromXml(xml);
		
		assertNotNull(datosEspecificosFromXml.getValues());
		
		assertEquals(xml, datosEspecificosFromXml.toXml());
		
		
		
	}
}
