/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.form.adminUser.bd;

import ieci.tecdoc.idoc.admin.api.user.AplicacionPerfil;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserForm extends ActionForm{

    private static Logger logger = Logger.getLogger(UserForm.class);

    int id;
    String nombre;
    String pwd;
    String repwd;
    String descripcion;
    String nombrePersonal;
    String apellidos;
    boolean pwdmbc;
    boolean pwdvpcheck;

    String gruposAsignados[];

    Collection listaAplicaciones;

    String idCert;

    public boolean systemSuperuser;
    /**
     * Valores posibles para user: superuser, manager, standard, none
     */
    public String user;
    /**
     * Valores posibles para idoc: superuser, manager, standard, none
     */
    public String idoc;
    public boolean volumeSuperuser;

    // Permisos Genéricos
    boolean idocConsulta;
    boolean idocModificacion;
    boolean idocCreacion;
    boolean idocBorrado;
    boolean idocImpresion;


    //Datos personales
    String cargo;
    String email;
    String tfnoMovil;
    
    

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        if (logger.isDebugEnabled())
            logger.debug("#User Form Reset!");
        int id = -1 ;
        nombre =
        descripcion =
        pwd =
        repwd =
        descripcion =
        user =
        idoc =
        idCert =
        cargo =
        email =
        tfnoMovil =
        nombrePersonal=
        apellidos=
        "";


        pwdmbc =
        pwdvpcheck =
        systemSuperuser =
        volumeSuperuser=
        idocConsulta=
        idocModificacion=
        idocCreacion =
        idocBorrado=
        idocImpresion = false;

        gruposAsignados = new String[0];

        listaAplicaciones = cargarListaAplicaciones();

    }



    public String getIdCert() {
        return idCert;
    }
    public void setIdCert(String idCert) {
        this.idCert = idCert;
    }
    /**
     * @return Returns the gruposAsignados.
     */
    public String[] getGruposAsignados() {
        return gruposAsignados;
    }
    /**
     * @param gruposAsignados The gruposAsignados to set.
     */
    public void setGruposAsignados(String[] gruposAsignados) {
        this.gruposAsignados = gruposAsignados;
    }
    /**
     * @return Returns the pwdmbc.
     */
    public boolean isPwdmbc() {
        return pwdmbc;
    }
    /**
     * @param pwdmbc The pwdmbc to set.
     */
    public void setPwdmbc(boolean pwdmbc) {
        this.pwdmbc = pwdmbc;
    }
    /**
     * @return Returns the pwdvpcheck.
     */
    public boolean isPwdvpcheck() {
        return pwdvpcheck;
    }
    /**
     * @param pwdvpcheck The pwdvpcheck to set.
     */
    public void setPwdvpcheck(boolean pwdvpcheck) {
        this.pwdvpcheck = pwdvpcheck;
    }
    /**
     * @return Returns the descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion The descripcion to set.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the idoc.
     */
    public String getIdoc() {
        return idoc;
    }
    /**
     * @param idoc The idoc to set.
     */
    public void setIdoc(String idoc) {
        this.idoc = idoc;
    }
    /**
     * @return Returns the idocBorrado.
     */
    public boolean isIdocBorrado() {
        return idocBorrado;
    }
    /**
     * @param idocBorrado The idocBorrado to set.
     */
    public void setIdocBorrado(boolean idocBorrado) {
        this.idocBorrado = idocBorrado;
    }
    /**
     * @return Returns the idocConsulta.
     */
    public boolean isIdocConsulta() {
        return idocConsulta;
    }
    /**
     * @param idocConsulta The idocConsulta to set.
     */
    public void setIdocConsulta(boolean idocConsulta) {
        this.idocConsulta = idocConsulta;
    }
    /**
     * @return Returns the idocCreacion.
     */
    public boolean isIdocCreacion() {
        return idocCreacion;
    }
    /**
     * @param idocCreacion The idocCreacion to set.
     */
    public void setIdocCreacion(boolean idocCreacion) {
        this.idocCreacion = idocCreacion;
    }
    /**
     * @return Returns the idocImpresion.
     */
    public boolean isIdocImpresion() {
        return idocImpresion;
    }
    /**
     * @param idocImpresion The idocImpresion to set.
     */
    public void setIdocImpresion(boolean idocImpresion) {
        this.idocImpresion = idocImpresion;
    }
    /**
     * @return Returns the idocModificacion.
     */
    public boolean isIdocModificacion() {
        return idocModificacion;
    }
    /**
     * @param idocModificacion The idocModificacion to set.
     */
    public void setIdocModificacion(boolean idocModificacion) {
        this.idocModificacion = idocModificacion;
    }
    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * @return Returns the pwd.
     */
    public String getPwd() {
        return pwd;
    }
    /**
     * @param pwd The pwd to set.
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    /**
     * @return Returns the repwd.
     */
    public String getRepwd() {
        return repwd;
    }
    /**
     * @param repwd The repwd to set.
     */
    public void setRepwd(String repwd) {
        this.repwd = repwd;
    }
    /**
     * @return Returns the systemSuperuser.
     */
    public boolean isSystemSuperuser() {
        return systemSuperuser;
    }
    /**
     * @param systemSuperuser The systemSuperuser to set.
     */
    public void setSystemSuperuser(boolean systemSuperuser) {
        this.systemSuperuser = systemSuperuser;
    }
    /**
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @return Returns the volumeSuperuser.
     */
    public boolean isVolumeSuperuser() {
        return volumeSuperuser;
    }
    /**
     * @param volumeSuperuser The volumeSuperuser to set.
     */
    public void setVolumeSuperuser(boolean volumeSuperuser) {
        this.volumeSuperuser = volumeSuperuser;
    }



	public Collection getListaAplicaciones() {
		return listaAplicaciones;
	}



	public void setListaAplicaciones(Collection listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}
	
	

	public String getCargo() {
		return cargo;
	}



	public void setCargo(String cargo) {
		this.cargo = cargo;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTfnoMovil() {
		return tfnoMovil;
	}



	public void setTfnoMovil(String tfnoMovil) {
		this.tfnoMovil = tfnoMovil;
	}
	


	/**
	 * @return el nombrePersonal
	 */
	public String getNombrePersonal() {
		return nombrePersonal;
	}



	/**
	 * @param nombrePersonal el nombrePersonal a fijar
	 */
	public void setNombrePersonal(String nombrePersonal) {
		this.nombrePersonal = nombrePersonal;
	}



	/**
	 * @return el apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}


	/**
	 * @param apellidos el apellidos a fijar
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	private ArrayList cargarListaAplicaciones(){
		ArrayList aplicaciones=new ArrayList();
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_TRAMITES,"Catalogo de tramites",UserDefs.PROFILE_NONE,true,false,true));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_PROCEDIMIENTOS,"Catalogo de procedimientos",UserDefs.PROFILE_STANDARD,false,false,false));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES,"Repositorios documentales",UserDefs.PROFILE_NONE,true,false,true));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_ESTRUCTURA_ORGANIZATIVA,"Estructura Organizativa",UserDefs.PROFILE_NONE,true,false,true));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO,"Registro",UserDefs.PROFILE_NONE,false,false,false));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_USUARIOS_PORTAL,"Usuarios del portal",UserDefs.PROFILE_NONE,true,false,true));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_ARCHIVO,"Archivo",UserDefs.PROFILE_STANDARD,false,false,false));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_CONSULTA_EXPEDIENTES,"Consulta total de expedientes",UserDefs.PROFILE_NONE,true,false,true));
		aplicaciones.add(new AplicacionPerfil(ConstantesGestionUsuariosAdministracion.APLICACION_CONSULTA_REGISTROS_TELEMATICOS,"Consulta total de registros telematicos",UserDefs.PROFILE_NONE,true,false,true));

		return aplicaciones;
	}

}
