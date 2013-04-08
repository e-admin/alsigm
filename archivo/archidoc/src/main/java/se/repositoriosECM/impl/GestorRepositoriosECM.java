package se.repositoriosECM.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.IGestorRepositoriosECM;
import se.util.MapUtil;

import common.MultiEntityConstants;

import docelectronicos.vos.IRepositorioEcmVO;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public abstract class GestorRepositoriosECM implements IGestorRepositoriosECM {

    protected LinkedHashMap<String, IRepositorioEcmVO> mapRepositoriosEcm = null;
    protected List<IRepositorioEcmVO> listaRepositorioEcmVOs = null;
    protected List<IRepositorioEcmVO> listaRepositoriosEcmInternos = null;

    public abstract void initialize(Properties params);


	protected abstract Properties getParams();

    public void establecerEntidad(){
		if(getParams() != null){
	    	String entidad = (String)getParams().get(MultiEntityConstants.ENTITY_PARAM);
			MultiEntityContextHolder.setEntity(entidad);
		}
	}


    public IRepositorioEcmVO getRepositorioEcmById(String idRepEcm) {
        IRepositorioEcmVO repEcm = (IRepositorioEcmVO) getMapRepositoriosEcm()
                .get(idRepEcm);
        return repEcm;
    }

    @SuppressWarnings("unchecked")
    public List<IRepositorioEcmVO> getRepositoriosEcm() {
        if (listaRepositorioEcmVOs == null) {
            listaRepositorioEcmVOs = MapUtil.toList(getMapRepositoriosEcm());
        }
        return listaRepositorioEcmVOs;
    }

    protected void addRepositorio(String id, IRepositorioEcmVO repositorioEcm) {
        mapRepositoriosEcm.put(repositorioEcm.getId(), repositorioEcm);
    }

    public List<IRepositorioEcmVO> getRepositoriosEcmInternos()
            throws GestorFicherosException {
        if (listaRepositoriosEcmInternos == null) {
            listaRepositoriosEcmInternos = new ArrayList<IRepositorioEcmVO>();

            List<IRepositorioEcmVO> listaRepositorios = getRepositoriosEcm();

            for (Iterator iterator = listaRepositorios.iterator(); iterator
                    .hasNext();) {
                IRepositorioEcmVO iRepositorioEcmVO = (IRepositorioEcmVO) iterator
                        .next();

                if (iRepositorioEcmVO != null && iRepositorioEcmVO.isInterno()) {
                    listaRepositoriosEcmInternos.add(iRepositorioEcmVO);
                }
            }

        }
        return listaRepositoriosEcmInternos;
    }

    protected LinkedHashMap<String, IRepositorioEcmVO> getMapRepositoriosEcm() {
        return mapRepositoriosEcm;
    }

    protected void setMapRepositoriosEcm(
            LinkedHashMap<String, IRepositorioEcmVO> mapRepositoriosEcm) {
        this.mapRepositoriosEcm = mapRepositoriosEcm;
    }
}
