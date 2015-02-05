package common.bi;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import se.tramites.InfoBExpediente;
import se.tramites.exceptions.SistemaTramitadorException;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import transferencias.TipoTransferencia;
import transferencias.actions.RelacionEntregaPO;
import transferencias.electronicas.udoc.InformacionUnidadDocumentalElectronica;
import transferencias.exceptions.PrevisionOperacionNoPermitidaException;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.vos.BusquedaRelacionesVO;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.TransferenciaElectronicaInfo;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import transferencias.vos.UnidadInstalacionVO;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;
import common.pagination.PageInfo;
import common.util.IntervalOptions;

import deposito.db.ConcurrentModificationException;
import deposito.model.GestorEstructuraDepositoBI;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.OrganoProductorVO;
import gcontrol.vos.UsuarioVO;

/**
 * Interface del servicio de relaciones
 *
 */
public interface GestionRelacionesEntregaBI {

	/**
	 * Si la relación solo tiene documentos electrónicos, no se pueden añadir
	 * documentos físicos.
	 */
	public static final int ERROR_RELACION_SIN_DOCS_FISICOS = 1;

	/**
	 * Obtiene la informacion referente a un conjunto de relaciones de entrega a
	 * partir de sus identificadore
	 *
	 * @param codigos
	 *            Identificadores de relacion de entrega
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesXIds(String[] codigos);

	/**
	 * Obtiene la informacion referente a una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega
	 */
	public RelacionEntregaVO getRelacionXIdRelacion(String idRelacion);

	/**
	 * Pone una relacion de entrega a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la relacion
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega
	 */
	public RelacionEntregaVO abrirRelacionEntrega(String idRelacion);

	/**
	 * Pone un ingreso directo a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la relacion
	 *
	 * @param idIngreso
	 *            Identificador del ingreso directo
	 * @return RelacionEntregaVO
	 */
	public RelacionEntregaVO abrirIngresoDirecto(String idIngreso);

	/**
	 * Pone una unidad documental a disposicion del usuario que solicita la
	 * apertura de manera que unicamente dicho usuario puede realizar acciones
	 * sobre la unidad documental
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Relacion de entrega
	 */
	public UnidadDocumentalVO abrirUnidadDocumental(String idUnidadDocumental);

	/**
	 * Obtiene el organo productor asociado a un id de descriptor
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor del productor
	 * @return Organo Productor
	 */
	public OrganoProductorVO getOrganoProductor(String idDescriptor);

	/**
	 * Obtiene la informacion referente a una unidad documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental
	 * @return Unidad documental {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO getUnidadDocumental(String idUnidadDocumental);

	/**
	 * Permite bloquear las unidades documentales seleccionadas
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega con las unidades a bloquear
	 * @param unidadDocumentalIDs
	 *            identificadores de las unidades a bloquear
	 * @throws ActionNotAllowedException
	 */
	public void lockUnidadesDocumentales(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs) throws ActionNotAllowedException;

	/**
	 * Permite desbloquear las unidades documentales seleccionadas
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega con las unidades a desbloquear
	 * @param unidadDocumentalIDs
	 *            identificadores de las unidades a desbloquear
	 * @throws ActionNotAllowedException
	 */
	public void unlockUnidadesDocumentales(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs) throws ActionNotAllowedException;

	/**
	 * Recupera la lista de relaciones a gestionar por un usuario
	 *
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario no tenga permitido realizar gestión de
	 *             relaciones de entrega
	 */
	public Collection getRelacionesAGestionar()
			throws ActionNotAllowedException;

	/**
	 * Recupera el número de relaciones a gestionar por un usuario. Un usuario
	 * puede realizar tareas de gestion sobre aquellas relaciones de entrega de
	 * las que sea gestor y que se encuentren en estado 'Recibida', 'Con Errores
	 * de Cotejo' y 'Errores Corregidos'. Aquellos usuario que tengan ademas
	 * permiso de 'Gestion de transferencias en Archivo Receptor' pueden llevar
	 * a cabo acciones sobre relaciones dirigidas a alguno de los archivos que
	 * tienen asociados y que se encuentren en estado 'Enviada' o 'Signaturada'
	 *
	 * @return Número de relaciones de entrega
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario no tenga permitido realizar gestión de
	 *             relaciones de entrega
	 */
	public int getCountRelacionesAGestionar() throws ActionNotAllowedException;

	/**
	 * Recupera el número de unidades documentales que pertenecen a la relacion
	 * de entrega pasada como parámetro.
	 *
	 * @param codigoRelacion
	 * @return
	 */
	public int getCountUnidadesDocRe(String codigoRelacion);

	/**
	 * Recupera el número de unidades de instalacion que pertenecen a la
	 * relacion de entrega pasada como parámetro.
	 *
	 * @param codigoRelacion
	 * @return
	 */
	public int getCountUnidadesInstalacion(String codigoRelacion);

	/**
	 * Obtiene el número de unidades de instalación de la relación de entrega
	 *
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @param tipoTransferencia
	 *            Tipo de relación
	 * @return
	 */
	public int getCountUnidadesInstalacion(String idRelEntrega,
			int tipoTransferencia);

	/**
	 * Obtiene el numero de relaciones que un usuario tiene en elaboracion
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Numero de relaciones de entrega
	 */
	public int getCountRelacionesEnElaboracionXUser(String idUser);

	/**
	 * Obtiene la lista de relaciones que un usuario tiene en elaboracion
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesEnElaboracionXUser(String idUser);

	int getCountRelacionesRechazadasXUser(String idUser);

	Collection getRelacionesRechazadasXUser(String idUser);

	/**
	 * Comprueba si existe una relación abierta con el mismo formato sobre la
	 * misma serie.
	 *
	 * @param RelacionEntregaVO
	 *            relacionEntregaVO Datos de la Relación de Entrega
	 * @param idUser
	 *            Identificador del Usuario
	 * @return true si existe. false en caso contrario.
	 */
	public boolean existeRelacionAbiertaXUsuario(
			RelacionEntregaVO relacionEntregaVO);

	/**
	 * Comprueba si existe una relación abierta con el mismo formato sobre la
	 * misma serie dentro del tipo de transferencia de la relación.
	 *
	 * @param RelacionEntregaVO
	 *            relacionEntregaVO Datos de la Relación de Entrega
	 * @param idUser
	 *            Identificador del Usuario
	 * @return true si existe. false en caso contrario.
	 */
	public boolean existeRelacionAbiertaXUsuarioYTipoYArchivo(
			RelacionEntregaVO relacionEntregaVO);

	/**
	 * Obtiene el numero de relaciones de entrega del año en curso que estan
	 * siendo elaboradas por un determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Numero de relaciones de entrega
	 */
	public int getCountRelacionesFinalizadasXUser(String idUser);

	/**
	 * Obtiene la lista de relaciones que un usuario tiene finalizadas
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de relaciones de entrega finalizadas
	 *         {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesFinalizadasXUser(String idUser);

	/**
	 * Obtiene El número de relaciones
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de relaciones de entrega finalizadas
	 *         {@link RelacionEntregaVO}
	 */
	public int getCountRelacionesXUser(String idUser);

	/**
	 * Obtiene el número de ingresos del año en curso que estan siendo
	 * elaborados por un determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de ingresos
	 */
	public int getCountIngresosEnElaboracionXUser(String idUser);

	/**
	 * Obtiene los ingresos del año en curso que estan siendo elaborados por un
	 * determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de ingresos {@link RelacionEntregaVO}
	 */
	public Collection getIngresosEnElaboracionXUser(String idUser);

	/**
	 * Obtiene el número de ingresos del año en curso que han sido finalizados
	 * por un determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de ingresos
	 */
	public int getCountIngresosFinalizadosXUser(String idUser);

	/**
	 * Obtiene los ingresos del año en curso que han sido finalizados por un
	 * determinado usuario
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de ingresos {@link RelacionEntregaVO}
	 */
	public Collection getIngresosFinalizadosXUser(String idUser);

	/**
	 * Transferencia de control de gestor de archivo de las previsiones de
	 * codigo
	 *
	 * @param codigosPrevisiones
	 * @param codigosPrevisiones
	 * @param idNewUserGArchivo
	 * @throws Exception
	 */
	public void transfControlRelacionesGArchivo(String[] codigosPrevisiones,
			String idNewUserGArchivo) throws ActionNotAllowedException;

	/**
	 * Transferencia de control de gestor de oficina de la previsiones de codigo
	 * alguno de los indicados en
	 *
	 * @param codigosPrevisiones
	 * @param codigosPrevisiones
	 * @param idNewUserRem
	 * @throws Exception
	 */
	public void transfControlRelacionesGOficina(String[] codigosPrevisiones,
			String idNewUserRem) throws ActionNotAllowedException;

	/**
	 * Permite enviar un ingreso directo para seleccionar la ubicación
	 *
	 * @param idIngreso
	 *            Id del ingreso
	 * @return RelacionEntregaVO
	 * @throws ActionNotAllowedException
	 */
	public RelacionEntregaVO enviarSeleccionarUbicacionIngresoDirecto(
			String idIngreso) throws ActionNotAllowedException;

	/**
	 * Envio de la relacionVO
	 *
	 * @param idRelacion
	 *            Identificador de la relacion a enviar
	 * @return {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             En caso de que el envio no pueda ser llevado a cabo
	 * @throws RelacionOperacionNoPermitidaException
	 *
	 */
	public RelacionEntregaVO enviarRelacionEntrega(String idRelacion)
			throws ActionNotAllowedException,
			RelacionOperacionNoPermitidaException;

	// /**
	// * Recepcion de una relacion de entraga sin reserva, asignandole una
	// * ubicacion por defecto
	// *
	// * @param idUsrArchivo
	// * @param codigoRelacion
	// * @param idUbicacion
	// * @throws Exception
	// */
	//
	// public void recibirRelacionEntregaSinReservaEnNoAsignable(String
	// idUsrArchivo,
	// String codigoRelacion, String idUbicacion) throws
	// ActionNotAllowedException;

	/**
	 * Recepcion de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega a recibir
	 * @param idGestor
	 *            Identificador del usuario que es asignado como gestor de la
	 *            relación
	 * @param idFormato
	 *            Identificador del formato de las unidades de instalación en
	 *            las que viene organizada la documentación de la relación
	 * @param tipoDocumental
	 *            Tipo documental de las unidades documentales incluidas en la
	 *            relación
	 * @param idUbicacion
	 *            Ubicación del depósito físico que se asigna como destino donde
	 *            previsiblemente se realizará la ubicación de la relación
	 * @param idDepositoReserva
	 *            Identificador del elemento del depósito físico donde se
	 *            solicita reserva de espacio para ubicar la relación
	 * @param idTipoDepositoReserva
	 *            Tipo del deposíto físico donde se solicita la reserva.
	 * @param idElementoSeleccionadoReserva
	 *            Id seleccionado para la reserva.
	 * @param idTipoElementoSeleccionadoReserva
	 *            Tipo del elemento seleccionado para la reserva.
	 * @throws ActionNotAllowedException
	 *             Caso de que la recepción de la relación de entrega no esté
	 *             permitida
	 */
	public void recibirRelacionEntrega(String idRelacion, String idGestor,
			String idFormato, String tipoDocumental, String idUbicacion,
			String idDepositoReserva, String idTipoDepositoReserva,
			String idElementoSeleccionadoReserva,
			String idTipoElementoSeleccionadoReserva)
			throws ActionNotAllowedException;

	/**
	 * Recepcion de una relacion de entrega sin documentos físicos
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega a recibir
	 * @param idGestor
	 *            Identificador del usuario que es asignado como gestor de la
	 *            relación
	 * @param idFormato
	 *            Identificador del formato de las unidades de instalación en
	 *            las que viene organizada la documentación de la relación
	 * @param tipoDocumental
	 *            Tipo documental de las unidades documentales incluidas en la
	 *            relación
	 * @throws ActionNotAllowedException
	 *             Caso de que la recepción de la relación de entrega no esté
	 *             permitida
	 */
	/*
	 * public void recibirRelacionEntregaSinDocsFisicos( String idRelacion,
	 * String idGestor, String idFormato, String tipoDocumental) throws
	 * ActionNotAllowedException;
	 */

	/**
	 * Crea una nueva relacion de entrega en el sistema
	 *
	 * @param idPrevision
	 *            Identificador de al prevision sobre la que se hace la relacion
	 * @param idDetallePrevision
	 *            Detalle de prevision sobre el que se hace la relacion. En caso
	 *            de tratarse de una prevision no detallada sera null
	 * @param idSerie
	 *            Serie a la que iran a parar las unidades documentales que se
	 *            incluyan en la relacion de entrega. Su valor solo se tendra en
	 *            consideracion en caso de que no se especifique detalle de
	 *            prevision
	 * @param idFormato
	 *            Identificador del formato de la relacion
	 * @param formaDocumental
	 *            Forma documental de las unidades documentales de la relación
	 * @param observaciones
	 *            Observaciones que se quieran adjuntar a la relacion a crear
	 * @param idDescriptorProductorRelacion
	 *            Id del productor por defecto de la relación
	 * @param idFondoOrigen
	 *            Id del fondo origen
	 * @return La relacion de entrega creada {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             En caso de que la creacion de una relacion con la informacion
	 *             suministrada no este permitida para el usuario que lo
	 *             solicita
	 */
	public RelacionEntregaVO insertRelacion(String idPrevision,
			String idDetallePrevision, String idSerie, String idFormato,
			String formaDocumental, String observaciones,
			String idDescriptorProductorRelacion, String idFondoOrigen,
			String idNivelDocumental, String idFicha)
			throws ActionNotAllowedException;

	/**
	 * Crea un ingreso directo en el sistema
	 *
	 * @param idArchivo
	 *            Id del archivo receptor
	 * @param idFondo
	 *            Id del fondo destino
	 * @param idSerie
	 *            Id de la serie destino
	 * @param idFormato
	 *            Id del formato
	 * @param formaDocumental
	 *            Id de la forma documental
	 * @param observaciones
	 *            Observaciones
	 * @param idDescrProductor
	 *            Id del productor
	 * @param idNivelDocumental
	 *            nivel documental de las unidades documentales del ingreso
	 * @param idFicha
	 *            Identificador de la ficha que utiliza
	 * @param idRevDoc
	 *            Identificador de la Revisión de la Documentación a la que va a
	 *            asociada.
	 * @return RelacionEntregaVO
	 * @throws ActionNotAllowedException
	 */
	public RelacionEntregaVO insertIngresoDirecto(String idArchivo,
			String idFondo, String idSerie, String idFormato,
			String formaDocumental, String observaciones,
			String idDescrProductor, String idNivelDocumental, String idFicha,
			String idRevDoc) throws ActionNotAllowedException;

	/**
	 * Crea un ingreso directo en el sistema
	 *
	 * @param idArchivo
	 *            Id del archivo receptor
	 * @param idFondo
	 *            Id del fondo destino
	 * @param idSerie
	 *            Id de la serie destino
	 * @param idFormato
	 *            Id del formato
	 * @param formaDocumental
	 *            Id de la forma documental
	 * @param observaciones
	 *            Observaciones
	 * @param idDescrProductor
	 *            Id del productor
	 * @param idNivelDocumental
	 *            nivel documental de las unidades documentales del ingreso
	 * @return RelacionEntregaVO
	 * @throws ActionNotAllowedException
	 */
	/*
	 * public RelacionEntregaVO insertIngresoDirecto(String idArchivo, String
	 * idFondo, String idSerie, String idFormato, String formaDocumental, String
	 * observaciones, String idDescrProductor, String idNivelDocumental, String
	 * idFicha) throws ActionNotAllowedException;
	 */

	/**
	 * Actualizacion de relacion de entrega
	 *
	 * @param relacionVO
	 *            Datos de la relacion a actualizar
	 * @throws ActionNotAllowedException
	 *             Cuando la actualizacion de la relacion no esté permitida
	 */
	public void updateRelacion(RelacionEntregaVO relacionVO)
			throws ActionNotAllowedException;

	/**
	 * Eliminacion de relacioens de entrega
	 *
	 * @param idsRelacionesEntrega
	 *            Lista de los identificadores de las relaciones de entrega a
	 *            eliminar
	 * @throws ActionNotAllowedException
	 *             Cuando la eliminación de alguna de las relaciones de entrega
	 *             no esté permitida
	 */
	public void eliminarRelaciones(String[] idsRelacionesEntrega)
			throws ActionNotAllowedException;

	/**
	 * Eliminacion de ingresos directos
	 *
	 * @param idsIngresos
	 *            Lista de los identificadores de los ingresos a eliminar
	 * @throws ActionNotAllowedException
	 *             Cuando la eliminación de alguno de los ingresos no esté
	 *             permitida
	 */
	public void eliminarIngresosDirectos(String[] idsIngresos)
			throws ActionNotAllowedException;

	/**
	 * Guardar una situacion de cotejo de una relacion de entrega de codigo
	 *
	 * @param codRelacionEntrega
	 *            con la unidades de instalacion en el estado en que se
	 *            encuentran en
	 * @param unidadesInstalacionCVO
	 * @param codRelacionEntrega
	 * @param unidadesInstalacionCVO
	 * @throws Exception
	 */
	public void guardarCotejo(String codRelacionEntrega, Map unidadesInstalacion);

	/**
	 * Guardar una situacion de cotejo de una relacion de entrega de codigo
	 *
	 * @param codRelacionEntrega
	 *            con la unidades de instalacion en el estado en que se
	 *            encuentran en
	 * @throws Exception
	 */
	public void guardarCotejoUDocsElectronicas(String codRelacionEntrega,
			Map udocsElectronicas, Map observacionesError);

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUnidadesDocumentales(String idRelacion);

	public List getUnidadesDocumentales(String[] idsUDocs);

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocs(String idRelacion, PageInfo pageInfo,
			boolean tieneDescripcion) throws TooManyResultsException;

	public List getUdocs(String idRelacion, boolean tieneDescripcion);

	/**
	 * Obtiene las unidades documentales físicas incluidas en una relacion de
	 * entrega que incluyen documentos fisicos
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	// public List getUdocsConDocumentosFisicos(String idRelacion, PageInfo
	// pageInfo) throws TooManyResultsException;
	public List getUdocsConDocumentosFisicos(String idRelacion);

	/**
	 * Obtiene las unidades documentales incluidas en una relacion de entrega
	 * que incluyen solo documentos electronicos
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 * @throws TooManyResultsException
	 */
	public List getUdocsConDocumentosElectronicos(String idRelacion,
			PageInfo pageInfo) throws TooManyResultsException;

	public List getUdocsConDocumentosElectronicos(String idRelacion);

	/**
	 * Incorpora una unidad documental a una relacion de entrega
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega a la que se quiere incorporar la unidad
	 *            documental
	 * @param udoc
	 *            Datos de unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param subtipo
	 *            Subtipo de elemento
	 * @param incrementoOrden
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporacion de unidades documentales a la
	 *             relación de entrega no esté permitida
	 */
	public void nuevaUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signatura, int subtipo,
			int incrementoOrden) throws ActionNotAllowedException;

	/**
	 * Incorpora una unidad documental a una relacion de entrega
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega a la que se quiere incorporar la unidad
	 *            documental
	 * @param udoc
	 *            Datos de unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param subtipo
	 *            Subtipo de elemento
	 * @param incrementoOrden
	 * @param copiarUdocsRelacionadas
	 *            Indica si se deben copiar las unidades documentales
	 *            relacionadas de la unidad origen
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporacion de unidades documentales a la
	 *             relación de entrega no esté permitida
	 */
	public void nuevaUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signatura, int subtipo,
			int incrementoOrden, boolean copiarUdocsRelacionadas)
			throws ActionNotAllowedException;

	/**
	 * Actualiza la informacion de una unidad documental incluida en una
	 * relacion de entrega
	 *
	 * @param relacionEntrega
	 *            Relacion de entrega en la que esta incluida la unida
	 *            documental
	 * @param udoc
	 *            Datos de la unidad documental a actualizar
	 * @throws ActionNotAllowedException
	 *             Caso de que la modificacion de la unidad documental no esté
	 *             permitida
	 */

	public void modificarUnidadDocumental(RelacionEntregaVO relacionEntrega,
			UnidadDocumentalVO udoc, String signaturaUI, String signaturaUdoc)
			throws ActionNotAllowedException;

	public void modificarInformacionUnidadDocumental(
			RelacionEntregaVO relacionEntrega, UnidadDocumentalVO udoc)
			throws ActionNotAllowedException;

	public void updateNumPartesUdoc(String udocID, int numPartes);

	/**
	 * Movemos una parte de unidad documental de una unidad de instalacion a
	 * otra
	 *
	 * @param parteUdoc
	 * @param unidadInstalacionID
	 * @throws Exception
	 */
	public void moveUdocToUi(IParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID);

	/**
	 * Inserta una parte de unidad documental en un unidad de instalacion
	 *
	 * @param parteUdoc
	 * @param unidadInstalacionID
	 * @throws Exception
	 */
	public void putUdocEnUi(ParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID);

	/**
	 * Elimina una parte de unidad documental de una unidad de instalacion
	 *
	 * @param parteUdoc
	 * @param unidadInstalacionID
	 * @throws Exception
	 */
	public void removeUdocFromUi(IParteUnidadDocumentalVO parteUdoc,
			String unidadInstalacionID);

	/**
	 * Saca las partes de unidades documentales que se encuentran en la posicion
	 * dentro de la caja
	 *
	 * @param posiciones
	 * @param unidadInstalacionID
	 */
	public void removeUdocsFromUi(int[] posiciones, String unidadInstalacionID)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Saca las partes de unidades documentales que se encuentran en la posicion
	 * dentro de la caja para cuando la relación está con errores de cotejo.
	 *
	 * @param posiciones
	 * @param unidadInstalacionID
	 */
	public void removeUdocsFromUiConErrores(int[] posiciones,
			String unidadInstalacionID)
			throws RelacionOperacionNoPermitidaException;

	public void removeUnidadInstalacion(RelacionEntregaVO relacionEntrega,
			UnidadInstalacionVO unidadInstalacion)
			throws RelacionOperacionNoPermitidaException;

	// public List getUnidadesDocumentalesNoAsignadas(String idRelacion);

	public UnidadInstalacionVO nuevaUnidadInstalacion(
			RelacionEntregaVO relacionEntrega, int numeroCaja,
			String signatura, String iduiubicada);

	public boolean existsUdocsEstado(String codigoRelacion, int estado);

	public boolean existsUInstalacionEstado(String codigoRelacion, int[] estados);

	/**
	 * Finaliza el proceso de correcion de errores de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @throws ActionNotAllowedException
	 *             Cuando la finalizacion de correccion de errores no esta
	 *             permitida para la relacion de entrega indicada
	 */
	public void finalizarCorreccionErrores(String idRelacion)
			throws ActionNotAllowedException;

	/**
	 * Finalizacion del proceso de cotejo de una relacion de entrega
	 *
	 * @param relacionSeleccionada
	 * @throws Exception
	 */
	public void finalizarCotejo(RelacionEntregaVO relacionSeleccionada,
			Map unidadesInstalacion) throws ActionNotAllowedException;

	/**
	 * Obtiene el número de relaciones de entrega para las que esta solicitada
	 * una reserva de espacion en el deposito fisico
	 *
	 * @return Número de relaciones de entrega
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario que solicita relaciones a reservar no
	 *             tenga permiso para realizar la gestión de reservas de espacio
	 */
	public int getCountRelacionesAReservar() throws ActionNotAllowedException;

	/**
	 * Obtiene las relaciones de entrega para las que esta solicitada una
	 * reserva de espacion en el deposito fisico
	 *
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 * @throws ActionNotAllowedException
	 *             Caso de que el usuario que solicita relaciones a reservar no
	 *             tenga permiso para realizar la gestión de reservas de espacio
	 */
	public Collection getRelacionesAReservar() throws ActionNotAllowedException;

	/**
	 * Obtiene el número de relaciones de entrega que estan en disposicion de
	 * ser ubicadas en el deposito fisico
	 *
	 * @return Número de relaciones de entrega
	 */
	public int getCountRelacionesAUbicar(String[] archivosCustodia);

	/**
	 * Obtiene las relaciones de entrega que estan en disposicion de ser
	 * ubicadas en el deposito fisico
	 *
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesAUbicar(String[] archivosCustodia);

	/**
	 * Actualizacion de una undidad de instalacion
	 *
	 * @param unaUI
	 * @throws Exception
	 */
	public void updateUnidadInstalacion(UnidadInstalacionVO unaUI);

	/**
	 * Modifica el estado de cotejo de las unidades de instalación.
	 *
	 * @param ids
	 *            Identificadores de las unidades de instalación.
	 * @param estado
	 *            Estado de cotejo.
	 * @param relacionEntrega
	 *            Relación de entrega.
	 */
	public void updateEstadoUnidadesInstalacion(String[] ids, int estado,
			RelacionEntregaVO relacionEntrega);

	/**
	 * Actualizacion de la descripcion de una unidad de instalacion
	 *
	 * @param idUInstalacion
	 * @param pos
	 * @param descripcion
	 * @throws Exception
	 */
	public void updateDescripcionUdoc(String idUdoc, int pos, String descripcion);

	/**
	 * Realiza la validación de un relación de entrega lo que supone la
	 * incorporación al cuadro de clasificación de fondos documentales de las
	 * unidades documentales incluídas en la relación de entrega
	 *
	 * @param relacion
	 *            Relación de entrega a validar
	 * @param idNivelUnidadDocumental
	 *            Nivel en el cuadro de clasificación de las unidades
	 *            documentales a generar
	 * @throws ActionNotAllowedException
	 *             Caso de que la validación de la relación no esté permitida
	 * @throws Exception
	 */
	public void validarRelacion(RelacionEntregaVO relacion,
			String idNivelUnidadDocumental, boolean comprobarPermisos, Collection udocsRelacion)
			throws ActionNotAllowedException, Exception;

	/**
	 * Método para signaturar, ubicar y validar un ingreso
	 *
	 * @param ingreso
	 *            Ingreso a validar
	 * @param huecosLibresAOcupar
	 *            Huecos libres a ocupar
	 * @param idElementoDestino
	 *            Id del elemento destino
	 * @param tipoElementoDestino
	 *            Tipo del elemento destino
	 * @param idNivelUnidadDocumental
	 *            Id del nivel unidad documental
	 * @throws ActionNotAllowedException
	 *             Si no se permite ubicar el ingreso
	 * @throws ConcurrentModificationException
	 *             Si no se puede ubicar en los huecos por estar usados
	 * @throws ArchivoModelException
	 *             Si no se puede conectar con el gestor de organización
	 */
	public void signaturarUbicarValidarIngreso(RelacionEntregaVO ingreso,
			List huecosLibresAOcupar, String idElementoDestino,
			String tipoElementoDestino, String idNivelUnidadDocumental)
			throws ActionNotAllowedException, ConcurrentModificationException,
			ArchivoModelException;

	/**
	 *
	 * @param idRelacionEntrega
	 * @return numero de unidades de instalacion de una relacion de entrega
	 * @throws Exception
	 */
	public int getNUnidadesInstalacion(String idRelacionEntrega);

	/**
	 *
	 * @param idPrevision
	 * @return Listado de relaciones que provienen de una prevision
	 * @throws Exception
	 */
	public List findByPrevision(String idPrevision);

	List findByPrevision(String idPrevision, int[] estados);

	// TODO Revisar: Creo q sobraria el idPrevision
	/**
	 *
	 * @param idPrevision
	 * @param idDetalle
	 * @return Listado de relaciones a partir del detalle de una prevision
	 * @throws Exception
	 */
	public List findByDetallePrevision(String idPrevision, String idDetalle);

	/**
	 * Elimina una parte de una unidad documental
	 *
	 * @param unidadDocumental
	 *            Unidad documental
	 * @param numParteUdoc
	 *            Numero de parte a eliminar de la unidad documental
	 */
	public void removeParteUdoc(IParteUnidadDocumentalVO parteUnidadDocumental)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Obtiene la lista de expedientes que es posible incorporar a una relacion
	 * de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de expedientes {@link se.tramites.InfoBExpediente}
	 * @throws SistemaTramitadorException
	 *             Caso de que se produzca un error obteniendo los expedientes a
	 *             partir del sistema de tramitación automatizado
	 * @throws NotAvailableException
	 *             Caso de que el sistema de tramitamitación del que es
	 *             necesario obtener los expedientes no soporte la funcionalidad
	 *             necesaria para suministrar la información requerida para la
	 *             incorporación de los expedientes al sistema de gestión de
	 *             archivo
	 * @throws ActionNotAllowedException
	 *             Caso de que la obtención d
	 */
	public List getExpedientesParaRelacion(String idRelacion)
			throws SistemaTramitadorException, NotAvailableException;

	/**
	 * Devuelve la lista de posibles gestores de una relacion
	 *
	 * @param relacionVO
	 *            Relacion para la que se buscaran posibles gestores
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getPosiblesGestores(RelacionEntregaVO relacionVO);

	/**
	 * Establece la ubicación del fondo físico en la que será depositada la
	 * documentación incluída en una relación de entrega una vez finalizado el
	 * proceso de transferencia
	 *
	 * @param relacionEntrega
	 *            Relación de entrega
	 * @param idUbicacion
	 *            Identificador de ubicación del fondo físico manejado por el
	 *            sistema
	 */
	public void updateDestinoRelacion(RelacionEntregaVO relacionEntrega,
			String idubicacionseleccionada) throws ActionNotAllowedException;

	/**
	 * Elimina un conjunto de unidades documentales incluidas en una relación de
	 * entrega
	 *
	 * @param relacionEntrega
	 *            Relación de entrega
	 * @param unidadDocumentalIDs
	 *            Conjunto de identificadores de unidad documental
	 * @throws ActionNotAllowedException
	 *             Caso de que no se permita realizar cambios sobre la relación
	 *             de entrega
	 */
	public void eliminarUnidadDocumental(RelacionEntregaVO relacionEntrega,
			String[] unidadDocumentalIDs, int subtipo)
			throws ActionNotAllowedException;

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List getUnidadesInstalacion(String idRelacion);

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega
	 *
	 * @param tipoUInstalacion
	 *            Identificador del tipo de unidad de instalación a mostrar
	 * @param asignado
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List getUnidadesInstalacion(String idRelacion, boolean asignado);

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega.
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param ordenes
	 *            Órdenes.
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List getUnidadesInstalacion(String idRelacion,
			IntervalOptions ordenes);

	/**
	 * Obtiene la unidades de instalación de una relacion de entrega
	 *
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalación
	 * @return Unidad de instalación
	 */
	public UnidadInstalacionVO getUnidadInstalacion(String idUnidadInstalacion);

	/**
	 * Obtiene las unidades documentales incluidas en una unidad de instalcion
	 *
	 * @param id
	 *            Identificador de unidad de instalcion
	 * @return Lista de partes de unidad documental
	 *         {@link ParteUnidadDocumentalVO}
	 */
	public List getUdocsEnUI(String id);

	/**
	 * Obtiene las unidades documentales incluidas en varias unidades de
	 * instalacion
	 *
	 * @param idsUnidadInstalacion
	 *            Identificador de unidades de instalcion
	 * @return Map con clave identificador de unidad de instalacion y valor
	 *         Lista de {@link ParteUnidadDocumentalVO}
	 */
	public Map getUdocsEnUIs(String[] idsUnidadInstalacion);

	/**
	 * Incorpora a una relacion de entrega unidades documentales a partir de un
	 * conjunto de expedientes
	 *
	 * @param orden
	 *            a partir del cual se van a insertar los diferentes expedientes
	 *            a la relación de entrega
	 * @param relacion
	 *            Relacion de entrega a la que se incorporarn las unidades
	 *            documentales
	 * @param expedientes
	 *            Lista de expedientes a incorporar {@link InfoBExpediente}.
	 *            Tras la finalización del proceso permanecen en esta lista
	 *            aquellos expedientes cuyo plazo de conservación ha vencido y
	 *            por tanto deben ser eliminados directamente en lugar de ser
	 *            incorporados en la transferencia
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporación de unidades documentales a la
	 *             relación de entrega no esté permitida
	 * @throws SistemaTramitadorException
	 *             Caso de error en la importación desde el sistema tramitador
	 *             de la información de los expedientes
	 * @throws NotAvailableException
	 *             Caso de que el sistema tramitador no ofrezca la funcionalidad
	 *             necesaria para la importación de la información necesaria
	 *             para la generación de las unidades documentales a partir de
	 *             los expedientes administrativos con los que se corresponden
	 *
	 */
	public int crearUnidadesDocumentales(Integer orden,
			RelacionEntregaVO relacion, List expedientes, int subtipo)
			throws ActionNotAllowedException, SistemaTramitadorException,
			GestorOrganismosException, NotAvailableException;

	public List getPartesUnidadDocumental(UnidadDocumentalVO unidadDocumental);

	/**
	 *
	 * @param idFondo
	 * @return las relaciones del fondo en un estado mas alla de enviada
	 */
	public List getRelacionesAceptadas(String idFondo);

	/**
	 * Obtiene la lista de los posibles roles que un interesado puede jugar en
	 * un expediente
	 *
	 * @return Lista de String con la descripción de los roles
	 */
	public List getListaRolesInteresado();

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega.
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacion(String idRelacion);

	public List getUbicacionesRelacionReencajado(String idRelacion);

	List getUbicacionesRelacionOuterJoin(String idRelacion,
			IntervalOptions ordenes, String idDepositoDestino);

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro.
	 *
	 * @param idElemento
	 *            Identificador del elemento del cuadro.
	 * @param tipoElemento
	 *            Tipo de elemento del cuadro (
	 *            {@link ElementoCuadroClasificacion}).
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByElemento(String idElemento, int tipoElemento,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene las relaciones cuyas unidades de instalación sean de un
	 * determinado formato que han sido recibidas y que se encuentran pendientes
	 * de ser ubicadas en una ubicación
	 *
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param formato
	 *            Identificador de formato. Puede ser nulo
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public List getRelacionesPendientesDeUbicar(String idUbicacion,
			String formato);

	/**
	 * Obtiene los estados de las relaciones.
	 *
	 * @return Estados de las relaciones.
	 */
	public List getEstadosRelaciones();

	/**
	 * Busca las relaciones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de relaciones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getRelaciones(BusquedaRelacionesVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene las relaciones cedibles que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de relaciones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getRelacionesCedibles(BusquedaRelacionesVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene los ingresos directos que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de ingresos directos.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getIngresosDirectosCedibles(BusquedaRelacionesVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de gestores en archivo con relaciones de entrega.
	 *
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresArchivoConRelaciones();

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia);

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @param idsExcluir
	 *            Ids de gestores a excluir
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia, String[] idsExcluir);

	/**
	 * Asigna una lista de relaciones de entrega a un gestor.
	 *
	 * @param idsRelaciones
	 *            Lista de identificadores de relaciones de entrega.
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws RelacionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de las
	 *             relaciones de entrega.
	 */
	public UsuarioVO asignarRelacionesAGestorArchivo(String[] idsRelaciones,
			String idGestor) throws PrevisionOperacionNoPermitidaException;

	/**
	 * Asigna una lista de relaciones de entrega a un gestor de órgano
	 * remitente.
	 *
	 * @param idsRelaciones
	 *            Lista de identificadores de relaciones de entrega.
	 * @param idGestor
	 *            Identificador del gestor.
	 * @return Información del gestor.
	 * @throws RelacionOperacionNoPermitidaException
	 *             si el gestor no puede recibir la cesión del control de las
	 *             relaciones de entrega.
	 */
	public UsuarioVO asignarRelacionesAGestorOrgRemitente(
			String[] idsRelaciones, String idGestor)
			throws PrevisionOperacionNoPermitidaException;

	/**
	 * @param idRelacion
	 * @return
	 */
	public List getExpedientesSinCaja(String idRelacion);

	public UnidadInstalacionVO crearCajaSignaturada(String idRelacion,
			String idArchivoReceptor, String[] udocIDs, String signatura,
			boolean asignando, String formato)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * @param idRelacion
	 * @param udocSeleccionada
	 */
	// public UnidadInstalacionVO crearCajaSignaturada(String idRelacion, String
	// idArchivoReceptor, String[] udocIDs,
	// String signatura) throws RelacionOperacionNoPermitidaException;
	public UnidadInstalacionVO crearCajaSinSignatura(String idRelacion,
			String[] udocIDs);

	/**
	 * Comprueba si se pueden añadir o quitar expedientes
	 *
	 * @param undidadInstalacionEnRelacion
	 * @throws RelacionOperacionNoPermitidaException
	 */
	public void checkModificablePorExpedientes(
			UnidadInstalacionVO undidadInstalacionEnRelacion)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Comprueba que todos los huecos asociados a una relacion de entrega estén
	 * libres y existan y ademas que no esten reservados por la propia relacion
	 * de entrega.
	 *
	 * @param idRelacion
	 *            Identificador de la relacion
	 * @throws RelacionOperacionNoPermitidaException
	 */
	public void checkCorreccionHuecosAsociadosRelacionLibres(String idRelacion)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Comprueba que todos los huecos asociados a una relacion de entrega estén
	 * libres y existan
	 *
	 * @param idRelacion
	 *            Identificador de la relacion
	 * @param finalizarCorreccion
	 *            para saber si estamos finalizando corrección o recibiendo la
	 *            relación de entrega
	 * @throws RelacionOperacionNoPermitidaException
	 */
	public void checkHuecosAsociadosRelacionLibres(String idRelacion,
			boolean finalizarCorreccion)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Incorporacion de undidades documentales a la caja de la relacion
	 *
	 * @param idRelacion
	 * @param udocSeleccionada
	 */
	public void incorporarACaja(
			UnidadInstalacionVO undidadInstalacionEnRelacion, String[] udocsIDS)
			throws RelacionOperacionNoPermitidaException;

	/**
	 *
	 * @param posicionesASubir
	 *            : las posisiciones de la unidades documentales que se van a
	 *            desplazar hacia arriba
	 * @param idUnidadInstalacion
	 */
	public void subirExpediente(List partesASubir, String idUnidadInstalacion)
			throws RelacionOperacionNoPermitidaException;

	/**
	 *
	 * @param posicionesABajar
	 *            : las posisiciones de la unidades documentales que se van a
	 *            desplazar hacia abajo
	 * @param idUnidadInstalacion
	 */
	public void bajarExpediente(List partesABajar, String idUnidadInstalacion)
			throws RelacionOperacionNoPermitidaException;

	public void checkIsPartDivisible(ParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException;

	public void checkIsPartEliminable(IParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * 1º Divide una parte de unidad documental 2º Crear una nueva caja 3º Mete
	 * la parte creada en esa caja 4º Actualiza el numero de parte de la unidad
	 * documental a la que pertence la parte
	 *
	 * @param parteUDoc
	 */
	public UnidadInstalacionVO dividirParteUdoc(RelacionEntregaVO relacion,
			ParteUnidadDocumentalVO parteUDoc)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Divide una unidad documental a cajas existentes y actualiza el numero de
	 * parte de la unidad documental.
	 *
	 * @param relacion
	 * @param parteUDoc
	 * @param signatura
	 * @return
	 * @throws RelacionOperacionNoPermitidaException
	 */
	public UnidadInstalacionVO dividirParteUdocEnUIExistente(
			RelacionEntregaVO relacion, IParteUnidadDocumentalVO parteUDoc,
			String signatura) throws RelacionOperacionNoPermitidaException;

	/**
	 * Permite modificar la signatura de una caja
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param caja
	 *            Caja a modificar
	 * @param signaturaCaja
	 *            Signatura de la caja
	 * @param idArchivoReceptor
	 *            Identificador del archivo receptor
	 */
	public void updateSignaturaCaja(String idRelacion,
			UnidadInstalacionVO caja, String signaturaCaja,
			String idArchivoReceptor)
			throws RelacionOperacionNoPermitidaException;

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion);

	public void validarMovimientoUnidades(List partesAMover,
			String unidadInstalacionID, int offset)
			throws RelacionOperacionNoPermitidaException;

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idFormato
	 * @param idCampoFechaExtremaFinal
	 * @param idsUnidadesDocumentales
	 * @param idUnidadInstalacion
	 */
	// public void getUInstConCondiciones(Date fechaInicial, Date fechaFinal,
	// String idArchivoEmisor, String idPadre, String idFormato, String
	// idCampoFechaExtremaFinal, List idsUnidadesDocumentales, String
	// idUnidadInstalacion);

	/**
	 * Devuelve una Lista de UnidadInstalacionVO
	 *
	 * @param idUnidadInstalacion
	 *            Id de la Unidad de Instalación
	 * @return Lista de UnidadInstalacionVO
	 */
	// public List getUdocsUInst(String idUnidadInstalacion);

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idCampoFechaExtremaFinal
	 * @return
	 */
	public List getUDocsXArchivoYSerieYFormatoConFechas(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idSerieOrigen);

	/**
	 * Devuelve las Unidades de Instalación para la relación entre archivos.
	 *
	 * @param fechaFinalDesde
	 *            Fecha Final Desde
	 * @param fechaFinalHast
	 *            Fecha Final Hasta
	 * @param idArchivoEmisor
	 *            Identificador del Id de Archivo Emisor
	 * @param idSerieOrigen
	 *            Id de la Serie Origen
	 * @param idCampoFechaExtremaFinal
	 * @param idFormato
	 * @return Lista de UInsDepositoVO
	 */
	public List getUInstParaRelacionEntreArchivos(Date fechaFinalDesde,
			Date fechaFinalHasta, String idArchivoEmisor, String idSerieOrigen,
			String idFormato);

	/**
	 * Devuelve las Unidades de Instalación para la relación entre archivos.
	 *
	 * @param fechaFinalDesde
	 *            Fecha Final Desde
	 * @param fechaFinalHast
	 *            Fecha Final Hasta
	 * @param idArchivoEmisor
	 *            Identificador del Id de Archivo Emisor
	 * @param idSerieOrigen
	 *            Id de la Serie Origen
	 * @param idCampoFechaExtremaFinal
	 * @param idFormato
	 * @param List
	 *            listaIdsAExcluir Lista de Ids de Unidad de Instalación a
	 *            excluir
	 * @return Lista de UInsDepositoVO
	 */
	public List getUInstParaRelacionEntreArchivos(Date fechaFinalDesde,
			Date fechaFinalHasta, String idArchivoEmisor, String idSerieOrigen,
			String idFormato, String idNivelDocumental, List listaIdsAExcluir);

	/**
	 * Devuelve las Unidades de Instalación para la relación entre archivos.
	 *
	 * @param fechaFinalDesde
	 *            Fecha Final Desde
	 * @param fechaFinalHast
	 *            Fecha Final Hasta
	 * @param idArchivoEmisor
	 *            Identificador del Id de Archivo Emisor
	 * @param idSerieOrigen
	 *            Id de la Serie Origen
	 * @param idCampoFechaExtremaFinal
	 * @param idFormato
	 * @param idNivelDocumental
	 *            Nivel documental de las unidades de la caja
	 * @return Lista de UInsDepositoVO
	 */
	public List getUInstParaRelacionEntreArchivos(Date fechaFinalDesde,
			Date fechaFinalHasta, String idArchivoEmisor, String idSerieOrigen,
			String idFormato, String idNivelDocumental);

	/**
	 * /** Obtiene el número de unidades de instalación que tiene una relación
	 * de entrega
	 *
	 * @param idRelacionEntrega
	 *            Identificador de relación de entrega
	 * @param idTipoTransferencia
	 *            id del tipo de transferencia
	 * @return Número de unidades de instalación de la relación de entrega
	 */
	public int getNUnidadesInstalacion(String idRelacionEntrega,
			int idTipoTransferencia);

	public int getNUnidadesInstalacionAsignadas(String idRelacionEntrega,
			int idTipoTransferencia);

	/**
	 * Inserte la Unidades de Instalación de Relación de Entrega entre Archivos
	 *
	 * @param listaUnidadesInstalacion
	 *            Lista de UnidadInstalacionReeaVO
	 */
	public void insertarUinstReea(List listaUnidadesInstalacion);

	/**
	 * Inserte la Unidades de Instalación de Relación de Entrega entre Archivos
	 *
	 * @param listaUnidadesInstalacion
	 *            Lista de UnidadInstalacionReeaVO
	 */
	public void eliminarUinstReea(String idRelacion, String[] ids);

	/**
	 * Marca las Unidades de Instalación como Revisadas
	 *
	 * @param Lista
	 *            de Unidades de Instalación para marcar como revisadas
	 */
	public void marcarUinstReeaRevisadas(String[] ids);

	/**
	 * Obtiene la unidades de Instalación de una relacion entre archivos.
	 *
	 * @param idRelacionEntrega
	 *            Identificador de la relación de entrega.
	 * @return Lista de {@link UnidadInstalacionReeaVO}
	 */
	public List getUnidadesInstalacionEntreArchivos(String idRelacionEntrega);

	/**
	 * Obtiene una lista de Unidades documentales que están en una Relación
	 *
	 * @param idsUnidadesInstalacion
	 *            String de ids
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUDocsXidRelacionEntreArchivos(String idRelacion);

	/**
	 * Obtiene una la Lista de Unidades Documentales de la Unidad de Instalación
	 *
	 * @param idUnidadInstalacion
	 *            Identificador de la Unidad de Instalación
	 * @return Lista de UnidadDocumentalVO
	 */
	public List getUdocsEnUInst(String idUnidadInstalacion);

	/**
	 * Obtiene las unidades de instalación dependiendo del tipo de transferencia
	 *
	 * @param idRelacionEntrega
	 * @param tipoTransferencia
	 * @return Lista de Unidades de instalación. Lista de
	 *         {@link UnidadInstalacionReeaVO} si la relación es entre archivos
	 *         Lista de {@link UnidadInstalacionVO} en caso contrario
	 */
	public List getUnidadesInstalacion(String idRelacionEntrega,
			int tipoTransferencia);

	/**
	 * Obtiene las unidades de instalación dependiendo del tipo de transferencia
	 *
	 * @param idRelacionEntrega
	 * @param tipoTransferencia
	 * @param tipoUInstalacion
	 * @return Lista de Unidades de instalación. Lista de
	 *         {@link UnidadInstalacionReeaVO} si la relación es entre archivos
	 *         Lista de {@link UnidadInstalacionVO} en caso contrario
	 */
	public List getUnidadesInstalacion(String idRelacionEntrega,
			int tipoTransferencia, boolean asignado);

	/**
	 * Obtiene la unidades de instalación de una relacion de entrega
	 *
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalación
	 * @return Unidad de instalación
	 */
	public UnidadInstalacionVO getUnidadInstalacion(String idUnidadInstalacion,
			int tipoTransferencia);

	/**
	 * Actualiza una unidad de instalación con los valores pasados por parámetro
	 *
	 * @param idUnidadInstalacion
	 * @param estadoCotejo
	 * @param notasCotejo
	 * @param devolver
	 * @param isEntreArchivos
	 * @param isConReencajado
	 */
	public void updateUnidadInstalacion(String idUnidadInstalacion,
			int estadoCotejo, String notasCotejo, String devolver,
			boolean isEntreArchivos, boolean isConReencajado);

	/**
	 * Obtiene la unidades de Instalación de una relacion entre archivos.
	 *
	 * @param idRelacionEntrega
	 *            Identificador de la relación de entrega.
	 * @return Lista de {@link UnidadInstalacionReeaVO}
	 */
	public List getUnidadesInstalacionEntreArchivos(String idRelacionEntrega,
			IntervalOptions ordenes);

	/**
	 * Actualiza unidades de instalación
	 *
	 * @param listaCajas
	 *            . Lista de {@link IUnidadInstalacionVO}
	 * @param RelacionEntregaPO
	 */
	public void updateUnidadInstalacion(List listaCajas,
			RelacionEntregaPO relacionPO);

	/**
	 * Obtiene las Partes Unidades documentales que no se han añadido Para
	 * evitar que en relaciones entre archivos se envíen unidades documentales
	 * por partes pero incompletas.
	 *
	 * @param idRelacion
	 *            Identificador de la Relación entre Archivos
	 * @param idUdocsRelacion
	 *            String[] ids de las unidades documentales de la relación
	 * @param idsUinstRelacion
	 *            String[] ids de las unidades de instalación de la relación
	 * @return Lista de UDocEnUiDepositoVO
	 */
	public List getPartesUdocsNoIncluidasEnRelacion(String idRelacion,
			List idsUdocsRelacion, List idsUinstRelacion);

	/**
	 * Obtiene las Partes Unidades documentales que no están incompletas Para
	 * evitar que en relaciones entre archivos se envíen unidades documentales
	 * por partes pero incompletas.
	 *
	 * @param List
	 *            idsUdocs Lista de String ids de las unidades documentales de
	 *            la relación
	 * @param idsUInst
	 *            String[] Lista de Strings ids de las unidades de instalación
	 *            de la relación
	 * @return Lista de UDocEnUiDepositoVO
	 */
	public List getPartesUdocsIncompletasEnRelacion(List idsUdocsIncompletas,
			List idsUInst);

	// informe de entrega
	public List getPartesUdocsXIdRelacionOficinaArchivo(String idRelacion);

	public List getPartesUdocsXIdRelacionEntreArchivos(String idRelacion);

	/**
	 * Comprueba si hay unidades documentales Electrónicas con errores
	 *
	 * @param idRelacion
	 *            Identificador de la Relación
	 * @return true si existen unidades documental electrónicas con Errores.
	 */
	public boolean checkUdocsElectronicasConErrores(final String idRelacion);

	/**
	 * Obtiene las Unidades Documentales Electrónicas que tiene la relación.
	 *
	 * @param idRelacion
	 *            Identificador de la Relación.
	 * @return Lista de Objetos {@link UDocElectronicaVO}
	 */
	public List getUDocsElectronicasByIdRelacion(final String idRelacion);

	/**
	 * Obtiene las Unidades Documentales Electrónicas que tiene la relación
	 * entre archivos
	 *
	 * @param idRelacion
	 *            Identificador de la Relación.
	 * @return Lista de Objetos {@link UDocElectronicaVO}
	 */
	public List getUDocsElectronicasByIdRelacionEntreArchivos(
			final String idRelacion);

	List getUDocsElectronicasByIdRelacionEntreArchivosConFechas(
			final String idRelacion);

	/**
	 * Elimina todas las Unidades Documentales Electrónicas de la Relación de
	 * Entrega.
	 *
	 * @param idRelacionEntrega
	 *            Identificador de la Relación de Entrega.
	 */
	public void deleteUDocElectronicas(final String idRelacionEntrega);

	/**
	 * Elimina la Unidad Documental Electrónica
	 *
	 * @param id
	 *            Identificador de la Unidad Documental.
	 */
	public void deleteUdocElectronica(final String id);

	/**
	 * Añade una Unidad Documental
	 *
	 * @param uDocElectronicaVO
	 *            Datos de la Unidad Electrónica.
	 */
	// public void addUDocElectronica(final UDocElectronicaVO
	// uDocElectronicaVO);

	/**
	 * Modifica el estado de cotejo de las unidades de instalación.
	 *
	 * @param ids
	 *            Identificadores de las unidades de instalación.
	 * @param estado
	 *            Estado de cotejo.
	 * @param relacionEntrega
	 *            Relación de entrega.
	 */
	public void updateEstadoUDocsElectronicas(String idRelacionEntrega,
			String[] ids, int estado);

	public boolean existenUDocsFisicas(String idRelacion);

	/**
	 * Obtiene la lista de Unidades Documentales Electrónicas que cumplen los
	 * requisitos para una relación entre archivos.
	 *
	 * @param fechaFinalDesde
	 *            Fecha Final del Expediente Desde
	 * @param fechaFinalHasta
	 *            Fecha Inicial del Expediente Hasta
	 * @param idArchivoEmisor
	 *            Identificador del Arhivo Emisor
	 * @param idSerieOrigen
	 *            Identificador de la Serie Origen
	 * @return Lista de {@link ElementoCuadroClasificacion}
	 */
	public List getUDocsElectronicasParaRelacionEntreArchivos(
			Date fechaFinalDesde, Date fechaFinalHasta, String idArchivoEmisor,
			String idSerieOrigen);

	/**
	 * Añade una unidad documental electrónica a una relación entre archivos. Y
	 * la bloque para transferencias
	 *
	 * @param udoc
	 *            Unidad Documental Electrónica.
	 */
	public void addUDocElectronicaARelacionEntreArchivos(
			UDocElectronicaVO udocElectronicaVO);

	/**
	 * Elimina la unidad documental electrónica de la relación entre archivos y
	 * la desbloquea
	 *
	 * @param id
	 */
	// public void removeUDocElectronicaARelacionEntreArchivos(String id);

	/**
	 * Eliminar las Unides Documentales Electrónicas de una Relación Entre
	 * Archivos Y Desbloquea las unidades documentales.
	 */
	public void eliminarUDocsElectronicasReea(String idRelacion,
			String[] idsSeleccionados);

	/**
	 * Comprueba si una relación de entrega entre archivos solo tiene documentos
	 * electrónicos
	 *
	 * @param idRelacion
	 *            identificador de la relación de entrega
	 * @return true si la relación solo tiene documentos electrónicos.
	 */
	public boolean isRelacionEAConSoloDocumentosElectronicos(String idRelacion);

	/**
	 * Comprueba si una relación de entrega entre archivos tiene documentos
	 * electrónicos.
	 *
	 * @param relacionEntrega
	 *            Relación de Entrega
	 * @return true si hay unidades documentales.
	 */
	public boolean hayUDocsElectronicasParaRelacionEntreArchivos(
			RelacionEntregaVO relacionEntrega);

	/**
	 * Obtiene las unidades documentales físicas incluidas en una relacion de
	 * entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUnidadesDocumentalesFisicas(String idRelacion);

	/**
	 * Obtiene una unidad documental en relación de entrega con su información
	 * de descripción rellena
	 *
	 * @param idUdocRE
	 * @return
	 */
	public UnidadDocumentalVO getUnidadDocumentalConInfoDesc(String idUdocRE);

	/**
	 * Método para conservar la descripción de la unidad documental pasada como
	 * parámetro origen en la copia creada cuyo id es el parámetro
	 * idUDocREDestino
	 *
	 * @param idUDocREOrigen
	 * @param idUDocREDestino
	 */
	public void conservarDescripcion(String idUDocREOrigen,
			String idUDocREDestino);

	public void conservarDescripcion(String idUDocOrigen, String idUDocDestino,
			Map mapCamposIgnorar);

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo unidad documental que es fruto de la división de una fracción de
	 * serie
	 *
	 * @param idUDoc
	 *            Identificador del elemento del cuadro.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByUDocFS(String idUDoc)
			throws TooManyResultsException;

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo unidad documental que provienen de una relación de entrega con ficha
	 * y a cuya ficha se está accediendo desde esa relación
	 *
	 * @param idUDoc
	 *            Identificador del elemento del cuadro.
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByUDocRE(String idUDoc)
			throws TooManyResultsException;

	/**
	 * Obtiene las relaciones de entrega asociadas a un elemento del cuadro de
	 * tipo unidad documental que es fruto de la división de una fracción de
	 * serie
	 *
	 * @param idFS
	 *            Identificador de la fracción de serie dividida
	 * @return Lista de relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesByFSDividida(String idFS)
			throws TooManyResultsException;

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionRelacionesEntregaBI#getUdocsEnUIEA(java.lang.String)
	 * Devuelve una lista de ParteUnidadDocumentalVO
	 */
	public List getUdocsEnUIEA(String idUnidadInstalacion);

	/**
	 * Permite obtener las unidades de instalación de una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param idTipoTransferencia
	 *            Identificador de tipo de transferencias
	 * @return Lista de Unidades de instalación {@link UnidadInstalacionVO}
	 */
	public List fetchRowsByIdRelacion(String idRelacion, int idTipoTransferencia);

	public String estableceUbicacionIngresoSignaturacionAsociadaHueco(
			String idIngreso, GestorEstructuraDepositoBI serviceDeposito,
			boolean actualizar);

	/**
	 * Permite obtener la siguiente unidad documental a partir de un orden en la
	 * relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param orden
	 *            Orden en la relación
	 * @return {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO getNextUnidadDocumental(String idRelacion,
			int orden);

	/**
	 * Permite obtener la anterior unidad documental a partir de un orden en la
	 * relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param orden
	 *            Orden en la relación
	 * @return {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO getPrevUnidadDocumental(String idRelacion,
			int orden);

	/**
	 * Permite obtener la lista de rangos de una unidad documental en relación
	 * de entrega de las tablas de descripción de udoc en relación
	 *
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @param idCampoInicial
	 *            identificador del campo rango inicial
	 * @param idCampoFinal
	 *            identificador del campo rango final
	 * @return Lista de rangos {@link transferencias.vos.RangoVO}
	 */
	public List getRangosUDocRE(String idUDoc, String idCampoInicial,
			String idCampoFinal);

	/**
	 * Actualiza el Identificador del Alta al con el que está relacionado.
	 *
	 * @param idRevDoc
	 *            Identificador de la Revisión de Documentación
	 * @param idAlta
	 *            Identficador del Alta de Unidades Documentales.
	 */
	public void updateIdAltaRevisionDocumentacion(String idRevDoc, String idAlta);

	/**
	 * Obtiene la revisión cuyo id concide con el que se le pasa
	 *
	 * @param idRevDoc
	 *            Identificador de la revisión de la documentación
	 * @return {@link RevisionDocumentacionVO}
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc);

	/**
	 * Obtiene la revisión cuyo idAlta concide con el que se le pasa
	 *
	 * @param idAlta
	 *            Identificador del alta de unidad documental
	 * @return {@link RevisionDocumentacionVO}
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionByIdAlta(
			String idAlta);

	/**
	 * Obtiene el identificador la unidad documental a la que está asociada la
	 * revisión.
	 *
	 * @param idRevDoc
	 *            Identificador de la revisión de la documentación
	 * @return
	 */
	public String getIdUnidadDocumentalRevision(String idRevDoc);

	/**
	 * Obtiene el identificador la unidad documental a la que está asociada el
	 * alta.
	 *
	 * @param idAlta
	 *            Identificador de la revisión
	 * @return
	 */
	public String getIdUnidadDocumentalRevisionByIdAlta(String idAlta);

	/**
	 * Comprueba si la relacion existe en base de datos o no.
	 *
	 * @param idRelacion
	 * @return boolean con el resultado de la busqueda.
	 */
	public boolean existeRelacion(String idRelacion);

	public List getEstadosIngresosDirectos();

	/**
	 * Obtiene el Estado de la Relaci&oacute;n de Entrega.
	 *
	 * @param idRelacion
	 *            Cadena que define el identificador de la relaci&oacute;n de
	 *            entrega
	 * @return
	 * @throws NotAvailableException
	 */
	public int getEstadoRelacion(String idRelacion);

	/**
	 * Establece el estado RECHAZADA a la relación de Entrega
	 *
	 * @param RelacionEntregaVO
	 *            Datos de la Relación de Entrega.
	 * @throws RelacionOperacionNoPermitidaException
	 */
	public void rechazarRelacionEntrega(RelacionEntregaVO relacionVO)
			throws RelacionOperacionNoPermitidaException;

	/**
	 * Eliminar unidades de instalación de la relación que estan vacías
	 *
	 * @param idRelEntrega
	 */
	public void eliminarUIsReeaCRVacias(String idRelEntrega);

	public void addUDocElectronica(
			TransferenciaElectronicaInfo transferenciaElectronicaInfo) throws TransferenciaElectronicaException;

	/**
	 * Obtiene una relacion de entrega electronica. Si no existe la crea con los
	 * datos que le llegan.
	 *
	 * @param transferenciaElectronicaInfo
	 * @throws TransferenciaElectronicaException
	 */
	public void establecerRelacionEntregaElectronica(
			TransferenciaElectronicaInfo transferenciaElectronicaInfo)
			throws TransferenciaElectronicaException;

	/**
	 * Obtiene la longitud del campo Código de sistema productor
	 *
	 * @return
	 */
	public int getLongitudCampoCodigoSistemaProductor();

	/**
	 * Obitiene la longitud del campo nombre del sistema productor
	 *
	 * @return
	 */
	public int getLongitudCampoNombreSistemaProductor();


	public UnidadDocumentalVO getUnidadDocumentalByRelacionAndId(
			String idRelacion, String idUnidad);

	/**
	 * Establece la variable a null para forzar a que se cargue de nuevo
	 */
	public void resetMapDescrUDoc();

}