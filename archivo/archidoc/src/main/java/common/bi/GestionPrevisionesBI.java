package common.bi;

import gcontrol.vos.UsuarioVO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import se.NotAvailableException;
import se.procedimientos.exceptions.GestorCatalogoException;
import transferencias.TipoTransferencia;
import transferencias.exceptions.PrevisionOperacionNoPermitidaException;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.TransferenciaElectronicaInfo;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;

/**
 * Interface del servicio de previsiones
 *
 */
public interface GestionPrevisionesBI {

	/**
	 * Busca las previsiones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de previsiones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrevisiones(BusquedaPrevisionesVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene el número de previsiones que pueden ser gestionadas por el
	 * usuario conectado.
	 *
	 * @return Número de previsiones
	 * @throws PrevisionOperacionNoPermitidaException
	 *             Caso de que el usuario no disponga de permisos para realizar
	 *             gestión de previsiones de transferencia
	 */
	public int getCountPrevisionesAGestionar()
			throws PrevisionOperacionNoPermitidaException;

	/**
	 * Obtiene las previsiones que pueden ser gestionadas por el usuario
	 * conectado.
	 *
	 * @return Listado de previsiones {@link PrevisionVO}
	 * @throws PrevisionOperacionNoPermitidaException
	 *             Caso de que el usuario no disponga de permisos para realizar
	 *             gestión de previsiones de transferencia
	 */
	public Collection getPrevisionesAGestionar()
			throws PrevisionOperacionNoPermitidaException;

	/**
	 * Obtiene el número de previsiones que un usuario tiene en elaboracion.
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de previsiones de transferencia
	 */
	public int getCountPrevisionesEnElaboracion(String idUser);

	/**
	 * Obtiene las previsiones que un usuario tiene en elaboracion.
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de previsiones de transferencia
	 */
	public List getPrevisionesEnElaboracion(String idUser);

	/**
	 * Obtiene las previsiones de un año para el calendario de previsiones
	 *
	 * @param anio
	 *            Anio del que se quieren obtener las transferencias
	 * @return Lista de previsiones del año indicado
	 */
	public List getPrevisionesCalendarioPrevisiones(String anio,
			String[] idsArchivo);

	/**
	 * Obtiene el número de previsiones que un usuario tiene ACEPTADAS o
	 * RECHAZADAS
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de previsiones ACEPTADAS o RECHAZADAS
	 */
	public int getCountPrevisionesAceptadasRechazadas(String idUser);

	/**
	 * Obtiene las previsiones que un usuario tiene ACEPTADAS o RECHAZADAS
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de previsiones ACEPTADAS o RECHAZADAS
	 */
	public List getPrevisionesAceptadasRechazadas(String idUser);

	/**
	 * Obtiene las previsiones de un gestor que aceptan que se creen relaciones
	 * de entrega a partir de ellas. Un usuario puede crear relaciones de
	 * entrega sobre aquellas previsiones de transferencia de las que es gestor,
	 * que hayan sido aceptadas por el archivo y cuyo rango de fechas de
	 * calendario asignadas no haya expirado
	 *
	 * @param idGestor
	 *            Identificador de gestor
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesAceptanRelaciones(String idGestor);

	/**
	 *
	 * @param ids
	 * @return Listado de las previsiones con id igual a alguno de los ids
	 *         pasados por parametros
	 * @throws Exception
	 */
	public Collection getPrevisionesXId(String[] ids);

	/**
	 * @param idPrevision
	 * @return True si la prevision tiene detalles asociados
	 * @throws Exception
	 */
	// public boolean isPrevisionConDetallesAsociados(String idPrevision);

	// /**
	// * @param idPrevision
	// * @return True si la prevision tiene relaciones asociadas
	// * @throws Exception
	// */
	// public boolean isPrevisionConRelacionesAsociadas(String idPrevision);

	// /**
	// *
	// * @param estados
	// * @return Listado de previsiones que el usuario del servicio tiene en
	// * elaboracion, por estados.
	// * @throws Exception
	// */
	// public Collection getPrevisionesEnElaboracionXEstados(int estados[]);

	/**
	 *
	 * @return Listado de las previsiones que el usuario del servicio tiene en
	 *         elaboracion
	 * @throws Exception
	 */
	// public Collection getPrevisionesEnElaboracion();

	/**
	 *
	 * @param userVO
	 * @param tipoTransferencia
	 * @param tipoPrevision
	 * @return Si el usuario userVO es de tipo archivo, devolvera null En otro
	 *         caso se comprobara si es posible crear una nueva prevision. En
	 *         caso de no serlo devolvera las previsiones por las que no es
	 *         posible la creación de la nueva prevision.
	 * @throws Exception
	 */
	// public Collection checkIfIsPosibleCreatePrevision(int tipoTransferencia,
	// int tipoPrevision);

	/**
	 * Obtiene la informacion correspondiente a una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO getPrevision(String idPrevision);

	/**
	 *
	 * @param previsionVO
	 * @return Obtencion de objeto
	 * @link PrevisionTO a partir de un
	 * @link PrevisionVO
	 * @throws Exception
	 */
	// public PrevisionTO getPrevisionTOFromPrevisionVO(PrevisionVO
	// previsionVO);

	/**
	 * @param previsionesVO
	 * @return Un listado de objetos
	 * @link PrevisionTO a partir de objetos
	 * @link PrevisionVO
	 * @throws Exception
	 */
	// public Collection getPrevisionesTOFromPrevisionesVO(Collection
	// previsionesVO);

	/**
	 * Realiza la transferencia de control de las previsiones a un nuevo usuario
	 *
	 * @param codigosPrevisiones
	 * @param idNewUser
	 * @throws Exception
	 */
	public void transfControlPrevisiones(String[] codigosPrevisiones,
			String idNewUser) throws ActionNotAllowedException;

	/**
	 * Una vez que el Órgano remitente finaliza la Previsión de Transferencia,
	 * la envía al Archivo para su revisión.
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a enviar
	 * @throws ActionNotAllowedException
	 *             El envio de la prevision no esta permitido
	 */
	public void enviarPrevision(String idPrevision)
			throws ActionNotAllowedException;

	/**
	 * Acepta una prevision de transferencia enviada al archivo Una vez que el
	 * Archivo dispone de la Previsión de Transferencia enviada por el Órgano
	 * Remitente, éste puede o bien rechazarla o bien aceptarla. Una vez
	 * aceptada, el Órgano Remitente podrá generar Relaciones de Entrega para
	 * cualquier procedimiento de la Previsión de Transferencia.
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a aceptar
	 * @param fechaIniTrans
	 *            Fecha a partir de la cual pueden ser enviadas relaciones de
	 *            entrega realizadas sobre esta prevision de tranferencia
	 * @param fechaFinTrans
	 *            Fecha hasta la que se permite el envio de relaciones de
	 *            entrega realizadas sobre esta prevision de tranferencia
	 * @throws ActionNotAllowedException
	 *             La aceptacion de la relacion de entrega no esta permitida
	 */
	public void aceptarPrevision(String idPrevision, Date fechaIniTrans,
			Date fechaFinTrans) throws ActionNotAllowedException;

	/**
	 * Rechaza una prevision de transferencia enviada al archivo Si una vez
	 * enviada el archivo decide rechazar la previsión el usuario gestor que la
	 * elaboró podrá realizar modificaciones sobre la misma
	 *
	 * @param idPrevision
	 *            Identificador de la prevision a aceptar
	 * @param motivoRechazo
	 *            Motivo por el que la prevision de transferencia es rechazada
	 * @throws ActionNotAllowedException
	 *             El rechazo de la relacion de entrega no está permitido
	 */
	public void rechazarPrevision(String codigoPrevision, String motivoRechazo)
			throws ActionNotAllowedException;

	/**
	 * Creacion de una prevision
	 *
	 * @param vo
	 * @throws Exception
	 */
	public void insertPrevision(PrevisionVO vo)
			throws ActionNotAllowedException;

	/**
	 * Actualizacion de una prevision
	 *
	 * @param prevision
	 *            Datos de prevision a actualizar
	 */
	public void actualizarPrevision(PrevisionVO prevision)
			throws ActionNotAllowedException;

	/**
	 * Modificacion el rango de fechas en las que se permite el envio de
	 * relaciones de entrega elaboradas sobre una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @param fechaInicio
	 *            Nueva fecha de inicio del perio en el que se pueden enviar al
	 *            archivo relaciones de entrega elaboradas sobre la prevision de
	 *            transferencia
	 * @param fechaFin
	 *            Nueva fecha de finalización del perio en el que se pueden
	 *            enviar al archivo relaciones de entrega elaboradas sobre la
	 *            prevision de transferencia
	 * @throws ActionNotAllowedException
	 *             La modificacion del periodo en el que se pueden enviar al
	 *             archivo relaciones de entrega elaboradas sobre la prevision
	 *             de transferencia no está permitida
	 */
	public void modificarFechasCalendario(String idPrevision, Date fechaInicio,
			Date fechaFin) throws ActionNotAllowedException;

	/**
	 * Elimina del sistema las previsiones de transferencia indicadas
	 *
	 * @param previsionesAEliminar
	 *            Identificadores de las previsiones a eliminar
	 * @throws ActionNotAllowedException
	 *             La eliminacion de alguna de las previsiones indicadas no está
	 *             premitida
	 */
	public void eliminarPrevisiones(String[] previsionesAEliminar)
			throws ActionNotAllowedException;

	/**
	 *
	 * @param codigoPrevision
	 * @return Obtiene todos los detalles de una prevision
	 * @throws Exception
	 */
	public Collection getDetallesPrevision(String codigoPrevision);

	// /**
	// * @param codigoPrevision
	// * @return Obtiene los detalles de un previsiones que no esten asociados a
	// * ninguna relacion
	// * @throws Exception
	// */
	// public Collection getDetallesPrevisionSinRelacion(String
	// codigoPrevision);

	/**
	 * Obtiene el numero de detalles que se han definido para una prevision de
	 * transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 */
	public int numeroDetallesPrevision(String codigoPrevision);

	/**
	 * Inserta un nuevo detalle a una prevision
	 *
	 * @param prevision
	 * @param infoDetallePrevision
	 * @throws Exception
	 */
	public void nuevoDetallePrevision(PrevisionVO prevision,
			DetallePrevisionVO infoDetallePrevision)
			throws ActionNotAllowedException;

	/**
	 * Actializa una linea de detalle de una prevision de transferencia
	 *
	 * @param prevision
	 *            Prevision a la que pertenece la linea de detalle
	 * @param infoDetallePrevision
	 *            Datos de la linea de detalle
	 * @throws ActionNotAllowedException
	 *             La modificacion de la linea de detalle no esta permitida
	 */
	public void modificarDetallePrevision(PrevisionVO prevision,
			DetallePrevisionVO infoDetallePrevision)
			throws ActionNotAllowedException;

	/**
	 * Elimina un conjunto de lineas de detalle de una prevision
	 *
	 * @param prevision
	 *            Prevision de la que se eliminan las lineas de detalle
	 * @param idDetallePrevision
	 *            Conjunto de identificadores de lineas de detalle
	 * @throws ActionNotAllowedException
	 *             Caso de que la eliminacion de los detalles indicados no esté
	 *             permitida
	 */
	public void eliminarDetallePrevision(PrevisionVO prevision,
			String[] idDetallePrevision) throws ActionNotAllowedException;

	/**
	 * Recupera una linea de detalle de una prevision
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @param numOrdenDetalle
	 *            Numero de linea de detalle
	 * @return Linea de detalle de prevision
	 */
	// public DetallePrevisionVO getDetallePrevision(String idPrevision, int
	// numOrdenDetalle);

	/**
	 * Recupera una linea de detalle de prevision
	 *
	 * @param idDetallePrevision
	 *            Identificador de detalle de prevision
	 * @return Detalle de prevision
	 */
	public DetallePrevisionVO getDetallePrevision(String idDetallePrevision);

	/**
	 * Pone un detalle de prevision a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre el detalle
	 *
	 * @param idDetallePrevision
	 *            Identificador de detalle de prevision
	 * @return Detalle de prevision
	 */
	public DetallePrevisionVO abrirDetallePrevision(String idDetallePrevision);

	/**
	 * Otiene los procedimientos cuyos expedientes pueden ser transferidos en la
	 * transferencia originada por una prevision de transferencia determinada
	 *
	 * @param idPrevision
	 *            Identificador de una prevision
	 * @return Lista de procedimientos {@link se.procedimientos.IProcedimiento}
	 */
	public List getProcedimientosPrevision(String idPrevision);

	/**
	 * Inicializa una nueva prevision de transferencia
	 *
	 * @param idUser
	 *            Identificador del usuario que solicita nueva prevision
	 * @param idOrg
	 *            Organo remitente de la transferencia
	 * @param tipoTransferencia
	 *            Tipo de transferencia: Ordinario, Extraordinaria sin signatura
	 *            o Extraordinaria con signatura
	 * @param tipoPrevision
	 *            Tipo de prevision: Con detalle o sin detalle
	 * @return Prevision de transferencia {@link PrevisionVO}
	 * @throws ActionNotAllowedException
	 *             En caso de que no sea posible la creacion de una prevision
	 *             con las caracteristicas indicadas
	 */
	public PrevisionVO nuevaPrevision(String idUser, String idOrg,
			int tipoTransferencia, int tipoPrevision, String idArchivo,
			String codArchivo) throws ActionNotAllowedException;

	/**
	 * Una prevision de transferencia estara disponible solo para aquel usuario
	 * que la ha abierto
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO abrirPrevision(String idPrevision);

	/**
	 * Obtiene la lista de previsiones activas que se encuentran en elaboracion
	 * en un organo remitente. La lista incluye previsiones del propio organo
	 * indicado y de sus organos dependientes
	 *
	 * @param idOrgano
	 *            Identificador de organo remitente
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesActivasEnOrgano(String idOrgano);

	/**
	 * Obtiene las previsiones que tienen a un usuario por gestor
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Lista de previsiones de transferencia
	 */
	public List getPrevisionesGestor(String idGestor);

	/**
	 * Obtiene los estados de las previsiones.
	 *
	 * @return Estados de las previsiones.
	 */
	public List getEstadosPrevisiones();

	/**
	 * Obtiene los tipos de transferencias.
	 *
	 * @return Tipos de transferencias.
	 */
	public List getTiposTransferencias();

	/**
	 * Obtiene la lista de gestores con previsiones.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrevision(String idOrgano,
			int[] tiposTransferencia);

	/**
	 * Obtiene la lista de gestores.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tipoTransferencia
	 *            Tipo de transferencia.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestores(String idOrgano, int tipoTransferencia);

	/**
	 * Asigna una lista de previsiones a un gestor.
	 *
	 * @param idsPrevisiones
	 *            Lista de identificadores de previsiones.
	 * @param tipoTransferencia
	 *            Tipo de las previsiones ({@link TipoTransferencia}).
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws PrevisionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de la
	 *             previsión
	 */
	public UsuarioVO asignarPrevisionesAGestor(String[] idsPrevisiones,
			TipoTransferencia tipoTransferencia, String idGestor)
			throws PrevisionOperacionNoPermitidaException;

/**
     * Busca los procedimientos que verifican los criterios que se indican y cuya
     * documentación puede ser transferida al archivo
     * @param tipoProcedimiento Tipo de procedimiento: AUTOMATICO o NO AUTOMATICO {@link se.procedimientos.IProcedimiento
     * @param titulo Patrón que debe estar contenido en el título del procedimiento
     * @return Lista de procedimientos {@link se.procedimientos.InfoBProcedimiento}
     * @throws GestorCatalogoException Caso de que no sea posible la comunicación con el sistema de gestión de catálogo
     * @throws NotAvailableException Caso de que la funcionalidad necesaria para la obtención de los procedimientos no
     * sea implementada por el sistema gestor de catálogo de procedimientos
     */
	public List findProcedimientosATransferir(String idFondo,
			int tipoProcedimiento, String titulo)
			throws GestorCatalogoException, NotAvailableException;

	/**
	 * Cierra las previsiones que hayan caducado.
	 */
	public void cerrarPrevisiones();

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idCampoFechaExtremaFinal
	 * @param idsUnidadesDocumentales
	 */
	public void getUInstConCondiciones(Date fechaInicial, Date fechaFinal,
			String idArchivoEmisor, String idPadre,
			String idCampoFechaExtremaFinal, List idsUnidadesDocumentales);

	/**
	 * Obtiene la prevision, si no existe la crea
	 * @param transferenciaElectronicaInfo Datos de la transfernecia
	 * @return
	 * @throws TransferenciaElectronicaException
	 */
	public void establecerPrevisionElectronica(TransferenciaElectronicaInfo transferenciaElectronicaInfo) throws TransferenciaElectronicaException;

	/**
	 * Obtiene el detalle de la previsión asociado, si no existe lo crea.
	 * @param transferenciaElectronicaInfo Datos de la transfernecia
	 * @return
	 * @throws TransferenciaElectronicaException
	 */
	public void establecerDetallePrevisionElectronica(
			TransferenciaElectronicaInfo transferenciaElectronicaInfo) throws TransferenciaElectronicaException ;

}