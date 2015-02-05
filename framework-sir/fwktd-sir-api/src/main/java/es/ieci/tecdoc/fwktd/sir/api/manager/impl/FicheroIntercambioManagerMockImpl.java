package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.util.ToStringLoggerHelper;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/** 
 * Implementación MOCK del manager de envío de ficheros de datos de intercambio
 * en formato SICRES 3.0 generado por la aplicación de registro.

 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class FicheroIntercambioManagerMockImpl extends FicheroIntercambioManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FicheroIntercambioManagerMockImpl.class);

	/**
	 * Constructor.
	 */
	public FicheroIntercambioManagerMockImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.impl.FicheroIntercambioManagerImpl#enviar(es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO)
	 */
	protected void enviar(AsientoRegistralVO asiento) {

		logger.warn("Se está utilizando la implementación MOCK de FicheroIntercambioManager !!!");
		
		if (logger.isInfoEnabled()){
			logger.info("Llamada a enviar: asiento=[{}]", ToStringLoggerHelper.toStringLogger(asiento));
		}
		
		// Componer el XML del fichero de intercambio en formato SICRES 3.0
		// con los documentos incluidos en el XML (attached)
		logger.debug("XML fichero de intercambio: {}", getSicresXMLManager()
				.createXMLFicheroIntercambio(asiento, true));
	}

}
