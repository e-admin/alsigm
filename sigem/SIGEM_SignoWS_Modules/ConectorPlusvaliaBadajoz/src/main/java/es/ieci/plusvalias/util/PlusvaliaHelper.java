package es.ieci.plusvalias.util;

import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.domain.PlusvaliaTempDTO;

public class PlusvaliaHelper {

	public Plusvalia getPlusvalia(PlusvaliaTempDTO plusvalia){
		if(plusvalia==null) return null;
		Plusvalia p=new Plusvalia();
		
		ResultadoUnitario resultado=new ResultadoUnitario();
		resultado.setNumAnnos(plusvalia.getAnyostranscurridos());
		resultado.setBaseImponible(plusvalia.getBaseImponible());
		resultado.setCuotaBonificada(plusvalia.getBonificacion());
		resultado.setCuotaLiquida(plusvalia.getCuotaLiquida());
		resultado.setDiasinteres(plusvalia.getDiasInteres());
		resultado.setFechaActual(plusvalia.getFechaactual());
		resultado.setInteres(plusvalia.getInteres());
		resultado.setPorcentajePlusvalia(plusvalia.getPorcAnual());
		resultado.setPorcentajeDeduccion(plusvalia.getPorcDeduccion());
		resultado.setPorcInteres(plusvalia.getPorcInteres());
		resultado.setPorcentajeRecargo(plusvalia.getPorcRecargo());
		resultado.setRecargo(plusvalia.getRecargo());
		resultado.setSituacion(plusvalia.getSituacion());
		resultado.setTipoImpositivo(plusvalia.getTipoImpositivo());
		resultado.setTotalLiquidacion(plusvalia.getTotal());
		resultado.setValorTerrenoFin(plusvalia.getValorFinalTerreno());
		resultado.setValorTerreno(plusvalia.getValorTerreno());
		resultado.setValorUnitario(plusvalia.getValorUnitarioTerreno());
		p.setResultado(resultado);
		
		Inmueble inmueble=new Inmueble();
		inmueble.setNombrevia(plusvalia.getNombrevia());
		inmueble.setNumvia(plusvalia.getNumerovia());
		inmueble.setRefCatastral(plusvalia.getRefcatastral());
		inmueble.setSupsolar(plusvalia.getSupsolar());
		p.setInmueble(inmueble);
		
		p.setClaseDerecho(plusvalia.getClaseDerecho());
		p.setEdadTransmitiente(plusvalia.getEdadTransmitiente());
		p.setFechatactual(plusvalia.getFechatactual());
		p.setFechatanterior(plusvalia.getFechatanterior());
		p.setFolderIdRegistro(plusvalia.getFolderIdRegistro());
		p.setNifAdq(plusvalia.getNifAdq());
		p.setNifTrans(plusvalia.getNifTrans());
		p.setNifTransAyto(plusvalia.getNifTransAyto());
		p.setNombreAdqui(plusvalia.getNombreAdqui());
		p.setNombreTrans(plusvalia.getNombreTrans());
		p.setNumExpediente(plusvalia.getNumExpediente());
		p.setNumNotario(plusvalia.getNumNotario());
		p.setNumProtocolo(plusvalia.getNumProtocolo());
		p.setPagada(plusvalia.getPagada().toLowerCase().equals("s"));
		p.setPorcAdquirido(plusvalia.getPorcAdquirido());
		p.setPorcBonificacion(plusvalia.getPorcBonificacion());
		p.setPorcTransmitido(plusvalia.getPorcTransmitido());
		p.setReferenciaPago(plusvalia.getReferenciaPago());
		return p;
	}

}
