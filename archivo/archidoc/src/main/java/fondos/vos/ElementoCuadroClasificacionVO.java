package fondos.vos;

import util.TreeModelItem;

import common.vos.Describible;
import common.vos.IKeyLabel;
import common.vos.Restringible;

/**
 * Interfaz para el value object de elemento del cuadro de clasificacion de
 * fondos documentales
 */
public interface ElementoCuadroClasificacionVO extends TreeModelItem,
		Describible, Restringible, IKeyLabel {

	public ElementoCuadroClasificacionVO getParentElement();

	public void setParentElement(ElementoCuadroClasificacionVO padre);

	public String getCodigo();

	public void setCodigo(String codigo);

	public String getCodRefFondo();

	public void setCodRefFondo(String codRefFondo);

	public void setCodReferencia(String codReferencia);

	public String getCodReferencia();

	public void setCodReferenciaPersonalizado(String codReferenciaPersonalizado);

	public void setOrdPos(String ordPos);

	public String getOrdPos();

	/**
	 * Obtiene el código de Referencia concatenado con el Título
	 *
	 * @return
	 */
	public String getCodReferenciaTitulo(String sep);

	/**
	 * Obtiene el código de Referencia concatenado con el Título
	 *
	 * @return
	 */
	public String getCodReferenciaTitulo();

	public int getEstado();

	public void setEstado(int estado);

	public String getEtiqueta();

	public String getId();

	public void setId(String id);

	public String getIdFichaDescr();

	public void setIdFichaDescr(String idFichaDescr);

	public String getIdFondo();

	public void setIdFondo(String idFondo);

	public String getIdNivel();

	public void setIdNivel(String idNivel);

	public String getIdPadre();

	public void setIdPadre(String idPadre);

	public int getTipo();

	public void setTipo(int tipo);

	public String getTitulo();

	public String getCodigoTitulo();

	public void setTitulo(String titulo);

	public boolean isVigente();

	public boolean isTemporal();

	public boolean isNoVigente();

	public void setTienedescr(String tienedescr);

	public String getTienedescr();

	public boolean tieneDescripcion();

	public String getIdArchivo();

	public void setIdArchivo(String idArchivo);

	public String getNombreArchivo();

	public void setNombreArchivo(String nombreArchivo);

	public int getNivelAcceso();

	public void setNivelAcceso(int nivelAcceso);

	public String getIdLCA();

	public void setIdLCA(String idLCA);

	public String getFinalCodRefPadre();

	public void setFinalCodRefPadre(String finalCodRefPadre);

	public String getEditClfDocs();

	public void setEditClfDocs(String editClfDocs);

	public String getIdEliminacion();

	public void setIdEliminacion(String idEliminacion);

	public String getIdRepEcm();

	public void setIdRepEcm(String idRepEcm);

	public int getSubtipo();

	public void setSubtipo(int subtipo);

	public boolean isTipoClasificadorFondo();

	public boolean isTipoClasificadorSerie();

	public boolean isTipoSerie();

	public boolean isTipoFondo();

	public boolean isTipoUnidadDocumental();

	public boolean isSubtipoCaja();

}
