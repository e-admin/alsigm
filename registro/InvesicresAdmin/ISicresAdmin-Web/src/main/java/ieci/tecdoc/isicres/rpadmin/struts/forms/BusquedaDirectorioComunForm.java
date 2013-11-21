package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;

public class BusquedaDirectorioComunForm extends RPAdminActionForm {
    private static final long serialVersionUID = 1L;


    //CAMPOS DEL VO

    private String codigo;
    private String nombre;


    private String codEntity;

    public Criterios<CriterioOficinaEnum> setForOficinas(Criterios<CriterioOficinaEnum> criterios)
    {
        if(nombre!=null && !"".equals(nombre))
        {
	        Criterio<CriterioOficinaEnum> criterioNombreOficina = new Criterio<CriterioOficinaEnum>();
	        criterioNombreOficina.setNombre(CriterioOficinaEnum.OFICINA_NOMBRE);
	        criterioNombreOficina.setOperador(OperadorCriterioEnum.LIKE);
	        criterioNombreOficina.setValor(getNombre());
	        criterios.addCriterio(criterioNombreOficina);
        }
        if(codigo!=null && !"".equals(codigo))
        {
	        Criterio<CriterioOficinaEnum> criterioCodigo = new Criterio<CriterioOficinaEnum>();
	        criterioCodigo.setNombre(CriterioOficinaEnum.OFICINA_ID);
	        criterioCodigo.setOperador(OperadorCriterioEnum.LIKE);
	        criterioCodigo.setValor(getCodigo());
	        criterios.addCriterio(criterioCodigo);
        }

        return criterios;
    }

    public Criterios<CriterioUnidadOrganicaEnum> setForUnidadOrganica(Criterios<CriterioUnidadOrganicaEnum> criterios)
    {
        if(nombre!=null && !"".equals(nombre))
        {
	        Criterio<CriterioUnidadOrganicaEnum> criterioNombreOficina = new Criterio<CriterioUnidadOrganicaEnum>();
	        criterioNombreOficina.setNombre(CriterioUnidadOrganicaEnum.UO_NOMBRE);
	        criterioNombreOficina.setOperador(OperadorCriterioEnum.LIKE);
	        criterioNombreOficina.setValor(getNombre());
	        criterios.addCriterio(criterioNombreOficina);
        }
        if(codigo!=null && !"".equals(codigo))
        {
	        Criterio<CriterioUnidadOrganicaEnum> criterioCodigo = new Criterio<CriterioUnidadOrganicaEnum>();
	        criterioCodigo.setNombre(CriterioUnidadOrganicaEnum.UO_ID);
	        criterioCodigo.setOperador(OperadorCriterioEnum.LIKE);
	        criterioCodigo.setValor(getCodigo());
	        criterios.addCriterio(criterioCodigo);
        }

        return criterios;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodEntity() {
		return codEntity;
	}

	public void setCodEntity(String codEntity) {
		this.codEntity = codEntity;
	}

	@Override
    public String[] getAttrsToUpper() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void toUpperCase(HttpServletRequest request)
            throws IllegalAccessException, InvocationTargetException {
        // TODO Auto-generated method stub

    }




}
