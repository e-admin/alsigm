package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapURL;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.admin.business.spring.AppContext;
import es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresListaDistribucionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOrganizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOrganizacionLocalizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresTipoOrganizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.ldap.Info;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapService;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.DepartamentosLista;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasAuthConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;

/*$Id*/

public class ISicresRPAdminOrganizacionManager {
    private static final Logger logger = Logger
            .getLogger(ISicresRPAdminOrganizacionManager.class);

    public static SicresOrganizacionesImpl obtenerOrganizacionesPorTipo(
            int type, String entidad) throws ISicresRPAdminDAOException {
        DbConnection db = new DbConnection();
        SicresOrganizacionesImpl organizaciones = null;
        try {
            db.open(DBSessionManager.getSession());
            organizaciones = SicresOrganizacionDatos.getOrganizacionesByType(
                    type, db);
        } catch (Exception e) {
            logger.error("Error obteniendo organizaciones");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return organizaciones;
    }

    public static SicresOrganizacionesImpl obtenerOrganizaciones(int idPadre,
            String entidad) throws ISicresRPAdminDAOException {
        DbConnection db = new DbConnection();
        SicresOrganizacionesImpl lista = null;
        try {
            db.open(DBSessionManager.getSession());
            if (idPadre < 0) {
                // Si el idPadre es menor que 0 es porque se buscan los hijos de
                // un tipo de organizacion
                lista = SicresOrganizacionDatos.getOrganizacionesByType(-1
                        * idPadre, db);
            } else if (idPadre > 0) {
                lista = SicresOrganizacionDatos.getOrganizacionesByFather(
                        idPadre, db);
            } else {
                lista = new SicresOrganizacionesImpl();
                logger
                        .error("Se ha llamado a RPAdminOrganizacionManager.obtenerOrganizaciones con idPadre=0");
            }
        } catch (Exception e) {
            logger.error("Error obteniendo organizaciones");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return lista;
    }

    public static SicresOrganizacionImpl getOrganizacion(int id, String entidad) {
        DbConnection db = new DbConnection();
        SicresOrganizacionDatos bean = new SicresOrganizacionDatos();
        try {
            db.open(DBSessionManager.getSession());
            bean.load(id, db);
        } catch (Exception e) {
            logger.error("No se ha podido recuperar el la organizacion");
            return null;
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return bean;
    }

    public static SicresOrganizacionImpl getOrganizacionByCode(String code, String entidad) {
        DbConnection db = new DbConnection();
        SicresOrganizacionDatos bean = new SicresOrganizacionDatos();
        try {
            db.open(DBSessionManager.getSession());
            bean.load(code, db);
        } catch (Exception e) {
            logger.error("No se ha podido recuperar el la organizacion");
            return null;
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return bean;
    }

    public static SicresOrganizacionLocalizacionImpl getLocalizacion(int id,
            String entidad) {
        DbConnection db = new DbConnection();
        SicresOrganizacionLocalizacionDatos bean = new SicresOrganizacionLocalizacionDatos();
        try {
            db.open(DBSessionManager.getSession());
            bean.load(id, db);
        } catch (Exception e) {
            logger
                    .error("No se ha podido recuperar la localizacion de la organizacion");
            return null;
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return bean;
    }

    public static int crearOrganizacion(SicresOrganizacionImpl organizacion,
            SicresOrganizacionLocalizacionImpl localizacion, UnidadRegistralVO unidadRegistral , String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();
            organizacion.setId(IdsGenerator.generateNextId(
                    IdsGenerator.SCR_ORGS, entidad));
            new SicresOrganizacionDatos(organizacion).add(db);
            if (localizacion != null) {
                localizacion.setIdOrgs(organizacion.getId());
                new SicresOrganizacionLocalizacionDatos(localizacion).add(db);
            }
            db.endTransaction(true);

            //intercambio registral
            if (unidadRegistral != null) {
                // asignamos el id de la unidad a la unidad registral
                unidadRegistral.setId_orgs(organizacion.getId());
                // creamos la unidad Registral
                IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

                addUnidadRegistralValidation(unidadRegistral, db,
                        intercambioRegistralManager);
            }

        } catch (Exception e) {
            if (db != null && db.inTransaction())
                try {
                    db.endTransaction(false);
                } catch (Exception e1) {
                    logger.error("Problemas con rollback");
                }
            logger.error("Error creando la organizacion");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.ERROR_CREATE_UNID_ADMIN, e);
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return organizacion.getId();
    }

    public static void editarOrganizacion(SicresOrganizacionImpl organizacion,
            SicresOrganizacionLocalizacionImpl localizacion, UnidadRegistralVO unidadRegistral, String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();
            (new SicresOrganizacionDatos(organizacion)).update(db);
            // Si viene nulo, borrar fila, si no viene nulo y la fila existe
            // acutalizar, si no existe añadir.
            // Si viene nulo, borrar fila, si no viene nulo y la fila existe
            // acutalizar, si no existe añadir.
            SicresOrganizacionLocalizacionDatos localizacionDAO = new SicresOrganizacionLocalizacionDatos();
            if (localizacion == null) {
                localizacionDAO.setIdOrgs(organizacion.getId());
                localizacionDAO.delete(db);
            } else {
                try {
                    localizacionDAO.load(organizacion.getId(), db);
                    new SicresOrganizacionLocalizacionDatos(localizacion)
                            .update(db);
                } catch (ISicresRPAdminDAOException e) {
                    new SicresOrganizacionLocalizacionDatos(localizacion)
                            .add(db);
                }
            }
			// Se cierra la transacción aqui, para evitar conflictos en
			// intercambio registral, ya que necesita utilizar datos de la tabla
			// scr_orgs y que no quede bloqueadas las transacciones
            db.endTransaction(true);

            //actualizamos los datos para el intercambio registral
            updateDataIntercambioRegistral(unidadRegistral, db);

        } catch (Exception e) {
            if (db != null && db.inTransaction())
                try {
                    db.endTransaction(false);
                } catch (Exception e1) {
                    logger.error("Problemas con rollback");
                }
            logger.error("Error editando la organizacion");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.ERROR_UPDATE_UNID_ADMIN, e);
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    /**
     * Método que actualiza los datos correspondientes al intercambio registral cuando editamos una unidad administrativa
     *
     * @param unidadRegistral - Unidad Registral
     * @param db - Conexión de BBDD
     *
     * @throws ISicresAdminIntercambioRegistralException
     * @throws ISicresAdminDAOException
     */
    private static void updateDataIntercambioRegistral(
            UnidadRegistralVO unidadRegistral, DbConnection db)
            throws ISicresAdminIntercambioRegistralException,
            ISicresAdminDAOException {
        //Intercambio Registral
        IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

        if(unidadRegistral.getId() != 0){
            //obtenemos los datos almacenados de la Entidad Registral
            UnidadRegistralVO unidadRegistralInBBDD = intercambioRegistralManager.getUnidadRegistral(unidadRegistral.getId());
            // comprobamos si los datos de la entidad Registral que recibimos
            // como parametro son nulos
            if (!unidadRegistral.isUnidadRegistralVacia()) {
                //modificamos los datos de la Entidad Registral

                //Obtenemos el listado de unidades admin con el código de unidad de tramitación indicada
                List<UnidadRegistralVO> listUnidadesRegistrales = searchMoreDataUnidadRegistral(
                        unidadRegistral.getCode_tramunit(), intercambioRegistralManager);

                //Verificamos si la unidad de tramitacion no ha sido mapeada, se procede a mapearla
                if((listUnidadesRegistrales==null) || (listUnidadesRegistrales.isEmpty())){
                        //actualizamos los datos de la unidad registral
                        actualizarUnidadeRegistral(unidadRegistral,
                                intercambioRegistralManager,
                                unidadRegistralInBBDD);
                }else{
                    //si ya ha sido mapeada
                    UnidadRegistralVO unidadRegistralAux = (UnidadRegistralVO) listUnidadesRegistrales.get(0);
                    //comprobamos si ha sido mapeada por la misma unidad administrativa
                    if(unidadRegistralInBBDD.getId_orgs() == unidadRegistralAux.getId_orgs()){
                        //si es asi actualizamos datos de la unidad registral
                        actualizarUnidadeRegistral(unidadRegistral,
                                intercambioRegistralManager,
                                unidadRegistralInBBDD);
                    }else{
                        //sino elevamos error, la unidad solo puede estar mapeada con una unidad administrativa
                        generateErrorOrganizacionDuplicada(
                                unidadRegistralAux.getId_orgs(), db);
                    }
                }
            }else{
                //borramos la Entidad Registral ya que los datos de codigo y nombre son vacios
                intercambioRegistralManager.deleteUnidadRegistral(unidadRegistralInBBDD);
            }
        }else{
            //creamos entidad Registral
            addUnidadRegistralValidation(unidadRegistral, db, intercambioRegistralManager);
        }
    }

    /**
     * Método que setea los valores de la unidadRegistral y la actualiza en BBDD
     * @param unidadRegistral - Unidad Registral Origen
     * @param intercambioRegistralManager - Manager
     * @param unidadRegistralUpdate - Unidad Registral a Actualizar
     * @throws ISicresAdminIntercambioRegistralException
     */
    private static void actualizarUnidadeRegistral(
            UnidadRegistralVO unidadRegistral,
            IntercambioRegistralManager intercambioRegistralManager,
            UnidadRegistralVO unidadRegistralUpdate)
            throws ISicresAdminIntercambioRegistralException {

        //Seteamos los valores a actualizar
        unidadRegistralUpdate.setCode_entity(unidadRegistral.getCode_entity());
        unidadRegistralUpdate.setName_entity(unidadRegistral.getName_entity());
        unidadRegistralUpdate.setCode_tramunit(unidadRegistral.getCode_tramunit());
        unidadRegistralUpdate.setName_tramunit(unidadRegistral.getName_tramunit());

        //actualizamos los datos
        intercambioRegistralManager.updateUnidadRegistral(unidadRegistralUpdate);

    }

    /**
     * Método que valida si la unidad registral indicada es valida (no esta duplicada), si es asi la añade a BBDD
     * @param unidadRegistral - Unidad Registral
     * @param db - Conexión de BBDD
     * @param intercambioRegistralManager - Contexto
     *
     * @throws ISicresAdminIntercambioRegistralException
     * @throws ISicresAdminDAOException
     */
    private static void addUnidadRegistralValidation(UnidadRegistralVO unidadRegistral,
            DbConnection db,
            IntercambioRegistralManager intercambioRegistralManager)
            throws ISicresAdminIntercambioRegistralException,
            ISicresAdminDAOException {
        //Obtenemos el listado de unidades admin con el código de unidad de tramitación indicada
        List<UnidadRegistralVO> listUnidadesRegistrales = searchMoreDataUnidadRegistral(
                unidadRegistral.getCode_tramunit(), intercambioRegistralManager);

        //si es != 0 quiere decir que dicha unidad de tramitación ya ha sido mapeada
        if((listUnidadesRegistrales!=null) && (!listUnidadesRegistrales.isEmpty())){
            // por tanto excepcion, no se puede mapear dos veces
            generateErrorOrganizacionDuplicada(
                    ((UnidadRegistralVO) listUnidadesRegistrales.get(0))
                            .getId_orgs(),
                    db);
        }

        //añadimos la unidad de tramitación
        unidadRegistral = intercambioRegistralManager
                .addUnidadRegistral(unidadRegistral);
    }

    /**
     * Método que busca en BBDD la información de unidades registrales con el código pasado como parámetro
     * @param codeTramUnit - Cod. de unidad a buscar
     * @param intercambioRegistralManager - Manager
     * @return {@link List} - Listado de unidades de registrales {@link UnidadRegistralVO} que coinciden con la búsqueda
     */
    private static List<UnidadRegistralVO> searchMoreDataUnidadRegistral(
            String codeTramUnit,
            IntercambioRegistralManager intercambioRegistralManager) {

        //List<UnidadRegistralVO> listUnidadesRegistrales =  new ArrayList<UnidadRegistralVO>();
    	List<UnidadRegistralVO> listUnidadesRegistrales =  null;

        //comprobamos si el código indicado es distinto de nulo
        if(StringUtils.isNotBlank(codeTramUnit)){
            //generamos un objeto con el que vamos a realizar la búsqueda
            UnidadRegistralVO unidadRegistralSearch = new UnidadRegistralVO();
            unidadRegistralSearch.setCode_tramunit(codeTramUnit);

            //realizamos la busqueda
            listUnidadesRegistrales = intercambioRegistralManager.findUnidadesRegistrales(unidadRegistralSearch);
        }

        //retornamos los datos
        return listUnidadesRegistrales;
    }

    /**
     * Método que eleva excepción ya que la unidad de tramitación indicada ya
     * esta asociada a otra unidad administrativa
     *
     * @param idOrgs - Id de la unidad administrativa
     * @param db - Conexión a BBDD
     * @throws ISicresAdminDAOException
     * @throws ISicresAdminIntercambioRegistralException
     */
    private static void generateErrorOrganizacionDuplicada(
            int idOrgs, DbConnection db)
            throws ISicresAdminDAOException,
            ISicresAdminIntercambioRegistralException {
        //Obtenemos la información de la unidad admin que ya tiene mapeada la unidad de tramitación
        SicresOrganizacionDatos scrOrgs = new SicresOrganizacionDatos();
        scrOrgs.load( idOrgs, db);

        //componemos cadena con la info de la unid. administrativa
        StringBuffer cadenaOrgs = new StringBuffer();
        cadenaOrgs.append(scrOrgs.getCode()).append(" - ").append(scrOrgs.getName());

        //Obtenemos el mensaje de error
        String msg = MessageFormat
                .format(new ISicresAdminIntercambioRegistralException(
                        ISicresAdminIntercambioRegistralException.EXC_ERROR_CODE_UNIDAD_TRAMITACION_MAPEADA)
                        .getMessage(),
                        new Object[] { cadenaOrgs.toString()});

        throw new ISicresAdminIntercambioRegistralException(msg);
    }

    public static void eliminarOrganizacion(int idOrg, String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();

            // Localizacion
            SicresOrganizacionLocalizacionDatos localizacion = new SicresOrganizacionLocalizacionDatos();
            localizacion.setIdOrgs(idOrg);
            localizacion.delete(db);

            //Intercambio Registral
            IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();
            //buscamos la Entidad Registral asociada a la oficina
            UnidadRegistralVO unidadRegistral = intercambioRegistralManager
                    .getUnidadRegistralByIdOrgs(idOrg);

            if(unidadRegistral != null){
                //borramos la relacion de la Entidad Registral
                intercambioRegistralManager.deleteUnidadRegistral(unidadRegistral);
            }

            // Distribuciones
            SicresListaDistribucionDatos.deleteDistribucionesOrganizacion(idOrg, db);

            //Organizacion
            SicresOrganizacionDatos org = new SicresOrganizacionDatos();
            org.setId(idOrg);
            org.delete(db);

            db.endTransaction(true);
        } catch (Exception e) {
            if (db != null && db.inTransaction())
                try {
                    db.endTransaction(false);
                } catch (Exception e1) {
                    logger.error("Problemas con rollback");
                }
            logger.error("Error eliminando la organizacion");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.SCR_ORGS_DELETE, e);
        } finally {
            try {
                if (db != null && db.existConnection()){
                    db.close();
                }
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    public static SicresTiposOrganizacionesImpl obtenerTiposOrganizaciones(
            String entidad) throws ISicresRPAdminDAOException {
        DbConnection db = new DbConnection();
        SicresTiposOrganizacionesImpl lista = null;
        try {
            db.open(DBSessionManager.getSession());
            lista = SicresTipoOrganizacionDatos.getTiposOrganizaciones(db);
        } catch (Exception e) {
            logger.error("Error obteniendo organizaciones");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return lista;
    }

    public static SicresTipoOrganizacionDatos getTipoOrganizacion(int id, String entidad) {
        DbConnection db = new DbConnection();
        SicresTipoOrganizacionDatos bean = new SicresTipoOrganizacionDatos();
        try {
            db.open(DBSessionManager.getSession());
            bean.load(id, db);
        } catch (Exception e) {
            logger.error("No se ha podido recuperar el tipo de organizacion");
            return null;
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return bean;
    }

    public static SicresTipoOrganizacionDatos getTipoOrganizacionByCode(String code, String entidad) {
        DbConnection db = new DbConnection();
        SicresTipoOrganizacionDatos bean = new SicresTipoOrganizacionDatos();
        try {
            db.open(DBSessionManager.getSession());
            bean.load(code, db);
        } catch (Exception e) {
            logger.error("No se ha podido recuperar el tipo de organizacion");
            return null;
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return bean;
    }

    public static int crearTipoOrganizacion(SicresTipoOrganizacionImpl tipoOrganizacion, String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();
            tipoOrganizacion.setId(IdsGenerator.generateNextId(
                    IdsGenerator.SCR_TYPEADMIN, entidad));
            new SicresTipoOrganizacionDatos(tipoOrganizacion).add(db);
            db.endTransaction(true);
        } catch (Exception e) {
            if (db != null && db.inTransaction())
                try {
                    db.endTransaction(false);
                } catch (Exception e1) {
                    logger.error("Problemas con rollback");
                }
            logger.error("Error creando el tipo de organización");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return tipoOrganizacion.getId();
    }

    public static void editarTipoOrganizacion(SicresTipoOrganizacionImpl tipoOrganizacion, String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            new SicresTipoOrganizacionDatos(tipoOrganizacion).update(db);
        } catch (Exception e) {
            logger.error("Error editando el tipo de organizacion");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    public static void eliminarTipoOrganizacion(int idTypeOrg, String entidad)
            throws ISicresRPAdminDAOException {

        DbConnection db = new DbConnection();

        try {
            db.open(DBSessionManager.getSession());
            //Organizacion
            SicresTipoOrganizacionDatos org = new SicresTipoOrganizacionDatos();
            org.setId(idTypeOrg);
            org.delete(db);

            db.endTransaction(true);
        } catch (Exception e) {
            logger.error("Error eliminando el tipo de organizacion");
            throw new ISicresRPAdminDAOException(
                    ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    public static void importarDepartamentos(int deptId, boolean isSelected, String idUnidad, String entidad) throws ISicresRPAdminDAOException {
        DbConnection db = new DbConnection();
        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();

            Departamento departamento = ISicresRPAdminOficinaManager.getDepartamento(deptId, entidad);
            SicresListaDistribucionesImpl listasDistribucion = new SicresListaDistribucionesImpl();

            //Cogemos de session el idUnidad seleccionada del arbol para ponerlo como padre (menos cuando sea la raiz PROPIOS == -1)
            Integer idOrgPadre = null;
            if(idUnidad != null && !idUnidad.equals("") && Integer.parseInt(idUnidad) > 0)
                idOrgPadre = new Integer(idUnidad);

            // Si esta seleccionado el nodo, entonces lo creamos con el resto de sus hijos
            // Sino solamente creamos sus hijos
            if(isSelected){
                SicresOrganizacionImpl organizacion = transformDepartamento(departamento, idOrgPadre, entidad);
                new SicresOrganizacionDatos(organizacion).add(db);
                idOrgPadre = new Integer(organizacion.getId());

                listasDistribucion.add(generateListaDistribucion(organizacion.getId(), deptId));
            }
            importarDepartamentosHijos(db, deptId, idOrgPadre, listasDistribucion, entidad);

            //Se crean las listas de distribucion para cada departamento importado
            ISicresRPAdminDistribucionManager.crearDistribuciones(listasDistribucion, entidad);

            db.endTransaction(true);
        } catch (Exception e) {
            logger.error("Error importando departamentos estructura organizativa a unidades administrativas.");
            throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    private static void importarDepartamentosHijos(DbConnection db, int deptIdPadre, Integer idOrgPadre,
            SicresListaDistribucionesImpl listasDistribucion, String entidad) throws Exception {
        Departamentos depts = ISicresRPAdminOficinaManager.obtenerDepartamentosHijos(deptIdPadre, entidad);
        DepartamentosLista listaDept = depts.getDepartamentosLista();
        Integer idOrgPadreHijo = null;
        for(int i=0; i<listaDept.count(); i++){
            SicresOrganizacionImpl dept = transformDepartamento(listaDept.get(i), idOrgPadre, entidad);
            new SicresOrganizacionDatos(dept).add(db);
            idOrgPadreHijo = new Integer(dept.getId());

            listasDistribucion.add(generateListaDistribucion(dept.getId(), listaDept.get(i).get_id()));

            importarDepartamentosHijos(db, listaDept.get(i).get_id(), idOrgPadreHijo, listasDistribucion, entidad);
        }
    }

    public static void importarGruposLdap(String nodeDn, int maxChildrenLdap, int treeType, boolean isSelected,
                                          String idUnidad, String entidad) throws ISicresRPAdminDAOException {
        DbConnection db = new DbConnection();
        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();

            //Cogemos de session el idUnidad seleccionada del arbol para ponerlo como padre (menos cuando sea la raiz PROPIOS == -1)
            Integer idOrgPadre = null;
            if(idUnidad != null && !idUnidad.equals("") && Integer.parseInt(idUnidad) > 0)
                idOrgPadre = new Integer(idUnidad);

            LdapConnection ldapConn = new LdapConnection();

            // Obtener la información del servidor
            LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
            ldapConn.open(connCfg);

            // Obtener el rootDn
            LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
            String ldapRootDn = Ldap_url.getDN();

            LdapService service = new LdapService(maxChildrenLdap, ldapRootDn, treeType);

            //Insertar tambien el nodo seleccionado
            if(isSelected){
                SicresOrganizacionImpl grupo = transformGroupLdap(nodeDn, idOrgPadre, entidad);
                new SicresOrganizacionDatos(grupo).add(db);
                idOrgPadre = new Integer(grupo.getId());
            }
            importarGruposLdapHijos(db, service, nodeDn, idOrgPadre, entidad);

            db.endTransaction(true);
        } catch (Exception e) {
            logger.error("Error importando grupos LDAP a unidades administrativas.");
            throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
    }

    private static void importarGruposLdapHijos(DbConnection db, LdapService service, String nodeDn, Integer idOrgPadre, String entidad) throws Exception {
        List hijos = service.GetLdapChildList(nodeDn, entidad);
        Integer idGroupPadreHijo = null;
        for(int i=0; i<hijos.size(); i++){
            Info hijo = (Info)hijos.get(i);
            SicresOrganizacionImpl grupo = transformGroupLdap(hijo.getValor(), idOrgPadre, entidad);
            new SicresOrganizacionDatos(grupo).add(db);
            idGroupPadreHijo = new Integer(grupo.getId());

            importarGruposLdapHijos(db, service, hijo.getValor(), idGroupPadreHijo, entidad);
        }
    }

    private static SicresOrganizacionImpl transformDepartamento(Departamento departamento, Integer idOrgPadre, String entidad) throws Exception{
        SicresOrganizacionImpl organizacion = new SicresOrganizacionImpl();
        int id_org = IdsGenerator.generateNextId(IdsGenerator.SCR_ORGS, entidad);
        organizacion.setId(id_org);
        organizacion.setCode("PRP" + id_org);
        if(idOrgPadre != null)
            organizacion.setIdFather(idOrgPadre.intValue());
        else
            organizacion.setIdFather(DbDataType.NULL_LONG_INTEGER);
        organizacion.setAcron(getAcronOrganizacion(departamento.get_name()));
        organizacion.setName(departamento.get_name());
        organizacion.setType(SicresOrganizacionImpl.TIPO_PROPIOS);
        organizacion.setEnabled(SicresOrganizacionImpl.ENABLED);
        organizacion.setCreationDate(new Date());
        return organizacion;
    }

    private static String getAcronOrganizacion(String nameDept){
        String pattern = "[^a-zA-Z0-9]";
        String acron = "";
        if(StringUtils.isNotEmpty(nameDept)){
            String palabras[] = nameDept.split(pattern);
            for(int i=0; i<palabras.length; i++){
                if(StringUtils.isNotEmpty(palabras[i]))
                    acron += palabras[i].charAt(0);
            }
        }
        return acron;
    }

    private static SicresOrganizacionImpl transformGroupLdap(String nameGroup, Integer idOrgPadre, String entidad) throws Exception{
        SicresOrganizacionImpl organizacion = new SicresOrganizacionImpl();
        int id_org = IdsGenerator.generateNextId(IdsGenerator.SCR_ORGS, entidad);
        organizacion.setId(id_org);
        organizacion.setCode("PRP" + id_org);
        if(idOrgPadre != null)
            organizacion.setIdFather(idOrgPadre.intValue());
        else
            organizacion.setIdFather(DbDataType.NULL_LONG_INTEGER);
        organizacion.setAcron(getAcronGrupoLdap(nameGroup));
        organizacion.setName(nameGroup.split(",")[0].split("=")[1]);
        organizacion.setType(SicresOrganizacionImpl.TIPO_PROPIOS);
        organizacion.setEnabled(SicresOrganizacionImpl.ENABLED);
        organizacion.setCreationDate(new Date());
        return organizacion;
    }

    private static String getAcronGrupoLdap(String nameGroup){
        String pattern = "[^a-zA-Z0-9]";
        String acron = "";
        if(StringUtils.isNotEmpty(nameGroup)){
            String dn[] = nameGroup.split(",");
            if(dn != null && dn.length > 0){
                String palabras[] = dn[0].split("=")[1].split(pattern);
                for(int i=0; i<palabras.length; i++){
                    if(StringUtils.isNotEmpty(palabras[i]))
                        acron += palabras[i].charAt(0);
                }
            }
        }
        return acron;
    }

    private static SicresListaDistribucionImpl generateListaDistribucion(int idOrg, int idDest){
        SicresListaDistribucionImpl distList = new SicresListaDistribucionImpl();
        distList.setIdOrgs(idOrg);
        distList.setTypeDest(SicresListaDistribucionImpl.TIPO_DEPARTAMENTO);
        distList.setIdDest(idDest);
        return distList;
    }


    public static SicresOficinasImpl getOficinasUsuarioLdap(String ldapguid, String entidad) throws ISicresRPAdminDAOException {
        SicresOficinasImpl oficinas = new SicresOficinasImpl();
        DbConnection db = new DbConnection();
        try {
            db.open(DBSessionManager.getSession());
            db.beginTransaction();

            LdapConnection ldapConn = new LdapConnection();

            // Obtener la información del servidor
            LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
            ldapConn.open(connCfg);
            UasAuthConfig authCfg = UasConfigUtil.createUasAuthConfig(entidad);

            //Obtenemos el DN del usuario a partir de su ldapguid
            String userDn = LdapUasFns.findUserDnByGuid(ldapConn, authCfg.getUserStart(), LdapScope.SUBTREE, ldapguid, entidad);
            if(userDn != null ){
                //Obtenemos los grupos a los que pertenece el usuario LDAP (por el DN)
                IeciTdShortTextArrayList listaGuids = LdapUasFns.findUserGroupGuids(ldapConn, authCfg.getGroupStart(), LdapScope.SUBTREE, userDn);

                SicresOficinaImpl oficina = null;
                for(int i=0; i<listaGuids.count(); i++){
                    try{
                        oficina = ISicresRPAdminOficinaManager.getOficinaByLdapGroup(listaGuids.get(i), entidad);
                        if(oficina!=null)
                            oficinas.add(oficina);
                    }catch(Exception e){
                        // Si hay excepción es porque no existia ninguna oficina.
                    }
                }
            }

            db.endTransaction(true);
        } catch (Exception e) {
            logger.error("Error obteniendo oficinas de un usuario LDAP.");
            throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
        } finally {
            try {
                if (db != null && db.existConnection())
                    db.close();
            } catch (Exception e) {
                logger.error("No se ha podido cerrar la conexión a la BBDD");
            }
        }
        return oficinas;
    }
}
