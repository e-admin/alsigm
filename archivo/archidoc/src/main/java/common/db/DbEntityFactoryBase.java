package common.db;

import fondos.db.CatalogoTablasTemporalesDBEntityImpl;
import fondos.db.DivisionFSDBEntityImpl;
import fondos.db.ElementoCuadroClasificacionVistaDBEntityImpl;
import fondos.db.EntidadProductoraDBEntityImpl;
import fondos.db.FondoDBEntityImpl;
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
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.OrganoProductorDBEntity;
import fondos.db.SerieDBEntityImpl;
import fondos.db.SolicitudSerieBusquedasDbEntityImpl;
import fondos.db.SolicitudSerieDBEntityImpl;
import fondos.db.TablaTemporalDBEntityImpl;
import fondos.db.UDocEnDivisionFSDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.db.UnidadDocumentalVistaDBEntityImpl;
import fondos.db.VolumenSerieDBEntityImpl;
import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.db.CAOrganoDbEntityImpl;
import gcontrol.db.GrupoDBEntityImpl;
import gcontrol.db.GrupoUsuarioDBEntityImpl;
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
import gcontrol.db.ListaControlAccesoDbEntityImpl;
import gcontrol.db.NivelArchivoDbEntityImpl;
import gcontrol.db.OrganoUsuarioDBEntityImpl;
import gcontrol.db.PermisoRolDBEntityImpl;
import gcontrol.db.PermisosListaDbEntityImpl;
import gcontrol.db.RolDBEntityImpl;
import gcontrol.db.RolUsuarioDBEntityImpl;
import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbConnection;
import ieci.core.guid.IGuidGenDBEntity;
import ieci.core.guid.impl.GuidGenDBEntityImpl;

import organizacion.persistence.db.IOrganizacionDBEntity;
import organizacion.persistence.db.IUserOrganoDBEntity;
import organizacion.persistence.db.OrganizacionDBEntityImpl;
import organizacion.persistence.db.UserOrganoDBEntityImpl;

import salas.db.EdificioDBEntityImpl;
import salas.db.IEdificioDBEntity;
import salas.db.IMesaDBEntity;
import salas.db.IRegistroConsultaSalaDBEntity;
import salas.db.ISalaDBEntity;
import salas.db.IUsuarioArchivoSalasConsultaDBEntity;
import salas.db.IUsuarioSalaConsultaDBEntity;
import salas.db.MesaDBEntityImpl;
import salas.db.RegistroConsultaSalaDBEntityImpl;
import salas.db.SalaDBEntityImpl;
import salas.db.UsuarioArchivoSalasConsultaDBEntityImpl;
import salas.db.UsuarioSalaConsultaDBEntityImpl;
import solicitudes.consultas.db.ConsultaDBEntity;
import solicitudes.consultas.db.ConsultaDBEntityImpl;
import solicitudes.consultas.db.IMotivoConsultaDBEntity;
import solicitudes.consultas.db.ITemaDBEntity;
import solicitudes.consultas.db.MotivoConsultaDBEntityImpl;
import solicitudes.consultas.db.TemaDBEntityImpl;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.INSecSolicitudesDBEntity;
import solicitudes.db.IRevisionDocumentacionDBEntity;
import solicitudes.db.MotivoRechazoDBEntityImpl;
import solicitudes.db.NSecDBEntityImpl;
import solicitudes.db.RevisionDocumentacionDBEntityImpl;
import solicitudes.prestamos.db.IMotivoPrestamoDBEntity;
import solicitudes.prestamos.db.IPrestamoDBEntity;
import solicitudes.prestamos.db.IProrrogaDBEntity;
import solicitudes.prestamos.db.MotivoPrestamoDBEntityImpl;
import solicitudes.prestamos.db.PrestamoDBEntityImpl;
import solicitudes.prestamos.db.ProrrogaDBEntityImpl;
import transferencias.db.DetallePrevisionDBEntityImpl;
import transferencias.db.IDetallePrevisionDBEntity;
import transferencias.db.IMapDescUDocDBEntity;
import transferencias.db.INSecTransferenciasDBEntity;
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
import transferencias.db.MapDescUDocDBEntityImpl;
import transferencias.db.NSecUDocDBEntityImpl;
import transferencias.db.NSecUIDBEntityImpl;
import transferencias.db.PrevisionDBEntityImpl;
import transferencias.db.UDocEnUIReeaCRDBEntityImpl;
import transferencias.db.UDocReeaCRDBEntityImpl;
import transferencias.db.UDocRelacionDBEntityImpl;
import transferencias.db.UIReeaCRDBEntityImpl;
import transferencias.db.UdocElectronicaDBEntityImpl;
import transferencias.db.UdocEnUIDBEntityImpl;
import transferencias.db.UnidadInstalacionDBEntityImpl;
import transferencias.db.UnidadInstalacionReeaDBEntityImpl;
import valoracion.db.EliminacionSerieDBEntityImpl;
import valoracion.db.EliminacionUDOCConservadaDBEntityImpl;
import valoracion.db.EliminacionUDOCEliminadaDBEntityImpl;
import valoracion.db.HistoricoUDOCDBEntityImpl;
import valoracion.db.IEliminacionSerieDBEntity;
import valoracion.db.IEliminacionUDOCConservadaDBEntity;
import valoracion.db.IEliminacionUDOCEliminadaDBEntity;
import valoracion.db.IHistoricoUDOCDBEntity;
import valoracion.db.INSecValoracionDBEntity;
import valoracion.db.INSecVersionDBEntity;
import valoracion.db.INSecVersionSelDBEntity;
import valoracion.db.IPlazosValoracionDBEntity;
import valoracion.db.IValoracionDBEntity;
import valoracion.db.NSecValoracionDBEntityImpl;
import valoracion.db.NSecVersionDBEntityImpl;
import valoracion.db.NSecVersionSelDBEntityImpl;
import valoracion.db.PlazosValoracionDBEntityImpl;
import valoracion.db.ValoracionDBEntityImpl;
import auditoria.db.CritAccionesDBEntityImpl;
import auditoria.db.CritUsuariosDBEntityImpl;
import auditoria.db.DatosTrazaDBEntityImpl;
import auditoria.db.ICritAccionesDBEntity;
import auditoria.db.ICritUsuariosDBEntity;
import auditoria.db.IDatosTrazaDBEntity;
import auditoria.db.ITrazaDBEntity;
import auditoria.db.TrazaDBEntityImpl;

import common.lock.db.ILockDBEntity;
import common.session.db.ISessionDBEntity;
import common.session.db.SessionDBEntity;

import configuracion.db.IInfoSistemaDBEntity;
import configuracion.db.InfoSistemaDBEntityImpl;
import deposito.db.DepositoDBEntityImpl;
import deposito.db.DepositoElectronicoDBEntityImpl;
import deposito.db.FormatoDBEntity;
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
import deposito.db.NsecSigNumHuecoDBEntityImpl;
import deposito.db.NumOrdenDBEntityImpl;
import deposito.db.TipoElementoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import descripcion.db.AreaDBEntityImpl;
import descripcion.db.CampoDatoDBEntityImpl;
import descripcion.db.CampoTablaDBEntityImpl;
import descripcion.db.CatalogoListaDescriptoresDBEntityImpl;
import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.FechaDescrDBEntityImpl;
import descripcion.db.FechaUDocREDBEntityImpl;
import descripcion.db.FichaDBEntityImpl;
import descripcion.db.FmtFichaDBEntityImpl;
import descripcion.db.FmtPrefDBEntityImpl;
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
import descripcion.db.NumeroDBEntityImpl;
import descripcion.db.NumeroDescrDBEntityImpl;
import descripcion.db.NumeroUDocREDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.ReferenciaDescrDBEntityImpl;
import descripcion.db.ReferenciaUDocREDBEntityImpl;
import descripcion.db.TablaValidacionDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import descripcion.db.TextoCortoDescrDBEntityImpl;
import descripcion.db.TextoCortoUDocREDBEntityImpl;
import descripcion.db.TextoLargoDBEntityImpl;
import descripcion.db.TextoLargoDescrDBEntityImpl;
import descripcion.db.TextoLargoUDocREDBEntityImpl;
import descripcion.db.UsoObjetoDBEntityImpl;
import descripcion.db.ValidacionDBEntityImpl;
import docelectronicos.db.DocDocumentoCFDBEntityImpl;
import docelectronicos.db.DocDocumentoDescrDBEntityImpl;
import docelectronicos.db.DocFichaDBEntityImpl;
import docelectronicos.db.DocTCapturaDBEntityImpl;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.db.IDocDocumentoDescrDBEntity;
import docelectronicos.db.IDocFichaDBEntity;
import docelectronicos.db.IDocTCapturaDBEntity;
import docelectronicos.db.IUnidadDocumentalElectronicaDBEntity;
import docelectronicos.db.UnidadDocumentalElectronicaDBEntityImpl;
import docvitales.db.DocumentoVitalDBEntityImpl;
import docvitales.db.IDocumentoVitalDBEntity;
import docvitales.db.ITipoDocVitProcedimientoDBEntity;
import docvitales.db.ITipoDocumentoVitalDBEntity;
import docvitales.db.IUsoDocumentoVitalDBEntity;
import docvitales.db.TipoDocVitProcedimientoDBEntityImpl;
import docvitales.db.TipoDocumentoVitalDBEntityImpl;
import docvitales.db.UsoDocumentoVitalDBEntityImpl;

/**
 */
public abstract class DbEntityFactoryBase implements IDBEntityFactory {

	public abstract int getDataBaseType();

	// Implementaciones específicas por base de datos

	public abstract IRelacionEntregaDBEntity getRelacionDBEntity(
			DbDataSource dataSource);

	public abstract IRelacionEntregaDBEntity getRelacionDBEntity(
			DbConnection conn);

	public abstract IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource);

	public abstract IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn);

	public abstract IProductorSerieDbEntity getProductorSerieDBEntity(
			DbDataSource dataSource);

	public abstract IProductorSerieDbEntity getProductorSerieDBEntity(
			DbConnection conn);

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn);

	public abstract IDocClasifCFDBEntity getDocClasifCFDBEntity(
			DbDataSource dataSource);

	public abstract IDocClasifCFDBEntity getDocClasifCFDBEntity(
			DbConnection conn);

	public abstract IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource);

	public abstract IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbConnection conn);

	public abstract IOcupacionDBEntity getOcupacionDBEntity(
			DbDataSource dataSource);

	public abstract IOcupacionDBEntity getOcupacionDBEntity(DbConnection conn);

	public abstract IHuecoDBEntity getHuecoDBEntity(DbDataSource dataSource);

	public abstract IHuecoDBEntity getHuecoDBEntity(DbConnection conn);

	public abstract ILockDBEntity getLockDBEntityImplBase(
			DbDataSource dataSource);

	public abstract ILockDBEntity getLockDBEntityImplBase(DbConnection conn);

	public INSecTransferenciasDBEntity getNSecDBEntity(DbDataSource dataSource) {
		return new transferencias.db.NSecDBEntityImpl(dataSource);
	}

	public IPrevisionDBEntity getPrevisionDBEntity(DbDataSource dataSource) {
		return new PrevisionDBEntityImpl(dataSource);
	}

	public IDetallePrevisionDBEntity getDetallePrevisionDBEntity(
			DbDataSource dataSource) {
		return new DetallePrevisionDBEntityImpl(dataSource);
	}

	public IFormatoDbEntity getFormatoDBEntity(DbDataSource dataSource) {
		return new FormatoDBEntity(dataSource);
	}

	public IUDocRelacionDBEntity getTransferenciasUnidadDocumentalDBEntity(
			DbDataSource dataSource) {
		return new UDocRelacionDBEntityImpl(dataSource);
	}

	public IUdocEnUIDBEntity getUdocEnUIDBEntity(DbDataSource dataSource) {
		return new UdocEnUIDBEntityImpl(dataSource);
	}

	public IUnidadInstalacionDBEntity getUnidadInstalacionDBEntity(
			DbDataSource dataSource) {
		return new UnidadInstalacionDBEntityImpl(dataSource);
	}

	public IUnidadDocumentalDbEntity getUnidadDocumentalDBEntity(
			DbDataSource dataSource) {
		return new UnidadDocumentalDBEntityImpl(dataSource);
	}

	public INSecUIDBEntity getNSecUIDBEntity(DbDataSource dataSource) {
		return new NSecUIDBEntityImpl(dataSource);
	}

	public IFondoDbEntity getFondoDBEntity(DbDataSource dataSource) {
		return new FondoDBEntityImpl(dataSource);
	}

	public IEntidadProductoraDBEntity getEntidadProductoraDBEntity(
			DbDataSource dataSource) {
		return new EntidadProductoraDBEntityImpl(dataSource);
	}

	public ISerieDbEntity getSerieDBEntity(DbDataSource dataSource) {
		return new SerieDBEntityImpl(dataSource);
	}

	public IDescriptorDBEntity getDescriptorDBEntity(DbDataSource dataSource) {
		return new DescriptorDBEntityImpl(dataSource);
	}

	public IFmtPrefDBEntity getFmtPrefDBEntity(DbDataSource dataSource) {
		return new FmtPrefDBEntityImpl(dataSource);
	}

	public IFmtFichaDBEntity getFmtFichaDBEntity(DbDataSource dataSource) {
		return new FmtFichaDBEntityImpl(dataSource);
	}

	public IOrganoProductorDbEntity getOrganoProductorDBEntity(
			DbDataSource dataSource) {
		return new OrganoProductorDBEntity(dataSource);
	}

	public ICatalogoListaDescriptoresDBEntity getCatalogoListaDescriptoresDBEntity(
			DbDataSource dataSource) {
		return new CatalogoListaDescriptoresDBEntityImpl(dataSource);
	}

	public INivelCFDBEntity getNivelCFDBEntity(DbDataSource dataSource) {
		return new NivelCFDBEntityImpl(dataSource);
	}

	public IFichaDBEntity getFichaDBEntity(DbDataSource dataSource) {
		return new FichaDBEntityImpl(dataSource);
	}

	public ITextoDBEntity getTextoCortoDBEntity(DbDataSource dataSource) {
		return new TextoCortoDBEntityImpl(dataSource);
	}

	public ITextoCortoDescrDBEntity getTextoCortoDescrDBEntity(
			DbDataSource dataSource) {
		return new TextoCortoDescrDBEntityImpl(dataSource);
	}

	public ITextoDBEntity getTextoLargoDBEntity(DbDataSource dataSource) {
		return new TextoLargoDBEntityImpl(dataSource);
	}

	public ITextoLargoDescrDBEntity getTextoLargoDescrDBEntity(
			DbDataSource dataSource) {
		return new TextoLargoDescrDBEntityImpl(dataSource);
	}

	public IFechaDBEntity getFechaDBEntity(DbDataSource dataSource) {
		return new FechaDBEntityImpl(dataSource);

	}

	public IFechaDescrDBEntity getFechaDescrDBEntity(DbDataSource dataSource) {
		return new FechaDescrDBEntityImpl(dataSource);
	}

	public INumeroDBEntity getNumeroDBEntity(DbDataSource dataSource) {
		return new NumeroDBEntityImpl(dataSource);
	}

	public INumeroDescrDBEntity getNumeroDescrDBEntity(DbDataSource dataSource) {
		return new NumeroDescrDBEntityImpl(dataSource);
	}

	public IReferenciaDBEntity getReferenciaDBEntity(DbDataSource dataSource) {
		return new ReferenciaDBEntityImpl(dataSource);
	}

	public IReferenciaDescrDBEntity getReferenciaDescrDBEntity(
			DbDataSource dataSource) {
		return new ReferenciaDescrDBEntityImpl(dataSource);
	}

	public ITablaValidacionDBEntity getTablaValidacionDBEntity(
			DbDataSource dataSource) {
		return new TablaValidacionDBEntityImpl(dataSource);
	}

	public IValidacionDBEntity getValidacionDBEntity(DbDataSource dataSource) {
		return new ValidacionDBEntityImpl(dataSource);
	}

	public IArchivoDbEntity getArchivoDbEntity(DbDataSource dataSource) {
		return new ArchivoDbEntityImpl(dataSource);
	}

	public ICAOrganoDbEntity getCAOrganoDbEntity(DbDataSource dataSource) {
		return new CAOrganoDbEntityImpl(dataSource);
	}

	public IOrganoUsuarioDBEntity getOrganoUsuarioDBEntity(
			DbDataSource dataSource) {
		return new OrganoUsuarioDBEntityImpl(dataSource);
	}

	public IUsuarioDBEntity getUsuarioDBEntity(DbDataSource dataSource) {
		return new UsuarioDBEntityImpl(dataSource);
	}

	public IRolUsuarioDBEntity getRolUsuarioDBEntity(DbDataSource dataSource) {
		return new RolUsuarioDBEntityImpl(dataSource);
	}

	public IRolDBEntity getRolDBEntity(DbDataSource dataSource) {
		return new RolDBEntityImpl(dataSource);
	}

	public IPermisoRolDBEntity getPermisoRolDBEntity(DbDataSource dataSource) {
		return new PermisoRolDBEntityImpl(dataSource);
	}

	public IGrupoDBEntity getGrupoDBEntity(DbDataSource dataSource) {
		return new GrupoDBEntityImpl(dataSource);
	}

	public IGrupoUsuarioDBEntity getGrupoUsuarioDBEntity(DbDataSource dataSource) {
		return new GrupoUsuarioDBEntityImpl(dataSource);
	}

	public IUnidadDocumentalDbEntity getFondosUnidadDocumentalDBEntity(
			DbDataSource dataSource) {
		return new UnidadDocumentalDBEntityImpl(dataSource);
	}

	public IUInstalacionDepositoDBEntity getUnidadInstalacionDepositoDbEntity(
			DbDataSource dataSource) {
		return new UInstalacionDepositoDBEntity(dataSource);
	}

	public IUDocEnUiDepositoDbEntity getUDocEnUiDepositoDbEntity(
			DbDataSource dataSource) {
		return new UDocEnUiDepositoDbEntityImpl(dataSource);
	}

	public ISolicitudSerieDbEntity getSolicitudSerieDBEntity(
			DbDataSource dataSource) {
		return new SolicitudSerieDBEntityImpl(dataSource);
	}

	public IListaControlAccesoDbEntity getListaControlAccesoDbEntity(
			DbDataSource dataSource) {
		return new ListaControlAccesoDbEntityImpl(dataSource);
	}

	public IPermisosListaDbEntity getPermisosListaDbEntity(
			DbDataSource dataSource) {
		return new PermisosListaDbEntityImpl(dataSource);
	}

	public ITipoElementoDBEntity getTipoElementoDBEntity(DbDataSource dataSource) {
		return new TipoElementoDBEntity(dataSource);
	}

	public IDepositoDbEntity getDepositoDbEntity(DbDataSource dataSource) {
		return new DepositoDBEntityImpl(dataSource);
	}

	public IDepositoElectronicoDBEntity getDepositoElectronicoDBEntity(
			DbDataSource dataSource) {
		return new DepositoElectronicoDBEntityImpl(dataSource);
	}

	public INumOrdenDBEntity getOrdenElementoDepositoDBEntity(
			DbDataSource dataSource) {
		return new NumOrdenDBEntityImpl(dataSource);
	}

	public IMotivoRechazoDBEntity getMotivoRechazoDBEntity(
			DbDataSource dataSource) {
		return new MotivoRechazoDBEntityImpl(dataSource);
	}

	public IProrrogaDBEntity getProrrogaDBEntity(DbDataSource dataSource) {
		return new ProrrogaDBEntityImpl(dataSource);
	}

	public INSecSolicitudesDBEntity getNSecDBEntityForSolicitudes(
			DbDataSource dataSource) {
		return new NSecDBEntityImpl(dataSource);
	}

	public IPrestamoDBEntity getPrestamoDBEntity(DbDataSource dataSource) {
		return new PrestamoDBEntityImpl(dataSource);
	}

	public IDetalleDBEntity getDetalleDBEntity(DbDataSource dataSource) {
		return new DetalleDBEntityImpl(dataSource);
	}

	public ConsultaDBEntity getConsultaDBEntity(DbDataSource dataSource) {
		return new ConsultaDBEntityImpl(dataSource);
	}

	public IDocFichaDBEntity getDocFichaDBEntity(DbDataSource dataSource) {
		return new DocFichaDBEntityImpl(dataSource);
	}

	public IDocDocumentoCFDBEntity getDocDocumentoCFDBEntity(
			DbDataSource dataSource) {
		return new DocDocumentoCFDBEntityImpl(dataSource);
	}

	public IDocDocumentoDescrDBEntity getDocDocumentoDescrDBEntity(
			DbDataSource dataSource) {
		return new DocDocumentoDescrDBEntityImpl(dataSource);
	}

	public ITemaDBEntity getTemaDBEntity(DbDataSource dataSource) {
		return new TemaDBEntityImpl(dataSource);
	}

	public IMotivoConsultaDBEntity getMotivoConsultaDBEntity(
			DbDataSource dataSource) {
		return new MotivoConsultaDBEntityImpl(dataSource);
	}

	public INSecValoracionDBEntity getNSecValoracionDBEntity(
			DbDataSource dataSource) {
		return new NSecValoracionDBEntityImpl(dataSource);
	}

	public INSecVersionDBEntity getNVersionValoracionDBEntity(
			DbDataSource dataSource) {
		return new NSecVersionDBEntityImpl(dataSource);
	}

	public INSecVersionSelDBEntity getNVersionSeleccionDBEntity(
			DbDataSource dataSource) {
		return new NSecVersionSelDBEntityImpl(dataSource);
	}

	public IValoracionDBEntity getValoracionDBEntity(DbDataSource dataSource) {
		return new ValoracionDBEntityImpl(dataSource);
	}

	public IPlazosValoracionDBEntity getPlazosValoracionDBEntity(
			DbDataSource dataSource) {
		return new PlazosValoracionDBEntityImpl(dataSource);
	}

	public IEliminacionSerieDBEntity getEliminacionDBEntity(
			DbDataSource dataSource) {
		return new EliminacionSerieDBEntityImpl(dataSource);
	}

	public IEliminacionUDOCConservadaDBEntity getEliminacionUDocConservadaDBEntity(
			DbDataSource dataSource) {
		return new EliminacionUDOCConservadaDBEntityImpl(dataSource);
	}

	public IHistoricoUDOCDBEntity getHistoricoDBEntity(DbDataSource dataSource) {
		return new HistoricoUDOCDBEntityImpl(dataSource);
	}

	public ITrazaDBEntity getTrazaDBEntity(DbDataSource dataSource) {
		return new TrazaDBEntityImpl(dataSource);
	}

	public IDatosTrazaDBEntity getDatosTrazaDBEntity(DbDataSource dataSource) {
		return new DatosTrazaDBEntityImpl(dataSource);
	}

	public ICritAccionesDBEntity getCritAccionesDBEntity(DbDataSource dataSource) {
		return new CritAccionesDBEntityImpl(dataSource);
	}

	public ICritUsuariosDBEntity getCritUsuariosDBEntity(DbDataSource dataSource) {
		return new CritUsuariosDBEntityImpl(dataSource);
	}

	public ISessionDBEntity getSessionDBEntity(DbDataSource dataSource) {
		return new SessionDBEntity(dataSource);
	}

	public IVolumenSerieDBEntity getVolumenSerieDBEntity(DbDataSource dataSource) {

		return new VolumenSerieDBEntityImpl(dataSource);
	}

	public ISolicitudSerieBusquedasDbEntity getSolicitudSerieBusquedasDbEntity(
			DbDataSource dataSource) {
		return new SolicitudSerieBusquedasDbEntityImpl(dataSource);
	}

	public IDocumentoVitalDBEntity getDocumentoVitalDBEntity(
			DbDataSource dataSource) {
		return new DocumentoVitalDBEntityImpl(dataSource);
	}

	public ITipoDocumentoVitalDBEntity getTipoDocumentoVitalDBEntity(
			DbDataSource dataSource) {
		return new TipoDocumentoVitalDBEntityImpl(dataSource);
	}

	public ITipoDocVitProcedimientoDBEntity getTipoDocVitProcedimientoDBEntity(
			DbDataSource dataSource) {
		return new TipoDocVitProcedimientoDBEntityImpl(dataSource);
	}

	public IUsoDocumentoVitalDBEntity getUsoDocumentoVitalDBEntity(
			DbDataSource dataSource) {
		return new UsoDocumentoVitalDBEntityImpl(dataSource);
	}

	public IDocTCapturaDBEntity getDocTCapturaDBEntity(DbDataSource dataSource) {

		return new DocTCapturaDBEntityImpl(dataSource);
	}

	public ICampoDatoDBEntity getCampoDatoDBEntity(DbDataSource dataSource) {

		return new CampoDatoDBEntityImpl(dataSource);
	}

	public ICampoTablaDBEntity getCampoTablaDBEntity(DbDataSource dataSource) {

		return new CampoTablaDBEntityImpl(dataSource);
	}

	public IAreaDBEntity getAreaDBEntity(DbDataSource dataSource) {

		return new AreaDBEntityImpl(dataSource);
	}

	public IUsoObjetoDBEntity getUsoObjetoDBEntity(DbDataSource dataSource) {

		return new UsoObjetoDBEntityImpl(dataSource);
	}

	public INivelArchivoDbEntity getNivelArchivoDBEntity(DbDataSource dataSource) {

		return new NivelArchivoDbEntityImpl(dataSource);
	}

	public IArchivoDbEntity getArchivoDBEntity(DbDataSource dataSource) {

		return new ArchivoDbEntityImpl(dataSource);
	}

	public IUnidadInstalacionReeaDBEntity getUnidadInstalacionReeaDBEntity(
			DbDataSource dataSource) {

		return new UnidadInstalacionReeaDBEntityImpl(dataSource);
	}

	public INSecUDocDBEntity getNSecUDocDBEntity(DbDataSource dataSource) {

		return new NSecUDocDBEntityImpl(dataSource);
	}

	public IInfoSistemaDBEntity getInfoSistemaDBEntity(DbDataSource dataSource) {

		return new InfoSistemaDBEntityImpl(dataSource);
	}

	public IMapDescUDocDBEntity getMapDescUDocDBEntity(DbDataSource dataSource) {

		return new MapDescUDocDBEntityImpl(dataSource);
	}

	public IUdocElectronicaDBEntity getUDocElectronicaDBEntity(
			DbDataSource dataSource) {

		return new UdocElectronicaDBEntityImpl(dataSource);
	}

	public IUnidadDocumentalElectronicaDBEntity getUnidadDocumentalElectronicaDBEntity(
			DbDataSource dataSource) {

		return new UnidadDocumentalElectronicaDBEntityImpl(dataSource);
	}

	public ITextoCortoUdocREDBEntity getTextoCortoUDocREDBEntity(
			DbDataSource dataSource) {

		return new TextoCortoUDocREDBEntityImpl(dataSource);
	}

	public ITextoDBEntity getTextoLargoUDocREDBEntity(DbDataSource dataSource) {

		return new TextoLargoUDocREDBEntityImpl(dataSource);
	}

	public INumeroDBEntity getNumeroUDocREDBEntity(DbDataSource dataSource) {

		return new NumeroUDocREDBEntityImpl(dataSource);
	}

	public IFechaDBEntity getFechaUDocREDBEntity(DbDataSource dataSource) {

		return new FechaUDocREDBEntityImpl(dataSource);
	}

	public IReferenciaDBEntity getReferenciaUDocREDBEntity(
			DbDataSource dataSource) {

		return new ReferenciaUDocREDBEntityImpl(dataSource);
	}

	public IDivisionFSDbEntity getDivisionFSDBEntity(DbDataSource dataSource) {

		return new DivisionFSDBEntityImpl(dataSource);
	}

	public IUDocEnDivisionFSDbEntity getUDocEnDivisionFSDBEntity(
			DbDataSource dataSource) {

		return new UDocEnDivisionFSDBEntityImpl(dataSource);
	}

	public INsecSigNumHuecoDBEntity getNsecSigNumHuecoDBEntity(
			DbDataSource dataSource) {

		return new NsecSigNumHuecoDBEntityImpl(dataSource);
	}

	public IRevisionDocumentacionDBEntity getRevisionDocumentacionDBEntity(
			DbDataSource dataSource) {

		return new RevisionDocumentacionDBEntityImpl(dataSource);
	}

	public IOrganizacionDBEntity getOrganizacionDBEntity(DbDataSource dataSource) {

		return new OrganizacionDBEntityImpl(dataSource);
	}

	public IUserOrganoDBEntity getUserOrganoDBEntity(DbDataSource dataSource) {

		return new UserOrganoDBEntityImpl(dataSource);
	}

	public HistUInstalacionDepositoDBEntity getHistUInstalacionDepositoDBEntity(
			DbDataSource dataSource) {

		return new HistUInstalacionDepositoDBEntity(dataSource);
	}

	public IMotivoPrestamoDBEntity getMotivoPrestamoDBEntity(
			DbDataSource dataSource) {

		return new MotivoPrestamoDBEntityImpl(dataSource);
	}

	public IEdificioDBEntity getEdificioDBEntity(DbDataSource dataSource) {

		return new EdificioDBEntityImpl(dataSource);
	}

	public ISalaDBEntity getSalaDBEntity(DbDataSource dataSource) {

		return new SalaDBEntityImpl(dataSource);
	}

	public IMesaDBEntity getMesaDBEntity(DbDataSource dataSource) {

		return new MesaDBEntityImpl(dataSource);
	}

	public IUsuarioSalaConsultaDBEntity getUsuarioSalaConsultaDBEntity(
			DbDataSource dataSource) {

		return new UsuarioSalaConsultaDBEntityImpl(dataSource);
	}

	public IUsuarioArchivoSalasConsultaDBEntity getUsuarioArchivoSalasConsultaDBEntity(
			DbDataSource dataSource) {

		return new UsuarioArchivoSalasConsultaDBEntityImpl(dataSource);
	}

	public IRegistroConsultaSalaDBEntity getRegistroConsultaSalaDBEntity(
			DbDataSource dataSource) {

		return new RegistroConsultaSalaDBEntityImpl(dataSource);
	}

	public IUIReeaCRDBEntity getUiReeaCRDBEntity(DbDataSource dataSource) {
		return new UIReeaCRDBEntityImpl(dataSource);
	}

	public IUDocEnUIReeaCRDBEntity getUDocEnUIReeaCRDBEntity(
			DbDataSource dataSource) {
		return new UDocEnUIReeaCRDBEntityImpl(dataSource);
	}

	public IUDocReeaCRDBEntity getUDocReeaCRDBEntity(DbDataSource dataSource) {
		return new UDocReeaCRDBEntityImpl(dataSource);
	}

	public ICatalogoTablasTemporalesDBEntity getCatalogoTablasTemporalesDBEntity(
			DbDataSource dataSource) {

		return new CatalogoTablasTemporalesDBEntityImpl(dataSource);
	}

	public ITablaTemporalDBEntity getTablaTemporalDBEntity(
			DbDataSource dataSource) {

		return new TablaTemporalDBEntityImpl(dataSource);
	}

	public IElementoCuadroClasificacionVistaDBEntity getElementoClasificacionVistaDBEntity(
			DbDataSource dataSource) {
		return new ElementoCuadroClasificacionVistaDBEntityImpl(dataSource);
	}

	public IUnidadDocumentalVistaDBEntity getUnidadDocumentalVistaDBEntity(
			DbDataSource dataSource) {
		return new UnidadDocumentalVistaDBEntityImpl(dataSource);
	}

	public IEliminacionUDOCEliminadaDBEntity getEliminacionUDocEliminadaDBEntity(
			DbDataSource dataSource) {
		return new EliminacionUDOCEliminadaDBEntityImpl(dataSource);
	}

	public IGuidGenDBEntity getGuidGenDBEntity(DbDataSource dataSource) {
		return new GuidGenDBEntityImpl(dataSource);
	}

	public INSecTransferenciasDBEntity getNSecDBEntity(DbConnection conn) {
		return new transferencias.db.NSecDBEntityImpl(conn);
	}

	public IPrevisionDBEntity getPrevisionDBEntity(DbConnection conn) {
		return new PrevisionDBEntityImpl(conn);
	}

	public IDetallePrevisionDBEntity getDetallePrevisionDBEntity(
			DbConnection conn) {
		return new DetallePrevisionDBEntityImpl(conn);
	}

	public IFormatoDbEntity getFormatoDBEntity(DbConnection conn) {
		return new FormatoDBEntity(conn);
	}

	public IUDocRelacionDBEntity getTransferenciasUnidadDocumentalDBEntity(
			DbConnection conn) {
		return new UDocRelacionDBEntityImpl(conn);
	}

	public IUdocEnUIDBEntity getUdocEnUIDBEntity(DbConnection conn) {
		return new UdocEnUIDBEntityImpl(conn);
	}

	public IUnidadInstalacionDBEntity getUnidadInstalacionDBEntity(
			DbConnection conn) {
		return new UnidadInstalacionDBEntityImpl(conn);
	}

	public IUnidadDocumentalDbEntity getUnidadDocumentalDBEntity(
			DbConnection conn) {
		return new UnidadDocumentalDBEntityImpl(conn);
	}

	public INSecUIDBEntity getNSecUIDBEntity(DbConnection conn) {
		return new NSecUIDBEntityImpl(conn);
	}

	public IFondoDbEntity getFondoDBEntity(DbConnection conn) {
		return new FondoDBEntityImpl(conn);
	}

	public IEntidadProductoraDBEntity getEntidadProductoraDBEntity(
			DbConnection conn) {
		return new EntidadProductoraDBEntityImpl(conn);
	}

	public ISerieDbEntity getSerieDBEntity(DbConnection conn) {
		return new SerieDBEntityImpl(conn);
	}

	public IDescriptorDBEntity getDescriptorDBEntity(DbConnection conn) {
		return new DescriptorDBEntityImpl(conn);
	}

	public IFmtPrefDBEntity getFmtPrefDBEntity(DbConnection conn) {
		return new FmtPrefDBEntityImpl(conn);
	}

	public IFmtFichaDBEntity getFmtFichaDBEntity(DbConnection conn) {
		return new FmtFichaDBEntityImpl(conn);
	}

	public IOrganoProductorDbEntity getOrganoProductorDBEntity(DbConnection conn) {
		return new OrganoProductorDBEntity(conn);
	}

	public ICatalogoListaDescriptoresDBEntity getCatalogoListaDescriptoresDBEntity(
			DbConnection conn) {
		return new CatalogoListaDescriptoresDBEntityImpl(conn);
	}

	public INivelCFDBEntity getNivelCFDBEntity(DbConnection conn) {
		return new NivelCFDBEntityImpl(conn);
	}

	public IFichaDBEntity getFichaDBEntity(DbConnection conn) {
		return new FichaDBEntityImpl(conn);
	}

	public ITextoDBEntity getTextoCortoDBEntity(DbConnection conn) {
		return new TextoCortoDBEntityImpl(conn);
	}

	public ITextoCortoDescrDBEntity getTextoCortoDescrDBEntity(DbConnection conn) {
		return new TextoCortoDescrDBEntityImpl(conn);
	}

	public ITextoDBEntity getTextoLargoDBEntity(DbConnection conn) {
		return new TextoLargoDBEntityImpl(conn);
	}

	public ITextoLargoDescrDBEntity getTextoLargoDescrDBEntity(DbConnection conn) {
		return new TextoLargoDescrDBEntityImpl(conn);
	}

	public IFechaDBEntity getFechaDBEntity(DbConnection conn) {
		return new FechaDBEntityImpl(conn);

	}

	public IFechaDescrDBEntity getFechaDescrDBEntity(DbConnection conn) {
		return new FechaDescrDBEntityImpl(conn);
	}

	public INumeroDBEntity getNumeroDBEntity(DbConnection conn) {
		return new NumeroDBEntityImpl(conn);
	}

	public INumeroDescrDBEntity getNumeroDescrDBEntity(DbConnection conn) {
		return new NumeroDescrDBEntityImpl(conn);
	}

	public IReferenciaDBEntity getReferenciaDBEntity(DbConnection conn) {
		return new ReferenciaDBEntityImpl(conn);
	}

	public IReferenciaDescrDBEntity getReferenciaDescrDBEntity(DbConnection conn) {
		return new ReferenciaDescrDBEntityImpl(conn);
	}

	public ITablaValidacionDBEntity getTablaValidacionDBEntity(DbConnection conn) {
		return new TablaValidacionDBEntityImpl(conn);
	}

	public IValidacionDBEntity getValidacionDBEntity(DbConnection conn) {
		return new ValidacionDBEntityImpl(conn);
	}

	public IArchivoDbEntity getArchivoDbEntity(DbConnection conn) {
		return new ArchivoDbEntityImpl(conn);
	}

	public ICAOrganoDbEntity getCAOrganoDbEntity(DbConnection conn) {
		return new CAOrganoDbEntityImpl(conn);
	}

	public IOrganoUsuarioDBEntity getOrganoUsuarioDBEntity(DbConnection conn) {
		return new OrganoUsuarioDBEntityImpl(conn);
	}

	public IUsuarioDBEntity getUsuarioDBEntity(DbConnection conn) {
		return new UsuarioDBEntityImpl(conn);
	}

	public IRolUsuarioDBEntity getRolUsuarioDBEntity(DbConnection conn) {
		return new RolUsuarioDBEntityImpl(conn);
	}

	public IRolDBEntity getRolDBEntity(DbConnection conn) {
		return new RolDBEntityImpl(conn);
	}

	public IPermisoRolDBEntity getPermisoRolDBEntity(DbConnection conn) {
		return new PermisoRolDBEntityImpl(conn);
	}

	public IGrupoDBEntity getGrupoDBEntity(DbConnection conn) {
		return new GrupoDBEntityImpl(conn);
	}

	public IGrupoUsuarioDBEntity getGrupoUsuarioDBEntity(DbConnection conn) {
		return new GrupoUsuarioDBEntityImpl(conn);
	}

	public IUnidadDocumentalDbEntity getFondosUnidadDocumentalDBEntity(
			DbConnection conn) {
		return new UnidadDocumentalDBEntityImpl(conn);
	}

	public IUInstalacionDepositoDBEntity getUnidadInstalacionDepositoDbEntity(
			DbConnection conn) {
		return new UInstalacionDepositoDBEntity(conn);
	}

	public IUDocEnUiDepositoDbEntity getUDocEnUiDepositoDbEntity(
			DbConnection conn) {
		return new UDocEnUiDepositoDbEntityImpl(conn);
	}

	public ISolicitudSerieDbEntity getSolicitudSerieDBEntity(DbConnection conn) {
		return new SolicitudSerieDBEntityImpl(conn);
	}

	public IListaControlAccesoDbEntity getListaControlAccesoDbEntity(
			DbConnection conn) {
		return new ListaControlAccesoDbEntityImpl(conn);
	}

	public IPermisosListaDbEntity getPermisosListaDbEntity(DbConnection conn) {
		return new PermisosListaDbEntityImpl(conn);
	}

	public ITipoElementoDBEntity getTipoElementoDBEntity(DbConnection conn) {
		return new TipoElementoDBEntity(conn);
	}

	public IDepositoDbEntity getDepositoDbEntity(DbConnection conn) {
		return new DepositoDBEntityImpl(conn);
	}

	public IDepositoElectronicoDBEntity getDepositoElectronicoDBEntity(
			DbConnection conn) {
		return new DepositoElectronicoDBEntityImpl(conn);
	}

	public INumOrdenDBEntity getOrdenElementoDepositoDBEntity(DbConnection conn) {
		return new NumOrdenDBEntityImpl(conn);
	}

	public IMotivoRechazoDBEntity getMotivoRechazoDBEntity(DbConnection conn) {
		return new MotivoRechazoDBEntityImpl(conn);
	}

	public IProrrogaDBEntity getProrrogaDBEntity(DbConnection conn) {
		return new ProrrogaDBEntityImpl(conn);
	}

	public INSecSolicitudesDBEntity getNSecDBEntityForSolicitudes(
			DbConnection conn) {
		return new NSecDBEntityImpl(conn);
	}

	public IPrestamoDBEntity getPrestamoDBEntity(DbConnection conn) {
		return new PrestamoDBEntityImpl(conn);
	}

	public IDetalleDBEntity getDetalleDBEntity(DbConnection conn) {
		return new DetalleDBEntityImpl(conn);
	}

	public ConsultaDBEntity getConsultaDBEntity(DbConnection conn) {
		return new ConsultaDBEntityImpl(conn);
	}

	public IDocFichaDBEntity getDocFichaDBEntity(DbConnection conn) {
		return new DocFichaDBEntityImpl(conn);
	}

	public IDocDocumentoCFDBEntity getDocDocumentoCFDBEntity(DbConnection conn) {
		return new DocDocumentoCFDBEntityImpl(conn);
	}

	public IDocDocumentoDescrDBEntity getDocDocumentoDescrDBEntity(
			DbConnection conn) {
		return new DocDocumentoDescrDBEntityImpl(conn);
	}

	public ITemaDBEntity getTemaDBEntity(DbConnection conn) {
		return new TemaDBEntityImpl(conn);
	}

	public IMotivoConsultaDBEntity getMotivoConsultaDBEntity(DbConnection conn) {
		return new MotivoConsultaDBEntityImpl(conn);
	}

	public INSecValoracionDBEntity getNSecValoracionDBEntity(DbConnection conn) {
		return new NSecValoracionDBEntityImpl(conn);
	}

	public INSecVersionDBEntity getNVersionValoracionDBEntity(DbConnection conn) {
		return new NSecVersionDBEntityImpl(conn);
	}

	public INSecVersionSelDBEntity getNVersionSeleccionDBEntity(
			DbConnection conn) {
		return new NSecVersionSelDBEntityImpl(conn);
	}

	public IValoracionDBEntity getValoracionDBEntity(DbConnection conn) {
		return new ValoracionDBEntityImpl(conn);
	}

	public IPlazosValoracionDBEntity getPlazosValoracionDBEntity(
			DbConnection conn) {
		return new PlazosValoracionDBEntityImpl(conn);
	}

	public IEliminacionSerieDBEntity getEliminacionDBEntity(DbConnection conn) {
		return new EliminacionSerieDBEntityImpl(conn);
	}

	public IEliminacionUDOCConservadaDBEntity getEliminacionUDocConservadaDBEntity(
			DbConnection conn) {
		return new EliminacionUDOCConservadaDBEntityImpl(conn);
	}

	public IHistoricoUDOCDBEntity getHistoricoDBEntity(DbConnection conn) {
		return new HistoricoUDOCDBEntityImpl(conn);
	}

	public ITrazaDBEntity getTrazaDBEntity(DbConnection conn) {
		return new TrazaDBEntityImpl(conn);
	}

	public IDatosTrazaDBEntity getDatosTrazaDBEntity(DbConnection conn) {
		return new DatosTrazaDBEntityImpl(conn);
	}

	public ICritAccionesDBEntity getCritAccionesDBEntity(DbConnection conn) {
		return new CritAccionesDBEntityImpl(conn);
	}

	public ICritUsuariosDBEntity getCritUsuariosDBEntity(DbConnection conn) {
		return new CritUsuariosDBEntityImpl(conn);
	}

	public ISessionDBEntity getSessionDBEntity(DbConnection conn) {
		return new SessionDBEntity(conn);
	}

	public IVolumenSerieDBEntity getVolumenSerieDBEntity(DbConnection conn) {

		return new VolumenSerieDBEntityImpl(conn);
	}

	public ISolicitudSerieBusquedasDbEntity getSolicitudSerieBusquedasDbEntity(
			DbConnection conn) {
		return new SolicitudSerieBusquedasDbEntityImpl(conn);
	}

	public IDocumentoVitalDBEntity getDocumentoVitalDBEntity(DbConnection conn) {
		return new DocumentoVitalDBEntityImpl(conn);
	}

	public ITipoDocumentoVitalDBEntity getTipoDocumentoVitalDBEntity(
			DbConnection conn) {
		return new TipoDocumentoVitalDBEntityImpl(conn);
	}

	public ITipoDocVitProcedimientoDBEntity getTipoDocVitProcedimientoDBEntity(
			DbConnection conn) {
		return new TipoDocVitProcedimientoDBEntityImpl(conn);
	}

	public IUsoDocumentoVitalDBEntity getUsoDocumentoVitalDBEntity(
			DbConnection conn) {
		return new UsoDocumentoVitalDBEntityImpl(conn);
	}

	public IDocTCapturaDBEntity getDocTCapturaDBEntity(DbConnection conn) {

		return new DocTCapturaDBEntityImpl(conn);
	}

	public ICampoDatoDBEntity getCampoDatoDBEntity(DbConnection conn) {

		return new CampoDatoDBEntityImpl(conn);
	}

	public ICampoTablaDBEntity getCampoTablaDBEntity(DbConnection conn) {

		return new CampoTablaDBEntityImpl(conn);
	}

	public IAreaDBEntity getAreaDBEntity(DbConnection conn) {

		return new AreaDBEntityImpl(conn);
	}

	public IUsoObjetoDBEntity getUsoObjetoDBEntity(DbConnection conn) {

		return new UsoObjetoDBEntityImpl(conn);
	}

	public INivelArchivoDbEntity getNivelArchivoDBEntity(DbConnection conn) {

		return new NivelArchivoDbEntityImpl(conn);
	}

	public IArchivoDbEntity getArchivoDBEntity(DbConnection conn) {

		return new ArchivoDbEntityImpl(conn);
	}

	public IUnidadInstalacionReeaDBEntity getUnidadInstalacionReeaDBEntity(
			DbConnection conn) {

		return new UnidadInstalacionReeaDBEntityImpl(conn);
	}

	public INSecUDocDBEntity getNSecUDocDBEntity(DbConnection conn) {

		return new NSecUDocDBEntityImpl(conn);
	}

	public IInfoSistemaDBEntity getInfoSistemaDBEntity(DbConnection conn) {

		return new InfoSistemaDBEntityImpl(conn);
	}

	public IMapDescUDocDBEntity getMapDescUDocDBEntity(DbConnection conn) {

		return new MapDescUDocDBEntityImpl(conn);
	}

	public IUdocElectronicaDBEntity getUDocElectronicaDBEntity(DbConnection conn) {

		return new UdocElectronicaDBEntityImpl(conn);
	}

	public IUnidadDocumentalElectronicaDBEntity getUnidadDocumentalElectronicaDBEntity(
			DbConnection conn) {

		return new UnidadDocumentalElectronicaDBEntityImpl(conn);
	}

	public ITextoCortoUdocREDBEntity getTextoCortoUDocREDBEntity(
			DbConnection conn) {

		return new TextoCortoUDocREDBEntityImpl(conn);
	}

	public ITextoDBEntity getTextoLargoUDocREDBEntity(DbConnection conn) {

		return new TextoLargoUDocREDBEntityImpl(conn);
	}

	public INumeroDBEntity getNumeroUDocREDBEntity(DbConnection conn) {

		return new NumeroUDocREDBEntityImpl(conn);
	}

	public IFechaDBEntity getFechaUDocREDBEntity(DbConnection conn) {

		return new FechaUDocREDBEntityImpl(conn);
	}

	public IReferenciaDBEntity getReferenciaUDocREDBEntity(DbConnection conn) {

		return new ReferenciaUDocREDBEntityImpl(conn);
	}

	public IDivisionFSDbEntity getDivisionFSDBEntity(DbConnection conn) {

		return new DivisionFSDBEntityImpl(conn);
	}

	public IUDocEnDivisionFSDbEntity getUDocEnDivisionFSDBEntity(
			DbConnection conn) {

		return new UDocEnDivisionFSDBEntityImpl(conn);
	}

	public INsecSigNumHuecoDBEntity getNsecSigNumHuecoDBEntity(DbConnection conn) {

		return new NsecSigNumHuecoDBEntityImpl(conn);
	}

	public IRevisionDocumentacionDBEntity getRevisionDocumentacionDBEntity(
			DbConnection conn) {

		return new RevisionDocumentacionDBEntityImpl(conn);
	}

	public IOrganizacionDBEntity getOrganizacionDBEntity(DbConnection conn) {

		return new OrganizacionDBEntityImpl(conn);
	}

	public IUserOrganoDBEntity getUserOrganoDBEntity(DbConnection conn) {

		return new UserOrganoDBEntityImpl(conn);
	}

	public HistUInstalacionDepositoDBEntity getHistUInstalacionDepositoDBEntity(
			DbConnection conn) {

		return new HistUInstalacionDepositoDBEntity(conn);
	}

	public IMotivoPrestamoDBEntity getMotivoPrestamoDBEntity(DbConnection conn) {

		return new MotivoPrestamoDBEntityImpl(conn);
	}

	public IEdificioDBEntity getEdificioDBEntity(DbConnection conn) {

		return new EdificioDBEntityImpl(conn);
	}

	public ISalaDBEntity getSalaDBEntity(DbConnection conn) {

		return new SalaDBEntityImpl(conn);
	}

	public IMesaDBEntity getMesaDBEntity(DbConnection conn) {

		return new MesaDBEntityImpl(conn);
	}

	public IUsuarioSalaConsultaDBEntity getUsuarioSalaConsultaDBEntity(
			DbConnection conn) {

		return new UsuarioSalaConsultaDBEntityImpl(conn);
	}

	public IUsuarioArchivoSalasConsultaDBEntity getUsuarioArchivoSalasConsultaDBEntity(
			DbConnection conn) {

		return new UsuarioArchivoSalasConsultaDBEntityImpl(conn);
	}

	public IRegistroConsultaSalaDBEntity getRegistroConsultaSalaDBEntity(
			DbConnection conn) {

		return new RegistroConsultaSalaDBEntityImpl(conn);
	}

	public IUIReeaCRDBEntity getUiReeaCRDBEntity(DbConnection conn) {
		return new UIReeaCRDBEntityImpl(conn);
	}

	public IUDocEnUIReeaCRDBEntity getUDocEnUIReeaCRDBEntity(DbConnection conn) {
		return new UDocEnUIReeaCRDBEntityImpl(conn);
	}

	public IUDocReeaCRDBEntity getUDocReeaCRDBEntity(DbConnection conn) {
		return new UDocReeaCRDBEntityImpl(conn);
	}

	public ICatalogoTablasTemporalesDBEntity getCatalogoTablasTemporalesDBEntity(
			DbConnection conn) {

		return new CatalogoTablasTemporalesDBEntityImpl(conn);
	}

	public ITablaTemporalDBEntity getTablaTemporalDBEntity(DbConnection conn) {

		return new TablaTemporalDBEntityImpl(conn);
	}

	public IEliminacionUDOCEliminadaDBEntity getEliminacionUDocEliminadaDBEntity(
			DbConnection conn) {
		return new EliminacionUDOCEliminadaDBEntityImpl(conn);
	}

	public IGuidGenDBEntity getGuidGenDBEntity(DbConnection conn) {
		return new GuidGenDBEntityImpl(conn);
	}

}