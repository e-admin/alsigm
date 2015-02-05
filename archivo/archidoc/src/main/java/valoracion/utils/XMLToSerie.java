package valoracion.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import valoracion.view.InfoSerie;

import common.Constants;
import common.exceptions.ArchivoModelException;

import fondos.model.Serie;
import fondos.vos.SerieVO;

/**
 * Clase que transforma del XML con la información de las series de una
 * valoración en un listado de objetos serie y a la inversa. El xml debe tener
 * el formato: <Series> <Serie> <Id_Serie></Id_Serie>
 * <Cod_Referencia></Cod_Referencia> <Título></Título> </Serie> . . . <Serie>
 * <Id_Serie></Id_Serie> <Cod_Referencia></Cod_Referencia> <Título></Título>
 * </Serie> </Series >
 */
public class XMLToSerie {
	/** Logger de la clase */
	private final static Logger logger = Logger.getLogger(XMLToSerie.class);

	public static XMLToSerie getInstance() {
		return new XMLToSerie();
	}

	/**
	 * Metodo que realizar todo el proceso de transformacion de una lista de
	 * series en un xml
	 * 
	 * @param series
	 *            Listado de series
	 * @return XML generado
	 */
	public String transform(List series) {
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");

		xml.append("<Series>");
		for (int i = 0; i < series.size(); i++) {
			InfoSerie serie = (InfoSerie) series.get(i);

			xml.append("<Serie>");
			xml.append("<Id_Serie>")
					.append(serie.getId() != null ? Constants.addCData(serie
							.getId()) : "").append("</Id_Serie>");
			xml.append("<Cod_Referencia>")
					.append(serie.getCodigo() != null ? Constants
							.addCData(serie.getCodigo()) : "")
					.append("</Cod_Referencia>");
			xml.append("<Titulo>")
					.append(Constants.addCData(serie.getTitulo()))
					.append("</Titulo>");
			xml.append("</Serie>");
		}
		xml.append("</Series>");

		return xml.toString();
	}

	/**
	 * Método que realiza todo el proceso de transformación del xml en la lista
	 * de series
	 * 
	 * @param xml
	 *            XML con la informacion de series
	 * @return Listado con la informacion de las series {@see SerieVO}
	 */
	public List transform(String xml) {
		Document udocInfo = null;
		String idSerie = null, codReferencia = null, tituloSerie = null;
		SerieVO serieVO = null;
		List seriesVO = new ArrayList();
		SAXReader saxReader = new SAXReader();

		if (xml != null && xml.trim().length() > 0) {
			try {
				udocInfo = saxReader.read(new StringReader(xml.toString()));
			} catch (DocumentException e) {
				logger.error("Se ha producido un error obteniendo las series del xml "
						+ xml);
				throw new ArchivoModelException(e, "transform",
						"Se ha producido un error obteniendo las series del xml "
								+ xml);
			}

			List series = udocInfo.selectNodes("/Series/Serie");

			for (Iterator iter = series.iterator(); iter.hasNext();) {
				org.dom4j.Node serie = ((org.dom4j.Node) iter.next());

				org.dom4j.Node id = serie.selectSingleNode("Id_Serie");
				idSerie = id.getStringValue();

				org.dom4j.Node ref = serie.selectSingleNode("Cod_Referencia");
				codReferencia = ref.getStringValue();

				org.dom4j.Node titulo = serie.selectSingleNode("Titulo");
				tituloSerie = titulo.getStringValue();

				// Creamos la serie con los datos
				serieVO = new Serie();
				serieVO.setId(idSerie);
				serieVO.setCodReferencia(codReferencia);
				serieVO.setTitulo(tituloSerie);
				seriesVO.add(serieVO);
			}
		}

		return seriesVO;
	}
}
