package gcontrol.db;

import gcontrol.vos.GrupoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Metodos de recuperacion y almacenamiento en base de datos referentes a grupos
 * de usuarios definidos en el sistema <br>
 * Entidad: <b>ASCAGRUPO</b>
 */
public interface IGrupoDBEntity extends IDBEntity {
	/**
	 * Obtiene los grupos asociados a un determinado archivo de custodia
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 */
	public List getGruposArchivo(String idArchivo);

	/**
	 * Obtiene los grupos existentes
	 * 
	 * @return Listado de grupos existentes
	 */
	public List getGrupos();

	/**
	 * Lista de grupos
	 * 
	 * @param ids
	 * @return
	 */
	public List getGrupos(String ids[]);

	/**
	 * Obtiene un grupo a partir de su identificador
	 * 
	 * @param id
	 *            Identificador del grupo
	 * @return Grupo asociado al id
	 */
	public GrupoVO getGrupo(String id);

	/**
	 * Inserta en la base de datos un grupo
	 * 
	 * @param grupo
	 *            Datos de grupo
	 */
	public void insertGrupo(GrupoVO grupo);

	/**
	 * Actualiza en la base de datos la informacion de un grupo de usuairos
	 * 
	 * @param grupo
	 *            Datos de grupo
	 */
	public void updateGrupo(GrupoVO grupo);

	/**
	 * Elimina de la base de datos grupos de usuario
	 * 
	 * @param grupos
	 *            Lista de identificadores de grupo a eliminar
	 */
	public void eliminarGrupos(String[] grupos);

	/**
	 * @param query
	 * @return
	 */
	public List findByName(String query);

	/**
	 * Obtiene los Grupos por nombre y cuyo id es distinto al que se le pasa.
	 * Para comprobar duplicados
	 * 
	 * @param id
	 *            Identificador del Grupo
	 * @param nombre
	 *            Nombre del Grupo
	 * @return Lista de GrupoVO
	 */
	public List getGruposXNombreConIdDistinto(String id, String nombre);
}