package com.ieci.tecdoc.isicres.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;

import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.web.util.CurrentUserSessionContextUtil;

public class ContextoAplicacionUtil {

	/**
	 * Metodo para obtener el contexto de aplicacion
	 * @param req
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public static ContextoAplicacionVO getContextoAplicacion(HttpServletRequest req) throws SessionException, TecDocException{
		ContextoAplicacionVO result = null;
		//obtenemos y seteamos el contexto de aplicacion para usar en el api
		CurrentUserSessionContextUtil currentUserSessionContextUtil= new CurrentUserSessionContextUtil();
		result = currentUserSessionContextUtil.getContextoAplicacionActual(req);
		return result;
	}
	
	
	/**
	 * Metodo para obtener el locale del usuario actual
	 * @param usuario
	 * @return
	 */
	public static  Locale getCurrentLocale(UsuarioVO usuario){
		Locale result=null;
		if (usuario!=null && usuario.getConfiguracionUsuario()!=null &&usuario.getConfiguracionUsuario().getLocale()!=null){
			result = usuario.getConfiguracionUsuario().getLocale();
		}else{
			result= Locale.getDefault();
		}
		return result;
	}
}
