package valoracion.utils;

import ieci.core.db.DbEngine;
import ieci.core.guid.GuidManager;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import util.StringOwnTokenizer;
import valoracion.vos.CriterioFechaVO;
import valoracion.vos.FechaVO;

import common.Constants;
import common.exceptions.ArchivoModelException;

/**
 * Clase que transforma del XML con la informacion de criterios de busqueda en
 * un listado de objetos criterios y a la inversa. El XML debe tener el
 * siguiente formato: <Criterios_Búsqueda Versión=”01.00”>
 * <Condiciones_Fechas_Extremas> <Condición> <Fecha_Inicial>
 * <Operador><![CDATA[<=]]></Operador> <Valor>2000/12/12 (es decir formato
 * yyyy/mm/dd</Valor> </Fecha_Inicial> <Fecha_Final> <Operador></Operador>
 * <Valor></Valor> </Fecha_Final> </Condición> . . .
 * </Condiciones_Fechas_Extremas> </Criterios_Búsqueda>
 */
public class XMLtoCriterio {
	/** Logger de la clase */
	private final static Logger logger = Logger.getLogger(XMLtoCriterio.class);

	private DbEngine dbEngine;

	/**
	 * @param dbEngine
	 */
	public XMLtoCriterio(DbEngine dbEngine) {
		super();
		this.dbEngine = dbEngine;
	}

	/**
	 * Método que realiza todo el proceso de transformación del xml en la lista
	 * de criterios
	 * 
	 * @param xml
	 *            XML con la informacion de criterios
	 * @return Listado con la informacion de los criterios {@see
	 *         CriterioFechaVO}
	 */
	public List transform(String xml) {
		List criterios = new ArrayList();
		FechaVO fechaInicial = null;
		FechaVO fechaFinal = null;
		CriterioFechaVO criterio = null;
		SAXReader saxReader = new SAXReader();
		Document udocInfo = null;

		if (xml != null && xml.trim().length() > 0) {
			try {
				udocInfo = saxReader.read(new StringReader(xml.toString()));
			} catch (DocumentException e) {
				logger.error("Se ha producido un error generando el reader para los criterios de la eliminacion");
				throw new ArchivoModelException(e, "transform",
						"Error generando el reader para los criterios de la eliminacion");
			}

			List condiciones = udocInfo
					.selectNodes("/Criterios_Busqueda/Condiciones_Fechas_Extremas/Condicion");

			for (Iterator iter = condiciones.iterator(); iter.hasNext();) {
				Node condicion = (Node) iter.next();

				Node operador = condicion
						.selectSingleNode("Fecha_Inicial/Operador");
				Node valor = condicion.selectSingleNode("Fecha_Inicial/Valor");

				if (valor != null && valor.getStringValue() != null
						&& valor.getStringValue().length() > 0) {
					fechaInicial = new FechaVO();
					fechaInicial.setOperador(operador.getStringValue());

					StringOwnTokenizer st = new StringOwnTokenizer(
							valor.getStringValue(), "/");
					fechaInicial.setMes(st.nextToken());
					fechaInicial.setDia(st.nextToken());
				}

				Node operadorFinal = condicion
						.selectSingleNode("Fecha_Final/Operador");
				Node valorFinal = condicion
						.selectSingleNode("Fecha_Final/Valor");

				if (valorFinal != null && valorFinal.getStringValue() != null
						&& valorFinal.getStringValue().length() > 0) {
					fechaFinal = new FechaVO();
					fechaFinal.setOperador(operadorFinal.getStringValue());

					StringOwnTokenizer st = new StringOwnTokenizer(
							valorFinal.getStringValue(), "/");
					fechaFinal.setMes(st.nextToken());
					fechaFinal.setDia(st.nextToken());
				}

				// Creamos el criterio con las fechas
				criterio = new CriterioFechaVO(dbEngine);
				criterio.setFechaFinal(fechaFinal);
				criterio.setFechaInicial(fechaInicial);
				try {
					criterio.setId(GuidManager.generateGUID(dbEngine));
				} catch (Exception e) {
					logger.error("Se ha producido un error generando el identificador para el criterio");
					throw new ArchivoModelException(e, "transform",
							"Error generando el identificador para el criterio");
				}

				// Añadimos el criterio a la listas
				criterios.add(criterio);
			}
		}

		return criterios;
	}

	/**
	 * Metodo que realizar todo el proceso de transformacion de una lista de
	 * criterios en un xml
	 * 
	 * @param series
	 *            Listado de criterios
	 * @return XML generado
	 */
	public String transform(List criterios) {
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
		xml.append("<Criterios_Busqueda version=\"01.00\">");

		if (criterios != null && criterios.size() > 0) {
			xml.append("<Condiciones_Fechas_Extremas>");

			for (int i = 0; i < criterios.size(); i++) {
				CriterioFechaVO criterio = (CriterioFechaVO) criterios.get(i);

				xml.append("<Condicion>");

				if (criterio.getFechaInicial() != null) {
					xml.append("<Fecha_Inicial>");

					xml.append("<Operador>")
							.append(Constants.addCData(criterio
									.getFechaInicial().getOperador()))
							.append("</Operador>");

					xml.append("<Valor>")
							.append(criterio.getFechaInicial().getMes())
							.append("/")
							.append(criterio.getFechaInicial().getDia())
							.append("/").append("</Valor>");

					xml.append("</Fecha_Inicial>");
				}

				if (criterio.getFechaFinal() != null) {
					xml.append("<Fecha_Final>");

					xml.append("<Operador>")
							.append(Constants.addCData(criterio.getFechaFinal()
									.getOperador())).append("</Operador>");

					xml.append("<Valor>")
							.append(criterio.getFechaFinal().getMes())
							.append("/")
							.append(criterio.getFechaFinal().getDia())
							.append("/").append("</Valor>");

					xml.append("</Fecha_Final>");
				}

				xml.append("</Condicion>");
			}

			xml.append("</Condiciones_Fechas_Extremas>");
		}
		xml.append("</Criterios_Busqueda>");

		return xml.toString();
	}
}
