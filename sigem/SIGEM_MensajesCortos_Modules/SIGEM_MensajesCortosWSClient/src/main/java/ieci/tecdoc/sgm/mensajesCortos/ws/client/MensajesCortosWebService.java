/**
 * MensajesCortosWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.mensajesCortos.ws.client;

public interface MensajesCortosWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.core.services.dto.RetornoServicio sendMail(java.lang.String from, java.lang.String[] to, java.lang.String[] cc, java.lang.String[] bcc, java.lang.String subject, java.lang.String content, ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment[] attachments) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String sendCertSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst, java.lang.String txt, java.lang.String lang) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaString sendSMS_Multiple(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String[] dst, java.lang.String txt) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.Entero getSMSStatus(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String sendSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst, java.lang.String txt) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.Entero getCertSMSSignatureStatus(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String getCertSMSSignatureXML(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaBytes getCertSMSSignatureDocument(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException;
}
