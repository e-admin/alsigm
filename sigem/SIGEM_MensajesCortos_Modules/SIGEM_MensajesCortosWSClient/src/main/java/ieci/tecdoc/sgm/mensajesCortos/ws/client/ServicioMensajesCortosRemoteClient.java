package ieci.tecdoc.sgm.mensajesCortos.ws.client;


import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.Entero;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaBytes;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaString;
import ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String;

public class ServicioMensajesCortosRemoteClient implements ServicioMensajesCortos {

	private MensajesCortosWebService service = null;

	public void setService(MensajesCortosWebService service) {
		this.service = service;
	}

	private MensajesCortosException getMensajesCortosException(IRetornoServicio ret) {
		return new MensajesCortosException(Long.valueOf(ret.getErrorCode()).longValue());
	}


	/**
	 * Obtiene el resguardo del envío de un SMS certificado en formato binario.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * 
	 * @param pwd
	 *            Clave del usuario del conector.
	 * 
	 * @param id
	 *            Identificador del SMS certificado.
	 * 
	 * @return Binario con el resguardo.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public byte[] getCertSMSSignatureDocument(java.lang.String user,
			java.lang.String pwd, java.lang.String id)
			throws MensajesCortosException {
		try {
			ListaBytes oResult = service.getCertSMSSignatureDocument(user, pwd, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getBytes();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Obtiene el estado del proceso de firma del resguardo del envío de un SMS
	 * certificado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 *            
	 * @param pwd
	 *            Clave del usuario del conector.
	 *            
	 * @param id
	 *            Identificador del SMS certificado.
	 *            
	 * @return Estado del proceso de firma.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public int getCertSMSSignatureStatus(java.lang.String user,
			java.lang.String pwd, java.lang.String id)
			throws MensajesCortosException {
		try {
			Entero oResult = service.getCertSMSSignatureStatus(user, pwd, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getValor();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Obtiene el resguardo del envío de un SMS certificado en formato XML.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * 
	 * @param pwd
	 *            Clave del usuario del conector.
	 * 
	 * @param id
	 *            Identificador del SMS certificado.
	 * 
	 * @return XML con el resguardo.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public java.lang.String getCertSMSSignatureXML(java.lang.String user,
			java.lang.String pwd, java.lang.String id)
			throws MensajesCortosException {
		try {
			String oResult = service.getCertSMSSignatureXML(user, pwd, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getValor();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Obtiene el estado de un mensaje SMS enviado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param id
	 *            Identificador del SMS enviado.
	 *            
	 * @return Estado del mensaje.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public int getSMSStatus(java.lang.String user, java.lang.String pwd,
			java.lang.String id) throws MensajesCortosException {
		try {
			Entero oResult = service.getSMSStatus(user, pwd, id);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getValor();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Envía un mensaje SMS certificado
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatario del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @return Identificador del mensaje enviado.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public java.lang.String sendCertSMS(java.lang.String user,
			java.lang.String pwd, java.lang.String src, java.lang.String dst,
			java.lang.String txt, java.lang.String lang)
			throws MensajesCortosException {
		try {
			String oResult = service.sendCertSMS(user, pwd, src, dst, txt, lang);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getValor();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Envía un correo electrónico.
	 * 
	 * @param from
	 *            Dirección de correo elentrónico del remitente.
	 * @param to
	 *            Dirección de correo electrónico del destinatario.
	 * @param cc
	 *            Dirección de correo electrónico de destinatarios en copia.
	 * @param bcc
	 *            Dirección de correo electrónico de destinatarios en copia
	 *            oculta.
	 * @param subject
	 *            Asunto del mensaje.
	 * @param content
	 *            Contenido del mensaje.
	 * @param attachments
	 *            Array de objetos Attachment con el nombre del fichero adjunto
	 *            y el contenido en binario.
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public void sendMail(java.lang.String from, java.lang.String[] to,
			java.lang.String[] cc, java.lang.String[] bcc,
			java.lang.String subject, java.lang.String content,
			Attachment[] attachments) throws MensajesCortosException {
		try {
			RetornoServicio oResult = service.sendMail(from, to, cc, bcc, subject, content, attachments);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return;
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
		
	}
	/**
	 * Envía un mensaje SMS no certificado.
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatarios del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * 
	 * @return Identificador del mensaje certificado.
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public java.lang.String sendSMS(java.lang.String user,
			java.lang.String pwd, java.lang.String src, java.lang.String dst,
			java.lang.String txt) throws MensajesCortosException {
		try {
			String oResult = service.sendSMS(user, pwd, src, dst, txt);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getValor();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	/**
	 * Envía un mensaje SMS a varios destinatarios
	 * 
	 * @param user
	 *            Usuario del conector.
	 * @param pwd
	 *            Clave del usuario del conector.
	 * @param src
	 *            Remitente del mensaje
	 * @param dst
	 *            Destinatarios del mensaje en formato internacional.
	 * @param txt
	 *            Texto a enviar.
	 * 
	 * @return Identificadores de los mensajes enviados.
	 * 
	 * @throws MensajesCortosException
	 *             si ocurre algún error.
	 */
	public java.lang.String[] sendSMS(java.lang.String user,
			java.lang.String pwd, java.lang.String src, java.lang.String[] dst,
			java.lang.String txt) throws MensajesCortosException {
		try {
			ListaString oResult = service.sendSMS_Multiple(user, pwd, src, dst, txt);
			if (ServiciosUtils.isReturnOK((IRetornoServicio) oResult)) {
				return oResult.getArray();
			} else {
				throw getMensajesCortosException((IRetornoServicio)oResult);
			}
		} catch (Exception e) {
			throw new MensajesCortosException(MensajesCortosException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	
}
