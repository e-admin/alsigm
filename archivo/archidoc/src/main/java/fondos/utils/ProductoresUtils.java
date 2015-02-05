package fondos.utils;

import java.util.Date;

import fondos.FondosConstants;

public class ProductoresUtils {

	public static final int ACCION_GUARDAR = 0;
	public static final int ACCION_PASAR_A_HISTORICO = 1;
	public static final int ACCION_SUSTITUIR_VIGENTE = 2;
	public static final int ACCION_PASAR_A_VIGENTE = 3;

	public static boolean isVinculado(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_VACIO
				|| marcas == FondosConstants.MARCA_ANTERIOR_ESTADO_ACTUAL) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isVigente(int marcas, Date fechaFin) {
		if (isEliminado(marcas))
			return false;

		if (fechaFin == null && !isPasadoAHistorico(marcas)
				&& !isIncorporadoComoHistorico(marcas)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNuevo(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPermitidoPasarAHistorico(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_VACIO
				|| marcas == FondosConstants.MARCA_ANTERIOR_ESTADO_ACTUAL
				|| marcas == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		}

		return false;
	}

	public static boolean isPermitidoReemplazar(int marcas, String idLCA) {
		if (isEliminado(marcas))
			return false;
		if ((marcas == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR || marcas == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_GUARDADO)) {
			return true;
		}
		return false;
	}

	public static String getTipoObjeto(Object clazz) {
		if (clazz != null) {
			return clazz.getClass().getName();
		} else {
			return null;
		}
	}

	public static boolean isPasadoAHistorico(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		}

		return false;
	}

	public static boolean isPermitidoEliminar(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		}

		return false;
	}

	public static boolean isIncorporadoComoHistorico(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		}

		return false;
	}

	public static boolean isSustituidoPorVigente(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO
				|| marcas == FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		}

		return false;
	}

	public static boolean isOriginalVigente(int marcas, Date fechaFin) {
		if (isEliminado(marcas))
			return false;
		if ((marcas == FondosConstants.MARCA_VACIO
				|| marcas == FondosConstants.MARCA_ANTERIOR_ESTADO_ACTUAL
				|| marcas == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO || marcas == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR)
				&& fechaFin == null) {
			return true;
		}
		return false;
	}

	public static String getTextoMarcas(int marcas) {
		return FondosConstants.getTextoMarcas(marcas);

	}

	public static boolean isSinGuardar(int marcas) {
		if (isEliminado(marcas))
			return false;
		if (marcas == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR
				|| marcas == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR
				|| marcas == FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR
				|| marcas == FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR
				|| marcas == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPermitidoPasarAVigente(int marcas) {

		if (isEliminado(marcas))
			return false;

		if (isPasadoAHistorico(marcas)
				|| marcas == FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO

		) {
			return true;
		}

		return false;
	}

	public static boolean isEliminado(int marcas) {
		if (marcas == FondosConstants.MARCA_ELIMINADO) {
			return true;
		}
		return false;
	}

	public static int getMarcaForAction(int marca, int accion) {
		switch (accion) {

		case ACCION_GUARDAR:

			if (marca == FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
				return FondosConstants.MARCA_CREADO_EN_ESTADO_ACTUAL_GUARDADO;
			} else if (marca == FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
				return FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO;
			} else if (marca == FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
				return FondosConstants.MARCA_CREADO_COMO_HISTORICO_EN_ESTADO_ACTUAL_GUARDADO;
			} else if (marca == FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
				return FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO;
			} else if (marca == FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR) {
				return FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_GUARDADO;
			}
			return marca;

		case ACCION_PASAR_A_HISTORICO:
			return FondosConstants.MARCA_PASADO_A_HISTORICO_EN_ESTADO_ACTUAL_SIN_GUARDAR;

		case ACCION_PASAR_A_VIGENTE:
			return FondosConstants.MARCA_PASADO_A_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR;

		case ACCION_SUSTITUIR_VIGENTE:
			return FondosConstants.MARCA_SUSTITUIDO_POR_VIGENTE_EN_ESTADO_ACTUAL_SIN_GUARDAR;

		default:
			return marca;

		}
	}

	/**
	 * @param marcas
	 * @param fechaFin
	 * @return
	 */
	public static boolean isHistorico(int marcas, Date fechaFin) {
		if (isEliminado(marcas))
			return false;

		if (fechaFin != null) {
			return true;
		}
		return false;
	}

	public static boolean isModificado(int marcas) {
		if (isEliminado(marcas))
			return false;

		if (!isNuevo(marcas) && isSinGuardar(marcas)) {
			return true;
		}
		return false;
	}
}