package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.TipoObjetoUsuario;
import descripcion.vos.UsoObjetoVO;

/**
 * Interfaz de comportamiento para la entidad de uso de objetos. <br>
 * Entidad: <b>ADUSOOBJETO</b>
 */
public interface IUsoObjetoDBEntity extends IDBEntity {

	public UsoObjetoVO create(UsoObjetoVO usoObjetoVO);

	public void create(List list);

	public void deleteByIdObjUsuario(String[] idsObjUsuario);

	public List getXIdObj(String idObj);

	public List getXIdsObj(String[] idsObj);

	public List getXIdObjUsuario(String idObjUsuario);

	public UsoObjetoVO update(UsoObjetoVO usoObjetoVO);

	public void deleteByIdObjUsuario(String idObjUsuario);

	public UsoObjetoVO getXIdObjUsuarioYTipoObj(String idObjUsuario,
			int idTipoObj);

	/**
	 * Obtien los objetos para un identificador y tipo de objeto
	 *
	 * @param idsObjeto
	 *            Conjunto de cadena que contiene el/los identificador(es) del
	 *            objeto
	 * @param tipoObjeto
	 *            Entero que contiene el tipo de objeto
	 *            {@link TipoObjetoUsuario}
	 * @return
	 */
	public List getXIdObjYTipo(String[] idsObjeto, int tipoObjeto);

}
