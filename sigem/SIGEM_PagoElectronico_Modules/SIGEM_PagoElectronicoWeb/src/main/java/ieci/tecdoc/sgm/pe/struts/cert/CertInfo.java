
package ieci.tecdoc.sgm.pe.struts.cert;

import java.security.cert.X509Certificate;
import java.util.Date;

public class CertInfo 
{
   private int     m_version;
   private String  m_issuer;         // Formato OU=FNMT Clase 2 CA, O=FNMT, C=ES
   private String  m_issuer2;        // Formato C=ES, O=FNMT, OU=FNMT Clase 2 CA
   private String  m_serialNumber;   // En mayúsculas
   private String  m_subject;
   private Date    m_notBefore;
   private Date    m_notAfter;
   private String  m_nombre;
   private String  m_nif;
   private String  m_sessionId;
   
   private X509Certificate  m_cert;

public X509Certificate getM_cert() {
	return m_cert;
}

public void setM_cert(X509Certificate m_cert) {
	this.m_cert = m_cert;
}

public String getM_issuer() {
	return m_issuer;
}

public void setM_issuer(String m_issuer) {
	this.m_issuer = m_issuer;
}

public String getM_issuer2() {
	return m_issuer2;
}

public void setM_issuer2(String m_issuer2) {
	this.m_issuer2 = m_issuer2;
}

public String getM_nif() {
	return m_nif;
}

public void setM_nif(String m_nif) {
	this.m_nif = m_nif;
}

public String getM_nombre() {
	return m_nombre;
}

public void setM_nombre(String m_nombre) {
	this.m_nombre = m_nombre;
}

public Date getM_notAfter() {
	return m_notAfter;
}

public void setM_notAfter(Date after) {
	m_notAfter = after;
}

public Date getM_notBefore() {
	return m_notBefore;
}

public void setM_notBefore(Date before) {
	m_notBefore = before;
}

public String getM_serialNumber() {
	return m_serialNumber;
}

public void setM_serialNumber(String number) {
	m_serialNumber = number;
}

public String getM_subject() {
	return m_subject;
}

public void setM_subject(String m_subject) {
	this.m_subject = m_subject;
}

public int getM_version() {
	return m_version;
}

public void setM_version(int m_version) {
	this.m_version = m_version;
}

public String getM_sessionId() {
	return m_sessionId;
}

public void setM_sessionId(String id) {
	m_sessionId = id;
}
   
}
