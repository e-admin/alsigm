package ieci.tecdoc.sgm.autenticacion.util;
import java.io.Serializable;

import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Clase que representa a un solicitante del sistema.
 *
 * @author IECISA
 *
 */
public class Solicitante implements Serializable {

	/**
	 * Constructor de la clase Solicitante
	 *
	 */
   public Solicitante()
   {
       id = "";
       name = "";
       firstName = "";
       surName = "";
       surName2 = "";
       email = "";
       certificateIssuer = "";
       certificateSN = "";
       inQuality ="";
       socialName ="";
       CIF ="";
       //additionalInfo = "";
   }

   /**
    * Devuelve el email de un usuario.
    * @return El email.
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * Establece el email para un usuario.
    * @param email El email a establecer.
    */
   public void setEmail(String email)
   {
      this.email = email;
   }

   public void setInQuality(String inQuality){
      this.inQuality = inQuality;
   }

   /*
   public void setAdditionalInfo(String additionalInfo)
   {
        this.additionalInfo = additionalInfo;
   }
   */

   /**
    * Devuelve el identificador de un usuario.
    * @return identificador de un usuario
    */
   public String getId()
   {
        return id;
   }

   /**
    * Establece el identificador para un usuario.
    * @param id Identificador de usuario
    */
   public void setId(String id)
   {
        this.id = id;
   }

   /**
    * Devuelve el tipo de documento de identidad
    * @return El tipo de ocumento de identidad
    */
   public int getIdentityType() {
      return Validador.validateDocumentType(id);
   }

//   /**
//    * @return Returns the senderSession.
//    */
//   public SenderSession getSenderSession() {
//       return senderSession;
//   }

//   /**
//    * @param senderSession The senderSession to set.
//    */
//   public void setSenderSession(SenderSession senderSession) {
//       this.senderSession = senderSession;
//   }

   /**
    * Devuelve el nombre completo de un usuario
    * @return Nombre de usuario.
    */
   public String getName() {
       return name;
    }

   /**
    * Establece el nombre completo para un usuario
    * @param name Nombre a establecer.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Devuelve el nombre sin apellidos de un usuario
    * @return Nombre de usuario.
    */
   public String getFirstName() {
       return firstName;
   }

   /**
    * Establece el nombre sin apellidos para un usuario
    * @param name Nombre a establecer.
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * Devuelve el primer apellido de un usuario
    * @return Apellido de usuario.
    */
   public String getSurName()
   {
        return surName;
   }

   /**
    * Establece el primer apellido para un usuario
    * @param Apellido a establecer.
    */
   public void setSurName(String surName) {
      this.surName = surName;
   }

   /**
    * Devuelve el segundo apellido de un usuario
    * @return Apellido de usuario.
    */
   public String getSurName2()
   {
        return surName2;
   }

   /**
    * Establece el segundo apellido para un usuario
    * @param Apellido a establecer.
    */
   public void setSurName2(String surName2) {
      this.surName2 = surName2;
   }

   /**
    * Establece el emisor del certificado de usuario
    * @param certificateIssuer Emisor del certificado
    */
  public void setCertificateIssuer(String certificateIssuer)
  {
   this.certificateIssuer = certificateIssuer;
  }

  /**
   * Establece el número de serie del certificado de un usuario
   * @param certificateSN Número de serie
   */
  public void setCertificateSN(String certificateSN)
  {
   this.certificateSN = certificateSN;
  }

  /**
   * Devuelve el emisor del certificado de usuario
   * @return Nombre del emisor del certificado
   */
  public String getCertificateIssuer()
  {
     return certificateIssuer;
  }

  /**
   * Devuelve el número de serie del certificado de usuario
   * @return CertificateSN Número de serie de un certificado
   */
  public String getCertificateSN()
  {
     return certificateSN;
  }

  public String getInQuality() {
     return inQuality;
  }

  /**
   * Devuelve el número de documento de identificación del usuario
   * @return Número de documento de identidad
   */
	public String getCIF() {
		return CIF;
	}

	/**
	 * Establece el número de documento de identidad de un usuario
	 * @param cif Número de identidad de un usuario
	 */
	public void setCIF(String cif) {
		CIF = cif;
	}

	/**
	 * Devuelve la razón social de un usuario
	 * @return Razón social
	 */
	public String getSocialName() {
		return socialName;
	}

	/**
	 * Establece la razón social para un usuario
	 * @param socialName Razón social
	 */
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}
  /*
  public String getAdditionalInfo()
  {
     return additionalInfo;
  }
  */

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Sender";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", id);
      bdr.addSimpleElement("Name", name);
      bdr.addSimpleElement("FirstName", firstName);
      bdr.addSimpleElement("SurName", surName);
      bdr.addSimpleElement("SurName2", surName2);
      bdr.addSimpleElement("EMail", email);
      bdr.addSimpleElement("Certificate_Issuer", certificateIssuer);
      bdr.addSimpleElement("Certificate_SN", certificateSN);
      bdr.addSimpleElement("InQualityOf", inQuality);
      bdr.addSimpleElement("Social_Name", socialName);
      bdr.addSimpleElement("CIF", CIF);
      //bdr.addSimpleElement("AdditionalInfo", additionalInfo);

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Método que forma una cadena de texto con la información para el log.
    * @return Cadena de texto con la información para el log.
    */
   public String toLog()
   {
      XmlTextBuilder bdr;
      String tagName = "Cert";

      bdr = new XmlTextBuilder();

      bdr.addSimpleElement("Id", id);
//      bdr.addSimpleElement("Name", name);
//      bdr.addSimpleElement("SurName", surName);
      if ((certificateIssuer != null) && (certificateIssuer.length() >= 0))
      {
         bdr.addOpeningTag(tagName);
         bdr.addSimpleElement("Issuer", certificateIssuer);
         bdr.addSimpleElement("Serial", certificateSN);
         bdr.addClosingTag(tagName);
      }
      //bdr.addSimpleElement("AdditionalInfo", additionalInfo);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString()
   {
      return toXML(false);
   }

    private String id;
    private String name;
    private String firstName;
    private String surName;
    private String surName2;
    private String email;
    private String certificateIssuer;
    private String certificateSN;
    private String inQuality;
    private String socialName;
    private String CIF;
    //private String additionalInfo;
}