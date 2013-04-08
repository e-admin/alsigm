package common.db;

import ieci.core.db.DbConnection;
import ieci.core.guid.IGuidGenDBEntity;

import organizacion.persistence.db.IOrganizacionDBEntity;
import organizacion.persistence.db.IUserOrganoDBEntity;

import salas.db.IEdificioDBEntity;
import salas.db.IMesaDBEntity;
import salas.db.IRegistroConsultaSalaDBEntity;
import salas.db.ISalaDBEntity;
import salas.db.IUsuarioArchivoSalasConsultaDBEntity;
import salas.db.IUsuarioSalaConsultaDBEntity;
import solicitudes.consultas.db.ConsultaDBEntity;
import solicitudes.consultas.db.IMotivoConsultaDBEntity;
import solicitudes.consultas.db.ITemaDBEntity;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.IRevisionDocumentacionDBEntity;
import solicitudes.prestamos.db.IMotivoPrestamoDBEntity;
import solicitudes.prestamos.db.IPrestamoDBEntity;
import solicitudes.prestamos.db.IProrrogaDBEntity;
import transferencias.db.IDetallePrevisionDBEntity;
import transferencias.db.IMapDescUDocDBEntity;
import transferencias.db.INSecUDocDBEntity;
import transferencias.db.INSecUIDBEntity;
import transferencias.db.IPrevisionDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.db.IUDocEnUIReeaCRDBEntity;
import transferencias.db.IUDocReeaCRDBEntity;
import transferencias.db.IUDocRelacionDBEntity;
import transferencias.db.IUIReeaCRDBEntity;
import transferencias.db.IUdocElectronicaDBEntity;
import transferencias.db.IUdocEnUIDBEntity;
import transferencias.db.IUnidadInstalacionDBEntity;
import transferencias.db.IUnidadInstalacionReeaDBEntity;
import valoracion.db.IEliminacionSerieDBEntity;
import valoracion.db.IEliminacionUDOCConservadaDBEntity;
import valoracion.db.IEliminacionUDOCEliminadaDBEntity;
import valoracion.db.IHistoricoUDOCDBEntity;
import valoracion.db.INSecVersionDBEntity;
import valoracion.db.INSecVersionSelDBEntity;
import valoracion.db.IPlazosValoracionDBEntity;
import valoracion.db.IValoracionDBEntity;
import auditoria.db.ICritAccionesDBEntity;
import auditoria.db.ICritUsuariosDBEntity;
import auditoria.db.IDatosTrazaDBEntity;
import auditoria.db.ITrazaDBEntity;

import common.lock.db.ILockDBEntity;
import common.session.db.ISessionDBEntity;

import configuracion.db.IInfoSistemaDBEntity;
import deposito.db.HistUInstalacionDepositoDBEntity;
import deposito.db.IDepositoDbEntity;
import deposito.db.IDepositoElectronicoDBEntity;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.IFormatoDbEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.INsecSigNumHuecoDBEntity;
import deposito.db.INumOrdenDBEntity;
import deposito.db.IOcupacionDBEntity;
import deposito.db.ITipoElementoDBEntity;
import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import descripcion.db.IAreaDBEntity;
import descripcion.db.ICampoDatoDBEntity;
import descripcion.db.ICampoTablaDBEntity;
import descripcion.db.ICatalogoListaDescriptoresDBEntity;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.IFechaDBEntity;
import descripcion.db.IFechaDescrDBEntity;
import descripcion.db.IFichaDBEntity;
import descripcion.db.IFmtFichaDBEntity;
import descripcion.db.IFmtPrefDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.INumeroDescrDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.IReferenciaDescrDBEntity;
import descripcion.db.ITablaValidacionDBEntity;
import descripcion.db.ITextoCortoDescrDBEntity;
import descripcion.db.ITextoCortoUdocREDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.db.ITextoLargoDescrDBEntity;
import descripcion.db.IUsoObjetoDBEntity;
import descripcion.db.IValidacionDBEntity;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.db.IDocDocumentoDescrDBEntity;
import docelectronicos.db.IDocFichaDBEntity;
import docelectronicos.db.IDocTCapturaDBEntity;
import docelectronicos.db.IUnidadDocumentalElectronicaDBEntity;
import docvitales.db.IDocumentoVitalDBEntity;
import docvitales.db.ITipoDocVitProcedimientoDBEntity;
import docvitales.db.ITipoDocumentoVitalDBEntity;
import docvitales.db.IUsoDocumentoVitalDBEntity;
import fondos.db.ICatalogoTablasTemporalesDBEntity;
import fondos.db.IDivisionFSDbEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IElementoCuadroClasificacionVistaDBEntity;
import fondos.db.IEntidadProductoraDBEntity;
import fondos.db.IFondoDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.ISolicitudSerieBusquedasDbEntity;
import fondos.db.ISolicitudSerieDbEntity;
import fondos.db.ITablaTemporalDBEntity;
import fondos.db.IUDocEnDivisionFSDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.db.IUnidadDocumentalVistaDBEntity;
import fondos.db.IVolumenSerieDBEntity;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.ICAOrganoDbEntity;
import gcontrol.db.IGrupoDBEntity;
import gcontrol.db.IGrupoUsuarioDBEntity;
import gcontrol.db.IListaControlAccesoDbEntity;
import gcontrol.db.INivelArchivoDbEntity;
import gcontrol.db.IOrganoUsuarioDBEntity;
import gcontrol.db.IPermisoRolDBEntity;
import gcontrol.db.IPermisosListaDbEntity;
import gcontrol.db.IRolDBEntity;
import gcontrol.db.IRolUsuarioDBEntity;
import gcontrol.db.IUsuarioDBEntity;

public interface IDBEntityFactory {

	/**
	 * Obtiene el tipo de Base de datos.
	 * 
	 * @return
	 */
	public abstract int getDataBaseType();

	public abstract IRelacionEntregaDBEntity getRelacionDBEntity(
			DbDataSource dataSource);

	public abstract transferencias.db.INSecTransferenciasDBEntity getNSecDBEntity(
			DbDataSource dataSource);

	public abstract IPrevisionDBEntity getPrevisionDBEntity(
			DbDataSource dataSource);

	public abstract IDetallePrevisionDBEntity getDetallePrevisionDBEntity(
			DbDataSource dataSource);

	public abstract IFormatoDbEntity getFormatoDBEntity(DbDataSource dataSource);

	public abstract IUDocRelacionDBEntity getTransferenciasUnidadDocumentalDBEntity(
			DbDataSource dataSource);

	public abstract IUdocEnUIDBEntity getUdocEnUIDBEntity(
			DbDataSource dataSource);

	public abstract IUnidadInstalacionDBEntity getUnidadInstalacionDBEntity(
			DbDataSource dataSource);

	public abstract IUnidadDocumentalDbEntity getUnidadDocumentalDBEntity(
			DbDataSource dataSource);

	public abstract INSecUIDBEntity getNSecUIDBEntity(DbDataSource dataSource);

	public abstract IFondoDbEntity getFondoDBEntity(DbDataSource dataSource);

	public abstract IEntidadProductoraDBEntity getEntidadProductoraDBEntity(
			DbDataSource dataSource);

	public abstract IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource);

	public abstract ISerieDbEntity getSerieDBEntity(DbDataSource dataSource);

	public abstract IDescriptorDBEntity getDescriptorDBEntity(
			DbDataSource dataSource);

	public abstract IFmtPrefDBEntity getFmtPrefDBEntity(DbDataSource dataSource);

	public abstract IFmtFichaDBEntity getFmtFichaDBEntity(
			DbDataSource dataSource);

	public abstract IOrganoProductorDbEntity getOrganoProductorDBEntity(
			DbDataSource dataSource);

	public abstract ICatalogoListaDescriptoresDBEntity getCatalogoListaDescriptoresDBEntity(
			DbDataSource dataSource);

	public abstract INivelCFDBEntity getNivelCFDBEntity(DbDataSource dataSource);

	public abstract IFichaDBEntity getFichaDBEntity(DbDataSource dataSource);

	public abstract ITextoDBEntity getTextoCortoDBEntity(DbDataSource dataSource);

	public abstract ITextoCortoDescrDBEntity getTextoCortoDescrDBEntity(
			DbDataSource dataSource);

	public abstract ITextoDBEntity getTextoLargoDBEntity(DbDataSource dataSource);

	public abstract ITextoLargoDescrDBEntity getTextoLargoDescrDBEntity(
			DbDataSource dataSource);

	public abstract IFechaDBEntity getFechaDBEntity(DbDataSource dataSource);

	public abstract IFechaDescrDBEntity getFechaDescrDBEntity(
			DbDataSource dataSource);

	public abstract INumeroDBEntity getNumeroDBEntity(DbDataSource dataSource);

	public abstract INumeroDescrDBEntity getNumeroDescrDBEntity(
			DbDataSource dataSource);

	public abstract IReferenciaDBEntity getReferenciaDBEntity(
			DbDataSource dataSource);

	public abstract IReferenciaDescrDBEntity getReferenciaDescrDBEntity(
			DbDataSource dataSource);

	public abstract ITablaValidacionDBEntity getTablaValidacionDBEntity(
			DbDataSource dataSource);

	public abstract IValidacionDBEntity getValidacionDBEntity(
			DbDataSource dataSource);

	public abstract IArchivoDbEntity getArchivoDbEntity(DbDataSource dataSource);

	public abstract ICAOrganoDbEntity getCAOrganoDbEntity(
			DbDataSource dataSource);

	public abstract IOrganoUsuarioDBEntity getOrganoUsuarioDBEntity(
			DbDataSource dataSource);

	public abstract IUsuarioDBEntity getUsuarioDBEntity(DbDataSource dataSource);

	public abstract IRolUsuarioDBEntity getRolUsuarioDBEntity(
			DbDataSource dataSource);

	public abstract IRolDBEntity getRolDBEntity(DbDataSource dataSource);

	public abstract IPermisoRolDBEntity getPermisoRolDBEntity(
			DbDataSource dataSource);

	public abstract IGrupoDBEntity getGrupoDBEntity(DbDataSource dataSource);

	public abstract IGrupoUsuarioDBEntity getGrupoUsuarioDBEntity(
			DbDataSource dataSource);

	public abstract IUnidadDocumentalDbEntity getFondosUnidadDocumentalDBEntity(
			DbDataSource dataSource);

	public abstract IUInstalacionDepositoDBEntity getUnidadInstalacionDepositoDbEntity(
			DbDataSource dataSource);

	public abstract IUDocEnUiDepositoDbEntity getUDocEnUiDepositoDbEntity(
			DbDataSource dataSource);

	public abstract ISolicitudSerieDbEntity getSolicitudSerieDBEntity(
			DbDataSource dataSource);

	public abstract IProductorSerieDbEntity getProductorSerieDBEntity(
			DbDataSource dataSource);

	public abstract IListaControlAccesoDbEntity getListaControlAccesoDbEntity(
			DbDataSource dataSource);

	public abstract IPermisosListaDbEntity getPermisosListaDbEntity(
			DbDataSource dataSource);

	public abstract ITipoElementoDBEntity getTipoElementoDBEntity(
			DbDataSource dataSource);

	public abstract IDepositoDbEntity getDepositoDbEntity(
			DbDataSource dataSource);

	public abstract IDepositoElectronicoDBEntity getDepositoElectronicoDBEntity(
			DbDataSource dataSource);

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract INumOrdenDBEntity getOrdenElementoDepositoDBEntity(
			DbDataSource dataSource);

	public abstract IMotivoRechazoDBEntity getMotivoRechazoDBEntity(
			DbDataSource dataSource);

	public abstract IProrrogaDBEntity getProrrogaDBEntity(
			DbDataSource dataSource);

	public abstract solicitudes.db.INSecSolicitudesDBEntity getNSecDBEntityForSolicitudes(
			DbDataSource dataSource);

	public abstract IPrestamoDBEntity getPrestamoDBEntity(
			DbDataSource dataSource);

	public abstract IDetalleDBEntity getDetalleDBEntity(DbDataSource dataSource);

	public abstract ConsultaDBEntity getConsultaDBEntity(DbDataSource dataSource);

	public abstract IDocFichaDBEntity getDocFichaDBEntity(
			DbDataSource dataSource);

	public abstract IDocClasifCFDBEntity getDocClasifCFDBEntity(
			DbDataSource dataSource);

	public abstract IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource);

	public abstract IDocDocumentoCFDBEntity getDocDocumentoCFDBEntity(
			DbDataSource dataSource);

	public abstract IDocDocumentoDescrDBEntity getDocDocumentoDescrDBEntity(
			DbDataSource dataSource);

	public abstract ITemaDBEntity getTemaDBEntity(DbDataSource dataSource);

	public abstract IMotivoConsultaDBEntity getMotivoConsultaDBEntity(
			DbDataSource dataSource);

	public abstract valoracion.db.INSecValoracionDBEntity getNSecValoracionDBEntity(
			DbDataSource dataSource);

	public abstract INSecVersionDBEntity getNVersionValoracionDBEntity(
			DbDataSource dataSource);

	public abstract INSecVersionSelDBEntity getNVersionSeleccionDBEntity(
			DbDataSource dataSource);

	public abstract IValoracionDBEntity getValoracionDBEntity(
			DbDataSource dataSource);

	public abstract IPlazosValoracionDBEntity getPlazosValoracionDBEntity(
			DbDataSource dataSource);

	public abstract IEliminacionSerieDBEntity getEliminacionDBEntity(
			DbDataSource dataSource);

	public abstract IEliminacionUDOCConservadaDBEntity getEliminacionUDocConservadaDBEntity(
			DbDataSource dataSource);

	public abstract IHistoricoUDOCDBEntity getHistoricoDBEntity(
			DbDataSource dataSource);

	public abstract ITrazaDBEntity getTrazaDBEntity(DbDataSource dataSource);

	public abstract IDatosTrazaDBEntity getDatosTrazaDBEntity(
			DbDataSource dataSource);

	public abstract ICritAccionesDBEntity getCritAccionesDBEntity(
			DbDataSource dataSource);

	public abstract ICritUsuariosDBEntity getCritUsuariosDBEntity(
			DbDataSource dataSource);

	public abstract ISessionDBEntity getSessionDBEntity(DbDataSource dataSource);

	public abstract IOcupacionDBEntity getOcupacionDBEntity(
			DbDataSource dataSource);

	public abstract IHuecoDBEntity getHuecoDBEntity(DbDataSource dataSource);

	public abstract IVolumenSerieDBEntity getVolumenSerieDBEntity(
			DbDataSource dataSource);

	public abstract ISolicitudSerieBusquedasDbEntity getSolicitudSerieBusquedasDbEntity(
			DbDataSource dataSource);

	public abstract IDocumentoVitalDBEntity getDocumentoVitalDBEntity(
			DbDataSource dataSource);

	public abstract ITipoDocumentoVitalDBEntity getTipoDocumentoVitalDBEntity(
			DbDataSource dataSource);

	public abstract ITipoDocVitProcedimientoDBEntity getTipoDocVitProcedimientoDBEntity(
			DbDataSource dataSource);

	public abstract IUsoDocumentoVitalDBEntity getUsoDocumentoVitalDBEntity(
			DbDataSource dataSource);

	public abstract IDocTCapturaDBEntity getDocTCapturaDBEntity(
			DbDataSource dataSource);

	public abstract ICampoDatoDBEntity getCampoDatoDBEntity(
			DbDataSource dataSource);

	public abstract ILockDBEntity getLockDBEntityImplBase(
			DbDataSource dataSource);

	public abstract ICampoTablaDBEntity getCampoTablaDBEntity(
			DbDataSource dataSource);

	public abstract IAreaDBEntity getAreaDBEntity(DbDataSource dataSource);

	public abstract IUsoObjetoDBEntity getUsoObjetoDBEntity(
			DbDataSource dataSource);

	public abstract INivelArchivoDbEntity getNivelArchivoDBEntity(
			DbDataSource dataSource);

	public abstract IArchivoDbEntity getArchivoDBEntity(DbDataSource dataSource);

	public abstract IUnidadInstalacionReeaDBEntity getUnidadInstalacionReeaDBEntity(
			DbDataSource dataSource);

	public abstract INSecUDocDBEntity getNSecUDocDBEntity(
			DbDataSource dataSource);

	public abstract IInfoSistemaDBEntity getInfoSistemaDBEntity(
			DbDataSource dataSource);

	public abstract IMapDescUDocDBEntity getMapDescUDocDBEntity(
			DbDataSource dataSource);

	public abstract IUdocElectronicaDBEntity getUDocElectronicaDBEntity(
			DbDataSource dataSource);

	public abstract IUnidadDocumentalElectronicaDBEntity getUnidadDocumentalElectronicaDBEntity(
			DbDataSource dataSource);

	public abstract ITextoCortoUdocREDBEntity getTextoCortoUDocREDBEntity(
			DbDataSource dataSource);

	public abstract ITextoDBEntity getTextoLargoUDocREDBEntity(
			DbDataSource dataSource);

	public abstract INumeroDBEntity getNumeroUDocREDBEntity(
			DbDataSource dataSource);

	public abstract IFechaDBEntity getFechaUDocREDBEntity(
			DbDataSource dataSource);

	public abstract IReferenciaDBEntity getReferenciaUDocREDBEntity(
			DbDataSource dataSource);

	public abstract IDivisionFSDbEntity getDivisionFSDBEntity(
			DbDataSource dataSource);

	public abstract IUDocEnDivisionFSDbEntity getUDocEnDivisionFSDBEntity(
			DbDataSource dataSource);

	public abstract INsecSigNumHuecoDBEntity getNsecSigNumHuecoDBEntity(
			DbDataSource dataSource);

	public abstract IRevisionDocumentacionDBEntity getRevisionDocumentacionDBEntity(
			DbDataSource dataSource);

	public abstract IOrganizacionDBEntity getOrganizacionDBEntity(
			DbDataSource dataSource);

	public abstract IUserOrganoDBEntity getUserOrganoDBEntity(
			DbDataSource dataSource);

	public abstract HistUInstalacionDepositoDBEntity getHistUInstalacionDepositoDBEntity(
			DbDataSource dataSource);

	public abstract IMotivoPrestamoDBEntity getMotivoPrestamoDBEntity(
			DbDataSource dataSource);

	public abstract IEdificioDBEntity getEdificioDBEntity(
			DbDataSource dataSource);

	public abstract ISalaDBEntity getSalaDBEntity(DbDataSource dataSource);

	public abstract IMesaDBEntity getMesaDBEntity(DbDataSource dataSource);

	public abstract IUsuarioSalaConsultaDBEntity getUsuarioSalaConsultaDBEntity(
			DbDataSource dataSource);

	public abstract IUsuarioArchivoSalasConsultaDBEntity getUsuarioArchivoSalasConsultaDBEntity(
			DbDataSource dataSource);

	public abstract IRegistroConsultaSalaDBEntity getRegistroConsultaSalaDBEntity(
			DbDataSource dataSource);

	public abstract IUIReeaCRDBEntity getUiReeaCRDBEntity(
			DbDataSource dataSource);

	public abstract IUDocEnUIReeaCRDBEntity getUDocEnUIReeaCRDBEntity(
			DbDataSource dataSource);

	public abstract IUDocReeaCRDBEntity getUDocReeaCRDBEntity(
			DbDataSource dataSource);

	public abstract ICatalogoTablasTemporalesDBEntity getCatalogoTablasTemporalesDBEntity(
			DbDataSource dataSource);

	public abstract ITablaTemporalDBEntity getTablaTemporalDBEntity(
			DbDataSource datasource);

	public abstract IElementoCuadroClasificacionVistaDBEntity getElementoClasificacionVistaDBEntity(
			DbDataSource dataSource);

	public abstract IUnidadDocumentalVistaDBEntity getUnidadDocumentalVistaDBEntity(
			DbDataSource dataSource);

	public abstract IEliminacionUDOCEliminadaDBEntity getEliminacionUDocEliminadaDBEntity(
			DbDataSource dataSource);

	public abstract IGuidGenDBEntity getGuidGenDBEntity(DbDataSource dataSource);

	// Recibiendo la conexión
	public abstract IRelacionEntregaDBEntity getRelacionDBEntity(
			DbConnection conn);

	public abstract transferencias.db.INSecTransferenciasDBEntity getNSecDBEntity(
			DbConnection conn);

	public abstract IPrevisionDBEntity getPrevisionDBEntity(DbConnection conn);

	public abstract IDetallePrevisionDBEntity getDetallePrevisionDBEntity(
			DbConnection conn);

	public abstract IFormatoDbEntity getFormatoDBEntity(DbConnection conn);

	public abstract IUDocRelacionDBEntity getTransferenciasUnidadDocumentalDBEntity(
			DbConnection conn);

	public abstract IUdocEnUIDBEntity getUdocEnUIDBEntity(DbConnection conn);

	public abstract IUnidadInstalacionDBEntity getUnidadInstalacionDBEntity(
			DbConnection conn);

	public abstract IUnidadDocumentalDbEntity getUnidadDocumentalDBEntity(
			DbConnection conn);

	public abstract INSecUIDBEntity getNSecUIDBEntity(DbConnection conn);

	public abstract IFondoDbEntity getFondoDBEntity(DbConnection conn);

	public abstract IEntidadProductoraDBEntity getEntidadProductoraDBEntity(
			DbConnection conn);

	public abstract IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn);

	public abstract ISerieDbEntity getSerieDBEntity(DbConnection conn);

	public abstract IDescriptorDBEntity getDescriptorDBEntity(DbConnection conn);

	public abstract IFmtPrefDBEntity getFmtPrefDBEntity(DbConnection conn);

	public abstract IFmtFichaDBEntity getFmtFichaDBEntity(DbConnection conn);

	public abstract IOrganoProductorDbEntity getOrganoProductorDBEntity(
			DbConnection conn);

	public abstract ICatalogoListaDescriptoresDBEntity getCatalogoListaDescriptoresDBEntity(
			DbConnection conn);

	public abstract INivelCFDBEntity getNivelCFDBEntity(DbConnection conn);

	public abstract IFichaDBEntity getFichaDBEntity(DbConnection conn);

	public abstract ITextoDBEntity getTextoCortoDBEntity(DbConnection conn);

	public abstract ITextoCortoDescrDBEntity getTextoCortoDescrDBEntity(
			DbConnection conn);

	public abstract ITextoDBEntity getTextoLargoDBEntity(DbConnection conn);

	public abstract ITextoLargoDescrDBEntity getTextoLargoDescrDBEntity(
			DbConnection conn);

	public abstract IFechaDBEntity getFechaDBEntity(DbConnection conn);

	public abstract IFechaDescrDBEntity getFechaDescrDBEntity(DbConnection conn);

	public abstract INumeroDBEntity getNumeroDBEntity(DbConnection conn);

	public abstract INumeroDescrDBEntity getNumeroDescrDBEntity(
			DbConnection conn);

	public abstract IReferenciaDBEntity getReferenciaDBEntity(DbConnection conn);

	public abstract IReferenciaDescrDBEntity getReferenciaDescrDBEntity(
			DbConnection conn);

	public abstract ITablaValidacionDBEntity getTablaValidacionDBEntity(
			DbConnection conn);

	public abstract IValidacionDBEntity getValidacionDBEntity(DbConnection conn);

	public abstract IArchivoDbEntity getArchivoDbEntity(DbConnection conn);

	public abstract ICAOrganoDbEntity getCAOrganoDbEntity(DbConnection conn);

	public abstract IOrganoUsuarioDBEntity getOrganoUsuarioDBEntity(
			DbConnection conn);

	public abstract IUsuarioDBEntity getUsuarioDBEntity(DbConnection conn);

	public abstract IRolUsuarioDBEntity getRolUsuarioDBEntity(DbConnection conn);

	public abstract IRolDBEntity getRolDBEntity(DbConnection conn);

	public abstract IPermisoRolDBEntity getPermisoRolDBEntity(DbConnection conn);

	public abstract IGrupoDBEntity getGrupoDBEntity(DbConnection conn);

	public abstract IGrupoUsuarioDBEntity getGrupoUsuarioDBEntity(
			DbConnection conn);

	public abstract IUnidadDocumentalDbEntity getFondosUnidadDocumentalDBEntity(
			DbConnection conn);

	public abstract IUInstalacionDepositoDBEntity getUnidadInstalacionDepositoDbEntity(
			DbConnection conn);

	public abstract IUDocEnUiDepositoDbEntity getUDocEnUiDepositoDbEntity(
			DbConnection conn);

	public abstract ISolicitudSerieDbEntity getSolicitudSerieDBEntity(
			DbConnection conn);

	public abstract IProductorSerieDbEntity getProductorSerieDBEntity(
			DbConnection conn);

	public abstract IListaControlAccesoDbEntity getListaControlAccesoDbEntity(
			DbConnection conn);

	public abstract IPermisosListaDbEntity getPermisosListaDbEntity(
			DbConnection conn);

	public abstract ITipoElementoDBEntity getTipoElementoDBEntity(
			DbConnection conn);

	public abstract IDepositoDbEntity getDepositoDbEntity(DbConnection conn);

	public abstract IDepositoElectronicoDBEntity getDepositoElectronicoDBEntity(
			DbConnection conn);

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn);

	public abstract INumOrdenDBEntity getOrdenElementoDepositoDBEntity(
			DbConnection conn);

	public abstract IMotivoRechazoDBEntity getMotivoRechazoDBEntity(
			DbConnection conn);

	public abstract IProrrogaDBEntity getProrrogaDBEntity(DbConnection conn);

	public abstract solicitudes.db.INSecSolicitudesDBEntity getNSecDBEntityForSolicitudes(
			DbConnection conn);

	public abstract IPrestamoDBEntity getPrestamoDBEntity(DbConnection conn);

	public abstract IDetalleDBEntity getDetalleDBEntity(DbConnection conn);

	public abstract ConsultaDBEntity getConsultaDBEntity(DbConnection conn);

	public abstract IDocFichaDBEntity getDocFichaDBEntity(DbConnection conn);

	public abstract IDocClasifCFDBEntity getDocClasifCFDBEntity(
			DbConnection conn);

	public abstract IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbConnection conn);

	public abstract IDocDocumentoCFDBEntity getDocDocumentoCFDBEntity(
			DbConnection conn);

	public abstract IDocDocumentoDescrDBEntity getDocDocumentoDescrDBEntity(
			DbConnection conn);

	public abstract ITemaDBEntity getTemaDBEntity(DbConnection conn);

	public abstract IMotivoConsultaDBEntity getMotivoConsultaDBEntity(
			DbConnection conn);

	public abstract valoracion.db.INSecValoracionDBEntity getNSecValoracionDBEntity(
			DbConnection conn);

	public abstract INSecVersionDBEntity getNVersionValoracionDBEntity(
			DbConnection conn);

	public abstract INSecVersionSelDBEntity getNVersionSeleccionDBEntity(
			DbConnection conn);

	public abstract IValoracionDBEntity getValoracionDBEntity(DbConnection conn);

	public abstract IPlazosValoracionDBEntity getPlazosValoracionDBEntity(
			DbConnection conn);

	public abstract IEliminacionSerieDBEntity getEliminacionDBEntity(
			DbConnection conn);

	public abstract IEliminacionUDOCConservadaDBEntity getEliminacionUDocConservadaDBEntity(
			DbConnection conn);

	public abstract IHistoricoUDOCDBEntity getHistoricoDBEntity(
			DbConnection conn);

	public abstract ITrazaDBEntity getTrazaDBEntity(DbConnection conn);

	public abstract IDatosTrazaDBEntity getDatosTrazaDBEntity(DbConnection conn);

	public abstract ICritAccionesDBEntity getCritAccionesDBEntity(
			DbConnection conn);

	public abstract ICritUsuariosDBEntity getCritUsuariosDBEntity(
			DbConnection conn);

	public abstract ISessionDBEntity getSessionDBEntity(DbConnection conn);

	public abstract IOcupacionDBEntity getOcupacionDBEntity(DbConnection conn);

	public abstract IHuecoDBEntity getHuecoDBEntity(DbConnection conn);

	public abstract IVolumenSerieDBEntity getVolumenSerieDBEntity(
			DbConnection conn);

	public abstract ISolicitudSerieBusquedasDbEntity getSolicitudSerieBusquedasDbEntity(
			DbConnection conn);

	public abstract IDocumentoVitalDBEntity getDocumentoVitalDBEntity(
			DbConnection conn);

	public abstract ITipoDocumentoVitalDBEntity getTipoDocumentoVitalDBEntity(
			DbConnection conn);

	public abstract ITipoDocVitProcedimientoDBEntity getTipoDocVitProcedimientoDBEntity(
			DbConnection conn);

	public abstract IUsoDocumentoVitalDBEntity getUsoDocumentoVitalDBEntity(
			DbConnection conn);

	public abstract IDocTCapturaDBEntity getDocTCapturaDBEntity(
			DbConnection conn);

	public abstract ICampoDatoDBEntity getCampoDatoDBEntity(DbConnection conn);

	public abstract ILockDBEntity getLockDBEntityImplBase(DbConnection conn);

	public abstract ICampoTablaDBEntity getCampoTablaDBEntity(DbConnection conn);

	public abstract IAreaDBEntity getAreaDBEntity(DbConnection conn);

	public abstract IUsoObjetoDBEntity getUsoObjetoDBEntity(DbConnection conn);

	public abstract INivelArchivoDbEntity getNivelArchivoDBEntity(
			DbConnection conn);

	public abstract IArchivoDbEntity getArchivoDBEntity(DbConnection conn);

	public abstract IUnidadInstalacionReeaDBEntity getUnidadInstalacionReeaDBEntity(
			DbConnection conn);

	public abstract INSecUDocDBEntity getNSecUDocDBEntity(DbConnection conn);

	public abstract IInfoSistemaDBEntity getInfoSistemaDBEntity(
			DbConnection conn);

	public abstract IMapDescUDocDBEntity getMapDescUDocDBEntity(
			DbConnection conn);

	public abstract IUdocElectronicaDBEntity getUDocElectronicaDBEntity(
			DbConnection conn);

	public abstract IUnidadDocumentalElectronicaDBEntity getUnidadDocumentalElectronicaDBEntity(
			DbConnection conn);

	public abstract ITextoCortoUdocREDBEntity getTextoCortoUDocREDBEntity(
			DbConnection conn);

	public abstract ITextoDBEntity getTextoLargoUDocREDBEntity(DbConnection conn);

	public abstract INumeroDBEntity getNumeroUDocREDBEntity(DbConnection conn);

	public abstract IFechaDBEntity getFechaUDocREDBEntity(DbConnection conn);

	public abstract IReferenciaDBEntity getReferenciaUDocREDBEntity(
			DbConnection conn);

	public abstract IDivisionFSDbEntity getDivisionFSDBEntity(DbConnection conn);

	public abstract IUDocEnDivisionFSDbEntity getUDocEnDivisionFSDBEntity(
			DbConnection conn);

	public abstract INsecSigNumHuecoDBEntity getNsecSigNumHuecoDBEntity(
			DbConnection conn);

	public abstract IRevisionDocumentacionDBEntity getRevisionDocumentacionDBEntity(
			DbConnection conn);

	public abstract IOrganizacionDBEntity getOrganizacionDBEntity(
			DbConnection conn);

	public abstract IUserOrganoDBEntity getUserOrganoDBEntity(DbConnection conn);

	public abstract HistUInstalacionDepositoDBEntity getHistUInstalacionDepositoDBEntity(
			DbConnection conn);

	public abstract IMotivoPrestamoDBEntity getMotivoPrestamoDBEntity(
			DbConnection conn);

	public abstract IEdificioDBEntity getEdificioDBEntity(DbConnection conn);

	public abstract ISalaDBEntity getSalaDBEntity(DbConnection conn);

	public abstract IMesaDBEntity getMesaDBEntity(DbConnection conn);

	public abstract IUsuarioSalaConsultaDBEntity getUsuarioSalaConsultaDBEntity(
			DbConnection conn);

	public abstract IUsuarioArchivoSalasConsultaDBEntity getUsuarioArchivoSalasConsultaDBEntity(
			DbConnection conn);

	public abstract IRegistroConsultaSalaDBEntity getRegistroConsultaSalaDBEntity(
			DbConnection conn);

	public abstract IUIReeaCRDBEntity getUiReeaCRDBEntity(DbConnection conn);

	public abstract IUDocEnUIReeaCRDBEntity getUDocEnUIReeaCRDBEntity(
			DbConnection conn);

	public abstract IUDocReeaCRDBEntity getUDocReeaCRDBEntity(DbConnection conn);

	public abstract ICatalogoTablasTemporalesDBEntity getCatalogoTablasTemporalesDBEntity(
			DbConnection conn);

	public abstract ITablaTemporalDBEntity getTablaTemporalDBEntity(
			DbConnection conn);

	public abstract IEliminacionUDOCEliminadaDBEntity getEliminacionUDocEliminadaDBEntity(
			DbConnection conn);

	public abstract IGuidGenDBEntity getGuidGenDBEntity(DbConnection conn);

}
