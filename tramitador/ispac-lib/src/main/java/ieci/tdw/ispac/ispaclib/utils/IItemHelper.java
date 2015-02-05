package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;

public class IItemHelper {

/**
 * Copia los datos del un participantes a otro salvo el numexp y el id
 * @param origen
 * @param destino
 * @param prefijo
 * @throws ISPACException
 */
public static void copyParticipantes(IItem origen, IItem destino , String prefijo) throws ISPACException{

	destino.set("ID_EXT" , origen.get(prefijo+"ID_EXT"));
	destino.set("TIPO", origen.get(prefijo+"TIPO"));
	destino.set("TIPO_PERSONA", origen.get(prefijo+"TIPO_PERSONA"));
	destino.set("NDOC", origen.get(prefijo+"NDOC"));
	destino.set("NOMBRE", origen.get(prefijo+"NOMBRE"));
	destino.set("DIRNOT", origen.get(prefijo+"DIRNOT"));
	destino.set("EMAIL", origen.get(prefijo+"EMAIL"));
	destino.set("C_POSTAL", origen.get(prefijo+"C_POSTAL"));
	destino.set("LOCALIDAD", origen.get(prefijo+"LOCALIDAD"));
	destino.set("CAUT", origen.get(prefijo+"CAUT"));
	destino.set("TFNO_FIJO", origen.get(prefijo+"TFNO_FIJO"));
	destino.set("TFNO_MOVIL", origen.get(prefijo+"TFNO_MOVIL"));
	destino.set("TIPO_DIRECCION", origen.get(prefijo+"TIPO_DIRECCION"));
	destino.set("DIRECCIONTELEMATICA", origen.get(prefijo+"DIRECCIONTELEMATICA"));
	destino.set("IDDIRECCIONPOSTAL", origen.get(prefijo+"IDDIRECCIONPOSTAL"));
	destino.set("EMAIL", origen.get(prefijo+"EMAIL"));
}



/**
 * Copia de item a newItem las propiedades espedificadas en properties
 * @param item
 * @param newItem
 * @param prefixProperties
 * @param properties
 * @throws ISPACException
 */
public static void copyDatas(IItem item, IItem newItem, String prefixProperties,
		String[] properties) throws ISPACException {
	if (prefixProperties == null) {
		prefixProperties = "";
	}
	for (int i = 0; i < properties.length; i++) {
		newItem.set(properties[i], item.get(prefixProperties + properties[i]));
	}
}

}
