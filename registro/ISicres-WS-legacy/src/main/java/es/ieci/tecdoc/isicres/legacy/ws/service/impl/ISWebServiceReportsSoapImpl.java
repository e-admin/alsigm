package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceReportsManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ArrayOfWSReport;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.ISWebServiceReportsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.reports.Security;

/**
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceReportsSoapImpl implements ISWebServiceReportsSoap {

	public byte[] wsCreateInputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security) {

		return getIsWebServiceReportsManager().createInputRegisterCertificate(
				bookIdentification, registerIdentification,
				reportIdentification, security);
	}

	public byte[] wsCreateOutputRegisterCertificate(int bookIdentification,
			int registerIdentification, int reportIdentification,
			Security security) {

		return getIsWebServiceReportsManager().createOutputRegisterCertificate(
				bookIdentification, registerIdentification,
				reportIdentification, security);
	}

	public ArrayOfWSReport wsLoadCertificates(int bookIdentification,
			Security security) {

		return getIsWebServiceReportsManager().loadCertificates(
				bookIdentification, security);
	}

	public ISWebServiceReportsManager getIsWebServiceReportsManager() {
		return isWebServiceReportsManager;
	}

	public void setIsWebServiceReportsManager(
			ISWebServiceReportsManager isWebServiceReportsManager) {
		this.isWebServiceReportsManager = isWebServiceReportsManager;
	}

	// Members
	protected ISWebServiceReportsManager isWebServiceReportsManager;
}
