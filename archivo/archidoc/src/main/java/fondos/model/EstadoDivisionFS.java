package fondos.model;

/**
 * Enumeración de los estados por los que puede pasar una división de fracción
 * de serie
 */
public class EstadoDivisionFS {

	private static int nextNumOrden = 1;

	private final int identificador = nextNumOrden++;

	private static final EstadoDivisionFS[] estadosDivisionFS = new EstadoDivisionFS[3];

	private EstadoDivisionFS() {
		int posicion = identificador - 1;

		estadosDivisionFS[posicion] = this;
	}

	public static final int ABIERTA = 1;
	public static final int PENDIENTE_VALIDACION = 2;
	public static final int VALIDADA = 3;

	public int getIdentificador() {
		return identificador;
	}

	public static EstadoDivisionFS getEstadoDivisionFS(int identificador) {
		return estadosDivisionFS[identificador - 1];
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((EstadoDivisionFS) otroObjeto)
				.getIdentificador();
	}

}
