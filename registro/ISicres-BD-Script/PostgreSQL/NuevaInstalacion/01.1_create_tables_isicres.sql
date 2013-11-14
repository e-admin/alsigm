CREATE TABLE scr_actws (
    idpuesto integer NOT NULL,
    idcambio integer NOT NULL
);


CREATE TABLE scr_address (
    id integer NOT NULL,
    id_person integer NOT NULL,
    type integer NOT NULL
);


CREATE TABLE scr_addrtel (
    id integer NOT NULL,
    address character varying(160) NOT NULL,
    type integer NOT NULL,
    preference integer NOT NULL
);


CREATE TABLE scr_bookadmin (
    idbook integer NOT NULL,
    iduser integer NOT NULL
);


CREATE TABLE scr_bookasoc (
    id_book integer NOT NULL,
    id_booksec integer NOT NULL,
    type integer NOT NULL
);


CREATE TABLE scr_bookofic (
    id integer NOT NULL,
    id_book integer NOT NULL,
    id_ofic integer NOT NULL,
    numeration integer NOT NULL
);


CREATE TABLE scr_ca (
    id integer NOT NULL,
    code character varying(16) NOT NULL,
    matter character varying(250) NOT NULL,
    for_ereg integer,
    for_sreg integer,
    all_ofics integer,
    id_arch integer NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    disable_date timestamp without time zone,
    enabled integer,
    id_org integer NOT NULL
);

CREATE TABLE scr_caadm (
    id integer NOT NULL,
    iduser integer NOT NULL,
    idca integer NOT NULL
);

CREATE TABLE scr_cadocs (
    id integer NOT NULL,
    id_matter integer NOT NULL,
    description character varying(32) NOT NULL,
    mandatory integer
);

CREATE TABLE scr_caofic (
    id integer NOT NULL,
    id_matter integer NOT NULL,
    id_ofic integer NOT NULL
);

CREATE TABLE scr_cities (
    id integer NOT NULL,
    tmstamp timestamp without time zone NOT NULL,
    code character varying(16) NOT NULL,
    name character varying(40) NOT NULL,
    id_prov integer NOT NULL
);


CREATE TABLE scr_cntcentral (
    an integer NOT NULL,
    num_reg integer NOT NULL,
    id_arch integer NOT NULL
);


CREATE TABLE scr_cntlocal (
    an integer NOT NULL,
    puesto integer NOT NULL,
    num_reg integer NOT NULL,
    id_arch integer NOT NULL
);

CREATE TABLE scr_cntoficina (
    an integer NOT NULL,
    oficina integer NOT NULL,
    num_reg integer NOT NULL,
    id_arch integer NOT NULL
);

CREATE TABLE scr_cntordinal (
    contador integer NOT NULL,
    hora character varying(4) NOT NULL
);


CREATE TABLE scr_cntrefcierre (
    tipo character varying(3) NOT NULL,
    contador integer NOT NULL
);

CREATE TABLE scr_configuration (
    id integer NOT NULL,
    version character varying(10) NOT NULL,
    options text NOT NULL
);

CREATE TABLE scr_caaux (
    id integer NOT NULL,
    id_matter integer NOT NULL,
    datos_aux text NOT NULL
);

CREATE TABLE scr_bookrepository (
    bookid integer NOT NULL,
    configuration text
);

CREATE TABLE scr_booktypeconfig (
    booktype integer NOT NULL,
    options text NOT NULL
);

CREATE TABLE scr_bookconfig (
    bookid integer NOT NULL,
    options text NOT NULL
);


CREATE TABLE scr_contador (
    tablaid character varying(30) NOT NULL,
    contador integer NOT NULL
);

CREATE TABLE scr_deltas (
    idcambio integer NOT NULL,
    tabla character varying(20) NOT NULL,
    idregistro integer NOT NULL,
    tipocambio integer NOT NULL
);

CREATE TABLE scr_dirofic (
    id_ofic integer NOT NULL,
    address character varying(255),
    city character varying(100),
    zip character varying(10),
    country character varying(100),
    telephone character varying(160),
    fax character varying(160),
    email character varying(255),
    signer character varying(160)
);

CREATE TABLE scr_dirorgs (
    id_orgs integer NOT NULL,
    address character varying(255),
    city character varying(100),
    zip character varying(10),
    country character varying(100),
    telephone character varying(160),
    fax character varying(160),
    email character varying(255)
);


CREATE TABLE scr_distaccept (
    bookid integer NOT NULL,
    regid integer NOT NULL,
    officeid integer NOT NULL,
    distdate timestamp without time zone NOT NULL,
    accdate timestamp without time zone NOT NULL,
    accuser character varying(80) NOT NULL,
    state integer NOT NULL,
    data text
);

CREATE TABLE scr_distlist (
    id integer NOT NULL,
    id_orgs integer NOT NULL,
    type_dest integer NOT NULL,
    id_dest integer NOT NULL
);

CREATE TABLE scr_distreg (
    id integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    dist_date timestamp without time zone NOT NULL,
    type_orig integer NOT NULL,
    id_orig integer NOT NULL,
    type_dest integer NOT NULL,
    id_dest integer NOT NULL,
    state integer NOT NULL,
    state_date timestamp without time zone NOT NULL,
    message character varying(250),
    id_dist_father integer
);

CREATE TABLE scr_distregstate (
    id integer NOT NULL,
    id_dist integer NOT NULL,
    state integer NOT NULL,
    state_date timestamp without time zone NOT NULL,
    username character varying(32) NOT NULL,
    message character varying(250)
);

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist integer NOT NULL,
    dist_actual text
);

CREATE TABLE scr_dom (
    id integer NOT NULL,
    address character varying(160) NOT NULL,
    city character varying(40),
    zip character varying(5),
    country character varying(30),
    preference integer NOT NULL
);

CREATE TABLE scr_entregsnd (
    id_entreg integer NOT NULL,
    transport_type integer NOT NULL,
    encoding_msg integer NOT NULL,
    certificate_issuer character varying(255),
    certificate_nserial character varying(255)
);

CREATE TABLE scr_expcnts (
    id integer NOT NULL,
    id_task integer NOT NULL,
    username character varying(32) NOT NULL,
    password character varying(34) NOT NULL
);

CREATE TABLE scr_exptasks (
    id integer NOT NULL,
    id_user integer NOT NULL,
    description character varying(50) NOT NULL,
    id_sourcearch integer NOT NULL,
    id_targetarch integer NOT NULL,
    id_targetdb integer NOT NULL,
    id_filter integer NOT NULL,
    id_connection integer NOT NULL,
    status integer NOT NULL,
    delete_source integer NOT NULL,
    explastdate timestamp without time zone
);

CREATE TABLE scr_fldaccperms (
    archivetype integer NOT NULL,
    idfld integer NOT NULL,
    userperms integer NOT NULL,
    adminperms integer NOT NULL,
    suserperms integer NOT NULL
);

CREATE TABLE scr_fldpermarch (
    idarchive integer NOT NULL,
    idfld integer NOT NULL,
    userperms integer NOT NULL,
    adminperms integer NOT NULL,
    suserperms integer NOT NULL
);

CREATE TABLE scr_fldpermuser (
    idarchive integer NOT NULL,
    idfld integer NOT NULL,
    iduser integer NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE scr_genperms (
    id integer NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE scr_lastconnection (
    iduser integer NOT NULL,
    connect_date timestamp without time zone NOT NULL
);

CREATE TABLE scr_expfilters (
    id integer NOT NULL,
    id_task integer NOT NULL,
    filter text NOT NULL
);

CREATE TABLE scr_lastregister (
    bookid integer NOT NULL,
    fdrid integer NOT NULL,
    userid integer NOT NULL
);

CREATE TABLE scr_ofic (
    id integer NOT NULL,
    code character varying(10) NOT NULL,
    acron character varying(12) NOT NULL,
    name character varying(32) NOT NULL,
    creation_date timestamp without time zone,
    disable_date timestamp without time zone,
    id_orgs integer,
    stamp character varying(240),
    deptid integer NOT NULL,
    type integer NOT NULL
);

CREATE TABLE scr_orgs (
    id integer NOT NULL,
    code character varying(16) NOT NULL,
    id_father integer,
    acron character varying(12) NOT NULL,
    name character varying(250) NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    disable_date timestamp without time zone,
    type integer NOT NULL,
    enabled integer NOT NULL,
    cif character varying(15)
);

CREATE TABLE scr_lockrelations (
    typebook integer NOT NULL,
    typerel integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_modifreg (
    id integer NOT NULL,
    usr character varying(32) NOT NULL,
    modif_date timestamp without time zone NOT NULL,
    id_fld integer NOT NULL,
    num_reg character varying(20) NOT NULL,
    id_arch integer NOT NULL,
    id_ofic integer NOT NULL,
    modif_type integer NOT NULL
);

CREATE TABLE scr_usrident (
    userid integer NOT NULL,
    tmstamp timestamp without time zone NOT NULL,
    first_name character varying(25) NOT NULL,
    second_name character varying(25),
    surname character varying(20) NOT NULL
);

CREATE TABLE scr_nackrecvmsg (
    id integer,
    id_msg integer NOT NULL,
    id_sendmsg integer NOT NULL,
    reason character varying(254)
);

CREATE TABLE scr_nackrecvreg (
    id integer NOT NULL,
    id_reg integer NOT NULL,
    id_sendmsg integer NOT NULL,
    reason character varying(254)
);

CREATE TABLE scr_nacksendmsg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_rcvmsg integer NOT NULL,
    reason character varying(254)
);

CREATE TABLE scr_nacksendreg (
    id integer NOT NULL,
    id_reg integer NOT NULL,
    id_rcvmsg integer NOT NULL,
    reason character varying(254)
);

CREATE TABLE scr_oficadm (
    id integer NOT NULL,
    iduser integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_pageinfo (
    bookid integer NOT NULL,
    regid integer NOT NULL,
    pageid integer NOT NULL,
    hashversion integer NOT NULL,
    hash text NOT NULL
);

CREATE TABLE scr_pagerepository (
    bookid integer NOT NULL,
    fdrid integer NOT NULL,
    pageid integer NOT NULL,
    docuid character varying(50) NOT NULL,
    id_pagetype integer NOT NULL default 0
);

CREATE TABLE scr_pagetype (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);

CREATE TABLE scr_pfis (
    id integer NOT NULL,
    type_doc integer,
    nif character varying(25),
    first_name character varying(30) NOT NULL,
    second_name character varying(30),
    surname character varying(30)
);

CREATE TABLE scr_pinfo (
    id integer NOT NULL,
    optype integer NOT NULL,
    officeid integer NOT NULL,
    username character varying(32) NOT NULL,
    opdate timestamp without time zone NOT NULL
);

CREATE TABLE scr_pjur (
    id integer NOT NULL,
    type_doc integer,
    cif character varying(17),
    name character varying(80) NOT NULL
);

CREATE TABLE scr_privorgs (
    id integer NOT NULL,
    idorgs integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_procreg (
    id integer NOT NULL,
    id_dist integer NOT NULL,
    id_proc integer NOT NULL,
    type integer NOT NULL
);

CREATE TABLE scr_prov (
    id integer NOT NULL,
    tmstamp timestamp without time zone NOT NULL,
    code character varying(16) NOT NULL,
    name character varying(30) NOT NULL
);

CREATE TABLE scr_recvmsg (
    id integer NOT NULL,
    uid_msg character varying(40) NOT NULL,
    type_msg integer NOT NULL,
    file_name character varying(40) NOT NULL,
    sender integer NOT NULL,
    destination integer NOT NULL,
    rcv_type integer NOT NULL,
    rcv_date timestamp without time zone NOT NULL,
    rcv_state integer NOT NULL,
    id_ackmsg integer
);

CREATE TABLE scr_recvreg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    regorig_code character varying(16) NOT NULL,
    regorig_num character varying(20) NOT NULL,
    regorig_date timestamp without time zone NOT NULL,
    regorig_type integer NOT NULL,
    attach_pages integer NOT NULL,
    attach_doc integer NOT NULL,
    id_ackmsg integer,
    rcv_state integer NOT NULL,
    rcv_numreg character varying(20),
    rcv_datereg timestamp without time zone
);

CREATE TABLE scr_regasoc (
    id integer NOT NULL,
    id_archprim integer NOT NULL,
    id_fdrprim integer NOT NULL,
    id_archsec integer NOT NULL,
    id_fdrsec integer NOT NULL
);

CREATE TABLE scr_regasocex (
    id_asoc integer NOT NULL,
    type integer NOT NULL
);

CREATE TABLE scr_regint (
    id integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    name character varying(95) NOT NULL,
    id_person integer NOT NULL,
    id_address integer,
    ord integer NOT NULL
);

CREATE TABLE scr_regpdocs (
    id integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    name_doc character varying(250) NOT NULL,
    presented integer NOT NULL
);

CREATE TABLE scr_regstate (
    id integer NOT NULL,
    idarchreg integer NOT NULL,
    state integer NOT NULL,
    closedate timestamp without time zone,
    closeuser character varying(32),
    numeration_type integer NOT NULL,
    image_auth integer NOT NULL
);

CREATE TABLE scr_relations (
    typebook integer NOT NULL,
    typerel integer NOT NULL,
    relyear integer NOT NULL,
    relmonth integer NOT NULL,
    relday integer NOT NULL,
    idofic integer NOT NULL,
    reldate timestamp without time zone NOT NULL,
    idunit integer NOT NULL,
    nrel integer NOT NULL
);

CREATE TABLE scr_reports (
    id integer NOT NULL,
    report character varying(250) NOT NULL,
    type_report integer NOT NULL,
    type_arch integer NOT NULL,
    all_arch integer NOT NULL,
    all_ofics integer NOT NULL,
    all_perfs integer NOT NULL,
    description character varying(250) NOT NULL,
    data bytea NOT NULL
);

CREATE TABLE scr_reportarch (
    id integer NOT NULL,
    id_report integer NOT NULL,
    id_arch integer NOT NULL
);

CREATE TABLE scr_reportofic (
    id integer NOT NULL,
    id_report integer NOT NULL,
    id_ofic integer NOT NULL
);

CREATE TABLE scr_reportperf (
    id integer NOT NULL,
    id_report integer NOT NULL,
    id_perf integer NOT NULL
);

CREATE TABLE scr_repository (
    id integer NOT NULL,
    type integer NOT NULL,
    configuration text NOT NULL
);

CREATE TABLE scr_sendmsg (
    id integer NOT NULL,
    uid_msg character varying(40) NOT NULL,
    type_msg integer NOT NULL,
    file_name character varying(40),
    sender integer NOT NULL,
    destination integer NOT NULL,
    send_type integer NOT NULL,
    gen_date timestamp without time zone NOT NULL,
    send_date timestamp without time zone,
    rcv_date timestamp without time zone,
    send_state integer NOT NULL,
    id_ackmsg integer,
    send_num integer NOT NULL
);

CREATE TABLE scr_sendreg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    regorig_code character varying(16) NOT NULL,
    regorig_num character varying(20) NOT NULL,
    regorig_date timestamp without time zone NOT NULL,
    regorig_type integer NOT NULL,
    attach_pages integer NOT NULL,
    attach_doc integer NOT NULL,
    id_ackmsg integer,
    rcv_state integer NOT NULL,
    id_entreg_dest integer NOT NULL,
    rcv_numreg character varying(20),
    rcv_datereg timestamp without time zone
);

CREATE TABLE scr_sharedfiles (
    fileid integer NOT NULL,
    ownerbookid integer NOT NULL,
    ownerregid integer NOT NULL,
    bookid integer NOT NULL,
    regid integer NOT NULL
);

CREATE TABLE scr_tmzofic (
    id integer NOT NULL,
    tmz integer NOT NULL
);

CREATE TABLE scr_translatedfmt (
    id integer NOT NULL,
    langid integer NOT NULL,
    spa_text character varying(254) NOT NULL,
    trans_text character varying(254) NOT NULL
);

CREATE TABLE scr_tt (
    id integer NOT NULL,
    transport character varying(31) NOT NULL
);

CREATE TABLE scr_typeaddress (
    id integer NOT NULL,
    description character varying(50) NOT NULL,
    code character varying(2) NOT NULL
);

CREATE TABLE scr_typeadm (
    id integer NOT NULL,
    code character varying(1) NOT NULL,
    description character varying(50) NOT NULL
);

CREATE TABLE scr_typedoc (
    id integer NOT NULL,
    description character varying(50) NOT NULL,
    type_person integer NOT NULL,
    code character varying(1) NOT NULL
);

CREATE TABLE scr_typeofic (
    id integer NOT NULL,
    description character varying(50) NOT NULL
);

CREATE TABLE scr_userconfig (
    userid integer NOT NULL,
    data text NOT NULL,
    idoficpref integer
);

CREATE TABLE scr_userfilter (
    idarch integer NOT NULL,
    iduser integer NOT NULL,
    filterdef text,
    type_obj integer NOT NULL
);

CREATE TABLE scr_usrloc (
    userid integer NOT NULL,
    tmstamp timestamp without time zone NOT NULL,
    address character varying(255),
    city character varying(100),
    zip character varying(10),
    country character varying(100),
    telephone character varying(160),
    fax character varying(160),
    email character varying(255)
);

CREATE TABLE scr_usrofic (
    id integer NOT NULL,
    iduser integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_usrperms (
    id_usr integer NOT NULL,
    tmstamp timestamp without time zone NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE scr_valdate (
    id integer NOT NULL,
    value timestamp without time zone,
    oldvalue timestamp without time zone
);

CREATE TABLE scr_valnum (
    id integer NOT NULL,
    value integer,
    oldvalue integer
);

CREATE TABLE scr_valstr (
    id integer NOT NULL,
    value character varying(250),
    oldvalue character varying(250)
);

CREATE TABLE scr_ws (
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    macaddr character varying(32),
    ipaddr character varying(20),
    idofic integer,
    code character varying(4) NOT NULL,
    descripcion character varying(250)
);

CREATE TABLE scr_unitadm (
    userid integer NOT NULL,
    objid integer NOT NULL,
    objtype integer NOT NULL
);

CREATE TABLE scr_lockitems (
    objtype integer NOT NULL,
    objid integer NOT NULL,
    userid integer NOT NULL,
    lockdate timestamp without time zone NOT NULL
);

CREATE TABLE scr_versionitems (
    objtype integer NOT NULL,
    code character varying(32) NOT NULL,
    objid integer NOT NULL,
    version integer NOT NULL,
    userid integer NOT NULL,
    versiondate timestamp without time zone NOT NULL
);

CREATE TABLE scr_orgslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(250) NOT NULL
);

CREATE TABLE scr_typeadmlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(50) NOT NULL
);

CREATE TABLE scr_oficlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(32) NOT NULL
);

CREATE TABLE scr_calang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(250) NOT NULL
);

CREATE TABLE scr_ttlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(31) NOT NULL
);

CREATE TABLE scr_reportslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(250) NOT NULL
);

CREATE TABLE scr_bookslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name character varying(250) NOT NULL
);

CREATE TABLE scr_repositoryconf (
	id integer NOT NULL,
	data text NOT NULL
);

CREATE TABLE scr_repobooktype (
	book_type integer NOT NULL,
	id_rep_conf integer NOT NULL
);



CREATE TABLE scr_doclocator
(
	bookID integer NOT NULL,
	folderID integer NOT NULL,
	pageID integer NOT NULL,
	locator character varying(128) NOT NULL
);

-- SEQUENCE

CREATE SEQUENCE scr_seqcnt
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;