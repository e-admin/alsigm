package es.ieci.tecdoc.fwktd.dm.business.config;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.dm.business.BaseDocumentoTest;

public class ConfigurationFactoryTest extends BaseDocumentoTest {

	protected static final String ENTIDAD 					= "000";
	protected static final String ENTIDAD_NO_EXISTENTE 		= "999";

	protected static final String JDBC_DATASOURCE = "JDBC_DATASOURCE";
	protected static final String JDBC_DRIVER_CLASS_NAME = "JDBC_DRIVER_CLASS_NAME";
	protected static final String JDBC_URL = "JDBC_URL";
	protected static final String JDBC_USERNAME = "JDBC_USERNAME";
	protected static final String JDBC_PASSWORD = "JDBC_PASSWORD";


	@Test
	public void testGetConfiguration() {

		try {

			Configuration configuration = getConfiguration();
			assertConfiguration(configuration);

		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testGetEntityConfiguration() {

		try {

			MultiEntityContextHolder.setEntity(ENTIDAD);

			Configuration configuration = getConfiguration();
			assertConfiguration(configuration);

		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	@Test
	public void testGetDefaultConfiguration() {

		try {

			MultiEntityContextHolder.setEntity(ENTIDAD_NO_EXISTENTE);

			Configuration configuration = getConfiguration();
			assertConfiguration(configuration);

		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	protected void assertConfiguration(Configuration configuration) {

		Assert.assertNotNull("No se ha cargado la configuración", configuration);

		// Propiedades globales
		assertGlobalProperties(configuration.getProperties());

		// Grupos de mapeos
		assertMappingGroups(configuration.getMappingGroups());

		// Tipos de contenido
		assertContentTypes(configuration.getContentTypes());
	}

	protected void assertGlobalProperties(Properties properties) {

		Assert.assertNotNull("Propiedades globales", properties);

		Assert.assertEquals(1, properties.size());
		Assert.assertEquals("test", properties.getProperty("TEST"));
	}

	protected void assertMappingGroups(List<MappingGroup> mappingGroups) {

		String entity = MultiEntityContextHolder.getEntity();

		Assert.assertNotNull("Grupos de mapeos", mappingGroups);

		if (StringUtils.isBlank(entity)
				|| ENTIDAD_NO_EXISTENTE.equals(entity)) {

			Assert.assertEquals(1, mappingGroups.size());

			MappingGroup mappingGroup = mappingGroups.get(0);
			Assert.assertEquals("grupo1", mappingGroup.getId());
			Assert.assertEquals(3, mappingGroup.getMappings().size());

		} else if (ENTIDAD.equals(entity)) {

			Assert.assertEquals(0, mappingGroups.size());
		}
	}

	protected void assertContentTypes(List<ContentType> contentTypes) {

		String entity = MultiEntityContextHolder.getEntity();

		Assert.assertNotNull("Tipos de contendio", contentTypes);

		if (StringUtils.isBlank(entity)
				|| ENTIDAD_NO_EXISTENTE.equals(entity)) {

			Assert.assertEquals(2, contentTypes.size());
			assertContentTypeBD(contentTypes.get(0));
			assertContentTypeInvesDoc(contentTypes.get(1));

		} else if (ENTIDAD.equals(entity)) {

			Assert.assertEquals(1, contentTypes.size());
			assertContentTypeBD000(contentTypes.get(0));
		}
	}

	protected void assertContentTypeBD(ContentType contentType) {

		Assert.assertNotNull("ContentType 'bd'", contentType);
		Assert.assertEquals("1", contentType.getId());
		Assert.assertEquals("bd", contentType.getName());
		Assert.assertEquals("bd", contentType.getType());

		Assert.assertEquals(4, contentType.getProperties().size());
		Assert.assertEquals("org.postgresql.Driver", contentType.getProperties().getProperty(JDBC_DRIVER_CLASS_NAME));
		Assert.assertEquals("jdbc:postgresql://localhost:5432/test", contentType.getProperties().getProperty(JDBC_URL));
		Assert.assertEquals("postgres", contentType.getProperties() .getProperty(JDBC_USERNAME));
		Assert.assertEquals("postgres", contentType.getProperties() .getProperty(JDBC_PASSWORD));

		Assert.assertEquals(7, contentType.getMappings().size());
		Assert.assertEquals(4, contentType.getTokens().size());
	}

	protected void assertContentTypeBD000(ContentType contentType) {

		Assert.assertNotNull("ContentType 'bd'", contentType);
		Assert.assertEquals("1", contentType.getId());
		Assert.assertEquals("baseDatos", contentType.getName());
		Assert.assertEquals("bd", contentType.getType());

		Assert.assertEquals(1, contentType.getProperties().size());
		Assert.assertEquals("java:comp/env/jdbc/testDS", contentType.getProperties().getProperty(JDBC_DATASOURCE));

		Assert.assertEquals(0, contentType.getMappings().size());
		Assert.assertEquals(0, contentType.getTokens().size());
	}

	protected void assertContentTypeInvesDoc(ContentType contentType) {

		Assert.assertNotNull("ContentType 'invesdoc'", contentType);
		Assert.assertEquals("2", contentType.getId());
		Assert.assertEquals("invesdoc", contentType.getName());
		Assert.assertEquals("invesdoc", contentType.getType());

		Assert.assertEquals(7, contentType.getProperties().size());
		Assert.assertEquals("org.postgresql.Driver", contentType.getProperties().getProperty(JDBC_DRIVER_CLASS_NAME));
		Assert.assertEquals("jdbc:postgresql://localhost:5432/invesdoc11", contentType.getProperties().getProperty(JDBC_URL));
		Assert.assertEquals("postgres", contentType.getProperties() .getProperty(JDBC_USERNAME));
		Assert.assertEquals("postgres", contentType.getProperties() .getProperty(JDBC_PASSWORD));

		Assert.assertEquals("1", contentType.getProperties().getProperty("ARCHIVE_ID"));
		Assert.assertEquals("0", contentType.getProperties().getProperty("USER_ID"));
		Assert.assertEquals("C:/temp", contentType.getProperties().getProperty("TEMPORARY_PATH"));

		Assert.assertEquals(15, contentType.getMappings().size());
	}

}
