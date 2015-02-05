package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.CriteriosMapper;

public class CriteriosMapperTest extends TestCase {

	public void testMarshall() throws Exception {
		CriteriosMapper mapper = new CriteriosMapper();

		Map<String, Object> context = new HashMap<String, Object>();
		CriteriaVO criteria = new CriteriaVO();
		String xml = mapper.marshall(criteria, context);

		Assert.assertNotNull(xml);
		Assert.assertEquals(
				xml,
				FileUtils
						.readFileToString(new File(
								getClass()
										.getResource(
												"criteriaEmpty.xml")
										.toURI())));
	}
}
