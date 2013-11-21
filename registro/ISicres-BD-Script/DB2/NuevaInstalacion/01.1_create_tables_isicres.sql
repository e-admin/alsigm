
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
    address varchar(160) NOT NULL,
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
    code varchar(16) NOT NULL,
    matter varchar(250) NOT NULL,
    for_ereg integer,
    for_sreg integer,
    all_ofics integer,
    id_arch integer NOT NULL,
    creation_date timestamp NOT NULL,
    disable_date timestamp,
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
    description varchar(32) NOT NULL,
    mandatory integer
);

CREATE TABLE scr_caofic (
    id integer NOT NULL,
    id_matter integer NOT NULL,
    id_ofic integer NOT NULL
);

CREATE TABLE scr_cities (
    id integer NOT NULL,
    tmstamp timestamp NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(40) NOT NULL,
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
    hora varchar(4) NOT NULL
);

CREATE TABLE scr_cntrefcierre (
    tipo varchar(3) NOT NULL,
    contador integer NOT NULL
);


CREATE TABLE scr_configuration (
    id integer NOT NULL,
    version varchar(10) NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_caaux (
    id integer NOT NULL,
    id_matter integer NOT NULL,
    datos_aux clob NOT NULL
);

CREATE TABLE scr_bookrepository (
    bookid integer NOT NULL,
    configuration clob
);

CREATE TABLE scr_booktypeconfig (
    booktype integer NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_bookconfig (
    bookid integer NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_contador (
    tablaid varchar(30) NOT NULL,
    contador integer NOT NULL
);

CREATE TABLE scr_deltas (
    idcambio integer NOT NULL,
    tabla varchar(20) NOT NULL,
    idregistro integer NOT NULL,
    tipocambio integer NOT NULL
);

CREATE TABLE scr_dirofic (
    id_ofic integer NOT NULL,
    address varchar(255),
    city varchar(100),
    zip varchar(10),
    country varchar(100),
    telephone varchar(160),
    fax varchar(160),
    email varchar(255),
    signer varchar(160)
);

CREATE TABLE scr_dirorgs (
    id_orgs integer NOT NULL,
    address varchar(255),
    city varchar(100),
    zip varchar(10),
    country varchar(100),
    telephone varchar(160),
    fax varchar(160),
    email varchar(255)
);

CREATE TABLE scr_distaccept (
    bookid integer NOT NULL,
    regid integer NOT NULL,
    officeid integer NOT NULL,
    distdate timestamp NOT NULL,
    accdate timestamp NOT NULL,
    accuser varchar(80) NOT NULL,
    state integer NOT NULL,
    data clob
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
    dist_date timestamp NOT NULL,
    type_orig integer NOT NULL,
    id_orig integer NOT NULL,
    type_dest integer NOT NULL,
    id_dest integer NOT NULL,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    message varchar(250),
    id_dist_father integer
);

CREATE TABLE scr_distregstate (
    id integer NOT NULL,
    id_dist integer NOT NULL,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    username varchar(32) NOT NULL,
    message varchar(250)
);

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist integer NOT NULL,
    dist_actual clob
);


CREATE TABLE scr_dom (
    id integer NOT NULL,
    address varchar(100) NOT NULL,
    city varchar(40),
    zip varchar(5),
    country varchar(30),
    preference integer NOT NULL
);

CREATE TABLE scr_entregsnd (
    id_entreg integer NOT NULL,
    transport_type integer NOT NULL,
    encoding_msg integer NOT NULL,
    certificate_issuer varchar(255),
    certificate_nserial varchar(255)
);

CREATE TABLE scr_expcnts (
    id integer NOT NULL,
    id_task integer NOT NULL,
    username varchar(32) NOT NULL,
    password varchar(34) NOT NULL
);

CREATE TABLE scr_exptasks (
    id integer NOT NULL,
    id_user integer NOT NULL,
    description varchar(50) NOT NULL,
    id_sourcearch integer NOT NULL,
    id_targetarch integer NOT NULL,
    id_targetdb integer NOT NULL,
    id_filter integer NOT NULL,
    id_connection integer NOT NULL,
    status integer NOT NULL,
    delete_source integer NOT NULL,
    explastdate timestamp
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
    connect_date timestamp NOT NULL
);

CREATE TABLE scr_expfilters (
    id integer NOT NULL,
    id_task integer NOT NULL,
    filter clob NOT NULL
);


CREATE TABLE scr_lastregister (
    bookid integer NOT NULL,
    fdrid integer NOT NULL,
    userid integer NOT NULL
);

CREATE TABLE scr_ofic (
    id integer NOT NULL,
    code varchar(10) NOT NULL,
    acron varchar(12) NOT NULL,
    name varchar(32) NOT NULL,
    creation_date timestamp,
    disable_date timestamp,
    id_orgs integer,
    stamp varchar(240),
    deptid integer NOT NULL,
    type integer NOT NULL
);

CREATE TABLE scr_orgs (
    id integer NOT NULL,
    code varchar(16) NOT NULL,
    id_father integer,
    acron varchar(12) NOT NULL,
    name varchar(250) NOT NULL,
    creation_date timestamp NOT NULL,
    disable_date timestamp,
    type integer NOT NULL,
    enabled integer NOT NULL,
    cif varchar(15)
);



CREATE TABLE scr_lockrelations (
    typebook integer NOT NULL,
    typerel integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_modifreg (
    id integer NOT NULL,
    usr varchar(32) NOT NULL,
    modif_date timestamp NOT NULL,
    id_fld integer NOT NULL,
    num_reg varchar(20) NOT NULL,
    id_arch integer NOT NULL,
    id_ofic integer NOT NULL,
    modif_type integer NOT NULL
);

CREATE TABLE scr_usrident (
    userid integer NOT NULL,
    tmstamp timestamp NOT NULL,
    first_name varchar(25) NOT NULL,
    second_name varchar(25),
    surname varchar(20) NOT NULL
);



CREATE TABLE scr_nackrecvmsg (
    id integer,
    id_msg integer NOT NULL,
    id_sendmsg integer NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nackrecvreg (
    id integer NOT NULL,
    id_reg integer NOT NULL,
    id_sendmsg integer NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nacksendmsg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_rcvmsg integer NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nacksendreg (
    id integer NOT NULL,
    id_reg integer NOT NULL,
    id_rcvmsg integer NOT NULL,
    reason varchar(254)
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
    hash clob NOT NULL
);

CREATE TABLE scr_pagerepository (
    bookid integer NOT NULL,
    fdrid integer NOT NULL,
    pageid integer NOT NULL,
    docuid varchar(50) NOT NULL,
    id_pagetype integer NOT NULL default 0
);

CREATE TABLE scr_pagetype (
    id integer NOT NULL,
    name varchar(100) NOT NULL
);

CREATE TABLE scr_pfis (
    id integer NOT NULL,
    type_doc integer,
    nif varchar(25),
    first_name varchar(30) NOT NULL,
    second_name varchar(30),
    surname varchar(30)
);

CREATE TABLE scr_pinfo (
    id integer NOT NULL,
    optype integer NOT NULL,
    officeid integer NOT NULL,
    username varchar(32) NOT NULL,
    opdate timestamp NOT NULL
);

CREATE TABLE scr_pjur (
    id integer NOT NULL,
    type_doc integer,
    cif varchar(17),
    name varchar(80) NOT NULL
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
    tmstamp timestamp NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(30) NOT NULL
);

CREATE TABLE scr_recvmsg (
    id integer NOT NULL,
    uid_msg varchar(40) NOT NULL,
    type_msg integer NOT NULL,
    file_name varchar(40) NOT NULL,
    sender integer NOT NULL,
    destination integer NOT NULL,
    rcv_type integer NOT NULL,
    rcv_date timestamp NOT NULL,
    rcv_state integer NOT NULL,
    id_ackmsg integer
);

CREATE TABLE scr_recvreg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    regorig_code varchar(16) NOT NULL,
    regorig_num varchar(20) NOT NULL,
    regorig_date timestamp NOT NULL,
    regorig_type integer NOT NULL,
    attach_pages integer NOT NULL,
    attach_doc integer NOT NULL,
    id_ackmsg integer,
    rcv_state integer NOT NULL,
    rcv_numreg varchar(20),
    rcv_datereg timestamp
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
    name varchar(95) NOT NULL,
    id_person integer NOT NULL,
    id_address integer,
    ord integer NOT NULL
);

CREATE TABLE scr_regpdocs (
    id integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    name_doc varchar(250) NOT NULL,
    presented integer NOT NULL
);

CREATE TABLE scr_regstate (
    id integer NOT NULL,
    idarchreg integer NOT NULL,
    state integer NOT NULL,
    closedate timestamp,
    closeuser varchar(32),
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
    reldate timestamp NOT NULL,
    idunit integer NOT NULL,
    nrel integer NOT NULL
);

CREATE TABLE scr_reports (
    id integer NOT NULL,
    report varchar(250) NOT NULL,
    type_report integer NOT NULL,
    type_arch integer NOT NULL,
    all_arch integer NOT NULL,
    all_ofics integer NOT NULL,
    all_perfs integer NOT NULL,
    description varchar(250) NOT NULL,
    data blob(307200) NOT NULL
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
    configuration clob NOT NULL
);

CREATE TABLE scr_sendmsg (
    id integer NOT NULL,
    uid_msg varchar(40) NOT NULL,
    type_msg integer NOT NULL,
    file_name varchar(40),
    sender integer NOT NULL,
    destination integer NOT NULL,
    send_type integer NOT NULL,
    gen_date timestamp NOT NULL,
    send_date timestamp,
    rcv_date timestamp,
    send_state integer NOT NULL,
    id_ackmsg integer,
    send_num integer NOT NULL
);

CREATE TABLE scr_sendreg (
    id integer NOT NULL,
    id_msg integer NOT NULL,
    id_arch integer NOT NULL,
    id_fdr integer NOT NULL,
    regorig_code varchar(16) NOT NULL,
    regorig_num varchar(20) NOT NULL,
    regorig_date timestamp NOT NULL,
    regorig_type integer NOT NULL,
    attach_pages integer NOT NULL,
    attach_doc integer NOT NULL,
    id_ackmsg integer,
    rcv_state integer NOT NULL,
    id_entreg_dest integer NOT NULL,
    rcv_numreg varchar(20),
    rcv_datereg timestamp
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
    spa_text varchar(254) NOT NULL,
    trans_text varchar(254) NOT NULL
);

CREATE TABLE scr_tt (
    id integer NOT NULL,
    transport varchar(31) NOT NULL
);


CREATE TABLE scr_typeaddress (
    id integer NOT NULL,
    description varchar(50) NOT NULL,
    code varchar(2) NOT NULL
);

CREATE TABLE scr_typeadm (
    id integer NOT NULL,
    code varchar(1) NOT NULL,
    description varchar(50) NOT NULL
);

CREATE TABLE scr_typedoc (
    id integer NOT NULL,
    description varchar(50) NOT NULL,
    type_person integer NOT NULL,
    code varchar(1) NOT NULL
);

CREATE TABLE scr_typeofic (
    id integer NOT NULL,
    description varchar(50) NOT NULL
);

CREATE TABLE scr_userconfig (
    userid integer NOT NULL,
    data clob NOT NULL,
	idoficpref integer
);

CREATE TABLE scr_userfilter (
    idarch integer NOT NULL,
    iduser integer NOT NULL,
    filterdef clob,
    type_obj integer NOT NULL
);


CREATE TABLE scr_usrloc (
    userid integer NOT NULL,
    tmstamp timestamp NOT NULL,
    address varchar(255),
    city varchar(100),
    zip varchar(10),
    country varchar(100),
    telephone varchar(160),
    fax varchar(160),
    email varchar(255)
);

CREATE TABLE scr_usrofic (
    id integer NOT NULL,
    iduser integer NOT NULL,
    idofic integer NOT NULL
);

CREATE TABLE scr_usrperms (
    id_usr integer NOT NULL,
    tmstamp timestamp NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE scr_valdate (
    id integer NOT NULL,
    value timestamp,
    oldvalue timestamp
);

CREATE TABLE scr_valnum (
    id integer NOT NULL,
    value integer,
    oldvalue integer
);

CREATE TABLE scr_valstr (
    id integer NOT NULL,
    value varchar(250),
    oldvalue varchar(250)
);

CREATE TABLE scr_ws (
    id integer NOT NULL,
    nombre varchar(50) NOT NULL,
    macaddr varchar(32),
    ipaddr varchar(20),
    idofic integer,
     code varchar(4) NOT NULL,
    descripcion varchar(250)
);

CREATE TABLE scr_unitadm (
    userid  integer NOT NULL,
    objid integer NOT NULL,
		objtype integer NOT NULL
);

CREATE TABLE scr_lockitems (
    objtype integer NOT NULL,
		objid integer NOT NULL,
		userid  integer NOT NULL,
		lockdate timestamp NOT NULL
);

CREATE TABLE scr_versionitems (
    objtype integer NOT NULL,
		code varchar(32) NOT NULL,
		objid integer NOT NULL,
		version  integer NOT NULL,
		userid  integer NOT NULL,
		versiondate timestamp NOT NULL
);

CREATE TABLE scr_orgslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_typeadmlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(50) NOT NULL
);

CREATE TABLE scr_oficlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(32) NOT NULL
);

CREATE TABLE scr_calang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_ttlang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(31) NOT NULL
);

CREATE TABLE scr_reportslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_bookslang (
	id integer NOT NULL,
	language integer NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_repositoryconf (
    ID INTEGER NOT NULL,
    DATA CLOB NOT NULL
);

CREATE TABLE scr_repobooktype (
    book_type INTEGER NOT NULL,
    id_rep_conf INTEGER NOT NULL
);

CREATE TABLE scr_doclocator
(
	bookID integer NOT NULL,
	folderID integer NOT NULL,
	pageID integer NOT NULL,
	locator varchar(128) NOT NULL
);
-- SEQUENCE

CREATE SEQUENCE scr_seqcnt
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

