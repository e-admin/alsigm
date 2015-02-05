package common.manager.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

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
import valoracion.db.INSecValoracionDBEntity;
import valoracion.db.INSecVersionDBEntity;
import valoracion.db.INSecVersionSelDBEntity;
import valoracion.db.IPlazosValoracionDBEntity;
import valoracion.db.IValoracionDBEntity;
import auditoria.db.ICritAccionesDBEntity;
import auditoria.db.ICritUsuariosDBEntity;
import auditoria.db.IDatosTrazaDBEntity;
import auditoria.db.ITrazaDBEntity;

import common.db.IDBEntity;
import common.definitions.ArchivoTables;
import common.lock.db.ILockDBEntity;
import common.manager.ArchidocDBBaseTest;
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
import es.ieci.tecdoc.fwktd.test.jndi.JndiContextLoader;
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

@ContextConfiguration({
	    "/beans/jndi-beans-test.xml",
})

public class ArchidocManagerTest extends ArchidocDBBaseTest
/* AbstractDbUnitTransactionalJUnit4SpringContextTests */{

//	@Autowired
//	public getManager()Impl getManager();



	/* (non-Javadoc)
	 * @see common.manager.ArchidocBaseTest#getDAO()
	 */
	@Override
	protected IDBEntity getDAO() {
		// TODO Auto-generated method stub
		return null;
	}


	@BeforeClass
	public static void setUp() throws Exception {
		new JndiContextLoader();
	}

	private String getTextoCount(String nombreTabla, int numeroRegistros) {
		return numeroRegistros + " Registro(s) en " + nombreTabla;

	}

	@Test
	public void datosTrazaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDatosTrazaDBEntity dbEntity = getManager().getDatosTrazaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AADATOSTRAZA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));

	}

	@Test
	public void critAccionesDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICritAccionesDBEntity dbEntity = getManager()
				.getCritAccionesDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AANAUDITACCION_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));

	}

	@Test
	public void critUsuariosDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICritUsuariosDBEntity dbEntity = getManager()
				.getCritUsuariosDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AANAUDITUSR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void sessionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ISessionDBEntity dbEntity = getManager().getSessionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AASESION_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void trazaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITrazaDBEntity dbEntity = getManager().getTrazaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AATRAZA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void areaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IAreaDBEntity dbEntity = getManager().getAreaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADAREA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void campoDatoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICampoDatoDBEntity dbEntity = getManager().getCampoDatoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADCAMPODATO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void campoTablaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICampoTablaDBEntity dbEntity = getManager().getCampoTablaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADCAMPOTBL_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void catalogoListaDescriptoresDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICatalogoListaDescriptoresDBEntity dbEntity = getManager()
				.getCatalogoListaDescriptoresDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADCTLGLISTAD_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void tablaValidacionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITablaValidacionDBEntity dbEntity = getManager()
				.getTablaValidacionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADCTLGTBLVLD_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void descriptorDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDescriptorDBEntity dbEntity = getManager().getDescriptorDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADDESCRIPTOR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fichaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFichaDBEntity dbEntity = getManager().getFichaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADFICHA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fmtFichaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFmtFichaDBEntity dbEntity = getManager().getFmtFichaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADFMTFICHA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fmtPrefDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFmtPrefDBEntity dbEntity = getManager().getFmtPrefDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADFMTPREF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docClasifCFDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocClasifCFDBEntity dbEntity = getManager()
				.getDocClasifCFDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCCLASIFCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docClasifDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocClasifDescrDBEntity dbEntity = getManager()
				.getDocClasifDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCCLASIFDESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docDocumentoCFDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocDocumentoCFDBEntity dbEntity = getManager()
				.getDocDocumentoCFDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCDOCUMENTOCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docDocumentoDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocDocumentoDescrDBEntity dbEntity = getManager()
				.getDocDocumentoDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCDOCUMENTODESCR_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docFichaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocFichaDBEntity dbEntity = getManager().getDocFichaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCFICHACLF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void docTCapturaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocTCapturaDBEntity dbEntity = getManager()
				.getDocTCapturaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADOCTCAPTURA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void documentoVitalDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDocumentoVitalDBEntity dbEntity = getManager()
				.getDocumentoVitalDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADPCDOCUMENTOVIT_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void tipoDocVitProcedimientoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITipoDocVitProcedimientoDBEntity dbEntity = getManager()
				.getTipoDocVitProcedimientoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADPCTIPODOCPROC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void tipoDocumentoVitalDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITipoDocumentoVitalDBEntity dbEntity = getManager()
				.getTipoDocumentoVitalDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADPCTIPODOCVIT_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void usoDocumentoVitalDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUsoDocumentoVitalDBEntity dbEntity = getManager()
				.getUsoDocumentoVitalDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADPCUSODOCVIT_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void validacionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IValidacionDBEntity dbEntity = getManager().getValidacionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADTEXTTBLVLD_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void usoObjetoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUsoObjetoDBEntity dbEntity = getManager().getUsoObjetoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADUSOOBJETO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fechaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFechaDBEntity dbEntity = getManager().getFechaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCFECHACF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fechaDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFechaDescrDBEntity dbEntity = getManager().getFechaDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCFECHADESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void numeroDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INumeroDBEntity dbEntity = getManager().getNumeroDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCNUMCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void numeroDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INumeroDescrDBEntity dbEntity = getManager()
				.getNumeroDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCNUMDESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void referenciaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IReferenciaDBEntity dbEntity = getManager().getReferenciaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCREFCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void referenciaDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IReferenciaDescrDBEntity dbEntity = getManager()
				.getReferenciaDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCREFDESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoCortoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoDBEntity dbEntity = getManager().getTextoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoCortoDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoCortoDescrDBEntity dbEntity = getManager()
				.getTextoCortoDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTDESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoLargoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoDBEntity dbEntity = getManager().getTextoLargoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTLCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoLargoDescrDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoLargoDescrDBEntity dbEntity = getManager()
				.getTextoLargoDescrDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTLDESCR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void archivoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IArchivoDbEntity dbEntity = getManager().getArchivoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AGARCHIVO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nivelArchivoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INivelArchivoDbEntity dbEntity = getManager()
				.getNivelArchivoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AGNIVELARCHIVO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void formatoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFormatoDbEntity dbEntity = getManager().getFormatoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AGFORMATO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void lockDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ILockDBEntity dbEntity = getManager().getLockDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AGOBJBLOQUEO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void grupoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IGrupoDBEntity dbEntity = getManager().getGrupoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAGRUPO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void listaControlAccesoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IListaControlAccesoDbEntity dbEntity = getManager()
				.getListaControlAccesoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCALISTCA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void organoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICAOrganoDbEntity dbEntity = getManager().getOrganoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAORGANO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void permisoRolDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IPermisoRolDBEntity dbEntity = getManager().getPermisoRolDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAPERMGENROL_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void permisosListaDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IPermisosListaDbEntity dbEntity = getManager()
				.getPermisosListaDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAPERMLISTCA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void rolDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IRolDBEntity dbEntity = getManager().getRolDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAROL_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void rolUsuarioDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IRolUsuarioDBEntity dbEntity = getManager().getRolUsuarioDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAROLUSR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void grupoUsuarioDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IGrupoUsuarioDBEntity dbEntity = getManager()
				.getGrupoUsuarioDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAUSRGRP_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void organoUsuarioDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IOrganoUsuarioDBEntity dbEntity = getManager()
				.getOrganoUsuarioDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAUSRORG_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void usuarioDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUsuarioDBEntity dbEntity = getManager().getUsuarioDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASCAUSUARIO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void depositoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDepositoDbEntity dbEntity = getManager().getDepositoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDDEPOSITO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void depositoElectronicoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDepositoElectronicoDBEntity dbEntity = getManager()
				.getDepositoElectronicoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDDEPOSITOELECTRONICO_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void elementoAsignableDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IElementoAsignableDBEntity dbEntity = getManager()
				.getElementoAsignableDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDELEMASIG_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void elementoNoAsignableDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IElementoNoAsignableDBEntity dbEntity = getManager()
				.getElementoNoAsignableDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDELEMNOASIG_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void huecoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IHuecoDBEntity dbEntity = getManager().getHuecoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDHUECO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void numOrdenDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INumOrdenDBEntity dbEntity = getManager().getNumOrdenDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDSIGNUMORDEN_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void tipoElementoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITipoElementoDBEntity dbEntity = getManager()
				.getTipoElementoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDTIPOELEMENTO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uDocEnUiDepositoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUDocEnUiDepositoDbEntity dbEntity = getManager()
				.getuDocEnUiDepositoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDUDOCENUI_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uInstalacionDepositoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUInstalacionDepositoDBEntity dbEntity = getManager()
				.getuInstalacionDepositoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDUINSTALACION_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void eliminacionUDOCEliminadaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IEliminacionUDOCEliminadaDBEntity dbEntity = getManager()
				.getEliminacionUDOCEliminadaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFELIMSELUDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void eliminacionUDocConservadaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IEliminacionUDOCConservadaDBEntity dbEntity = getManager()
				.getEliminacionUDocConservadaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFELIMUDOCCONS_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void eliminacionSerieDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IEliminacionSerieDBEntity dbEntity = getManager()
				.getEliminacionSerieDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFELIMSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void entidadProductoraDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IEntidadProductoraDBEntity dbEntity = getManager()
				.getEntidadProductoraDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFENTPRODUCTORA_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fondoDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFondoDbEntity dbEntity = getManager().getFondoDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFFONDO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void historicoUDOCDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IHistoricoUDOCDBEntity dbEntity = getManager()
				.getHistoricoUDOCDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFHISTUDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nivelCFDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INivelCFDBEntity dbEntity = getManager().getNivelCFDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFNIVELCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecVersionSelDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecVersionSelDBEntity dbEntity = getManager()
				.getnSecVersionSelDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFNUMSECSEL_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecVersionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecVersionDBEntity dbEntity = getManager()
				.getnSecVersionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFNUMSECVAL_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void organoProductorDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IOrganoProductorDbEntity dbEntity = getManager()
				.getOrganoProductorDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFORGPROD_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void productorSerieDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IProductorSerieDbEntity dbEntity = getManager()
				.getProductorSerieDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFPRODSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void elementoCuadroClasificacionDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IElementoCuadroClasificacionDbEntity dbEntity = getManager()
				.getElementoCuadroClasificacionDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFELEMENTOCF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void serieDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ISerieDbEntity dbEntity = getManager().getSerieDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecFondosDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecValoracionDBEntity dbEntity = getManager()
				.getnSecFondosDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFSNSEC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void solicitudSerieDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ISolicitudSerieDbEntity dbEntity = getManager()
				.getSolicitudSerieDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFSOLICITUDSERIE_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void unidadDocumentalDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUnidadDocumentalDbEntity dbEntity = getManager()
				.getUnidadDocumentalDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFUNIDADDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void valoracionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IValoracionDBEntity dbEntity = getManager().getValoracionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFVALSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void volumenSerieDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IVolumenSerieDBEntity dbEntity = getManager()
				.getVolumenSerieDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFVOLUMENSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void consultaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IConsultaDBEntity dbEntity = getManager().getConsultaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPCONSULTA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void motivoConsultaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IMotivoConsultaDBEntity dbEntity = getManager()
				.getMotivoConsultaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPMTVCONSULTA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void motivoRechazoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IMotivoRechazoDBEntity dbEntity = getManager()
				.getMotivoRechazoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPMTVRECHAZO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void prestamoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IPrestamoDBEntity dbEntity = getManager().getPrestamoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPPRESTAMO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void prorrogaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IProrrogaDBEntity dbEntity = getManager().getProrrogaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPPRORROGA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecSolicitudesDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecSolicitudesDBEntity dbEntity = getManager()
				.getnSecSolicitudesDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPSNSEC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void detalleDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDetalleDBEntity dbEntity = getManager().getDetalleDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPSOLICITUDUDOC_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void temaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITemaDBEntity dbEntity = getManager().getTemaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPTEMA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void detallePrevisionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDetallePrevisionDBEntity dbEntity = getManager()
				.getDetallePrevisionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTDETALLEPREV_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void mapDescUDocDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IMapDescUDocDBEntity dbEntity = getManager()
				.getMapDescUDocDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTMAPDESCRUDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void previsionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IPrevisionDBEntity dbEntity = getManager().getPrevisionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTPREVISION_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void relacionEntregaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IRelacionEntregaDBEntity dbEntity = getManager()
				.getRelacionEntregaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTRENTREGA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecTransferenciasTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecTransferenciasDBEntity dbEntity = getManager()
				.getnSecTransferencias();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTSNSEC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecUIDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecUIDBEntity dbEntity = getManager().getnSecUIDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTSNSECUI_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nSecUDocDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INSecUDocDBEntity dbEntity = getManager().getnSecUDocDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTSNSECUDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void udocEnUIDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUdocEnUIDBEntity dbEntity = getManager().getUdocEnUIDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUDOCENUI_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void udocElectronicaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUdocElectronicaDBEntity dbEntity = getManager()
				.getUdocElectronicaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUDOCSDF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void unidadInstalacionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUnidadInstalacionDBEntity dbEntity = getManager()
				.getUnidadInstalacionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUINSTALACIONRE_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uDocRelacionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUDocRelacionDBEntity dbEntity = getManager()
				.getuDocRelacionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUNIDADDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void infoSistemaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IInfoSistemaDBEntity dbEntity = getManager().getInfoSistemaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.AGINFOSISTEMA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}


	@Test
	public void unidadInstalacionReeaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUnidadInstalacionReeaDBEntity dbEntity = getManager()
				.getUnidadInstalacionReeaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUINSTALACIONREEA_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void unidadDocumentalElectronicaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUnidadDocumentalElectronicaDBEntity dbEntity = getManager()
				.getUnidadDocumentalElectronicaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFUDOCSDF_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void fechaUdocReDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IFechaDBEntity dbEntity = getManager().getFechaUdocReDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCFECHAUDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void numeroUdocReDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INumeroDBEntity dbEntity = getManager().getNumeroUdocReDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCNUMUDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void referenciaUdocReDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IReferenciaDBEntity dbEntity = getManager().getReferenciaUdocReDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCREFUDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoLargoUdocReDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoDBEntity dbEntity = getManager().getTextoLargoUdocReDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTLUDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void textoCortoUdocReDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ITextoDBEntity dbEntity = getManager().getTextoCortoUdocReDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ADVCTEXTUDOCRE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void divisionFSDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IDivisionFSDbEntity dbEntity = getManager().getDivisionFSDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFDIVISIONFS_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uDocEnDivisionFSDbEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUDocEnDivisionFSDbEntity dbEntity = getManager()
				.getuDocEnDivisionFSDbEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFUDOCENDIVISIONFS_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void plazosValoracionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IPlazosValoracionDBEntity dbEntity = getManager()
				.getPlazosValoracionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFPZTRVALSERIE_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void nsecSigNumHuecoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		INsecSigNumHuecoDBEntity dbEntity = getManager()
				.getNsecSigNumHuecoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDSIGNUMHUECO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void revisionDocumentacionDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IRevisionDocumentacionDBEntity dbEntity = getManager()
				.getRevisionDocumentacionDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPREVDOCUDOC_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void histUInstalacionDepositoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IHistUInstalacionDepositoDBEntity dbEntity = getManager()
				.getHistUInstalacionDepositoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGDHISTUINSTALACION_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void motivoPrestamoDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IMotivoPrestamoDBEntity dbEntity = getManager()
				.getMotivoPrestamoDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGPMTVPRESTAMO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void edificioDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IEdificioDBEntity dbEntity = getManager().getEdificioDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSEDIFICIO_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void salaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ISalaDBEntity dbEntity = getManager().getSalaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSSALA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void mesaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IMesaDBEntity dbEntity = getManager().getMesaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSMESA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void usuarioSalaConsultaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUsuarioSalaConsultaDBEntity dbEntity = getManager()
				.getUsuarioSalaConsultaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSUSRCSALA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void usuarioArchivoSalasConsultaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUsuarioArchivoSalasConsultaDBEntity dbEntity = getManager()
				.getUsuarioArchivoSalasConsultaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSUSRCSARCH_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void registroConsultaSalaDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IRegistroConsultaSalaDBEntity dbEntity = getManager()
				.getRegistroConsultaSalaDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGSREGCSALA_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uiReeaCRDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUIReeaCRDBEntity dbEntity = getManager().getUiReeaCRDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUIREEACR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uDocEnUIReeaCRDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUDocEnUIReeaCRDBEntity dbEntity = getManager()
				.getuDocEnUIReeaCRDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUDOCENUIREEACR_TABLE_NAME
				.equals(dbEntity.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void uDocReeaCRDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		IUDocReeaCRDBEntity dbEntity = getManager().getuDocReeaCRDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGTUDOCREEACR_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}

	@Test
	public void catalogoTablasTemporalesDBEntityTest() {
		Assert.notNull(getManager(), "getManager() es nulo");
		ICatalogoTablasTemporalesDBEntity dbEntity = getManager()
				.getCatalogoTablasTemporalesDBEntity();
		Assert.notNull(dbEntity, "entity es nulo");
		Assert.isTrue(ArchivoTables.ASGFCTLGTBLTMP_TABLE_NAME.equals(dbEntity
				.getTableName()),"Las tablas no coinciden");

		logger.info(getTextoCount(dbEntity.getTableName(), dbEntity.getCount()));
	}


}
