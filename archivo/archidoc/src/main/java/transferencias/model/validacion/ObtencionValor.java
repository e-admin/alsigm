package transferencias.model.validacion;

import common.db.DbDataSource;

/**
 * Interfaz para la obtencion de datos a incorporar a la descripcion de unidad
 * documental durante la validacion de la misma
 * 
 */
public interface ObtencionValor {

	public String obtenerValor(DbDataSource dataSource,
			String idUnidadDocumental);

}