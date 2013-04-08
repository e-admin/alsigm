
package ieci.tecdoc.sgm.autenticacion.util.hook.cert;

/**
 * Clase que encapsula la información perteneciente a un certificado de
 * autenticación X509.
 *
 * @author IECISA
 *
 */
public class CertificadoAutenticacionX509Info
{
   public CertificadoAutenticacionX509Info()
   {
      legalEntity = false;
      valid = false;
      email = "";
      id = "";
      name = "";
      firstName = "";
      surName = "";
      surName2 = "";
      issuer = "";
      serialNumber = "";
      CIF ="";
      socialName ="";
      additionalInfo = "";
      status = "";
      caResponse = "";
   }

   public void setValid(boolean valid)
   {
      this.valid = valid;
   }

   public void setNIF(String id)
   {
      this.id = id;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public void setSurName(String surName)
   {
      this.surName = surName;
   }

   public void setSurName2(String surName2)
   {
      this.surName2 = surName2;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public void setIssuer(String issuer)
   {
      this.issuer = issuer;
   }

   public void setSerialNumber(String serialNumber)
   {
      this.serialNumber = serialNumber;
   }

   public void setAdditionalInfo(String additionalInfo)
   {
      this.additionalInfo = additionalInfo;
   }

   public void setCaResponse(String caResponse)
   {
      this.caResponse = caResponse;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

   public boolean isValid()
   {
      return valid;
   }

   public String getNIF()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public String getSurName()
   {
      return surName;
   }

   public String getSurName2()
   {
      return surName2;
   }

   public String getEmail()
   {
      return email;
   }

   public String getIssuer()
   {
      return issuer;
   }

   public String getSerialNumber()
   {
      return serialNumber;
   }

	public String getCIF() {
		return CIF;
	}
	public void setCIF(String cif) {
		CIF = cif;
	}
	public String getSocialName() {
		return socialName;
	}
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}
   public String getAdditionalInfo()
   {
      return additionalInfo;
   }

   public String getCaResponse()
   {
      return caResponse;
   }

   public String getStatus()
   {
      return status;
   }

   public boolean isLegalEntity()
   {
      return legalEntity;
   }

   public void setLegalEntity(boolean legalEntity)
   {
      this.legalEntity = legalEntity;
   }

   public String toString()
   {

      String ret = "";

      ret = "[ valid = " + valid + "]";
      ret += "[ id = " + id + "]";
      ret += "[ name = " + name + "]";
      ret += "[ firstName = " + firstName + "]";
      ret += "[ surName = " + surName + "]";
      ret += "[ surName2 = " + surName2 + "]";
      ret += "[ email = " + email + "]";
      ret += "[ issuer = " + issuer + "]";
      ret += "[ serialNumber = " + serialNumber + "]";
      ret += "[ additionalInfo = " + additionalInfo + "]";
      ret += "[ status = " + status + "]";
      ret += "[ caResponse = " + caResponse + "]";
      ret += "[ juridical = " + legalEntity + "]";

      return ret;
   }

   protected boolean valid;

   protected String  id;

   protected String  name;

   protected String  firstName;

   protected String  surName;

   protected String  surName2;

   protected String  email;

   protected String  issuer;

   protected String  serialNumber;

   protected String CIF;

   protected String socialName;

   protected String  additionalInfo;

   protected String  status;

   protected String  caResponse;

   private boolean   legalEntity = false;


}