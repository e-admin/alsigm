
package ieci.tecdoc.sgm.pe.struts.cert;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

public class Certificate
{
	private static final String DNI_ELECTRONICO = "DNI";
	private static final String FNMT			= "FNMT";

	private static final String  DNI_KEY		= "DNIE";
	private static final String  FNMT_KEY		= "FNMT";
	
	
   public static CertInfo getClientCert(HttpServletRequest request)
   {
      X509Certificate cert = null;
      CertInfo certInfo = new CertInfo();

      Object obj = request.getAttribute("javax.servlet.request.X509Certificate");
      
      if (obj instanceof X509Certificate[])
      {
         X509Certificate[] certArr = (X509Certificate[])obj;
         cert = certArr[0];
      }        
      else
      {
         if(obj instanceof X509Certificate)    
            cert = (X509Certificate)obj;
      }
      
      if (cert == null)
         return null;

      	certInfo.setM_version(cert.getVersion());
      	certInfo.setM_issuer(cert.getIssuerDN().getName());
      	certInfo.setM_issuer2(reverseDn(certInfo.getM_issuer()));
      	certInfo.setM_serialNumber(guion(cert.getSerialNumber().toString(16).toUpperCase()));
      	certInfo.setM_subject(cert.getSubjectDN().getName());
      	certInfo.setM_notBefore(cert.getNotBefore());
      	certInfo.setM_notAfter(cert.getNotAfter());
      	certInfo.setM_cert(cert);
 
      	certInfo = getUserPersonalData(certInfo);      
      return certInfo;
   }
   
   private static String reverseDn(String dn)
   {
      String revDn = "";
      int    index = 0;
      
      while (index != -1)
      {
         index = dn.lastIndexOf(',');
         if (index != -1)
         {
            revDn += dn.substring(index+2, dn.length());
            dn = dn.substring(0, index);
            revDn += ", ";
         }
         else
            revDn += dn;
      }
      
      return revDn;
   }

   private static String guion(String serialNumber)
   {
      String gSerialNumber = "";
      String tmpSerialNumber;
      
      if ((serialNumber.length() % 2) > 0)
         tmpSerialNumber = "0" + serialNumber;
      else
         tmpSerialNumber = serialNumber;
         
      
      while (tmpSerialNumber.length() > 2)
      {
         gSerialNumber += tmpSerialNumber.substring(0, 2);
         gSerialNumber += "-";
         tmpSerialNumber = tmpSerialNumber.substring(2);
      }
      
      gSerialNumber += tmpSerialNumber;
      
      return(gSerialNumber);
   }
   
   private static CertInfo getUserPersonalData(CertInfo poCert){
	   String cTipo = getCertType(poCert);
	   
	   if(FNMT.equals(cTipo)){
	      try {
	          String subjectCN = null;;
	          int    pos = 0;
	          int pos1 = 0;
	          subjectCN = poCert.getM_subject();
	          pos = subjectCN.indexOf("- NIF");
	          pos1 = subjectCN.indexOf(", OU=");
	          poCert.setM_nombre(subjectCN.substring(10, pos-1));
	          poCert.setM_nif(subjectCN.substring(pos+6, pos1));
	      } catch (Throwable e) {
	          int    posCn = 0;
	          int posC = 0;
	          String subjectCN;          
	          subjectCN = poCert.getM_subject();
	          posCn = subjectCN.indexOf("CN=");
	          posC = subjectCN.indexOf("C=");
	          poCert.setM_nif(subjectCN.substring(13, posCn-2));
	          poCert.setM_nombre(subjectCN.substring(posCn+3, posC-2));           
	      }		   
	      return poCert;
	   }else if(DNI_ELECTRONICO.equals(cTipo)){
		      try {
		          String subjectCN = null;;
		          int    pos = 0;
		          int pos1 = 0;
		          subjectCN = poCert.getM_subject();
		          pos = subjectCN.indexOf("CN = ");
		          pos1 = subjectCN.indexOf(" serie = ");
		          poCert.setM_nombre(subjectCN.substring(10, pos-1));
		          poCert.setM_nif(subjectCN.substring(pos+6, pos1));
		      } catch (Throwable e) {
		          int    posCn = 0;
		          int posC = 0;
		          String subjectCN;          
		          subjectCN = poCert.getM_subject();
		          posCn = subjectCN.indexOf("CN=");
		          posC = subjectCN.indexOf("C=");
		          poCert.setM_nif(subjectCN.substring(13, posCn-2));
		          poCert.setM_nombre(subjectCN.substring(posCn+3, posC-2));           
		      }		   		
		      return poCert;
	   }
	   
	   return null;
   }
   
   private static String getCertType(CertInfo poCert){
	   String cEmisor = poCert.getM_issuer();
	   
	   if((cEmisor == null) || ("".equals(cEmisor))){
		   return null;
	   }
	   // Buscamos el textoo FNMT en el asunto para saber si es
	   // un certificado de la fabrica.
	   if(cEmisor.indexOf(FNMT_KEY) > -1){
		   // Es un certificado de la fabrica
		   return FNMT;
	   }else if(cEmisor.indexOf(DNI_KEY) > -1){
		   return DNI_ELECTRONICO;
	   }
	   return null;
   }
}
