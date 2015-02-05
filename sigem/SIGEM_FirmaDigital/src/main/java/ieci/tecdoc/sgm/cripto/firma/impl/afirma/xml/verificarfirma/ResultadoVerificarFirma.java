package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Resultado;

import java.util.List;
import java.util.ArrayList;

/* $Id: ResultadoVerificarFirma.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class ResultadoVerificarFirma extends Resultado {

    private List firmantes=new ArrayList();


    public List getFirmantes() {
	return firmantes;
    }
    public void setFirmantes(List firmantes) {
	this.firmantes=firmantes;
    }
    public void addFirmante(Firmante firmante) {
	firmantes.add(firmante);
    }
}
