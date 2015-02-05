package ieci.tecdoc.sgm.certificacion.xml.bean;

import java.util.ArrayList;

/**
 * Clase que almacena el valor del nodo de un campo del XML
 * @author José Antonio Nogales
 */
public class Campo {
	String etiquetaXML;
	String valor;
	ArrayList hijosCampo;
	
	/**
	 * Constructor de la clase
	 * @param etiquetaXML Valor de la etiqueta del XML
	 * @param valor  Valor del nodo del XML
	 * @param hijosCampo Nodos hijo del campo. Ejemplo
	 * 	Etiqueta: TAG1, valor: null, hijosCampo: [(TAG2, V1, null), (TAG3, V2, null)]
	 * <TAG1> <TAG2>V1</TAG2> <TAG3>V2</TAG3> </TAG1>
	 */
	public Campo(String etiquetaXML, String valor, ArrayList hijosCampo) {
		this.etiquetaXML = etiquetaXML;
		this.valor = valor;
		this.hijosCampo = hijosCampo;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public Campo() {
		this.etiquetaXML = null;
		this.valor = null;
		this.hijosCampo = null;
	}

	/**
	 * Métodos get y set de la clase
	 */
	public String getEtiquetaXML() {
		return etiquetaXML;
	}

	public void setEtiquetaXML(String etiquetaXML) {
		this.etiquetaXML = etiquetaXML;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public ArrayList getHijosCampo() {
		return hijosCampo;
	}

	public void setHijosCampo(ArrayList hijosCampo) {
		this.hijosCampo = hijosCampo;
	}
	
	public Campo getHijoCampo(int index) {
		if(index < hijosCampo.size())
			return (Campo)hijosCampo.get(index);
		return null;
	}

	public void setHijoCampo(int index, Campo hijoCampo) {
		if(index < hijosCampo.size())
			hijosCampo.set(index, hijoCampo);
	}
	
	public void addHijoCampo(int index, Campo hijoCampo) {
		hijosCampo.add(hijoCampo);
	}
}
