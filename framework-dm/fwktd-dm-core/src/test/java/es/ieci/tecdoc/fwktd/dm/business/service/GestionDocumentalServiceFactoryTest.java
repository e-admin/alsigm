package es.ieci.tecdoc.fwktd.dm.business.service;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.dm.business.BaseDocumentoTest;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;

public class GestionDocumentalServiceFactoryTest extends BaseDocumentoTest {

    @Test
    public void testGetService() {

    	try {

    		// Instanciar para que se registre
    		new DummyGestionDocumentalServiceBuilder("bd");

    		GestionDocumentalServiceFactory factory = getGestionDocumentalServiceFactory();
    		GestionDocumentalService service = factory.getService("1");
        	Assert.assertNotNull(service);

        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }

    private class DummyGestionDocumentalServiceBuilder extends GestionDocumentalServiceBuilder {

    	public DummyGestionDocumentalServiceBuilder(String type) {
    		super(type);
    	}

		public GestionDocumentalService createGestionDocumentalService(
				ContentType contentType) {
			return new GestionDocumentalService() {

				public void createSesion()
						throws GestionDocumentalException {
				}

				public void releaseSesion()
						throws GestionDocumentalException {
				}

				public boolean existeDocumento(String idDocumento)
						throws GestionDocumentalException {
					return false;
				}

				public InfoDocumentoVO getInfoDocumento(
						String idDocumento)
						throws GestionDocumentalException {
					return null;
				}

				public void retrieveDocumento(String idDocumento,
						OutputStream out)
						throws GestionDocumentalException {
				}

				public InfoDocumentoVO createDocumento(
						InfoDocumentoVO documento, InputStream in)
						throws GestionDocumentalException {
					return null;
				}

				public InfoDocumentoVO updateDocumento(
						InfoDocumentoVO documento, InputStream in)
						throws GestionDocumentalException {
					return null;
				}

				public void deleteDocumento(String idDocumento)
						throws GestionDocumentalException {
				}
			};
		}
	}
}
