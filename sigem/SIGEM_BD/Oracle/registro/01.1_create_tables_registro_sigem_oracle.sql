
CREATE TABLE scr_actws (
    idpuesto NUMBER(10) NOT NULL,
    idcambio NUMBER(10) NOT NULL
);

CREATE TABLE scr_address (
    id NUMBER(10) NOT NULL,
    id_person NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL
);

CREATE TABLE scr_addrtel (
    id NUMBER(10) NOT NULL,
    address VARCHAR2(160 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    preference NUMBER(10) NOT NULL
);

CREATE TABLE scr_bookadmin (
    idbook NUMBER(10)  NOT NULL,
    iduser NUMBER(10)  NOT NULL
);

CREATE TABLE scr_bookasoc (
    id_book NUMBER(10) NOT NULL,
    id_booksec NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL
);

CREATE TABLE scr_bookofic (
    id NUMBER(10) NOT NULL,
    id_book NUMBER(10) NOT NULL,
    id_ofic NUMBER(10) NOT NULL,
    numeration NUMBER(10) NOT NULL
);

CREATE TABLE scr_ca (
    id NUMBER(10) NOT NULL,
    code VARCHAR2(16 CHAR) NOT NULL,
    matter VARCHAR2(250 CHAR) NOT NULL,
    for_ereg NUMBER(10),
    for_sreg NUMBER(10),
    all_ofics NUMBER(10),
    id_arch NUMBER(10) NOT NULL,
    creation_date DATE NOT NULL,
    disable_date DATE,
    enabled NUMBER(10),
    id_org NUMBER(10) NOT NULL
);

CREATE TABLE scr_caadm (
    id NUMBER(10) NOT NULL,
    iduser NUMBER(10) NOT NULL,
    idca NUMBER(10) NOT NULL
);

CREATE TABLE scr_cadocs (
    id NUMBER(10) NOT NULL,
    id_matter NUMBER(10) NOT NULL,
    description VARCHAR2(32 CHAR) NOT NULL,
    mandatory NUMBER(10)
);

CREATE TABLE scr_caofic (
    id NUMBER(10) NOT NULL,
    id_matter NUMBER(10) NOT NULL,
    id_ofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_cities (
    id NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    code VARCHAR2(16 CHAR) NOT NULL,
    name VARCHAR2(40 CHAR) NOT NULL,
    id_prov NUMBER(10) NOT NULL
);

CREATE TABLE scr_cntcentral (
    an NUMBER(10) NOT NULL,
    num_reg NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL
);

CREATE TABLE scr_cntlocal (
    an NUMBER(10) NOT NULL,
    puesto NUMBER(10) NOT NULL,
    num_reg NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL
);

CREATE TABLE scr_cntoficina (
    an NUMBER(10) NOT NULL,
    oficina NUMBER(10) NOT NULL,
    num_reg NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL
);

CREATE TABLE scr_cntordinal (
    contador NUMBER(10) NOT NULL,
    hora VARCHAR2(4 CHAR) NOT NULL
);

CREATE TABLE scr_cntrefcierre (
    tipo VARCHAR2(3 CHAR) NOT NULL,
    contador NUMBER(10) NOT NULL
);

CREATE TABLE scr_configuration (
    id NUMBER(10) NOT NULL,
    version VARCHAR2(10 CHAR) NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_caaux (
    id NUMBER(10) NOT NULL,
    id_matter NUMBER(10) NOT NULL,
    datos_aux clob NOT NULL
);

CREATE TABLE scr_bookrepository (
    bookid NUMBER(10) NOT NULL,
    configuration clob
);

CREATE TABLE scr_booktypeconfig (
    booktype NUMBER(10) NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_bookconfig (
    bookid NUMBER(10) NOT NULL,
    options clob NOT NULL
);

CREATE TABLE scr_contador (
    tablaid VARCHAR2(30 CHAR) NOT NULL,
    contador NUMBER(10) NOT NULL
);

CREATE TABLE scr_deltas (
    idcambio NUMBER(10) NOT NULL,
    tabla VARCHAR2(20 CHAR) NOT NULL,
    idregistro NUMBER(10) NOT NULL,
    tipocambio NUMBER(10) NOT NULL
);

CREATE TABLE scr_dirofic (
    id_ofic NUMBER(10) NOT NULL,
    address VARCHAR2(255 CHAR),
    city VARCHAR2(100 CHAR),
    zip VARCHAR2(10 CHAR),
    country VARCHAR2(100 CHAR),
    telephone VARCHAR2(160 CHAR),
    fax VARCHAR2(160 CHAR),
    email VARCHAR2(255 CHAR),
    signer VARCHAR2(160 CHAR)
);

CREATE TABLE scr_dirorgs (
    id_orgs NUMBER(10) NOT NULL,
    address VARCHAR2(255 CHAR),
    city VARCHAR2(100 CHAR),
    zip VARCHAR2(10 CHAR),
    country VARCHAR2(100 CHAR),
    telephone VARCHAR2(160 CHAR),
    fax VARCHAR2(160 CHAR),
    email VARCHAR2(255 CHAR)
);

CREATE TABLE scr_distaccept (
    bookid NUMBER(10) NOT NULL,
    regid NUMBER(10) NOT NULL,
    officeid NUMBER(10) NOT NULL,
    distdate DATE NOT NULL,
    accdate DATE NOT NULL,
    accuser VARCHAR2(80 CHAR) NOT NULL,
    state NUMBER(10) NOT NULL,
    data clob
);


CREATE TABLE scr_distlist (
    id NUMBER(10) NOT NULL,
    id_orgs NUMBER(10) NOT NULL,
    type_dest NUMBER(10) NOT NULL,
    id_dest NUMBER(10) NOT NULL
);

CREATE TABLE scr_distreg (
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_fdr NUMBER(10) NOT NULL,
    dist_date DATE NOT NULL,
    type_orig NUMBER(10) NOT NULL,
    id_orig NUMBER(10) NOT NULL,
    type_dest NUMBER(10) NOT NULL,
    id_dest NUMBER(10) NOT NULL,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    message VARCHAR2(250 CHAR),
    id_dist_father NUMBER(10)
);

CREATE TABLE scr_distregstate (
    id NUMBER(10) NOT NULL,
    id_dist NUMBER(10) NOT NULL,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    message VARCHAR2(250 CHAR)
);

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist NUMBER(10) NOT NULL,
    dist_actual clob
);

CREATE TABLE scr_dom (
    id NUMBER(10) NOT NULL,
    address VARCHAR2(100 CHAR) NOT NULL,
    city VARCHAR2(40 CHAR),
    zip VARCHAR2(5 CHAR),
    country VARCHAR2(30 CHAR),
    preference NUMBER(10) NOT NULL
);

CREATE TABLE scr_entregsnd (
    id_entreg NUMBER(10) NOT NULL,
    transport_type NUMBER(10) NOT NULL,
    encoding_msg NUMBER(10) NOT NULL,
    certificate_issuer VARCHAR2(255 CHAR),
    certificate_nserial VARCHAR2(255 CHAR)
);

CREATE TABLE scr_expcnts (
    id NUMBER(10) NOT NULL,
    id_task NUMBER(10) NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    password VARCHAR2(34 CHAR) NOT NULL
);

CREATE TABLE scr_exptasks (
    id NUMBER(10) NOT NULL,
    id_user NUMBER(10) NOT NULL,
    description VARCHAR2(50 CHAR) NOT NULL,
    id_sourcearch NUMBER(10) NOT NULL,
    id_targetarch NUMBER(10) NOT NULL,
    id_targetdb NUMBER(10) NOT NULL,
    id_filter NUMBER(10) NOT NULL,
    id_connection NUMBER(10) NOT NULL,
    status NUMBER(10) NOT NULL,
    delete_source NUMBER(10) NOT NULL,
    explastdate DATE
);

CREATE TABLE scr_fldaccperms (
    archivetype NUMBER(10) NOT NULL,
    idfld NUMBER(10) NOT NULL,
    userperms NUMBER(10) NOT NULL,
    adminperms NUMBER(10) NOT NULL,
    suserperms NUMBER(10) NOT NULL
);

CREATE TABLE scr_fldpermarch (
    idarchive NUMBER(10) NOT NULL,
    idfld NUMBER(10) NOT NULL,
    userperms NUMBER(10) NOT NULL,
    adminperms NUMBER(10) NOT NULL,
    suserperms NUMBER(10) NOT NULL
);

CREATE TABLE scr_fldpermuser (
    idarchive NUMBER(10) NOT NULL,
    idfld NUMBER(10) NOT NULL,
    iduser NUMBER(10) NOT NULL,
    perms NUMBER(10) NOT NULL
);

CREATE TABLE scr_genperms (
    id NUMBER(10) NOT NULL,
    perms NUMBER(10) NOT NULL
);

CREATE TABLE scr_lastconnection (
    iduser NUMBER(10) NOT NULL,
    connect_date DATE NOT NULL
);

CREATE TABLE scr_expfilters (
    id NUMBER(10) NOT NULL,
    id_task NUMBER(10) NOT NULL,
    filter clob NOT NULL
);

CREATE TABLE scr_lastregister (
    bookid NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    userid NUMBER(10) NOT NULL
);

CREATE TABLE scr_ofic (
    id NUMBER(10) NOT NULL,
    code VARCHAR2(10 CHAR) NOT NULL,
    acron VARCHAR2(12 CHAR) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    creation_date DATE,
    disable_date DATE,
    id_orgs NUMBER(10),
    stamp VARCHAR2(240 CHAR),
    deptid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL
);

CREATE TABLE scr_orgs (
    id NUMBER(10) NOT NULL,
    code VARCHAR2(16 CHAR) NOT NULL,
    id_father NUMBER(10),
    acron VARCHAR2(12 CHAR) NOT NULL,
    name VARCHAR2(250 CHAR) NOT NULL,
    creation_date DATE NOT NULL,
    disable_date DATE,
    type NUMBER(10) NOT NULL,
    enabled NUMBER(10) NOT NULL,
    cif VARCHAR2(15 CHAR)
);

CREATE TABLE scr_lockrelations (
    typebook NUMBER(10) NOT NULL,
    typerel NUMBER(10) NOT NULL,
    idofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_modifreg (
    id NUMBER(10) NOT NULL,
    usr VARCHAR2(32 CHAR) NOT NULL,
    modif_date DATE NOT NULL,
    id_fld NUMBER(10) NOT NULL,
    num_reg VARCHAR2(20 CHAR) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_ofic NUMBER(10) NOT NULL,
    modif_type NUMBER(10) NOT NULL
);

CREATE TABLE scr_usrident (
    userid NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    first_name VARCHAR2(25 CHAR) NOT NULL,
    second_name VARCHAR2(25 CHAR),
    surname VARCHAR2(20 CHAR) NOT NULL
);

CREATE TABLE scr_nackrecvmsg (
    id NUMBER(10),
    id_msg NUMBER(10) NOT NULL,
    id_sendmsg NUMBER(10) NOT NULL,
    reason VARCHAR2(254 CHAR)
);

CREATE TABLE scr_nackrecvreg (
    id NUMBER(10) NOT NULL,
    id_reg NUMBER(10) NOT NULL,
    id_sendmsg NUMBER(10) NOT NULL,
    reason VARCHAR2(254 CHAR)
);

CREATE TABLE scr_nacksendmsg (
    id NUMBER(10) NOT NULL,
    id_msg NUMBER(10) NOT NULL,
    id_rcvmsg NUMBER(10) NOT NULL,
    reason VARCHAR2(254 CHAR)
);

CREATE TABLE scr_nacksendreg (
    id NUMBER(10) NOT NULL,
    id_reg NUMBER(10) NOT NULL,
    id_rcvmsg NUMBER(10) NOT NULL,
    reason VARCHAR2(254 CHAR)
);

CREATE TABLE scr_oficadm (
    id NUMBER(10) NOT NULL,
    iduser NUMBER(10) NOT NULL,
    idofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_pageinfo (
    bookid NUMBER(10) NOT NULL,
    regid NUMBER(10) NOT NULL,
    pageid NUMBER(10) NOT NULL,
    hashversion NUMBER(10) NOT NULL,
    hash clob NOT NULL
);

CREATE TABLE scr_pagerepository (
    bookid NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    pageid NUMBER(10) NOT NULL,
    docuid VARCHAR2(50 CHAR) NOT NULL,
    id_pagetype NUMBER default 0 NOT NULL
);

CREATE TABLE scr_pagetype (
    id NUMBER NOT NULL,
    name VARCHAR2(100 CHAR) NOT NULL
);

CREATE TABLE scr_pfis (
    id NUMBER(10) NOT NULL,
    type_doc NUMBER(10),
    nif VARCHAR2(25 CHAR),
    first_name VARCHAR2(25 CHAR) NOT NULL,
    second_name VARCHAR2(25 CHAR),
    surname VARCHAR2(20 CHAR)
);

CREATE TABLE scr_pinfo (
    id NUMBER(10) NOT NULL,
    optype NUMBER(10) NOT NULL,
    officeid NUMBER(10) NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    opdate DATE NOT NULL
);

CREATE TABLE scr_pjur (
    id NUMBER(10) NOT NULL,
    type_doc NUMBER(10),
    cif VARCHAR2(17 CHAR),
    name VARCHAR2(72 CHAR) NOT NULL
);

CREATE TABLE scr_privorgs (
    id NUMBER(10) NOT NULL,
    idorgs NUMBER(10) NOT NULL,
    idofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_procreg (
    id NUMBER(10) NOT NULL,
    id_dist NUMBER(10) NOT NULL,
    id_proc NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL
);

CREATE TABLE scr_prov (
    id NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    code VARCHAR2(16 CHAR)  NOT NULL,
    name VARCHAR2(30 CHAR) NOT NULL
);

CREATE TABLE scr_recvmsg (
    id NUMBER(10) NOT NULL,
    uid_msg VARCHAR2(40 CHAR) NOT NULL,
    type_msg NUMBER(10) NOT NULL,
    file_name VARCHAR2(40 CHAR) NOT NULL,
    sender NUMBER(10) NOT NULL,
    destination NUMBER(10) NOT NULL,
    rcv_type NUMBER(10) NOT NULL,
    rcv_date DATE NOT NULL,
    rcv_state NUMBER(10) NOT NULL,
    id_ackmsg NUMBER(10)
);

CREATE TABLE scr_recvreg (
    id NUMBER(10) NOT NULL,
    id_msg NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_fdr NUMBER(10) NOT NULL,
    regorig_code VARCHAR2(16 CHAR) NOT NULL,
    regorig_num VARCHAR2(20 CHAR) NOT NULL,
    regorig_date DATE NOT NULL,
    regorig_type NUMBER(10) NOT NULL,
    attach_pages NUMBER(10) NOT NULL,
    attach_doc NUMBER(10) NOT NULL,
    id_ackmsg NUMBER(10),
    rcv_state NUMBER(10) NOT NULL,
    rcv_numreg VARCHAR2(20 CHAR),
    rcv_datereg DATE
);

CREATE TABLE scr_regasoc (
    id NUMBER(10) NOT NULL,
    id_archprim NUMBER(10) NOT NULL,
    id_fdrprim NUMBER(10) NOT NULL,
    id_archsec NUMBER(10) NOT NULL,
    id_fdrsec NUMBER(10) NOT NULL
);

CREATE TABLE scr_regasocex (
    id_asoc NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL
);

CREATE TABLE scr_regint (
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_fdr NUMBER(10) NOT NULL,
    name VARCHAR2(95 CHAR) NOT NULL,
    id_person NUMBER(10) NOT NULL,
    id_address NUMBER(10),
    ord NUMBER(10) NOT NULL
);

CREATE TABLE scr_regpdocs (
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_fdr NUMBER(10) NOT NULL,
    name_doc VARCHAR2(250 CHAR) NOT NULL,
    presented NUMBER(10) NOT NULL
);

CREATE TABLE scr_regstate (
    id NUMBER(10) NOT NULL,
    idarchreg NUMBER(10) NOT NULL,
    state NUMBER(10) NOT NULL,
    closedate DATE,
    closeuser VARCHAR2(32 CHAR),
    numeration_type NUMBER(10) NOT NULL,
    image_auth NUMBER(10) NOT NULL
);

CREATE TABLE scr_relations (
    typebook NUMBER(10) NOT NULL,
    typerel NUMBER(10) NOT NULL,
    relyear NUMBER(10) NOT NULL,
    relmonth NUMBER(10) NOT NULL,
    relday NUMBER(10) NOT NULL,
    idofic NUMBER(10) NOT NULL,
    reldate DATE NOT NULL,
    idunit NUMBER(10) NOT NULL,
    nrel NUMBER(10) NOT NULL
);


CREATE TABLE scr_reports (
	id NUMBER(10) NOT NULL,
	report VARCHAR2(250 CHAR) NOT NULL,
	type_report NUMBER(10) NOT NULL,
	type_arch NUMBER(10) NOT NULL,
	all_arch NUMBER(10) NOT NULL,
	all_ofics NUMBER(10) NOT NULL,
	all_perfs NUMBER(10) NOT NULL,
	description VARCHAR2(250 CHAR) NOT NULL,
	data BLOB NOT NULL
);

CREATE TABLE scr_reportarch (
    id NUMBER(10) NOT NULL,
    id_report NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL
);

CREATE TABLE scr_reportofic (
    id NUMBER(10) NOT NULL,
    id_report NUMBER(10) NOT NULL,
    id_ofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_reportperf (
    id NUMBER(10) NOT NULL,
    id_report NUMBER(10) NOT NULL,
    id_perf NUMBER(10) NOT NULL
);

CREATE TABLE scr_repository (
    id NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    configuration clob NOT NULL
);

CREATE TABLE scr_sendmsg (
    id NUMBER(10) NOT NULL,
    uid_msg VARCHAR2(40 CHAR) NOT NULL,
    type_msg NUMBER(10) NOT NULL,
    file_name VARCHAR2(40 CHAR),
    sender NUMBER(10) NOT NULL,
    destination NUMBER(10) NOT NULL,
    send_type NUMBER(10) NOT NULL,
    gen_date DATE NOT NULL,
    send_date DATE,
    rcv_date DATE,
    send_state NUMBER(10) NOT NULL,
    id_ackmsg NUMBER(10),
    send_num NUMBER(10) NOT NULL
);

CREATE TABLE scr_sendreg (
    id NUMBER(10) NOT NULL,
    id_msg NUMBER(10) NOT NULL,
    id_arch NUMBER(10) NOT NULL,
    id_fdr NUMBER(10) NOT NULL,
    regorig_code VARCHAR2(16 CHAR) NOT NULL,
    regorig_num VARCHAR2(20 CHAR) NOT NULL,
    regorig_date DATE NOT NULL,
    regorig_type NUMBER(10) NOT NULL,
    attach_pages NUMBER(10) NOT NULL,
    attach_doc NUMBER(10) NOT NULL,
    id_ackmsg NUMBER(10),
    rcv_state NUMBER(10) NOT NULL,
    id_entreg_dest NUMBER(10) NOT NULL,
    rcv_numreg VARCHAR2(20 CHAR),
    rcv_datereg DATE
);

CREATE TABLE scr_sharedfiles (
    fileid NUMBER(10) NOT NULL,
    ownerbookid NUMBER(10) NOT NULL,
    ownerregid NUMBER(10) NOT NULL,
    bookid NUMBER(10) NOT NULL,
    regid NUMBER(10) NOT NULL
);

CREATE TABLE scr_tmzofic (
    id NUMBER(10) NOT NULL,
    tmz NUMBER(10) NOT NULL
);

CREATE TABLE scr_translatedfmt (
    id NUMBER(10) NOT NULL,
    langid NUMBER(10) NOT NULL,
    spa_text VARCHAR2(254 CHAR) NOT NULL,
    trans_text VARCHAR2(254 CHAR) NOT NULL
);

CREATE TABLE scr_tt (
    id NUMBER(10) NOT NULL,
    transport VARCHAR2(31 CHAR) NOT NULL
);

CREATE TABLE scr_typeaddress (
    id NUMBER(10) NOT NULL,
    description VARCHAR2(50 CHAR) NOT NULL,
    code VARCHAR2(2 CHAR) NOT NULL
);

CREATE TABLE scr_typeadm (
    id NUMBER(10) NOT NULL,
    code VARCHAR2(1 CHAR) NOT NULL,
    description VARCHAR2(50 CHAR) NOT NULL
);

CREATE TABLE scr_typedoc (
    id NUMBER(10) NOT NULL,
    description VARCHAR2(50 CHAR) NOT NULL,
    type_person NUMBER(10) NOT NULL,
    code VARCHAR2(1 CHAR) NOT NULL
);

CREATE TABLE scr_typeofic (
    id NUMBER(10) NOT NULL,
    description VARCHAR2(50 CHAR) NOT NULL
);

CREATE TABLE scr_userconfig (
    userid NUMBER(10) NOT NULL,
    data clob NOT NULL,
	idoficpref NUMBER(10)
);

CREATE TABLE scr_userfilter (
    idarch NUMBER(10) NOT NULL,
    iduser NUMBER(10) NOT NULL,
    filterdef clob,
    type_obj NUMBER(10) NOT NULL
);


CREATE TABLE scr_usrloc (
    userid NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    address VARCHAR2(255 CHAR),
    city VARCHAR2(100 CHAR),
    zip VARCHAR2(10 CHAR),
    country VARCHAR2(100 CHAR),
    telephone VARCHAR2(160 CHAR),
    fax VARCHAR2(160 CHAR),
    email VARCHAR2(255 CHAR)
);

CREATE TABLE scr_usrofic (
    id NUMBER(10) NOT NULL,
    iduser NUMBER(10) NOT NULL,
    idofic NUMBER(10) NOT NULL
);

CREATE TABLE scr_usrperms (
    id_usr NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    perms NUMBER(10) NOT NULL
);

CREATE TABLE scr_valdate (
    id NUMBER(10) NOT NULL,
    value DATE,
    oldvalue DATE
);

CREATE TABLE scr_valnum (
    id NUMBER(10) NOT NULL,
    value NUMBER(10),
    oldvalue NUMBER(10)
);

CREATE TABLE scr_valstr (
    id NUMBER(10) NOT NULL,
    value VARCHAR2(250 CHAR),
    oldvalue VARCHAR2(250 CHAR)
);

CREATE TABLE scr_ws (
    id NUMBER(10) NOT NULL,
    nombre VARCHAR2(50 CHAR) NOT NULL,
    macaddr VARCHAR2(32 CHAR),
    ipaddr VARCHAR2(20 CHAR),
    idofic NUMBER(10),
    code VARCHAR2(4 CHAR) NOT NULL,
    descripcion VARCHAR2(250 CHAR)
);

CREATE TABLE scr_unitadm (
    userid NUMBER(10) NOT NULL,
    objid NUMBER(10) NOT NULL,
    objtype NUMBER(10) NOT NULL
);

CREATE TABLE scr_lockitems (
    objtype NUMBER(10) NOT NULL,
    objid NUMBER(10) NOT NULL,
    userid  NUMBER(10) NOT NULL,
    lockdate DATE NOT NULL
);

CREATE TABLE scr_versionitems (
    objtype NUMBER(10) NOT NULL,
    code VARCHAR2(32 CHAR) NOT NULL,
    objid NUMBER(10) NOT NULL,
    version  NUMBER(10) NOT NULL,
    userid  NUMBER(10) NOT NULL,
    versiondate DATE NOT NULL
);

CREATE TABLE scr_orgslang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(250 CHAR) NOT NULL
);

CREATE TABLE scr_typeadmlang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(50 CHAR) NOT NULL
);

CREATE TABLE scr_oficlang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(32 CHAR) NOT NULL
);

CREATE TABLE scr_calang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(250 CHAR) NOT NULL
);

CREATE TABLE scr_ttlang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(31 CHAR) NOT NULL
);

CREATE TABLE scr_reportslang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(250 CHAR) NOT NULL
);

CREATE TABLE scr_bookslang (
	id NUMBER(10) NOT NULL,
	language NUMBER(10) NOT NULL,
	name VARCHAR2(250 CHAR) NOT NULL
);


CREATE TABLE scr_repositoryconf (
    ID NUMBER(10) NOT NULL,
    DATA CLOB NOT NULL
);

CREATE TABLE scr_repobooktype (
    book_type NUMBER(10) NOT NULL,
    id_rep_conf NUMBER(10) NOT NULL
);


CREATE TABLE scr_doclocator
(
 	bookID NUMBER(10) NOT NULL,
	folderID NUMBER(10) NOT NULL,
	pageID NUMBER(10) NOT NULL,
	locator VARCHAR2(128 CHAR) NOT NULL
);

-- SEQUENCE

CREATE SEQUENCE scr_seqcnt
    START WITH 1
    INCREMENT BY 1
    CACHE 20;
