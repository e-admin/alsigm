/**
 * LocalgisMap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class Mapa {

	private Short mapdefault;
    private Integer mapid;
    private Integer mapidgeopista;
    private Integer mapidentidad;
    private Short mappublic;
    private Double maxx;
    private Double maxy;
    private Double minx;
    private Double miny;
    private String name;
    private String srid;

    public Mapa() {
    }

    public Mapa(
           Short mapdefault,
           Integer mapid,
           Integer mapidgeopista,
           Integer mapidentidad,
           Short mappublic,
           Double maxx,
           Double maxy,
           Double minx,
           Double miny,
           String name,
           String srid) {
           this.mapdefault = mapdefault;
           this.mapid = mapid;
           this.mapidgeopista = mapidgeopista;
           this.mapidentidad = mapidentidad;
           this.mappublic = mappublic;
           this.maxx = maxx;
           this.maxy = maxy;
           this.minx = minx;
           this.miny = miny;
           this.name = name;
           this.srid = srid;
    }


    /**
     * Gets the mapdefault value for this LocalgisMap.
     * 
     * @return mapdefault
     */
    public Short getMapdefault() {
        return mapdefault;
    }


    /**
     * Sets the mapdefault value for this LocalgisMap.
     * 
     * @param mapdefault
     */
    public void setMapdefault(Short mapdefault) {
        this.mapdefault = mapdefault;
    }


    /**
     * Gets the mapid value for this LocalgisMap.
     * 
     * @return mapid
     */
    public Integer getMapid() {
        return mapid;
    }


    /**
     * Sets the mapid value for this LocalgisMap.
     * 
     * @param mapid
     */
    public void setMapid(Integer mapid) {
        this.mapid = mapid;
    }


    /**
     * Gets the mapidgeopista value for this LocalgisMap.
     * 
     * @return mapidgeopista
     */
    public Integer getMapidgeopista() {
        return mapidgeopista;
    }


    /**
     * Sets the mapidgeopista value for this LocalgisMap.
     * 
     * @param mapidgeopista
     */
    public void setMapidgeopista(Integer mapidgeopista) {
        this.mapidgeopista = mapidgeopista;
    }


    /**
     * Gets the mapidentidad value for this LocalgisMap.
     * 
     * @return mapidentidad
     */
    public Integer getMapidentidad() {
        return mapidentidad;
    }


    /**
     * Sets the mapidentidad value for this LocalgisMap.
     * 
     * @param mapidentidad
     */
    public void setMapidentidad(Integer mapidentidad) {
        this.mapidentidad = mapidentidad;
    }


    /**
     * Gets the mappublic value for this LocalgisMap.
     * 
     * @return mappublic
     */
    public Short getMappublic() {
        return mappublic;
    }


    /**
     * Sets the mappublic value for this LocalgisMap.
     * 
     * @param mappublic
     */
    public void setMappublic(Short mappublic) {
        this.mappublic = mappublic;
    }


    /**
     * Gets the maxx value for this LocalgisMap.
     * 
     * @return maxx
     */
    public Double getMaxx() {
        return maxx;
    }


    /**
     * Sets the maxx value for this LocalgisMap.
     * 
     * @param maxx
     */
    public void setMaxx(Double maxx) {
        this.maxx = maxx;
    }


    /**
     * Gets the maxy value for this LocalgisMap.
     * 
     * @return maxy
     */
    public Double getMaxy() {
        return maxy;
    }


    /**
     * Sets the maxy value for this LocalgisMap.
     * 
     * @param maxy
     */
    public void setMaxy(Double maxy) {
        this.maxy = maxy;
    }


    /**
     * Gets the minx value for this LocalgisMap.
     * 
     * @return minx
     */
    public Double getMinx() {
        return minx;
    }


    /**
     * Sets the minx value for this LocalgisMap.
     * 
     * @param minx
     */
    public void setMinx(Double minx) {
        this.minx = minx;
    }


    /**
     * Gets the miny value for this LocalgisMap.
     * 
     * @return miny
     */
    public Double getMiny() {
        return miny;
    }


    /**
     * Sets the miny value for this LocalgisMap.
     * 
     * @param miny
     */
    public void setMiny(Double miny) {
        this.miny = miny;
    }


    /**
     * Gets the name value for this LocalgisMap.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name value for this LocalgisMap.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the srid value for this LocalgisMap.
     * 
     * @return srid
     */
    public String getSrid() {
        return srid;
    }


    /**
     * Sets the srid value for this LocalgisMap.
     * 
     * @param srid
     */
    public void setSrid(String srid) {
        this.srid = srid;
    }
}
