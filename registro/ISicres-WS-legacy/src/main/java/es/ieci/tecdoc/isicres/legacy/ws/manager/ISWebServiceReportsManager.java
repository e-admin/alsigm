package es.ieci.tecdoc.isicres.legacy.ws.manager;

import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ArrayOfWSReport;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.Security;

/**
 * 
 * @author IECISA
 * 
 */
public interface ISWebServiceReportsManager {

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param reportIdentification
	 * @param security
	 * @return
	 */
	byte[] createInputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param reportIdentification
	 * @param security
	 * @return
	 */
	byte[] createOutputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param security
	 * @return
	 */
	ArrayOfWSReport loadCertificates(int bookIdentification, Security security);

}
