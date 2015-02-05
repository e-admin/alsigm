package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper;

/**
 * Interfaz marcador para clases que se encargan de mapear las propiedades de un
 * objeto en propiedades de otro objeto de distinto tipo.
 * 
 * @author IECISA
 * 
 */
public interface Mapper {

	/**
	 * Devuelve un objeto cuyas propiedades toman los valores del objeto origen
	 * <code>obj</code>.
	 * 
	 * @param obj
	 *            objeto origen del que se toman los valores de las propiedades
	 *            a mapear
	 * @return
	 */
	public Object map(Object obj);
}
