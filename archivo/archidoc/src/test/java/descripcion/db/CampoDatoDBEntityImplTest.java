package descripcion.db;

import org.junit.Test;

import common.manager.ArchidocBasePostgreSQLTest;
import common.manager.ArchidocDBBaseTest;
import descripcion.vos.CampoDatoVO;

public class CampoDatoDBEntityImplTest extends ArchidocBasePostgreSQLTest {

	@Override
	protected ICampoDatoDBEntity getDAO() {
		return getManager().getCampoDatoDBEntity();
	}

	@Test
	public void getCampoDatoByEtiquetaXml(){
		CampoDatoVO campoDato = getDAO().getCampoDatoByEtiquetaXml(etiquetaXml);
	}

	@Test
	public void getCampoDatoByEtiquetaXmlIdTabla(){
		CampoDatoVO campoDatoVO = getDAO().getCampoDatoByEtiquetaXml(etiquetaXml, idTabla);
	}

}
