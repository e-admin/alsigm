package ieci.tdw.ispac.ispaclib.sign;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignDocument {

	public final String PROPERTY_NUMEXP = "Nº Expediente";
	public final String PROPERTY_CODIGO_PROCEDIMIENTO = "Código Procedimiento";
	public final String PROPERTY_FASE = "Fase";
	public final String PROPERTY_TRAMITE = "Trámite";
	public final String PROPERTY_ID_DOCUMENTO = "Id Documento";
	public final String PROPERTY_NOMBRE_DOCUMENTO = "Nombre Documento";
	public final String PROPERTY_TIPO_DOCUMENTO = "Tipo Documento";
	public final String PROPERTY_FIRMA = "Firma";
	public final String PROPERTY_CERTIFICADO="Certificado";
	public final String PROPERTY_TIPO_MIME = "Tipo Mime";
	public final String PROPERTY_LONGITUD = "longitud";
	public final String PROPERTY_FECHA_CREACION = "Fecha Creación";
	
	private final String TAG_NAME = "name";
	private final String TAG_VALUE = "value";
	private final String TAG_PROPERTY = "property";
	public static final String TAG_FIRMA = "firma";
	public static final String TAG_CERTIFICADO = "certificado";
	public static final String ATTR_ID = "id";
	public static final String TAG_FIRMAS = "firmas";
	public static final String ATTR_TYPE = "tipo";
	public static final String TAG_CERTIFICADOS="certificados";

	public static final String TYPE_PORTAFIRMAS = "Portafirmas";

	DateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private String numExp;
	private int idPcd;
	private String stage;
	private String taskName;
	private String documentId;
	private String documentName;
	private String documentType;
	private List sign;
	private String mimetype;
	private IItem itemDoc;
	private Date fechaCreacion;
	
	private InputStream document;
	private int length;

	public int getNumSigner() {
		return numSigner;
	}
	public void setNumSigner(int numSigner) {
		this.numSigner = numSigner;
	}
	/**
	 * Hash o resumen del fichero firmado.
	 */
	private String hash;

	/**
	 * Número de firmante del documento, en los casos que no sean una firma mediante un circuito de firma
	 * tendra el valor 1, en otro casos el valor del paso de la firma.
	 */
	private int numSigner=1;

	/**Listado de certicados participantes en la firma del documento*/
	private List signCertificate;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	/**
	 * Obtiene el certificado del ultimo firmante
	 * @return
	 */
	public List getSignCertificate() {
		return signCertificate;
	}
	public void setSignCertificate(List signCertificate) {
		this.signCertificate = signCertificate;
	}
	public SignDocument(IItem itemDoc) throws ISPACException{
		this.itemDoc = itemDoc;
		
		setNumExp(itemDoc.getString("NUMEXP"));
		setDocumentId(itemDoc.getString("ID"));
		setDocumentType(itemDoc.getString("ID_TPDOC"));
		
		String documentName = itemDoc.getString("DESCRIPCION");
		if (StringUtils.isBlank(documentName)) {
			documentName = itemDoc.getString("NOMBRE");
		}
		
		documentName += "." + itemDoc.getString("EXTENSION");
		
		setDocumentName(documentName);
		
		sign = new ArrayList();
		signCertificate=new ArrayList();
	}
	public String getProperties(){
        
		String sXML = null;
		String str;
		String sProperty;
		StringBuffer sProperties = null;

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_NUMEXP);
		str += XmlTag.newTag(TAG_VALUE, numExp);
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties = new StringBuffer(sProperty);

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_CODIGO_PROCEDIMIENTO);
		str += XmlTag.newTag(TAG_VALUE, idPcd);
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FASE);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(stage));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        
        
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TRAMITE);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(taskName ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_ID_DOCUMENTO);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(documentId));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_NOMBRE_DOCUMENTO);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(documentName));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TIPO_DOCUMENTO);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(documentType  ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FIRMA);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(getSignsXml() ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty); 
		
		
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_CERTIFICADO);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(getCertsXml() ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty); 
		
		
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TIPO_MIME);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(mimetype  ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        


		str  = XmlTag.newTag(TAG_NAME, PROPERTY_LONGITUD);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(""+length));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        
		
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FECHA_CREACION);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(mDateFormat.format(fechaCreacion) ));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		
		
		sXML = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", sProperties.toString());		
		return sXML;
    }

	
	public String getSignsXml() {
		String str = null;
		String att = null;
		StringBuffer sProperties = new StringBuffer();
		for (int j = 0; j < sign.size(); j++) {
			att = XmlTag.newAttr(ATTR_ID, ""+j);
			str = XmlTag.newTag(TAG_FIRMA, (String)sign.get(j), att);
			sProperties.append(str);
		}
		return XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag(TAG_FIRMAS, sProperties.toString());
	}
	
	public String getCertsXml() {
		String str = null;
		String att = null;
		StringBuffer sProperties = new StringBuffer();
		for (int j = 0; j < signCertificate.size(); j++) {
			att = XmlTag.newAttr(ATTR_ID, ""+j);
			str = XmlTag.newTag(TAG_CERTIFICADO, (String)signCertificate.get(j), att);
			sProperties.append(str);
		}
		return XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag(TAG_CERTIFICADOS, sProperties.toString());
	}

	public InputStream getDocument() {
		return document;
	}

	public void setDocument(InputStream document) {
		this.document = document;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	
	
	
	public int getIdPcd() {
		return idPcd;
	}

	public void setIdPcd(int idPcd) {
		this.idPcd = idPcd;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public List getSign() {
		return sign;
	}

	public void addSign(String sign) {
		this.sign.add(sign);
	}

	public void addCertificate(String signCertificate) {
		this.signCertificate.add(signCertificate);
	}
	public void setSign(List sign) {
		this.sign = sign;
	}	
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public IItem getItemDoc() {
		return itemDoc;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public void setItemDoc(IItem itemDoc) {
		this.itemDoc = itemDoc;
	}
	public String getSignPropertyName() {
		// TODO Auto-generated method stub
		return null;
	}

}
