package ieci.tecdoc.sgm.admsistema.bean;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.admin.interfaces.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UsuarioEntidad implements Serializable, Usuario{

	private static final long serialVersionUID = 6890077441562503048L;

	private String usuario; //PK
	private String password;
	private String nombre; 
	private String apellidos;
	private Date fechaAlta;
	private List entidades;
	

	public UsuarioEntidad(ieci.tecdoc.sgm.core.services.administracion.Usuario oUsuario, String entidad) {
		this.usuario = oUsuario.getUsuario();
		this.password = oUsuario.getPassword();
		this.nombre = oUsuario.getNombre();
		this.apellidos = oUsuario.getApellidos();
		this.fechaAlta = oUsuario.getFechaAlta();
		this.entidades = new ArrayList();
		entidades.add(entidad);
	}
	
	public UsuarioEntidad(ieci.tecdoc.sgm.core.services.administracion.Usuario oUsuario, Entidad[] entidad) {
		this.usuario = oUsuario.getUsuario();
		this.password = oUsuario.getPassword();
		this.nombre = oUsuario.getNombre();
		this.apellidos = oUsuario.getApellidos();
		this.fechaAlta = oUsuario.getFechaAlta();
		this.entidades = new ArrayList();
		for(int i=0; i<entidad.length; i++)
			entidades.add(entidad[i].getNombreLargo());
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
		
	}
	
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
		
	}
	
	public List getEntidades() {
		return entidades;
	}

	public void setEntidades(List entidades) {
		this.entidades = entidades;
	}

	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Usuario";
		String tagNameEnts = "Entidades";
		
		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Usuario", usuario);
		bdr.addSimpleElement("Nombre", nombre);
		bdr.addSimpleElement("Apellidos", apellidos);
		bdr.addSimpleElement("Fecha Alta", fechaAlta.toString());
		bdr.addOpeningTag(tagNameEnts);
		if (entidades != null)
			for(int i=0; i<entidades.size(); i++)
				bdr.addSimpleElement("Entidad", (String)entidades.get(i));
		bdr.addClosingTag(tagNameEnts);
		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	public String toString() {
		return toXML(false);
	}

	public void addEntidad(String idEntidad)  {
		if (entidades == null)
			entidades = new ArrayList();
		entidades.add(idEntidad);
	}
	
}
