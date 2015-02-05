package transferencias.electronicas.ficha;

import descripcion.vos.ICampoVO;


public interface ICampoFicha {
	public String getEtiqueta();
	public String getValor();

	/**
	 * Establece el valor del campo obtenido de la base de datos
	 * @param campoVO
	 */
	public void setCampoVO(ICampoVO campoVO);

	/**
	 * Obtiene el campoVO asociado al campo ficha.
	 * @return
	 */
	public ICampoVO getCampoVO();

	/**
	 * @return el objeto breadCrumb
	 */
	public String getBaseBreadCrumb();

	/**
	 * @param breadCrumb el objeto breadCrumb a fijar
	 */
	public void setBaseBreadCrumb(String baseBreadCrumb);

	public String getTipoElemento();

	public String getFullBreadCrumb();

	public void setPosicion(int position);

	public String getIdTabla();
}
