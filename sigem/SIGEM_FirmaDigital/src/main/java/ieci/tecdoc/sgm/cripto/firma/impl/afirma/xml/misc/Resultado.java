package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc;

/* $Id: Resultado.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class Resultado /* extends ieci.tecdoc.sgm.xml.ElementoSinTexto */ {

    static {
	new Valida("");
	new InformacionAuxiliar("");
    }

    /*
      cada implementacion xml usara estos campos de acuerdo a su contexto.
    */
    private Valida valida;
    private InformacionAuxiliar informacionAuxiliar;


    public Valida getValida() {
	return valida;
    }
    public void setValida(Valida valida) {
	this.valida=valida;
    }

    public InformacionAuxiliar getInformacionAuxiliar() {
	return informacionAuxiliar;
    }
    public void setInformacionAuxiliar(InformacionAuxiliar informacionAuxiliar) {
	this.informacionAuxiliar=informacionAuxiliar;
    }

}
