package es.ieci.tecdoc.fwktd.dir3.api.xstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosDependenciaJerarquicaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosIdentificativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosLocalizacionVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosOperativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosVigenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosDependenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosIdentificativosVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoDatosVigenciaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismoVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.unidad.OrganismosVO;

public class XmlDcoToObject {

	private static XmlDcoToObject _instance = new XmlDcoToObject();

	/**
	 * Constructor protegido para evitar creación de instancias desde otras clases
	 */
	public XmlDcoToObject() {}



	/**
	 * Devuelve un objeto "Oficinas" a partir de un Xml de Oficinas de DCO
	 * @param xmlFilePath Path al fichero XML de Oficinas de DCO
	 * @return Un objeto "Oficinas" a partir de un Xml de Oficinas de DCO
	 */
	@Test
	public void getOficinasFromXmlFile()
	{
		XStream xstream = new XStream();


		//Anotaciones para el mapeo de las clases con los nodos del XML
		xstream.processAnnotations(OficinasVO.class);
		xstream.processAnnotations(OficinaVO.class);
		xstream.processAnnotations(OficinaDatosIdentificativosVO.class);
		xstream.processAnnotations(OficinaDatosOperativosVO.class);
		xstream.processAnnotations(OficinaDatosVigenciaVO.class);
		xstream.processAnnotations(OficinaDatosDependenciaJerarquicaVO.class);
		xstream.processAnnotations(OficinaDatosLocalizacionVO.class);

		try{
			InputStream input = this.getClass().getResourceAsStream("datosBasicosOficina.xml");
			if(input==null)
			{
				input = new FileInputStream(new File("C:/tmp/datosBasicosOficina.xml"));
			}
			OficinasVO oficinas = (OficinasVO)xstream.fromXML(input);
			oficinas.toString();
		}catch (Exception e) {
			e.printStackTrace();
			Assert.assertNotNull(null);
		}
	}

	/**
	 * Devuelve un objeto "Organismos" a partir de un Xml de Organismos de DCO
	 * @param xmlFilePath Path al fichero XML de Organismos de DCO
	 * @return Un objeto "Organismos" a partir de un Xml de Organismos de DCO
	 */
	@Test
	public void testGetOrganismosFromXmlFile()
	{
		XStream xstream = new XStream();


		//Anotaciones para el mapeo de las clases con los nodos del XML
		xstream.processAnnotations(OrganismosVO.class);
		xstream.processAnnotations(OrganismoVO.class);
		xstream.processAnnotations(OrganismoDatosIdentificativosVO.class);
		xstream.processAnnotations(OrganismoDatosDependenciaVO.class);
		xstream.processAnnotations(OrganismoDatosVigenciaVO.class);
		try{
			InputStream input = this.getClass().getResourceAsStream("datosBasicosUOrganica.xml");
			if(input==null)
			{
				input = new FileInputStream(new File("C:/tmp/datosBasicosUOrganicaCompleto.xml"));
			}
			OrganismosVO oficinas = (OrganismosVO)xstream.fromXML(input);
			oficinas.toString();
		}catch (Exception e) {
			e.printStackTrace();
			Assert.assertNotNull(null);
		}

	}
}
