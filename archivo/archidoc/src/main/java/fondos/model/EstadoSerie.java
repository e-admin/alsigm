package fondos.model;

/**
 * Enumeración de los estados por los que puede pasar una serie documental
 */
public class EstadoSerie {
	protected static int i = 1;
	public static final int SOLICITIDA_ALTA = 1;
	public static final int NO_VIGENTE = 2;
	public static final int PENDIENTE_VIGENTE = 3;
	public static final int VIGENTE = 4;
	public static final int PENDIENTE_ANULACIONVIGENTE = 5;
	public static final int ANULADA = 6;
	public static final int PENDIENTE_HISTORICA = 7;
	public static final int HISTORICA = 8;
	public static final int EN_ESTUDIO = 9;
	public static final int PENDIENTE_AUTORIZACION_CAMBIOS = 10;

	public static String getNombreEstadoSerie(int estado) {
		switch (estado) {
		case SOLICITIDA_ALTA:
			return "solicitada alta";

		case NO_VIGENTE:
			return "No Vigente";

		case PENDIENTE_VIGENTE:
			return "Pendiente Vigente";

		case VIGENTE:
			return "Vigente";

		case PENDIENTE_ANULACIONVIGENTE:
			return "Anulador Vigente";

		case ANULADA:
			return "Anulada";

		case PENDIENTE_HISTORICA:
			return "Pendiente Histórica";

		case HISTORICA:
			return "Anulada";

		case EN_ESTUDIO:
			return "En estudio";

		case PENDIENTE_AUTORIZACION_CAMBIOS:
			return "Pendiente Autorización Cambios";
		}

		return "ESTADO NO EXISTENTE - " + estado;

	}

}
