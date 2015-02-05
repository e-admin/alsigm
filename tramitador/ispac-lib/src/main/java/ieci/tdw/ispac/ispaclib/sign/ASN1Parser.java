package ieci.tdw.ispac.ispaclib.sign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERBoolean;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERVisibleString;

/***
 * 
 * Clase que permite leer los oids de una estructura en ASN1 DER.
 * 
 * @see http://www.viafirma.org/xref/org/viafirma/nucleo/X509/X509Handler.html
 * @author antoniomaria_sanchez@ieci.es
 */
public class ASN1Parser {

	public static final Logger logger = Logger.getLogger(ASN1Parser.class);
	
	public ASN1Parser() {
		super();
	}
	
	public Map readPropertiesOid(DEREncodable signature) {
		Map propiedadesOid = new HashMap();
		DERObject derOBJ  = signature.getDERObject();
		readPropiedadesOid(null,derOBJ , propiedadesOid);
		return propiedadesOid;
	}
	

	/***
	 * Navega sobre los campos ASN.1 del certificado recuperando los pares valor
	 * oid= valor
	 * 
	 * @param extensionType
	 * @param propiedadesOid
	 */
	
	protected void readPropiedadesOid(String oidActual, DERObject extension,
			Map propiedadesOid) {
		if (extension instanceof DERSequence) {
			// tengo un objeto de tipo secuence.
			DERSequence secuence = (DERSequence) extension;
			Enumeration enumObjetos = secuence.getObjects();
			String oidUtilizadoNodo = oidActual;
			while (enumObjetos.hasMoreElements()) {
				DERObject objeto = (DERObject) enumObjetos.nextElement();
				// si este objeto fuese un identificador quiere decir que el
				// siguiente seria un objeto que queremos guardar
				if (objeto instanceof DERObjectIdentifier) {
					DERObjectIdentifier objetoID = (DERObjectIdentifier) objeto;
					// este es el oid utilizado para los nodos que estan por
					// debajo del actual
					oidUtilizadoNodo = objetoID.getId();
				} else {
					readPropiedadesOid(oidUtilizadoNodo, objeto, propiedadesOid);
				}
			}
		} else if (extension instanceof DERObjectIdentifier) {
			// el objeto es un identificador.
			DERObjectIdentifier objetoID = (DERObjectIdentifier) extension;
			String oid = objetoID.getId();
			logger.warn("Valor perdido " + oid);
		} else if (extension instanceof DERIA5String) {
			// hemos localizado un par id-valor
			String valor = ((DERIA5String) extension).getString();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERVisibleString) {
			// hemos localizado un par id-valor
			String valor = ((DERVisibleString) extension).getString();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERPrintableString) {
			// hemos localizado un par id-valor
			String valor = ((DERPrintableString) extension).getString();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERBitString) {
			String valor = "" + ((DERBitString) extension).getPadBits();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERSet) {
			Enumeration enumSet = ((DERSet) extension).getObjects();
			while (enumSet.hasMoreElements()) {
				readPropiedadesOid(oidActual,
						(DERObject) enumSet.nextElement(), propiedadesOid);
			}
		}
		else if (extension instanceof DERTaggedObject) {
			DERTaggedObject derTag = (DERTaggedObject) extension;
			if ( !derTag.isEmpty() || derTag.getObject() instanceof DERSequence) {
				DERObject nestedObj = derTag.getObject();
				readPropiedadesOid(oidActual, nestedObj, propiedadesOid);
			}
		}
		
		else if (extension instanceof DEROctetString) {
			DEROctetString oct = (DEROctetString) extension;
			ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(oct.getOctets()));
			try {
				DERObject extensionObj = aIn.readObject();
				readPropiedadesOid(oidActual, extensionObj, propiedadesOid);
			} catch (IOException e) {
				// si no es un nuevo objeto codificado quizas sea un string(ej :
				// las crls se recuperan asi)
				propiedadesOid.put(oidActual, new String(oct.getOctets()));
			} catch (IllegalStateException e) {
				// Problema extra√±o detectado con los certificados corruptos.
				// OID: 2.5.29.14 :java.lang.IllegalStateException: DER length
				// more than 4 bytes
				// DER length more than 4 bytes
				logger.warn(e.getMessage());
			} catch (Exception e) {
				// Problema extra√±o detectado con los certificados corruptos.
				// OID: 2.5.29.14 :java.lang.IllegalStateException: DER length
				// more than 4 bytes
				e.printStackTrace();
			}

		} else if (extension instanceof DERInteger) {
			String valor = "" + ((DERInteger) extension).getValue().longValue();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERT61String) {
			String valor = ((DERT61String) extension).getString();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERUTF8String) {
			String valor = ((DERUTF8String) extension).getString();
			storeOid(oidActual, propiedadesOid, valor);
		} else if (extension instanceof DERApplicationSpecific) {
			DERApplicationSpecific temp = (DERApplicationSpecific) extension;
			String valor = new String(temp.getContents());
			storeOid(oidActual, propiedadesOid, valor);
		}
		else if (extension instanceof DERUTCTime){
			DERUTCTime temp = (DERUTCTime) extension;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			try {
				storeOid(oidActual, propiedadesOid, sdf.format(temp.getAdjustedDate()));
			} catch (ParseException e) {
			}
		}
		else if (extension instanceof DERGeneralizedTime){
			DERGeneralizedTime temp = (DERGeneralizedTime) extension;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			try {
				storeOid(oidActual, propiedadesOid, sdf.format(temp.getDate()));
			} catch (ParseException e) {
			}
		}
		else if ( !(extension instanceof DERNull) && !(extension instanceof DERBoolean)) {
			logger.warn("Tipo de estructura ANS1 no soportada: (" + oidActual + ": "+extension + ")");
		}
	}

	/**
	 * MÈtodo que almacena un oid y su valor en el map de oids,
	 * 
	 * @param oid
	 * @param propiedadesOid
	 * @param valor
	 */
	private void storeOid(String oid, Map propiedadesOid,
			String valor) {
		if (oid != null){ // Existe informaciÛn dentro de una estructura asn1 que
			// no tiene porque tener un oid asociado, p.ej fecha caducidad de un certificado
			propiedadesOid.put(oid, valor);	
		}
	}
	
}