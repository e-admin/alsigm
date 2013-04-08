package transferencias.model.validacion;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import descripcion.vos.ValorCampoGenericoVO;

/**
 * Verifica que los datos numericos a incorporan a una descripcion durante la
 * validacion de unidades documentales son mayores que cero
 */
public class ComprobacionDatosNumericosNoNulos extends ComprobacionFilaTabla {

	/**
	 * Verifica que los valores a establecer para los datos de tipo numerico
	 * especificados en las definiciones de datos suministradas sean mayores que
	 * cero
	 * 
	 * @param listaDatos
	 *            Lista de definiciones de datos que deben ser verificados
	 * @param udocInfo
	 *            Documento xml con informacion de la unidad documental en
	 *            validacion
	 * @return <b>true</b> si todos los valores para los datos de tipo numerico
	 *         especificados en las definiciones de datos indicadas son mayores
	 *         que cero y <b>false</b> en caso contrario
	 */
	public boolean datosValidos(List listaDatos, Document udocInfo) {
		Node dato = null;
		for (Iterator i = listaDatos.iterator(); i.hasNext();) {
			dato = (Node) i.next();
			int tipoDato = Integer.parseInt(dato.valueOf("@TIPO"));
			if (tipoDato == ValorCampoGenericoVO.TIPO_NUMERICO) {
				Node nodeParam = dato.selectSingleNode("PARAM");
				Node nValorDato = udocInfo
						.selectSingleNode(nodeParam.getText());
				if (nValorDato != null) {
					try {
						double doubleValue = Double.parseDouble(nValorDato
								.getText());
						if (!(doubleValue > 0))
							return false;
					} catch (NumberFormatException nfe) {
						return false;
					}
				}
			}
		}
		return true;
	}
}