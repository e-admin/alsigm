package descripcion.vos;

import common.vos.IKeyId;

public interface ICampoVO extends IKeyId {

	public String getId();

	public void setId(String id);

	public int getTipo();

	public void setTipo(int tipo);

	public String getNombre();

	public void setNombre(String nombreCampo);

	public String getEtiquetaXml();

	public void setEtiquetaXml(String etiquetaXml);

	public String getEtiqXmlFila();

	public void setEtiqXmlFila(String etiqXmlFila);

	public void setNombreArea(String nombreArea);

	public void setIdArea(String idArea);

	public String getIdTblPadre();

	public void setEtiquetaXmlTabla(String etiquetaXml);

	public void setNombreTabla(String nombre);

	public String getTipoText();

	public void setTipoText(String tipoText);

	public int getTipoNorma();

	public void setTipoNormaText(String tipoNormaText);

	public String getEtiquetaXmlText();

	public String getNombreTabla();

	public boolean isTextoCorto();

	public boolean isTextoLargo();

	public boolean isFecha();

	public boolean isNumerico();

	public boolean isReferencia();

}
