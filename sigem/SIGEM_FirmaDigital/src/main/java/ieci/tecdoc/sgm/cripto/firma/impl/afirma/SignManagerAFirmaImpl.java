package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.cripto.firma.Certificado;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.config.AFirmaConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.handlers.Afirma521MessagesHandler;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.handlers.IAfirmaMessagesHandler;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.FirmarDocumento;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento.ResultadoFirmarDocumento;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Codigo;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.InformacionError;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Tipo;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Version;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.registrarFirma.FirmarUsuario;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.registrarFirma.ResultadoFirmarUsuario;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Firmante;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.ResultadoVerificarFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.VerificarFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.config.ConfigLoader;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Encoder;

/**
 * Implementación del servicio de Firma Digital contra los servicios de AFirma.
 *
 */
public class SignManagerAFirmaImpl implements ServicioFirmaDigital {

    private Map configuracionesPorEntidad = new HashMap();

    private IAfirmaMessagesHandler afirmaMessagesHandler = new Afirma521MessagesHandler();

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SignManagerAFirmaImpl.class);

	public IAfirmaMessagesHandler getAfirmaMessagesHandler() {
		return afirmaMessagesHandler;
	}

	public void setAfirmaMessagesHandler(IAfirmaMessagesHandler afirmaMessagesHandler) {
		this.afirmaMessagesHandler = afirmaMessagesHandler;
	}

	public String firmar(byte[] pbaB64Source) throws FirmaDigitalException {

		AFirmaConfiguration config = loadConfig();
		FirmarDocumento fd = new FirmarDocumento();
		fd.setTipo(new Tipo("salida"));
		fd.setVersion(new Version("1.1"));
		InformacionError ie = new InformacionError();
		fd.setInformacionError(ie);

		try {
			// Configuración de seguridad
		    Properties props = config.getConfSeguridad();
			if (props == null) {
			    throw new Exception("no se tiene acceso a las propiedades de seguridad");
			}

			// Almacernar documento
		    CustodiaRemoteServiceLocator crsl = new CustodiaRemoteServiceLocator();
		    crsl.setAlmacenarDocumentoEndpointAddress(config.getPathServicios()+"/"+config.getNombreServicioAlmacenarDocumento());

			// Manejador para securizar los mensajes SOAP a partir de la configuración de seguridad de la entidad
		    HandlerAFirma handler = new HandlerAFirma(props);

		    CustodiaRemote cr = crsl.getAlmacenarDocumento();
		    ((AlmacenarDocumentoSoapBindingStub)cr).setHandler(handler);

		    BASE64Encoder encoder = new BASE64Encoder();
		    String b64 = encoder.encodeBuffer(pbaB64Source);
		    String param = afirmaMessagesHandler.createRequestAlmacenarDocumento(this, b64);

	    	// Configuración de proxy si existe
    		setProxyConfigurationProperties(props);

		    String res = cr.almacenarDocumento(param);
		    afirmaMessagesHandler.checkResponseAlmacenarDocumento(res);

		    // Firmar
		    FirmaRemoteServiceLocator frsl = new FirmaRemoteServiceLocator();
		    frsl.setFirmaServidorEndpointAddress(config.getPathServicios()+"/"+config.getNombreServicioFirmar());

		    FirmaRemote fr = frsl.getFirmaServidor();
		    ((FirmaServidorSoapBindingStub)fr).setHandler(handler);

		    param = afirmaMessagesHandler.createRequestFirmar(this, res);
		    res = fr.firmaServidor(param);
		    afirmaMessagesHandler.checkResponseFirmar(fd, res);

		    ie.setCodigo(new Codigo("0"));

		} catch (ExcepcionAFirma eaf) {
		    throw new FirmaDigitalException(eaf.getMessage(), eaf);
		} catch(Exception e) {
		    log.error("Error al realizar firma [firmar][Exception]", e.fillInStackTrace());
		    throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }

		return ((ResultadoFirmarDocumento)fd.getResultado()).getFirma().get();
	}

	public ResultadoValidacionFirma validarFirma(byte[] pabB64SourceSigned , byte [] signedContentB64 ) throws FirmaDigitalException {

		AFirmaConfiguration config = loadConfig();
		VerificarFirma vf = new VerificarFirma();
		vf.setTipo(new Tipo("salida"));
		vf.setVersion(new Version("1.1"));
		InformacionError ie = new InformacionError();
		vf.setInformacionError(ie);

		try {
			// Configuración de seguridad
			Properties props = config.getConfSeguridad();
			if (props == null) {
			    throw new Exception("no se tiene acceso a las propiedades de seguridad");
			}

		    FirmaServiceLocator fsl = new FirmaServiceLocator();
		    fsl.setValidarFirmaEndpointAddress(config.getPathServicios()+"/"+config.getNombreServicioVerificar());

		    Firma f = fsl.getValidarFirma();
		    // Manejador para securizar los mensajes SOAP a partir de la configuración de seguridad de la entidad
		    ((ValidarFirmaSoapBindingStub)f).setHandler(new HandlerAFirma(props));

			String param = afirmaMessagesHandler.createRequestVerificar(this, new String(pabB64SourceSigned), new String(signedContentB64));

	    	// Configuración de proxy si existe
    		setProxyConfigurationProperties(props);

		    String res = f.validarFirma(param);
		    afirmaMessagesHandler.checkResponseVerificar(vf, res);

		    ie.setCodigo(new Codigo("0"));

		} catch (ExcepcionAFirma eaf) {
		    throw new FirmaDigitalException(eaf.getMessage(), eaf);
		} catch(Exception e) {
		    log.error("Error al validar firma [validarFirma][Exception]", e.fillInStackTrace());
		    throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }

		return getResultadoValidacionFirma(vf);
	}

	public CertificadoX509Info getcertInfo() throws FirmaDigitalException {

		AFirmaConfiguration config = loadConfig();
		Security.addProvider(new BouncyCastleProvider());
		KeyStore keystore = null;
		CertificadoX509Info certInfo = new CertificadoX509Info();
		ArrayList certList = new ArrayList();
		java.security.cert.Certificate[] certChain = null;

		try {
			keystore = KeyStore.getInstance("PKCS12");
			keystore.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());
			certChain = keystore.getCertificateChain(config.getAlias());
			for (int i = 0; i < certChain.length;i++) {
				certList.add(certChain[i]);
			}
			certInfo.setCert((X509Certificate)keystore.getCertificate(config.getAlias()));
			certInfo.setPrivKey((PrivateKey)(keystore.getKey(config.getAlias(), config.getPassword().toCharArray())));
			certInfo.setCerts(certChain);
			certInfo.setCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));

		} catch(Exception e){
			log.error("Error al obtener información de certificado [getcertInfo][Exception]", e.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_SIGN_EXCEPTION, e.getMessage(), e);
		}

		return certInfo;
	}

	public String registrarFirma(byte[] signatureValue,	byte[] signCertificateValue, byte[] hash) throws FirmaDigitalException {

		AFirmaConfiguration config = loadConfig();
		FirmarUsuario fu = new FirmarUsuario();
		fu.setTipo(new Tipo("salida"));
		fu.setVersion(new Version("1.1"));
		InformacionError ie=new InformacionError();
		fu.setInformacionError(ie);

		try {
			// Configuración de seguridad
			Properties props = config.getConfSeguridad();
			if (props == null) {
			    throw new Exception("no se tiene acceso a las propiedades de seguridad");
			}

		    FirmaUsuarioServiceLocator fsl = new FirmaUsuarioServiceLocator();
		    fsl.setFirmaUsuarioEndpointAddress(config.getPathServicios()+"/"+config.getNombreServicioRegistrarFirma());

		    RegistrarFirma fus = fsl.getFirmaUsuario();
		    // Manejador para securizar los mensajes SOAP a partir de la configuración de seguridad de la entidad
		    ((RegistrarFirmaSoapBindingStub)fus).setHandler(new HandlerAFirma(props));

		    BASE64Encoder encoder = new BASE64Encoder();
			String signatureValueS = encoder.encodeBuffer(signatureValue);
			String signCertificateValueS = encoder.encodeBuffer(signCertificateValue);
			String hashS = encoder.encodeBuffer(hash);
			String param = afirmaMessagesHandler.createRequestFirmarUsuario(this, signatureValueS, signCertificateValueS, hashS);

	    	// Configuración de proxy si existe
    		setProxyConfigurationProperties(props);

		    String res = fus.registrarFirma(param);
		    afirmaMessagesHandler.checkResponseFirmaUsuario(fu, res);

		    ie.setCodigo(new Codigo("0"));

		} catch (ExcepcionAFirma eaf) {
		    throw new FirmaDigitalException(eaf.getMessage(), eaf);
		} catch(Exception e) {
		    log.error("Error al validar firma [validarFirma][Exception]", e.fillInStackTrace());
		    throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
	    }

		return ((ResultadoFirmarUsuario)fu.getResultado()).getIdTransacion();

	}

	protected void setProxyConfigurationProperties(Properties props) {

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
	}

	private ResultadoValidacionFirma getResultadoValidacionFirma(VerificarFirma verificarFirma) {

		// Procesar la respuesta
		ResultadoValidacionFirma oResult = new ResultadoValidacionFirma();
		ResultadoVerificarFirma poResult = (ResultadoVerificarFirma)verificarFirma.getResultado();

		if ((poResult != null) && (poResult.getValida().get() != null) && ("0".equals(poResult.getValida().get()))){
			oResult.setValidationResult(true);
		} else {
			oResult.setValidationResult(false);
		}
		oResult.setSigners(getSigners(verificarFirma));

		return oResult;
	}

	private List getSigners(VerificarFirma verificarFirma) {

		if (verificarFirma == null) {
			return null;
		}

		// Procesar la respuesta
		ResultadoVerificarFirma poResult = (ResultadoVerificarFirma)verificarFirma.getResultado();
		List poFirmantes = poResult.getFirmantes();
		if (poFirmantes == null) {
			return null;
		}

		Iterator oIterator = poFirmantes.iterator();
		List oFirmantes = new ArrayList(poFirmantes.size());
		while (oIterator.hasNext()) {
			oFirmantes.add(getFirmanteServicio((Firmante)oIterator.next()));
		}

		return oFirmantes;
	}

	private ieci.tecdoc.sgm.core.services.cripto.firma.Firmante getFirmanteServicio(Firmante poFirmante) {

		if (poFirmante == null) {
			return null;
		}

		// Procesar la respuesta
		ieci.tecdoc.sgm.core.services.cripto.firma.Firmante oFirmante = new ieci.tecdoc.sgm.core.services.cripto.firma.Firmante();
		if (poFirmante.getNombre() != null) {
			oFirmante.setName(poFirmante.getNombre().get());
		}
		if (poFirmante.getNIF() != null) {
			oFirmante.setNif(poFirmante.getNIF().get());
		}
		if (poFirmante.getCertificado() != null) {
			oFirmante.setCertificate(getCertificadoServicio(poFirmante.getCertificado()));
		}

		return oFirmante;
	}

	private Certificado getCertificadoServicio(ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Certificado poCertificado) {

		if (poCertificado == null) {
			return null;
		}

		Certificado oCertificado = new Certificado();
		oCertificado.setIssuer(poCertificado.getEmisor().get());
		oCertificado.setSerialNumber(poCertificado.getNumeroSerie().get());
		oCertificado.setSubject(poCertificado.getAsunto().get());

		return oCertificado;
	}

	public AFirmaConfiguration loadConfig() throws FirmaDigitalException {

		String idEntidad = MultiEntityContextHolder.getEntity();

		// Cacheo de configuraciones
		AFirmaConfiguration config = (AFirmaConfiguration)configuracionesPorEntidad.get(idEntidad);
		if (config == null) {

			ConfigLoader configLoader = new ConfigLoader();
			config = configLoader.loadAFirmaConfiguration(idEntidad);
			configuracionesPorEntidad.put(idEntidad, config);
		}

		return config;
	}
}
