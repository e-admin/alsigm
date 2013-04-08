package deposito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;

import common.ConfigConstants;
import common.Constants;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoVO;
import deposito.vos.TipoElementoVO;

public class CartelasDepositoHelper {

	/**
	 * Permite obtener un HashMap con los valores necesarios para mostrar en el
	 * informe de cartelas de depósito
	 * 
	 * @param ltPadres
	 *            Lista de padres del hueco (inclusive)
	 * @param depositoBI
	 *            Gestor de Depósito
	 * @return
	 */
	public static HashMap getMapElementosDepositoNombresElemento(
			LinkedList ltPadres, GestorEstructuraDepositoBI depositoBI) {
		HashMap mapCodOrden = new HashMap();

		if ((ltPadres != null) && (!ltPadres.isEmpty())) {
			ArrayList nivelesVisibles = ConfigConstants.getInstance()
					.getNivelesDepositoVisiblesCartelasAsList();
			TipoElementoVO tipoElementoVO = null;
			ElementoVO elementoVO = null;
			Object elemento = null;
			Iterator it = ltPadres.iterator();
			int i = 1;
			while (it.hasNext()) {
				elemento = it.next();
				if (elemento instanceof ElementoVO) {
					elementoVO = (ElementoVO) elemento;

					tipoElementoVO = depositoBI
							.getTipoElementoSingleton(elementoVO
									.getIdTipoElemento());

					if (nivelesVisibles.contains(tipoElementoVO
							.getCaracterorden())) {
						String nombreElemento = String.valueOf(elementoVO
								.getNumorden());
						String nombreCompleto = elementoVO.getNombre();
						if (StringUtils.isNotEmpty(nombreCompleto)) {
							nombreElemento = StringUtils.replace(
									nombreCompleto, tipoElementoVO.getNombre(),
									Constants.STRING_EMPTY);
							if (StringUtils.isNotEmpty(nombreElemento))
								nombreElemento = nombreElemento.trim();
						}
						mapCodOrden.put(String.valueOf(i), new String[] {
								tipoElementoVO.getCaracterorden(),
								nombreElemento });
					}
				} else if (elemento instanceof HuecoVO) {
					if (nivelesVisibles.contains(Constants.LETRA_HUECO)) {
						HuecoVO huecoVO = (HuecoVO) elemento;
						mapCodOrden
								.put(String.valueOf(i),
										new String[] {
												Constants.LETRA_HUECO,
												String.valueOf(huecoVO
														.getNumorden()) });
					}
				}
				i++;
			}
		}
		return mapCodOrden;
	}
}
