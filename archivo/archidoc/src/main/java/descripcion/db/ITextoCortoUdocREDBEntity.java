package descripcion.db;

import java.util.List;

/**
 * Interfaz de comportamiento para la entidad de acceso a campos de tipo texto
 * corto en la relacion.
 */
public interface ITextoCortoUdocREDBEntity extends ITextoDBEntity {

	/**
	 * Obtiene la lista de interesados de varias unidades documentales en una
	 * relación de entrega
	 * 
	 * @param idsUDoc
	 *            Identificador de las unidades documentales en la relación
	 * @param tipoUdoc
	 *            Tipo de unidad documental
	 * @return Lista de interesados principales.
	 */
	public List getInteresadosPrincipales(String[] idsUDoc, int tipoUdoc);

}