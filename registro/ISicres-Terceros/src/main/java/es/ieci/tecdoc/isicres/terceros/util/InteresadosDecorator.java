package es.ieci.tecdoc.isicres.terceros.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;
import es.ieci.tecdoc.isicres.terceros.business.manager.InteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 *
 * @author IECISA
 *
 */
public class InteresadosDecorator {

	/**
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @return
	 */
	public String getInteresados(String idLibro, String idRegistro) {
		List<InteresadoVO> interesados = ((InteresadoManager) getInteresadoManager())
				.getAll(idLibro, idRegistro);

		return interesados2string(interesados);
	}

	/**
	 *
	 * @param interesados
	 * @return
	 */
	public String interesados2string(List<InteresadoVO> interesados) {
		Iterator<InteresadoVO> iterator = interesados.iterator();
		StringBuffer sb = new StringBuffer();
		VelocityEngine engine = new VelocityEngine();
		Template template = engine
				.getTemplate("es/ieci/tecdoc/isicres/terceros/util/interesados.vm");
		while (iterator.hasNext()) {
			InteresadoVO interesado = (InteresadoVO) iterator.next();
			VelocityContext ctx = new VelocityContext();
			ctx.put("interesado", interesado);
			StringWriter sw = new StringWriter();
			engine.merge(template, ctx, sw);
			sb.append(sw.toString());

			if (iterator.hasNext()) {
				sb.append("&&");
			}
		}
		return sb.toString();
	}

	/**
	 *
	 * @param interesadosEncoded
	 * @return
	 */
	public List<InteresadoVO> string2interesados(String interesadosEncoded) {
		List<InteresadoVO> interesados = new ArrayList<InteresadoVO>();

		String[] interesadosSplitted = StringUtils.split(interesadosEncoded,
				"&&");
		for (String interesadoSplitted : interesadosSplitted) {
			String[] tokens = StringUtils
					.splitByWholeSeparatorPreserveAllTokens(interesadoSplitted,
							"#");
			InteresadoVO interesado = new InteresadoVO();
			interesado.setId(String.valueOf(interesados.size()));
			interesado.setOrden(interesados.size());
			BaseTerceroVO tercero = null;
			BaseDireccionVO direccionTercero = null;
			RepresentanteInteresadoVO representante = null;
			BaseDireccionVO direccionRepresentante = null;
			TerceroValidadoVO terceroValidadoVO = null;
			for (int i = 0; i < tokens.length; i++) {
				switch (i) {
				case 0:
					//obtenemos la informacion del representante de BBDD
					terceroValidadoVO = getTerceroManager().get(tokens[i]);

					if (null != terceroValidadoVO) {
						if (terceroValidadoVO instanceof TerceroValidadoFisicoVO) {
							tercero = new TerceroValidadoFisicoVO();
						} else {
							tercero = new TerceroValidadoJuridicoVO();
						}
						tercero.setId(tokens[i]);
					}
					break;
				case 1:
					if(tercero instanceof TerceroValidadoFisicoVO){
						tercero.setNombre(tokens[i]);
					}else{
						((TerceroValidadoJuridicoVO)tercero).setRazonSocial(tokens[i]);
					}

					interesado.setTercero(tercero);
					break;
				case 2:
					if (!StringUtils.isBlank(tokens[i])) {
						direccionTercero = new BaseDireccionVO();
						direccionTercero.setId(tokens[i]);
					}
					break;
				case 3:
					//Token con el literal de la Direccion del interesado
					break;
				case 4:
					if (null != direccionTercero) {
						// seteamos la direccion del interesado con los datos recuperados de BBDD
						interesado.setDireccionNotificacion(getDireccion(tokens[i],
								direccionTercero.getId(), terceroValidadoVO));
					}

					break;
				case 5:
					if (!StringUtils.isBlank(tokens[i])) {
						BaseTerceroVO terceroRepresentante = new BaseTerceroVO();
						terceroRepresentante.setId(tokens[i]);
						representante = new RepresentanteInteresadoVO();
						representante.setRepresentante(terceroRepresentante);
						interesado.setRepresentante(representante);
					}
					break;
				case 6:
					if (null != representante) {
						interesado.getRepresentante().getRepresentante()
								.setNombre(tokens[i]);
					} else {
						interesado
								.setRepresentante(new RepresentanteInteresadoVO());
					}
					break;
				case 7:
					if (!StringUtils.isBlank(tokens[i])) {
						direccionRepresentante = new BaseDireccionVO();
						direccionRepresentante.setId(tokens[i]);
					}
					break;
				case 8:
					//Token con el literal de la Direccion del representante
					break;
				case 9:
					if (null != direccionRepresentante) {
						//obtenemos la informacion del representante de BBDD
						TerceroValidadoVO representanteVO = getTerceroManager()
								.get(interesado.getRepresentante()
										.getRepresentante().getId());

						// seteamos la direccion del representante con los datos recuperados de BBDD
						interesado.getRepresentante().setDireccionNotificacion(
								getDireccion(tokens[i],
										direccionRepresentante.getId(),
										representanteVO));
					}

					break;
				}
			}
			interesados.add(interesado);
		}

		return interesados;
	}


	/**
	 * Obtenemos la direccion del interesado o del representante pasada como parametro
	 *
	 * @param typeDir - Tipo de Direccion - FISICA/TELEMATICA
	 * @param idDireccion - Identificador direccion del interesado/representante
	 * @param terceroValidadoVO - Informacion del tercero/representante
	 *
	 * @return datos de la Direccion del interesado/representente pasada como parametro
	 */
	private BaseDireccionVO getDireccion(String typeDir, String idDireccion,
			TerceroValidadoVO terceroValidadoVO) {

		//Buscamos la direccion del tercero
		if (DireccionType.TELEMATICA_VALUE == DireccionType.getEnum(
				Integer.valueOf(typeDir).intValue()).getValue()) {
			for(Iterator<DireccionTelematicaVO> it = terceroValidadoVO.getDireccionesTelematicas().iterator(); it.hasNext();){
				DireccionTelematicaVO direccionTelematicaTercero = (DireccionTelematicaVO) it.next();
				if(direccionTelematicaTercero.getId().equals(idDireccion)){
					return direccionTelematicaTercero;
				}
			}
		}else{
			for(Iterator<DireccionFisicaVO> it = terceroValidadoVO.getDireccionesFisicas().iterator(); it.hasNext();){
				DireccionFisicaVO direccionFisicaTercero = (DireccionFisicaVO) it.next();
				if(direccionFisicaTercero.getId().equals(idDireccion)){
					return direccionFisicaTercero;
				}
			}
		}
		return new BaseDireccionVO();
	}

	public InteresadoManager getInteresadoManager() {
		return interesadoManager;
	}

	public void setInteresadoManager(InteresadoManager interesadoManager) {
		this.interesadoManager = interesadoManager;
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	protected InteresadoManager interesadoManager;

	protected TerceroManager terceroManager;

}