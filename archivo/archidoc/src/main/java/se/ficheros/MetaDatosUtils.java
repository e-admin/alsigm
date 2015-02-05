package se.ficheros;

import java.util.Date;

import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;

public class MetaDatosUtils {

	public static final String METADATO_ID = "id";
	public static final String METADATO_NAME = "name";
	public static final String METADATO_FECHA = "date";
	public static final String METADATO_DESCRIPCION = "description";
	public static final String METADATO_TITULO = "title";

	public static MetadatoVO createMetadato(String nombre, Object valor) {
		MetadatoVO metadato = new MetadatoVO();
		metadato.setNombre(nombre);
		metadato.setValor(valor);
		return metadato;
	}

	public static MetadatoVO getMetaDatoNombre(String nombre) {
		return createMetadato(METADATO_NAME, nombre);
	}

	public static MetadatoVO getMetaDatoId(String id) {
		return createMetadato(METADATO_ID, id);
	}

	public static MetadatoVO getMetaDatoFecha(Date fecha) {
		return createMetadato(METADATO_FECHA, fecha);
	}

	public static MetadatoVO getMetaDatoDescripcion(String descripcion) {
		return createMetadato(METADATO_DESCRIPCION, descripcion);
	}

	public static MetadatoVO getMetaDatoTitulo(String titulo) {
		return createMetadato(METADATO_TITULO, titulo);
	}
}
