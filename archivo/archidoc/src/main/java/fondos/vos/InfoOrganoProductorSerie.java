package fondos.vos;

import common.util.StringUtils;

import fondos.utils.ProductoresUtils;

public class InfoOrganoProductorSerie extends InfoProductorSerie {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idEnSistemaExterno = null;
	String sistemaExterno = null;
	String sustituyeAHistorico = null;
	private ProductorSerieVO productorSerieVO = null;

	/**
	 * Indica si el InfoOrganoProductor ya estaba añadido y se ha eliminado sin
	 * guardar.
	 */
	private boolean eliminadoSinGuardar = false;

	public InfoOrganoProductorSerie(int tipoProductor, String sistemaExterno,
			String idEnSistemaExterno, String nombre, String codigo) {
		super(tipoProductor, nombre);
		super.setCodigo(codigo);
		this.idEnSistemaExterno = idEnSistemaExterno;
		this.sistemaExterno = sistemaExterno;
	}

	public String getIdEnSistemaExterno() {
		return idEnSistemaExterno;
	}

	public void setIdEnSistemaExterno(String idEnSistemaExterno) {
		this.idEnSistemaExterno = idEnSistemaExterno;
	}

	public String getSistemaExterno() {
		return sistemaExterno;
	}

	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}

	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj != null)
			if (obj == this)
				returnValue = true;
			else if (obj instanceof InfoOrganoProductorSerie) {
				InfoOrganoProductorSerie objAsProductor = (InfoOrganoProductorSerie) obj;
				returnValue = this.sistemaExterno.equals(objAsProductor
						.getSistemaExterno())
						&& this.idEnSistemaExterno.equals(objAsProductor
								.getIdEnSistemaExterno());
			} else if (obj instanceof InfoProductorSerie) {
				IInfoProductorSerie objAsProductor = (IInfoProductorSerie) obj;
				returnValue = (this.idDescriptor != null)
						&& (this.idDescriptor.equals(objAsProductor
								.getIdDescriptor()));
			}
		return returnValue;
	}

	public String getSustituyeAHistorico() {
		return sustituyeAHistorico;
	}

	public void setSustituyeAHistorico(String sustituyeAHistorico) {
		this.sustituyeAHistorico = sustituyeAHistorico;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idEnSistemaExterno == null) ? 0 : idEnSistemaExterno
						.hashCode());
		result = prime * result
				+ ((sistemaExterno == null) ? 0 : sistemaExterno.hashCode());
		return result;
	}

	public String getGuid() {
		if (StringUtils.isNotEmpty(getIdEnSistemaExterno())) {
			return getIdEnSistemaExterno();
		} else {
			return super.getGuid();
		}
	}

	public String getTipoObjeto() {
		return ProductoresUtils.getTipoObjeto(this);
	}

	public ProductorSerieVO getProductorSerieVO() {

		if (productorSerieVO == null) {

			productorSerieVO = super.getProductorSerieVO();

			productorSerieVO.setIdDescrSistExt(getIdEnSistemaExterno());
			productorSerieVO.setIdSistExt(getSistemaExterno());

		}
		return productorSerieVO;
	}

	public void setEliminadoSinGuardar(boolean eliminadoSinGuardar) {
		this.eliminadoSinGuardar = eliminadoSinGuardar;
	}

	public boolean isEliminadoSinGuardar() {
		return eliminadoSinGuardar;
	}

	public boolean isPermitidoReemplazar() {
		return ProductoresUtils.isPermitidoReemplazar(this.marcas,
				this.getIdLCA());
	}

}
