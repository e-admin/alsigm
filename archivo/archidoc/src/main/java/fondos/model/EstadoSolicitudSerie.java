package fondos.model;

public class EstadoSolicitudSerie {

	int value;

	public static final EstadoSolicitudSerie ESTADO_SOLICITADA = new EstadoSolicitudSerie(
			SolicitudSerie.ESTADO_SOLICITADA);

	public static final EstadoSolicitudSerie ESTADO_EJECUTADA = new EstadoSolicitudSerie(
			SolicitudSerie.ESTADO_EJECUTADA);

	public static final EstadoSolicitudSerie ESTADO_DENEGADA = new EstadoSolicitudSerie(
			SolicitudSerie.ESTADO_DENEGADA);

	public EstadoSolicitudSerie(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
