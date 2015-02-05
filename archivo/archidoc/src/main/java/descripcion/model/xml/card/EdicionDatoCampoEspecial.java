package descripcion.model.xml.card;

import descripcion.model.xml.definition.DefTipos;

public class EdicionDatoCampoEspecial extends EdicionDato {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EdicionDatoCampoEspecial(short tipo) {
		super(DefTipos.TIPO_DATO_TEXTO_CORTO);
	}

}
