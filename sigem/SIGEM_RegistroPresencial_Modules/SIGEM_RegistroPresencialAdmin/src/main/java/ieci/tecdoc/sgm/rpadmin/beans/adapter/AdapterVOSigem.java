package ieci.tecdoc.sgm.rpadmin.beans.adapter;

import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * Clase que adapta los VO de ISicres a VO de SIGEM
 *
 * @author IECISA
 *
 */
public class AdapterVOSigem {
	private static Logger _logger = Logger.getLogger(AdapterVOSigem.class);

	/**
	 * Adapta VO Archive de ISicres a SIGEM
	 * @param archive - VO de ISicres
	 * @return Archive - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive adapterSIGEMArchive(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Archive archive) {

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive();
		try {
			if(archive != null){
				BeanUtils.copyProperties(result, archive);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMArchive", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresListaDistribucionesImpl de ISicres a SIGEM
	 * @param sicresListaDistribucionesImpl - VO ISicres
	 * @return sicresListaDistribucionesImpl - VO SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl adapterSIGEMListaDistribucionesImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl sicresListaDistribucionesImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl();
		try {
			if(sicresListaDistribucionesImpl != null){
				for(Iterator it = sicresListaDistribucionesImpl.getLista().iterator();it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl auxSicresListaDistribucionImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl) it.next();
					result.add(adapterSIGEMListaDistribucionImpl(auxSicresListaDistribucionImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMListaDistribucionesImpl", e);
		}

		return result;
	}



	/**
	 * Adapta el VO SicresListaDistribucionImpl de ISicres a SIGEM
	 * @param sicresListaDistribucionImpl - VO SIGEM
	 * @return sicresListaDistribucionImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl adapterSIGEMListaDistribucionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl sicresListaDistribucionImpl){

		ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl();
		try{
			if(sicresListaDistribucionImpl!=null){
				BeanUtils.copyProperties(result, sicresListaDistribucionImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMListaDistribucionImpl", e);
		}

		return result;

	}

	/**
	 * Adapta VO de Sicres a SIGEM
	 * @param informesBean - VO de ISicres
	 * @return informesBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.InformesBean adapterSIGEMInformesBean(
			es.ieci.tecdoc.isicres.admin.beans.InformesBean informesBean) {

		ieci.tecdoc.sgm.core.services.rpadmin.InformesBean result = new ieci.tecdoc.sgm.core.services.rpadmin.InformesBean();

		try {
			if(informesBean != null){
				for(Iterator it = informesBean.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.beans.InformeBean auxInformeBean = (es.ieci.tecdoc.isicres.admin.beans.InformeBean)it.next();
					result.add(adapterSIGEMInformeBean(auxInformeBean));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMInformesBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO de SICRES a SIGEM
	 * @param informeBean - VO de ISicres
	 * @return informeBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.InformeBean adapterSIGEMInformeBean(
			es.ieci.tecdoc.isicres.admin.beans.InformeBean informeBean) {

		ieci.tecdoc.sgm.core.services.rpadmin.InformeBean result = new ieci.tecdoc.sgm.core.services.rpadmin.InformeBean();

		try {
			if(informeBean != null){
				BeanUtils.copyProperties(result, informeBean);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMInformeBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OptionBean de SICRES a SIGEM
	 * @param optionBean - VO de ISicres
	 * @return optionBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.OptionBean adapterSIGEMOptionBean(
			es.ieci.tecdoc.isicres.admin.beans.OptionBean optionBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.OptionBean result = new ieci.tecdoc.sgm.core.services.rpadmin.OptionBean();

		try {
			if(optionBean != null){
				BeanUtils.copyProperties(result, optionBean);
			}
		} catch (Exception e) {

			_logger.error("Error al adaptar los objetos del método adapterSIGEMOptionBean", e);
		}

		return result;
	}



	/**
	 * Adapta VO OptionsBean de SICRES a SIGEM
	 * @param optionsBean - VO de ISicres
	 * @return optionsBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean adapterSIGEMOptionsBean(
			es.ieci.tecdoc.isicres.admin.beans.OptionsBean optionsBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean result = new ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean();

		try {
			if(optionsBean != null){
				for(Iterator it = optionsBean.getLista().iterator();it.hasNext();){
					es.ieci.tecdoc.isicres.admin.beans.OptionBean auxOptionBean = (es.ieci.tecdoc.isicres.admin.beans.OptionBean) it.next();
					result.add(adapterSIGEMOptionBean(auxOptionBean));
				}
			}
		} catch (Exception e) {

			_logger.error("Error al adaptar los objetos del método adapterSIGEMOptionsBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO OficinaInformeBean de ISicres a SIGEM
	 * @param oficinaInformeBean - VO de ISicres
	 * @return OficinaInformeBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean adapterSIGEMOficinaInformeBean(
			es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean oficinaInformeBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean result = new ieci.tecdoc.sgm.core.services.rpadmin.OficinaInformeBean();

		try {
			if(oficinaInformeBean != null){
				BeanUtils.copyProperties(result, oficinaInformeBean);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMOficinaInformeBean", e);
		}

		return result;
	}


	/**
	 * Adapta VO OficinasInformeBean de ISicres a SIGEM
	 * @param oficinasInformeBean - VO de ISicres
	 * @return OficinasInformeBean - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.OficinasInformeBean adapterSIGEMOficinasInformeBean(
			es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean oficinasInformeBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.OficinasInformeBean result = new ieci.tecdoc.sgm.core.services.rpadmin.OficinasInformeBean();

		try {
			if(oficinasInformeBean != null){
				for(Iterator it = oficinasInformeBean.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean auxOficinaInformeBean = (es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean) it.next();
					result.add(adapterSIGEMOficinaInformeBean(auxOficinaInformeBean));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMOficinasInformeBean", e);
		}

		return result;
	}

	/**
	 * Adapta VO IDocArchsHDRImpl de ISicres a SIGEM
	 * @param iDocArchsHDRImpl - ISicres
	 * @return iDocArchsHDRImpl - SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl adapterSIGEMIDocArchsHDRImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl iDocArchsHDRImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl result = new ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl();

		try {
			if(iDocArchsHDRImpl != null){
				for(Iterator it = iDocArchsHDRImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl auxIDocArchHDRImpl = (es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl) it.next();
					result.add(adapterSIGEMIDocArchHDRImpl(auxIDocArchHDRImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMIDocArchsHDRImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO IDocArchHDRImpl de ISicres a SIGEM
	 * @param iDocArchHDRImpl - VO IDocArchHDRImpl de ISicres
	 * @return IDocArchHDRImpl - VO IDocArchHDRImpl de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl adapterSIGEMIDocArchHDRImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl iDocArchHDRImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl result = new ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl();

		try {
			if(iDocArchHDRImpl != null){
				BeanUtils.copyProperties(result, iDocArchHDRImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMIDocArchHDRImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO SicresLibroEstadoImpl de ISicres a SIGEM
	 * @param sicresLibroEstadoImpl - VO de ISicres
	 * @return sicresLibroEstadoImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl adapterSIGEMSicresLibroEstadoImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl sicresLibroEstadoImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl();

		try {
			if(sicresLibroEstadoImpl != null){
				BeanUtils.copyProperties(result, sicresLibroEstadoImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresLibroEstadoImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO IVolArchListImpl de ISicres a SIGEM
	 * @param iVolArchListImpl - VO de ISicres
	 * @return iVolArchListImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl adapterSIGEMIVolArchListImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl iVolArchListImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl result = new ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl();

		try {
			if(iVolArchListImpl != null){
				BeanUtils.copyProperties(result, iVolArchListImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMIVolArchListImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IUserObjPermImpl de ISicres a SIGEM
	 * @param iUserObjPermImpl - VO de ISicres
	 * @return iUserObjPermImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl adapterSIGEMIUserObjPermImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl iUserObjPermImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl result = new ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl();

		try {
			if(iUserObjPermImpl != null){
				BeanUtils.copyProperties(result, iUserObjPermImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMIUserObjPermsImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO IUserObjsPermsImpl de ISicres a SIGEM
	 * @param iUserObjsPermsImpl - VO ISicres
	 * @return  iUserObjsPermsImpl - VO SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl adapterSIGEMIUserObjsPermsImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl iUserObjsPermsImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl result = new ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl();

		try {
			if(iUserObjsPermsImpl != null){
				for(Iterator it = iUserObjsPermsImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl auxIUserObjPermImpl = (es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl) it.next();
					result.add(adapterSIGEMIUserObjPermImpl(auxIUserObjPermImpl));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMIUserObjsPermsImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO SicresContadorOficinaImpl de ISicres a SIGEM
	 * @param sicresContadorOficinaImpl - VO de ISicres
	 * @return sicresContadorOficinaImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl adapterSIGEMSicresContadorOficinaImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl sicresContadorOficinaImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl();

		try {
			if(sicresContadorOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresContadorOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresContadorOficinaImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO SicresContadoresOficinasImpl de ISicres a SIGEM
	 * @param sicresContadoresOficinasImpl - VO de ISicres
	 * @return sicresContadoresOficinasImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl adapterSIGEMSicresContadoresOficinasImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl sicresContadoresOficinasImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl();

		try {
			if(sicresContadoresOficinasImpl != null){
				for(Iterator it = sicresContadoresOficinasImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl auxSicresContadorOficinaImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl)it.next();
					result.add(adapterSIGEMSicresContadorOficinaImpl(auxSicresContadorOficinaImpl));

				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresContadoresOficinasImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresContadorCentralImpl de ISicres a SIGEM
	 * @param sicresContadorCentralImpl - VO de ISIcres
	 * @return sicresContadorCentralImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl adapterSIGEMSicresContadorCentralImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl sicresContadorCentralImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl();

		try {
			if(sicresContadorCentralImpl != null){
				BeanUtils.copyProperties(result, sicresContadorCentralImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresContadorCentralImpl", e);
		}

		return result;
	}


	/**
	 * Adapta VO SicresLibroOficinaImpl de ISicres a SIGEM
	 * @param sicresLibroOficinaImpl - VO de ISIcres
	 * @return sicresLibroOficinaImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl adapterSIGEMSicresLibroOficinaImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl sicresLibroOficinaImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl();

		try {
			if(sicresLibroOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresLibroOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresLibroOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresLibrosOficinasImpl de ISicres a SIGEM
	 * @param sicresLibrosOficinasImpl - VO de ISIcres
	 * @return sicresLibrosOficinasImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl adapterSIGEMSicresLibrosOficinasImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl sicresLibrosOficinasImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl();

		try {
			if(sicresLibrosOficinasImpl != null){
				for(Iterator it = sicresLibrosOficinasImpl.getLista().iterator();it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl auxSicresLibroOficinaImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl)it.next();
					result.add(adapterSIGEMSicresLibroOficinaImpl(auxSicresLibroOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresLibrosOficinasImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO FiltroImpl de ISicres a SIGEM
	 * @param filtroImpl - VO de ISIcres
	 * @return filtroImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl adapterSIGEMFiltroImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl filtroImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl result = new ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl();

		try {
			if(filtroImpl != null){
				BeanUtils.copyProperties(result, filtroImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMFiltroImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO FiltrosImpl de ISicres a SIGEM
	 * @param filtrosImpl - VO de ISIcres
	 * @return filtrosImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl adapterSIGEMFiltrosImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl filtrosImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl result = new ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl();

		try {
			if(filtrosImpl != null){
				for(Iterator it = filtrosImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl auxFiltroImpl = (es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl)it.next();
					result.add(adapterSIGEMFiltroImpl(auxFiltroImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMFiltrosImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO ArchiveFld de ISicres a SIGEM
	 * @param archiveFld - VO ISicres
	 * @return archiveFld - VO SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld adapterSIGEMArchiveFld(
			es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld archiveFld) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld();
		try {
			if(archiveFld != null){
				BeanUtils.copyProperties(result, archiveFld);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMArchiveFld", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinasImpl de ISicres a SIGEM
	 * @param sicresOficinasImpl - VO SIGEM
	 * @return sicresOficinasImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl adapterSIGEMSicresOficinasImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl sicresOficinasImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl();
		try {
			if(sicresOficinasImpl != null){
				for(Iterator it = sicresOficinasImpl.getLista().iterator();it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl auxSicresOficinaImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl)it.next();
					result.add(adapterSIGEMSicresOficinaImpl(auxSicresOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresOficinasImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinaImpl de ISicres a SIGEM
	 * @param sicresOficinaImpl - VO SIGEM
	 * @return sicresOficinaImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl adapterSIGEMSicresOficinaImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl sicresOficinaImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl();
		try {
			if(sicresOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresOficinaImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOficinaLocalizacionImpl de ISicres a SIGEM
	 * @param sicresOficinaLocalizacionImpl - VO SIGEM
	 * @return sicresOficinaLocalizacionImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl adapterSIGEMSicresOficinaLocalizacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl sicresOficinaLocalizacionImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl();

		try {
			if(sicresOficinaLocalizacionImpl != null){
				BeanUtils.copyProperties(result, sicresOficinaLocalizacionImpl);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresOficinaLocalizacionImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresTipoOficinaImpl de ISicres a SIGEM
	 * @param sicresTipoOficinaImpl - VO SIGEM
	 * @return SicresTipoOficinaImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl adapterSIGEMSicresTipoOficinaImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl sicresTipoOficinaImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl();

		try {
			if(sicresTipoOficinaImpl != null){
				BeanUtils.copyProperties(result, sicresTipoOficinaImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresTipoOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO SicresTiposOficinaImpl de ISicres a SIGEM
	 * @param sicresTiposOficinaImpl - VO SIGEM
	 * @return SicresTiposOficinaImpl - VO ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl adapterSIGEMSicresTiposOficinaImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl sicresTiposOficinaImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl();

		try {
			if(sicresTiposOficinaImpl != null){
				for(Iterator it = sicresTiposOficinaImpl.getLista().iterator();it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl auxSicresTipoOficinaImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl)it.next();
					result.add(adapterSIGEMSicresTipoOficinaImpl(auxSicresTipoOficinaImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresTiposOficinaImpl", e);
		}

		return result;
	}

	/**
	 * Adapta VO Departamentos de ISicres a SIGEM
	 * @param departamentos - VO de ISicres
	 * @return Departamentos - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos adapterSIGEMDepartamentos(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos departamentos) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos();
		try {
			if(departamentos != null){
				for (int i = 0; i < departamentos.getDepartamentosLista().count(); i++){
					es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento auxDepartamento = departamentos.getDepartamentosLista().get(i);
					result.getDepartamentosLista().add(adapterSIGEMDepartamento(auxDepartamento));
				}
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMDepartamentos", e);
		}
		return result;

	}

	/**
	 * Adapta VO Departamento de ISicres a SIGEM
	 * @param departamento - VO de ISicres
	 * @return Departamento - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento adapterSIGEMDepartamento(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento departamento) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento();
		try {
			if(departamento != null){
				BeanUtils.copyProperties(result, departamento);
			}
		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMDepartamento", e);
		}
		return result;

	}

	/**
	 * Adapta VO SicresOrganizacionesImpl de ISicres a SIGEM
	 * @param sicresOrganizacionesImpl - VO de ISicres
	 * @return SicresOrganizacionesImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl adapterSIGEMSicresOrganizacionesImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl sicresOrganizacionesImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl();
		try {
			if(sicresOrganizacionesImpl != null){
				for(Iterator it = sicresOrganizacionesImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl auxSicresOrganizacionImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl)it.next();
					result.add(adapterSIGEMSicresOrganizacionImpl(auxSicresOrganizacionImpl));
				}
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresOrganizacionesImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOrganizacionImpl de ISicres a SIGEM
	 * @param sicresOrganizacionImpl - VO de ISicres
	 * @return SicresOrganizacionImpl - VO de SIGEM
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl adapterSIGEMSicresOrganizacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl sicresOrganizacionImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl();
		try {
			if(sicresOrganizacionImpl != null){
				BeanUtils.copyProperties(result, sicresOrganizacionImpl);
			}

		} catch (Exception e) {
			_logger.error("Error al adaptar los objetos del método adapterSIGEMSicresOrganizacionImpl", e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresOrganizacionLocalizacionImpl de ISicres a SIGEM
	 * @param sicresOrganizacionLocalizacionImpl - VO de SIGEM
	 * @return SicresOrganizacionLocalizacionImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl adapterSIGEMSicresOrganizacionLocalizacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl sicresOrganizacionLocalizacionImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl();
		try {
			if (sicresOrganizacionLocalizacionImpl != null) {
				BeanUtils.copyProperties(result,
						sicresOrganizacionLocalizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresOrganizacionLocalizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresTiposOrganizacionesImpl de ISicres a SIGEM
	 * @param sicresTiposOrganizacionesImpl - VO de SIGEM
	 * @return SicresTiposOrganizacionesImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl adapterSIGEMSicresTiposOrganizacionesImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl sicresTiposOrganizacionesImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl();
		try {
			if (sicresTiposOrganizacionesImpl != null) {
				for (Iterator it = sicresTiposOrganizacionesImpl.getLista()
						.iterator(); it.hasNext();) {
					es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl auxSicresTipoOrganizacionImpl = (es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl) it
							.next();
					result.add(adapterSIGEMSicresTipoOrganizacionImpl(auxSicresTipoOrganizacionImpl));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresTiposOrganizacionesImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresTipoOrganizacionImpl de ISicres a SIGEM
	 * @param sicresTipoOrganizacionImpl - VO de SIGEM
	 * @return SicresTipoOrganizacionImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl adapterSIGEMSicresTipoOrganizacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl sicresTipoOrganizacionImpl) {

		ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl();
		try {
			if (sicresTipoOrganizacionImpl != null) {
				BeanUtils.copyProperties(result, sicresTipoOrganizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresTipoOrganizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TiposAsuntoBean de ISicres a SIGEM
	 * @param tiposAsuntoBean - VO de SIGEM
	 * @return TiposAsuntoBean - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean adapterSIGEMTiposAsuntoBean(
			es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean tiposAsuntoBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean result = new ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean();
		try {
			if (tiposAsuntoBean != null) {
				for(Iterator it = tiposAsuntoBean.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean auxTipoAsuntoBean = (es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean)it.next();
					result.add(adapterSIGEMTipoAsuntoBean(auxTipoAsuntoBean));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMTiposAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TipoAsuntoBean de ISicres a SIGEM
	 * @param tipoAsuntoBean - VO de SIGEM
	 * @return TipoAsuntoBean - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean adapterSIGEMTipoAsuntoBean(
			es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean tipoAsuntoBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean result = new ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean();
		try {
			if (tipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, tipoAsuntoBean);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO DocumentoTipoAsuntoBean de ISicres a SIGEM
	 * @param documentoTipoAsuntoBean - VO de SIGEM
	 * @return DocumentoTipoAsuntoBean - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean adapterSIGEMDocumentoTipoAsuntoBean(
			es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean documentoTipoAsuntoBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean result = new ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean();
		try {
			if (documentoTipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, documentoTipoAsuntoBean);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMDocumentoTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO OficinaTipoAsuntoBean de ISicres a SIGEM
	 * @param oficinaTipoAsuntoBean - VO de SIGEM
	 * @return OficinaTipoAsuntoBean - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean adapterSIGEMOficinaTipoAsuntoBean(
			es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean oficinaTipoAsuntoBean) {
		ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean result = new ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean();
		try {
			if (oficinaTipoAsuntoBean != null) {
				BeanUtils.copyProperties(result, oficinaTipoAsuntoBean);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMOficinaTipoAsuntoBean",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO TipoAsunto de ISicres a SIGEM
	 * @param tipoAsunto - VO de SIGEM
	 * @return TipoAsunto - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto adapterSIGEMTipoAsunto(
			es.ieci.tecdoc.isicres.admin.beans.TipoAsunto tipoAsunto) {
		ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto result = new ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto();
		try {
			if (tipoAsunto != null) {
				BeanUtils.copyProperties(result, tipoAsunto);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMTipoAsunto",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Transportes de ISicres a SIGEM
	 * @param transportes - VO de SIGEM
	 * @return Transportes - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.Transportes adapterSIGEMTransportes(
			es.ieci.tecdoc.isicres.admin.beans.Transportes transportes) {
		ieci.tecdoc.sgm.core.services.rpadmin.Transportes result = new ieci.tecdoc.sgm.core.services.rpadmin.Transportes();
		try {
			if (transportes != null) {
				for(Iterator it = transportes.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.beans.Transporte auxTransporte = (es.ieci.tecdoc.isicres.admin.beans.Transporte)it.next();
					result.add(adapterSIGEMTransporte(auxTransporte));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMTransportes",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO Transporte de ISicres a SIGEM
	 * @param transporte - VO de SIGEM
	 * @return Transporte - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.Transporte adapterSIGEMTransporte(
			es.ieci.tecdoc.isicres.admin.beans.Transporte transporte) {
		ieci.tecdoc.sgm.core.services.rpadmin.Transporte result = new ieci.tecdoc.sgm.core.services.rpadmin.Transporte();
		try {
			if (transporte != null) {
				BeanUtils.copyProperties(result, transporte);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMTransporte",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO ListaUsuariosImpl de ISicres a SIGEM
	 * @param listaUsuariosImpl - VO de SIGEM
	 * @return ListaUsuariosImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl adapterSIGEMListaUsuariosImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl listadoUsuariosImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl result = new ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl();

		try {
			if (listadoUsuariosImpl != null) {
				for(Iterator it = listadoUsuariosImpl.getLista().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl auxListaUsuarioImpl = (es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl)it.next();
					result.add(adapterSIGEMListaUsuarioImpl(auxListaUsuarioImpl));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMListaUsuariosImpl",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO ListaUsuarioImpl de ISicres a SIGEM
	 * @param listaUsuarioImpl - VO de SIGEM
	 * @return ListaUsuarioImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl adapterSIGEMListaUsuarioImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl listadoUsuarioImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl result = new ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl();

		try {
			if (listadoUsuarioImpl != null) {
				BeanUtils.copyProperties(result, listadoUsuarioImpl);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMListaUsuariosImpl",
					e);
		}

		return result;
	}

	/**
	 * Adapta VO UsuariosLdap de ISicres a SIGEM
	 * @param usuariosLdap - VO de SIGEM
	 * @return UsuariosLdap - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap adapterSIGEMUsuariosLdap(
			es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap usuariosLdap) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap();
		try {
			if (usuariosLdap != null) {
				for(Iterator it = usuariosLdap.get_list().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap auxUsuarioLdap = (es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap)it.next();
					result.add(adapterSIGEMUsuarioLdap(auxUsuarioLdap));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMUsuariosLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Usuarios de ISicres a SIGEM
	 * @param usuarios - VO de SIGEM
	 * @return Usuarios - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios adapterSIGEMUsuarios(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios usuarios) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios();
		try {
			if (usuarios != null) {
				for(Iterator it = usuarios.get_list().iterator(); it.hasNext();){
					es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario auxUsuario = (es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario)it.next();
					result.add(adapterSIGEMUsuario(auxUsuario));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMUsuarios",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserPermisosImpl de ISicres a SIGEM
	 * @param sicresUserPermisosImpl - VO de SIGEM
	 * @return SicresUserPermisosImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl adapterSIGEMSicresUserPermisosImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl sicresUserPermisosImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl();
		try {
			if (sicresUserPermisosImpl != null) {
				BeanUtils.copyProperties(result, sicresUserPermisosImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresUserPermisosImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserIdentificacionImpl de ISicres a SIGEM
	 * @param sicresUserIdentificacionImpl - VO de SIGEM
	 * @return SicresUserIdentificacionImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl adapterSIGEMSicresUserIdentificacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl sicresUserIdentificacionImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl();
		try {
			if (sicresUserIdentificacionImpl != null) {
				BeanUtils.copyProperties(result, sicresUserIdentificacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresUserIdentificacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUserLocalizacionImpl de ISicres a SIGEM
	 * @param sicresUserLocalizacionImpl - VO de SIGEM
	 * @return SicresUserLocalizacionImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl adapterSIGEMSicresUserLocalizacionImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl sicresUserLocalizacionImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl();
		try {
			if (sicresUserLocalizacionImpl != null) {
				BeanUtils.copyProperties(result, sicresUserLocalizacionImpl);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresUserLocalizacionImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Usuario de ISicres a SIGEM
	 * @param usuario - VO de SIGEM
	 * @return Usuario - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario adapterSIGEMUsuario(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario usuario) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario();
		try {
			if (usuario != null) {
				BeanUtils.copyProperties(result, usuario);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMUsuario",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO UsuarioLdap de ISicres a SIGEM
	 * @param usuarioLdap - VO de SIGEM
	 * @return UsuarioLdap - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap adapterSIGEMUsuarioLdap(
			es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap usuarioLdap) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap();
		try {
			if (usuarioLdap != null) {
				BeanUtils.copyProperties(result, usuarioLdap);
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMUsuarioLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Grupos de ISicres a SIGEM
	 * @param grupos - VO de SIGEM
	 * @return Grupos - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos adapterSIGEMGrupos(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos grupos) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos();
		try {
			if (grupos != null) {
				for(int i = 0; i < grupos.getGruposLista().count(); i++){
					es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo auxGrupo = grupos.getGruposLista().get(i);
					result.getGruposLista().add(adapterSIGEMGrupo(auxGrupo));
				}
			}

		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMGrupos",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Grupo de ISicres a SIGEM
	 * @param grupo - VO de SIGEM
	 * @return Grupo - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo adapterSIGEMGrupo(
			es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo grupo) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo();
		try {
			if (grupo != null) {
				BeanUtils.copyProperties(result, grupo);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMGrupo",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO GrupoLdap de ISicres a SIGEM
	 * @param grupoLdap - VO de SIGEM
	 * @return GrupoLdap - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap adapterSIGEMGrupoLdap(
			es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap grupoLdap) {
		ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap result = new ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap();
		try {
			if (grupoLdap != null) {
				BeanUtils.copyProperties(result, grupoLdap);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMGrupoLdap",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO SicresUsuarioConfigImpl de ISicres a SIGEM
	 * @param sicresUsuarioConfigImpl - VO de SIGEM
	 * @return SicresUsuarioConfigImpl - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioConfigImpl adapterSIGEMSicresUsuarioConfigImpl(
			es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl sicresUsuarioConfigImpl) {
		ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioConfigImpl result = new ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioConfigImpl();
		try {
			if (sicresUsuarioConfigImpl != null) {
				BeanUtils.copyProperties(result, sicresUsuarioConfigImpl);
			}
		} catch (Exception e) {
			_logger.error(
					"Error al adaptar los objetos del método adapterSIGEMSicresUsuarioConfigImpl",
					e);
		}
		return result;
	}

	/**
	 * Adapta VO Oficina de ISicres a SIGEM
	 * @param oficina - VO de SIGEM
	 * @return Oficina - VO de ISicres
	 */
	public static ieci.tecdoc.sgm.core.services.rpadmin.Oficina adapterSIGEMOficina(
			es.ieci.tecdoc.isicres.admin.beans.Oficina oficina) {
		ieci.tecdoc.sgm.core.services.rpadmin.Oficina result = new ieci.tecdoc.sgm.core.services.rpadmin.Oficina();
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
