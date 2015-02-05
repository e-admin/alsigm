package fondos.model;

import ieci.core.db.DbColumnDef;
import fondos.CamposBusquedasConstants;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;

/**
 * Campos para las busquedas
 */
public class CamposBusquedas extends CamposBusquedasConstants {

	/* Mapeos a tablas */
	/*
	 * private static String [] camposEntradaTratamientoDirecto = new String []
	 * { CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE,
	 * CAMPO_ENTRADA_BUSQUEDA_CODIGO, CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA,
	 * CAMPO_ENTRADA_BUSQUEDA_TITULO};
	 * 
	 * private static DbColumnDef [] camposEntradaTratamientoDirectoMappings =
	 * {UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
	 * ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
	 * ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
	 * ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD };
	 * 
	 * public static int esCampoEntradaTratamientoDirecto(String campo) { int
	 * ret = -1;
	 * 
	 * for (int i=0; i<camposEntradaTratamientoDirecto.length; i++) if
	 * (camposEntradaTratamientoDirecto[i].equals(campo)) { ret = i; break; }
	 * 
	 * return ret; }
	 * 
	 * public static DbColumnDef getCampoEntradaTratamientoDirecto(int i) {
	 * return camposEntradaTratamientoDirectoMappings[i]; }
	 */

	private static String[] camposSalidaTratamientoDirecto = new String[] {
			CamposBusquedasConstants.CAMPO_SALIDA_BUSQUEDA_CODIGO,
			// CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE,
			CamposBusquedasConstants.CAMPO_SALIDA_BUSQUEDA_TITULO,
			CamposBusquedasConstants.CAMPO_SALIDA_BUSQUEDA_ESTADO,
			CamposBusquedasConstants.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA // ,
	// CAMPO_SALIDA_BUSQUEDA_SIGNATURA
	};

	private static DbColumnDef[] camposSalidaTratamientoDirectoMappings = {
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
			// ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
			// UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
			ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
			ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_FIELD,
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD // ,
	// ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD
	};

	public static int esCampoSalidaTratamientoDirecto(String campo) {
		int ret = -1;
		for (int i = 0; i < camposSalidaTratamientoDirecto.length; i++)
			if (camposSalidaTratamientoDirecto[i].equals(campo)) {
				ret = i;
				break;
			}

		return ret;
	}

	public static DbColumnDef getCampoSalidaTratamientoDirecto(int i) {
		return camposSalidaTratamientoDirectoMappings[i];
	}

}