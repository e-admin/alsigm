package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.firmardocumento;

import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Resultado;

import java.util.List;
import java.util.ArrayList;

/* $Id: ResultadoFirmarDocumento.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class ResultadoFirmarDocumento extends Resultado {

    private Firma firma;


    public Firma getFirma() {
	return firma;
    }
    public void setFirma(Firma firma) {
	this.firma=firma;
    }
    
}
