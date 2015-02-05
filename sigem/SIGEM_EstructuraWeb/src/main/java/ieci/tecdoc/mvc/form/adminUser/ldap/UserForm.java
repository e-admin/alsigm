/*
 * Created on 15-feb-2005
 *
 */
package ieci.tecdoc.mvc.form.adminUser.ldap;

import ieci.tecdoc.idoc.admin.api.user.AplicacionPerfil;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserForm extends ActionForm{

    int id;
    public String guid = null;

    String gruposAsignados[];

    Collection listaAplicaciones;

    public boolean systemSuperuser;
    /**
     * Valores posibles para user: superuser, standard, none
     */
    public String user;
    public String nombreAtributo;
    public String valorAtributo;

    String campoBusqueda = null;
    byte tipoBusqueda = -1;

    public String idoc;
    public boolean volumeSuperuser;

    // Permisos Genéricos
    boolean idocConsulta;
    boolean idocModificacion;
    boolean idocCreacion;
    boolean idocBorrado;
    boolean idocImpresion;

    public String idCert;



    public String getIdCert() {
        return idCert;
    }
    public void setIdCert(String idCert) {
        this.idCert = idCert;
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
     * @return Returns the tipoBusqueda.
     */
    public byte getTipoBusqueda() {
        return tipoBusqueda;
    }
    /**
     * @param tipoBusqueda The tipoBusqueda to set.
     */
    public void setTipoBusqueda(byte tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }
    /**
     * @return Returns the campoBusqueda.
     */
    public String getCampoBusqueda() {
        return campoBusqueda;
    }
    /**
     * @param campoBusqueda The campoBusqueda to set.
     */
    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }
    /**
     * @return Returns the nombreAtributo.
     */
    public String getNombreAtributo() {
        return nombreAtributo;
    }
    /**
     * @param nombreAtributo The nombreAtributo to set.
     */
    public void setNombreAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }
    /**
     * @return Returns the valorAtributo.
     */
    public String getValorAtributo() {
        return valorAtributo;
    }
    /**
     * @param valorAtributo The valorAtributo to set.
     */
    public void setValorAtributo(String valorAtributo) {
        this.valorAtributo = valorAtributo;
    }


    /*
    // Producto System
    boolean systemSuperuser;
    // Producto Administrador de usuarios
    boolean userSuperuser;
    boolean userManager;
    boolean userNone;
    // Producto InvesDoc
    boolean idocSuperuser;
    boolean idocManager;
    boolean idocStandard;
    boolean idocNone;
    // Producto Administrador de volúmenes
    boolean volumeSuperuser;
    */

    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
        // Es llamada la primera vez que ejecuta el action
        systemSuperuser =
        volumeSuperuser =
        idocConsulta =
        idocModificacion =
        idocCreacion =
        idocBorrado =
        idocImpresion = false;
        user =
        idoc = idCert = "";

        listaAplicaciones = cargarListaAplicaciones();
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
    /**
     * @return Returns the guid.
     */
    public String getGuid() {
        return guid;
    }
    /**
     * @param guid The guid to set.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

	public Collection getListaAplicaciones() {
		return listaAplicaciones;
	}

	public void setListaAplicaciones(Collection listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
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
