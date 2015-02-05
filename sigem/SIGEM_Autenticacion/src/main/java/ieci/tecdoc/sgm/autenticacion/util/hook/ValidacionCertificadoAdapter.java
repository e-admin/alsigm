package ieci.tecdoc.sgm.autenticacion.util.hook;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;

import java.security.cert.X509Certificate;

import sun.misc.BASE64Encoder;

public class ValidacionCertificadoAdapter {

	public CertificadoAutenticacionX509Info verifyCertificate(X509Certificate cert)
		throws Exception {

			ServicioCriptoValidacion oServicio = LocalizadorServicios.getServicioCriptoValidacion();

			BASE64Encoder encoder=new BASE64Encoder();
    	    String psB64Certificate = encoder.encodeBuffer(cert.getEncoded());

			ResultadoValidacion resultado = oServicio.validateCertificate(psB64Certificate);

			return getCertificadoAutenticacionX509Info(resultado);
	}

	private CertificadoAutenticacionX509Info getCertificadoAutenticacionX509Info(ResultadoValidacion resultado) {

		InfoCertificado poCertificado = resultado.getCertificado();

		boolean valido = false;
		if (ResultadoValidacion.VALIDACION_OK.equals(resultado.getResultadoValidacion())) {
			valido = true;
		}

		CertificadoAutenticacionX509Info oCertificado = new CertificadoAutenticacionX509Info();
		if (poCertificado != null) {

			oCertificado.setCIF(poCertificado.getCif());
			oCertificado.setIssuer(poCertificado.getIssuer());
			oCertificado.setName(poCertificado.getName());
			oCertificado.setNIF(poCertificado.getNif());
			oCertificado.setSerialNumber(poCertificado.getSerialNumber());
			// Certificado de persona juridica? (representante legal)
			if (poCertificado.getCorporateName() != null) {
				oCertificado.setSocialName(poCertificado.getCorporateName());
				oCertificado.setLegalEntity(true);
			}
			oCertificado.setFirstName(poCertificado.getFirstname());
			oCertificado.setSurName(poCertificado.getLastname1());
			oCertificado.setSurName2(poCertificado.getLastname2());
			oCertificado.setValid(valido);
		}

		return oCertificado;
	}
}
