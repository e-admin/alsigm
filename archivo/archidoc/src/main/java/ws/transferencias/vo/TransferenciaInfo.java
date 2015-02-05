/**
 *
 */
package ws.transferencias.vo;

import java.io.Serializable;




/**
 * Información de la transferencia.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TransferenciaInfo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Año del expediente en formato AAAA
	 */
	private int anioExpediente;

	/**
	 * Código del procedimiento
	 */
	private String codigoProcedimiento;

	private TramitadorInfo sistemaTramitador;

	/**
	 * Contenido XML del expediente
	 */
	private byte[] contenidoXml;

	/**
	 * 0 (No verifica) <br/>
	 * 1 (Por número de expediente no nulo <br/>
	 * 2 (Por nº de expediente no nulo + titulo (asunto))<br/>
	 * 3 (Por nº de expediente no nulo + titulo (asunto) + fecha inicial)<br/>
	 * 4 (Por nº de expediente no nulo + titulo (asunto) + fecha inicial + fecha
	 * final)
	 */
	private int verificarUnicidad;

	public TransferenciaInfo() {
		super();
	}

	public TransferenciaInfo(int anioExpediente,
			String codigoProcedimiento, TramitadorInfo sistemaTramitador,
			byte[] contenidoXml, int verificarUnicidad) {
		super();
		this.anioExpediente = anioExpediente;
		this.codigoProcedimiento = codigoProcedimiento;
		this.sistemaTramitador = sistemaTramitador;
		this.contenidoXml = contenidoXml;
		this.verificarUnicidad = verificarUnicidad;
	}

	/**
	 * @return el anioExpediente
	 */
	public int getAnioExpediente() {
		return anioExpediente;
	}

	/**
	 * @param anioExpediente
	 *            el anioExpediente a fijar
	 */
	public void setAnioExpediente(int anioExpediente) {
		this.anioExpediente = anioExpediente;
	}

	/**
	 * @return el codigoProcedimiento
	 */
	public String getCodigoProcedimiento() {
		return codigoProcedimiento;
	}

	/**
	 * @param codigoProcedimiento
	 *            el codigoProcedimiento a fijar
	 */
	public void setCodigoProcedimiento(String codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}

	/**
	 * @return el contenidoXml
	 */
	public byte[] getContenidoXml() {
		return contenidoXml;
	}

	/**
	 * @param contenidoXml
	 *            el contenidoXml a fijar
	 */
	public void setContenidoXml(byte[] contenidoXml) {
		this.contenidoXml = contenidoXml;
	}

	/**
	 * @return el verificarUnicidad
	 */
	public int getVerificarUnicidad() {
		return verificarUnicidad;
	}

	/**
	 * @param verificarUnicidad
	 *            el verificarUnicidad a fijar
	 */
	public void setVerificarUnicidad(int verificarUnicidad) {
		this.verificarUnicidad = verificarUnicidad;
	}

	public void setSistemaTramitador(TramitadorInfo sistemaTramitador) {
		this.sistemaTramitador = sistemaTramitador;
	}

	public TramitadorInfo getSistemaTramitador() {
		return sistemaTramitador;
	}

}
