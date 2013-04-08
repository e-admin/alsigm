package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceRegistersManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ISWebServiceRegistersSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocumentsResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

public class ISWebServiceRegistersSoapImpl implements ISWebServiceRegistersSoap {

	/**
	 * {@inheritDoc}
	 */
	public void wsAttachPage(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security) {

		Assert.notNull(documents,
				"No se puede adjuntar una lista de documentos nula");

		getIsWebServiceRegistersManager().attachPageEx(bookIdentification,
				registerIdentification, documents, security);

	}

	/**
	 * {@inheritDoc}
	 */
	public WSDocumentsResponse wsAttachPageEx(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security) {

		Assert.notNull(documents,
				"No se puede adjuntar una lista de documentos nula");

		return getIsWebServiceRegistersManager().attachPage(bookIdentification,
				registerIdentification, documents, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsCancelInputRegister(int bookIdentification,
			int registerIdentification, Security security) {

		getIsWebServiceRegistersManager().cancelInputRegister(
				bookIdentification, registerIdentification, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsCancelOutputRegister(int bookIdentification,
			int registerIdentification, Security security) {

		getIsWebServiceRegistersManager().cancelOutputRegister(
				bookIdentification, registerIdentification, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] wsGetPage(int bookIdentification, int registerIdentification,
			int documentIndex, int pageIndex, Security security) {

		return getIsWebServiceRegistersManager().getPageByIndex(
				bookIdentification, registerIdentification, documentIndex,
				pageIndex, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] wsGetPageEx(int bookIdentification,
			int registerIdentification, int documentId, int pageId,
			Security security) {

		return getIsWebServiceRegistersManager().getPageById(
				bookIdentification, registerIdentification, documentId, pageId,
				security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister wsImportInputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamInputRegisterEx datas, Security security) {

		return getIsWebServiceRegistersManager().importInputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister wsImportOutputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamOutputRegisterEx datas, Security security) {

		return getIsWebServiceRegistersManager().importOutputRegister(
				bookIdentification, regNumber, regDate, user, sysDate, office,
				datas, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSInputRegister wsLoadInputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security) {

		return getIsWebServiceRegistersManager().loadInputRegisterFromId(
				bookIdentification, registerIdentification, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSInputRegistersResponse wsLoadInputRegisters(
			int bookIdentification, String condition, int initValue, int size,
			Security security) {

		return getIsWebServiceRegistersManager().loadInputRegisters(
				bookIdentification, condition, initValue, size, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSLoadInputRegistersExResponse wsLoadInputRegistersExOp(
			WSLoadInputRegistersEx parameters, Security security) {

		return getIsWebServiceRegistersManager().loadInputRegistersExOp(
				parameters, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSOutputRegister wsLoadOutputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security) {

		return getIsWebServiceRegistersManager().loadOutputRegisterFromId(
				bookIdentification, registerIdentification, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSOutputRegistersResponse wsLoadOutputRegisters(
			int bookIdentification, String condition, int initValue, int size,
			Security security) {

		return getIsWebServiceRegistersManager().loadOutputRegisters(
				bookIdentification, condition, initValue, size, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSLoadOutputRegistersExResponse wsLoadOutputRegistersExOp(
			WSLoadOutputRegistersEx parameters, Security security) {

		return getIsWebServiceRegistersManager().loadOutputRegistersExOp(
				parameters, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister wsNewInputRegister(int bookIdentification,
			WSParamInputRegisterEx datas, Security security) {

		return getIsWebServiceRegistersManager().newInputRegister(
				bookIdentification, datas, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSRegister wsNewOutputRegister(int bookIdentification,
			WSParamOutputRegisterEx datas, Security security) {

		return getIsWebServiceRegistersManager().newOutputRegister(
				bookIdentification, datas, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsUpdateInputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security) {

		getIsWebServiceRegistersManager().updateInputRegister(
				bookIdentification, registerIdentification, fields, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsUpdateOutputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security) {

		getIsWebServiceRegistersManager().updateOutputRegister(
				bookIdentification, registerIdentification, fields, security);
	}

	public ISWebServiceRegistersManager getIsWebServiceRegistersManager() {
		return isWebServiceRegistersManager;
	}

	public void setIsWebServiceRegistersManager(
			ISWebServiceRegistersManager isWebServiceRegistersManager) {
		this.isWebServiceRegistersManager = isWebServiceRegistersManager;
	}

	// Members

	protected ISWebServiceRegistersManager isWebServiceRegistersManager;
}
