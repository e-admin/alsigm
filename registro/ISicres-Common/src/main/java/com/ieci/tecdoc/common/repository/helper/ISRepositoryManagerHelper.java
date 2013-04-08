package com.ieci.tecdoc.common.repository.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosValueVO;

public class ISRepositoryManagerHelper {

	/**
	 * Método que compone un <code>ISicresBasicDatosEspecificosVO</code> a
	 * partir de un Mapa con los valores
	 * 
	 * @param values
	 * @return
	 */
	public static ISicresBasicDatosEspecificosVO getMapDatosEspecificosValueVO(
			Map values) {
		Map mapDatosEspecificosVO = new HashMap();

		if (!values.isEmpty()) {
			for (Iterator iterator = values.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				String value = (String) values.get(key);

				ISicresBasicDatosEspecificosValueVO valueVO = new ISicresBasicDatosEspecificosValueVO();
				valueVO.setName(key);
				valueVO.setValue(value);

				mapDatosEspecificosVO.put(key, valueVO);
			}
		}

		ISicresBasicDatosEspecificosVO iSicresBasicDatosEspecificosVO = new ISicresBasicDatosEspecificosVO();
		iSicresBasicDatosEspecificosVO.setValues(mapDatosEspecificosVO);

		return iSicresBasicDatosEspecificosVO;

	}

}
