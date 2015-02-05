package transferencias.electronicas.serie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.procedimientos.IProcedimiento;
import se.procedimientos.InfoBProcedimiento;
import transferencias.electronicas.common.SistemaTramitador;

import common.Constants;
import common.util.ListUtils;

public class Procedimiento implements IProcedimiento, InfoBProcedimiento{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String codigo;
	private String nombre;
	private String tituloCorto;
	private String objeto;
	private String documentos;
	private String normativa;
	private List<Tramite> listaTramites = new ArrayList<Tramite>();
	private SistemaTramitador sistemaTramitador;

	/**
	 * @return el id
	 */
	public String getId() {
		return codigo;
	}
	/**
	 * @param id el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo el codigo a fijar
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return el tituloCorto
	 */
	public String getTituloCorto() {
		return tituloCorto;
	}
	/**
	 * @param tituloCorto el tituloCorto a fijar
	 */
	public void setTituloCorto(String tituloCorto) {
		this.tituloCorto = tituloCorto;
	}
	/**
	 * @return el objeto
	 */
	public String getObjeto() {
		return objeto;
	}
	/**
	 * @param objeto el objeto a fijar
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	/**
	 * @return el documentos
	 */
	public String getDocumentos() {
		return documentos;
	}
	/**
	 * @param documentos el documentos a fijar
	 */
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}
	/**
	 * @return el tramites
	 */
	public List<Tramite> getListaTramites() {
		return listaTramites;
	}
	/**
	 * @param tramites el tramites a fijar
	 */
	public void setTramites(List<Tramite> tramites) {
		this.listaTramites = tramites;
	}
	/**
	 * @return el sistemaTramitador
	 */
	public SistemaTramitador getSistemaTramitador() {
		return sistemaTramitador;
	}
	/**
	 * @param sistemaTramitador el sistemaTramitador a fijar
	 */
	public void setSistemaTramitador(SistemaTramitador sistemaTramitador) {
		this.sistemaTramitador = sistemaTramitador;
	}

	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}
	public String getNormativa() {
		return normativa;
	}

	public void addTramite(Tramite tramite){
		listaTramites.add(tramite);
	}

	public InfoBProcedimiento getInformacionBasica() {
		return this;
	}

	public String getTramites() {
		StringBuilder str = new StringBuilder();

		if(ListUtils.isNotEmpty(listaTramites)){
			for (Iterator<Tramite> iterator = listaTramites.iterator(); iterator.hasNext();) {
				Tramite tramite =  iterator.next();

				if(tramite != null){
					str.append(tramite);
					str.append(Constants.NEWLINE);
				}
			}
		}

		return str.toString();

	}
	public String getDocumentosBasicos() {
		return documentos;
	}
	public String getCodSistProductor() {
		return getSistemaTramitador().getId();
	}
	public String getNombreSistProductor() {
		return getSistemaTramitador().getNombre();
	}
	public List getOrganosProductores() {
		return null;
	}
}
