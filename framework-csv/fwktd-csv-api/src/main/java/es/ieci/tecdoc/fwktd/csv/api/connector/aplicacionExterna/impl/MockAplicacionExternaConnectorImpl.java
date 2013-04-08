package es.ieci.tecdoc.fwktd.csv.api.connector.aplicacionExterna.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Mock del conector con las aplicaciones externas para pruebas unitarias.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class MockAplicacionExternaConnectorImpl extends AbstractAplicacionExternaConnector{

	public MockAplicacionExternaConnectorImpl() {
		super();
	}

	public boolean existeDocumento(String csv, Map<String, String> params) {
		if ("XXXXX".equals(csv)) {
			return false;
		} else {
			return true;
		}
	}

	public byte[] getContenidoDocumento(String csv, Map<String, String> params) {
		if ("XXXXX".equals(csv)) {
			return null;
		} else {
			return "Contenido del documento".getBytes();
		}
	}

	public void writeDocumento(String csv, OutputStream outputStream,
			Map<String, String> params) throws IOException {
		if (!"XXXXX".equals(csv)) {
			outputStream.write("Contenido del documento".getBytes());
			outputStream.flush();
		}
	}

}
