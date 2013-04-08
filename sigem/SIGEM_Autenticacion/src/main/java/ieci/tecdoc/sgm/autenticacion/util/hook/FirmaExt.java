package ieci.tecdoc.sgm.autenticacion.util.hook;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.vo.ReceiptVO;

import java.io.InputStream;

public interface FirmaExt {
	public abstract ReceiptVO sign(InputStream data, String request, String additionalInfo,
			CertificadoFirmaX509Info certificate) throws Exception;
}