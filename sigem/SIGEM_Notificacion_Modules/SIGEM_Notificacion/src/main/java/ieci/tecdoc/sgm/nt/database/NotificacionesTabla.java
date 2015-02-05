/**
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.nt.database;

import org.apache.axis.utils.StringUtils;

import ieci.tecdoc.sgm.nt.GestorNotificaciones;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.nt.database.datatypes.NotificacionImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * expedientes.
 *
 */
public class NotificacionesTabla extends NotificacionImpl {

	private static final String TABLE_NAME = "SGMNTINFO_NOTIFICACION";

        private static final String CN_ID = "CGUID";

	private static final String CN_NIFDEST = "CNIFDEST";

	private static final String CN_NUMREG = "CNUMREG";

	private static final String CN_FHREG = "CFHREG";

	private static final String CN_NUMEXP = "CNUMEXP";

	private static final String CN_PROC = "CPROC";

        private static final String CN_ESTADO = "CESTADO";

        private static final String CN_FHESTADO = "CFHESTADO";

        private static final String CN_FHENTREGA = "CFHENTREGA";

        private static final String CN_USUARIO = "CUSUARIO";

        private static final String CN_TIPOCORRESPONDENCIA = "CTIPOCORRESPONDENCIA";

        private static final String CN_ORGANISMO = "CORGANISMO";

        private static final String CN_ASUNTO = "CASUNTO";

        private static final String CN_TIPO = "CTIPO";

        private static final String CN_TEXTO = "CTEXTO";

        private static final String CN_NOMBREDEST = "CNOMBREDEST";

        private static final String CN_APELLIDOSDEST = "CAPELLIDOSDEST";

        private static final String CN_CORREODEST = "CCORREODEST";

        private static final String CN_IDIOMA = "CIDIOMA";

        private static final String CN_TIPOVIA = "CTIPOVIA";

        private static final String CN_VIA = "CVIA";

        private static final String CN_NUMEROVIA = "CNUMEROVIA";

        private static final String CN_ESCALERAVIA = "CESCALERAVIA";

        private static final String CN_PISOVIA = "CPISOVIA";

        private static final String CN_PUERTAVIA = "CPUERTAVIA";

        private static final String CN_TELEFONO = "CTELEFONO";

        private static final String CN_MUNICIPIO = "CMUNICIPIO";

        private static final String CN_PROVINCIA = "CPROVINCIA";

        private static final String CN_CP = "CCP";

        private static final String CN_ERROR = "CERROR";

        private static final String CN_NOTIID = "CNOTIID";

        private static final String CN_SISTEMAID = "CSISTEMAID";

        private static final String CN_DEU = "CDEU";

        private static final String CN_TFNOMOVIL = "CTFNOMOVIL";

	private static final String ALL_COLUMN_NAMES =  CN_ID + "," +
                                                        CN_NIFDEST + "," +
                                                        CN_NUMREG + "," +
                                                        CN_FHREG + "," +
                                                        CN_NUMEXP + "," +
                                                        CN_PROC + "," +
                                                        CN_ESTADO + "," +
                                                        CN_FHESTADO + "," +
                                                        CN_FHENTREGA + "," +
                                                        CN_USUARIO + "," +
                                                        CN_TIPOCORRESPONDENCIA + "," +
                                                        CN_ORGANISMO + "," +
                                                        CN_ASUNTO + "," +
                                                        CN_TIPO + "," +
                                                        CN_TEXTO + "," +
                                                        CN_NOMBREDEST + "," +
                                                        CN_APELLIDOSDEST + "," +
                                                        CN_CORREODEST + "," +
                                                        CN_IDIOMA + "," +
                                                        CN_TIPOVIA  + "," +
                                                        CN_VIA + "," +
                                                        CN_NUMEROVIA + "," +
                                                        CN_ESCALERAVIA + "," +
                                                        CN_PISOVIA + "," +
                                                        CN_PUERTAVIA + "," +
                                                        CN_TELEFONO + "," +
                                                        CN_MUNICIPIO + "," +
                                                        CN_PROVINCIA + "," +
                                                        CN_CP + "," +
                                                        CN_ERROR + "," +
                                                        CN_NOTIID + "," +
                                                        CN_SISTEMAID + "," +
                                                        CN_DEU + "," +
                                                        CN_TFNOMOVIL;

	protected static final String ALL_COLUMN_NAMES_WITH_ESTADO =  CN_ID + "," +
													    CN_NIFDEST + "," +
													    CN_NUMREG + "," +
													    CN_FHREG + "," +
													    CN_NUMEXP + "," +
													    CN_PROC + "," +
													    CN_ESTADO + "," +
													    CN_FHESTADO + "," +
													    CN_FHENTREGA + "," +
													    CN_USUARIO + "," +
													    CN_TIPOCORRESPONDENCIA + "," +
													    CN_ORGANISMO + "," +
													    CN_ASUNTO + "," +
													    CN_TIPO + "," +
													    CN_TEXTO + "," +
													    CN_NOMBREDEST + "," +
													    CN_APELLIDOSDEST + "," +
													    CN_CORREODEST + "," +
													    CN_IDIOMA + "," +
													    CN_TIPOVIA  + "," +
													    CN_VIA + "," +
													    CN_NUMEROVIA + "," +
													    CN_ESCALERAVIA + "," +
													    CN_PISOVIA + "," +
													    CN_PUERTAVIA + "," +
													    CN_TELEFONO + "," +
													    CN_MUNICIPIO + "," +
													    CN_PROVINCIA + "," +
													    CN_CP + "," +
													    CN_ERROR + "," +
													    CN_NOTIID + "," +
													    CN_SISTEMAID + "," +
													    CN_DEU + "," +
                                                        CN_TFNOMOVIL;

	/**
	 * Constructor de la clase ExpedientesTable
	 */
	public NotificacionesTabla() {
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
	 * Devuelve los nombres de las columnas que se puede actualizar
	 *
	 * @return String Nombres de las columnas que se puede actualizar
	 */
        public String getUpdateColumnNames(){
            return CN_ESTADO + ", " + CN_FHESTADO + " ," + CN_ID + " ," + CN_ERROR;
        }
        
        public String getUpdateEstadoColumnNames(){
            return CN_ESTADO + ", " + CN_FHESTADO + " ," + CN_ERROR;
        }

	/**
	 * Devuelve el nombre de la columna id
	 *
	 * @return String Nombre de la columna  id
	 */
	public String getIdColumnName() {
		return CN_ID;
	}

	/**
	 * Devuelve el nombre de la columa nif destino
	 *
	 * @return String Nombre de la columna nif destino
	 */
	public String getNifDestinoColumnName() {
		return CN_NIFDEST;
	}

	/**
	 * Devuelve el nombre de la columna Numero de registro
	 *
	 * @return String Nombre de la columna Numero de registro
	 */
	public String getNumeroRegistroColumnName() {
		return CN_NUMREG;
	}

        /**
	 * Devuelve el nombre de la columna del error
	 *
	 * @return String Nombre de la columna del error
	 */
	public String getErrorColumnName() {
		return CN_NUMREG;
	}

       	/**
	 * Devuelve el nombre de la columna fecha de registro
	 *
	 * @return String Nombre de la columna fecha de registro
	 */
	public String getFechaRegistroColumnName() {
		return CN_FHREG;
	}

	/**
	 * Devuelve el nombre de la columna numero de expediente
	 *
	 * @return String Nombre de la columna numero de expediente
	 */
	public String getNumeroExpedienteColumnName() {
		return CN_NUMEXP;
	}


	/**
	 * Devuelve el nombre de la columna procedimiento
	 *
	 * @return String Nombre de la columna procedimiento
	 */
	public String getProcedimientoColumnName() {
		return CN_PROC;
	}

	/**
	 * Devuelve el nombre de la columna estado
	 *
	 * @return String Nombre de la columna estado
	 */
	public String getEstadoColumnName() {
		return CN_ESTADO;
	}

	/**
	 * Devuelve el nombre de la columna de la fecha de la ultima actualizacion del estado
	 *
	 * @return String Nombre de la columna de la fecha de la ultima actualizacion del estado
	 */
	public String getFechaActualizacionEstadoColumnName() {
		return CN_FHESTADO;
	}

        /**
	 * Devuelve el nombre de la columna fecha de entrega
	 *
	 * @return String Nombre de la columna fecha de entrega
	 */
	public String getFechaEntregaColumnName() {
		return CN_FHENTREGA;
	}

        /**
	 * Devuelve el nombre de la columna del usuario
	 *
	 * @return String nombre columna id de usuario
	 */
	public String getUsuarioColumnName() {
		return CN_USUARIO;
        }

         /**
	 * Devuelve el nombre de la columna del tipo de correspondencia
	 *
	 * @return String nombre columna tipo de correspondencia
	 */
	public String getTipoCorrespondenciaColumnName() {
		return CN_TIPOCORRESPONDENCIA;
        }

         /**
	 * Devuelve el nombre de la columna del organismo
	 *
	 * @return String nombre columna oraganismo
	 */
	public String getOrganismoColumnName() {
		return CN_ORGANISMO;
        }

         /**
	 * Devuelve el nombre de la columna del asunto
	 *
	 * @return String nombre columna asunto
	 */
	public String getAsuntoColumnName() {
		return CN_ASUNTO;
        }

         /**
	 * Devuelve el nombre de la columna del tipo de notificacion
	 *
	 * @return String nombre columna tipo
	 */
	public String getTipoColumnName() {
		return CN_TIPO ;
        }

        /**
	 * Devuelve el nombre de la columna del texto de notificacion
	 *
	 * @return String nombre columna texto
	 */
	public String getTextoColumnName() {
		return CN_TEXTO;
        }

         /**
	 * Devuelve el nombre de la columna del nombre del destinaario de notificacion
	 *
	 * @return String nombre columna nombre destinatario
	 */
	public String getNombreDestinatarioColumnName() {
		return CN_NOMBREDEST;
        }

        /**
	 * Devuelve el nombre de la columna de los apellidos del destinaario de notificacion
	 *
	 * @return String nombre columna apellidos destinatario
	 */
	public String getApellidosDestinatarioColumnName() {
		return  CN_APELLIDOSDEST ;
        }

         /**
	 * Devuelve el nombre de la columna del correo del destinaario de notificacion
	 *
	 * @return String nombre columna correo destinatario
	 */
	public String getCorreoDestinatarioColumnName() {
		return  CN_CORREODEST;
        }

         /**
	 * Devuelve el nombre de la columna idioma de la notificacion
	 *
	 * @return String nombre columna idioma
	 */
	public String getIdiomaColumnName() {
		return  CN_IDIOMA;
        }

         /**
	 * Devuelve el nombre de la columna tipo de via
	 *
	 * @return String nombre columna tipo de via
	 */
	public String getTipoViaColumnName() {
		return  CN_TIPOVIA;
        }

        /**
	 * Devuelve el nombre de la columna nombre de la via
	 *
	 * @return String nombre columna nombre de via
	 */
	public String getNombreViaColumnName() {
		return  CN_VIA;
        }

        /**
	 * Devuelve el nombre de la columna numero
	 *
	 * @return String nombre columna numero
	 */
	public String getNumeroViaColumnName() {
		return  CN_NUMEROVIA;
        }

          /**
	 * Devuelve el nombre de la columna escalera
	 *
	 * @return String nombre columna escalera
	 */
	public String getEscaleraViaColumnName() {
		return CN_ESCALERAVIA;
        }

          /**
	 * Devuelve el nombre de la columna piso
	 *
	 * @return String nombre columna piso
	 */
	public String getPisoViaColumnName() {
		return CN_PISOVIA;
        }

          /**
	 * Devuelve el nombre de la columna puerta
	 *
	 * @return String nombre columna puerta
	 */
	public String getPuertaViaColumnName() {
		return CN_PUERTAVIA;
        }

          /**
	 * Devuelve el nombre de la columna telefono
	 *
	 * @return String nombre columna telefono
	 */
	public String getTelefonoColumnName() {
		return CN_TELEFONO;
        }

          /**
	 * Devuelve el nombre de la columna municipio
	 *
	 * @return String nombre columna nmunicipio
	 */
	public String getMunicipioColumnName() {
		return CN_MUNICIPIO;
        }

          /**
	 * Devuelve el nombre de la columna provincia
	 *
	 * @return String nombre columna provincia
	 */
	public String getProvinciaColumnName() {
		return CN_PROVINCIA;
        }

          /**
	 * Devuelve el nombre de la columna codigo postal
	 *
	 * @return String nombre columna codigo postal
	 */
	public String geCodigoPostalColumnName() {
		return CN_CP;
        }

	public String getNotiIdColumnName() {
		return CN_NOTIID;
	}

	public String getSistemaIdColumnName() {
		return CN_SISTEMAID;
	}

	public String getDeuColumnName() {
		return CN_DEU;
	}

	public String getTfnoMovilColumnName() {
		return CN_TFNOMOVIL;
	}

	/**
	 * Devuelve la clausula de consulta por el numero de expediente
	 *
	 * @param nif del destinatario
         * @param numero de expediente de la notifiacion
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorId(String nif_, String numeroExpe_) {
		String clausula;

		clausula = "WHERE " + CN_NIFDEST + " = '" + DbUtil.replaceQuotes(nif_) + "' and " +
                            CN_NUMEXP + " = '" + DbUtil.replaceQuotes(numeroExpe_) + "'" ;

		return clausula;
	}

	public String getClausulaPorNotiId(String notiId_,String nif_, String numeroExpe_) {
		String clausula;

		if(!StringUtils.isEmpty(notiId_))
			clausula = "WHERE " + CN_NOTIID + " = '" + DbUtil.replaceQuotes(notiId_) + "'" ;
		else
			clausula = getClausulaPorId(nif_,numeroExpe_);

		return clausula;
	}

         /**
	 * Devuelve la clausula de consulta por rango de estado
	 *
	 * @param nif del destinatario
         * @param numero de expediente de la notifiacion
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorBusquedaEstado (Integer estado_){
            String clausula = null;

            clausula = " WHERE " + CN_ESTADO + " <= " + estado_.intValue() + " ORDER BY " + CN_ID + ", " + CN_NUMEXP;

            return clausula;
        }
	
	public String getClausulaPorBusquedaEstadoParaActualizar (Integer estado_){
        String clausula = null;
        
        clausula = " WHERE " + CN_ESTADO + " > " + GestorNotificaciones.VALOR_ESTADO_ERROR_DEVUELTO_CONECTOR + " AND " + CN_ESTADO + " <= " + estado_.intValue() + " ORDER BY " + CN_ID + ", " + CN_NUMEXP;
        
        return clausula;
    }    

        /**
	 * Devuelve la clausula de consulta por criterios de busqueda
	 *
	 * @param nif del destinatario
         * @param numero de expediente de la notifiacion
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorBusqueda (ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean params_){

            String clausula = null;

            if (params_.getEstado() != null)
                clausula = CN_ESTADO + " = " +params_.getEstado().intValue();

            if (params_.getNotificacion() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_ID + " = '" + DbUtil.replaceQuotes(params_.getNotificacion()) + "'";
                else
                    clausula = CN_ID + " = '" + DbUtil.replaceQuotes(params_.getNotificacion()) + "'";

             if (params_.getFechaDesde() != null )
                 if (clausula != null)
                    clausula = clausula + " and " + CN_FHENTREGA + " >= '" + DbUtil.replaceQuotes((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(params_.getFechaDesde())) + "'";
                 else
                    clausula = CN_FHENTREGA + " >= '" + DbUtil.replaceQuotes((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(params_.getFechaDesde())) + "'";

             if (params_.getTipo() != null )
                 if (clausula != null)
                     clausula = clausula + " and " + CN_TIPO + " = '" + DbUtil.replaceQuotes(params_.getTipo()) + "'";
                 else
                     clausula = CN_TIPO + " = '" + DbUtil.replaceQuotes(params_.getTipo()) + "'";

             if (params_.getFechaHasta() != null )
                 if (clausula != null)
                     clausula = clausula + " and " + CN_FHENTREGA + " <= '" + DbUtil.replaceQuotes((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(params_.getFechaHasta())) + "'";
                 else
                     clausula = CN_FHENTREGA + " <= '" + DbUtil.replaceQuotes((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(params_.getFechaHasta())) + "'";

            if (params_.getNif() != null )
                 if (clausula != null)
                     clausula = clausula + " and " + CN_NIFDEST + " = '" + DbUtil.replaceQuotes(params_.getNif()) + "'";
                 else
                     clausula = CN_NIFDEST + " = '" + DbUtil.replaceQuotes(params_.getNif()) + "'";

            if (params_.getNumExp() != null )
                if (clausula != null)
                    clausula = clausula + " and " + CN_NUMEXP + " = '" + DbUtil.replaceQuotes(params_.getNumExp()) + "'";
                else
                    clausula = CN_NUMEXP + " = '" + DbUtil.replaceQuotes(params_.getNumExp()) + "'";


            if (clausula != null)
                clausula = " WHERE " + clausula + " ORDER BY " + CN_ID + ", " + CN_NUMEXP;


            return clausula;
        }


}