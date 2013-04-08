/**
 * ISOALocalGISPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.localgis.ln;

public interface ISOALocalGISPortType extends java.rmi.Remote {
    public java.lang.String altaNumerero(com.localgis.model.ot.NumeroOT in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.String insertBienPreAlta(com.localgis.model.ot.BienPreAltaOT in0) throws java.rmi.RemoteException;
    public com.localgis.model.ot.ArrayOfParcelaOT consultarCatastro(java.lang.String in0) throws java.rmi.RemoteException;
    public boolean validarReferencia(java.lang.String in0) throws java.rmi.RemoteException;
    public com.localgis.model.ot.ArrayOfProvinciaOT obtenerProvincias() throws java.rmi.RemoteException;
    public com.localgis.web.core.model.ArrayOfLocalgisMap verPlanosPublicados(int in0) throws java.rmi.RemoteException;
    public com.localgis.web.core.model.ArrayOfGeopistaMunicipio verMunicipiosPublicados() throws java.rmi.RemoteException;
    public java.lang.String modificacionCallejero(int in0, com.localgis.model.ot.CalleOT in1) throws java.rmi.RemoteException;
    public com.localgis.web.core.model.ArrayOfGeopistaColumn selectColumnsByLayerTranslated(int in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String bajaPOI(int in0) throws java.rmi.RemoteException;
    public java.lang.String altaCallejero(com.localgis.model.ot.CalleOT in0) throws java.rmi.RemoteException;
    public java.lang.String altaPOI(com.localgis.model.ot.PoiOT in0) throws java.rmi.RemoteException;
    public java.lang.String bajaCallejero(int in0, int in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.util.Calendar in5) throws java.rmi.RemoteException;
    public com.localgis.model.ot.URLsPlano verPlanoPorIdNumeroPolicia(int in0, int in1, int in2, int in3, int in4, int in5) throws java.rmi.RemoteException;
    public com.localgis.model.ot.ArrayOfCapaOT obtenerListaCapas(int in0) throws java.rmi.RemoteException;
    public com.localgis.ln.AnyType2AnyTypeMap getURLReportMap(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.Object in4, java.lang.String in5, int in6, int in7, java.lang.String in8, java.lang.String in9, java.lang.String in10, java.lang.String in11) throws java.rmi.RemoteException;
    public com.localgis.model.ot.URLsPlano verPlanoPorReferenciaCatastral(int in0, java.lang.String in1, int in2, int in3, int in4, int in5) throws java.rmi.RemoteException;
    public java.lang.String bajaNumerero(com.localgis.model.ot.NumeroOT in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public com.localgis.model.ot.ArrayOfMunicipioOT obtenerMunicipios(int in0) throws java.rmi.RemoteException;
    public com.localgis.web.core.model.ArrayOfLocalgisLayer selectLayersByIdMap(int in0) throws java.rmi.RemoteException;
    public java.lang.String modificacionNumerero(com.localgis.model.ot.NumeroOT in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public com.localgis.model.ot.ArrayOfTipoViaOT obtenerTiposDeVia() throws java.rmi.RemoteException;
    public com.localgis.model.ot.URLsPlano verPlanoPorIdVia(int in0, int in1, int in2, int in3, int in4, int in5) throws java.rmi.RemoteException;
    public com.localgis.model.ot.URLsPlano verPlanoPorCoordenadas(int in0, double in1, double in2, int in3, int in4, int in5, int in6) throws java.rmi.RemoteException;
}
