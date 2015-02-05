package es.ieci.tecdoc.isicres.legacy.ws.manager;

import javax.xml.datatype.XMLGregorianCalendar;

import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamField;
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

/**
 * 
 * @author IECISA
 * 
 */
public interface ISWebServiceRegistersManager {

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param documents
	 * @param security
	 * @return
	 */
	public WSDocumentsResponse attachPage(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param documents
	 * @param security
	 */
	public void attachPageEx(int bookIdentification,
			int registerIdentification, ArrayOfWSParamDocument documents,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param security
	 */
	public void cancelInputRegister(int bookIdentification,
			int registerIdentification, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param security
	 */
	public void cancelOutputRegister(int bookIdentification,
			int registerIdentification, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param documentIndex
	 * @param pageIndex
	 * @param security
	 * @return
	 */
	public byte[] getPageByIndex(int bookIdentification,
			int registerIdentification, int documentIndex, int pageIndex,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param documentId
	 * @param pageId
	 * @param security
	 * @return
	 */
	public byte[] getPageById(int bookIdentification,
			int registerIdentification, int documentId, int pageId,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param regNumber
	 * @param regDate
	 * @param user
	 * @param sysDate
	 * @param office
	 * @param datas
	 * @param security
	 * @return
	 */
	public WSRegister importInputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamInputRegisterEx datas, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param regNumber
	 * @param regDate
	 * @param user
	 * @param sysDate
	 * @param office
	 * @param datas
	 * @param security
	 * @return
	 */
	public WSRegister importOutputRegister(int bookIdentification,
			String regNumber, XMLGregorianCalendar regDate, String user,
			XMLGregorianCalendar sysDate, String office,
			WSParamOutputRegisterEx datas, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param security
	 * @return
	 */
	public WSInputRegister loadInputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param condition
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	public WSInputRegistersResponse loadInputRegisters(int bookIdentification,
			String condition, int initValue, int size, Security security);

	/**
	 * 
	 * @param parameters
	 * @param security
	 * @return
	 */
	public WSLoadInputRegistersExResponse loadInputRegistersExOp(
			WSLoadInputRegistersEx parameters, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param security
	 * @return
	 */
	public WSOutputRegister loadOutputRegisterFromId(int bookIdentification,
			int registerIdentification, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param condition
	 * @param initValue
	 * @param size
	 * @param security
	 * @return
	 */
	public WSOutputRegistersResponse loadOutputRegisters(
			int bookIdentification, String condition, int initValue, int size,
			Security security);

	/**
	 * 
	 * @param parameters
	 * @param security
	 * @return
	 */
	public WSLoadOutputRegistersExResponse loadOutputRegistersExOp(
			WSLoadOutputRegistersEx parameters, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param datas
	 * @param security
	 * @return
	 */
	public WSRegister newInputRegister(int bookIdentification,
			WSParamInputRegisterEx datas, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param datas
	 * @param security
	 * @return
	 */
	public WSRegister newOutputRegister(int bookIdentification,
			WSParamOutputRegisterEx datas, Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param fields
	 * @param security
	 */
	public void updateInputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security);

	/**
	 * 
	 * @param bookIdentification
	 * @param registerIdentification
	 * @param fields
	 * @param security
	 */
	public void updateOutputRegister(int bookIdentification,
			int registerIdentification, ArrayOfWSParamField fields,
			Security security);

}
