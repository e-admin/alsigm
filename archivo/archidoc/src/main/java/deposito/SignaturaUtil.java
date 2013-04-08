package deposito;

import java.text.DecimalFormat;

import transferencias.db.INSecUIDBEntity;
import transferencias.db.IUIReeaCRDBEntity;
import transferencias.db.IUdocElectronicaDBEntity;
import transferencias.db.IUnidadInstalacionDBEntity;
import transferencias.db.IUnidadInstalacionReeaDBEntity;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.UIReeaCRVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import transferencias.vos.UnidadInstalacionVO;

import common.ConfigConstants;
import common.Constants;

import deposito.db.IHuecoDBEntity;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoVO;

public class SignaturaUtil {

	private static final String FORMATO_SIGNATURA = Constants.FORMATO_SIGNATURA;

	public static final DecimalFormat SIGNATURA_FORMATER = new DecimalFormat(
			FORMATO_SIGNATURA);

	public static long obtenerSignaturaNumerica(String idArchivoReceptor,
			INSecUIDBEntity _nSecUIDBEntity,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity,
			IUIReeaCRDBEntity _unidadInstalacionReeaCrDBEntity,
			IHuecoDBEntity _huecoDBEntity,
			IUdocElectronicaDBEntity _udocElectronicaDBEntity) {

		String signatura = null;
		boolean signaturaValida = false;
		long numSecuencia = -1;
		while (!signaturaValida) {
			numSecuencia = _nSecUIDBEntity.incrementarNumeroSec(1,
					idArchivoReceptor);
			signatura = formatearSignaturaNumerica(numSecuencia);

			UnidadInstalacionVO ui = null;
			UnidadInstalacionReeaVO uiea = null;
			UIReeaCRVO uieacr = null;
			int cnt = 0;

			// Comprobar si se está usando signatura por archivo
			if (ConfigConstants.getInstance().getSignaturacionPorArchivo()) {
				// Signatura por archivo -> Comprobar que no exista la signatura
				// en una unidad de instalación
				// de una relación de entrega con Id archivo receptor = el
				// pasa<do como parámetro, hay que comprobar
				// también las relaciones de transferencias entre archivos ya
				// que se vuelven a signaturar las unidades
				// de instalación. Hay que comprobar también en la ubicación
				// porque se pierde la relación de entrega al
				// compactar
				ui = _unidadInstalacionDBEntity.fetchRowBySignaturaYArchivo(
						signatura, idArchivoReceptor);
				uiea = _unidadInstalacionReeaDBEntity
						.fetchRowBySignaturaYArchivo(signatura,
								idArchivoReceptor);
				uieacr = _unidadInstalacionReeaCrDBEntity
						.fetchRowBySignaturaYArchivo(signatura,
								idArchivoReceptor);

			} else {
				// Signatura común para todos los archivos -> Comprobar que no
				// exista la signatura en una unidad de instalación
				// de una relación de entrega, no hace falta comprobar las
				// unidades de instalación transferidas entre archivos
				// ya que no se cambia la signatura. Hay que comprobar también
				// en la ubicación porque se pierde la relación de entrega al
				// compactar
				ui = _unidadInstalacionDBEntity.fetchRowBySignatura(signatura);
				uieacr = _unidadInstalacionReeaCrDBEntity
						.fetchRowBySignatura(signatura);
			}

			// Buscar en todos los huecos que estén en ubicaciones con el
			// archivo receptor pasado como parámetro
			cnt = _huecoDBEntity.countSignaturaInDeposito(signatura,
					idArchivoReceptor);

			signaturaValida = (ui == null) && (uiea == null)
					&& (uieacr == null) && (cnt == 0);
		}
		return numSecuencia;
	}

	/**
	 * Permite formatear una signatura numérica
	 * 
	 * @param signatura
	 *            Signatura a formatear
	 * @return signatura formateada
	 */
	public static String formatearSignaturaNumerica(long signatura) {

		if (ConfigConstants.getInstance().getFormatearSignaturaNumerica())
			return SIGNATURA_FORMATER.format(signatura);
		else
			return String.valueOf(signatura);
	}

	/**
	 * Permite formatear una signatura numérica
	 * 
	 * @param signatura
	 *            Signatura a formatear
	 * @return signatura formateada
	 */
	public static String formatearSignaturaNumerica(Long signatura) {

		if (signatura != null)
			return formatearSignaturaNumerica(signatura.longValue());
		else
			return null;
	}

	public static boolean existeSignatura(String signatura,
			String idArchivoReceptor,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity,
			IUIReeaCRDBEntity _unidadInstalacionReeaCrDBEntity,
			IHuecoDBEntity _huecoDBEntity, boolean signaturacionPorAchivo) {

		UnidadInstalacionVO ui = null;
		UnidadInstalacionReeaVO uiea = null;
		UIReeaCRVO uieacr = null;

		int cnt = 0;

		// Comprobar si se está usando signatura por archivo
		if (signaturacionPorAchivo) {
			// Signatura por archivo -> Comprobar que no exista la signatura en
			// una unidad de instalación
			// de una relación de entrega con Id archivo receptor = el pasa<do
			// como parámetro, hay que comprobar
			// también las relaciones de transferencias entre archivos ya que se
			// vuelven a signaturar las unidades
			// de instalación. Hay que comprobar también en la ubicación porque
			// se pierde la relación de entrega al
			// compactar
			ui = _unidadInstalacionDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidada(signatura,
							idArchivoReceptor);
			uiea = _unidadInstalacionReeaDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidada(signatura,
							idArchivoReceptor);
			uieacr = _unidadInstalacionReeaCrDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidada(signatura,
							idArchivoReceptor);
		} else {
			// Signatura común para todos los archivos -> Comprobar que no
			// exista la signatura en una unidad de instalación
			// de una relación de entrega, no hace falta comprobar las unidades
			// de instalación transferidas entre archivos
			// ya que no se cambia la signatura. Hay que comprobar también en la
			// ubicación porque se pierde la relación de entrega al
			// compactar
			ui = _unidadInstalacionDBEntity
					.fetchRowBySignaturaEnRENoValidada(signatura);
			uieacr = _unidadInstalacionReeaCrDBEntity
					.fetchRowBySignaturaEnRENoValidada(signatura);
		}

		// Buscar en todos los huecos que estén en ubicaciones con el archivo
		// receptor pasado como parámetro
		cnt = _huecoDBEntity.countSignaturaInDeposito(signatura,
				idArchivoReceptor);

		return !((ui == null) && (uiea == null) && (uieacr == null) && (cnt == 0));
	}

	public static boolean existeSignaturaEnOtraTransferencia(String signatura,
			String idArchivoReceptor, String idRelacion,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity,
			IUIReeaCRDBEntity _unidadInstalacionReeaCrDBEntity,
			IHuecoDBEntity _huecoDBEntity, boolean signaturacionPorAchivo) {

		UnidadInstalacionVO ui = null;
		UnidadInstalacionReeaVO uiea = null;
		UIReeaCRVO uieacr = null;

		// Comprobar si se está usando signatura por archivo
		if (signaturacionPorAchivo) {
			ui = _unidadInstalacionDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
							signatura, idArchivoReceptor, idRelacion);
			uiea = _unidadInstalacionReeaDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
							signatura, idArchivoReceptor, idRelacion);
			uieacr = _unidadInstalacionReeaCrDBEntity
					.fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
							signatura, idArchivoReceptor, idRelacion);
		} else {
			ui = _unidadInstalacionDBEntity
					.fetchRowBySignaturaEnRENoValidadaOtraRelacion(signatura,
							idRelacion);
			uieacr = _unidadInstalacionReeaCrDBEntity
					.fetchRowBySignaturaEnRENoValidadaOtraRelacion(signatura,
							idRelacion);
		}

		return !((ui == null) && (uiea == null) && (uieacr == null));
	}

	public static boolean existeSignaturaDeposito(String signatura,
			String idArchivoReceptor,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity,
			IUIReeaCRDBEntity _unidadInstalacionReeaCrDBEntity,
			IHuecoDBEntity _huecoDBEntity, boolean signaturacionPorAchivo) {

		int cnt = _huecoDBEntity.countSignaturaInDeposito(signatura,
				idArchivoReceptor);

		return !(cnt == 0);
	}

	/**
	 * Permite obtener la unidad de instalación en una relacion a partir de una
	 * signatura
	 * 
	 * @param idRelacion
	 *            Identificador de la relacion
	 * @param signatura
	 *            Signatura a buscar
	 * @param idArchivoReceptor
	 *            Identificador del archivo receptor
	 * @param _unidadInstalacionDBEntity
	 * @param _unidadInstalacionReeaDBEntity
	 * @return Unidad de instalacion
	 */
	public static UnidadInstalacionVO obtenerUIREporSignatura(
			String idRelacion, String signatura, String idArchivoReceptor,
			IUnidadInstalacionDBEntity _unidadInstalacionDBEntity,
			IUnidadInstalacionReeaDBEntity _unidadInstalacionReeaDBEntity) {
		UnidadInstalacionVO ui = _unidadInstalacionDBEntity
				.fetchRowBySignaturaRE(idRelacion, signatura);
		return ui;
	}

	/*
	 * public static UnidadInstalacionVO obtenerUIporSignatura(String signatura,
	 * String idArchivoReceptor, IUnidadInstalacionDBEntity
	 * _unidadInstalacionDBEntity, IUnidadInstalacionReeaDBEntity
	 * _unidadInstalacionReeaDBEntity, IHuecoDBEntity _huecoDBEntity) {
	 * UnidadInstalacionVO ui = null;
	 * 
	 * // Comprobar si se está usando signatura por archivo if
	 * (ConfigConstants.getInstance().getSignaturacionPorArchivo()) { ui =
	 * _unidadInstalacionDBEntity.fetchRowBySignaturaYArchivo(signatura,
	 * idArchivoReceptor);
	 * 
	 * if (ui==null) ui =
	 * _unidadInstalacionReeaDBEntity.fetchRowBySignaturaYArchivo(signatura,
	 * idArchivoReceptor);
	 * 
	 * if (ui==null){ HuecoVO huecoVO =
	 * _huecoDBEntity.getHuecoBySignaturaInDeposito(signatura,
	 * idArchivoReceptor);
	 * 
	 * ui =
	 * _unidadInstalacionDBEntity.fetchRowById(huecoVO.getIduinstalacion());
	 * 
	 * if (ui == null) ui =
	 * _unidadInstalacionReeaDBEntity.fetchRowById(huecoVO.getIduinstalacion());
	 * }
	 * 
	 * } else { ui = _unidadInstalacionDBEntity.fetchRowBySignatura(signatura);
	 * 
	 * if (ui==null){ HuecoVO huecoVO =
	 * _huecoDBEntity.getHuecoBySignaturaInDeposito(signatura,
	 * idArchivoReceptor); ui =
	 * _unidadInstalacionReeaDBEntity.fetchRowById(huecoVO.getIduinstalacion());
	 * } }
	 * 
	 * return ui; }
	 */

	/**
	 * Permite obtener la signatura de una unidad documental a partir de la de
	 * su unidad de instalación, el formato y la parte de la unidad documental
	 * 
	 * @param signaturaUInstalacion
	 *            Signatura de la unidad de instalación
	 * @param formatoRelacion
	 *            formato del hueco
	 * @param parteUDoc
	 *            Parte de la unidad documental
	 * @return signatura de la unidad documental
	 */
	public static String getSignaturaUdoc(String signaturaUInstalacion,
			FormatoHuecoVO formatoRelacion, IParteUnidadDocumentalVO parteUDoc) {
		return getSignaturaUdoc(signaturaUInstalacion, formatoRelacion,
				parteUDoc.getPosUdocEnUI());
	}

	public static String getSignaturaUdoc(String signaturaUInstalacion,
			FormatoHuecoVO formatoRelacion, int posUdocEnUI) {
		if (formatoRelacion.isMultidoc()) {
			return new StringBuffer(signaturaUInstalacion)
					.append(Constants.SEPARADOR_SIGNATURA_UNIDAD_DOCUMENTAL)
					.append(posUdocEnUI).toString();
		} else
			return signaturaUInstalacion;
	}

	/**
	 * Permite obtener el hueco asociado a una signatura cuando la signaturación
	 * es asociada a hueco
	 * 
	 * @param signatura
	 *            Signatura / Numeracion del Hueco
	 * @param idArchivoReceptor
	 *            Identificador del archivo receptor
	 * @param _huecoDBEntity
	 *            Entity de Huecos
	 * @return Hueco asociado a la signatura / numeracion {@link HuecoVO}
	 */
	public static HuecoVO getHuecoAsociadoASignatura(String signatura,
			String idArchivoReceptor, IHuecoDBEntity _huecoDBEntity) {
		return _huecoDBEntity.getHuecoAsociadoNumeracion(idArchivoReceptor,
				signatura);
	}
}
