package salas.model;

import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoIdentificativo {

	public static final List listaTiposDocumento = new ArrayList();

	private TipoDocumentoIdentificativo(String identificador) {
		TipoDocumentoIdentificativo.listaTiposDocumento.add(identificador);
	}

	public static final TipoDocumentoIdentificativo NIF = new TipoDocumentoIdentificativo(
			"1");
	public static final TipoDocumentoIdentificativo PASAPORTE = new TipoDocumentoIdentificativo(
			"2");
	public static final TipoDocumentoIdentificativo TARJETA_RESIDENCIA = new TipoDocumentoIdentificativo(
			"3");
	public static final TipoDocumentoIdentificativo OTROS = new TipoDocumentoIdentificativo(
			"4");

	public static List getListaTiposDocumentoIdentificativo() {
		return listaTiposDocumento;
	}
}
