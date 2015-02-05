package es.ieci.plusvalias.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.util.NumberConversionUtil;

/**
 * @author angel_castro@ieci.es - 23/07/2010
 */
public class ReportService implements ResourceLoaderAware
{

	public static final Logger logger = Logger.getLogger(ReportService.class);
	private String reportDir;
	private ResourceLoader resourceLoader;

	public byte[] generarReport(String tipoDocumento, Plusvalia plusvalia)
			throws Exception
	{
		String resourcesDir = "." + File.separator + reportDir + File.separator + "resources" + File.separator;
		Map<String, String> param = new HashMap<String, String>();
		
		Transmitente transmitente = plusvalia.getTransmitente();
		Adquiriente adquiriente = plusvalia.getAdquiriente();

		if (tipoDocumento.equals("calculo"))
		{
			ResultadoUnitario plusvaliaParcial = plusvalia.getResultado();

			param.put("IMPORTEPARCIAL", NumberConversionUtil.formatNumber(plusvaliaParcial.getTotalLiquidacion()));
			param.put("PORCENTAJE", NumberConversionUtil.formatNumber(transmitente.getPorcentajeTransmitido()));
			param.put("TOTALPLUSVALIA", NumberConversionUtil.formatNumber(plusvaliaParcial.getTotalLiquidacion()));
			param.put("REFCATASTRAL", plusvalia.getInmueble().getRefCatastral());

			String situacion = plusvalia.getInmueble().getTipovia() + " " + plusvalia.getInmueble().getNombrevia() + " " + plusvalia.getInmueble().getNumero() + " "
					+ plusvalia.getInmueble().getEscalera() + " " + plusvalia.getInmueble().getPlanta() + " " + plusvalia.getInmueble().getPuerta();

			param.put("NOMBREVIA", situacion.replaceAll("null", "").trim());
			
			
			if (plusvalia.getFolderIdRegistro() != null && plusvalia.getFolderIdRegistro().length() > 0)
			{
				param.put("NUMEROREGISTRO", plusvalia.getFolderIdRegistro());
			}
			else
			{
				//param.put("NUMEROREGISTRO", "" + System.currentTimeMillis());
				param.put("NUMEROREGISTRO", "BORRADOR");
			}

			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(plusvalia.getFechatactual());
			int anno = cal.get(Calendar.YEAR);
			param.put("ANNOTRANSMISION", String.valueOf(anno));

			param.put("NOTARIA", plusvalia.getNotario());
			param.put("PROTOCOLO", String.valueOf(plusvalia.getNumProtocolo()));
			param.put("NIFADQ", adquiriente.getNif());
			param.put("NIFTRAN", plusvalia.getNifTrans());
			param.put("NOMBREADQ", adquiriente.getNombreCompleto());
			param.put("DIRECCIONADQ", adquiriente.getCalle());
			param.put("POBLACIONADQ", adquiriente.getPoblacion());
			param.put("CPADQ", adquiriente.getCp());
			param.put("NOMBRETRAN", transmitente.getNombreCompleto());
			param.put("DIRECCIONTRAN", transmitente.getCalle());
			param.put("POBLACIONTRAN", transmitente.getPoblacion());
			param.put("CPTRAN", transmitente.getCp());
			param.put("DERECHO", plusvaliaParcial.getTextDerecho());
			param.put("PORTRAN", NumberConversionUtil.formatNumber(transmitente.getPorcentajeTransmitido()));
			param.put("PORADQ", NumberConversionUtil.formatNumber(adquiriente.getCuotaParticipacion()));
			param.put("FECTRANSANTE", NumberConversionUtil.formatDate(plusvalia.getFechatanterior()));
			param.put("FECTRANSACT", NumberConversionUtil.formatDate(plusvalia.getFechatactual()));
			// TODO IBI
			param.put("IBI", "");
			param.put("VALORSUELO", NumberConversionUtil.formatNumber(plusvaliaParcial.getValorUnitario()));
			param.put("VALORREDUCIDO", NumberConversionUtil.formatNumber(plusvaliaParcial.getValorTerreno()));
			param.put("VALORFINAL", NumberConversionUtil.formatNumber(plusvaliaParcial.getValorTerrenoFin()));
			param.put("PORDEDUC", NumberConversionUtil.formatNumber(plusvaliaParcial.getPorcentajeDeduccion()));
			param.put("ANNOS", String.valueOf(plusvaliaParcial.getNumAnnos()));
			param.put("PORCANUAL", NumberConversionUtil.formatNumber(plusvaliaParcial.getPorcentajePlusvalia()));
			param.put("BASEIMPONIBLE", NumberConversionUtil.formatNumber(plusvaliaParcial.getBaseImponible()));
			param.put("TIPOIMPO", NumberConversionUtil.formatNumber(plusvaliaParcial.getTipoImpositivo()));
			param.put("CUOTATRI", NumberConversionUtil.formatNumber(plusvaliaParcial.getCuotaLiquida()));
			param.put("PORCBONIF", NumberConversionUtil.formatNumber(plusvaliaParcial.getPorcBonif()));
			param.put("CUOTABONIF", NumberConversionUtil.formatNumber(plusvaliaParcial.getCuotaBonificada()));
			param.put("RECARGO", NumberConversionUtil.formatNumber(plusvaliaParcial.getRecargo()));
			
			param.put("DIASINTERES", String.valueOf(plusvaliaParcial.getDiasinteres()));
			param.put("PORCINTERES", NumberConversionUtil.formatNumber(plusvaliaParcial.getPorcInteres()));
			param.put("INTERES", NumberConversionUtil.formatNumber(plusvaliaParcial.getInteres()));
			
			param.put("SITUACION", plusvaliaParcial.getSituacionDocumento());
		}
		else
		{
			param.put("NOMBRESUJETO", adquiriente.getNombreCompleto());
			param.put("NIFSUJETO", adquiriente.getNif());
			param.put("PORCENTAJE", NumberConversionUtil.formatNumber(adquiriente.getCuotaParticipacion()));
			param.put("TOTALPLUSVALIA", NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));
			param.put("REFCATASTRAL", plusvalia.getInmueble().getRefCatastral());
			param.put("NOMBREVIA", plusvalia.getInmueble().getNombrevia());
			param.put("NUMERO", plusvalia.getInmueble().getNumero());
			param.put("IMPORTEPARCIAL", NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));
			param.put("NUMEROREGISTRO", plusvalia.getFolderIdRegistro());
		}

		Resource resourceDir = resourceLoader.getResource(resourcesDir);
		param.put("RESOURCES_DIR", resourceDir.getFile().getAbsolutePath() + File.separator);

		JasperPrint print;

		try
		{
			// Intentamos rellenar directamente el .jasper
			logger.info("Completando .jasper");
			String relativeLocation = reportDir + File.separator + tipoDocumento + ".jasper";
			Resource report = resourceLoader.getResource(relativeLocation);
			print = JasperFillManager.fillReport(report.getInputStream(), param);
		}
		catch (JRException e)
		{
			// Si falla problablemente es necesario compilar el report de nuevo
			logger.info("Recompilando .jasper desde .jrxml");
			String relativeLocation = reportDir + File.separator + tipoDocumento + ".jrxml";
			Resource jrxml = resourceLoader.getResource(relativeLocation);
			JasperReport report = JasperCompileManager.compileReport(jrxml.getInputStream());
			print = JasperFillManager.fillReport(report, param);
		}

		logger.debug("Informe Jasper generado");

		byte[] pdf = JasperExportManager.exportReportToPdf(print);

		if (logger.isDebugEnabled())
		{
			JasperExportManager.exportReportToPdfFile(print, "./plusvalia_" + tipoDocumento + "_" + new Date().getTime() + ".pdf");
		}
		return pdf;
	}

	@SuppressWarnings("unchecked")
	public byte[] generarReport(String tipoDocumento, Adquiriente adquiriente, Plusvalia plusvalia, double importeLiquidacion, String numRegistro)
		throws Exception
	{
		String resourcesDir = reportDir + File.separator + "resources";
		Map param = new HashMap();

		if (tipoDocumento.equals("calculo"))
		{

		}
		else
		{
			param.put("NOMBRESUJETO", adquiriente.getNombreCompleto());
			param.put("NIFSUJETO", adquiriente.getNif());
			param.put("PORCENTAJE", NumberConversionUtil.formatNumber(adquiriente.getCuotaParticipacion()));
			param.put("TOTALPLUSVALIA", NumberConversionUtil.formatNumber(plusvalia.getResultado().getTotalLiquidacion()));
			param.put("REFCATASTRAL", plusvalia.getInmueble().getRefCatastral());
			param.put("NOMBREVIA", plusvalia.getInmueble().getNombrevia());
			param.put("NUMERO", plusvalia.getInmueble().getNumero());
			param.put("IMPORTEPARCIAL", NumberConversionUtil.formatNumber(importeLiquidacion));
			param.put("RESOURCESDIR", resourcesDir);
			param.put("NUMEROREGISTRO", numRegistro);
		}

		// No es necesario compilar el report, rellenamos directamente el .jasper
		// JasperReport report = JasperCompileManager.compileReport(reportDir +
		// File.separator + tipoDocumento + ".jrxml");

		String relativeLocation = reportDir + File.separator + tipoDocumento + ".jasper";
		Resource report = resourceLoader.getResource(relativeLocation);
		JasperPrint print = JasperFillManager.fillReport(report.getInputStream(), param);
		logger.debug("Informe Jasper generado");

		byte[] pdf = JasperExportManager.exportReportToPdf(print);

		if (logger.isDebugEnabled())
		{
			JasperExportManager.exportReportToPdfFile(print, "./plusvalia_" + tipoDocumento + "_" + new Date().getTime() + ".pdf");
		}
		
		return pdf;
	}

	public void setReportDir(String reportDir)
	{
		this.reportDir = reportDir;
	}

	public void setResourceLoader(ResourceLoader resourceLoader)
	{
		this.resourceLoader = resourceLoader;
	}
}