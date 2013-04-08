package es.ieci.plusvalias.api;

import java.io.Serializable;

public class Inmueble implements Serializable{
	private static final long serialVersionUID = 5171072905591886596L;

	private String numvia;
	private String tipovia;
	private String nombrevia;
	private String numero;
	private String escalera;
	private String planta;
	private String puerta;
	private double supsolar;
	private double coepro;
	private double supconstruida;
	private double valsuelo;
	private double valconstruido;
	private double valcatastral;
	private String refCatastral;

	public Inmueble() {
		super();
	}

	public Inmueble(String numvia, String tipovia, String nombrevia,
			String numero, String escalera, String planta, String puerta,
			double supsolar, double coepro, double supconstruida,
			double valsuelo, double valconstruido, double valcatastral,
			String refCatastral) {
		super();
		this.numvia = numvia;
		this.tipovia = tipovia;
		this.nombrevia = nombrevia;
		this.numero = numero;
		this.escalera = escalera;
		this.planta = planta;
		this.puerta = puerta;
		this.supsolar = supsolar;
		this.coepro = coepro;
		this.supconstruida = supconstruida;
		this.valsuelo = valsuelo;
		this.valconstruido = valconstruido;
		this.valcatastral = valcatastral;
		this.refCatastral = refCatastral;
	}

	public String getNumvia() {
		return numvia;
	}

	public void setNumvia(String numvia) {
		this.numvia = numvia;
	}

	public String getTipovia() {
		return tipovia;
	}

	public void setTipovia(String tipovia) {
		this.tipovia = tipovia;
	}

	public String getNombrevia() {
		return nombrevia;
	}

	public void setNombrevia(String nombrevia) {
		this.nombrevia = nombrevia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public double getSupsolar() {
		return supsolar;
	}

	public void setSupsolar(double supsolar) {
		this.supsolar = supsolar;
	}

	public double getCoepro() {
		return coepro;
	}

	public void setCoepro(double coepro) {
		this.coepro = coepro;
	}

	public double getSupconstruida() {
		return supconstruida;
	}

	public void setSupconstruida(double supconstruida) {
		this.supconstruida = supconstruida;
	}

	public double getValsuelo() {
		return valsuelo;
	}

	public void setValsuelo(double valsuelo) {
		this.valsuelo = valsuelo;
	}

	public double getValconstruido() {
		return valconstruido;
	}

	public void setValconstruido(double valconstruido) {
		this.valconstruido = valconstruido;
	}

	public double getValcatastral() {
		return valcatastral;
	}

	public void setValcatastral(double valcatastral) {
		this.valcatastral = valcatastral;
	}

	public String getRefCatastral() {
		return refCatastral;
	}

	public void setRefCatastral(String refCatastral) {
		this.refCatastral = refCatastral;
	}
}