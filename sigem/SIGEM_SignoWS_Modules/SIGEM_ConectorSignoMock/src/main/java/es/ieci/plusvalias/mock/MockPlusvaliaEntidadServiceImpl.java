package es.ieci.plusvalias.mock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosBancarios;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.util.DateUtils;

public class MockPlusvaliaEntidadServiceImpl implements IPlusvaliaEntidadService{

	public void almacenarPlusvalia(Plusvalia arg0) {

	}

	public ResultadoUnitario calcular(String actoJuridico, Inmueble inmueble,
			Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion)
			throws Exception {
		int anyoLiquidacionActual = DateUtils.getYearFrom(datosLiquidacion.getFechaTransActual());
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

		// Porcentaje Adquirido
		double valorUnitario = valorSuelo * (adquiriente.getCuotaParticipacion() / 100);

		// Porcentaje Transmitido
		valorUnitario = valorUnitario * (transmitente.getPorcentajeTransmitido() / 100);

		// VALOR UNITARIO TERRENO // valor reducido
		double porcentajeReduccion = 30;
		double valorUnitarioReducidoTerreno = valorUnitario * porcentajeReduccion/100;

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
			porcentajeDeduc = 10+(18-edadSujeto);

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

			porcentajeDeduc = 10+(18-edadSujeto);

			valorUnitarioTerrenoFinal = valorUnitarioReducidoTerreno - ((valorUnitarioReducidoTerreno * porcentajeDeduc / 100));
		}

		// BASE IMPONIBLE
		// int numAnyos = fechaTransActual.get(Calendar.YEAR) -
		// fechaTransAnterior.get(Calendar.YEAR);

		double porcentajePlus = 4-((numAnyosALiquidar/5.0)*0.5);
		int numAnyosBaseImpo = 0;

		int maxAniosLiquidar = 20;

		if (numAnyosALiquidar > maxAniosLiquidar) {
			numAnyosBaseImpo = maxAniosLiquidar;
		}else{
			numAnyosBaseImpo = numAnyosALiquidar;
		}

		double baseImponible = valorUnitarioTerrenoFinal * porcentajePlus / 100 * numAnyosBaseImpo;

		// CUOTA LIQUIDA
		double tipoImpo = 30.0;
		double cuotaLiquida = baseImponible * tipoImpo / 100;

		// CUOTA Y BONIFICACION O EXENCION
		double porcentajeBonif = transmitente.getBonificacion();
		double bonificacion = (cuotaLiquida * porcentajeBonif) / 100;
		double cuotaBonificada = cuotaLiquida - bonificacion;

		// RECARGOS

		double recargo = 0;
		double interes = 0;

		// TOTAL LIQUIDACION
		double totalLiquidacion = cuotaBonificada + recargo + interes;

		String situacion = anyoLiquidacionActual+" EN TRAMITE";
		String situacionDocumento = "";

		//public static final String COBRADA = "COBRADA";

		// SIN INCREMENTO DE VALOR
		if (numAnyosALiquidar == 0){
			situacion = anyoLiquidacionActual+" SIN_INCREMENTO_VALOR";
			situacionDocumento = "SIN_INCREMENTO_VALOR";
		}

		// EXENCIÓN
		double importeExencion = 50.0;

		if (totalLiquidacion <= importeExencion && numAnyosALiquidar > 0){
			totalLiquidacion = 0;

			situacion = anyoLiquidacionActual+" EXENTAS";
			situacionDocumento = "EXENTAS";
		}

		// PRESCRIPCION
		int aniosPrescripcion = 50;

		if(DateUtils.getNumAnyos(fechaActual, fechaTransActual) > aniosPrescripcion){
			recargo = 0;
			interes = 0;
			totalLiquidacion = 0;

			situacion = anyoLiquidacionActual+" PRESCRITAS";
			situacionDocumento = "PRESCRITAS";
		}

		totalLiquidacion = Math.rint(totalLiquidacion * 100)/100;

		ResultadoUnitario resultado = new ResultadoUnitario(numAnyosALiquidar, 30, recargo, 0.0, valorUnitario,
				valorUnitarioReducidoTerreno, porcentajeDeduc, valorUnitarioTerrenoFinal, porcentajePlus, baseImponible, tipoImpo,
				cuotaLiquida, bonificacion, totalLiquidacion, numAnyosALiquidar, porcentajeBonif, situacion, situacionDocumento,
				"Herencia", 0, 0.0, interes, fechaActual.getTime());

		//logger.debug("Resultado Unitario " + totalLiquidacion);
		return resultado;
	}

	public void completarPlusvalia(Plusvalia arg0, String arg1, Inmueble arg2,
			Adquiriente arg3, Transmitente arg4, DatosLiquidacion arg5)
			throws Exception {

	}

	public void comprobar(String arg0, Transmitente[] arg1, Adquiriente[] arg2,
			String arg3, boolean arg4) throws Exception {

	}

	public Inmueble getInmueble(String arg0, DatosLiquidacion arg1)
			throws Exception {
		Inmueble inmueble=new Inmueble();
		inmueble.setCoepro(7.92);
		inmueble.setEscalera("1");
		inmueble.setNombrevia("NUEVA");
		inmueble.setNumero("1");
		inmueble.setNumvia("");
		inmueble.setPlanta("02");
		inmueble.setPuerta("E");
		inmueble.setRefCatastral(arg0);
		inmueble.setSupconstruida(1290);
		inmueble.setSupsolar(352);
		inmueble.setTipovia("CL");
		inmueble.setValcatastral(74068.07);
		inmueble.setValconstruido(27606.27);
		inmueble.setValsuelo(46461.80);
		return inmueble;
	}

	private Adquiriente getAdquiriente(String nifAdq){
		Adquiriente adquiriente=new Adquiriente();
		adquiriente.setApellido1("García");
		adquiriente.setApellido2("Martínez");
		adquiriente.setCalle("CL General Elorza");
		adquiriente.setCp("32000");
		adquiriente.setCuotaParticipacion(50.0);
		adquiriente.setEdad(35);
		adquiriente.setIdentificador("1");
		adquiriente.setNif(nifAdq);
		adquiriente.setNombre("Sandra");
		adquiriente.setNombreCompleto("Sandra García Martínez");
		adquiriente.setNumDerecho(1);
		adquiriente.setPoblacion("Oviedo");
		return adquiriente;
	}

	private Transmitente getTransmitente(String nifTrans){
		Transmitente transmitente=new Transmitente();
		transmitente.setApellido1("Rodríguez");
		transmitente.setApellido2("González");
		transmitente.setCalle("CL Coronel Aranda");
		transmitente.setCp("32000");
		transmitente.setEdad(89);
		transmitente.setIdentificador("1");
		transmitente.setNif(nifTrans);
		transmitente.setNombre("Daniel");
		transmitente.setNombreCompleto("Daniel Rodríguez González");
		transmitente.setNumDerecho(1);
		transmitente.setPoblacion("Oviedo");
		transmitente.setAnyosUsufruto(0);
		transmitente.setFechaDefuncion(null);
		try{
			transmitente.setFechaTransAnterior(new SimpleDateFormat("dd/MM/yyyy").parse("03/11/1993"));
		}catch(Exception e){
			transmitente.setFechaTransAnterior(null);
		}
		transmitente.setPorcentajeTransmitido(100);
		transmitente.setBonificacion(0);
		return transmitente;
	}

	public Plusvalia getPlusvalia(String refCatastral, String nifTrans, String nifAdq,
			int claseDerecho) {
//		Plusvalia plusvalia=new Plusvalia();
//
//
//		Adquiriente adquiriente=getAdquiriente(nifAdq);
//		plusvalia.setAdquiriente(adquiriente);
//
//		Transmitente transmitente=getTransmitente(nifTrans);
//		plusvalia.setTransmitente(transmitente);
//
//		plusvalia.setClaseDerecho(1);
//		plusvalia.setDiasPlazo(30);
//		plusvalia.setEdadTransmitiente(transmitente.getEdad());
//		plusvalia.setFechatactual(new Date());
//		plusvalia.setFechatanterior(transmitente.getFechaTransAnterior());
//		plusvalia.setFolderIdRegistro("");
//
//		DatosLiquidacion datosLiquidacion=new DatosLiquidacion();
//		datosLiquidacion.setFechaTransActual(new Date());
//		Inmueble inmueble=null;
//		try{
//			inmueble=getInmueble(refCatastral,datosLiquidacion);
//		}catch(Exception e){
//			return null;
//		}
//		plusvalia.setInmueble(inmueble);
//
//		plusvalia.setNifAdq(nifAdq);
//		plusvalia.setNifTrans(nifTrans);
//		plusvalia.setNifTransAyto("");
//		plusvalia.setNombreAdqui(adquiriente.getNombreCompleto());
//		plusvalia.setNombreTrans(transmitente.getNombreCompleto());
//		plusvalia.setNotario("Fernando Fernández");
//		plusvalia.setNumExpediente("EXP2001200000001234");
//		plusvalia.setNumNotario(3702391);
//		plusvalia.setNumProtocolo(0);
//		plusvalia.setPagada(false);
//		plusvalia.setPorcAdquirido(100);
//		plusvalia.setPorcBonificacion(0);
//		plusvalia.setPorcTransmitido(100);
//		plusvalia.setReferenciaPago(null);
//
//		ResultadoUnitario resultado=null;
//		try{
//			resultado=calcular("",inmueble,adquiriente,transmitente, datosLiquidacion);
//		}catch(Exception e){
//			return null;
//		}
//		plusvalia.setResultado(resultado);
//		plusvalia.setSujetoPasivo(adquiriente);
//		return plusvalia;
		return null;

	}

	public boolean isDerechosCompatible(Transmitente arg0, Adquiriente arg1) {
		return true;
	}

	public boolean isPlusvaliaDonacion(String arg0) throws Exception {
		return false;
	}

	public boolean isPlusvaliaHerencia(String arg0) throws Exception {
		return false;
	}

	public void postpagoProcess(Plusvalia arg0, DatosBancarios arg1,
			Liquidacion arg2) throws Exception {

	}

	public void prepagoProcess(Plusvalia arg0, DatosBancarios arg1,
			Liquidacion arg2) throws Exception {

	}
}
