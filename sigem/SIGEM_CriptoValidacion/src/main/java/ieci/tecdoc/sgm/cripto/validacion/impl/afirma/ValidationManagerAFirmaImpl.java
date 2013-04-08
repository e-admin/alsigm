package ieci.tecdoc.sgm.cripto.validacion.impl.afirma;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.config.AFirmaValidacionConfiguration;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Codigo;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.InformacionError;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.ResultadoValidarCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Tipo;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.ValidarCertificado;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml.Version;
import ieci.tecdoc.sgm.cripto.validacion.impl.config.ConfigValidacionLoader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

public class ValidationManagerAFirmaImpl implements ServicioCriptoValidacion {

	private Map configuracionesPorEntidad = new HashMap();

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ValidationManagerAFirmaImpl.class);

    public String createHash(String psBase64Document) throws CriptoValidacionException {

		try {
			AFirmaValidacionConfiguration config = loadConfig();
		    byte[]res = crearResumenReal(config.getAlgorithm(), config.getProvider(), psBase64Document);
		    BASE64Encoder encoder = new BASE64Encoder();
		    String b64 = encoder.encodeBuffer(res);
		    return b64;
		} catch (Exception e) {
		    log.error("Error al crear hash de documento [createHash] [Exception]", e.fillInStackTrace());
		    throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }
	}

	public ResultadoValidacion validateCertificate(String psB64Certificate)	throws CriptoValidacionException {

		AFirmaValidacionConfiguration config = loadConfig();
		ValidarCertificado vc = new ValidarCertificado();
		vc.setTipo(new Tipo("salida"));
		vc.setVersion(new Version("1.1"));
		InformacionError ie = new InformacionError();
		vc.setInformacionError(ie);

		try {
			// Configuración de seguridad
			Properties props = config.getConfSeguridad();
	    	if (props == null) {
	    	    throw new Exception("no se tiene acceso a las propiedades de seguridad");
	    	}

		    ValidacionRemoteServiceLocator vrsl = new ValidacionRemoteServiceLocator();
		    vrsl.setValidarCertificadoEndpointAddress(config.getPathServicios()+"/"+config.getNombreServicioValidarCertificado());

		    ValidacionRemote vr = vrsl.getValidarCertificado();
		    // Manejador para securizar los mensajes SOAP a partir de la configuración de seguridad de la entidad
		    ((ValidarCertificadoSoapBindingStub)vr).setHandler(new HandlerAFirma(props));

		    String peticion = Conversor.tratarEntradaValidar(psB64Certificate, this);

	    	// Configuración de proxy si existe
	    	if (StringUtils.isNotBlank(props.getProperty("http.proxyHost"))) {

	    		// Nueva propiedad de configuración para el proxy de
	    		// Excepciones del proxy: a list of hosts that should be reached directly, bypassing the proxy.
	    		// This is a list of patterns separated by '|'. The patterns may start or end with a '*' for wildcards.
	    		// Any host matching one of these patterns will be reached through a direct connection instead of through a proxy.
	    		String nonProxyHostsProperty = props.getProperty("http.nonProxyHosts");
	    		if (nonProxyHostsProperty != null) {
	    			System.setProperty("http.nonProxyHosts", nonProxyHostsProperty);
	    		}

				System.setProperty("http.proxyHost", props.getProperty("http.proxyHost"));
				System.setProperty("http.proxyPort", props.getProperty("http.proxyPort"));
				System.setProperty("http.proxyUser", props.getProperty("http.proxyUser"));
				System.setProperty("http.proxyPassword", props.getProperty("http.proxyPassword"));
	    	}

		    String res = vr.validarCertificado(peticion);
		    Conversor.tratarSalidaValidar(vc, res);
		    ie.setCodigo(new Codigo("0"));

		} catch (ExcepcionAFirma eaf) {
		    throw new CriptoValidacionException(eaf.getMessage(), eaf);
	    } catch (Exception e) {
		    log.error("Error al validar certificado de usuario [validateCertificate][Exception]", e.fillInStackTrace());
		    throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }

		return getResultadoValidacionServicio(vc);
	}

	public boolean validateHash(String psBase64Document, String psB64Hash) throws CriptoValidacionException {

		try {
			AFirmaValidacionConfiguration config = loadConfig();
		    String resumenDoc = new String(crearResumenReal(config.getAlgorithm(), config.getProvider(), psBase64Document));
		    if (resumenDoc.equals(psB64Hash)) {
		    	return true;
		    } else {
		    	return false;
		    }
		} catch (Exception e) {
		    log.error("Error al validar hash de documento [validateHash] [Exception]", e.fillInStackTrace());
		    throw new CriptoValidacionException(CriptoValidacionException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }
	}

    private byte[] crearResumenReal(String tipo, String provider, String documento) throws NoSuchAlgorithmException, NoSuchProviderException,CriptoValidacionException {

    	MessageDigest md=MessageDigest.getInstance(tipo, provider);
    	md.update(documento.getBytes());
    	return md.digest();
    }

	private ResultadoValidacion getResultadoValidacionServicio(ValidarCertificado poResult){

		if (poResult == null) {
			return null;
		}

		// Procesar la respuesta
		ResultadoValidacion oResult = new ResultadoValidacion();
		if (ResultadoValidacion.VALIDACION_OK.equals(((ResultadoValidarCertificado)poResult.getResultado()).getValida().get())){
			oResult.setResultadoValidacion(ResultadoValidacion.VALIDACION_OK);
		} else {
			oResult.setResultadoValidacion(ResultadoValidacion.VALIDACION_ERROR);
			oResult.setMensajeValidacion(((ResultadoValidarCertificado)poResult.getResultado()).getInformacionAuxiliar().get());
			return oResult;
		}

		InfoCertificado oInfo = new InfoCertificado();
		try {
			oInfo.setCif(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getCIF().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setCorporateName(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getRazonSocial().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setIssuer(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getEmisor().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setFirstname(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getNombre().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setLastname1(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getApellido1().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setLastname2(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getApellido2().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setName(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getNombreCompleto().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setNif(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getNIF().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setSerialNumber(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getNumeroSerie().get());
		} catch(NullPointerException e){}
		try {
			oInfo.setSubject(((ResultadoValidarCertificado)poResult.getResultado()).getInfoCertificado().getAsunto().get());
		} catch(NullPointerException e){}

		oResult.setCertificado(oInfo);

		return oResult;
	}

	public AFirmaValidacionConfiguration loadConfig() throws CriptoValidacionException {

		String idEntidad = MultiEntityContextHolder.getEntity();

		// Cacheo de configuraciones
		AFirmaValidacionConfiguration config = (AFirmaValidacionConfiguration)configuracionesPorEntidad.get(idEntidad);
		if(config == null) {

			ConfigValidacionLoader configLoader = new ConfigValidacionLoader();
			config = configLoader.loadAFirmaConfiguration(idEntidad);
			configuracionesPorEntidad.put(idEntidad, config);
		}

		return config;
	}
}
