package transferencias.model;

import java.util.ArrayList;
import java.util.List;

import transferencias.TipoSignaturacionConstants;

import common.util.ListUtils;

/**
 * Posibles tipos de signaturación por archivo
 */
public class TipoSignaturacion extends TipoSignaturacionConstants {

	private static final List listaTiposSignaturacion = new ArrayList();

	public static List getTiposSignaturacion() {
		if (ListUtils.isEmpty(listaTiposSignaturacion)) {
			for (int i = 0; i < tiposSignaturacion.length; i++) {
				listaTiposSignaturacion.add(tiposSignaturacion[i]);
			}
		}
		return listaTiposSignaturacion;
	}
}