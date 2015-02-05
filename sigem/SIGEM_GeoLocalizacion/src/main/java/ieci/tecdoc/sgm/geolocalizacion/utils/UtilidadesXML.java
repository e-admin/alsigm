package ieci.tecdoc.sgm.geolocalizacion.utils;

import ieci.tecdoc.sgm.geolocalizacion.datatypes.Coordenada;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.Portal;
import ieci.tecdoc.sgm.geolocalizacion.datatypes.Via;

import java.util.StringTokenizer;

import xtom.parser.Element;
import xtom.parser.Parser;
import xtom.parser.UnknownElementException;
import xtom.parser.XMLTree;

public class UtilidadesXML {
	
	public static Via[] parsearXMLVias(String xml) {
		Parser parser = new Parser(xml);
		XMLTree xmlTree = parser.parse();
		Element root = xmlTree.getRootElement();
		
		Element[] nodos = root.getElementsByPath(TAG_NODO);
		if (nodos == null)
			return new Via[0];
		
		Element hijo = null;
		Via[] vias = new Via[nodos.length];
		String cs, ts, coords;
		StringTokenizer st, stcoord;
		
		for (int i=0; i<nodos.length; i++) {
			vias[i] = new Via();
			hijo = nodos[i].getElementByPath(TAG_ID);
			int idVia = (hijo.getAttribute(TAG_ATRIBUTO_ID).getValue() != null) ? hijo.getAttribute(TAG_ATRIBUTO_ID).getValueAsInt() : 0;
			vias[i].setIdVia(idVia);
			hijo = nodos[i].getElementByPath(TAG_NOMBRE);
			vias[i].setNombreVia(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_IDIOMA);
			vias[i].setIdioma(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_CLASE_NOMBRE);
			vias[i].setClaseNombre(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_ESTATUS);
			vias[i].setEstatus(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_FUENTE);
			vias[i].setFuente(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_TIPO);
			vias[i].setTipoVia(hijo.getValue());
			
			hijo = getElementByPath(nodos[i], TAG_COORDENADAS);
			if (hijo != null) {
				Element[] coordenadas = hijo.getElementsByPath(TAG_COORDENADAS_MULTI);
				Coordenada[][] coordenadasVia = null; 
				if (coordenadas != null) {
					coordenadasVia = new Coordenada[coordenadas.length][];
					for (int j=0; j<coordenadas.length; j++){
						hijo =coordenadas[j].getElementByPath(TAG_COORDENADA);
						cs = hijo.getAttribute(TAG_ATRIBUTO_SEPARADOR_CS).getValue();
						//decimal = hijo.getAttribute(TAG_ATRIBUTO_SEPARADOR_DECIMAL).getValue();
						ts = hijo.getAttribute(TAG_ATRIBUTO_SEPARADOR_TS).getValue();
						st = new StringTokenizer(hijo.getValue(), ts);
						coordenadasVia[j] = new Coordenada[st.countTokens()];
						int k=0;
						while(st.hasMoreTokens()){
				            coords = st.nextToken();
				            stcoord = new StringTokenizer(coords, cs);
				            coordenadasVia[j][k] = new Coordenada(
				            		new Double(stcoord.nextToken()).doubleValue(), new Double(stcoord.nextToken()).doubleValue()
				            	);
				            k++;
				        }
					}
				}
				vias[i].setCoordenadas(coordenadasVia);
			}
			
			Element[] portales = getElementsByPath(nodos[i], TAG_PORTALES);
			Portal[] portalesVia = null;
			if (portales != null){
				portalesVia = new Portal[portales.length];
				for(int h=0; h<portales.length; h++){
					portalesVia[h] = new Portal(0, portales[h].getValueAsInt(), 0, null, null, null, null, null, null, null, 0);
				}
			}
			vias[i].setPortales(portalesVia);
			
			hijo = getElementByPath(nodos[i], TAG_CODIGO_INE_MUNICIPIO);
			if (hijo != null) {
				int codigoINE = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
				vias[i].setCodigoINEMunicipio(codigoINE);
			}
			
			hijo = getElementByPath(nodos[i], TAG_PROVINCIA);
			if (hijo != null) {
				int provincia = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
				vias[i].setProvincia(provincia);
			}
		}

		return vias;
	}
	
	public static Portal[] parsearXMLPortales(String xml) {
		Parser parser = new Parser(xml);
		XMLTree xmlTree = parser.parse();
		Element root = xmlTree.getRootElement();
		
		Element[] nodos = root.getElementsByPath(TAG_NODO);
		if (nodos == null)
			return new Portal[0];
		
		Element hijo = null;
		Portal[] portales = new Portal[nodos.length];
		String cs;
		StringTokenizer st;
		
		for (int i=0; i<nodos.length; i++) {
			portales[i] = new Portal();
			hijo = nodos[i].getElementByPath(TAG_ID);
			int idPortal = (hijo.getAttribute(TAG_ATRIBUTO_ID).getValue() != null) ? hijo.getAttribute(TAG_ATRIBUTO_ID).getValueAsInt() : 0;
			portales[i].setIdPortal(idPortal);
			hijo = nodos[i].getElementByPath(TAG_NOMBRE);
			portales[i].setNumPortal(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_IDIOMA);
			portales[i].setIdioma(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_CLASE_NOMBRE);
			portales[i].setClaseNombre(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_ESTATUS);
			portales[i].setEstatus(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_FUENTE);
			portales[i].setFuente(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_TIPO);
			portales[i].setTipoPortal(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_COORDENADA_PUNTO);
			cs = hijo.getAttribute(TAG_ATRIBUTO_SEPARADOR_CS).getValue();
			st = new StringTokenizer(hijo.getValue(), cs);
			portales[i].setCoords(
					new Coordenada(new Double(st.nextToken()).doubleValue(), new Double(st.nextToken()).doubleValue())
				);
			hijo = nodos[i].getElementByPath(TAG_PORTALES);
			int idVia =  (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setIdVia(idVia);
			hijo = nodos[i].getElementByPath(TAG_CODIGO_INE_MUNICIPIO);
			int codigoINE = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setCodigoINEMunicipio(codigoINE);
			hijo = nodos[i].getElementByPath(TAG_PROVINCIA);
			int provincia = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setProvincia(provincia);
		}
		
		return portales;
	}
	
	public static Portal[] parsearXMLPodsrtales(String xml) {
		Parser parser = new Parser(xml);
		XMLTree xmlTree = parser.parse();
		Element root = xmlTree.getRootElement();
		
		Element[] nodos = root.getElementsByPath(TAG_NODO);
		if (nodos == null)
			return new Portal[0];
		
		Element hijo = null;
		Portal[] portales = new Portal[nodos.length];
		String cs;
		StringTokenizer st;
		
		for (int i=0; i<nodos.length; i++) {
			portales[i] = new Portal();
			hijo = nodos[i].getElementByPath(TAG_ID);
			int idPortal = (hijo.getAttribute(TAG_ATRIBUTO_ID).getValue() != null) ? hijo.getAttribute(TAG_ATRIBUTO_ID).getValueAsInt() : 0;
			portales[i].setIdPortal(idPortal);
			hijo = nodos[i].getElementByPath(TAG_NOMBRE);
			portales[i].setNumPortal(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_IDIOMA);
			portales[i].setIdioma(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_CLASE_NOMBRE);
			portales[i].setClaseNombre(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_ESTATUS);
			portales[i].setEstatus(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_FUENTE);
			portales[i].setFuente(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_TIPO);
			portales[i].setTipoPortal(hijo.getValue());
			hijo = nodos[i].getElementByPath(TAG_COORDENADA_PUNTO);
			cs = hijo.getAttribute(TAG_ATRIBUTO_SEPARADOR_CS).getValue();
			st = new StringTokenizer(hijo.getValue(), cs);
			portales[i].setCoords(
					new Coordenada(new Double(st.nextToken()).doubleValue(), new Double(st.nextToken()).doubleValue())
				);
			hijo = nodos[i].getElementByPath(TAG_PORTALES);
			int idVia =  (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setIdVia(idVia);
			hijo = nodos[i].getElementByPath(TAG_CODIGO_INE_MUNICIPIO);
			int codigoINE = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setCodigoINEMunicipio(codigoINE);
			hijo = nodos[i].getElementByPath(TAG_PROVINCIA);
			int provincia = (hijo.getValue() != null) ? hijo.getValueAsInt() : 0;
			portales[i].setProvincia(provincia);
		}
		
		return portales;
	}

	protected static Element getElementByPath(Element node, String path) {
		Element element = null;
		
		if (node != null) {
			try {
				element = node.getElementByPath(path);
			} catch (UnknownElementException e) {}
		}
		
		return element;
	}

	protected static Element[] getElementsByPath(Element node, String path) {
		Element[] elements = null;
		
		if (node != null) {
			try {
				elements = node.getElementsByPath(path);
			} catch (UnknownElementException e) {}
		}
		
		return elements;
	}

	private static final String TAG_NODO = "gml:featureMember";
	private static final String TAG_NOMBRE = "Entidad/nombreEntidad/nombre";
	private static final String TAG_IDIOMA = "Entidad/nombreEntidad/idioma";
	private static final String TAG_CLASE_NOMBRE = "Entidad/nombreEntidad/claseNombre";
	private static final String TAG_ESTATUS = "Entidad/nombreEntidad/estatus";
	private static final String TAG_FUENTE = "Entidad/nombreEntidad/fuente";
	private static final String TAG_TIPO = "Entidad/tipoEntidad/tipo";
	private static final String TAG_ID = "Entidad";
	private static final String TAG_ATRIBUTO_ID = "fid";
	private static final String TAG_COORDENADAS = "Entidad/posicionEspacial/gml:MultiLineString";
	private static final String TAG_COORDENADAS_MULTI = "gml:LineString";
	private static final String TAG_COORDENADA = "gml:coordinates";
	private static final String TAG_COORDENADA_PUNTO = "Entidad/posicionEspacial/gml:Point/gml:coordinates";
	private static final String TAG_PORTALES = "Entidad/EntidadRelacionada/idEntidad";
	private static final String TAG_ATRIBUTO_SEPARADOR_CS = "cs";
	//private static final String TAG_ATRIBUTO_SEPARADOR_DECIMAL = "decimal";
	private static final String TAG_ATRIBUTO_SEPARADOR_TS = "ts";
	private static final String TAG_CODIGO_INE_MUNICIPIO = "Entidad/entidadLocal/municipio";
	private static final String TAG_PROVINCIA = "Entidad/entidadLocal/provincia";
	
}
