package es.ieci.tecdoc.isicres.deprecated.ws.manager.impl;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.ApplicationContext;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.utils.RBUtil;

import es.ieci.tecdoc.isicres.api.IsicresSpringAppContext;
import es.ieci.tecdoc.isicres.api.business.helper.LibroHelper;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.deprecated.ws.manager.ISWebServiceImportManager;
import es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.input.FolderToWSParamInputRegisterExMapper;
import es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.input.FolderToWSParamOutputRegisterExMapper;
import es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.input.ListPersonInfoToArrayOfWsParamPersonMapper;
import es.ieci.tecdoc.isicres.deprecated.ws.service.impl.mapper.output.WSRegisterToRegisterInfoMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceRegistersManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.XMLGregorianCalendarHelper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamInputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamOutputRegisterEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSRegister;

public class ISWebServiceImportManagerImpl implements ISWebServiceImportManager {


	private static final String FECHA_REGISTRO_FLD_ID = "2";

	protected ISWebServiceRegistersManager isWebServiceRegistersManager;
	protected LoginManager loginManager;
	protected LibroManager libroManager;

	public RegisterInfo importFolder(UserInfo user, List<PersonInfo> inter,
			Folder folder, Entidad entidad) {

		if(entidad==null)
		{
			entidad = new Entidad();
		}

		ApplicationContext appContext = IsicresSpringAppContext.getApplicationContext();

		UsuarioVO usuario = createUsuarioVO(user.getUserName(), user.getPassword(), user.getAditionalOfic(), entidad.getIdentificador());
		Security security = createSecurityVO(user);

		//Logueamos al usuario para comprobar el tipo de libro
		try{
			usuario = getLoginManager().login(usuario);
		}
		catch (Exception e) {
			throw new RuntimeException("No se ha podido loguear al usuario");
		}
		BaseLibroVO baseLibro = new BaseLibroVO();
		baseLibro.setId(folder.getBookId().getBookId());
		baseLibro = getLibroManager().abrirLibro(usuario, baseLibro);

		AxSf axsf = LibroHelper.getBookFormFormat(usuario, baseLibro);

		WSRegister registro = null;

		int bookId = Integer.valueOf(folder.getBookId().getBookId());
		String numRegistro = folder.getFolderNumber();

		XMLGregorianCalendar sysDate = XMLGregorianCalendarHelper.toXMLGregorianCalendar(GregorianCalendar.getInstance().getTime());
		XMLGregorianCalendar regDate = getRegDate(folder);

		if(axsf==null)
		{
			throw new RuntimeException("El libro no existe");
		}
		try{
			if(axsf instanceof AxSfIn)
			{
				//Si es libro de entrada, llamamos a importar de entrada
				FolderToWSParamInputRegisterExMapper folderToParamsInput = new FolderToWSParamInputRegisterExMapper();
				ListPersonInfoToArrayOfWsParamPersonMapper personInfoToWSParamPersonMapper = new ListPersonInfoToArrayOfWsParamPersonMapper();
				WSParamInputRegisterEx paramsRegistroEntrada = (WSParamInputRegisterEx)folderToParamsInput.map(folder);
				ArrayOfWSParamPerson terceros = (ArrayOfWSParamPerson)personInfoToWSParamPersonMapper.map(inter);
				paramsRegistroEntrada.setPersons(terceros);
				registro = getIsWebServiceRegistersManager().importInputRegister(bookId, numRegistro, regDate, user.getUserName(), sysDate, user.getAditionalOfic(), paramsRegistroEntrada, security);
			}
			else
			{
				FolderToWSParamOutputRegisterExMapper folderToParamsOutput = new FolderToWSParamOutputRegisterExMapper();
				ListPersonInfoToArrayOfWsParamPersonMapper personInfoToWSParamPersonMapper = new ListPersonInfoToArrayOfWsParamPersonMapper();
				WSParamOutputRegisterEx paramsRegistroSalida = (WSParamOutputRegisterEx)folderToParamsOutput.map(folder);
				ArrayOfWSParamPerson terceros = (ArrayOfWSParamPerson)personInfoToWSParamPersonMapper.map(inter);
				paramsRegistroSalida.setPersons(terceros);
				registro = getIsWebServiceRegistersManager().importOutputRegister(bookId, numRegistro, regDate, user.getUserName(), sysDate, user.getAditionalOfic(), paramsRegistroSalida, security);
			}
		}catch (Exception e) {
			throw new RuntimeException("No se ha podido crear el registro");
		}
		WSRegisterToRegisterInfoMapper outMapper = new WSRegisterToRegisterInfoMapper();
		RegisterInfo registerInfo =  (RegisterInfo)outMapper.map(registro);

		try {
			//cerramos la sesion del usuario
			getLoginManager().logout(usuario);
		} catch (Exception e) {
			throw new RuntimeException(
					"No se ha podido cerrar la sesión del usuario");
		}

		return registerInfo;
	}

	private XMLGregorianCalendar getRegDate(Folder folder) {

		SimpleDateFormat longFormatter = new SimpleDateFormat(com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil
			.getInstance(null).getProperty(com.ieci.tecdoc.isicres.desktopweb.Keys.I18N_DATE_LONGFORMAT));

		List<FieldInfo> listaFields = folder.getFields().getFields().getItem();
		XMLGregorianCalendar regDate = null;
		for (FieldInfo fieldInfo : listaFields) {
			String fieldId = fieldInfo.getFieldId();
			String fieldValue = fieldInfo.getValue();

			if(FECHA_REGISTRO_FLD_ID.equals(fieldId))
			{
				try {
					regDate = XMLGregorianCalendarHelper.toXMLGregorianCalendar(longFormatter.parse(fieldValue));
				break;
				}catch (ParseException e) {
					throw new RuntimeException("Error al parsear la fecha de registro");
				}


			}
		}
		return regDate;
	}

	private Security createSecurityVO(UserInfo user) {
		Security security = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername(user.getUserName());
		value.setPassword(user.getPassword());
		value.setOfficeCode(user.getAditionalOfic());
		security.setUsernameToken(value);
		return security;
	}

	private UsuarioVO createUsuarioVO(String userName, String password,
			String idOficina, String idEntidad) {
		UsuarioVO usuario = new UsuarioVO();
		OficinaVO oficina =  new OficinaVO();
		ConfiguracionUsuarioVO config = new ConfiguracionUsuarioVO();
		usuario.setLoginName(userName);
		usuario.setPassword(password);
		config.setIdEntidad(idEntidad);
		oficina.setCodigoOficina(idOficina);
		config.setOficina(oficina);
		usuario.setConfiguracionUsuario(config);
		return usuario;
	}

	public ISWebServiceRegistersManager getIsWebServiceRegistersManager() {
		return isWebServiceRegistersManager;
	}

	public void setIsWebServiceRegistersManager(
			ISWebServiceRegistersManager isWebServiceRegistersManager) {
		this.isWebServiceRegistersManager = isWebServiceRegistersManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public LibroManager getLibroManager() {
		return libroManager;
	}

	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
	}


}
