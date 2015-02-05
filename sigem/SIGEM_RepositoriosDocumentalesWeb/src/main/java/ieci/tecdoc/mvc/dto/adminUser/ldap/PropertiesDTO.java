/*
 * Created on 07-mar-2005
 *
 */
package ieci.tecdoc.mvc.dto.adminUser.ldap;

import java.util.Map;

/**
 * @author Antonio María
 *
 */
public class PropertiesDTO {
    String dn;
    boolean esUsuario;
    boolean esGrupo;
    /**
     * @return Returns the esGrupo.
     */
    public boolean isEsGrupo() {
        return esGrupo;
    }
    /**
     * @param esGrupo The esGrupo to set.
     */
    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }
    Map attsMap;
    boolean dadoDeAlta;
    String valorGuid;
    String nombreAtributo;
    String valorAtributo;
    
    

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
    /**
     * @return Returns the valorGuid.
     */
    public String getValorGuid() {
        return valorGuid;
    }
    /**
     * @param valorGuid The valorGuid to set.
     */
    public void setValorGuid(String valorGuid) {
        this.valorGuid = valorGuid;
    }
    /**
     * @return Returns the dadoDeAlta.
     */
    public boolean isDadoDeAlta() {
        return dadoDeAlta;
    }
    /**
     * @param dadoDeAlta The dadoDeAlta to set.
     */
    public void setDadoDeAlta(boolean dadoDeAlta) {
        this.dadoDeAlta = dadoDeAlta;
    }
    /**
     * @return Returns the attsMap.
     */
    public Map getAttsMap() {
        return attsMap;
    }
    /**
     * @param attsMap The attsMap to set.
     */
    public void setAttsMap(Map attsMap) {
        this.attsMap = attsMap;
    }
    /**
     * @return Returns the esUsuario.
     */
    public boolean isEsUsuario() {
        return esUsuario;
    }
    /**
     * @param esUsuario The esUsuario to set.
     */
    public void setEsUsuario(boolean esUsuario) {
        this.esUsuario = esUsuario;
    }
    /**
     * @return Returns the dn.
     */
    public String getDn() {
        return dn;
    }
    /**
     * @param dn The dn to set.
     */
    public void setDn(String dn) {
        this.dn = dn;
    }
}
