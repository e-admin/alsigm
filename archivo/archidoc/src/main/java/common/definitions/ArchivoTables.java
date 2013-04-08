package common.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import auditoria.vos.ArchivoTable;

/**
 * Clase que encapsula las tablas en base de datos para la aplicación.
 */
public class ArchivoTables {

	/**
	 * TABLAS PARA DESCRIPCIÓN - 1XX
	 */
	public static final int ADAREA_TABLE = 101;
	public static final int ADCAMPODATO_TABLE = 102;
	public static final int ADCAMPOTBL_TABLE = 103;
	public static final int ADCTLGLISTAD_TABLE = 104;
	public static final int ADCTLGTBLVLD_TABLE = 105;
	public static final int ADDESCRIPTOR_TABLE = 106;
	public static final int ADFICHA_TABLE = 107;
	public static final int ADTEXTTBLVLD_TABLE = 108;
	public static final int ADUSOOBJETO_TABLE = 109;
	public static final int ADVCFECHACF_TABLE = 110;
	public static final int ADVCFECHADESCR_TABLE = 111;
	public static final int ADVCNUMCF_TABLE = 112;
	public static final int ADVCNUMDESCR_TABLE = 113;
	public static final int ADVCREFCF_TABLE = 114;
	public static final int ADVCREFDESCR_TABLE = 115;
	public static final int ADVCTEXTCF_TABLE = 116;
	public static final int ADVCTEXTDESCR_TABLE = 117;
	public static final int ADVCTEXTLCF_TABLE = 118;
	public static final int ADVCTEXTLDESCR_TABLE = 119;

	/**
	 * TABLAS PARA DEPOSITO - 2XX
	 */
	public static final int ASGDDEPOSITO_TABLE = 201;
	public static final int ASGDELEMASIG_TABLE = 202;
	public static final int ASGDELEMNOASIG_TABLE = 203;
	public static final int ASGDHUECO_TABLE = 204;
	public static final int ASGDSIGNUMORDEN_TABLE = 205;
	public static final int ASGDTIPOELEMENTO_TABLE = 206;
	public static final int ASGDUDOCENUI_TABLE = 207;
	public static final int ASGDUINSTALACION_TABLE = 208;

	/**
	 * TABLAS PARA FONDOS - 3XX
	 */
	public static final int ASGFARCHIVO_TABLE = 301;
	public static final int ASGFELEMENTOCF_TABLE = 302;
	public static final int ASGFENTPRODUCTORA_TABLE = 303;
	public static final int ASGFESTRUCTJNIVCF_TABLE = 304;
	public static final int ASGFFONDO_TABLE = 305;
	public static final int ASGFNIVELCF_TABLE = 306;
	public static final int ASGFORGPROD_TABLE = 307;
	public static final int ASGFPRODSERIE_TABLE = 308;
	public static final int ASGFSOLICITUDSERIE_TABLE = 309;
	public static final int ASGFUNIDADDOC_TABLE = 310;

	/**
	 * TABLAS PARA PRESTAMOS - 4XX
	 */
	public static final int ASGPBLOQUDOC_TABLE = 401;
	public static final int ASGPCONSULTA_TABLE = 402;
	public static final int ASGPMOTIVO_TABLE = 403;
	public static final int ASGPMOTIVOCONSULTA_TABLE = 404;
	public static final int ASGPMOTIVORECHAZO_TABLE = 405;
	public static final int ASGPPRESTAMO_TABLE = 406;
	public static final int ASGPPRORROGA_TABLE = 407;
	public static final int ASGPSNSEC_TABLE = 408;
	public static final int ASGPSOLICITUDUDOC_TABLE = 409;
	public static final int ASGPTEMA_TABLE = 410;

	/**
	 * TABLAS PARA TRANSFERENCIAS - 5XX
	 */
	public static final int ASGTDETALLEPREV_TABLE = 501;
	public static final int ASGTPREVISION_TABLE = 502;
	public static final int ASGTRENTREGA_TABLE = 503;
	public static final int ASGTSNSEC_TABLE = 504;
	public static final int ASGTSNSECUI_TABLE = 505;
	public static final int ASGTUDOCENUI_TABLE = 506;
	public static final int ASGTUINSTALACIONRE_TABLE = 507;
	public static final int ASGTUNIDADDOCRE_TABLE = 508;
	public static final int ASGUNIDADDOCUMENTAL_TABLE = 509;

	/**
	 * TABLAS PARA AUDITORIA - 6XX
	 */
	public static final int AATRAZA_TABLE = 601;
	public static final int AADATOSTRAZA_TABLE = 602;
	public static final int AACRITUSUARIOS_TABLE = 603;
	public static final int AACRITACCIONES_TABLE = 604;

	/**
	 * OTRAS TABLAS - 7XX
	 */
	public static final int ITDGUIDGEN_TABLE = 701;
	public static final int ARCHIVOFORMATO_TABLE = 702;

	/*********************************************
	 * Nombre tablas
	 *********************************************/
	/**
	 * DESCRIPCIÓN
	 */
	public static final String ADAREA_TABLE_NAME = "ADAREA";
	public static final String ADCAMPODATO_TABLE_NAME = "ADCAMPODATO";
	public static final String ADCAMPOTBL_TABLE_NAME = "ADCAMPOTBL";
	public static final String ADCTLGLISTAD_TABLE_NAME = "ADCTLGLISTAD";
	public static final String ADCTLGTBLVLD_TABLE_NAME = "ADCTLGTBLVLD";
	public static final String ADDESCRIPTOR_TABLE_NAME = "ADDESCRIPTOR";
	public static final String ADFICHA_TABLE_NAME = "ADFICHA";
	public static final String ADTEXTTBLVLD_TABLE_NAME = "ADTEXTTBLVLD";
	public static final String ADUSOOBJETO_TABLE_NAME = "ADUSOOBJETO";
	public static final String ADVCFECHACF_TABLE_NAME = "ADVCFECHACF";
	public static final String ADVCFECHADESCR_TABLE_NAME = "ADVCFECHADESCR";
	public static final String ADVCNUMCF_TABLE_NAME = "ADVCNUMCF";
	public static final String ADVCNUMDESCR_TABLE_NAME = "ADVCNUMDESCR";
	public static final String ADVCREFCF_TABLE_NAME = "ADVCREFCF";
	public static final String ADVCREFDESCR_TABLE_NAME = "ADVCREFDESCR";
	public static final String ADVCTEXTCF_TABLE_NAME = "ADVCTEXTCF";
	public static final String ADVCTEXTDESCR_TABLE_NAME = "ADVCTEXTDESCR";
	public static final String ADVCTEXTLCF_TABLE_NAME = "ADVCTEXTLCF";
	public static final String ADVCTEXTLDESCR_TABLE_NAME = "ADVCTEXTLDESCR";

	/**
	 * DEPOSITO
	 */
	public static final String ASGDDEPOSITO_TABLE_NAME = "ASGDDEPOSITO";
	public static final String ASGDELEMASIG_TABLE_NAME = "ASGDELEMASIG";
	public static final String ASGDELEMNOASIG_TABLE_NAME = "ASGDELEMNOASIG";
	public static final String ASGDHUECO_TABLE_NAME = "ASGDHUECO";
	public static final String ASGDSIGNUMORDEN_TABLE_NAME = "ASGDSIGNUMORDEN";
	public static final String ASGDTIPOELEMENTO_TABLE_NAME = "ASGDTIPOELEMENTO";
	public static final String ASGDUDOCENUI_TABLE_NAME = "ASGDUDOCENUI";
	public static final String ASGDUINSTALACION_TABLE_NAME = "ASGDUINSTALACION";

	/**
	 * FONDOS
	 */
	public static final String ASGFARCHIVO_TABLE_NAME = "ASGFARCHIVO";
	public static final String ASGFELEMENTOCF_TABLE_NAME = "ASGFELEMENTOCF";
	public static final String ASGFENTPRODUCTORA_TABLE_NAME = "ASGFENTPRODUCTORA";
	public static final String ASGFESTRUCTJNIVCF_TABLE_NAME = "ASGFESTRUCTJNIVCF";
	public static final String ASGFFONDO_TABLE_NAME = "ASGFFONDO";
	public static final String ASGFNIVELCF_TABLE_NAME = "ASGFNIVELCF";
	public static final String ASGFORGPROD_TABLE_NAME = "ASGFORGPROD";
	public static final String ASGFPRODSERIE_TABLE_NAME = "ASGFPRODSERIE";
	public static final String ASGFSOLICITUDSERIE_TABLE_NAME = "ASGFSOLICITUDSERIE";
	public static final String ASGFUNIDADDOC_TABLE_NAME = "ASGFUNIDADDOC";

	/**
	 * PRESTAMOS
	 */
	public static final String ASGPBLOQUDOC_TABLE_NAME = "ASGPBLOQUDOC";
	public static final String ASGPCONSULTA_TABLE_NAME = "ASGPCONSULTA";
	public static final String ASGPMOTIVO_TABLE_NAME = "ASGPMOTIVO";
	public static final String ASGPMOTIVOCONSULTA_TABLE_NAME = "ASGPMOTIVOCONSULTA";
	public static final String ASGPMOTIVORECHAZO_TABLE_NAME = "ASGPMOTIVORECHAZO";
	public static final String ASGPPRESTAMO_TABLE_NAME = "ASGPPRESTAMO";
	public static final String ASGPPRORROGA_TABLE_NAME = "ASGPPRORROGA";
	public static final String ASGPSNSEC_TABLE_NAME = "ASGPSNSEC";
	public static final String ASGPSOLICITUDUDOC_TABLE_NAME = "ASGPSOLICITUDUDOC";
	public static final String ASGPTEMA_TABLE_NAME = "ASGPTEMA";

	/**
	 * PARA TRANSFERENCIAS
	 */
	public static final String ASGTDETALLEPREV_TABLE_NAME = "ASGTDETALLEPREV";
	public static final String ASGTPREVISION_TABLE_NAME = "ASGTPREVISION";
	public static final String ASGTRENTREGA_TABLE_NAME = "ASGTRENTREGA";
	public static final String ASGTSNSEC_TABLE_NAME = "ASGTSNSEC";
	public static final String ASGTSNSECUI_TABLE_NAME = "ASGTSNSECUI";
	public static final String ASGTUDOCENUI_TABLE_NAME = "ASGTUDOCENUI";
	public static final String ASGTUINSTALACIONRE_TABLE_NAME = "ASGTUINSTALACIONRE";
	public static final String ASGTUNIDADDOCRE_TABLE_NAME = "ASGTUNIDADDOCRE";
	public static final String ASGUNIDADDOCUMENTAL_TABLE_NAME = "ASGUNIDADDOCUMENTAL";

	/**
	 * AUDITORIA
	 */
	public static final String AATRAZA_TABLE_NAME = "AATRAZA";
	public static final String AADATOSTRAZA_TABLE_NAME = "AADATOSTRAZA";
	public static final String AACRITUSUARIOS_TABLE_NAME = "AACRITUSUARIOS";
	public static final String AACRITACCIONES_TABLE_NAME = "AACRITACCIONES";

	/**
	 * OTRAS TABLAS
	 */
	public static final String ITDGUIDGEN_TABLE_NAME = "ITDGUIDGEN";
	public static final String ARCHIVOFORMATO_TABLE_NAME = "ARCHIVOFORMATO";

	public static final String AANAUDITACCION_TABLE_NAME = "AANAUDITACCION";
	public static final String AANAUDITUSR_TABLE_NAME = "AANAUDITUSR";
	public static final String AASESION_TABLE_NAME = "AASESION";
	public static final String ADFMTFICHA_TABLE_NAME = "ADFMTFICHA";
	public static final String ADFMTPREF_TABLE_NAME = "ADFMTPREF";
	public static final String ADOCCLASIFCF_TABLE_NAME = "ADOCCLASIFCF";
	public static final String ADOCCLASIFDESCR_TABLE_NAME = "ADOCCLASIFDESCR";
	public static final String ADOCDOCUMENTOCF_TABLE_NAME = "ADOCDOCUMENTOCF";
	public static final String ADOCDOCUMENTODESCR_TABLE_NAME = "ADOCDOCUMENTODESCR";
	public static final String ADOCFICHACLF_TABLE_NAME = "ADOCFICHACLF";
	public static final String ADOCTCAPTURA_TABLE_NAME = "ADOCTCAPTURA";
	public static final String ADPCDOCUMENTOVIT_TABLE_NAME = "ADPCDOCUMENTOVIT";
	public static final String ADPCTIPODOCPROC_TABLE_NAME = "ADPCTIPODOCPROC";
	public static final String ADPCTIPODOCVIT_TABLE_NAME = "ADPCTIPODOCVIT";
	public static final String ADPCUSODOCVIT_TABLE_NAME = "ADPCUSODOCVIT";
	public static final String AGNIVELARCHIVO_TABLE_NAME = "AGNIVELARCHIVO";
	public static final String AGARCHIVO_TABLE_NAME = "AGARCHIVO";
	public static final String AGFORMATO_TABLE_NAME = "AGFORMATO";
	public static final String AGOBJBLOQUEO_TABLE_NAME = "AGOBJBLOQUEO";
	public static final String ASCAGRUPO_TABLE_NAME = "ASCAGRUPO";
	public static final String ASCALISTCA_TABLE_NAME = "ASCALISTCA";
	public static final String ASCAORGANO_TABLE_NAME = "ASCAORGANO";
	public static final String ASCAPERMGENROL_TABLE_NAME = "ASCAPERMGENROL";
	public static final String ASCAPERMLISTCA_TABLE_NAME = "ASCAPERMLISTCA";
	public static final String ASCAROL_TABLE_NAME = "ASCAROL";
	public static final String ASCAROLUSR_TABLE_NAME = "ASCAROLUSR";
	public static final String ASCAUSRGRP_TABLE_NAME = "ASCAUSRGRP";
	public static final String ASCAUSRORG_TABLE_NAME = "ASCAUSRORG";
	public static final String ASCAUSUARIO_TABLE_NAME = "ASCAUSUARIO";
	public static final String ASGDDEPOSITOELECTRONICO_TABLE_NAME = "ASGDDEPOSITOELECTRONICO";
	public static final String ASGFELIMSELUDOC_TABLE_NAME = "ASGFELIMSELUDOC";
	public static final String ASGFELIMUDOCCONS_TABLE_NAME = "ASGFELIMUDOCCONS";
	public static final String ASGFELIMSERIE_TABLE_NAME = "ASGFELIMSERIE";
	public static final String ASGFHISTUDOC_TABLE_NAME = "ASGFHISTUDOC";
	public static final String ASGFNUMSECSEL_TABLE_NAME = "ASGFNUMSECSEL";
	public static final String ASGFNUMSECVAL_TABLE_NAME = "ASGFNUMSECVAL";
	public static final String ASGFSERIE_TABLE_NAME = "ASGFSERIE";
	public static final String ASGFSNSEC_TABLE_NAME = "ASGFSNSEC";
	public static final String ASGFVALSERIE_TABLE_NAME = "ASGFVALSERIE";
	public static final String ASGFVOLUMENSERIE_TABLE_NAME = "ASGFVOLUMENSERIE";
	public static final String ASGPMTVCONSULTA_TABLE_NAME = "ASGPMTVCONSULTA";
	public static final String ASGPMTVRECHAZO_TABLE_NAME = "ASGPMTVRECHAZO";
	public static final String ASGTMAPDESCRUDOC_TABLE_NAME = "ASGTMAPDESCRUDOC";
	public static final String ASGTSNSECUDOC_TABLE_NAME = "ASGTSNSECUDOC";
	public static final String ASGTUDOCSDF_TABLE_NAME = "ASGTUDOCSDF";
	public static final String ASGTUINSTALACIONREEA_TABLE_NAME = "ASGTUINSTALACIONREEA";
	public static final String ASGFUDOCSDF_TABLE_NAME = "ASGFUDOCSDF";
	public static final String ADVCFECHAUDOCRE_TABLE_NAME = "ADVCFECHAUDOCRE";
	public static final String ADVCNUMUDOCRE_TABLE_NAME = "ADVCNUMUDOCRE";
	public static final String ADVCREFUDOCRE_TABLE_NAME = "ADVCREFUDOCRE";
	public static final String ADVCTEXTLUDOCRE_TABLE_NAME = "ADVCTEXTLUDOCRE";
	public static final String ADVCTEXTUDOCRE_TABLE_NAME = "ADVCTEXTUDOCRE";
	public static final String ASGFDIVISIONFS_TABLE_NAME = "ASGFDIVISIONFS";
	public static final String ASGFUDOCENDIVISIONFS_TABLE_NAME = "ASGFUDOCENDIVISIONFS";
	public static final String ASGFPZTRVALSERIE_TABLE_NAME = "ASGFPZTRVALSERIE";
	public static final String ASGDSIGNUMHUECO_TABLE_NAME = "ASGDSIGNUMHUECO";
	public static final String ASGPREVDOCUDOC_TABLE_NAME = "ASGPREVDOCUDOC";
	public static final String ASGDHISTUINSTALACION_TABLE_NAME = "ASGDHISTUINSTALACION";
	public static final String ASGPMTVPRESTAMO_TABLE_NAME = "ASGPMTVPRESTAMO";
	public static final String ASGSEDIFICIO_TABLE_NAME = "ASGSEDIFICIO";
	public static final String ASGSSALA_TABLE_NAME = "ASGSSALA";
	public static final String ASGSMESA_TABLE_NAME = "ASGSMESA";
	public static final String ASGSUSRCSALA_TABLE_NAME = "ASGSUSRCSALA";
	public static final String ASGSUSRCSARCH_TABLE_NAME = "ASGSUSRCSARCH";
	public static final String ASGSREGCSALA_TABLE_NAME = "ASGSREGCSALA";
	public static final String ASGTUIREEACR_TABLE_NAME = "ASGTUIREEACR";
	public static final String ASGTUDOCENUIREEACR_TABLE_NAME = "ASGTUDOCENUIREEACR";
	public static final String ASGTUDOCREEACR_TABLE_NAME = "ASGTUDOCREEACR";
	public static final String ASGFCTLGTBLTMP_TABLE_NAME = "ASGFCTLGTBLTMP";
	public static final String AGINFOSISTEMA_TABLE_NAME = "AGINFOSISTEMA";

	/** Asociacion tabla/nombre */
	private static HashMap tableNames;

	static {
		tableNames = new HashMap();
		// DESCRIPCION
		tableNames.put(new Integer(ADAREA_TABLE), ADAREA_TABLE_NAME);
		tableNames.put(new Integer(ADCAMPODATO_TABLE), ADCAMPODATO_TABLE_NAME);
		tableNames.put(new Integer(ADCAMPOTBL_TABLE), ADCAMPOTBL_TABLE_NAME);
		tableNames
				.put(new Integer(ADCTLGLISTAD_TABLE), ADCTLGLISTAD_TABLE_NAME);
		tableNames
				.put(new Integer(ADCTLGTBLVLD_TABLE), ADCTLGTBLVLD_TABLE_NAME);
		tableNames
				.put(new Integer(ADDESCRIPTOR_TABLE), ADDESCRIPTOR_TABLE_NAME);
		tableNames.put(new Integer(ADFICHA_TABLE), ADFICHA_TABLE_NAME);
		tableNames
				.put(new Integer(ADTEXTTBLVLD_TABLE), ADTEXTTBLVLD_TABLE_NAME);
		tableNames.put(new Integer(ADUSOOBJETO_TABLE), ADUSOOBJETO_TABLE_NAME);
		tableNames.put(new Integer(ADVCFECHACF_TABLE), ADVCFECHACF_TABLE_NAME);
		tableNames.put(new Integer(ADVCFECHADESCR_TABLE),
				ADVCFECHADESCR_TABLE_NAME);
		tableNames.put(new Integer(ADVCNUMCF_TABLE), ADVCNUMCF_TABLE_NAME);
		tableNames
				.put(new Integer(ADVCNUMDESCR_TABLE), ADVCNUMDESCR_TABLE_NAME);
		tableNames.put(new Integer(ADVCREFCF_TABLE), ADVCREFCF_TABLE_NAME);
		tableNames
				.put(new Integer(ADVCREFDESCR_TABLE), ADVCREFDESCR_TABLE_NAME);
		tableNames.put(new Integer(ADVCTEXTCF_TABLE), ADVCTEXTCF_TABLE_NAME);
		tableNames.put(new Integer(ADVCTEXTDESCR_TABLE),
				ADVCTEXTDESCR_TABLE_NAME);
		tableNames.put(new Integer(ADVCTEXTLCF_TABLE), ADVCTEXTLCF_TABLE_NAME);
		tableNames.put(new Integer(ADVCTEXTLDESCR_TABLE),
				ADVCTEXTLDESCR_TABLE_NAME);
		// DEPOSITO
		tableNames
				.put(new Integer(ASGDDEPOSITO_TABLE), ASGDDEPOSITO_TABLE_NAME);
		tableNames
				.put(new Integer(ASGDELEMASIG_TABLE), ASGDELEMASIG_TABLE_NAME);
		tableNames.put(new Integer(ASGDELEMNOASIG_TABLE),
				ASGDELEMNOASIG_TABLE_NAME);
		tableNames.put(new Integer(ASGDHUECO_TABLE), ASGDHUECO_TABLE_NAME);
		tableNames.put(new Integer(ASGDSIGNUMORDEN_TABLE),
				ASGDSIGNUMORDEN_TABLE_NAME);
		tableNames.put(new Integer(ASGDTIPOELEMENTO_TABLE),
				ASGDTIPOELEMENTO_TABLE_NAME);
		tableNames
				.put(new Integer(ASGDUDOCENUI_TABLE), ASGDUDOCENUI_TABLE_NAME);
		tableNames.put(new Integer(ASGDUINSTALACION_TABLE),
				ASGDUINSTALACION_TABLE_NAME);
		// FONDOS
		tableNames.put(new Integer(ASGFARCHIVO_TABLE), ASGFARCHIVO_TABLE_NAME);
		tableNames.put(new Integer(ASGFELEMENTOCF_TABLE),
				ASGFELEMENTOCF_TABLE_NAME);
		tableNames.put(new Integer(ASGFENTPRODUCTORA_TABLE),
				ASGFENTPRODUCTORA_TABLE_NAME);
		tableNames.put(new Integer(ASGFESTRUCTJNIVCF_TABLE),
				ASGFESTRUCTJNIVCF_TABLE_NAME);
		tableNames.put(new Integer(ASGFFONDO_TABLE), ASGFFONDO_TABLE_NAME);
		tableNames.put(new Integer(ASGFNIVELCF_TABLE), ASGFNIVELCF_TABLE_NAME);
		tableNames.put(new Integer(ASGFORGPROD_TABLE), ASGFORGPROD_TABLE_NAME);
		tableNames.put(new Integer(ASGFPRODSERIE_TABLE),
				ASGFPRODSERIE_TABLE_NAME);
		tableNames.put(new Integer(ASGFSOLICITUDSERIE_TABLE),
				ASGFSOLICITUDSERIE_TABLE_NAME);
		tableNames.put(new Integer(ASGFUNIDADDOC_TABLE),
				ASGFUNIDADDOC_TABLE_NAME);
		// PRESTAMOS
		tableNames
				.put(new Integer(ASGPBLOQUDOC_TABLE), ASGPBLOQUDOC_TABLE_NAME);
		tableNames
				.put(new Integer(ASGPCONSULTA_TABLE), ASGPCONSULTA_TABLE_NAME);
		tableNames.put(new Integer(ASGPMOTIVO_TABLE), ASGPMOTIVO_TABLE_NAME);
		tableNames.put(new Integer(ASGPMOTIVOCONSULTA_TABLE),
				ASGPMOTIVOCONSULTA_TABLE_NAME);
		tableNames.put(new Integer(ASGPMOTIVORECHAZO_TABLE),
				ASGPMOTIVORECHAZO_TABLE_NAME);
		tableNames
				.put(new Integer(ASGPPRESTAMO_TABLE), ASGPPRESTAMO_TABLE_NAME);
		tableNames
				.put(new Integer(ASGPPRORROGA_TABLE), ASGPPRORROGA_TABLE_NAME);
		tableNames.put(new Integer(ASGPSNSEC_TABLE), ASGPSNSEC_TABLE_NAME);
		tableNames.put(new Integer(ASGPSOLICITUDUDOC_TABLE),
				ASGPSOLICITUDUDOC_TABLE_NAME);
		tableNames.put(new Integer(ASGPTEMA_TABLE), ASGPTEMA_TABLE_NAME);
		// TRANSFERENCIAS
		tableNames.put(new Integer(ASGTDETALLEPREV_TABLE),
				ASGTDETALLEPREV_TABLE_NAME);
		tableNames.put(new Integer(ASGTPREVISION_TABLE),
				ASGTPREVISION_TABLE_NAME);
		tableNames
				.put(new Integer(ASGTRENTREGA_TABLE), ASGTRENTREGA_TABLE_NAME);
		tableNames.put(new Integer(ASGTSNSEC_TABLE), ASGTSNSEC_TABLE_NAME);
		tableNames.put(new Integer(ASGTSNSECUI_TABLE), ASGTSNSECUI_TABLE_NAME);
		tableNames
				.put(new Integer(ASGTUDOCENUI_TABLE), ASGTUDOCENUI_TABLE_NAME);
		tableNames.put(new Integer(ASGTUINSTALACIONRE_TABLE),
				ASGTUINSTALACIONRE_TABLE_NAME);
		tableNames.put(new Integer(ASGTUNIDADDOCRE_TABLE),
				ASGTUNIDADDOCRE_TABLE_NAME);
		tableNames.put(new Integer(ASGUNIDADDOCUMENTAL_TABLE),
				ASGUNIDADDOCUMENTAL_TABLE_NAME);
		// AUDITORIA
		tableNames.put(new Integer(AATRAZA_TABLE), AATRAZA_TABLE_NAME);
		tableNames
				.put(new Integer(AADATOSTRAZA_TABLE), AADATOSTRAZA_TABLE_NAME);
		tableNames.put(new Integer(AACRITUSUARIOS_TABLE),
				AACRITUSUARIOS_TABLE_NAME);
		tableNames.put(new Integer(AACRITACCIONES_TABLE),
				AACRITACCIONES_TABLE_NAME);
		// OTRAS TABLAS
		tableNames.put(new Integer(ITDGUIDGEN_TABLE), ITDGUIDGEN_TABLE_NAME);
		tableNames.put(new Integer(ARCHIVOFORMATO_TABLE),
				ARCHIVOFORMATO_TABLE_NAME);
	}

	/**
	 * Devuelve el nombre asociado a la tabla pasada o null en caso de no
	 * existir la tabla.
	 * 
	 * @param table
	 *            Tabla de la que deseamos obtener el nombre
	 * @return Nombre de la tabla
	 */
	public static String getTableName(int table) {
		return (String) tableNames.get(new Integer(table));
	}

	/**
	 * Obtiene un listado de las tablas existentes.
	 * 
	 * @return Listado de las tablas {@link auditoria.vos.ArchivoTable}
	 *         existentes.
	 */
	public static Collection getTableNames() {
		Integer i = null;
		String name = null;
		ArchivoTable table = null;
		ArrayList result = new ArrayList();
		Iterator it = tableNames.keySet().iterator();

		while (it.hasNext()) {
			i = (Integer) it.next();

			name = (String) tableNames.get(i);

			table = new ArchivoTable();
			table.setId(i.intValue());
			table.setName(name);

			result.add(table);
		}

		return result;
	}
}
