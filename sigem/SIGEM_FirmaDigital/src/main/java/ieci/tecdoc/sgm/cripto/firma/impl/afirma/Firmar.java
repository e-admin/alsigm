package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

/**
 * $Id: Firmar.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $
 *
 * interfaz del servicio firmar
 */

public interface Firmar {
    public String firmar(byte[] in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public String verificar(byte[] in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
}
