package fondos.vos;

/**
 * Jerarquia de Niveles de descripcion dentro del cuadro de clasificacion de
 * fondos documentales
 * 
 */
public interface JerarquiaNivelCFVO {

	public String getIdNivelPadre();

	public int getTipoNivelPadre();

	public String getIdNivelHijo();

	public int getTipoNivelHijo();

	public void setIdNivelPadre(String idNivelPadre);

	public void setTipoNivelPadre(int idTipoNivelPadre);

	public void setIdNivelHijo(String idNivelHijo);

	public void setTipoNivelHijo(int idTipoNivelHijo);
}
