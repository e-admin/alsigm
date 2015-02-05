package ieci.tdw.ispac.ispacweb.dwr;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterBook;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOffice;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.dwr.vo.SimpleBean;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clase de acceso a los métodos del API de Registro
 *
 */
public class RegisterAPI extends BaseDWR {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(RegisterAPI.class);
	/**
	 * Tipos de registro de salida o entrada
	 */
	private static int TIPO_REGISTRO_SALIDA=1;
	private static int TIPO_REGISTRO_ENTRADA=2;

	
	/**
	 * Obtiene los libros de registro de salida.
	 * @return Libros de registro de salida.
	 * @throws ISPACException si ocurre algún error.
	 */
	public SimpleBean[] getBooks() throws ISPACException {
		
		ISessionAPI sessionAPI = null;
		
		try {
			
			sessionAPI = createSessionAPI();
			
			IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
			IRegisterAPI registerAPI = invesflowAPI.getRegisterAPI();
			
			List books = registerAPI.getBooks(IRegisterAPI.BOOK_TYPE_OUTPUT);
			return getBookBeans(books);

		} catch (ISPACException e) {
			logger.error("Error al obtener los libros de registro", e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener los libros de registro", t);
			throw new ISPACException("Error al obtener los libros de registro", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
	}
	
	protected SimpleBean[] getBookBeans(List books) {
		SimpleBean[] beans = null;
		if (books != null) {
			beans = new SimpleBean[books.size()];
			for (int i = 0; i < books.size(); i++) {
				beans[i] = getBookBean((RegisterBook)books.get(i));
			}
		}
		return beans;
	}
	
	protected SimpleBean getBookBean(RegisterBook book) {
		SimpleBean bean = null;
		if (book != null) {
			bean = new SimpleBean(book.getId(), book.getName());
		}
		return bean;
	}
	
	/**
	 * Obtiene las oficinas de registro dependientes de un libro.
	 * @param bookId Identificador del libro.
	 * @return Oficinas de registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public SimpleBean[] getOffices(String bookId) throws ISPACException {

		ISessionAPI sessionAPI = null;
		
		try {
			
			sessionAPI = createSessionAPI();
			
			IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
			IRegisterAPI registerAPI = invesflowAPI.getRegisterAPI();
			
			List offices = registerAPI.getOffices(bookId);
			return getOfficeBeans(offices);
			
		} catch (ISPACException e) {
			logger.error("Error al obtener las oficinas de registro del libro " + bookId, e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener las oficinas de registro del libro " + bookId, t);
			throw new ISPACException("Error al obtener las oficinas de registro", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
	}
	
	protected SimpleBean[] getOfficeBeans(List offices) {
		SimpleBean[] beans = null;
		if (offices != null) {
			beans = new SimpleBean[offices.size()];
			for (int i = 0; i < offices.size(); i++) {
				beans[i] = getOfficeBean((RegisterOffice)offices.get(i));
			}
		}
		return beans;
	}
	
	protected SimpleBean getOfficeBean(RegisterOffice office) {
		SimpleBean bean = null;
		if (office != null) {
			bean = new SimpleBean(office.getCode(), office.getName());
		}
		return bean;
	}

	
	/**
	 * Obtiene los destinatarios (participantes del expediente).
	 * @param numExp Número de Expediente.
	 * @return Destinatarios.
	 * @throws ISPACException si ocurre algún error.
	 */
	public SimpleBean[] getReceivers(String numExp) throws ISPACException {
		ISessionAPI sessionAPI = null;
		
		try {
			
			sessionAPI = createSessionAPI();
			
			IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			
			
			IItemCollection itemcol = entitiesAPI.getParticipants(numExp, null, null);
			
			
			SimpleBean[] receivers = getReceiversBeans(itemcol.toList());
			SimpleBean[] principal = null;
		
			IItem itemExp = entitiesAPI.getExpedient(numExp);
			if (StringUtils.isNotEmpty(itemExp.getString("IDENTIDADTITULAR"))){
				principal = new SimpleBean[1];
				//principal[0] = new SimpleBean(itemExp.getString("IDTITULAR"), itemExp.getString("IDENTIDADTITULAR"));
				principal[0] = new SimpleBean("0", itemExp.getString("IDENTIDADTITULAR"));
			}

			return (SimpleBean[])ArrayUtils.concat(receivers, principal);			
		} catch (ISPACException e) {
			logger.error("Error al obtener los participantes del expediente " + numExp, e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener los participantes del expediente " + numExp, t);
			throw new ISPACException("Error al obtener los participantes del expediente", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
	}
	
	
	private SimpleBean[] getReceiversBeans(List receivers) throws ISPACException {
		
		SimpleBean[] beans = null;
		if (receivers != null) {
			beans = new SimpleBean[receivers.size()];
			for (int i = 0; i < receivers.size(); i++) {
				IItem item = (IItem)receivers.get(i);
				//beans[i] = new SimpleBean( (item.getString("ID_EXT")!= null) ? item.getString("ID_EXT") : "0", item.getString("NOMBRE")); 
				beans[i] = new SimpleBean(item.getString("ID"), item.getString("NOMBRE"));
			}
		}
		return beans;		
	}

	/**
	 * Obtiene la información de una organización a partir de su código.
	 * @param code Código de la organización.
	 * @return Información de la organización.
	 * @throws ISPACException si ocurre algún error.
	 */
	public SimpleBean getOrganization(String code) throws ISPACException {

		SimpleBean bean = null;
		ISessionAPI sessionAPI = null;
		
		try {

			if (StringUtils.isNotBlank(code)) {

				sessionAPI = createSessionAPI();

				IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
				IRegisterAPI registerAPI = invesflowAPI.getRegisterAPI();
				
				Organization org = registerAPI.getOrganizationByCode(code);
				bean = getOrganizationBean(org);
			}
			
		} catch (ISPACException e) {
			logger.error("Error al obtener la unidad administrativa con código " + code, e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener la unidad administrativa con código " + code, t);
			throw new ISPACException("Error al obtener la unidad administrativa", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
		
		return bean;
	}
	
	protected SimpleBean getOrganizationBean(Organization org) {
		SimpleBean bean = null;
		if (org != null) {
			bean = new SimpleBean(org.getCode(), org.getName());
		}
		return bean;
	}
	
	/**
	 * Obtiene la información de un registro de salida a partir de su número de registro
	 * @param registerNumber Número de registro 
	 * @return Información del registro cuyo número de registro coincida con el que se recibe por parámetro
	 * @throws ISPACException
	 */
	public ieci.tdw.ispac.ispacweb.dwr.vo.Register getOutputRegistryByRegisterNumber (String registerNumber) throws ISPACException{
		
		
		return getRegistryByRegisterNumber(registerNumber, TIPO_REGISTRO_SALIDA);
		
	}

	/**
	 * Obtiene la información de un registro de entrada a partir de su número de registro
	 * @param registerNumber Número de registro 
	 * @return Información del registro cuyo número de registro coincida con el que se recibe por parámetro
	 * @throws ISPACException
	 */
	public ieci.tdw.ispac.ispacweb.dwr.vo.Register getInputRegistryByRegisterNumber (String registerNumber) throws ISPACException{	
		return getRegistryByRegisterNumber(registerNumber, TIPO_REGISTRO_ENTRADA);			
	}

	protected ieci.tdw.ispac.ispacweb.dwr.vo.Register getRegistryByRegisterNumber(String registerNumber, int tipoRegistro)throws ISPACException{
		
		ieci.tdw.ispac.ispacweb.dwr.vo.Register register = null;
		   ISessionAPI sessionAPI = null;
			try {

				if (StringUtils.isNotBlank(registerNumber)) {

					sessionAPI = createSessionAPI();

					IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
					IRegisterAPI registerAPI = invesflowAPI.getRegisterAPI();
					IThirdPartyAPI thirdPartyAPI = invesflowAPI.getThirdPartyAPI();
					
					Register reg =null;
					if(tipoRegistro==TIPO_REGISTRO_SALIDA){
						RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, RegisterType.SALIDA);
						reg=registerAPI.readRegister(registerInfo);
					}
					else{
						RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, RegisterType.ENTRADA);
						reg=registerAPI.readRegister(registerInfo);
					}
					register = getRegister(reg,thirdPartyAPI);
				}
				
			} catch (ISPACException e) {
				logger.error("Error al obtener el registro de salida número " + registerNumber, e);
				throw e;
			} catch (Throwable t) {
				logger.error("Error al obtener el registro de salida número " + registerNumber, t);
				throw new ISPACException("Error al obtener el registro de salida", t);
			} finally {
				releaseSessionAPI(sessionAPI);
			}
		   return register;
	}
	
	protected ieci.tdw.ispac.ispacweb.dwr.vo.Register getRegister(Register reg,
																IThirdPartyAPI thirdPartyAPI) throws ISPACException {
		
		ieci.tdw.ispac.ispacweb.dwr.vo.Register registro = null;
		if (reg != null) {
			registro=new ieci.tdw.ispac.ispacweb.dwr.vo.Register(reg.getRegisterOrigin().getRegisterNumber(),
																 DateUtil.formatDate(reg.getRegisterOrigin().getRegisterDate().getTime()));
			// Origen
			if (reg.getRegisterOrigin() != null) {

				 
				registro.setDateRegister((reg.getRegisterOrigin().getRegisterDate() == null) ? ""
										: DateUtil.formatCalendar(reg.getRegisterOrigin().getRegisterDate()));
				registro.setDateCreationOffice((reg.getRegisterOrigin().getCreationDate() == null) ? ""
												: DateUtil.formatDate(reg.getRegisterOrigin().getCreationDate().getTime()));
				registro.setUser((reg.getRegisterOrigin().getUser() == null) ? "" : reg.getRegisterOrigin().getUser());

				if (reg.getRegisterOrigin().getRegisterOffice() != null) {
					registro.setOffice((reg.getRegisterOrigin().getRegisterOffice().getName() == null) ? "" 
										:reg.getRegisterOrigin().getRegisterOffice().getName());
				
				}
			}

			// Unidades administrativas origen y destino
			if (reg.getOriginOrganization() != null) {

				registro.setUniAdmOrigin((reg.getOriginOrganization().getName() == null) ? ""
										 : reg.getOriginOrganization().getName());
			}
			if (reg.getDestinationOrganization() != null) {

				registro.setUniAdmAddressee((reg.getDestinationOrganization().getName() == null) ? ""
											: reg.getDestinationOrganization().getName());
			}

			// destinatarios y asunto
			if (reg.getRegisterData() != null) {

				if ((reg.getRegisterData().getParticipants() != null)
						&& (reg.getRegisterData().getParticipants().length > 0)) {

					ThirdPerson thirdPerson = reg.getRegisterData()
							.getParticipants()[0];
					String destinatarios = thirdPerson.getName();
					for (int i = 1; i < reg.getRegisterData()
							.getParticipants().length; i++) {
						thirdPerson = reg.getRegisterData()
								.getParticipants()[i];
						destinatarios += ", " + thirdPerson.getName();
					}
					registro.setAddressee((destinatarios == null) ? "" : destinatarios);

				} 
				registro.setSummary((reg.getRegisterData().getSummary() == null) ? "" 
									: reg.getRegisterData().getSummary());

				if (reg.getRegisterData().getSubject() != null) {
					registro.setSubjectType((reg.getRegisterData().getSubject().getName() == null) ? "" 
							: reg.getRegisterData().getSubject().getName());
				
				}
			}

		
         
		}
		return registro;
	}
}
