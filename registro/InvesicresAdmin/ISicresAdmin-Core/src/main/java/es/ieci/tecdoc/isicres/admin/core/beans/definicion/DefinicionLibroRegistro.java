package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.util.ResourceBundle;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;

public interface DefinicionLibroRegistro {

	int LIBRO_ENTRADA = 1;
	int LIBRO_SALIDA = 2;
	ResourceBundle bundle = ResourceBundle.getBundle("es.ieci.tecdoc.isicres.admin.core.manager.rpadmin");

	Archive getBookDefinition(String nombre);

	void makeConstraints(int archiveId, DbConnection db) throws Exception;

	void makeFormats(int archiveId, DbConnection db, String entidad) throws Exception;
}
