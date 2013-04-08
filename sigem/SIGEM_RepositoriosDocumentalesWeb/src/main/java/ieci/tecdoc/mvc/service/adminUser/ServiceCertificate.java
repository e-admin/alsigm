/*
 * Created on 18-oct-2005
 *
 */
package ieci.tecdoc.mvc.service.adminUser;


import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.mvc.dto.access.ErrorCertDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.error.WardaAdmException;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;

/**
 * @author Antonio María
 *  
 */
public class ServiceCertificate {

    private ServiceCertificate() {
    }

    public static ServiceCertificate getInstance() {
        return new ServiceCertificate();
    }

    public String decodeCert(HttpServletRequest request) throws Exception {
		String idCert = new String();

		String datos = request.getParameter("datos");
		String clave = request.getParameter("clave");

	
		BASE64Decoder decoder = new BASE64Decoder();
		String datosDesencriptados = "";

		byte[] TRIPLEDESKEY = new byte[24];
		StringTokenizer st = new StringTokenizer(clave, "|");
		for (int j = 0; st.hasMoreTokens(); j++) {
			byte ivalor = (byte) Integer.valueOf(st.nextToken().substring(2),
					16).intValue();
			TRIPLEDESKEY[j] = ivalor;

		}
		DESedeKeySpec deskeySpec = new DESedeKeySpec(TRIPLEDESKEY);
		SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
		SecretKey k = kf.generateSecret(deskeySpec);
		Cipher cd = Cipher.getInstance("DESede");
		cd.init(Cipher.DECRYPT_MODE, k);

		byte[] dLimpios = cd.doFinal(decoder.decodeBuffer(datos));
		datosDesencriptados = new String(dLimpios);
		StringTokenizer a = new StringTokenizer(datosDesencriptados, ";");
		int error = Integer.parseInt(a.nextToken());

		ConfigCertificate config = new ConfigCertificate();
		config.parse();
		List ignoredErrors = config.getIgnoredErrorsList();
		String tokenIdCert = config.getTypeCert();
		String entrado = (String) request.getSession().getAttribute("_FIELATO_F");
        request.getSession().setAttribute("_FIELATO_F","Entrada interna de FIELATO, no modificar");
        Boolean addCertificate = (Boolean) request.getSession().getAttribute("addCertificate");
        if (entrado != null && addCertificate != null && error == 9 )
        	error = 0;
        else if (entrado != null)
        	error = 9;
		
		String dn = a.nextToken();
		if (dn.indexOf("FNMT") < 0 ) {
			error = 99;
		}
		if (error > 0 ){
			boolean is = isAnIgnoredError(error,request,ignoredErrors);
			if (is) error = 0;
		}
		
		if (error > 0 )
			checkError(error);
		
		
		/*
		if ( enableError9.booleanValue() ) 
        {
        	
        }
        else
        {
        	if (error == 9) error = 0;
        }
        */
		StringTokenizer st2 = null;
		String valor = "";
		String nombre = "";
		boolean enc = false;
		while (a.hasMoreTokens() && !enc){
			st2 = new StringTokenizer(a.nextToken(), ":"); 
			nombre = st2.nextToken();
			try {
				valor = st2.nextToken();
			} catch (Exception e) {
				valor = "x";
			}
			if (nombre.equals(tokenIdCert)) {
				idCert = valor;
				enc = true;
			}
			
		}
		
		/*
		for (int i = 1; i < n - 1; i++) {
			st2 = new StringTokenizer(a.nextToken(), ":");
			nombre = st2.nextToken();
			valor = st2.nextToken();
			
			try {
				valor = st2.nextToken();
			} catch (Exception e) {
				valor = "x";
			}
			if (nombre.equals(tokenIdCert)) {
				idCert = valor;
			}
		}
		*/
		return idCert;
    }
    
    /**
	 * Comprueba si el error del servidor de firmas esta ignorado en el fichero de configuración
	 * 
	 * @param error
	 * @return
	 */
	private boolean isAnIgnoredError(int error,HttpServletRequest request, List ignoredErrors){
		boolean is = false;
		
		Iterator it = ignoredErrors.iterator();
		while (it.hasNext())
		{
			ErrorCertDTO xError = (ErrorCertDTO) it.next();
			if (error == xError.getNum())
				return true;
		}
		return is;
	}

    private void checkError(int error) throws Exception {
        if (error != 0 ) {
            
        switch (error) {
        case 99: // Lo he cambiado a mi gusto. El certificado presentado NO ha
                // sido expedido por la FNMT
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_NOT_FNMT);
            break;
        case 1:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_1);
            break;
        case 2:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_2);
            break;
        case 5:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_5);
            break;
        case 6:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_6);
            break;
        case 7:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_7);
            break;
        case 8:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_8);
            break;
        case 9:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_9);
            break;
        case 11:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_11);
            break;
        case 12:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_12);
            break;
        case 13:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_13);
            break;
        case 14:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_14);
            break;
        case 15:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_15);
            break;
        case 16:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_16);
            break;
        case 17:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_17);
            break;
        case 18:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_18);
            break;
        case 19:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_19);
            break;
        case 20:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_20);
            break;
        case 21:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_21);
            break;
        case 22:
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_22);
            break;
        //Este certificado no tiene puntos de distribución de CRLS. 
        //case 33:
        //  WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_33);
        //  break;
        }
        if (error >= 24 && error <= 32)
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_24);
        else if (error !=33 )
            WardaAdmException.throwException(MvcError.EC_CERTIFICATE_ERROR_UNKNOW);
        }
    }

    public void deleteIdCert(int id, String entidad) throws Exception {
        DbConnection dbConn= new DbConnection();
    	try {
            dbConn.open(DBSessionManager.getSession(entidad));
            int count = DbSelectFns.selectCount(dbConn, "IUSERUSERCERT", "WHERE ID='"
                    + id + "'");
            String stmtText = new String();
            if (count != 0) {
                stmtText = "DELETE FROM IUSERUSERCERT WHERE ID='" + id + "'";
                DbUtil.executeStatement(dbConn, stmtText);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dbConn.close();
        }
 
    }

    public void addIdCert(int userId, String idCert, String userAttName, String entidad)
            throws Exception {
    	DbConnection dbConn = new DbConnection();
    	try {

            dbConn.open(DBSessionManager.getSession(entidad));
            String stmtText = new String();
            if (idCert != null && !idCert.equals(""))
                idCert = "'" + idCert.toUpperCase() + "'";
            else
                idCert = "NULL";

            if (userAttName != null && !userAttName.equals(""))
                userAttName = "'" + userAttName + "'";
            else
                userAttName = "NULL";

            checkIsNotDuplicatedCert(dbConn, userId, idCert);
            stmtText = "INSERT INTO IUSERUSERCERT (ID,CERT, LDAPDN) VALUES ("
                    + userId + "," + idCert + "," + userAttName + " )";
            DbUtil.executeStatement(dbConn, stmtText);

        } catch (Exception e) {
            throw e;
        } finally {
            dbConn.close();
        }

    }

    public void updateIdCert(int userId, String idCert, String userAttName, String entidad)
            throws Exception {
    	DbConnection dbConn = new DbConnection();
    	try {
            dbConn.open(DBSessionManager.getSession(entidad));
            int count = DbSelectFns.selectCount(dbConn, "IUSERUSERCERT", "WHERE ID="
                    + userId);
            String stmtText = new String();

            if (idCert != null && !idCert.equals(""))
                idCert = "'" + idCert.toUpperCase() + "'";
            else
                idCert = "NULL";

            if (userAttName != null && !userAttName.equals(""))
                userAttName = "'" + userAttName + "'";
            else
                userAttName = "NULL";

            checkIsNotDuplicatedCert(dbConn, userId, idCert);

            if (count == 0) {
                stmtText = "INSERT INTO IUSERUSERCERT (ID,CERT, LDAPDN) VALUES("
                        + userId + "," + idCert + "," + userAttName + ")";
            } else {
                stmtText = "UPDATE IUSERUSERCERT SET CERT = " + idCert
                        + " WHERE ID=" + userId;
            }
            DbUtil.executeStatement(dbConn, stmtText);

        } catch (Exception e) {
            throw e;
        } finally {
            dbConn.close();
        }

    }

    public String getIdCert(int id, String entidad) throws Exception {
        String idCert = null;
    	DbConnection dbConn = new DbConnection();
        try {
            dbConn.open(DBSessionManager.getSession(entidad));
            int count = DbSelectFns.selectCount(dbConn, "IUSERUSERCERT", "WHERE ID="
                    + id);
            if (count > 0) {
                idCert = DbSelectFns.selectShortText(dbConn, "IUSERUSERCERT", "CERT",
                        "WHERE ID=" + id);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            dbConn.close();
        }
        return idCert;
    }

    private void checkIsNotDuplicatedCert(DbConnection dbConn, int userId, String idCert)
            throws Exception {
        if (!idCert.equals("NULL")) {
            int count = DbSelectFns.selectCount(dbConn, "IUSERUSERCERT", "WHERE CERT="
                    + idCert + "AND NOT ID = " + userId);
            if (count > 0) {
                throw new IeciTdException(String
                        .valueOf(MvcError.EC_DUPLICATE_CERTIFICATE), "");
            }
        }

    }

}