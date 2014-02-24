package ieci.tecdoc.sgm.migration.mgr.dto;

import java.util.List;

public class ResultadoMigracionDto {

	/**
	 * Total de registros migrados del libro de entrada
	 */
	private int totalConsolidadosEntrada;
	
	/**
	 * Total de registros NO migrados del libro de entrada
	 */
	private int totalNoConsolidadosEntrada;
	
	/**
	 * Total de registros migrados del libro de salida
	 */
	private int totalConsolidadosSalida;
	
	/**
	 * Total de registros NO migrados del libro de salida
	 */
	private int totalNoConsolidadosSalida;
	
	/**
	 * Fecha de inicio del proceso de migración en formato dd/MM/yyyy hh:mm:ss
	 */
	private String fechaInicio;
	
	/**
	 * Fecha de finalización del proceso de migración en formato dd/MM/yyyy hh:mm:ss
	 */
	private String fechaFin;
	
	/**
	 * Listado de números de registros de entrada consolidados
	 * @return
	 */
	private List<String> numbersRegistersConsolidadosEntrada;
	
	/**
	 * Listado de números de registros de entrada no consolidados
	 * @return
	 */
	private List<String> numbersRegistersNoConsolidadosEntrada;
	
	/**
	 * Listado de números de registros de salida consolidados
	 * @return
	 */
	private List<String> numbersRegistersConsolidadosSalida;
	
	/**
	 * Listado de números de registros de salida no consolidados
	 * @return
	 */
	private List<String> numbersRegistersNoConsolidadosSalida;
	
	
	public int getTotalConsolidadosEntrada() {
		return totalConsolidadosEntrada;
	}
	public void setTotalConsolidadosEntrada(int totalConsolidadosEntrada) {
		this.totalConsolidadosEntrada = totalConsolidadosEntrada;
	}
	public int getTotalNoConsolidadosEntrada() {
		return totalNoConsolidadosEntrada;
	}
	public void setTotalNoConsolidadosEntrada(int totalNoConsolidadosEntrada) {
		this.totalNoConsolidadosEntrada = totalNoConsolidadosEntrada;
	}
	public int getTotalConsolidadosSalida() {
		return totalConsolidadosSalida;
	}
	public void setTotalConsolidadosSalida(int totalConsolidadosSalida) {
		this.totalConsolidadosSalida = totalConsolidadosSalida;
	}
	public int getTotalNoConsolidadosSalida() {
		return totalNoConsolidadosSalida;
	}
	public void setTotalNoConsolidadosSalida(int totalNoConsolidadosSalida) {
		this.totalNoConsolidadosSalida = totalNoConsolidadosSalida;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List<String> getNumbersRegistersConsolidadosEntrada() {
		return numbersRegistersConsolidadosEntrada;
	}
	public void setNumbersRegistersConsolidadosEntrada(
			List<String> numbersRegistersConsolidadosEntrada) {
		this.numbersRegistersConsolidadosEntrada = numbersRegistersConsolidadosEntrada;
	}
	public List<String> getNumbersRegistersConsolidadosSalida() {
		return numbersRegistersConsolidadosSalida;
	}
	public void setNumbersRegistersConsolidadosSalida(
			List<String> numbersRegistersConsolidadosSalida) {
		this.numbersRegistersConsolidadosSalida = numbersRegistersConsolidadosSalida;
	}
	public List<String> getNumbersRegistersNoConsolidadosEntrada() {
		return numbersRegistersNoConsolidadosEntrada;
	}
	public void setNumbersRegistersNoConsolidadosEntrada(
			List<String> numbersRegistersNoConsolidadosEntrada) {
		this.numbersRegistersNoConsolidadosEntrada = numbersRegistersNoConsolidadosEntrada;
	}
	public List<String> getNumbersRegistersNoConsolidadosSalida() {
		return numbersRegistersNoConsolidadosSalida;
	}
	public void setNumbersRegistersNoConsolidadosSalida(
			List<String> numbersRegistersNoConsolidadosSalida) {
		this.numbersRegistersNoConsolidadosSalida = numbersRegistersNoConsolidadosSalida;
	}
}
