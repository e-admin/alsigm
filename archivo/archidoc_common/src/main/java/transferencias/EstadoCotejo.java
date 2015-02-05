package transferencias;

/**
 * Enumeracion con los posibles estados de cotejo por los que puede pasar una
 * relacion de entrega o las udocs
 *
 */
public class EstadoCotejo {
	private static int nextNumOrden = 1;

	private final int identificador = nextNumOrden++;
	private String key = null;

	private static final EstadoCotejo[] estadosCotejo = new EstadoCotejo[3];

	public EstadoCotejo(String key) {
		this.key = key;
		estadosCotejo[identificador - 1] = this;
	}

	// Pendiente de cotejar
	public static final EstadoCotejo PENDIENTE = new EstadoCotejo("archigest.archivo.transferencias.estadoCotejoPendiente");
	// Cotejada no habiendo detectado errores
	public static final EstadoCotejo REVISADA = new EstadoCotejo("archigest.archivo.transferencias.estadoCotejoRevisada");
	// Cotejada habiendo detectado errores
	public static final EstadoCotejo ERRORES = new EstadoCotejo("archigest.archivo.transferencias.estadoCotejoConErrores");

	public int getIdentificador() {
		return identificador;
	}

	public String getKey() {
		return key;
	}

	public static EstadoCotejo getEstadoCotejo(int identificador) {
		return estadosCotejo[identificador - 1];
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((EstadoCotejo) otroObjeto).getIdentificador();
	}

	public static boolean isConErrores(int estadoCotejo) {
		return estadoCotejo == ERRORES.getIdentificador();
	}

	public static boolean isPendiente(int estadoCotejo) {
		return estadoCotejo == PENDIENTE.getIdentificador();
	}

	public static boolean isRevisada(int estadoCotejo) {
		return estadoCotejo == REVISADA.getIdentificador();
	}
}