package transferencias.model.validacion;

import java.util.List;

import org.dom4j.Document;

/**
 * Intefaz que deben presentar las clases mediante las que se implementan las
 * comprobaciones a realizar durante el proceso de generacion de la descripcion
 * de una unidad documental que se realiza cuando esta es validada
 */
public interface ComprobacionDatosDescripcion {

	/**
	 * Metodo en el que se implementa la comprobacion
	 * 
	 * @param listaDatos
	 *            Lista de definiciones de datos que deben ser verificados
	 * @param udocInfo
	 *            Documento xml con informacion de la unidad documental en
	 *            validacion
	 * @return <b>true</b> si la comprobacion realizada da como resultado que
	 *         los datos pueden ser incorporados a la descripcion de la unidad
	 *         documental y <b>false</b> en caso contrario
	 */
	public boolean datosValidos(List listaDatos, Document udocInfo);
}