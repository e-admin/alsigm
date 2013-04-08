package fondos.vos;

import fondos.model.TipoNivelCF;

/**
 * Nivel de descripcion dentro del cuadro de clasificacion de fondos
 * documentales
 * 
 */
public interface INivelCFVO {

	public String getIdFichaDescrPref();

	public String getNombre();

	public int getTipo();

	public String getIdFichaClfDocPref();

	public String getIdRepEcmPref();

	public TipoNivelCF getTipoNivel();

	public String getId();

	public int getSubtipo();

	public void setIdFichaDescrPref(String idFichaDescrpref);

	public void setNombre(String nombre);

	public void setTipo(int tipo);

	public void setIdFichaClfDocPref(String idFichaClfDocPref);

	public void setIdRepEcmPref(String idRepEcmPref);

	public void setTipoNivel(int tipoNivel);

	public void setId(String id);

	public void setSubtipo(int subtipo);
}
