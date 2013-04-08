package ieci.tecdoc.sgm.admsistema.util;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import ieci.tecdoc.idoc.admin.internal.UsuarioPermisosBackOfficeImpl;
import ieci.tecdoc.idoc.admin.internal.UsuariosPermisosBackOfficeImpl;
import ieci.tecdoc.sgm.admsistema.bean.UsuarioEntidad;
import ieci.tecdoc.sgm.admsistema.proceso.Proceso;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbEngine;
import ieci.tecdoc.sgm.core.admin.web.PermisosBackOfficeAdministradores;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.entidades.database.DBSessionManager;


public class Utilidades {
	private static final Logger logger = Logger.getLogger(Utilidades.class);

	public static boolean esNuloOVacio(String cadena) {
		return (cadena==null || cadena.equals(""));
	}

	public static List entidadesAdministrar(String usuario, Entidad[] entidades) {
		List ent = new ArrayList();
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			for(int i=0; i<entidades.length; i++) {
				Perfil[] perfiles = oServicio.getPerfiles(usuario, entidades[i].getIdentificador());
				for(int j=0; j<perfiles.length; j++)
					if (ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equalsIgnoreCase(perfiles[j].getIdAplicacion()))
						ent.add(entidades[i]);
			}
		} catch(Exception e) {
			logger.error("Se ha producido un error al obtener las aplicacionesd e usuario", e.getCause());
		}
		return ent;
	}

	public static boolean permisoAdministrarEntidad(String usuario, String entidad) {
		List entidades = null;
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			entidades = entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
			for(int i=0; i<entidades.size(); i++)
				if (((Entidad)entidades.get(i)).getIdentificador().equalsIgnoreCase(entidad))
					return true;
		} catch(Exception e) {
			logger.error("Se ha producido un error al validar permisos para operar sobre entidad", e.getCause());
		}
		return false;
	}

	public static List obtenerUsuariosEntidades(List entidades) {
		List usuarios = new ArrayList();
		HashMap indexUsuarios = new HashMap();
		try {
			if (entidades != null) {
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				for (int i=0; i<entidades.size(); i++) {
					Usuario[] usuariosEnt = oServicio.getUsuariosEntidad(((Entidad)entidades.get(i)).getIdentificador());
					if (usuariosEnt != null) {
						for(int j=0; j<usuariosEnt.length; j++) {
							if (indexUsuarios.get(usuariosEnt[j].getUsuario()) == null) {
								usuarios.add(new UsuarioEntidad(usuariosEnt[j], oServicio.getEntidades(usuariosEnt[j].getUsuario())));
								indexUsuarios.put(usuariosEnt[j].getUsuario(), new Integer(usuarios.size()-1));
							}
						}
					}
				}
			}
		} catch(Exception e) {
			logger.error("Se ha producido un error al obtener los usuarios de una lista de entidades", e.getCause());
		}
		return usuarios;
	}

	public static List obtenerUsuariosInternosEntidades(List entidades) {
		List usuarios = new ArrayList();
		HashMap indexUsuarios = new HashMap();
		try {
			if (entidades != null) {
				//ServicioPermisosBackOffice oServicio = LocalizadorServicios.getServicioPermisosBackOffice();
				ServicioEntidades oServicioEntidades = LocalizadorServicios.getServicioEntidades();
				for (int i=0; i<entidades.size(); i++) {
					try {
						UsuariosPermisosBackOfficeImpl usuariosEnt = PermisosBackOfficeAdministradores.obtenerUsuarios(((Entidad)entidades.get(i)).getIdentificador());
						//DatosUsuario[] usuariosEnt = oServicio.obtenerUsuarios(((Entidad)entidades.get(i)).getIdentificador());
						if (usuariosEnt != null) {
							for(int j=0; j<usuariosEnt.count(); j++) {
								if (indexUsuarios.get(usuariosEnt.get(j).getNombre()) == null) {
									String nombre = oServicioEntidades.obtenerEntidad(((Entidad)entidades.get(i)).getIdentificador()).getNombreLargo();
									usuarios.add(new UsuarioEntidad(getUsuarioServicio(usuariosEnt.get(j)), nombre));
									indexUsuarios.put(usuariosEnt.get(j).getNombre(), new Integer(usuarios.size()-1));
								} else {
									int pos = ((Integer)indexUsuarios.get(usuariosEnt.get(j).getNombre())).intValue();
									String nombre = oServicioEntidades.obtenerEntidad(((Entidad)entidades.get(i)).getIdentificador()).getNombreLargo();
									if (!((UsuarioEntidad)usuarios.get(pos)).getEntidades().contains(nombre))
										((UsuarioEntidad)usuarios.get(pos)).addEntidad(nombre);
								}
							}
						}
					} catch (Exception e1) {
						logger.error("Se ha producido un error al obtener los usuarios de la entidad " + ((Entidad)entidades.get(i)).getIdentificador(), e1.fillInStackTrace());
					}
				}
			}
		} catch(Exception e) {
			logger.error("Se ha producido un error al obtener los usuarios de una lista de entidades", e);
		}
		return usuarios;
	}

	private static Usuario getUsuarioServicio(UsuarioPermisosBackOfficeImpl oUsuario) {
		Usuario poUsuario = new Usuario();

		String n = oUsuario.getNombreReal();
		poUsuario.setNombre(esNuloOVacio(n) ? "":n);
		String a1 = oUsuario.getPrimerApellido();
		String a2 = oUsuario.getSegundoApellido();
		poUsuario.setApellidos( (esNuloOVacio(a1) ? "":(a1+" ")) + (esNuloOVacio(a2) ? "":a2) );
		poUsuario.setUsuario(oUsuario.getNombre());

		return poUsuario;
	}

	public static String[] obtenerEntidadesSeleccionadas(String entidades) {
		StringTokenizer st = new StringTokenizer(entidades,",");
		String[] listadoEntidades = new String[st.countTokens()];
		int i=0;

		while(st.hasMoreElements()) {
			listadoEntidades[i++] = st.nextToken();
		}

		return listadoEntidades;
	}

	public static String[][] obtenerPermisosSeleccionados(String permisos) {
		StringTokenizer st = new StringTokenizer(permisos,"[");
		String[][] listadoPermisos = new String[st.countTokens()][];
		int i=0, j;

		while(st.hasMoreElements()) {
			String aux = st.nextToken();
			StringTokenizer stp = new StringTokenizer(aux.substring(0,aux.length()-1), ",");
			listadoPermisos[i] = new String[stp.countTokens()];
			j=0;
			while(stp.hasMoreElements()) {
				listadoPermisos[i][j++] = stp.nextToken();
			}
			i++;
		}

		return listadoPermisos;
	}

	public static boolean comprobarConexionBBDD(String direccion, String puerto, String usuario, String password){
		boolean conexion = false;

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(direccion, new Integer(puerto).intValue()) , 5000);

			DbConnection dbConn = new DbConnection();
			int tipo = -1;
			try {
				dbConn.open(DBSessionManager.getSession());
				tipo = dbConn.getEngine();
			} catch(Exception e1) {
				return false;
			} finally {
				if (dbConn != null) {
					try { dbConn.close(); } catch(Exception e) {}
				}
			}

			if (tipo == DbEngine.POSTGRESQL) {
				try {
					dbConn.open(Defs.DRIVER_POSTGRES, "jdbc:postgresql://"+direccion+":"+puerto+"/postgres", usuario, password);
					dbConn.close();
					conexion = true;
				} catch(Exception e1) {
					conexion = false;
				} finally {
					if (dbConn != null) {
						try { dbConn.close(); } catch(Exception e) {}
					}
				}
			}else if (tipo == DbEngine.SQLSERVER) {
				try {
					//dbConn.open(Defs.DRIVER_SQLSERVER, "jdbc:sqlserver://"+direccion+":"+puerto+";DatabaseName=sqlserver", usuario, password);
					dbConn.open(Defs.DRIVER_SQLSERVER, "jdbc:sqlserver://"+direccion+":"+puerto, usuario, password);
					dbConn.close();
					conexion = true;
				} catch(Exception e1) {
					conexion = false;
				} finally {
					if (dbConn != null) {
						try { dbConn.close(); } catch(Exception e) {}
					}
				}
			}
			return conexion;
		} catch(Exception e) {
			return conexion;
		}
	}

	public static boolean comprobarConexion(String direccion, String puerto){

		boolean res = false;
		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress(direccion, new Integer(puerto).intValue()) , 5000);
			res = true;
		} catch(Exception e) {
			res = false;
		} finally {
			try {
				socket.close();
			} catch(Throwable t) {}
		}

		return res;
	}

	public static boolean comprobarConexionBBDDEsquema(String direccion, String puerto, String esquema, String usuario, String password){

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(direccion, new Integer(puerto).intValue()) , 5000);

			DbConnection dbConn = new DbConnection();

			try {
				dbConn.open(Defs.DRIVER_ORACLE, "jdbc:oracle:thin:@"+direccion+":"+puerto+":"+esquema, usuario, password);
				dbConn.close();
				return true;
			} catch(Exception e1) {
				if (dbConn != null) {
					try { dbConn.close(); } catch(Exception e) {}
				}
			}

			try {
				dbConn.open(Defs.DRIVER_DB2, "jdbc:db2://"+direccion+":"+puerto+"/"+esquema, usuario, password);
				dbConn.close();
				return true;
				} catch(Exception e1) {
					if (dbConn != null) {
						try { dbConn.close(); } catch(Exception e) {}
				}
			}

			return false;
		} catch(Exception e) {
			return false;
		}
	}

	public static boolean comprobarConexionFTP(String direccion, String puerto, String usuario, String password) {
		boolean conexionFtp = false;
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(direccion, new Integer(puerto).intValue()) , 5000);
			FTPClient cliente = new FTPClient();
			cliente.connect(direccion, new Integer(puerto).intValue());
			conexionFtp = cliente.login(usuario, password);
			cliente.disconnect();
			socket.close();
		} catch(Exception e) {
			conexionFtp = false;
		}
		return conexionFtp;
	}

	public static String[] obtenerIdentificadoresAplicacaciones() {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Aplicacion[] aplicaciones = oServicio.getAplicaciones();
			if (aplicaciones == null)
				return new String[0];
			String[] idsApps = new String[aplicaciones.length];
			for(int i=0; i<aplicaciones.length; i++)
				idsApps[i] = aplicaciones[i].getIdentificador();
			return idsApps;
		} catch(Exception e) {
			return new String[0];
		}
	}

	public static String obtenerUrlAplicacion(HttpServletRequest request, Aplicacion aplicacion) {
		try {
			String Url = aplicacion.getProtocolo() + "://" +
								(esNuloOVacio(aplicacion.getServidorApp()) ? request.getServerName() : aplicacion.getServidorApp()) +
								(esNuloOVacio(aplicacion.getPuertoApp()) ? "" : (":" + aplicacion.getPuertoApp())) +
								aplicacion.getContextoApp();
			return Url;
		} catch(Exception e) {
			return null;
		}
	}


	public static List obtenerProcesos(String[] entidades, String tipo) {
		String directorio = System.getProperties().getProperty("user.home") + File.separator;
		File d=new java.io.File(directorio+File.separator+tipo);
	    File f[]=d.listFiles();
	    List listado = new ArrayList();

	    if (f != null) {
	    	for(int i=0;i<f.length;i++) {
	    		if(!f[i].isDirectory())
	    			continue;
	    		if (tipo.equals(Defs.EXPORTAR) && !permisosDirectorio(f[i].getName(), entidades))
	    			continue;
	    		if (listado.size() == 0)
	    			listado.add(new Proceso("", ""));
	    		listado.add(new Proceso(obtenerNombreDescriptivoProceso(f[i].getName(), tipo), f[i].getName()));
	    	}
	    }

	    return ordenarListado(listado);
	}

	private static List ordenarListado(List listado) {
		Collections.sort(listado, new ProcesosSorter());
		return listado;
	}

	private static boolean permisosDirectorio(String directorio, String entidades[]) {
		for(int j=0; j<entidades.length; j++)
			if (directorio.endsWith("_" + entidades[j]))
				return true;
		return false;
	}

	public static String[] obtenerIdentificadoresEntidades(List entidades) {
		if(entidades == null)
			return new String[0];
		String[] ids = new String[entidades.size()];
		for(int i=0; i<entidades.size(); i++)
			ids[i] = ((Entidad)entidades.get(i)).getIdentificador();
		return ids;
	}

	public static String obtenerNombreDescriptivoProceso(String nombre, String tipo) {
		try  {
			StringTokenizer st = new StringTokenizer(nombre, "_");
			StringTokenizer fecha = new StringTokenizer(st.nextToken(), "-");
			String anyo = fecha.nextToken();
			String mes = fecha.nextToken();
			String dia = fecha.nextToken();
			String hora = st.nextToken();
			if (tipo.equals(Defs.EXPORTAR)) {
				String entidad = st.nextToken();
				return dia + "/" + mes + "/" + anyo + "  " + hora + " (" + entidad + ")";
			} else if (tipo.equals(Defs.ACCION_MULTIENTIDAD)) {
				String accion = st.nextToken();
				return dia + "/" + mes + "/" + anyo + "  " + hora + " (" + accion + ")";
			}
			else {
				return dia + "/" + mes + "/" + anyo + "  " + hora;
			}
		} catch(Exception e) {
			return nombre;
		}
	}

}
