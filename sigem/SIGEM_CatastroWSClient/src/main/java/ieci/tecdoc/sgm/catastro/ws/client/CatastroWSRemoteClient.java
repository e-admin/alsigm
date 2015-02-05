package ieci.tecdoc.sgm.catastro.ws.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.catastro.CatastroServicioException;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class CatastroWSRemoteClient implements ServicioCatastro {

	private CatastroWebService service;

	public CatastroWebService getService() {
		return service;
	}

	public void setService(CatastroWebService service) {
		this.service = service;
	}
	
	public boolean validarReferenciaCatastral(String referenciaCatastral) throws CatastroServicioException {
		try{
			RetornoLogico valido =  getService().validarReferenciaCatastral(getRetornoCadenaWS(referenciaCatastral));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)valido)){
				return getRetornoLogicoServicio(valido);
			}else{
				throw getCatastroException((IRetornoServicio)valido);
			}
		}catch (RemoteException e) {
			throw new CatastroServicioException(CatastroServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.catastro.Parcelas consultarCatastro(String referenciaCatastral) throws CatastroServicioException {
		try{
			Parcelas parcelas =  getService().consultarCatastro(getRetornoCadenaWS(referenciaCatastral));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)parcelas)){
				return getParcelasServicio(parcelas);
			}else{
				throw getCatastroException((IRetornoServicio)parcelas);
			}
		}catch (RemoteException e) {
			throw new CatastroServicioException(CatastroServicioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	private CatastroServicioException getCatastroException(IRetornoServicio oReturn){
		return new CatastroServicioException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private RetornoCadena getRetornoCadenaWS(String cadena) {
		RetornoCadena oRetorno = new RetornoCadena();
		oRetorno.setCadena(cadena);
		return oRetorno;
	}

	private boolean getRetornoLogicoServicio(RetornoLogico oLogico) {
		return oLogico.isValor();
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Parcelas getParcelasServicio(Parcelas oParcelas){
		if (oParcelas == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Parcelas poParcelas = new ieci.tecdoc.sgm.core.services.catastro.Parcelas();
		List lParcelas = new ArrayList();
		for (int i=0; i<oParcelas.getParcelas().length; i++) 
			lParcelas.add(getParcelaServicio(oParcelas.getParcelas()[i]));
		poParcelas.setParcelas(lParcelas);
		return poParcelas;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Parcela getParcelaServicio(Parcela oParcela){
		if (oParcela == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Parcela poParcela = new ieci.tecdoc.sgm.core.services.catastro.Parcela();
		
		poParcela.setDireccion(getLocalizacionServicio(oParcela.getDireccion()));
		poParcela.setLstBienesInmuebles(getBienesInmueblesServicio(oParcela.getLstBienesInmuebles()));
		poParcela.setRefCatastral(oParcela.getRefCatastral());
		poParcela.setSuperficie(oParcela.getSuperficie());
		poParcela.setSuperficieConstruida(oParcela.getSuperficieConstruida());
		
		return poParcela;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Localizacion getLocalizacionServicio(Localizacion oLocalizacion){
		if (oLocalizacion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Localizacion poLocalizacion = new ieci.tecdoc.sgm.core.services.catastro.Localizacion();
		
		poLocalizacion.setBloque(oLocalizacion.getBloque());
		poLocalizacion.setCodigoPostal(oLocalizacion.getCodigoPostal());
		poLocalizacion.setKilometro(oLocalizacion.getKilometro());
		poLocalizacion.setNombreMunicipio(oLocalizacion.getNombreMunicipio());
		poLocalizacion.setNombreProvincia(oLocalizacion.getNombreProvincia());
		poLocalizacion.setNombreVia(oLocalizacion.getNombreVia());
		poLocalizacion.setPrimeraLetra(oLocalizacion.getPrimeraLetra());
		poLocalizacion.setPrimerNumero(oLocalizacion.getPrimerNumero());
		poLocalizacion.setSegundaLetra(oLocalizacion.getSegundaLetra());
		poLocalizacion.setSegundoNumero(oLocalizacion.getSegundoNumero());
		
		return poLocalizacion;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.BienesInmuebles getBienesInmueblesServicio(BienesInmuebles oBienesInmuebles){
		if (oBienesInmuebles == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.BienesInmuebles poBienesInmuebles = new ieci.tecdoc.sgm.core.services.catastro.BienesInmuebles();
		List lBienesInmuebles = new ArrayList();
		for (int i=0; i<oBienesInmuebles.getBienesInmuebles().length; i++)
			lBienesInmuebles.add(getBienInmuebleServicio(oBienesInmuebles.getBienesInmuebles()[i]));
		poBienesInmuebles.setBienesInmuebles(lBienesInmuebles);
		return poBienesInmuebles;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.BienInmueble getBienInmuebleServicio(BienInmueble oBienInmueble){
		if (oBienInmueble == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.BienInmueble poBienInmueble = new ieci.tecdoc.sgm.core.services.catastro.BienInmueble();
		
		poBienInmueble.setClaseBienInmueble(oBienInmueble.getClaseBienInmueble());
		poBienInmueble.setClaseUso(oBienInmueble.getClaseUso());
		poBienInmueble.setDireccionLocalizacion(getLocalizacionServicio(oBienInmueble.getDireccionLocalizacion()));
		poBienInmueble.setLstConstrucciones(getConstruccionesServicio(oBienInmueble.getLstConstrucciones()));
		poBienInmueble.setLstCultivos(getCultivosServicio(oBienInmueble.getLstCultivos()));
		poBienInmueble.setReferencia_catastral(oBienInmueble.getReferencia_catastral());
		poBienInmueble.setSuperficie(oBienInmueble.getSuperficie());
				
		return poBienInmueble;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Construcciones getConstruccionesServicio(Construcciones oConstrucciones){
		if (oConstrucciones == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Construcciones poConstrucciones = new ieci.tecdoc.sgm.core.services.catastro.Construcciones();
		List lConstrucciones = new ArrayList();
		for (int i=0; i<oConstrucciones.getConstrucciones().length; i++)
			lConstrucciones.add(getConstruccionServicio(oConstrucciones.getConstrucciones()[i]));
		poConstrucciones.setConstrucciones(lConstrucciones);
		return poConstrucciones;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Construccion getConstruccionServicio(Construccion oConstruccion){
		if (oConstruccion == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Construccion poConstruccion = new ieci.tecdoc.sgm.core.services.catastro.Construccion();
		
		poConstruccion.setCodigoUso(oConstruccion.getCodigoUso());
		poConstruccion.setEscalera(oConstruccion.getEscalera());
		poConstruccion.setIdentificador(oConstruccion.getIdentificador());
		poConstruccion.setPlanta(oConstruccion.getPlanta());
		poConstruccion.setPuerta(oConstruccion.getPuerta());
		poConstruccion.setSuperficieTotal(oConstruccion.getSuperficieTotal());
				
		return poConstruccion;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Cultivos getCultivosServicio(Cultivos oCultivos){
		if (oCultivos == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Cultivos poCultivos = new ieci.tecdoc.sgm.core.services.catastro.Cultivos();
		List lCultivos = new ArrayList();
		for (int i=0; i<oCultivos.getCultivos().length; i++)
			lCultivos.add(getCultivoServicio(oCultivos.getCultivos()[i]));
		poCultivos.setCultivos(lCultivos);
		return poCultivos;
	}
	
	private ieci.tecdoc.sgm.core.services.catastro.Cultivo getCultivoServicio(Cultivo oCultivo){
		if (oCultivo == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.catastro.Cultivo poCultivo = new ieci.tecdoc.sgm.core.services.catastro.Cultivo();
		
		poCultivo.setCalificacion(oCultivo.getCalificacion());
		poCultivo.setDenominacion(oCultivo.getDenominacion());
		poCultivo.setIdentificador(oCultivo.getIdentificador());
		poCultivo.setIntensidadProductiva(oCultivo.getIntensidadProductiva());
		poCultivo.setSuperficie(oCultivo.getSuperficie());
				
		return poCultivo;
	}
}
