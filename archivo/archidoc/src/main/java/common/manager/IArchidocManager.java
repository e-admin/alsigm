package common.manager;

import fondos.db.ICatalogoTablasTemporalesDBEntity;
import fondos.db.IDivisionFSDbEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IEntidadProductoraDBEntity;
import fondos.db.IFondoDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.ISolicitudSerieDbEntity;
import fondos.db.IUDocEnDivisionFSDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
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
import salas.db.IEdificioDBEntity;
import salas.db.IMesaDBEntity;
import salas.db.IRegistroConsultaSalaDBEntity;
import salas.db.ISalaDBEntity;
import salas.db.IUsuarioArchivoSalasConsultaDBEntity;
import salas.db.IUsuarioSalaConsultaDBEntity;
import solicitudes.consultas.db.IConsultaDBEntity;
import solicitudes.consultas.db.IMotivoConsultaDBEntity;
import solicitudes.consultas.db.ITemaDBEntity;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.INSecSolicitudesDBEntity;
import solicitudes.db.IRevisionDocumentacionDBEntity;
import solicitudes.prestamos.db.IMotivoPrestamoDBEntity;
import solicitudes.prestamos.db.IPrestamoDBEntity;
import solicitudes.prestamos.db.IProrrogaDBEntity;
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
import deposito.db.IDepositoDbEntity;
import deposito.db.IDepositoElectronicoDBEntity;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.IFormatoDbEntity;
import deposito.db.IHistUInstalacionDepositoDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.INsecSigNumHuecoDBEntity;
import deposito.db.INumOrdenDBEntity;
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

public interface IArchidocManager {

	public String getDbFactoryClass();

	/**
	 * @return Entity que gestiona la tabla <b>AADATOSTRAZA</b>
	 */
	public abstract IDatosTrazaDBEntity getDatosTrazaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AANAUDITACCION</b>
	 */
	public abstract ICritAccionesDBEntity getCritAccionesDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AANAUDITUSR</b>
	 */
	public abstract ICritUsuariosDBEntity getCritUsuariosDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AASESION</b>
	 */
	public abstract ISessionDBEntity getSessionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AATRAZA</b>
	 */
	public abstract ITrazaDBEntity getTrazaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADAREA</b>
	 */
	public abstract IAreaDBEntity getAreaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADCAMPODATO</b>
	 */
	public abstract ICampoDatoDBEntity getCampoDatoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADCAMPOTBL</b>
	 */
	public abstract ICampoTablaDBEntity getCampoTablaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADCTLGLISTAD</b>
	 */
	public abstract ICatalogoListaDescriptoresDBEntity getCatalogoListaDescriptoresDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADCTLGTBLVLD</b>
	 */
	public abstract ITablaValidacionDBEntity getTablaValidacionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADDESCRIPTOR</b>
	 */
	public abstract IDescriptorDBEntity getDescriptorDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADFICHA</b>
	 */
	public abstract IFichaDBEntity getFichaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADFMTFICHA</b>
	 */
	public abstract IFmtFichaDBEntity getFmtFichaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADFMTPREF</b>
	 */
	public abstract IFmtPrefDBEntity getFmtPrefDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCCLASIFCF</b>
	 */
	public abstract IDocClasifCFDBEntity getDocClasifCFDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCCLASIFDESCR</b>
	 */
	public abstract IDocClasifDescrDBEntity getDocClasifDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCDOCUMENTOCF</b>
	 */
	public abstract IDocDocumentoCFDBEntity getDocDocumentoCFDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCDOCUMENTODESCR</b>
	 */
	public abstract IDocDocumentoDescrDBEntity getDocDocumentoDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCFICHACLF</b>
	 */
	public abstract IDocFichaDBEntity getDocFichaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADOCTCAPTURA</b>
	 */
	public abstract IDocTCapturaDBEntity getDocTCapturaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADPCDOCUMENTOVIT</b>
	 */
	public abstract IDocumentoVitalDBEntity getDocumentoVitalDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADPCTIPODOCPROC</b>
	 */
	public abstract ITipoDocVitProcedimientoDBEntity getTipoDocVitProcedimientoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADPCTIPODOCVIT</b>
	 */
	public abstract ITipoDocumentoVitalDBEntity getTipoDocumentoVitalDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADPCUSODOCVIT</b>
	 */
	public abstract IUsoDocumentoVitalDBEntity getUsoDocumentoVitalDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADTEXTTBLVLD</b>
	 */
	public abstract IValidacionDBEntity getValidacionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADUSOOBJETO</b>
	 */
	public abstract IUsoObjetoDBEntity getUsoObjetoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCFECHACF</b>
	 */
	public abstract IFechaDBEntity getFechaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCFECHADESCR</b>
	 */
	public abstract IFechaDescrDBEntity getFechaDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCNUMCF</b>
	 */
	public abstract INumeroDBEntity getNumeroDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCNUMDESCR</b>
	 */
	public abstract INumeroDescrDBEntity getNumeroDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCREFCF</b>
	 */
	public abstract IReferenciaDBEntity getReferenciaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ITextoDBEntity</b>
	 */
	public abstract IReferenciaDescrDBEntity getReferenciaDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTCF</b>
	 */
	public abstract ITextoDBEntity getTextoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTDESCR</b>
	 */
	public abstract ITextoCortoDescrDBEntity getTextoCortoDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTLCF</b>
	 */
	public abstract ITextoDBEntity getTextoLargoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTLDESCR</b>
	 */
	public abstract ITextoLargoDescrDBEntity getTextoLargoDescrDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AGARCHIVO</b>
	 */
	public abstract IArchivoDbEntity getArchivoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AGNIVELARCHIVO</b>
	 */
	public abstract INivelArchivoDbEntity getNivelArchivoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AGFORMATO</b>
	 */
	public abstract IFormatoDbEntity getFormatoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>AGOBJBLOQUEO</b>
	 */
	public abstract ILockDBEntity getLockDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAGRUPO</b>
	 */
	public abstract IGrupoDBEntity getGrupoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCALISTCA</b>
	 */
	public abstract IListaControlAccesoDbEntity getListaControlAccesoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAORGANO</b>
	 */
	public abstract ICAOrganoDbEntity getOrganoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAPERMGENROL</b>
	 */
	public abstract IPermisoRolDBEntity getPermisoRolDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAPERMLISTCA</b>
	 */
	public abstract IPermisosListaDbEntity getPermisosListaDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAROL</b>
	 */
	public abstract IRolDBEntity getRolDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAROLUSR</b>
	 */
	public abstract IRolUsuarioDBEntity getRolUsuarioDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAUSRGRP</b>
	 */
	public abstract IGrupoUsuarioDBEntity getGrupoUsuarioDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAUSRORG</b>
	 */
	public abstract IOrganoUsuarioDBEntity getOrganoUsuarioDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASCAUSUARIO</b>
	 */
	public abstract IUsuarioDBEntity getUsuarioDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDDEPOSITO</b>
	 */
	public abstract IDepositoDbEntity getDepositoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDDEPOSITOELECTRONICO</b>
	 */
	public abstract IDepositoElectronicoDBEntity getDepositoElectronicoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDELEMASIG</b>
	 */
	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDELEMNOASIG</b>
	 */
	public abstract IElementoNoAsignableDBEntity getElementoNoAsignableDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDHUECO</b>
	 */
	public abstract IHuecoDBEntity getHuecoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDSIGNUMORDEN</b>
	 */
	public abstract INumOrdenDBEntity getNumOrdenDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDTIPOELEMENTO</b>
	 */
	public abstract ITipoElementoDBEntity getTipoElementoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDUDOCENUI</b>
	 */
	public abstract IUDocEnUiDepositoDbEntity getuDocEnUiDepositoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDUINSTALACION</b>
	 */
	public abstract IUInstalacionDepositoDBEntity getuInstalacionDepositoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFELEMENTOCF</b>
	 */
	public abstract IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFELIMSELUDOC</b>
	 */
	public abstract IEliminacionUDOCEliminadaDBEntity getEliminacionUDOCEliminadaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFELIMUDOCCONS</b>
	 */
	public abstract IEliminacionUDOCConservadaDBEntity getEliminacionUDocConservadaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFELIMSERIE</b>
	 */
	public abstract IEliminacionSerieDBEntity getEliminacionSerieDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFENTPRODUCTORA</b>
	 */
	public abstract IEntidadProductoraDBEntity getEntidadProductoraDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFFONDO</b>
	 */
	public abstract IFondoDbEntity getFondoDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFHISTUDOC</b>
	 */
	public abstract IHistoricoUDOCDBEntity getHistoricoUDOCDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFNIVELCF</b>
	 */
	public abstract INivelCFDBEntity getNivelCFDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFNUMSECSEL</b>
	 */
	public abstract INSecVersionSelDBEntity getnSecVersionSelDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFNUMSECVAL</b>
	 */
	public abstract INSecVersionDBEntity getnSecVersionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFORGPROD</b>
	 */
	public abstract IOrganoProductorDbEntity getOrganoProductorDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFPRODSERIE</b>
	 */
	public abstract IProductorSerieDbEntity getProductorSerieDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFSERIE</b>
	 */
	public abstract ISerieDbEntity getSerieDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFSNSEC</b>
	 */
	public abstract valoracion.db.INSecValoracionDBEntity getnSecFondosDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFSOLICITUDSERIE</b>
	 */
	public abstract ISolicitudSerieDbEntity getSolicitudSerieDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFUNIDADDOC</b>
	 */
	public abstract IUnidadDocumentalDbEntity getUnidadDocumentalDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFVALSERIE</b>
	 */
	public abstract IValoracionDBEntity getValoracionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFVOLUMENSERIE</b>
	 */
	public abstract IVolumenSerieDBEntity getVolumenSerieDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPCONSULTA</b>
	 */
	public abstract IConsultaDBEntity getConsultaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPMTVCONSULTA</b>
	 */
	public abstract IMotivoConsultaDBEntity getMotivoConsultaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPMTVRECHAZO</b>
	 */
	public abstract IMotivoRechazoDBEntity getMotivoRechazoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPPRESTAMO</b>
	 */
	public abstract IPrestamoDBEntity getPrestamoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPPRORROGA</b>
	 */
	public abstract IProrrogaDBEntity getProrrogaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPSNSEC</b>
	 */
	public abstract INSecSolicitudesDBEntity getnSecSolicitudesDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPSOLICITUDUDOC</b>
	 */
	public abstract IDetalleDBEntity getDetalleDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPTEMA</b>
	 */
	public abstract ITemaDBEntity getTemaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTDETALLEPREV</b>
	 */
	public abstract IDetallePrevisionDBEntity getDetallePrevisionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTMAPDESCRUDOC</b>
	 */
	public abstract IMapDescUDocDBEntity getMapDescUDocDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTPREVISION</b>
	 */
	public abstract IPrevisionDBEntity getPrevisionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTRENTREGA</b>
	 */
	public abstract IRelacionEntregaDBEntity getRelacionEntregaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTSNSEC</b>
	 */
	public abstract INSecTransferenciasDBEntity getnSecTransferencias();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTSNSECUI</b>
	 */
	public abstract INSecUIDBEntity getnSecUIDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTSNSECUDOC</b>
	 */
	public abstract INSecUDocDBEntity getnSecUDocDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUDOCENUI</b>
	 */
	public abstract IUdocEnUIDBEntity getUdocEnUIDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUDOCSDF</b>
	 */
	public abstract IUdocElectronicaDBEntity getUdocElectronicaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUINSTALACIONRE</b>
	 */
	public abstract IUnidadInstalacionDBEntity getUnidadInstalacionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUNIDADDOCRE</b>
	 */
	public abstract IUDocRelacionDBEntity getuDocRelacionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ITDGUIDGEN</b>
	 */
	// public abstract IGuidGenDBEntity getGuidGenDBEntity();

	public abstract IInfoSistemaDBEntity getInfoSistemaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUINSTALACIONREEA</b>
	 */
	public abstract IUnidadInstalacionReeaDBEntity getUnidadInstalacionReeaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFUDOCSDF</b>
	 */
	public abstract IUnidadDocumentalElectronicaDBEntity getUnidadDocumentalElectronicaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCFECHAUDOCRE</b>
	 */
	public abstract IFechaDBEntity getFechaUdocReDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCNUMUDOCRE</b>
	 */
	public abstract INumeroDBEntity getNumeroUdocReDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCREFUDOCRE</b>
	 */
	public abstract IReferenciaDBEntity getReferenciaUdocReDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTLUDOCRE</b>
	 */
	public abstract ITextoDBEntity getTextoLargoUdocReDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ADVCTEXTUDOCRE</b>
	 */
	public abstract ITextoDBEntity getTextoCortoUdocReDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFDIVISIONFS</b>
	 */
	public abstract IDivisionFSDbEntity getDivisionFSDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFUDOCENDIVISIONFS</b>
	 */
	public abstract IUDocEnDivisionFSDbEntity getuDocEnDivisionFSDbEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFPZTRVALSERIE</b>
	 */
	public abstract IPlazosValoracionDBEntity getPlazosValoracionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDSIGNUMHUECO</b>
	 */
	public abstract INsecSigNumHuecoDBEntity getNsecSigNumHuecoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPREVDOCUDOC</b>
	 */
	public abstract IRevisionDocumentacionDBEntity getRevisionDocumentacionDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGDHISTUINSTALACION</b>
	 */
	public abstract IHistUInstalacionDepositoDBEntity getHistUInstalacionDepositoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGPMTVPRESTAMO</b>
	 */
	public abstract IMotivoPrestamoDBEntity getMotivoPrestamoDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSEDIFICIO</b>
	 */
	public abstract IEdificioDBEntity getEdificioDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSSALA</b>
	 */
	public abstract ISalaDBEntity getSalaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSMESA</b>
	 */
	public abstract IMesaDBEntity getMesaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSUSRCSALA</b>
	 */
	public abstract IUsuarioSalaConsultaDBEntity getUsuarioSalaConsultaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSUSRCSARCH</b>
	 */
	public abstract IUsuarioArchivoSalasConsultaDBEntity getUsuarioArchivoSalasConsultaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGSREGCSALA</b>
	 */
	public abstract IRegistroConsultaSalaDBEntity getRegistroConsultaSalaDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUIREEACR</b>
	 */
	public abstract IUIReeaCRDBEntity getUiReeaCRDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUDOCENUIREEACR</b>
	 */
	public abstract IUDocEnUIReeaCRDBEntity getuDocEnUIReeaCRDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGTUDOCREEACR</b>
	 */
	public abstract IUDocReeaCRDBEntity getuDocReeaCRDBEntity();

	/**
	 * @return Entity que gestiona la tabla <b>ASGFCTLGTBLTMP</b>
	 */
	public abstract ICatalogoTablasTemporalesDBEntity getCatalogoTablasTemporalesDBEntity();

	// /**
	// * Obtiene un GUID
	// * @return Cadena que contiene l GUID
	// */
	// public abstract String getGuid();

	/**
	 * Comprueba si un usuario tiene los permisos especificados
	 * 
	 * @param idUsuario
	 *            Cadena que contiene el identificador del usuario
	 * @param permisos
	 *            Array de cadena que contiene los permisos.
	 * @return true si el usuario tiene alguno de los permisos.
	 */
	public boolean hasPermisos(String idUsuario, String[] permisos);

	/**
	 * Comprueba si existe la signatura especificada en otra transferencia
	 * 
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @param signatura
	 *            Signatrua a comprobar
	 * @param idRelEntrega
	 *            Identificador de la relación entre a excluir en la búsqueda.
	 * @param signaturacionPorArchivo
	 *            Es signaturación por archivo
	 * @return
	 */
	public boolean existeSignaturaEnOtraTransferencia(String idArchivo,
			String signatura, String idRelEntrega,
			boolean signaturacionPorArchivo);

	/**
	 * Comprueba si existe la signatura especificada en el depósito
	 * 
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @param signatura
	 *            Signatrua a comprobar
	 * @param idRelEntrega
	 *            Identificador de la relación entre a excluir en la búsqueda.
	 * @param signaturacionPorArchivo
	 *            Es signaturación por archivo
	 * @return
	 */
	public boolean existeSignaturaDeposito(String idArchivo, String signatura,
			boolean signaturacionPorArchivo);

}