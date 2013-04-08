package transferencias.vos;

import java.util.LinkedList;

import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.model.ValoresFicha;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.CampoNumericoVO;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.ValorCampoGenericoVO;

/**
 * VO para obtener del xml de información de la unidad documental un campo junto
 * con sus valores
 */
public class CampoDescripcionUdocReVO {

	/**
	 * Identificador del campo
	 */
	String idCampo = null;

	/**
	 * Tipo del campo
	 */
	String tipoCampo = null;

	/**
	 * Lista de valores del campo
	 */
	LinkedList listaValoresCampo = new LinkedList();

	/**
	 * Constructor por defecto
	 */
	public CampoDescripcionUdocReVO() {
	}

	/**
	 * Constructor
	 * 
	 * @param idCampo
	 *            identificador del campo
	 * @param listaValoresCampo
	 *            Lista de valores del campo
	 */
	public CampoDescripcionUdocReVO(String idCampo, String tipoCampo,
			LinkedList listaValoresCampo) {
		this.idCampo = idCampo;
		this.listaValoresCampo = listaValoresCampo;
	}

	/**
	 * Devuelve el identificador del campo
	 * 
	 * @return identificador del campo
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * Establece el identificador del campo
	 * 
	 * @param idCampo
	 *            Identificador del campo a establecer
	 */
	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * Devuelve el tipo de campo
	 * 
	 * @return tipo de campo
	 */
	public String getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Devuelve el tipo de campo
	 * 
	 * @return tipo de campo
	 */
	public int getTipoCampoInt() {
		return Integer.parseInt(tipoCampo);
	}

	/**
	 * Establece el tipo de campo
	 * 
	 * @param tipoCampo
	 *            tipo de campo
	 */
	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * Devuelve la lista de valores del campo
	 * 
	 * @return Lista de valores del campo
	 */
	public LinkedList getListaValoresCampo() {
		return listaValoresCampo;
	}

	/**
	 * Permite añadir un valor a la lista de valores del campo
	 */
	public void addValor(ValorCampoDescripcionUdocReVO valor) {

		if (valor != null) {

			// Obtener el tipo del valor
			short tipoValor = TypeConverter.toShort(tipoCampo);

			switch (tipoValor) {
			case ValorCampoGenericoVO.TIPO_TEXTO_CORTO:
			case ValorCampoGenericoVO.TIPO_TEXTO_LARGO:

				CampoTextoVO campoTextoVO = new CampoTextoVO();
				campoTextoVO.setTipo(tipoValor);
				campoTextoVO.setOrden(TypeConverter.toInt(valor.getOrden()));
				campoTextoVO.setValor(getValueIfNullStringValue(valor
						.getValor()));
				listaValoresCampo.add(campoTextoVO);
				break;

			case ValorCampoGenericoVO.TIPO_FECHA:

				CampoFechaVO campoFechaVO = new CampoFechaVO();
				campoFechaVO.setTipo(tipoValor);
				campoFechaVO.setOrden(TypeConverter.toInt(valor.getOrden()));
				campoFechaVO.setValor(valor.getValor());
				campoFechaVO.setCalificador(getValueIfNullStringValue(valor
						.getCalificador()));
				campoFechaVO.setFormato(getValueIfNullStringValue(valor
						.getFormato()));
				campoFechaVO.setSep(getValueIfNullStringValue(valor.getSep()));
				listaValoresCampo.add(campoFechaVO);
				break;

			case ValorCampoGenericoVO.TIPO_NUMERICO:

				CampoNumericoVO campoNumericoVO = new CampoNumericoVO();
				campoNumericoVO.setTipo(tipoValor);
				campoNumericoVO.setOrden(TypeConverter.toInt(valor.getOrden()));
				campoNumericoVO.setValor(TypeConverter.toDouble(valor
						.getValor()));
				campoNumericoVO.setTipoMedida(TypeConverter.toInt(valor
						.getTipoMedida()));
				campoNumericoVO.setUnidadMedida(getValueIfNullStringValue(valor
						.getUnidadMedida()));
				listaValoresCampo.add(campoNumericoVO);
				break;

			case ValorCampoGenericoVO.TIPO_REFERENCIA:

				CampoReferenciaVO campoReferenciaVO = new CampoReferenciaVO();
				campoReferenciaVO.setTipo(tipoValor);
				campoReferenciaVO
						.setOrden(TypeConverter.toInt(valor.getOrden()));
				campoReferenciaVO.setTipoObjRef(TypeConverter.toInt(valor
						.getTipoObjRef()));
				campoReferenciaVO.setIdObjRef(getValueIfNullStringValue(valor
						.getIdObjRef()));
				campoReferenciaVO.setNombre(getValueIfNullStringValue(valor
						.getNombre()));
				listaValoresCampo.add(campoReferenciaVO);
				break;
			}
		}
	}

	/**
	 * Permite obtener el valor String asociado a uno leído del xml de
	 * descripción Si es null o vacío --> el mismo Si es 'vnull' --> null
	 * 
	 * @param valor
	 *            Valor a comprobar
	 * @return Valor procesado
	 */
	private String getValueIfNullStringValue(String valor) {
		if (StringUtils.isEmpty(valor))
			return valor;
		if (ValoresFicha.XML_DESCRIPCION_VALUE_NULL.equals(valor))
			return null;
		return valor;
	}

}
