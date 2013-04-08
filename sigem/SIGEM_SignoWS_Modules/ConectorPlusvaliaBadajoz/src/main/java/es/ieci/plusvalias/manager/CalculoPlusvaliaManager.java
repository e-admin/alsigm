package es.ieci.plusvalias.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.dao.ActjuridancertDao;
import es.ieci.plusvalias.dao.CataDao;
import es.ieci.plusvalias.dao.DeduccionDao;
import es.ieci.plusvalias.dao.DerechoDao;
import es.ieci.plusvalias.dao.InmuebleDao;
import es.ieci.plusvalias.dao.IntedemoraDao;
import es.ieci.plusvalias.dao.PlusvalConfigDao;
import es.ieci.plusvalias.dao.PlusvaliaTempDao;
import es.ieci.plusvalias.dao.PorcentajeDao;
import es.ieci.plusvalias.dao.RecargoDao;
import es.ieci.plusvalias.dao.ReduccionDao;
import es.ieci.plusvalias.dao.SituacionDao;
import es.ieci.plusvalias.dao.TipoimpositivoDao;
import es.ieci.plusvalias.domain.ActjuridancertDTO;
import es.ieci.plusvalias.domain.CataDTO;
import es.ieci.plusvalias.domain.DerechoDTO;
import es.ieci.plusvalias.domain.PlusvaliaTempDTO;
import es.ieci.plusvalias.domain.RecargoDTO;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;
import es.ieci.plusvalias.util.Constants;
import es.ieci.plusvalias.util.DateUtils;

public class CalculoPlusvaliaManager {

	public static Logger logger = Logger.getLogger(CalculoPlusvaliaManager.class);
	
	private DerechoDao derechodao;
	private RecargoDao recargodao;
	private CataDao catadao;
	private PlusvaliaTempDao plusvaliatempdao;
	private PorcentajeDao porcentajedao;
	private ReduccionDao reducciondao;
	private TipoimpositivoDao tipoimpositivodao;
	private InmuebleDao inmuebledao;
	private DeduccionDao deducciondao;
	private ActjuridancertDao actjuridancertdao;
	private IntedemoraDao intedemoradao;
	private SituacionDao situaciondao;
	private PlusvalConfigDao plusvaliaconfigdao;

	/**
	 * Calculamos la plusvalía por transmitiente y por adquiriente
	 * 
	 * @param inmueble
	 * @param adquiriente
	 * @param transmitente
	 * @param datosLiquidacion
	 * @param propiedades
	 * @return
	 * @throws Exception
	 */
	public ResultadoUnitario calcular(String actoJuridico,Inmueble inmueble, Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion) throws Exception	{
		int anyoLiquidacionActual = DateUtils.getYearFrom(datosLiquidacion.getFechaTransActual());
		Map propiedades = getPlusvaliaProperties(anyoLiquidacionActual);
		
		String tipoAncert=getTipoAncert(actoJuridico);
		Calendar fechaActual = GregorianCalendar.getInstance();
		
		Calendar fechaTransActual = GregorianCalendar.getInstance();
		fechaTransActual.setTime(datosLiquidacion.getFechaTransActual());

		Calendar fechaTransAnterior = GregorianCalendar.getInstance();
		fechaTransAnterior.setTime(transmitente.getFechaTransAnterior());

		// Cálculo del valor del suelo Original
		double superficieSolarOriginal = inmueble.getSupsolar();
		double valorSueloOriginal = inmueble.getValsuelo();
		double valorSuelo = valorSueloOriginal;

		if (superficieSolarOriginal != 0){
			double valorMetroCuadrado = valorSueloOriginal / superficieSolarOriginal;
			valorSuelo = superficieSolarOriginal * valorMetroCuadrado;
		}

		// Operador y coeficiente de MaeCata
		double coeficiente = ((Double)propiedades.get("coeficiente")).doubleValue();
		String operador = (String) propiedades.get("operador");

		if (operador.equals("*")){
			valorSuelo = valorSuelo * coeficiente;
		}else if (operador.equals("/")){
			valorSuelo = valorSuelo / coeficiente;
		}

		// Porcentaje Adquirido
		double valorUnitario = valorSuelo * (adquiriente.getCuotaParticipacion() / 100);

		// Porcentaje Transmitido
		valorUnitario = valorUnitario * (transmitente.getPorcentajeTransmitido() / 100);

		// VALOR UNITARIO TERRENO // valor reducido
		double porcentajeReduccion = reducciondao.findByYear(new Integer(anyoLiquidacionActual)).doubleValue();
		double valorUnitarioReducidoTerreno = valorUnitario * porcentajeReduccion;

		// VALOR TERRENO FIN // valor final
		int edadSujeto = transmitente.getEdad();
		int claseDerecho = transmitente.getNumDerecho();
		double valorUnitarioTerrenoFinal = 0;
		double porcentajeDeduc = 0;	
		
		int numAnyosALiquidar = DateUtils.getNumAnyos(fechaTransActual, fechaTransAnterior);

		if (claseDerecho == 1 || claseDerecho == 6)	{
			porcentajeDeduc = 0;
			valorUnitarioTerrenoFinal = valorUnitarioReducidoTerreno;
		}else if (claseDerecho == 2 || claseDerecho == 3 || claseDerecho == 4)	{			
			porcentajeDeduc = deducciondao.findByAnyos(new Integer(edadSujeto)).doubleValue();			
			
			// Usufructo vitalicio porcentaje de deduccion  = 100% - deducción por edad
			if (claseDerecho == 3)	{
				porcentajeDeduc = 100 - porcentajeDeduc;
			}
			
			valorUnitarioTerrenoFinal = valorUnitarioReducidoTerreno - valorUnitarioReducidoTerreno * porcentajeDeduc / 100;
		}else if (claseDerecho == 7){
			double porcentajeValorSuelo = 2 * transmitente.getAnyosUsufruto();
			
			// Valor Catastral del terreno para usufructo temporal no puede superar 70% del valor catastral
			if (porcentajeValorSuelo > 70)	{
				porcentajeValorSuelo = 70;
			}
			
			valorUnitarioReducidoTerreno = valorUnitarioReducidoTerreno * porcentajeValorSuelo / 100;
			
			porcentajeDeduc = deducciondao.findByAnyos(new Integer(edadSujeto)).doubleValue();
			
			valorUnitarioTerrenoFinal = valorUnitarioReducidoTerreno - ((valorUnitarioReducidoTerreno * porcentajeDeduc / 100));
		}

		// BASE IMPONIBLE
		// int numAnyos = fechaTransActual.get(Calendar.YEAR) -
		// fechaTransAnterior.get(Calendar.YEAR);
		
		double porcentajePlus = porcentajedao.findByYears(new Integer(anyoLiquidacionActual), new Integer(numAnyosALiquidar)).doubleValue();
		int numAnyosBaseImpo = 0;
		
		int maxAniosLiquidar = plusvaliaconfigdao.findFirst().getAniosMaxLiquidar();
		
		if (numAnyosALiquidar > maxAniosLiquidar) {
			numAnyosBaseImpo = maxAniosLiquidar;
		}else{
			numAnyosBaseImpo = numAnyosALiquidar;
		}

		double baseImponible = valorUnitarioTerrenoFinal * porcentajePlus / 100 * numAnyosBaseImpo;

		// CUOTA LIQUIDA
		double tipoImpo = tipoimpositivodao.findByYears(new Integer(anyoLiquidacionActual), new Integer(numAnyosALiquidar)).doubleValue();
		double cuotaLiquida = baseImponible * tipoImpo / 100;

		// CUOTA Y BONIFICACION O EXENCION
		double porcentajeBonif = transmitente.getBonificacion();
		double bonificacion = (cuotaLiquida * porcentajeBonif) / 100;
		double cuotaBonificada = cuotaLiquida - bonificacion;

		// RECARGOS
		int numDerecho = transmitente.getNumDerecho();
		DerechoDTO derecho = derechodao.findByCodAncert(anyoLiquidacionActual, numDerecho, tipoAncert);
		int numDias = derecho.getDias().intValue();
		double recargo = 0;
		double porcentajeRecargo = 0;
		double interes = 0;
		int numDiasInteres = 0;
		double porcentajeInteres = 0;

		long diferencia = (fechaActual.getTimeInMillis() - fechaTransActual.getTimeInMillis()) / Constants.MILLSECS_PER_DAY;

		if (numDias > diferencia){
			recargo = 0;
		}else{
			int numDiasRecargo = (int) (diferencia - numDias);
			RecargoDTO recargoDto = recargodao.findByYear(new Integer(anyoLiquidacionActual), new Integer(numDiasRecargo));
			porcentajeRecargo = recargoDto.getPorcentaje().doubleValue();
			recargo = cuotaBonificada * porcentajeRecargo / 100;
			
			// Hay que liquidar intereses?
			if (recargoDto.getLiqinteresessn().trim().equalsIgnoreCase("S") && numDiasRecargo > 365){
				numDiasInteres = numDiasRecargo - 365;
				
				porcentajeInteres = intedemoradao.findByYear(fechaActual.get(Calendar.YEAR) - 1);
				
				interes = (cuotaBonificada * porcentajeInteres * numDiasInteres) / 36500;
			}
		}

		// TOTAL LIQUIDACION
		double totalLiquidacion = cuotaBonificada + recargo + interes;
		
		String situacion = situaciondao.findByYearTexto(anyoLiquidacionActual, SituacionDao.EN_TRAMITE);
		String situacionDocumento = "";
		
		// SIN INCREMENTO DE VALOR
		if (numAnyosALiquidar == 0){
			situacion = situaciondao.findByYearTexto(anyoLiquidacionActual, SituacionDao.SIN_INCREMENTO_VALOR);
			situacionDocumento = SituacionDao.SIN_INCREMENTO_VALOR;
		}

		// EXENCIÓN
		double importeExencion = plusvaliaconfigdao.findFirst().getImpExencion();
		
		if (totalLiquidacion <= importeExencion && numAnyosALiquidar > 0){
			totalLiquidacion = 0;
			
			situacion = situaciondao.findByYearTexto(anyoLiquidacionActual, SituacionDao.EXENTAS);
			situacionDocumento = SituacionDao.EXENTAS;
		}
		
		// PRESCRIPCION
		int aniosPrescripcion = plusvaliaconfigdao.findFirst().getAniosPrescripcion();
		
		if(DateUtils.getNumAnyos(fechaActual, fechaTransActual) > aniosPrescripcion){ 
			recargo = 0;
			numDiasInteres = 0;
			porcentajeInteres = 0;
			interes = 0;
			totalLiquidacion = 0;
			
			situacion = situaciondao.findByYearTexto(anyoLiquidacionActual, SituacionDao.PRESCRITAS);
			situacionDocumento = SituacionDao.PRESCRITAS;
		}
		
		totalLiquidacion = Math.rint(totalLiquidacion * 100)/100;

		ResultadoUnitario resultado = new ResultadoUnitario(numAnyosALiquidar, numDias, recargo, porcentajeRecargo, valorUnitario,
				valorUnitarioReducidoTerreno, porcentajeDeduc, valorUnitarioTerrenoFinal, porcentajePlus, baseImponible, tipoImpo,
				cuotaLiquida, bonificacion, totalLiquidacion, numAnyosALiquidar, porcentajeBonif, situacion, situacionDocumento,
				derecho.getTexto(), numDiasInteres, porcentajeInteres, interes, fechaActual.getTime());
		
		logger.debug("Resultado Unitario " + totalLiquidacion);
		
		return resultado;
	}
	
	public boolean isDerechosCompatible(Transmitente transmitente, Adquiriente adquiriente){
		boolean ok = false;
		
		if (transmitente.getNumDerecho() == adquiriente.getNumDerecho() || adquiriente.getNumDerecho() == 1){
			ok = true;
		}
		
		return ok;
	}
	
	public void registrar(Plusvalia[] plusvalias)
	{
		for (int i = 0; i < plusvalias.length; i++)
		{
			registrar(plusvalias[i]);
		}
	}

	// Guardamos Plusvalía en BBDD
	public void registrar(Plusvalia plusvalia)
	{
		PlusvaliaTempDTO plusvaliaDTO = new PlusvaliaTempDTO();

		plusvaliaDTO.setRefcatastral(plusvalia.getInmueble().getRefCatastral().trim());
		plusvaliaDTO.setNombrevia(plusvalia.getInmueble().getNombrevia());
		plusvaliaDTO.setNumerovia(plusvalia.getInmueble().getNumero());
		plusvaliaDTO.setSupsolar(new Double(plusvalia.getInmueble().getSupsolar()));

		plusvaliaDTO.setNumNotario(new Integer(plusvalia.getNumNotario()));
		plusvaliaDTO.setNumProtocolo(new Integer(plusvalia.getNumProtocolo()));

		plusvaliaDTO.setNifTrans(plusvalia.getNifTrans());
		plusvaliaDTO.setNifTransAyto(plusvalia.getNifTransAyto());
		plusvaliaDTO.setNombreTrans(plusvalia.getNombreTrans());
		plusvaliaDTO.setEdadTransmitiente(new Integer(plusvalia.getEdadTransmitiente()));
		plusvaliaDTO.setPorcTransmitido(new Double(plusvalia.getPorcTransmitido()));
		plusvaliaDTO.setPorcBonificacion(new Double(plusvalia.getPorcBonificacion()));

		plusvaliaDTO.setNifAdq(plusvalia.getNifAdq());
		plusvaliaDTO.setNombreAdqui(plusvalia.getNombreAdqui());
		plusvaliaDTO.setPorcAdquirido(new Double(plusvalia.getPorcAdquirido()));
		plusvaliaDTO.setClaseDerecho(new Integer(plusvalia.getClaseDerecho()));

		plusvaliaDTO.setTotal(new Double(plusvalia.getResultado().getTotalLiquidacion()));
		
		if (plusvalia.getPagada())
		{
			plusvaliaDTO.setPagada("S");
		}
		else
		{
			plusvaliaDTO.setPagada("N");
		}
		
		plusvaliaDTO.setFechatanterior(plusvalia.getFechatanterior());
		plusvaliaDTO.setFechatactual(plusvalia.getFechatactual());

		ResultadoUnitario resultadoUnitario = plusvalia.getResultado();
		
		plusvaliaDTO.setFechaactual(resultadoUnitario.getFechaActual());
		plusvaliaDTO.setAnyostranscurridos(resultadoUnitario.getNumAnnos());

		plusvaliaDTO.setValorUnitarioTerreno(new Double(resultadoUnitario.getValorTerreno()));
		plusvaliaDTO.setValorTerreno(new Double(resultadoUnitario.getValorUnitario()));
		plusvaliaDTO.setValorFinalTerreno(new Double(resultadoUnitario.getValorTerrenoFin()));
		plusvaliaDTO.setPorcDeduccion(new Double(resultadoUnitario.getPorcentajeDeduccion()));
		plusvaliaDTO.setTipoImpositivo(new Double(resultadoUnitario.getTipoImpositivo()));
		
		plusvaliaDTO.setBonificacion(resultadoUnitario.getCuotaBonificada());
		
		plusvaliaDTO.setPorcRecargo(resultadoUnitario.getPorcentajeRecargo());
		plusvaliaDTO.setRecargo(new Double(resultadoUnitario.getRecargo()));
		
		plusvaliaDTO.setDiasInteres(resultadoUnitario.getDiasinteres());
		plusvaliaDTO.setPorcInteres(resultadoUnitario.getPorcInteres());
		plusvaliaDTO.setInteres(resultadoUnitario.getInteres());

		plusvaliaDTO.setFolderIdRegistro(plusvalia.getFolderIdRegistro());
		plusvaliaDTO.setNumExpediente(plusvalia.getNumExpediente());
		plusvaliaDTO.setReferenciaPago(plusvalia.getReferenciaPago());
		plusvaliaDTO.setSituacion(resultadoUnitario.getSituacion());

		plusvaliaDTO.setBaseImponible(new Double(resultadoUnitario.getBaseImponible()));
		plusvaliaDTO.setCuotaLiquida(new Double(resultadoUnitario.getCuotaLiquida()));
		plusvaliaDTO.setPorcAnual(new Double(resultadoUnitario.getPorcentajePlusvalia()));

		try
		{			
			plusvaliatempdao.persist(plusvaliaDTO);
		}
		catch (Exception e)
		{
			throw new PlusvaliaException(ErrorCode.PLUSVALIA_SAVE_ERROR);
		}
	}
	
	/**
	 * Comprobamos que no exista ninguna plusvalia ya realizada
	 * 
	 * @param refCatastral
	 * @param transmitentes
	 * @param adquirientes
	 * @param tipoAncert
	 * @throws Exception
	 */
	public void comprobar(String refCatastral, Transmitente[] transmitentes, Adquiriente[] adquirientes, String code) throws Exception{
		if (!isActoJuridicoValido(code)){
			throw new PlusvaliaException(ErrorCode.NOT_PLUSVALIA);
		}
		String tipoAncert = getTipoAncert(code);
		
		for (int i = 0; i < transmitentes.length; i++){
			for (int j = 0; j < adquirientes.length; j++){
				comprobar(refCatastral, transmitentes[i].getNif(), adquirientes[j].getNif(), tipoAncert, adquirientes[j].getNumDerecho());
			}
		}
	}

	/**
	 * Comprobamos que no exista la plusvalía en la BBDD
	 * 
	 * @param refCatastral
	 * @param nifTrans
	 * @param nifAdq
	 */
	public void comprobar(String refCatastral, String nifTrans, String nifAdq, String tipoAncert, int claseDerecho) throws Exception	{
		PlusvaliaTempDTO plusvaliaTempDto = getPlusvalia(refCatastral, nifTrans, nifAdq, tipoAncert, claseDerecho);
		
		if (plusvaliaTempDto != null)	{
			throw new PlusvaliaException(ErrorCode.PLUSVALIA_EXISTS);
		}
	}
	
	private PlusvaliaTempDTO getPlusvalia(String refCatastral, String nifTrans, String nifAdq, String tipoAncert, int claseDerecho)	{
		String nifTransmitiente = nifTrans;		
		
		// Si es por herencia, la fecha de transmisión actual es la fecha de defunción
		if (tipoAncert.equalsIgnoreCase("H"))	{
			//nifTransmitiente = "HERENCIA";
		}
		
		return plusvaliatempdao.find(refCatastral, nifTransmitiente, nifAdq, claseDerecho);
	}
	
	/**
	 * Comprueba si el código del acto juridico existe en la tabla de códigos
	 * 
	 * @param codigo
	 * @return
	 */
	public boolean isActoJuridicoValido(String codigo) throws Exception {					
		ActjuridancertDTO acto = actjuridancertdao.findByCode(codigo);
		
		if (acto == null){
			return false;
		}else{
			return true;
		}		
	}
	
	public String getTipoAncert(String actoJuridico) throws Exception {
		return actjuridancertdao.findByCode(actoJuridico).getTipo();
	}
	
	public Inmueble getImueble(String refCatastral,int anyoPlusvaliaLiquidacionActual) throws Exception {
		Map propiedades=getPlusvaliaProperties(anyoPlusvaliaLiquidacionActual);
		return inmuebledao.findByRefCatastral(refCatastral, propiedades);
	}
	
	private Map getPlusvaliaProperties(int anyoLiquidacionActual) throws Exception {
		Map map = new HashMap();
		CataDTO cataDto = catadao.findByYear(new Integer(anyoLiquidacionActual));
		map.put("tabla", cataDto.getTabla().trim());
		map.put("operador", cataDto.getOperador());
		map.put("coeficiente", cataDto.getCoeficiente());
		
		return map;
	}

	private int getYearFrom(Date date)
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}
	
	public void validarDerecho(Adquiriente[] adquirientes)
	{
		for (int i = 0; i < adquirientes.length; i++)
		{
			// En caso de que el adquiriente tenga derecho número 6
			if (adquirientes[i].getNumDerecho() == 6)
			{
				throw new PlusvaliaException(ErrorCode.NOT_VALID_DERECHO);
			}
		}		
	}

	public void setDerechodao(DerechoDao derechodao)
	{
		this.derechodao = derechodao;
	}

	public void setCatadao(CataDao catadao)
	{
		this.catadao = catadao;
	}

	public void setPorcentajedao(PorcentajeDao porcentajedao)
	{
		this.porcentajedao = porcentajedao;
	}

	public void setReducciondao(ReduccionDao reducciondao)
	{
		this.reducciondao = reducciondao;
	}

	public void setTipoimpositivodao(TipoimpositivoDao tipoimpositivodao)
	{
		this.tipoimpositivodao = tipoimpositivodao;
	}

	public void setInmuebledao(InmuebleDao inmuebledao)
	{
		this.inmuebledao = inmuebledao;
	}

	public void setDeducciondao(DeduccionDao deducciondao)
	{
		this.deducciondao = deducciondao;
	}
	
	public void setActjuridancertdao(ActjuridancertDao actjuridancertdao)
	{
		this.actjuridancertdao = actjuridancertdao;
	}
	
	public void setIntedemoradao(IntedemoraDao intedemoradao)
	{
		this.intedemoradao = intedemoradao;
	}

	public void setRecargodao(RecargoDao recargodao)
	{
		this.recargodao = recargodao;
	}
	
	public void setSituaciondao(SituacionDao situaciondao)
	{
		this.situaciondao = situaciondao;
	}

	public void setPlusvaliatempdao(PlusvaliaTempDao plusvaliatempdao)
	{
		this.plusvaliatempdao = plusvaliatempdao;
	}
	
	public void setPlusvaliaconfigdao(PlusvalConfigDao plusvaliaconfigdao)
	{
		this.plusvaliaconfigdao = plusvaliaconfigdao;
	}

}
