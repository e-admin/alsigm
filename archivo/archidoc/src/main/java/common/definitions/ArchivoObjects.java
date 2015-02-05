package common.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import salas.vos.EdificioVO;
import salas.vos.UsuarioSalasConsultaVO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.ProrrogaVO;
import solicitudes.vos.BusquedaDetalleVO;
import transferencias.vos.PrevisionVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.ValoracionSerieVO;
import auditoria.vos.ArchivoObject;
import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.CritAccionVO;
import auditoria.vos.CritUsuarioVO;
import deposito.vos.DepositoVO;
import deposito.vos.HuecoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.TablaValidacionVO;
import descripcion.vos.TextoTablaValidacionVO;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;
import docvitales.vos.DocumentoVitalExtVO;
import docvitales.vos.TipoDocumentoVitalVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.FondoVO;
import fondos.vos.SerieVO;
import fondos.vos.UDocFondo;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioVO;

/**
 * Clase que encapsula los objetos existentes en la aplicacion
 */
public class ArchivoObjects {
	public static final int OBJECT_NULL = 0;
	/** Objetos TRANSFERENCIAS_MODULE - 1xxx */
	public static final int OBJECT_PREVISION = 1000;
	public static final int OBJECT_RELACION = 1001;
	public static final int OBJECT_LISTA_PREVISIONES = 1002;
	public static final int OBJECT_LISTA_RELACIONES = 1003;
	/** Objetos DEPOSITOS_MODULE - 2xxx */
	public static final int OBJECT_DEPOSITO = 2000;
	/** Objetos PRESTAMOS_MODULE - 3xxx */
	public static final int OBJECT_PRESTAMO = 3001;
	public static final int OBJECT_DETALLE_PRESTAMO = 3002;
	public static final int OBJECT_CONSULTA = 3003;
	public static final int OBJECT_DETALLE_CONSULTA = 3004;
	public static final int OBJECT_PRORROGA = 3005;
	public static final int OBJECT_LISTADO_PRESTAMOS = 3006;
	public static final int OBJECT_LISTADO_CONSULTAS = 3007;
	/** Objetos DESCRIPCION_MODULE - 4xxx */
	public static final int OBJECT_DESCRIPCION_ELEMENTO = 40001;
	public static final int OBJECT_DESCRIPCION_DESCRIPTOR = 40002;
	public static final int OBJECT_DESCRIPCION_LISTA_DESCRIPTORES = 40003;
	public static final int OBJECT_DESCRIPCION_LISTA_VALORES = 40004;
	public static final int OBJECT_DESCRIPCION_VALOR_VALIDACION = 40005;
	/** Objetos FONDOS - 5xxx */
	public static final int OBJECT_ELEMENTO_CUADRO = 50001;
	public static final int OBJECT_FONDO = 50002;
	public static final int OBJECT_SERIE = 50003;
	public static final int OBJECT_VALORACION = 50004;
	public static final int OBJECT_ELIMINACION = 50005;
	public static final int OBJECT_UDOC = 50006;
	public static final int OBJECT_LISTA_VALORACIONES = 50007;
	public static final int OBJECT_LISTA_ELIMINACIONES = 50008;
	public static final int OBJECT_HUECO = 50009;
	public static final int OBJECT_UNIDAD_INSTALACION = 50010;
	public static final int OBJECT_INGRESO = 50011;
	public static final int OBJECT_FRACCION_SERIE = 500012;
	/** Objetos SERIES_MODULE - 6xxx */
	/** Objetos USUARIOS_MODULE - 7xxx */
	public static final int OBJECT_USUARIO = 70001;
	public static final int OBJECT_ROLE = 70002;
	public static final int OBJECT_GRUPO = 70003;
	public static final int OBJECT_ORGANO = 70004;

	/** Objetos DOCUMENOS_VITALES_MODULE - 8xxx */
	public static final int OBJECT_DOCUMENTO_VITAL = 80001;
	public static final int OBJECT_TIPO_DOCUMENTO_VITAL = 80002;

	/** Objetos EXPLOTACION_MODULE - 9xxx */

	/** Objetos DOCUMENTOS_ELECTRONICOS_MODULE - 10xxx */
	public static final int OBJECT_CLASIFICADOR = 10001;
	public static final int OBJECT_DOCUMENTO = 10002;
	public static final int OBJECT_TAREA = 10003;

	/** Objetos AUDITORIA_MODULE - 11xxx */
	public static final int OBJECT_GROUP = 11001;
	public static final int OBJECT_ACTION = 11002;
	public static final int OBJECT_CONSULTA_AUDITORIA = 11003;

	/** Objetos SALAS_MODULE - 14xxx */
	public static final int OBJECT_EDIFICIO = 14001;
	public static final int OBJECT_SALA = 14002;
	public static final int OBJECT_MESA = 14003;

	public static final int OBJECT_USUARIO_SALA_CONSULTA = 14004;
	public static final int OBJECT_REGISTRO_CONSULTA = 14005;

	/*****************************
	 * NOMBRE
	 ****************************/
	public static final String OBJECT_NULL_NAME = "NINGUNO";

	/** Nombres Objetos TRANSFERENCIAS_MODULE - 1xxx */
	public static final String OBJECT_PREVISION_NAME = "PREVISIÓN DE TRANSFERENCIA";
	public static final String OBJECT_RELACIONES_NAME = "RELACIÓN DE ENTREGA";

	/** Nombres Objetos DEPOSITOS_MODULE - 2xxx */
	public static final String OBJECT_DEPOSITO_NAME = "DEPÓSITO";

	/** Nombres Objetos PRESTAMOS_MODULE - 3xxx */
	public static final String OBJECT_PRESTAMO_NAME = "PRESTAMO";
	public static final String OBJECT_DETALLE_PRESTAMO_NAME = "DETALLE PRESTAMO";
	public static final String OBJECT_CONSULTA_NAME = "CONSULTA";
	public static final String OBJECT_DETALLE_CONSULTA_NAME = "DETALLE CONSULTA";
	public static final String OBJECT_PRORROGA_NAME = "PRORROGA";
	public static final String OBJECT_LISTADO_PRESTAMOS_NAME = "LISTADO PRESTAMOS";
	public static final String OBJECT_LISTADO_CONSULTAS_NAME = "LISTADO CONSULTAS";

	/** Nombres Objetos DESCRIPCION_MODULE - 4xxx */
	public static final String OBJECT_DESCRIPCION_ELEMENTO_NAME = "DESCRIPCION DE ELEMENTO";
	public static final String OBJECT_DESCRIPCION_DESCRIPTOR_NAME = "DESCRIPCION DE DESCRIPTOR";
	public static final String OBJECT_DESCRIPCION_LISTA_DESCRIPTORES_NAME = "LISTA DE DESCRIPTORES";
	public static final String OBJECT_DESCRIPCION_LISTA_VALORES_NAME = "LISTA DE VALORES";
	public static final String OBJECT_DESCRIPCION_VALOR_VALIDACION_NAME = "VALOR DE VALIDACIÓN";

	/** Nombres Objetos FONDOS_MODULE - 5xxx */
	public static final String OBJECT_ELEMENTO_CUADRO_ELEMENTO_NAME = "ELEMENTO_CUADRO";
	public static final String OBJECT_FONDO_NAME = "FONDO_NAME";
	public static final String OBJECT_SERIE_NAME = "SERIE_NAME";

	public static final String OBJECT_VALORACION_NAME = "VALORACION DE SERIE";
	public static final String OBJECT_ELIMINACION_NAME = "ELIMINACION DE SERIE";
	public static final String OBJECT_UDOC_NAME = "UNIDAD DOCUMENTAL";
	public static final String OBJECT_LISTA_VALORACIONES_NAME = "LISTA DE VALORACIONES";
	public static final String OBJECT_LISTA_ELIMINACIONES_NAME = "LISTA DE ELIMINACIONES";
	public static final String OBJECT_HUECO_NAME = "HUECO";
	public static final String OBJECT_UNIDAD_INSTALACION_NAME = "UNIDAD INSTALACION";
	public static final String OBJECT_INGRESO_NAME = "ALTA UNIDADES DOCUMENTALES";
	public static final String OBJECT_FRACCION_SERIE_NAME = "FRACCIÓN DE SERIE";

	/** Nombres Objetos USUARIOS_MODULE - 7xxx */
	public static final String OBJECT_USUARIO_NAME = "USUARIO";
	public static final String OBJECT_ROLE_NAME = "ROLE";
	public static final String OBJECT_GRUPO_NAME = "GRUPO";
	public static final String OBJECT_ORGANO_NAME = "ORGANO";

	/** Nombres Objetos DOCUMENTOS_VITALES_MODULE - 8xxx */
	public static final String OBJECT_DOCUMENTO_VITAL_NAME = "DOCUMENTO VITAL";
	public static final String OBJECT_TIPO_DOCUMENTO_VITAL_NAME = "TIPO DOCUMENTO VITAL";

	/** Nombres Objetos EXPLOTACION_MODULE - 9xxx */

	/** Nombres Objetos DOCUMENTOS_ELECTRONICOS_MODULE - 10xxx */
	public static final String OBJECT_CLASIFICADOR_NAME = "CLASIFICADOR";
	public static final String OBJECT_DOCUMENTO_NAME = "DOCUMENTO_ELECTRONICO";
	public static final String OBJECT_TAREA_NAME = "TAREA_CAPTURA";

	/** Nombres Objetos AUDITORIA_MODULE - 11xxx */
	public static final String OBJECT_GROUP_NAME = "GRUPO_AUDITORIA";
	public static final String OBJECT_ACTION_NAME = "ACCION_AUDITORIA";
	public static final String OBJECT_CONSULTA_AUDITORIA_NAME = "CONSULTA_AUDITORIA";

	public static final String OBJECT_EDIFICIO_NAME = "EDIFICIO";
	public static final String OBJECT_SALA_NAME = "SALA";

	public static final String OBJECT_USUARIO_SALA_CONSULTA_NAME = "USUARIO SALA CONSULTA";

	/*****************************
	 * CLASE
	 ****************************/
	/** Clase Objetos TRANSFERENCIAS_MODULE - 1xxx */
	public static final Class OBJECT_PREVISION_CLASS = PrevisionVO.class;
	/** Clase Objetos DEPOSITOS_MODULE - 2xxx */
	public static final Class OBJECT_DEPOSITO_CLASS = DepositoVO.class;
	/** Clase Objetos PRESTAMOS_MODULE - 3xxx */
	public static final Class OBJECT_PRESTAMO_CLASS = PrestamoVO.class;
	public static final Class OBJECT_CONSULTA_CLASS = ConsultaVO.class;
	public static final Class OBJECT_DETALLE_CONSULTA_CLASS = BusquedaDetalleVO.class;
	public static final Class OBJECT_PRORROGA_CLASS = ProrrogaVO.class;

	/** Clase Objetos DESCRIPCION_MODULE - 4xxx */
	public static final Class OBJECT_DESCRIPCION_ELEMENTO_CLASS = ElementoCuadroClasificacionVO.class;
	public static final Class OBJECT_DESCRIPCION_DESCRIPTOR_CLASS = DescriptorVO.class;
	public static final Class OBJECT_DESCRIPCION_LISTA_DESCRIPTORES_CLASS = ListaDescrVO.class;
	public static final Class OBJECT_DESCRIPCION_LISTA_VALORES_CLASS = TablaValidacionVO.class;
	public static final Class OBJECT_DESCRIPCION_VALOR_VALIDACION_CLASS = TextoTablaValidacionVO.class;

	/** Clase Objetos FONDOS_MODULE - 5xxx */
	public static final Class OBJECT_FONDO_NAME_CLASS = FondoVO.class;
	public static final Class OBJECT_SERIE_NAME_CLASS = SerieVO.class;
	public static final Class OBJECT_ELEMENTO_CUADRO_CLASS = ElementoCuadroClasificacionVO.class;

	public static final Class OBJECT_VALORACION_CLASS = ValoracionSerieVO.class;
	public static final Class OBJECT_ELIMINACION_CLASS = EliminacionSerieVO.class;
	public static final Class OBJECT_UDOC_CLASS = UDocFondo.class;
	public static final Class OBJECT_LISTA_VALORACIONES_CLASS = List.class;
	public static final Class OBJECT_LISTA_ELIMINACIONES_CLASS = List.class;
	public static final Class OBJECT_HUECO_CLASS = HuecoVO.class;
	public static final Class OBJECT_UNIDAD_INSTALACION_CLASS = UInsDepositoVO.class;
	/** Clase Objetos USUARIOS_MODULE - 7xxx */
	public static final Class OBJECT_USUARIO_CLASS = UsuarioVO.class;
	public static final Class OBJECT_ROLE_CLASS = RolVO.class;
	public static final Class OBJECT_GRUPO_CLASS = GrupoVO.class;

	/** Clase Objetos DOCUMENTOS_VITALES_MODULE - 8xxx */
	public static final Class OBJECT_DOCUMENTO_VITAL_CLASS = DocumentoVitalExtVO.class;
	public static final Class OBJECT_TIPO_DOCUMENTO_VITAL_CLASS = TipoDocumentoVitalVO.class;

	/** Clase Objetos EXPLOTACION_MODULE - 9xxx */

	/** Clase Objetos DOCUMENTOS_ELECTRONICOS_MODULE - 10xxx */
	public static final Class OBJECT_CLASIFICADOR_CLASS = DocClasificadorVO.class;
	public static final Class OBJECT_DOCUMENTO_CLASS = DocDocumentoVO.class;
	public static final Class OBJECT_TAREA_CLASS = DocTCapturaVO.class;

	/** Clase Objetos AUDITORIA_MODULE - 11xxx */
	public static final Class OBJECT_GROUP_CLASS = CritUsuarioVO.class;
	public static final Class OBJECT_ACTION_CLASS = CritAccionVO.class;
	public static final Class OBJECT_CONSULTA_AUDITORIA_CLASS = BusquedaPistasVO.class;

	public static final Class OBJECT_EDIFICIO_CLASS = EdificioVO.class;
	public static final Class OBJECT_USUARIO_SALAS_CLASS = UsuarioSalasConsultaVO.class;

	/** Asociacion object/class */
	private static HashMap objectClasses;

	static {
		objectClasses = new HashMap();
		/*** MAPEO DE PRESTAMOS ***/
		objectClasses.put(new Integer(OBJECT_PRESTAMO), OBJECT_PRESTAMO_CLASS);
		objectClasses.put(new Integer(OBJECT_CONSULTA), OBJECT_CONSULTA_CLASS);
		objectClasses.put(new Integer(OBJECT_DETALLE_CONSULTA),
				OBJECT_DETALLE_CONSULTA_CLASS);
		objectClasses.put(new Integer(OBJECT_PRORROGA), OBJECT_PRORROGA_CLASS);

		/*** MAPEO DEPOSITO ***/
		objectClasses.put(new Integer(OBJECT_DEPOSITO), OBJECT_DEPOSITO_CLASS);

		/*** MAPEO DE PREVISION ***/
		objectClasses
				.put(new Integer(OBJECT_PREVISION), OBJECT_PREVISION_CLASS);

		/*** MAPEO DE FONDOS ***/
		objectClasses.put(new Integer(OBJECT_ELEMENTO_CUADRO),
				OBJECT_ELEMENTO_CUADRO_CLASS);

		objectClasses.put(new Integer(OBJECT_VALORACION),
				OBJECT_VALORACION_CLASS);
		objectClasses.put(new Integer(OBJECT_ELIMINACION),
				OBJECT_ELIMINACION_CLASS);
		objectClasses.put(new Integer(OBJECT_UDOC), OBJECT_UDOC_CLASS);
		objectClasses.put(new Integer(OBJECT_LISTA_VALORACIONES),
				OBJECT_LISTA_VALORACIONES_CLASS);
		objectClasses.put(new Integer(OBJECT_LISTA_ELIMINACIONES),
				OBJECT_LISTA_ELIMINACIONES_CLASS);
		objectClasses.put(new Integer(OBJECT_HUECO), OBJECT_HUECO_CLASS);
		objectClasses.put(new Integer(OBJECT_UNIDAD_INSTALACION),
				OBJECT_UNIDAD_INSTALACION_CLASS);
		/** MAPEO USUARIOS ***/
		objectClasses.put(new Integer(OBJECT_USUARIO), OBJECT_USUARIO_CLASS);
		objectClasses.put(new Integer(OBJECT_ROLE), OBJECT_ROLE_CLASS);
		objectClasses.put(new Integer(OBJECT_GRUPO), OBJECT_GRUPO_CLASS);

		/*** MAPEO DE DESCRIPCION ***/
		objectClasses.put(new Integer(OBJECT_DESCRIPCION_ELEMENTO),
				OBJECT_DESCRIPCION_ELEMENTO_CLASS);
		objectClasses.put(new Integer(OBJECT_DESCRIPCION_DESCRIPTOR),
				OBJECT_DESCRIPCION_DESCRIPTOR_CLASS);
		objectClasses.put(new Integer(OBJECT_DESCRIPCION_LISTA_DESCRIPTORES),
				OBJECT_DESCRIPCION_LISTA_DESCRIPTORES_CLASS);
		objectClasses.put(new Integer(OBJECT_DESCRIPCION_LISTA_VALORES),
				OBJECT_DESCRIPCION_LISTA_VALORES_CLASS);
		objectClasses.put(new Integer(OBJECT_DESCRIPCION_VALOR_VALIDACION),
				OBJECT_DESCRIPCION_VALOR_VALIDACION_CLASS);

		/*** MAPEO DE DOCUMENTOS VITALES ***/
		objectClasses.put(new Integer(OBJECT_DOCUMENTO_VITAL),
				OBJECT_DOCUMENTO_VITAL_CLASS);
		objectClasses.put(new Integer(OBJECT_TIPO_DOCUMENTO_VITAL),
				OBJECT_TIPO_DOCUMENTO_VITAL_CLASS);

		/*** MAPEO DE DOCUMENTOS ELECTRONICOS ***/
		objectClasses.put(new Integer(OBJECT_CLASIFICADOR),
				OBJECT_CLASIFICADOR_CLASS);
		objectClasses
				.put(new Integer(OBJECT_DOCUMENTO), OBJECT_DOCUMENTO_CLASS);
		objectClasses.put(new Integer(OBJECT_TAREA), OBJECT_TAREA_CLASS);

		/*** MAPEO DE AUDITORIA ***/
		objectClasses.put(new Integer(OBJECT_GROUP), OBJECT_GROUP_CLASS);
		objectClasses.put(new Integer(OBJECT_ACTION), OBJECT_ACTION_CLASS);
		objectClasses.put(new Integer(OBJECT_CONSULTA_AUDITORIA),
				OBJECT_CONSULTA_AUDITORIA_CLASS);

		/*** MAPEO DE SALAS DE CONSULTA ***/
		objectClasses.put(new Integer(OBJECT_EDIFICIO), OBJECT_EDIFICIO_CLASS);
		objectClasses.put(new Integer(OBJECT_USUARIO_SALA_CONSULTA),
				OBJECT_USUARIO_SALAS_CLASS);
	}

	/** Asociacion object/nombre */
	private static HashMap objectNames;

	static {
		objectNames = new HashMap();
		objectNames.put(new Integer(OBJECT_NULL), OBJECT_NULL_NAME);
		objectNames.put(new Integer(OBJECT_PRESTAMO), OBJECT_PRESTAMO_NAME);
		objectNames.put(new Integer(OBJECT_PREVISION), OBJECT_PREVISION_NAME);
		objectNames.put(new Integer(OBJECT_RELACION), OBJECT_RELACIONES_NAME);
		objectNames.put(new Integer(OBJECT_CONSULTA), OBJECT_CONSULTA_NAME);
		objectNames.put(new Integer(OBJECT_DETALLE_CONSULTA),
				OBJECT_DETALLE_CONSULTA_NAME);
		objectNames.put(new Integer(OBJECT_PRORROGA), OBJECT_PRORROGA_NAME);
		objectNames.put(new Integer(OBJECT_LISTADO_PRESTAMOS),
				OBJECT_LISTADO_PRESTAMOS_NAME);
		objectNames.put(new Integer(OBJECT_LISTADO_CONSULTAS),
				OBJECT_LISTADO_CONSULTAS_NAME);
		/*** MAPEO DEPOSITO ***/
		objectNames.put(new Integer(OBJECT_DEPOSITO), OBJECT_DEPOSITO_NAME);

		/*** MAPEO DE FONDOS ***/
		objectNames.put(new Integer(OBJECT_ELEMENTO_CUADRO),
				OBJECT_ELEMENTO_CUADRO_ELEMENTO_NAME);
		objectNames.put(new Integer(OBJECT_FONDO), OBJECT_FONDO_NAME);
		objectNames.put(new Integer(OBJECT_SERIE), OBJECT_SERIE_NAME);
		objectNames.put(new Integer(OBJECT_VALORACION), OBJECT_VALORACION_NAME);
		objectNames.put(new Integer(OBJECT_ELIMINACION),
				OBJECT_ELIMINACION_NAME);

		objectNames.put(new Integer(OBJECT_UDOC), OBJECT_UDOC_NAME);
		objectNames.put(new Integer(OBJECT_LISTA_VALORACIONES),
				OBJECT_LISTA_VALORACIONES_NAME);
		objectNames.put(new Integer(OBJECT_LISTA_ELIMINACIONES),
				OBJECT_LISTA_ELIMINACIONES_NAME);
		objectNames.put(new Integer(OBJECT_HUECO), OBJECT_HUECO_NAME);
		objectNames.put(new Integer(OBJECT_UNIDAD_INSTALACION),
				OBJECT_UNIDAD_INSTALACION_NAME);
		objectNames.put(new Integer(OBJECT_INGRESO), OBJECT_INGRESO_NAME);
		objectNames.put(new Integer(OBJECT_FRACCION_SERIE),
				OBJECT_FRACCION_SERIE_NAME);
		/*** USUARIOS ***/
		objectNames.put(new Integer(OBJECT_USUARIO), OBJECT_USUARIO_NAME);
		objectNames.put(new Integer(OBJECT_ROLE), OBJECT_ROLE_NAME);
		objectNames.put(new Integer(OBJECT_GRUPO), OBJECT_GRUPO_NAME);

		/*** MAPEO DE DESCRIPCION ***/
		objectNames.put(new Integer(OBJECT_DESCRIPCION_ELEMENTO),
				OBJECT_DESCRIPCION_ELEMENTO_NAME);
		objectNames.put(new Integer(OBJECT_DESCRIPCION_DESCRIPTOR),
				OBJECT_DESCRIPCION_DESCRIPTOR_NAME);
		objectNames.put(new Integer(OBJECT_DESCRIPCION_LISTA_DESCRIPTORES),
				OBJECT_DESCRIPCION_LISTA_DESCRIPTORES_NAME);
		objectNames.put(new Integer(OBJECT_DESCRIPCION_LISTA_VALORES),
				OBJECT_DESCRIPCION_LISTA_VALORES_NAME);
		objectNames.put(new Integer(OBJECT_DESCRIPCION_VALOR_VALIDACION),
				OBJECT_DESCRIPCION_VALOR_VALIDACION_NAME);

		/*** MAPEO DE DOCUMENTOS VITALES ***/
		objectNames.put(new Integer(OBJECT_DOCUMENTO_VITAL),
				OBJECT_DOCUMENTO_VITAL_NAME);
		objectNames.put(new Integer(OBJECT_TIPO_DOCUMENTO_VITAL),
				OBJECT_TIPO_DOCUMENTO_VITAL_NAME);

		/*** MAPEO DE DOCUMENTOS ELECTRONICOS ***/
		objectNames.put(new Integer(OBJECT_CLASIFICADOR),
				OBJECT_CLASIFICADOR_NAME);
		objectNames.put(new Integer(OBJECT_DOCUMENTO), OBJECT_DOCUMENTO_NAME);
		objectNames.put(new Integer(OBJECT_TAREA), OBJECT_TAREA_NAME);

		/*** MAPEO DE AUDITORIA ***/
		objectNames.put(new Integer(OBJECT_GROUP), OBJECT_GROUP_NAME);
		objectNames.put(new Integer(OBJECT_ACTION), OBJECT_ACTION_NAME);
		objectNames.put(new Integer(OBJECT_CONSULTA_AUDITORIA),
				OBJECT_CONSULTA_AUDITORIA_NAME);

		/*** MAPEO DE SALAS DE CONSULTA ***/
		objectClasses.put(new Integer(OBJECT_EDIFICIO), OBJECT_EDIFICIO_NAME);
		objectClasses.put(new Integer(OBJECT_USUARIO_SALA_CONSULTA),
				OBJECT_USUARIO_SALA_CONSULTA_NAME);
	}

	/**
	 * Devuelve el nombre del objeto a partir de la cte de identificación del
	 * mismo.
	 * 
	 * @param object
	 *            Cte de identificacion del objeto
	 * @return Nombre del objeto asociado a la cte de identificacion
	 */
	public static String getObjectName(int object) {
		return (String) objectNames.get(new Integer(object));
	}

	/**
	 * Devuelve la clase{@link Class} del objeto a partir de la cte de
	 * identificación del mismo.
	 * 
	 * @param object
	 *            Cte de identificacion del objeto
	 * @return Clase {@link Class} del objeto asociado a la cte de
	 *         identificacion
	 */
	public static Class getObjectClass(int object) {
		return (Class) objectClasses.get(new Integer(object));
	}

	/**
	 * Devuelve un listado de los objetos existentes en la aplicacion con su cte
	 * de identificación, su nombre y su clase asociada.
	 * 
	 * @return Listado de los objetos{@link ArchivoObject} de la aplicación.
	 */
	public static Collection getObjects() {
		Integer i = null;
		String name = null;
		Class clase = null;
		ArchivoObject object = null;
		ArrayList result = new ArrayList();
		Iterator it = objectNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) objectNames.get(i);
			clase = (Class) objectClasses.get(i);

			object = new ArchivoObject();
			object.setId(i.intValue());
			object.setName(name);
			object.setClase(clase);

			result.add(object);
		}

		return result;
	}

}
