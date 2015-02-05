package es.ieci.tecdoc.fwktd.sir.api.dao;

import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.ContadorVO;

/**
 * Interfaz de los DAOs de contadores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ContadorDao {

	/**
	 * Obtiene el número de contadores.
	 * @return Número de contadores.
	 */
	public int count();

	/**
	 * Obtiene la información del contador.
	 * @param codigoEntidadRegistral Código de la entidad registral.
	 * @param tipoContador Tipo de contador.
	 * @return Información del contador.
	 */
    public ContadorVO get(String codigoEntidadRegistral, TipoContadorEnum tipoContador);

	/**
	 * Guarda la información del contador.
	 * @param contador Información del contador.
	 * @return Información del contador creado.
	 */
    public ContadorVO save(ContadorVO contador);

	/**
	 * Actualiza la información del contador.
	 * @param contador Información del contador.
	 * @return Información del contador actualizado.
	 */
    public ContadorVO update(ContadorVO contador);

	/**
	 * Elimina la información del contador.
	 * @param codigoEntidadRegistral Código de la entidad registral.
	 * @param tipoContador Tipo de contador.
	 */
    public void delete(String codigoEntidadRegistral, TipoContadorEnum tipoContador);

    /**
     * Elimina la información de todos los contadores.
     */
    public void deleteAll();

}
