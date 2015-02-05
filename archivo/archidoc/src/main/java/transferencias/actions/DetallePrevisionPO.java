package transferencias.actions;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import se.NotAvailableException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import xml.config.InfoDetallePrev;

import common.Globals;
import common.bi.GestionArchivosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionSeriesBI;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;

/**
 * Información de presentacion de las líneas de detalle de una prevision de
 * transferencia
 * 
 * @see DetallePrevisionVO
 */
public class DetallePrevisionPO extends DetallePrevisionVO {

	/** Logger de la clase. */
	private final static Logger logger = Logger
			.getLogger(DetallePrevisionPO.class);

	GestionSeriesBI serieBI = null;
	GestionCuadroClasificacionBI cclfBI = null;
	GestorEstructuraDepositoBI depositoBI = null;
	GestorCatalogo catalogoProcedimientos = null;
	GestionArchivosBI archivosBI = null;
	GestionPrevisionesBI previsionBI = null;
	SerieVO serieDestino = null;
	SerieVO serieOrigen = null;
	InfoBProcedimiento procedimiento = null;
	FormatoHuecoVO formatoHueco = null;
	ArchivoVO archivoOrigen = null;
	ArchivoVO archivoDestino = null;

	InfoDetallePrev infoDetallePrev = null;

	protected DetallePrevisionPO(GestionSeriesBI serieBI,
			GestionCuadroClasificacionBI cclfBI,
			GestorCatalogo catalogoProcedimientos,
			GestorEstructuraDepositoBI depositoBI,
			GestionArchivosBI archivosBI, GestionPrevisionesBI previsionBI) {
		this.serieBI = serieBI;
		this.cclfBI = cclfBI;
		this.catalogoProcedimientos = catalogoProcedimientos;
		this.depositoBI = depositoBI;
		this.archivosBI = archivosBI;
		this.previsionBI = previsionBI;

	}

	public SerieVO getSerieDestino() {
		if (serieDestino == null)
			serieDestino = serieBI.getSerie(getIdSerieDestino());
		return serieDestino;
	}

	public SerieVO getSerieOrigen() {
		if (serieOrigen == null)
			serieOrigen = serieBI.getSerie(getIdSerieOrigen());
		return serieOrigen;
	}

	public ElementoCuadroClasificacionVO getFuncion() {
		SerieVO serie = getSerieDestino();
		return cclfBI.getElementoCuadroClasificacion(serie.getIdPadre());
	}

	public FormatoHuecoVO getFormato() {
		if (formatoHueco == null)
			formatoHueco = depositoBI.getFormatoHueco(getIdFormatoUI());
		return formatoHueco;
	}

	public InfoBProcedimiento getProcedimiento()
			throws GestorCatalogoException, NotAvailableException {
		// InfoBProcedimiento infoProcedimiento = null;
		if (getIdProcedimiento() != null) {
			if (procedimiento == null) {
				List procedimientos = catalogoProcedimientos
						.recuperarInfoBProcedimientos(new String[] { getIdProcedimiento() });
				if (procedimientos != null && procedimientos.size() > 0)
					procedimiento = (InfoBProcedimiento) procedimientos.get(0);
				// procedimiento =
				// catalogoProcedimientos.recuperarProcedimiento(getIdProcedimiento());
			}
			// infoProcedimiento = procedimiento.getInformacionBasica();
		}
		return procedimiento;
		// return infoProcedimiento;
	}

	public String getNombreArchivoOrigen() {
		PrevisionVO previsionVO = previsionBI.getPrevision(getIdPrevision());
		if (archivoOrigen == null) {
			archivoOrigen = archivosBI.getArchivoXId(previsionVO
					.getIdarchivoremitente());
		}
		return archivoOrigen.getNombre();
	}

	public String getNombreArchivoDestino() {
		PrevisionVO previsionVO = previsionBI.getPrevision(getIdPrevision());
		if (archivoDestino == null) {
			archivoDestino = archivosBI.getArchivoXId(previsionVO
					.getIdarchivoreceptor());
		}
		return archivoDestino.getNombre();
	}

	public String getTotalVolumenFormato() {
		if (infoDetallePrev == null) {
			// CARGAR LA INFORMACION DEL CAMPO INFO
			URL rulesInfoDetallePrev = DetallePrevisionPO.class
					.getClassLoader().getResource(
							Globals.RULES_INFO_DETALLE_PREV_CFG_FILE);

			Digester digester = DigesterLoader
					.createDigester(rulesInfoDetallePrev);
			String info = getInfo();
			try {
				if (!StringUtils.isBlank(info)) {
					this.infoDetallePrev = (InfoDetallePrev) digester
							.parse(new StringReader(info));
					if (infoDetallePrev != null) {
						return infoDetallePrev.getTotalUI();
					}
				}
			} catch (IOException e) {
				logger.error("Se ha producido un error al leer el fichero:" + e);
			} catch (SAXException e) {
				logger.error("Se ha producido un error al parsear el xml:" + e);
			}
		} else {
			return infoDetallePrev.getTotalUI();

		}
		return null;
	}

	public String getUdocsElectronicas() {
		if (infoDetallePrev == null) {
			// CARGAR LA INFORMACION DEL CAMPO INFO
			URL rulesInfoDetallePrev = DetallePrevisionPO.class
					.getClassLoader().getResource(
							Globals.RULES_INFO_DETALLE_PREV_CFG_FILE);

			Digester digester = DigesterLoader
					.createDigester(rulesInfoDetallePrev);
			String info = getInfo();
			try {
				if (!StringUtils.isBlank(info)) {
					this.infoDetallePrev = (InfoDetallePrev) digester
							.parse(new StringReader(info));
					if (infoDetallePrev != null) {
						return infoDetallePrev.getUdocsElectronicas();
					}
				}
			} catch (IOException e) {
				logger.error("Se ha producido un error al leer el fichero:" + e);
			} catch (SAXException e) {
				logger.error("Se ha producido un error al parsear el xml:" + e);
			}
		} else {
			return infoDetallePrev.getUdocsElectronicas();

		}
		return null;

	}

	public List getVolumenFormato() {
		if (infoDetallePrev == null) {
			// CARGAR LA INFORMACION DEL CAMPO INFO
			URL rulesInfoDetallePrev = DetallePrevisionPO.class
					.getClassLoader().getResource(
							Globals.RULES_INFO_DETALLE_PREV_CFG_FILE);

			Digester digester = DigesterLoader
					.createDigester(rulesInfoDetallePrev);
			String info = getInfo();
			try {
				if (!StringUtils.isBlank(info)) {
					this.infoDetallePrev = (InfoDetallePrev) digester
							.parse(new StringReader(info));
					if (infoDetallePrev != null) {
						return infoDetallePrev.getVolumenFormato();
					}
				}
			} catch (IOException e) {
				logger.error("Se ha producido un error al leer el fichero:" + e);
			} catch (SAXException e) {
				logger.error("Se ha producido un error al parsear el xml:" + e);
			}
		} else {
			return infoDetallePrev.getVolumenFormato();

		}
		return null;
	}

}
