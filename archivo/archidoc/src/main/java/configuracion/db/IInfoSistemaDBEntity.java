package configuracion.db;

import common.db.IDBEntity;
import common.vos.DatosGeograficosVO;
import common.vos.PaisesVO;

/**
 * Entidad: <b>AGINFOSISTEMA</b>
 * 
 * @author IECISA
 * 
 */
public interface IInfoSistemaDBEntity extends IDBEntity {

	/**
	 * Obtiene la Información de Páis Comunidad y Provincia.
	 * 
	 * @return
	 */
	public DatosGeograficosVO getDatosGeograficos();

	/**
	 * Obtiene Información de Páises y Comunidades
	 * 
	 * @return
	 */
	public PaisesVO getMapPaises();

	String getManejadorIText();
}
