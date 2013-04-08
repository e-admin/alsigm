/*
 * DocumentosTabla.java
 *
 * Created on 23 de mayo de 2007, 15:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database;

import org.apache.axis.utils.StringUtils;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.nt.database.datatypes.DocumentoImpl;
/**
 *
 * @author Usuario
 */
public class DocumentosTabla  extends DocumentoImpl {

    private static final String TABLE_NAME = "SGMNTINFO_DOCUMENTO";

    private static final String TABLE_NAME_PARENT = "SGMNTINFO_NOTIFICACION";

    private static final String CN_EXPEDIENTE  = "CNOTIEXPE";

    private static final String CN_NIF = "CNOTINIFDEST";

    private static final String CN_CODIGO = "CCODIGO";

    private static final String CN_GUID = "CGUID";

    private static final String CN_NOTIID = "CNOTIID";

    private static final String CN_TIPODOC = "CTIPODOC";

    private static final String ALL_COLUMN_NAMES =  CN_EXPEDIENTE + "," +
                                                    CN_NIF + "," +
                                                    CN_CODIGO + "," +
                                                    CN_GUID + "," +
                                                    CN_NOTIID + "," +
                                                    CN_TIPODOC;

    private static final String ALL_COLUMN_NAMES_WITH_ALIAS =  "B." + CN_EXPEDIENTE +
    											",B." + CN_NIF +
    											",B." + CN_CODIGO +
    											",B." + CN_GUID +
    											",B." + CN_NOTIID +
    											",B." + CN_TIPODOC;


    /** Creates a new instance of DocumentosTabla */
    public DocumentosTabla() {
    }


    public String getClausulaPorRegistroPadre (ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean params_){

        NotificacionesTabla tipoPadre = new NotificacionesTabla();

        String salida =  " WHERE A." + tipoPadre.getIdColumnName() +" = '" + DbUtil.replaceQuotes(params_.getCodigoNoti()) + "' "+
               " AND A." + tipoPadre.getNumeroExpedienteColumnName() +" = B." + CN_EXPEDIENTE +
               " AND A." + tipoPadre.getNifDestinoColumnName() +" = B." + CN_NIF +
               " AND A." + tipoPadre.getNotiIdColumnName() +" = B." + CN_NOTIID +
               " AND B." + CN_CODIGO + " = '" + DbUtil.replaceQuotes(params_.getCodigDoc()) + "'";

         return salida;
    }

    /**
     * Devuelve el nombre de la tabla con la de su hija de documentos
     *
     * @return String Nombre de la tabla
     */
     public String getTableNameChildDoc() {

	return TABLE_NAME_PARENT + " A, " + TABLE_NAME + " B ";
    }



    /**
     * Devuelve el nombre de la tabla
     *
     * @return String Nombre de la tabla
     */
    public String getTableName() {
    	return TABLE_NAME;
    }

    /**
     * Devuelve los nombres de las columnas
     *
     * @return String Nombres de las columnas
     */
    public String getAllColumnNames() {
        return ALL_COLUMN_NAMES;
    }

     /**
     * Devuelve los nombres de las columnas
     *
     * @return String Nombres de las columnas
     */
    public String getAllColumnNamesWithAlias() {
        return ALL_COLUMN_NAMES_WITH_ALIAS ;
    }

    /**
     * Devuelve el nombre de la columna expediente
     *
     * @return String Nombre de la columna expediente
     */
    public String getExpedienteColumnName() {
    	return CN_EXPEDIENTE;
    }

    /**
     * Devuelve el nombre de la columna guid
     *
     * @return String Nombre de la columna guid
     */
    public String getGuidColumnName() {
    	return CN_GUID;
    }

     /**
     * Devuelve el nombre de la columna nif destinatario
     *
     * @return String Nombre de la columna nif destinatario
     */
    public String getNifDestinatarioColumnName() {
    	return CN_NIF;
    }

     /**
     * Devuelve el nombre de la columna codigo
     *
     * @return String Nombre de la columna codigo
     */
    public String getCodigoColumnName() {
    	return  CN_CODIGO;
    }

    public String getNotiIdColumnName() {
    	return  CN_NOTIID;
    }

    public String getTipoDocColumnName() {
    	return  CN_TIPODOC;
    }

    /**
     * Devuelve la clausula de consulta por el numero de expediente
     *
     * @param String codigo propio del docuemtno
     * @param String Pk de la notificacion (expediente)
     * @param String Pk de la notificacion (nif de destino)
     * @return String Clausula de consulta por numero
     */
    public String getClausulaPorId(String expediente_, String nif_, String notiid_) {
        String clausula;

        if(StringUtils.isEmpty(notiid_)){
        	clausula = "WHERE " + CN_EXPEDIENTE + " = '" + DbUtil.replaceQuotes(expediente_) + "' and " +
                              CN_NIF + " = '" + DbUtil.replaceQuotes(nif_) + "'";
        }else{
        	clausula = "WHERE " + CN_NOTIID + " = '" + DbUtil.replaceQuotes(notiid_) + "'";
        }
        return clausula;
    }

    /**
     * Devuelve la clausula de consulta por el numero de expediente
     *
     * @param nif del destinatario
     * @param numero de expediente de la notifiacion
     * @return String Clausula de consulta por numero
     */
    public String getClausulaPorBusqueda (ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean params_){

        String clausula = null;

            if (params_.getExpediente() != null)
                clausula = CN_EXPEDIENTE + " = '" + DbUtil.replaceQuotes(params_.getExpediente()) + "'";

            if (params_.getNifDestinatario() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_NIF + " = '" + DbUtil.replaceQuotes(params_.getNifDestinatario()) + "'";
                else
                    clausula =CN_NIF + " = '" + DbUtil.replaceQuotes(params_.getNifDestinatario()) + "'";

            if (params_.getCodigoNoti() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_CODIGO + " = '" + DbUtil.replaceQuotes(params_.getCodigoNoti()) + "'";
                else
                    clausula =CN_CODIGO + " = '" + DbUtil.replaceQuotes(params_.getCodigoNoti()) + "'";

            if (params_.getNotiId() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_NOTIID + " = '" + DbUtil.replaceQuotes(params_.getNotiId()) + "'";
                else
                    clausula =CN_NOTIID + " = '" + DbUtil.replaceQuotes(params_.getNotiId()) + "'";

            if (params_.getTipoDoc() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_TIPODOC + " = " + params_.getTipoDoc().intValue();
                else
                    clausula =CN_TIPODOC + " = " + params_.getTipoDoc().intValue();

            if (clausula != null)
                clausula = " WHERE " + clausula;


            return clausula;
        }

}
