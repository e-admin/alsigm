package ieci.tdw.ispac.ispaclib.alfresco.doc;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import org.apache.axis.encoding.Base64;

public class AlfrescoConnectorTest extends TestCase {

	protected AlfrescoConnector connector = null;
	
	protected static final String FILE_CONTENT_BASE64 = "/9j/4AAQSkZJRgABAgEAlgCWAAD/7Q0eUGhvdG9zaG9wIDMuMAA4QklNA+0AAAAAABAAlgAAAAEAAQCWAAAAAQABOEJJTQQNAAAAAAAEAAAAeDhCSU0D8wAAAAAACAAAAAAAAAAAOEJJTQQKAAAAAAABAAA4QklNJxAAAAAAAAoAAQAAAAAAAAACOEJJTQP1AAAAAABIAC9mZgABAGxmZgAGAAAAAAABAC9mZgABAKGZmgAGAAAAAAABADIAAAABAFoAAAAGAAAAAAABADUAAAABAC0AAAAGAAAAAAABOEJJTQP4AAAAAABwAAD/////////////////////////////A+gAAAAA/////////////////////////////wPoAAAAAP////////////////////////////8D6AAAAAD/////////////////////////////A+gAADhCSU0ECAAAAAAAEAAAAAEAAAJAAAACQAAAAAA4QklNBBQAAAAAAAQAAAAKOEJJTQQMAAAAAAuOAAAAAQAAAHAAAABPAAABUAAAZ7AAAAtyABgAAf/Y/+AAEEpGSUYAAQIBAEgASAAA//4AJkZpbGUgd3JpdHRlbiBieSBBZG9iZSBQaG90b3Nob3CoIDUuMP/uAA5BZG9iZQBkgAAAAAH/2wCEAAwICAgJCAwJCQwRCwoLERUPDAwPFRgTExUTExgRDAwMDAwMEQwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwBDQsLDQ4NEA4OEBQODg4UFA4ODg4UEQwMDAwMEREMDAwMDAwRDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDP/AABEIAE8AcAMBIgACEQEDEQH/3QAEAAf/xAE/AAABBQEBAQEBAQAAAAAAAAADAAECBAUGBwgJCgsBAAEFAQEBAQEBAAAAAAAAAAEAAgMEBQYHCAkKCxAAAQQBAwIEAgUHBggFAwwzAQACEQMEIRIxBUFRYRMicYEyBhSRobFCIyQVUsFiMzRygtFDByWSU/Dh8WNzNRaisoMmRJNUZEXCo3Q2F9JV4mXys4TD03Xj80YnlKSFtJXE1OT0pbXF1eX1VmZ2hpamtsbW5vY3R1dnd4eXp7fH1+f3EQACAgECBAQDBAUGBwcGBTUBAAIRAyExEgRBUWFxIhMFMoGRFKGxQiPB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";
	protected static final String REPOSITORY_ID = "3";
	
	protected static final ThreadLocal thread = new ThreadLocal();

	private void setDocumentId(String uid) {
		thread.set(uid);
	}
	
	private String getDocumentId() {
		return (String) thread.get();	
	}
	
	public void setUp() throws Exception {
		connector = AlfrescoConnector.getInstance(null);
	}

	public final void testConnection() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			assertNull(session);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testNewDocument() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			// Obtener el contenido de un documento
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			Base64.decode(FILE_CONTENT_BASE64, baos);

			// Metadatos
			String properties = getMetadatos();
			
			String uid = connector.newDocument(session, new ByteArrayInputStream(baos.toByteArray()), 
					baos.size(), properties);
			
			setDocumentId(uid);
			
			assertNotNull(uid);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	public final void testUpdateDocument() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			// Obtener el contenido de un documento
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			Base64.decode(FILE_CONTENT_BASE64, baos);

			// Metadatos modificados
			String properties = getMetadatosUpdate();
			
			String uid = connector.updateDocument(session, getDocumentId(), new ByteArrayInputStream(baos.toByteArray()), 
					baos.size(), properties);
			
			assertNotNull(uid);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testExistsDocument() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			boolean exists = connector.existsDocument(session, getDocumentId());
			
			assertTrue(exists);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testGetDocumentSize() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			int size = connector.getDocumentSize(session, getDocumentId());
			
			assertTrue(size > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	public final void testGetMimeType() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			String mimeType = connector.getMimeType(session, getDocumentId());
			
			assertNotNull(mimeType);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testGetProperties() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			String properties = connector.getProperties(session, getDocumentId());
			
			assertNotNull(properties);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testGetProperty() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			String value = connector.getProperty(session, getDocumentId(), "documentName");
			
			value = connector.getProperty(session, getDocumentId(), "document_name");
			
			assertNotNull(value);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testGetDocument() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			connector.getDocument(session, getDocumentId(), baos);
			
			assertTrue(baos.toByteArray().length > 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}

	public final void testSetProperty() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			connector.setProperty(session, getDocumentId(), "procedure_name", "PROCEDIMIENTO MODIFICADO");
			
			assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	public final void testGetRepositoryInfo() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			connector.getRepositoryInfo(session, REPOSITORY_ID);
			
			assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	public final void testDeleteDocument() {
		
		Object session = null;
		
		try {
			
			session = connector.createSession();

			connector.deleteDocument(session, getDocumentId());
			
			assertTrue(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		} finally {
			try {
				connector.closeSession(session);
			} catch (ISPACException e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}
	}
	

	private static String getMetadatos() {
		
		return XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties",
					XmlTag.newTag("property", 
							XmlTag.newTag("name", "document_id") 
								+ XmlTag.newTag("value", "999"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "document_type") 
								+ XmlTag.newTag("value", "6"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "document_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("dni.gif")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "document_size") 
								+ XmlTag.newTag("value", "12525"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "procedure_id") 
								+ XmlTag.newTag("value", "3"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "procedure_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("Reclamaciones, quejas y sugerencias")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "expedient_id") 
								+ XmlTag.newTag("value", "36"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "expedient_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("EXP2009/000018")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "user_guid") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("1-9")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "user_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("1-9")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "stage_id") 
								+ XmlTag.newTag("value", "6"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "stage_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("Fase Inicio")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "task_id") 
								+ XmlTag.newTag("value", "6"))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "task_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("Comunicación de apertura")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "mimetype") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("image/gif")))
		);
	}

	private static String getMetadatosUpdate() {
		
		return XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties",
					XmlTag.newTag("property", 
							XmlTag.newTag("name", "procedure_name") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("Reclamaciones, quejas y sugerencias MODIFICADO")))
					+ XmlTag.newTag("property", 
							XmlTag.newTag("name", "mimetype") 
								+ XmlTag.newTag("value", XmlTag.newCDATA("image/gif")))
		);
	}
	
}
