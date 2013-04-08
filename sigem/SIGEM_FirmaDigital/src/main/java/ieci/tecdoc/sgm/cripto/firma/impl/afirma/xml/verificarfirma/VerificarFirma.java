package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.DocumentoIER;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.Gestion;

/* $Id: VerificarFirma.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class VerificarFirma extends DocumentoIER {

    static {
	// new ResultadoVerificarFirma();

	new Nombre("");
	new NIF("");
	new Emisor("");
	new Asunto("");
	new NumeroSerie("");

	Gestion.getXStream().alias("firmante", Firmante.class);
	Gestion.getXStream().alias("certificado", Certificado.class);

    }
}
