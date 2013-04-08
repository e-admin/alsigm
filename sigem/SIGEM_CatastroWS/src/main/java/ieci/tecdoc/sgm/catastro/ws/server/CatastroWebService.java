package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.catastro.CatastroServicioException;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class CatastroWebService {

	private static final Logger logger = Logger.getLogger(CatastroWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_CATASTRO;

	private ServicioCatastro getServicioCatastro() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioCatastro();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioCatastro(sbImpl.toString());
		}
	}
	
	public RetornoLogico validarReferenciaCatastral(RetornoCadena referenciaCatastral){
		try {
			RetornoLogico valido = getRetornoLogicoWS(
					getServicioCatastro().validarReferenciaCatastral(getRetornoCadenaServicio(referenciaCatastral))
				);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(valido);
		} catch (CatastroServicioException e) {
			logger.error("Error al validar la referencia catastral.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al validar la referencia catastral.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al validar la referencia catastral.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));			
		}
	}
	
	public Parcelas consultarCatastro(RetornoCadena referenciaCatastral){
		try {
			Parcelas parcelas= getParcelasWS(
					getServicioCatastro().consultarCatastro(getRetornoCadenaServicio(referenciaCatastral))
				);
			return (Parcelas)ServiciosUtils.completeReturnOK(parcelas);
		} catch (CatastroServicioException e) {
			logger.error("Error al consultar el catastro.", e);
			return (Parcelas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Parcelas()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al consultar el catastro.", e);
			return (Parcelas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Parcelas()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al consultar el catastro.", e);
			return (Parcelas)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Parcelas()));			
		}
	}
	
	
	private String getRetornoCadenaServicio(RetornoCadena cadena) {
		if (cadena == null)
			return null;
		
		return  cadena.getCadena();
	}
	
	private RetornoLogico getRetornoLogicoWS(boolean valor) {
		RetornoLogico retorno = new  RetornoLogico();
		retorno.setValor(valor);
		return retorno;
	}
	
	private Parcelas getParcelasWS(ieci.tecdoc.sgm.core.services.catastro.Parcelas oParcelas){
		if (oParcelas == null || oParcelas.count() == 0)
			return null;
		
		Parcelas poParcelas = new Parcelas();
		Parcela[] poParcelasArray = new Parcela[oParcelas.count()];
		for (int i=0; i<oParcelas.count(); i++)
			poParcelasArray[i] = getParcelaWS((ieci.tecdoc.sgm.core.services.catastro.Parcela)oParcelas.get(i));
		poParcelas.setParcelas(poParcelasArray);
		return poParcelas;
	}
	
	private Parcela getParcelaWS(ieci.tecdoc.sgm.core.services.catastro.Parcela oParcela){
		if (oParcela == null)
			return null;
		
		Parcela poParcela = new Parcela();
		
		poParcela.setDireccion(getLocalizacionWS(oParcela.getDireccion()));
		poParcela.setLstBienesInmuebles(getBienesInmueblesWS(oParcela.getLstBienesInmuebles()));
		poParcela.setRefCatastral(oParcela.getRefCatastral());
		poParcela.setSuperficie(oParcela.getSuperficie());
		poParcela.setSuperficieConstruida(oParcela.getSuperficieConstruida());
		
		return poParcela;
	}
	
	private Localizacion getLocalizacionWS(ieci.tecdoc.sgm.core.services.catastro.Localizacion oLocalizacion){
		if (oLocalizacion == null)
			return null;
		
		Localizacion poLocalizacion = new Localizacion();
		
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
	
	private BienesInmuebles getBienesInmueblesWS(ieci.tecdoc.sgm.core.services.catastro.BienesInmuebles oBienesInmuebles){
		if (oBienesInmuebles == null || oBienesInmuebles.count() == 0)
			return null;
		
		BienesInmuebles poBienesInmuebles = new BienesInmuebles();
		BienInmueble[] poBienesInmueblesArray = new BienInmueble[oBienesInmuebles.count()];
		for (int i=0; i<oBienesInmuebles.count(); i++)
			poBienesInmueblesArray[i] = getBienInmuebleWS((ieci.tecdoc.sgm.core.services.catastro.BienInmueble)oBienesInmuebles.get(i));
		poBienesInmuebles.setBienesInmuebles(poBienesInmueblesArray);
		return poBienesInmuebles;
	}
	
	private BienInmueble getBienInmuebleWS(ieci.tecdoc.sgm.core.services.catastro.BienInmueble oBienInmueble){
		if (oBienInmueble == null)
			return null;
		
		BienInmueble poBienInmueble = new BienInmueble();
		
		poBienInmueble.setClaseBienInmueble(oBienInmueble.getClaseBienInmueble());
		poBienInmueble.setClaseUso(oBienInmueble.getClaseUso());
		poBienInmueble.setDireccionLocalizacion(getLocalizacionWS(oBienInmueble.getDireccionLocalizacion()));
		poBienInmueble.setLstConstrucciones(getConstruccionesWS(oBienInmueble.getLstConstrucciones()));
		poBienInmueble.setLstCultivos(getCultivosWS(oBienInmueble.getLstCultivos()));
		poBienInmueble.setReferencia_catastral(oBienInmueble.getReferencia_catastral());
		poBienInmueble.setSuperficie(oBienInmueble.getSuperficie());
				
		return poBienInmueble;
	}
	
	private Construcciones getConstruccionesWS(ieci.tecdoc.sgm.core.services.catastro.Construcciones oConstrucciones){
		if (oConstrucciones == null || oConstrucciones.count() == 0)
			return null;
		
		Construcciones poConstrucciones = new Construcciones();
		Construccion[] poConstruccionesArray = new Construccion[oConstrucciones.count()];
		for (int i=0; i<oConstrucciones.count(); i++)
			poConstruccionesArray[i] = getConstruccionWS((ieci.tecdoc.sgm.core.services.catastro.Construccion)oConstrucciones.get(i));
		poConstrucciones.setConstrucciones(poConstruccionesArray);
		return poConstrucciones;
	}
	
	private Construccion getConstruccionWS(ieci.tecdoc.sgm.core.services.catastro.Construccion oConstruccion){
		if (oConstruccion == null)
			return null;
		
		Construccion poConstruccion = new Construccion();
		
		poConstruccion.setCodigoUso(oConstruccion.getCodigoUso());
		poConstruccion.setEscalera(oConstruccion.getEscalera());
		poConstruccion.setIdentificador(oConstruccion.getIdentificador());
		poConstruccion.setPlanta(oConstruccion.getPlanta());
		poConstruccion.setPuerta(oConstruccion.getPuerta());
		poConstruccion.setSuperficieTotal(oConstruccion.getSuperficieTotal());
				
		return poConstruccion;
	}
	
	private Cultivos getCultivosWS(ieci.tecdoc.sgm.core.services.catastro.Cultivos oCultivos){
		if (oCultivos == null || oCultivos.count() == 0)
			return null;
		
		Cultivos poCultivos = new Cultivos();
		Cultivo[] poCultivosArray = new Cultivo[oCultivos.count()];
		for (int i=0; i<oCultivos.count(); i++)
			poCultivosArray[i] = getCultivoWS((ieci.tecdoc.sgm.core.services.catastro.Cultivo)oCultivos.get(i));
		poCultivos.setCultivos(poCultivosArray);
		return poCultivos;
	}
	
	private Cultivo getCultivoWS(ieci.tecdoc.sgm.core.services.catastro.Cultivo oCultivo){
		if (oCultivo == null)
			return null;
		
		Cultivo poCultivo = new Cultivo();
		
		poCultivo.setCalificacion(oCultivo.getCalificacion());
		poCultivo.setDenominacion(oCultivo.getDenominacion());
		poCultivo.setIdentificador(oCultivo.getIdentificador());
		poCultivo.setIntensidadProductiva(oCultivo.getIntensidadProductiva());
		poCultivo.setSuperficie(oCultivo.getSuperficie());
				
		return poCultivo;
	}
}
