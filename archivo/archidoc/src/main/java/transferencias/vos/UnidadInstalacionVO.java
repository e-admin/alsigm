package transferencias.vos;

/**
 * Value Object con la informacion referente a una unidad de instalacion
 */
public class UnidadInstalacionVO extends UnidadInstalacionBaseVO {

	private static final long serialVersionUID = 1L;

	public UnidadInstalacionVO() {
	}

	public UnidadInstalacionVO(String id, String idRelEntrega, int orden,
			String signaturaUI, int estadoCotejo, boolean devolver,
			String iduiubicada) {

		super(id, idRelEntrega, orden, null, signaturaUI, null, estadoCotejo,
				devolver);
		this.setIduiubicada(iduiubicada);
	}

}