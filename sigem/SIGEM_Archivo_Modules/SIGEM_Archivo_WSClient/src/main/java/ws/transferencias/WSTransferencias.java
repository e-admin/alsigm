/**
 * WSTransferencias.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.transferencias;

public interface WSTransferencias extends java.rmi.Remote {
    public java.lang.Boolean transferirExpedienteElectronicoConDocumentos(java.lang.String codigoTramitador, java.lang.String nombreTramitador, int anioExpediente, java.lang.String codigoProcedimiento, byte[] contenidoXML, int verificarUnicidad, java.lang.String usuario, java.lang.String password, java.lang.String entidad) throws java.rmi.RemoteException;
}
