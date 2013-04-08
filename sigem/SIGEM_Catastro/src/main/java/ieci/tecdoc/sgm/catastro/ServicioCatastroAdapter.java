package ieci.tecdoc.sgm.catastro;


import ieci.tecdoc.sgm.catastro.exception.CatastroExcepcion;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.catastro.BienInmueble;
import ieci.tecdoc.sgm.core.services.catastro.BienesInmuebles;
import ieci.tecdoc.sgm.core.services.catastro.CatastroServicioException;
import ieci.tecdoc.sgm.core.services.catastro.Construccion;
import ieci.tecdoc.sgm.core.services.catastro.Construcciones;
import ieci.tecdoc.sgm.core.services.catastro.Cultivo;
import ieci.tecdoc.sgm.core.services.catastro.Cultivos;
import ieci.tecdoc.sgm.core.services.catastro.Localizacion;
import ieci.tecdoc.sgm.core.services.catastro.Parcela;
import ieci.tecdoc.sgm.core.services.catastro.Parcelas;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.localgis.model.ot.ArrayOfBienInmuebleOT;
import com.localgis.model.ot.ArrayOfConstruccionOT;
import com.localgis.model.ot.ArrayOfCultivoOT;
import com.localgis.model.ot.BienInmuebleOT;
import com.localgis.model.ot.ConstruccionOT;
import com.localgis.model.ot.CultivoOT;
import com.localgis.model.ot.LocalizacionOT;
import com.localgis.model.ot.ParcelaOT;

public class ServicioCatastroAdapter implements ServicioCatastro {

	private static final Logger logger = Logger.getLogger(ServicioCatastroAdapter.class);
	
	/**
	 * Método que comprueba si una referencia catastral está dada de alta
	 * @param referenciaCatastral Valor de la referencia catastral
	 * @return Verdadero si está dada de alta la referencia castastral, Falso si no lo está
	 * @throws CatastroServicioException En caso de producirse algún error
	 */
	public  boolean validarReferenciaCatastral(String referenciaCatastral) throws CatastroServicioException {
		try {
			return CatastroManager.validarReferenciaCatastral(referenciaCatastral);
		} catch (CatastroExcepcion e) {
			logger.error("Error al validar la referencia catastral.", e);
			throw getCatastroServicioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al validar la referencia catastral.", e);
			throw new CatastroServicioException(CatastroServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que obtiene la información asociada a una refrencia catastral
	 * @param referenciaCatastral Valor de la referencia catastral
	 * @return Datos de la referencia catastral
	 * @throws CatastroServicioException En caso de producirse algún error
	 */
	public  Parcelas consultarCatastro(String referenciaCatastral) throws CatastroServicioException {
		try {
			return getParcelasServicio(CatastroManager.consultarCatastro(referenciaCatastral));
		} catch (CatastroExcepcion e) {
			logger.error("Error al validar la referencia catastral.", e);
			throw getCatastroServicioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al validar la referencia catastral.", e);
			throw new CatastroServicioException(CatastroServicioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	
	private CatastroServicioException getCatastroServicioException(CatastroExcepcion poException){
		if(poException == null){
			return new CatastroServicioException(CatastroServicioException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_CATASTRO_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new CatastroServicioException(Long.valueOf(cCodigo.toString()).longValue(), poException);
		
	}
	
	private Parcelas getParcelasServicio(ParcelaOT[] oParcelas){
		if (oParcelas == null || oParcelas.length == 0)
			return null;
		
		Parcelas poParcelas = new Parcelas();
		for (int i=0; i<oParcelas.length; i++) {
			poParcelas.add(getParcelaServicio(oParcelas[i]));
		}
		return poParcelas;
	}
	
	private Parcela getParcelaServicio(ParcelaOT oParcela){
		if (oParcela == null)
			return null;
		
		Parcela poParcela = new Parcela();
		
		poParcela.setDireccion(getLocalizacionServicio(oParcela.getDireccion()));
		poParcela.setLstBienesInmuebles(getBienesInmueblesServicio(oParcela.getLstBienesInmuebles()));
		poParcela.setRefCatastral(oParcela.getRefCatastral());
		poParcela.setSuperficie(oParcela.getSuperficie());
		poParcela.setSuperficieConstruida(oParcela.getSuperficieConstruida());
		
		return poParcela;
	}
	
	private Localizacion getLocalizacionServicio(LocalizacionOT oLocalizacion){
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
	
	private BienesInmuebles getBienesInmueblesServicio(ArrayOfBienInmuebleOT oBienesInmuebles){
		if (oBienesInmuebles == null || ArrayUtils.isEmpty(oBienesInmuebles.getBienInmuebleOT())) {
			return null;
		}
		
		BienesInmuebles poBienesInmuebles = new BienesInmuebles();
		for (BienInmuebleOT bien : oBienesInmuebles.getBienInmuebleOT()) {
			poBienesInmuebles.add(getBienInmuebleServicio(bien));
		}
		return poBienesInmuebles;
	}
	
	private BienInmueble getBienInmuebleServicio(BienInmuebleOT oBienInmueble){
		if (oBienInmueble == null)
			return null;
		
		BienInmueble poBienInmueble = new BienInmueble();
		
		poBienInmueble.setClaseBienInmueble(oBienInmueble.getClaseBienInmueble());
		poBienInmueble.setClaseUso(oBienInmueble.getClaseUso());
		poBienInmueble.setDireccionLocalizacion(getLocalizacionServicio(oBienInmueble.getDireccionLocalizacion()));
		poBienInmueble.setLstConstrucciones(getConstruccionesServicio(oBienInmueble.getLstConstrucciones()));
		poBienInmueble.setLstCultivos(getCultivosServicio(oBienInmueble.getLstCultivos()));
		poBienInmueble.setReferencia_catastral(oBienInmueble.getReferencia_catastral());
		poBienInmueble.setSuperficie(oBienInmueble.getSuperficie());
				
		return poBienInmueble;
	}
	
	private Construcciones getConstruccionesServicio(
			ArrayOfConstruccionOT oConstrucciones) {
		if (oConstrucciones == null
				|| ArrayUtils.isEmpty(oConstrucciones.getConstruccionOT())) {
			return null;
		}

		Construcciones poConstrucciones = new Construcciones();
		for (ConstruccionOT construccion : oConstrucciones.getConstruccionOT()) {
			poConstrucciones.add(getConstruccionServicio(construccion));
		}
		return poConstrucciones;
	}
	
	private Construccion getConstruccionServicio(ConstruccionOT oConstruccion){
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
	
	private Cultivos getCultivosServicio(ArrayOfCultivoOT oCultivos) {
		if (oCultivos == null || ArrayUtils.isEmpty(oCultivos.getCultivoOT())) {
			return null;
		}

		Cultivos poCultivos = new Cultivos();
		for (CultivoOT cultivo : oCultivos.getCultivoOT()) {
			poCultivos.add(getCultivoServicio(cultivo));
		}
		return poCultivos;
	}
	
	private Cultivo getCultivoServicio(CultivoOT oCultivo){
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
