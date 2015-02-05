package descripcion.model.documentos;

import java.io.IOException;
import java.util.Properties;

public class CargarCamposDocumento {

	private static CargarCamposDocumento instancia = null;
	private static Properties properties = null;
	private final static String FILE_NAME = "/descripcion/model/eventos/camposDescripcionDocumentos.properties";

	private CargarCamposDocumento() {

	}

	public static CargarCamposDocumento getInstance() {
		if (instancia == null)
			instancia = new CargarCamposDocumento();
		return instancia;
	}

	public Properties cargarCampos() {
		try {
			if (properties == null || properties.isEmpty()) {
				properties = new Properties();
				properties.load(this.getClass().getClassLoader()
						.getResourceAsStream(FILE_NAME));
			}
			return properties;
		} catch (IOException e) {
			throw new NullPointerException(
					"Can not determinate configuration settings.");
		}
	}
}
