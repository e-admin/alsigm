package es.ieci.tecdoc.fwktd.dir3.api.xstream;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;


public class XstreamTest {

	@Test
	public void testUnidadesConverter(){
		try{
		XStream xstream = new XStream();
		xstream.processAnnotations(DatosBasicosOficinaVO.class);

		ClassPathResource classpathREsourceLoader = new ClassPathResource("jndi.xml");
		File file = classpathREsourceLoader.getFile();
		DatosBasicosOficinaVO oficina = (DatosBasicosOficinaVO)xstream.fromXML(new FileInputStream(new File("C:/tmp/datosBasicosUOrganica.xml")));
		System.out.print(oficina.getNombre());
		}catch (Exception e) {
			e.toString();
		}
	}


}
