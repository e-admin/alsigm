/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;

import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroNoValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase con metodo que tratan la informacion de los terceros
 */
public class TercerosHelper {

	/**
	 * Método para transformar los interesados del registro en el formato del
	 * API a terceros en el formato de Invesicres
	 *
	 * @param registro
	 * @return
	 */
	public static List<FlushFdrInter> getListaInteresadosISicres(BaseRegistroVO registro) {
		List<FlushFdrInter> listaTerceros = new ArrayList<FlushFdrInter>();

		if (isNotNullInteresado(registro.getInteresadoPrincipal())) {
			FlushFdrInter iSicresPersonPrincipal = new FlushFdrInter();
			iSicresPersonPrincipal.setInterName(getNombreTercero(registro
					.getInteresadoPrincipal()));
			iSicresPersonPrincipal.setInterId(getIdentificadorTercero(registro
					.getInteresadoPrincipal()));
			iSicresPersonPrincipal = getDomicilioTercero(
					registro.getInteresadoPrincipal(), iSicresPersonPrincipal);

			listaTerceros.add(iSicresPersonPrincipal);
		}

		if (registro.getInteresados() != null
				&& !registro.getInteresados().isEmpty()) {

			for (Iterator iterator = registro.getInteresados().iterator(); iterator
					.hasNext();) {
				InteresadoVO tercero = (InteresadoVO) iterator.next();

				FlushFdrInter iSicresPerson = new FlushFdrInter();
				iSicresPerson.setInterName(getNombreTercero(tercero));
				iSicresPerson.setInterId(getIdentificadorTercero(tercero));
				iSicresPerson = getDomicilioTercero(tercero, iSicresPerson);

				listaTerceros.add(iSicresPerson);
			}
		}

		return listaTerceros;
	}
	
	/**
	 * Devuelve la lista de interesados no validados a partir de una lista de campos genéricos
	 * 
	 * @param atts Atributos
	 * 
	 * @return La lista de interesados no validados a partir de una lista de campos genéricos
	 */
	public static List<FlushFdrInter> getListaInteresadosISicres(List<FlushFdrField> atts){
		List<FlushFdrInter> listaTerceros = new ArrayList<FlushFdrInter>();
		for(FlushFdrField field : atts){
			//Si es interesado
			if (field.getFldid()==9){				
					FlushFdrInter iSicresPerson = new FlushFdrInter();
					iSicresPerson.setInterName(field.getValue());
					listaTerceros.add(iSicresPerson);
			}
		}
		
		return listaTerceros;
	}

	/**
	 * Metodo que comprueba si un interesado es nulo
	 *
	 * @param interesado
	 * @return boolean - TRUE: el interesado no es nulo FALSE: el interesado es
	 *         nulo
	 */
	private static boolean isNotNullInteresado(InteresadoVO interesado) {
		boolean result = true;

		if (StringUtils.isEmpty(interesado.getId())
				&& StringUtils.isEmpty(interesado.getNombre())) {
			return false;
		}

		return result;
	}

	/**
	 * Método para obtener el nombre del tercero.
	 *
	 * El nombre depende del tipo de tercero que sea, validado o no... fisico o
	 * juridico...
	 *
	 * @param interesado
	 * @return
	 */
	private static String getNombreTercero(InteresadoVO interesado) {
		if (interesado.getTercero() instanceof TerceroValidadoFisicoVO) {
			TerceroValidadoFisicoVO terceroFisico = (TerceroValidadoFisicoVO) interesado
					.getTercero();
			String nombre = terceroFisico.getNombre();
			if (StringUtils.isNotBlank(terceroFisico.getApellido1())) {
				nombre = nombre + " " + terceroFisico.getApellido1();
			}
			if (StringUtils.isNotBlank(terceroFisico.getApellido2())) {
				nombre = nombre + " " + terceroFisico.getApellido2();
			}
			return nombre;
		} else {
			return interesado.getNombre();
		}
	}

	/**
	 * Método para obtener el indetificador del tercero.
	 *
	 * El identificador depende de si es un tercero validado o no
	 *
	 * @param interesado
	 * @return
	 */
	private static int getIdentificadorTercero(InteresadoVO interesado) {
		if (interesado.getTercero() instanceof TerceroNoValidadoVO) {
			return 0;
		} else if (StringUtils.isNotBlank(interesado.getId())
				&& StringUtils.isNumeric(interesado.getId())) {
			return Integer.parseInt(interesado.getId());
		}

		return 0;
	}

	/**
	 * Método para obtener la direccion de notificacion de un tercero
	 *
	 * @param interesado
	 * @param iSicresPerson
	 * @return
	 */
	private static FlushFdrInter getDomicilioTercero(InteresadoVO interesado,
			FlushFdrInter iSicresPerson) {
		if (interesado.getTercero() instanceof TerceroValidadoVO) {
			TerceroValidadoVO terceroValidado = (TerceroValidadoVO) interesado
					.getTercero();
			List direcciones = terceroValidado.getDirecciones();

			if (direcciones != null && !direcciones.isEmpty()) {
				for (Iterator iterator = direcciones.iterator(); iterator
						.hasNext();) {
					BaseDireccionVO direccion = (BaseDireccionVO) iterator
							.next();

					if (StringUtils.isNotBlank(direccion.getId())
							&& StringUtils.isNumeric(direccion.getId())) {
						iSicresPerson.setDomId(Integer.parseInt(direccion
								.getId()));
						iSicresPerson.setDirection(direccion.getDireccion());

						if (direccion.isPrincipal()) {
							break;
						}
					}
				}
			}
		} else {
			iSicresPerson.setDomId(0);
			iSicresPerson.setDirection("");
		}
		return iSicresPerson;
	}
}
