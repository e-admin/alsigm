package fondos.vos;

/**
 * Métodos de obtención de los valores de los campos de una unidad documental
 * del cuadro de clasificación de fondos documentales
 */
public interface UnidadDocumentalVO extends ElementoCuadroClasificacionVO {

	public String getCodSistProductor();

	public String getDenominacion();

	public String getIdElementocf();

	public String getIdSerie();

	public String getNumExp();

	public String getTipoDocumental();

	public int getMarcasBloqueo();

}
