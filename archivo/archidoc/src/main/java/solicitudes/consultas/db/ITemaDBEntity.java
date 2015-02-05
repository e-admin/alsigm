package solicitudes.consultas.db;

import java.util.List;

import solicitudes.consultas.vos.TemaVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGPTEMA</b>
 * 
 * @author IECISA
 * 
 */
public interface ITemaDBEntity extends IDBEntity {
	/**
	 * Obtiene todos los temas existentes para un determinado tipo de entidad
	 * 
	 * @param tipo
	 *            Tipo de la entidad de la que deseamos obtener los temas: -1
	 *            Investigador -2 Ciudadano -3 Organo Externo
	 * @return Listado de los temas
	 */
	public abstract List getTemasTipoEntidad(String tipo);

	/**
	 * Obtiene un listado de temas para un determinado usuario dado por su
	 * identificador
	 * 
	 * @param id
	 *            Identificador del usuario
	 * @return Listado de los temas del usuario
	 */
	public abstract List getTemasUsuario(String id);

	/**
	 * Obtiene un listado de temas para una determinado usuario de consulta en
	 * Sala.
	 * 
	 * @param idUsrCSala
	 *            Identificador del Usuario de Sala.
	 * @return Lista de {@link TemaVO}
	 */
	public List getTemasUsuarioSala(String idUsrCSala);

	/**
	 * Obtiene un listado de temas para un determinado usuario dado por su
	 * identificador y además los temas de los usuarios investigadores
	 * 
	 * @param id
	 *            Identificador del usuario
	 * @return Listado de los temas del usuario
	 */
	public abstract List getTemasUsuarioInvestigador(String id);

	/**
	 * Realiza la insercion de un nuevo tema en la base de datos
	 * 
	 * @param tema
	 *            Tema que se desea insertar
	 */
	public abstract void insertTema(final TemaVO tema);
}