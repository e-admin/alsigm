package es.ieci.tecdoc.fwktd.core.locale.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public abstract class LocaleUtilAbstractImpl implements LocaleUtil {
	
	/**
	 * Lista de <code>String</code> que contiene el nombre de las keys de session donde se almacenaran los locales
	 *
	 */
	protected List sessionLocaleKeys;
	
	public LocaleUtilAbstractImpl(List sessionKeys){
		this.sessionLocaleKeys=sessionKeys;
	}
	
	public LocaleUtilAbstractImpl(){
		sessionLocaleKeys= new ArrayList();
	}

	/* 
	 * Método a sobreescribir por cada implementacion para ver que locale se obtiene
	 * (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.core.locale.web.LocaleUtil#getLocale(javax.servlet.http.HttpServletRequest)
	 */
	public Locale getLocale(HttpServletRequest request) {
		Locale result=getCurrentLocale(request);
		return result;
	}

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.core.locale.web.LocaleUtil#setLocale(javax.servlet.http.HttpServletRequest)
	 */
	public void setLocale(HttpServletRequest request) {
		
		Locale locale= parseLocaleFromRequest(request);
		this.setLocale(request, locale);
	}
	
	/**
	 * Metodo que indicará cual es el actual locale en uso
	 * @param request
	 * @return
	 */
	protected abstract Locale getCurrentLocale(HttpServletRequest request);
	
		
	
	/**
	 * metodo que obtiene el locale a partir de un parametro de la request
	 * @param request
	 * @return
	 */
	public Locale parseLocaleFromRequest(HttpServletRequest request){
		Locale result=null;
		String localeString =getLocaleString(request);
		
		if (StringUtils.isNotBlank(localeString)) {
			
			String language = "";
			String country = "";
			String variant = "";
			
			 String[] valoresLocale = StringUtils.split(localeString, '_');
			 
			 for (int i = 0; i < valoresLocale.length; i++) {
				if (i==0){
					language=valoresLocale[i];
				}
				if (i==1){
					country=valoresLocale[i];
				}
				if (i==2){
					variant=valoresLocale[i];
				}
			}
				
			result= new Locale(language, country, variant);
		}
		
		return result;
	}
	
	/**
	 * Metodo a sobrescribir que contendra la cadena locale a partir de la request
	 * @return
	 */
	abstract protected String getLocaleString(HttpServletRequest request);

	public void setLocale(HttpServletRequest request, Locale locale) {
		
		HttpSession session = request.getSession();
		
		for (Iterator iterator = sessionLocaleKeys.iterator(); iterator.hasNext();) {
			String localeKey = (String) iterator.next();
			session.setAttribute(localeKey, locale);
			
		}

	}

	public List getSessionLocaleKeys() {
		return sessionLocaleKeys;
	}

	public void setSessionLocaleKeys(List sessionLocaleKeys) {
		this.sessionLocaleKeys = sessionLocaleKeys;
	}

	
	
	

}
