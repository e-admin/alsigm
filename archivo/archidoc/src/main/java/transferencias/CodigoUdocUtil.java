package transferencias;

import java.text.DecimalFormat;

import transferencias.db.INSecUDocDBEntity;

import common.ConfigConstants;
import common.Constants;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.vos.ElementoCuadroClasificacionVO;

public class CodigoUdocUtil {

	private static final String FORMATO_CODIGO_UDOC = Constants.FORMATO_COD_UDOC;

	public static final DecimalFormat CODIGO_UDOC_FORMATER = new DecimalFormat(
			FORMATO_CODIGO_UDOC);

	public static int obtenerCodigoUdoc(
			INSecUDocDBEntity _nSecUDocDBEntity,
			IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDbEntity) {

		String codigo = null;
		boolean codigoValido = false;
		int numSecuencia = -1;
		while (!codigoValido) {
			numSecuencia = _nSecUDocDBEntity.incrementarNumeroSec(1);
			codigo = CODIGO_UDOC_FORMATER.format(numSecuencia);
			codigoValido = (existeCodigo(codigo,
					_elementoCuadroClasificacionDbEntity) == false);
		}
		return numSecuencia;
	}

	public static String obtenerCodigoUdocFormateado(
			INSecUDocDBEntity _nSecUDocDBEntity,
			IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDbEntity) {
		int codigo = obtenerCodigoUdoc(_nSecUDocDBEntity,
				_elementoCuadroClasificacionDbEntity);
		return formatearCodigoNumerico(codigo);
	}

	public static boolean existeCodigo(
			String codigo,
			IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDbEntity) {
		ElementoCuadroClasificacionVO elemento = _elementoCuadroClasificacionDbEntity
				.getElementoCFXCodigo(codigo);
		return !(elemento == null);
	}

	/**
	 * Permite formatear un código de unidad documental
	 * 
	 * @param codigo
	 *            Codigo a formatear
	 * @return Codigo formateado
	 */
	public static String formatearCodigoNumerico(int codigo) {

		if (ConfigConstants.getInstance().getFormatearSignaturaNumerica())
			return CODIGO_UDOC_FORMATER.format(codigo);
		else
			return String.valueOf(codigo);
	}
}
