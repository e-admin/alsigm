package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;

/*
 La versión 6.0 de @firma depreca estos métodos.
    1. Obtener Bloques
    2. Validar Firma Bloques Completo
    3. Validar Firma Bloques Documento
    4. Firma Usuario Bloques F1
    5. Firma Usuario Bloques F3
    6. Obtener Identificadores Documentos Bloque Firmas
    7. Obtener Identificadores Documentos Bloque Firmas Backwards
    8. Obtener Información Bloque Firmas
    9. Obtener Información Bloque Firmas Backwards
   10. Obtener Información Completa Bloque Firmas
   11. Obtener Transacciones Por Referencia
   12. Obtener Transacciones Por Fecha
   13. Obtener Transacciones
   14. Obtener transacciones por fecha
   15. Firma Usuario 3 Fases F1
   16. Firma Usuario 3 Fases F1 CoSign
   17. Firma Usuario 3 Fases F1 CounterSign
   18. Firma Usuario 3 Fases F3
   19. Firma Usuario 2 Fases F2
 */

public class SignManagerAFirma6Impl extends SignManagerAFirmaImpl implements ServicioFirmaDigital{
	private ServicioFirmaDigital conectorFirmaInterno;

	//La versión 6.0 de @firma depreca FirmaUsuario2FasesF2
	public String registrarFirma(byte[] signatureValue,	byte[] signCertificateValue, byte[] hash) throws FirmaDigitalException {
		return null;
	}

	public String firmar(byte[] pbaB64Source) throws FirmaDigitalException {
		return conectorFirmaInterno.firmar(pbaB64Source);
	}

	public CertificadoX509Info getcertInfo() throws FirmaDigitalException {
		return conectorFirmaInterno.getcertInfo();
	}




	public void setConectorFirmaInterno(ServicioFirmaDigital conectorFirmaInterno){
		this.conectorFirmaInterno=conectorFirmaInterno;
	}
}
