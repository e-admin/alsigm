package es.ieci.tecdoc.isicres.api.compulsa.business.manager;

import es.ieci.tecdoc.isicres.api.compulsa.business.vo.ConfiguracionCompulsaVO;
import es.ieci.tecdoc.isicres.api.compulsa.business.vo.DocumentoCompulsarVO;

/**
 * Interfaz para el proceso de compulsa.
 * A partir de un fichero fisico y su firma, se guardan en el repositorio y se genera un nuevo documento a partir del original que 
 * contendra un localizador
 * @author IECISA
 *
 */
public interface CompulsaManager {
	
	/**
	 * Metodo que realiza el proceso de compulsa.
	 * @param documentoCompulsar
	 * @param configuracion
	 */
	public void compulsar(DocumentoCompulsarVO documentoCompulsar,ConfiguracionCompulsaVO configuracion);
	
	

}
