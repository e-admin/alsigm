package common.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import solicitudes.consultas.ConsultasConstants;
import transferencias.TipoTransferencia;
import transferencias.model.EstadoPrevision;
import transferencias.model.EstadoREntrega;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.RelacionEntregaVO;

import common.Constants;
import common.db.IDBEntity;
import common.manager.impl.ArchidocManagerConnectionImpl;
import common.util.DateUtils;


@ContextConfiguration( {
	"/beans/datasource-beans-test.xml",
	"/beans/transaction-beans-test.xml"

})
public abstract class ArchidocDBBaseTest extends ArchidocBaseTest
	/*extends AbstractDbUnitTransactionalJUnit4SpringContextTests*/{

	protected abstract IDBEntity getDAO();


	//AGARCHIVO
	protected String idArchivo = "IDARCHIVO";

	//ASCAORGANO
	protected String idOrgano = "IDORGANO";
	protected String codigoOrgano = "CODIGOORGANO";
	protected String nombreOrgano = "NOMBREORGANO";
	protected String nombreLargoOrgano = "NOMBRELARGOORGANO";
	protected String sistExtGestor = "SISTEXTGESTOR";
	protected String idOrgsExtGestor = "IDORGSEXTGESTOR";

	//ADDESCRIPTOR;
	protected String idDescriptor = "IDDESCRIPTOR";
	protected String nombreDescriptor = "NOMBREDESCRIPTOR";
	protected String idListaDescriptor = "IDLISTADESCRIPTOR";

	//ASGFPRODSERIE;
	protected String idProductor = "IDPRODUCTOR";

	//ASGFORGPROD;


	//ASGFELEMENTOCF
	protected String idElemento = "IDELEMENTO";
	protected String codigoElemento = "CODIGO";
	protected String tipoElemento = "TIPO";

	protected String idDeposito = "IDDEPOSITO";
	protected String codOrden = "CODORDEN";
	protected String idFormato = "IDFORMATO";

	protected String[] idsPadres = new String[]{"ID1","ID2"};


	protected String idSerie = "IDSERIE";
	protected String nombreProductor="NOMBREPRODUCTOR";


	protected String idNivel = "ID";
	protected String nombreNivel = "NOMBRE";
	protected int tipoNivel = 1;

	protected String idUdocRe = "IDUDOCRE";

	protected String idElementoCf = "IDELEMENTOCF";
	protected String idCampo = "IDCAMPO";
	protected int ultimoOrden = 1;


	protected String idUnidadDoc = "IDUNIDADDOC";

	protected String idUsuario = "IDUSUARIO";

	protected String idFS = "IDFS";

	protected String idUdoc = "IDUDOC";

	protected String regEntrada = "REGENTRADA";


	protected String etiquetaXml = "etiquetaxml";
	protected String idTabla = "idtabla";


	protected String signatura = "SIGNATURA";

	protected String idPrevision = "IDPREVISION";

	protected String idDetallePrevision = "IDDETPREVISION";

	protected String idOrgRemitente = "IDORGANOREMITENTE";

	protected String idUsrGestor = "IDUSRGESTOR";

	protected int numPrevisiones = 1;


	protected String id1 = "ID1";
	protected String id2 = "ID2";
	protected String numeroExpediente = "NUMEXP";
	protected String idRelEntrega = "IDRELENTREGA";
	protected String asunto = "ASUNTO";
	protected String codSistProductor ="CODSISTPRODUCTOR";
	protected Date fechaInicio = DateUtils.getDate("01/01/2012");
	protected Date fechaFin = DateUtils.getDate("31/12/2012");



	protected int[] estadosSolicitud = new int[]{
			ConsultasConstants.ESTADO_SOLICITUD_PENDIENTE,
			ConsultasConstants.ESTADO_SOLICITUD_RESERVADA,
			ConsultasConstants.ESTADO_SOLICITUD_AUTORIZADA,
			ConsultasConstants.ESTADO_SOLICITUD_ENTREGADA
	};

	protected RelacionEntregaVO relacionEntregaVO;

	protected PrevisionVO previsionVO;

	protected String codSistemaProductor = "CODSISTPRODUCTOR";

	protected String nombreSistemaProductor = "NOMBRESISTPRODUCTOR";

	protected String idFondoDestino = "IDFONDODESTINO";

	protected String idProcedimiento = "IDPROCEDIMIENTO";

	protected String idArchivoReceptor = "IDARCHIVORECEPTOR";

	protected int estadoPrevision = EstadoPrevision.AUTOMATIZADA.getIdentificador();

	protected int estadoRelacionEntrega = EstadoREntrega.VALIDADA_AUTOMATIZADA.getIdentificador();

	protected int anio = 2012;

	protected String anioString = String.valueOf(anio);

	protected int orden = 1;

	protected String motivoRechazo = "MOTIVORECHAZO";

	protected String observaciones = "OBSERVACIONES";

	protected String idArchivoRemitente = "IDARCHIVOREMITENTE";

	protected int tipoTransferencia = TipoTransferencia.ORDINARIA.getIdentificador();

	private DetallePrevisionVO detallePrevision;

	private String idSerieDestino = "IDSERIEDESTINO";

	private int numUInstalacion = 0;

	private int numrentrega = 1;

	private String cerrada = Constants.FALSE_STRING;

	private String idSerieOrigen = "IDSERIEORIGEN";

	private String info = "INFO";


	protected PrevisionVO getPrevisionVOElectronica() {
		if(previsionVO == null){
			previsionVO = new PrevisionVO();

			previsionVO.setId(idPrevision);
			previsionVO.setTipotransferencia(tipoTransferencia);
			previsionVO.setTipoprevision(PrevisionVO.PREVISION_DETALLADA);
			previsionVO.setIdorgremitente(idOrgRemitente);
			previsionVO.setAno(String.valueOf(anio));
			previsionVO.setOrden(orden );
			previsionVO.setIdfondodestino(idFondoDestino);
			previsionVO.setFechainitrans(DateUtils.getFirstDayOfYear(anio));
			previsionVO.setFechafintrans(DateUtils.getLastDayOfYear(anio));
			previsionVO.setNumuinstalacion(0);
			previsionVO.setEstado(estadoPrevision);
			previsionVO.setIdarchivoreceptor(idArchivoReceptor);
			previsionVO.setIdusrgestor(idUsrGestor);
			previsionVO.setMotivorechazo(motivoRechazo );
			previsionVO.setObservaciones(observaciones);
			previsionVO.setIdarchivoremitente(idArchivoRemitente );
		}

		return previsionVO;
	}

	protected DetallePrevisionVO getMockDetallePrevisionVO(){
		if(detallePrevision == null){
			detallePrevision = new DetallePrevisionVO();
			detallePrevision.setId(idDetallePrevision);
			detallePrevision.setIdPrevision(idPrevision);
			detallePrevision.setNumeroOrden(orden);
			detallePrevision.setCodSistProductor(codSistemaProductor);
			detallePrevision.setNombreSistProductor(nombreSistemaProductor);
			detallePrevision.setIdProcedimiento(idProcedimiento);
			detallePrevision.setIdSerieDestino(idSerieDestino);
			detallePrevision.setAnoFinUdoc(anioString);
			detallePrevision.setAnoIniUdoc(anioString);
			detallePrevision.setNumUInstalacion(numUInstalacion );
			detallePrevision.setIdFormatoUI(idFormato);
			detallePrevision.setCerrado(cerrada);
			detallePrevision.setNumrentrega(numrentrega);
			detallePrevision.setObservaciones(observaciones);
			detallePrevision.setIdSerieOrigen(idSerieOrigen );
			detallePrevision.setInfo(info);

		}
		return detallePrevision;
	}

	protected RelacionEntregaVO getMockRelacionEntregaElectronica(){
		if(relacionEntregaVO == null){
			relacionEntregaVO = new RelacionEntregaVO();
			relacionEntregaVO.setId(id1);
			relacionEntregaVO.setEstado(estadoRelacionEntrega);
			relacionEntregaVO.setTipotransferencia(tipoTransferencia);
			relacionEntregaVO.setIdorganoremitente(idOrgRemitente);
			relacionEntregaVO.setCodsistproductor(codSistemaProductor);
			relacionEntregaVO.setNombresistproductor(nombreSistemaProductor);
			relacionEntregaVO.setIdfondo(idFondoDestino);
			relacionEntregaVO.setIdprocedimiento(idProcedimiento);
			relacionEntregaVO.setIdarchivoreceptor(idArchivoReceptor);
			relacionEntregaVO.setAno(String.valueOf(anio));
		}

		return relacionEntregaVO;
	}


	@Autowired
	public DataSource dataSource;

	private Connection conn;

	private IArchidocManager manager;

	protected Connection getConn() throws SQLException{
		if(conn == null){
			conn = dataSource.getConnection();
		}
		return dataSource.getConnection();
	}

	protected IArchidocManager getManager(){
		if(manager == null){
			try {
				manager = new ArchidocManagerConnectionImpl(getConn());
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return manager;
	}

}
