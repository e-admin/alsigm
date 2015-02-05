package ieci.tecdoc.sgm.rpadmin.beans.adapter;

import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
/**
 * Clase que adapta los VO de SIGEM a VO de ISicres
 *
 * @author IECISA
 *
 */
public class AdapterVOSicres {

	private static Logger _logger = Logger.getLogger(AdapterVOSicres.class);
	/**
	 * Adapta VO Archive de SIGEM a ISicres
	 * @param archive - VO de SIGEM
	 * @return Archive - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Archive adapterISicresArchive(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive archive) {

		es.ieci.tecdoc.isicres.admin.estructura.beans.Archive result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Archive();
		try {
			if(archive != null){
				BeanUtils.copyProperties(result, archive);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresArchive", e);
		}
		return result;
	}

	/**
	 * Transforma VO SicresListaDistribucionesImpl de SIGEM a ISICRES
	 * @param sicresListaDistribucionesImpl - VO SIGE
	 * @return sicresListaDistribucionesImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl adapterISicresListaDistribucionesImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl sicresListaDistribucionesImpl) {

		es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl();

		try {
			if(sicresListaDistribucionesImpl != null){
				for(Iterator it = sicresListaDistribucionesImpl.getLista().iterator();it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl auxSicresListaDistribucionImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl) it.next();
					result.add(adapterISicresListaDistribucionImpl(auxSicresListaDistribucionImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresListaDistribucionesImpl", e);
		}

		return result;
	}

	/**
	 * Adapta el VO SicresListaDistribucionImpl de SIGEM a ISicres
	 * @param sicresListaDistribucionImpl - VO SIGEM
	 * @return sicresListaDistribucionImpl- VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl adapterISicresListaDistribucionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl sicresListaDistribucionImpl){

		es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl();
		try{
			if(sicresListaDistribucionImpl!=null){
				BeanUtils.copyProperties(result, sicresListaDistribucionImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresListaDistribucionImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO de SIGEM a SICRES
	 * @param informesBean - VO SIGEM
	 * @return informesBean - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.InformesBean adapterISicresInformesBean(
			ieci.tecdoc.sgm.core.services.rpadmin.InformesBean informesBean) {

		es.ieci.tecdoc.isicres.admin.beans.InformesBean result = new es.ieci.tecdoc.isicres.admin.beans.InformesBean();

		try {
			if(informesBean != null){
				for(Iterator it = informesBean.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.rpadmin.InformeBean auxInformeBean = (ieci.tecdoc.sgm.core.services.rpadmin.InformeBean)it.next();
					result.add(adapterISicresInformeBean(auxInformeBean));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresInformesBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO de SIGEM a SICRES
	 * @param informeBean - VO de SIGEM
	 * @return informeBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.InformeBean adapterISicresInformeBean(
			ieci.tecdoc.sgm.core.services.rpadmin.InformeBean informeBean) {

		es.ieci.tecdoc.isicres.admin.beans.InformeBean result = new es.ieci.tecdoc.isicres.admin.beans.InformeBean();

		try {
			if(informeBean != null){
				BeanUtils.copyProperties(result, informeBean);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresInformeBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OptionBean de SIGEM a SICRES
	 * @param optionBean - VO de SIGEM
	 * @return optionBean - VO de SICRES
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.OptionBean adapterISicresOptionBean(
			ieci.tecdoc.sgm.core.services.rpadmin.OptionBean optionBean) {
		es.ieci.tecdoc.isicres.admin.beans.OptionBean result = new es.ieci.tecdoc.isicres.admin.beans.OptionBean();

		try {
			if(optionBean != null){
				BeanUtils.copyProperties(result, optionBean);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresOptionBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OptionsBean de SIGEM a SICRES
	 * @param optionsBean - VO de SIGEM
	 * @return optionsBean - VO de SICRES
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.OptionsBean adapterISicresOptionsBean(
			ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean optionsBean) {
		es.ieci.tecdoc.isicres.admin.beans.OptionsBean result = new es.ieci.tecdoc.isicres.admin.beans.OptionsBean();

		try {
			if(optionsBean != null){
				for(Iterator it = optionsBean.getLista().iterator();it.hasNext();){
					ieci.tecdoc.sgm.core.services.rpadmin.OptionBean auxOptionBean = (ieci.tecdoc.sgm.core.services.rpadmin.OptionBean)it.next();
					result.add(adapterISicresOptionBean(auxOptionBean));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresOptionsBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OficinaInformeBean de SIGEM a ISicres
	 * @param oficinaInformeBean - VO de SIGEM
	 * @return oficinaInformeBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean adapterISicresOficinaInformeBean(
			ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean oficinaInformeBean) {
		es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean result = new es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean();

		try {
			if(oficinaInformeBean != null){
				BeanUtils.copyProperties(result, oficinaInformeBean);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresOficinaInformeBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OficinasInformeBean de SIGEM a ISicres
	 * @param oficinasInformeBean - VO de SIGEM
	 * @return oficinasInformeBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean adapterISicresOficinasInformeBean(
			ieci.tecdoc.sgm.core.services.rpadmin.OficinasInformeBean oficinasInformeBean) {
		es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean result = new es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean();

		try {
			if(oficinasInformeBean != null){
				for(Iterator it = oficinasInformeBean.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean auxOficinaInformeBean = (ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean)it.next();
					result.add(adapterISicresOficinaInformeBean(auxOficinaInformeBean));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresOficinasInformeBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO IDocArchsHDRImpl de SIGEM a ISicres
	 * @param iDocArchsHDRImpl - VO de SIGEM
	 * @return iDocArchsHDRImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl adapterISicresIDocArchsHDRImpl(
			ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl iDocArchsHDRImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl();

		try {
			if(iDocArchsHDRImpl != null){
				for(Iterator it = iDocArchsHDRImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl auxIDocArchHDRImpl = (ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl) it.next();
					result.add(adapterISicresIDocArchHDRImpl(auxIDocArchHDRImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresIDocArchsHDRImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IDocArchHDRImpl de SIGEM a ISicres
	 * @param iDocArchHDRImpl - VO IDocArchHDRImpl de SIGEM
	 * @return IDocArchHDRImpl - VO IDocArchHDRImpl de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl adapterISicresIDocArchHDRImpl(
			ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl iDocArchHDRImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl();

		try {
			if(iDocArchHDRImpl != null){
				BeanUtils.copyProperties(result, iDocArchHDRImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresIDocArchHDRImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresLibroEstadoImpl de SIGEM a ISicres
	 * @param sicresLibroEstadoImpl - VO de SIGES
	 * @return sicresLibroEstadoImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl adapterISicresSicresLibroEstadoImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl sicresLibroEstadoImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl();

		try {
			if(sicresLibroEstadoImpl != null){
				BeanUtils.copyProperties(result, sicresLibroEstadoImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresLibroEstadoImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IVolArchListImpl de SIGEM a ISicres
	 * @param iVolArchListImpl - VO de SIGEM
	 * @return iVolArchListImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl adapterISicresIVolArchListImpl(
			ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl iVolArchListImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl();

		try {
			if(iVolArchListImpl != null){
				BeanUtils.copyProperties(result, iVolArchListImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresIVolArchListImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IUserObjPermImpl de SIGEM a ISicres
	 * @param iUserObjPermImpl - VO de SIGEM
	 * @return iUserObjPermImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl adapterISicresIUserObjPermImpl(
			ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl iUserObjPermImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl();

		try {
			if(iUserObjPermImpl != null){
				BeanUtils.copyProperties(result, iUserObjPermImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresIUserObjPermsImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IUserObjsPermsImpl de SIGEM a ISicres
	 * @param iUserObjsPermsImpl - VO SIGEM
	 * @return  iUserObjsPermsImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl adapterISicresIUserObjsPermsImpl(
			ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl iUserObjsPermsImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl();

		try {
			if(iUserObjsPermsImpl != null){
				for(Iterator it = iUserObjsPermsImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl auxIUserObjPermImpl = (ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl) it.next();
					result.add(adapterISicresIUserObjPermImpl(auxIUserObjPermImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresIUserObjsPermsImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresContadorOficinaImpl de SIGEM a ISicres
	 * @param sicresContadorOficinaImpl - VO de SIGEM
	 * @return sicresContadorOficinaImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl adapterISicresSicresContadorOficinaImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl sicresContadorOficinaImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl();

		try {
			if(sicresContadorOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresContadorOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresContadorOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresContadoresOficinasImpl de SIGEM a ISicres
	 * @param sicresContadoresOficinasImpl - VO de SIGEM
	 * @return sicresContadoresOficinasImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl adapterISicresSicresContadoresOficinasImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl sicresContadoresOficinasImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl();

		try {
			if(sicresContadoresOficinasImpl != null){
				for(Iterator it = sicresContadoresOficinasImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl auxSicresContadorOficinaImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl)it.next();
					result.add(adapterISicresSicresContadorOficinaImpl(auxSicresContadorOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresContadoresOficinasImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresContadorCentralImpl de SIGEM a ISicres
	 * @param sicresContadorCentralImpl - VO de SIGEM
	 * @return sicresContadorCentralImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl adapterISicresSicresContadorCentralImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl sicresContadorCentralImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl();

		try {
			if(sicresContadorCentralImpl != null){
				BeanUtils.copyProperties(result, sicresContadorCentralImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresContadorCentralImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresLibroOficinaImpl de SIGEM a ISicres
	 * @param sicresLibroOficinaImpl - VO de SIGEM
	 * @return sicresLibroOficinaImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl adapterISicresSicresLibroOficinaImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl sicresLibroOficinaImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl();

		try {
			if(sicresLibroOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresLibroOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresLibroOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresLibrosOficinasImpl de SIGEM a ISicres
	 * @param sicresLibrosOficinasImpl - VO de SIGEM
	 * @return sicresLibrosOficinasImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl adapterISicresSicresLibrosOficinasImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl sicresLibrosOficinasImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl();

		try {
			if(sicresLibrosOficinasImpl != null){
				for(Iterator it = sicresLibrosOficinasImpl.getLista().iterator();it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl auxSicresLibroOficinaImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl)it.next();
					result.add(adapterISicresSicresLibroOficinaImpl(auxSicresLibroOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresLibrosOficinasImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO FiltroImpl de SIGEM a ISicres
	 * @param filtroImpl - VO de SIGEM
	 * @return filtroImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl adapterISicresFiltroImpl(
			ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl filtroImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl();

		try {
			if(filtroImpl != null){
				BeanUtils.copyProperties(result, filtroImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresFiltroImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO FiltrosImpl de SIGEM a ISicres
	 * @param filtrosImpl - VO de SIGEM
	 * @return filtrosImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl adapterISicresFiltrosImpl(
			ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl filtrosImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl();

		try {
			if(filtrosImpl != null){
				for(Iterator it = filtrosImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl auxFiltroImpl = (ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl)it.next();
					result.add(adapterISicresFiltroImpl(auxFiltroImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresFiltrosImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO ArchiveFld de SIGEM a ISicres
	 * @param archiveFld - VO SIGEM
	 * @return archiveFld - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld adapterISicresArchiveFld(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld archiveFld) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld result = new es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld();
		try {
			if(archiveFld != null){
				BeanUtils.copyProperties(result, archiveFld);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresArchiveFld", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinasImpl de SIGEM a ISicres
	 * @param sicresOficinasImpl - VO SIGEM
	 * @return sicresOficinasImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl adapterISicresSicresOficinasImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl sicresOficinasImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl();
		try {
			if(sicresOficinasImpl != null){
				for(Iterator it = sicresOficinasImpl.getLista().iterator();it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl auxSicresOficinaImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl)it.next();
					result.add(adapterISicresSicresOficinaImpl(auxSicresOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresOficinasImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinaImpl de SIGEM a ISicres
	 * @param sicresOficinaImpl - VO SIGEM
	 * @return sicresOficinaImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl adapterISicresSicresOficinaImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl sicresOficinaImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl();
		try {
			if(sicresOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresOficinaImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresOficinaImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinaLocalizacionImpl de SIGEM a ISicres
	 * @param sicresOficinaLocalizacionImpl - VO SIGEM
	 * @return sicresOficinaLocalizacionImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl adapterISicresSicresOficinaLocalizacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl sicresOficinaLocalizacionImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl();

		try {
			if(sicresOficinaLocalizacionImpl != null){
				BeanUtils.copyProperties(result, sicresOficinaLocalizacionImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresOficinaLocalizacionImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresTipoOficinaImpl de SIGEM a ISicres
	 * @param sicresTipoOficinaImpl - VO SIGEM
	 * @return SicresTipoOficinaImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl adapterISicresSicresTipoOficinaImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl sicresTipoOficinaImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl();

		try {
			if(sicresTipoOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresTipoOficinaImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresTipoOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresTiposOficinaImpl de SIGEM a ISicres
	 * @param sicresTiposOficinaImpl - VO SIGEM
	 * @return SicresTiposOficinaImpl - VO ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl adapterISicresSicresTiposOficinaImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl sicresTiposOficinaImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl();

		try {
			if(sicresTiposOficinaImpl != null){
				for(Iterator it = sicresTiposOficinaImpl.getLista().iterator();it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl auxSicresTipoOficinaImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl)it.next();
					result.add(adapterISicresSicresTipoOficinaImpl(auxSicresTipoOficinaImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresTiposOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO Departamentos de SIGEM a ISicres
	 * @param departamentos - VO de SIGEM
	 * @return Departamentos - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos adapterISicresDepartamentos(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos departamentos) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos();
		try {
			if(departamentos != null){
				for (int i = 0; i < departamentos.getDepartamentosLista().count(); i++){
					ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento auxDepartamento = departamentos.getDepartamentosLista().get(i);
					result.getDepartamentosLista().add(adapterISicresDepartamento(auxDepartamento));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresDepartamentos", e);
		}
		return result;

	}

	/**
	 * Adapta VO Departamento de SIGEM a ISicres
	 * @param departamento - VO de SIGEM
	 * @return Departamento - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento adapterISicresDepartamento(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento departamento) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento();
		try {
			if(departamento != null){
				BeanUtils.copyProperties(result, departamento);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresDepartamento", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOrganizacionesImpl de SIGEM a ISicres
	 * @param sicresOrganizacionesImpl - VO de SIGEM
	 * @return SicresOrganizacionesImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl adapterISicresSicresOrganizacionesImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl sicresOrganizacionesImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl();
		try {
			if(sicresOrganizacionesImpl != null){
				for(Iterator it = sicresOrganizacionesImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl auxSicresOrganizacionImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl)it.next();
					result.add(adapterISicresSicresOrganizacionImpl(auxSicresOrganizacionImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresOrganizacionesImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOrganizacionImpl de SIGEM a ISicres
	 * @param sicresOrganizacionImpl - VO de SIGEM
	 * @return SicresOrganizacionImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl adapterISicresSicresOrganizacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl sicresOrganizacionImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl();
		try {
			if(sicresOrganizacionImpl != null){
				BeanUtils.copyProperties(result, sicresOrganizacionImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresOrganizacionImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOrganizacionLocalizacionImpl de SIGEM a ISicres
	 * @param sicresOrganizacionLocalizacionImpl - VO de SIGEM
	 * @return SicresOrganizacionLocalizacionImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl adapterISicresSicresOrganizacionLocalizacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl sicresOrganizacionLocalizacionImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl();
		try {
			if (sicresOrganizacionLocalizacionImpl != null) {
				BeanUtils.copyProperties(result,
						sicresOrganizacionLocalizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresOrganizacionLocalizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresTiposOrganizacionesImpl de SIGEM a ISicres
	 * @param sicresTiposOrganizacionesImpl - VO de SIGEM
	 * @return SicresTiposOrganizacionesImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl adapterISicresSicresTiposOrganizacionesImpl(ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl sicresTiposOrganizacionesImpl){
		es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl();
		try {
			if(sicresTiposOrganizacionesImpl != null){
				for (Iterator it = sicresTiposOrganizacionesImpl.getLista()
						.iterator(); it.hasNext();) {
					ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl auxSicresTipoOrganizacionImpl = (ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl) it
							.next();
					result.add(adapterISicresSicresTipoOrganizacionImpl(auxSicresTipoOrganizacionImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterISicresSicresTiposOrganizacionesImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresTipoOrganizacionImpl de SIGEM a ISicres
	 * @param sicresTipoOrganizacionImpl - VO de SIGEM
	 * @return SicresTipoOrganizacionImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl adapterISicresSicresTipoOrganizacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl sicresTipoOrganizacionImpl) {

		es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl();
		try {
			if (sicresTipoOrganizacionImpl != null) {
				BeanUtils.copyProperties(result, sicresTipoOrganizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresTipoOrganizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TiposAsuntoBean de SIGEM a ISicres
	 * @param tiposAsuntoBean - VO de SIGEM
	 * @return TiposAsuntoBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean adapterISicresTiposAsuntoBean(
			ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean tiposAsuntoBean) {
		es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean result = new es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean();
		try {
			if (tiposAsuntoBean != null) {
				for(Iterator it = tiposAsuntoBean.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean auxTipoAsuntoBean = (ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean)it.next();
					result.add(adapterISicresTipoAsuntoBean(auxTipoAsuntoBean));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresTiposAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TipoAsuntoBean de SIGEM a ISicres
	 * @param tipoAsuntoBean - VO de SIGEM
	 * @return TipoAsuntoBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean adapterISicresTipoAsuntoBean(
			ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean tipoAsuntoBean) {
		es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean result = new es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean();
		try {
			if (tipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, tipoAsuntoBean);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO DocumentoTipoAsuntoBean de SIGEM a ISicres
	 * @param documentoTipoAsuntoBean - VO de SIGEM
	 * @return DocumentoTipoAsuntoBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean adapterISicresDocumentoTipoAsuntoBean(
			ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean documentoTipoAsuntoBean) {
		es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean result = new es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean();
		try {
			if (documentoTipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, documentoTipoAsuntoBean);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresDocumentoTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO OficinaTipoAsuntoBean de SIGEM a ISicres
	 * @param oficinaTipoAsuntoBean - VO de SIGEM
	 * @return OficinaTipoAsuntoBean - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean adapterISicresOficinaTipoAsuntoBean(
			ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean oficinaTipoAsuntoBean) {
		es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean result = new es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean();
		try {
			if (oficinaTipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, oficinaTipoAsuntoBean);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresOficinaTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TipoAsunto de SIGEM a ISicres
	 * @param tipoAsunto - VO de SIGEM
	 * @return TipoAsunto - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.TipoAsunto adapterISicresTipoAsunto(
			ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto tipoAsunto) {
		es.ieci.tecdoc.isicres.admin.beans.TipoAsunto result = new es.ieci.tecdoc.isicres.admin.beans.TipoAsunto();
		try {
			if (tipoAsunto != null) {
				BeanUtils.copyProperties(result, tipoAsunto);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresTipoAsunto",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Transportes de SIGEM a ISicres
	 * @param transportes - VO de SIGEM
	 * @return Transportes - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.Transportes adapterISicresTransportes(
			ieci.tecdoc.sgm.core.services.rpadmin.Transportes transportes) {
		es.ieci.tecdoc.isicres.admin.beans.Transportes result = new es.ieci.tecdoc.isicres.admin.beans.Transportes();
		try {
			if (transportes != null) {
				for(Iterator it = transportes.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.rpadmin.Transporte auxTransporte = (ieci.tecdoc.sgm.core.services.rpadmin.Transporte)it.next();
					result.add(adapterISicresTransporte(auxTransporte));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresTransportes",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO Transporte de SIGEM a ISicres
	 * @param transporte - VO de SIGEM
	 * @return Transporte - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.Transporte adapterISicresTransporte(
			ieci.tecdoc.sgm.core.services.rpadmin.Transporte transporte) {
		es.ieci.tecdoc.isicres.admin.beans.Transporte result = new es.ieci.tecdoc.isicres.admin.beans.Transporte();
		try {
			if (transporte != null) {
				BeanUtils.copyProperties(result, transporte);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresTransporte",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO ListaUsuariosImpl de SIGEM a ISicres
	 * @param listaUsuariosImpl - VO de SIGEM
	 * @return ListaUsuariosImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl adapterISicresListaUsuariosImpl(
			ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl listadoUsuariosImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl();

		try {
			if (listadoUsuariosImpl != null) {
				for(Iterator it = listadoUsuariosImpl.getLista().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl auxListaUsuarioImpl = (ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl)it.next();
					result.add(adapterISicresListaUsuarioImpl(auxListaUsuarioImpl));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresListaUsuariosImpl",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO ListaUsuarioImpl de SIGEM a ISicres
	 * @param listaUsuarioImpl - VO de SIGEM
	 * @return ListaUsuarioImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl adapterISicresListaUsuarioImpl(
			ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl listadoUsuarioImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl();

		try {
			if (listadoUsuarioImpl != null) {
				BeanUtils.copyProperties(result, listadoUsuarioImpl);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresListaUsuarioImpl",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO UsuariosLdap de SIGEM a ISicres
	 * @param usuariosLdap - VO de SIGEM
	 * @return UsuariosLdap - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap adapterISicresUsuariosLdap(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap usuariosLdap) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap result = new es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap();
		try {
			if (usuariosLdap != null) {
				for(Iterator it = usuariosLdap.get_list().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap auxUsuarioLdap = (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap)it.next();
					result.add(adapterISicresUsuarioLdap(auxUsuarioLdap));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresUsuariosLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Usuarios de SIGEM a ISicres
	 * @param usuarios - VO de SIGEM
	 * @return Usuarios - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios adapterISicresUsuarios(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios usuarios) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios();
		try {
			if (usuarios != null) {
				for(Iterator it = usuarios.get_list().iterator(); it.hasNext();){
					ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario auxUsuario = (ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario)it.next();
					result.add(adapterISicresUsuario(auxUsuario));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresUsuarios",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserPermisosImpl de SIGEM a ISicres
	 * @param sicresUserPermisosImpl - VO de SIGEM
	 * @return SicresUserPermisosImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl adapterISicresSicresUserPermisosImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl sicresUserPermisosImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl();
		try {
			if (sicresUserPermisosImpl != null) {
				BeanUtils.copyProperties(result, sicresUserPermisosImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresUserPermisosImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserIdentificacionImpl de SIGEM a ISicres
	 * @param sicresUserIdentificacionImpl - VO de SIGEM
	 * @return SicresUserIdentificacionImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl adapterISicresSicresUserIdentificacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl sicresUserIdentificacionImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl();
		try {
			if (sicresUserIdentificacionImpl != null) {
				BeanUtils.copyProperties(result, sicresUserIdentificacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresUserIdentificacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserLocalizacionImpl de SIGEM a ISicres
	 * @param sicresUserLocalizacionImpl - VO de SIGEM
	 * @return SicresUserLocalizacionImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl adapterISicresSicresUserLocalizacionImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl sicresUserLocalizacionImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl();
		try {
			if (sicresUserLocalizacionImpl != null) {
				BeanUtils.copyProperties(result, sicresUserLocalizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresUserLocalizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Usuario de SIGEM a ISicres
	 * @param usuario - VO de SIGEM
	 * @return Usuario - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario adapterISicresUsuario(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario usuario) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario();
		try {
			if (usuario != null) {
				BeanUtils.copyProperties(result, usuario);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresUsuario",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO UsuarioLdap de SIGEM a ISicres
	 * @param usuarioLdap - VO de SIGEM
	 * @return UsuarioLdap - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap adapterISicresUsuarioLdap(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap usuarioLdap) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap result = new es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap();
		try {
			if (usuarioLdap != null) {
				BeanUtils.copyProperties(result, usuarioLdap);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresUsuarioLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Grupos de SIGEM a ISicres
	 * @param grupos - VO de SIGEM
	 * @return Grupos - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos adapterISicresGrupos(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos grupos) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos();
		try {
			if (grupos != null) {
				for(int i = 0; i < grupos.getGruposLista().count(); i++){
					ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo auxGrupo = grupos.getGruposLista().get(i);
					result.getGruposLista().add(adapterISicresGrupo(auxGrupo));
				}
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresGrupos",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Grupo de SIGEM a ISicres
	 * @param grupo - VO de SIGEM
	 * @return Grupo - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo adapterISicresGrupo(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo grupo) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo result = new es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo();
		try {
			if (grupo != null) {
				BeanUtils.copyProperties(result, grupo);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresGrupo",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO GrupoLdap de SIGEM a ISicres
	 * @param grupoLdap - VO de SIGEM
	 * @return GrupoLdap - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap adapterISicresGrupoLdap(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap grupoLdap) {
		es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap result = new es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap();
		try {
			if (grupoLdap != null) {
				BeanUtils.copyProperties(result, grupoLdap);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresGrupoLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUsuarioConfigImpl de SIGEM a ISicres
	 * @param sicresUsuarioConfigImpl - VO de SIGEM
	 * @return SicresUsuarioConfigImpl - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl adapterISicresSicresUsuarioConfigImpl(
			ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioConfigImpl sicresUsuarioConfigImpl) {
		es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl result = new es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl();
		try {
			if (sicresUsuarioConfigImpl != null) {
				BeanUtils.copyProperties(result, sicresUsuarioConfigImpl);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterISicresSicresUsuarioConfigImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Oficina de SIGEM a ISicres
	 * @param oficinaOficina - VO de SIGEM
	 * @return Oficina - VO de ISicres
	 */
	public static es.ieci.tecdoc.isicres.admin.beans.Oficina adapterISicresOficina(
			ieci.tecdoc.sgm.core.services.rpadmin.Oficina oficina) {
		es.ieci.tecdoc.isicres.admin.beans.Oficina result = new es.ieci.tecdoc.isicres.admin.beans.Oficina();
		try {
			if (oficina != null) {
				BeanUtils.copyProperties(result, oficina);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMOficina",
					e);
		}
		return result;
	}
}
