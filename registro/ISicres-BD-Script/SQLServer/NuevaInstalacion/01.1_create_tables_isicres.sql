
---------------------------
-- TABLAS DE INVESSICRES --
---------------------------
CREATE TABLE scr_actws (
    idpuesto int NOT NULL,
    idcambio int NOT NULL
);

CREATE TABLE scr_address (
    id int NOT NULL,
    id_person int NOT NULL,
    type int NOT NULL
);

CREATE TABLE scr_addrtel (
    id int NOT NULL,
    address varchar(160) NOT NULL,
    type int NOT NULL,
    preference int NOT NULL
);

CREATE TABLE scr_bookadmin (
    idbook int NOT NULL,
    iduser int NOT NULL
);

CREATE TABLE scr_bookasoc (
    id_book int NOT NULL,
    id_booksec int NOT NULL,
    type int NOT NULL
);

CREATE TABLE scr_bookofic (
    id int NOT NULL,
    id_book int NOT NULL,
    id_ofic int NOT NULL,
    numeration int NOT NULL
);

CREATE TABLE scr_ca (
    id int NOT NULL,
    code varchar(16) NOT NULL,
    matter varchar(250) NOT NULL,
    for_ereg int,
    for_sreg int,
    all_ofics int,
    id_arch int NOT NULL,
    creation_date datetime NOT NULL,
    disable_date datetime,
    enabled int,
    id_org int NOT NULL
);

CREATE TABLE scr_caadm (
    id int NOT NULL,
    iduser int NOT NULL,
    idca int NOT NULL
);

CREATE TABLE scr_cadocs (
    id int NOT NULL,
    id_matter int NOT NULL,
    description varchar(32) NOT NULL,
    mandatory int
);

CREATE TABLE scr_caofic (
    id int NOT NULL,
    id_matter int NOT NULL,
    id_ofic int NOT NULL
);

CREATE TABLE scr_cities (
    id int NOT NULL,
    tmstamp datetime NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(40) NOT NULL,
    id_prov int NOT NULL
);

CREATE TABLE scr_cntcentral (
    an int NOT NULL,
    num_reg int NOT NULL,
    id_arch int NOT NULL
);

CREATE TABLE scr_cntlocal (
    an int NOT NULL,
    puesto int NOT NULL,
    num_reg int NOT NULL,
    id_arch int NOT NULL
);

CREATE TABLE scr_cntoficina (
    an int NOT NULL,
    oficina int NOT NULL,
    num_reg int NOT NULL,
    id_arch int NOT NULL
);

CREATE TABLE scr_cntordinal (
    contador int NOT NULL,
    hora varchar(4) NOT NULL
);

CREATE TABLE scr_cntrefcierre (
    tipo varchar(3) NOT NULL,
    contador int NOT NULL
);


CREATE TABLE scr_configuration (
    id int NOT NULL,
    version varchar(10) NOT NULL,
    options text NOT NULL
);

CREATE TABLE scr_caaux (
    id int NOT NULL,
    id_matter int NOT NULL,
    datos_aux text NOT NULL
);

CREATE TABLE scr_bookrepository (
    bookid int NOT NULL,
    configuration text
);

CREATE TABLE scr_booktypeconfig (
    booktype int NOT NULL,
    options text NOT NULL
);

CREATE TABLE scr_bookconfig (
    bookid int NOT NULL,
    options text NOT NULL
);

CREATE TABLE scr_contador (
    tablaid varchar(30) NOT NULL,
    contador int NOT NULL
);

CREATE TABLE scr_deltas (
    idcambio int NOT NULL,
    tabla varchar(20) NOT NULL,
    idregistro int NOT NULL,
    tipocambio int NOT NULL
);

CREATE TABLE scr_dirofic (
    id_ofic int NOT NULL,
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
    id_orgs int NOT NULL,
    address varchar(255),
    city varchar(100),
    zip varchar(10),
    country varchar(100),
    telephone varchar(160),
    fax varchar(160),
    email varchar(255)
);

CREATE TABLE scr_distaccept (
    bookid int NOT NULL,
    regid int NOT NULL,
    officeid int NOT NULL,
    distdate datetime NOT NULL,
    accdate datetime NOT NULL,
    accuser varchar(80) NOT NULL,
    state int NOT NULL,
    data text
);


CREATE TABLE scr_distlist (
    id int NOT NULL,
    id_orgs int NOT NULL,
    type_dest int NOT NULL,
    id_dest int NOT NULL
);

CREATE TABLE scr_distreg (
    id int NOT NULL,
    id_arch int NOT NULL,
    id_fdr int NOT NULL,
    dist_date datetime NOT NULL,
    type_orig int NOT NULL,
    id_orig int NOT NULL,
    type_dest int NOT NULL,
    id_dest int NOT NULL,
    state int NOT NULL,
    state_date datetime NOT NULL,
    message varchar(250),
    id_dist_father int
);

CREATE TABLE scr_distregstate (
    id int NOT NULL,
    id_dist int NOT NULL,
    state int NOT NULL,
    state_date datetime NOT NULL,
    username varchar(32) NOT NULL,
    message varchar(250)
);

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist int NOT NULL,
    dist_actual text
);


CREATE TABLE scr_dom (
    id int NOT NULL,
    address varchar(160) NOT NULL,
    city varchar(40),
    zip varchar(5),
    country varchar(30),
    preference int NOT NULL
);

CREATE TABLE scr_entregsnd (
    id_entreg int NOT NULL,
    transport_type int NOT NULL,
    encoding_msg int NOT NULL,
    certificate_issuer varchar(255),
    certificate_nserial varchar(255)
);

CREATE TABLE scr_expcnts (
    id int NOT NULL,
    id_task int NOT NULL,
    username varchar(32) NOT NULL,
    password varchar(34) NOT NULL
);

CREATE TABLE scr_exptasks (
    id int NOT NULL,
    id_user int NOT NULL,
    description varchar(50) NOT NULL,
    id_sourcearch int NOT NULL,
    id_targetarch int NOT NULL,
    id_targetdb int NOT NULL,
    id_filter int NOT NULL,
    id_connection int NOT NULL,
    status int NOT NULL,
    delete_source int NOT NULL,
    explastdate datetime
);

CREATE TABLE scr_fldaccperms (
    archivetype int NOT NULL,
    idfld int NOT NULL,
    userperms int NOT NULL,
    adminperms int NOT NULL,
    suserperms int NOT NULL
);

CREATE TABLE scr_fldpermarch (
    idarchive int NOT NULL,
    idfld int NOT NULL,
    userperms int NOT NULL,
    adminperms int NOT NULL,
    suserperms int NOT NULL
);


CREATE TABLE scr_fldpermuser (
    idarchive int NOT NULL,
    idfld int NOT NULL,
    iduser int NOT NULL,
    perms int NOT NULL
);

CREATE TABLE scr_genperms (
    id int NOT NULL,
    perms int NOT NULL
);

CREATE TABLE scr_lastconnection (
    iduser int NOT NULL,
    connect_date datetime NOT NULL
);

CREATE TABLE scr_expfilters (
    id int NOT NULL,
    id_task int NOT NULL,
    filter text NOT NULL
);


CREATE TABLE scr_lastregister (
    bookid int NOT NULL,
    fdrid int NOT NULL,
    userid int NOT NULL
);

CREATE TABLE scr_ofic (
    id int NOT NULL,
    code varchar(10) NOT NULL,
    acron varchar(12) NOT NULL,
    name varchar(32) NOT NULL,
    creation_date datetime,
    disable_date datetime,
    id_orgs int,
    stamp varchar(240),
    deptid int NOT NULL,
    type int NOT NULL
);

CREATE TABLE scr_orgs (
    id int NOT NULL,
    code varchar(16) NOT NULL,
    id_father int,
    acron varchar(12) NOT NULL,
    name varchar(250) NOT NULL,
    creation_date datetime NOT NULL,
    disable_date datetime,
    type int NOT NULL,
    enabled int NOT NULL,
    cif varchar(15)
);



CREATE TABLE scr_lockrelations (
    typebook int NOT NULL,
    typerel int NOT NULL,
    idofic int NOT NULL
);

CREATE TABLE scr_modifreg (
    id int NOT NULL,
    usr varchar(32) NOT NULL,
    modif_date datetime NOT NULL,
    id_fld int NOT NULL,
    num_reg varchar(20) NOT NULL,
    id_arch int NOT NULL,
    id_ofic int NOT NULL,
    modif_type int NOT NULL
);

CREATE TABLE scr_usrident (
    userid int NOT NULL,
    tmstamp datetime NOT NULL,
    first_name varchar(25) NOT NULL,
    second_name varchar(25),
    surname varchar(20) NOT NULL
);



CREATE TABLE scr_nackrecvmsg (
    id int,
    id_msg int NOT NULL,
    id_sendmsg int NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nackrecvreg (
    id int NOT NULL,
    id_reg int NOT NULL,
    id_sendmsg int NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nacksendmsg (
    id int NOT NULL,
    id_msg int NOT NULL,
    id_rcvmsg int NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_nacksendreg (
    id int NOT NULL,
    id_reg int NOT NULL,
    id_rcvmsg int NOT NULL,
    reason varchar(254)
);

CREATE TABLE scr_oficadm (
    id int NOT NULL,
    iduser int NOT NULL,
    idofic int NOT NULL
);

CREATE TABLE scr_pageinfo (
    bookid int NOT NULL,
    regid int NOT NULL,
    pageid int NOT NULL,
    hashversion int NOT NULL,
    hash text NOT NULL
);

CREATE TABLE scr_pagerepository (
    bookid int NOT NULL,
    fdrid int NOT NULL,
    pageid int NOT NULL,
    docuid varchar(50) NOT NULL,
    id_pagetype int NOT NULL default 0
);

CREATE TABLE scr_pagetype (
    id int NOT NULL,
    name varchar(100) NOT NULL
);


CREATE TABLE scr_pfis (
    id int NOT NULL,
    type_doc int,
    nif varchar(25),
    first_name varchar(25) NOT NULL,
    second_name varchar(25),
    surname varchar(20)
);

CREATE TABLE scr_pinfo (
    id int NOT NULL,
    optype int NOT NULL,
    officeid int NOT NULL,
    username varchar(32) NOT NULL,
    opdate datetime NOT NULL
);

CREATE TABLE scr_pjur (
    id int NOT NULL,
    type_doc int,
    cif varchar(17),
    name varchar(80) NOT NULL
);

CREATE TABLE scr_privorgs (
    id int NOT NULL,
    idorgs int NOT NULL,
    idofic int NOT NULL
);

CREATE TABLE scr_procreg (
    id int NOT NULL,
    id_dist int NOT NULL,
    id_proc int NOT NULL,
    type int NOT NULL
);

CREATE TABLE scr_prov (
    id int NOT NULL,
    tmstamp datetime NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(30) NOT NULL
);

CREATE TABLE scr_recvmsg (
    id int NOT NULL,
    uid_msg varchar(40) NOT NULL,
    type_msg int NOT NULL,
    file_name varchar(40) NOT NULL,
    sender int NOT NULL,
    destination int NOT NULL,
    rcv_type int NOT NULL,
    rcv_date datetime NOT NULL,
    rcv_state int NOT NULL,
    id_ackmsg int
);

CREATE TABLE scr_recvreg (
    id int NOT NULL,
    id_msg int NOT NULL,
    id_arch int NOT NULL,
    id_fdr int NOT NULL,
    regorig_code varchar(16) NOT NULL,
    regorig_num varchar(20) NOT NULL,
    regorig_date datetime NOT NULL,
    regorig_type int NOT NULL,
    attach_pages int NOT NULL,
    attach_doc int NOT NULL,
    id_ackmsg int,
    rcv_state int NOT NULL,
    rcv_numreg varchar(20),
    rcv_datereg datetime
);


CREATE TABLE scr_regasoc (
    id int NOT NULL,
    id_archprim int NOT NULL,
    id_fdrprim int NOT NULL,
    id_archsec int NOT NULL,
    id_fdrsec int NOT NULL
);

CREATE TABLE scr_regasocex (
    id_asoc int NOT NULL,
    type int NOT NULL
);

CREATE TABLE scr_regint (
    id int NOT NULL,
    id_arch int NOT NULL,
    id_fdr int NOT NULL,
    name varchar(95) NOT NULL,
    id_person int NOT NULL,
    id_address int,
    ord int NOT NULL
);

CREATE TABLE scr_regpdocs (
    id int NOT NULL,
    id_arch int NOT NULL,
    id_fdr int NOT NULL,
    name_doc varchar(250) NOT NULL,
    presented int NOT NULL
);

CREATE TABLE scr_regstate (
    id int NOT NULL,
    idarchreg int NOT NULL,
    state int NOT NULL,
    closedate datetime,
    closeuser varchar(32),
    numeration_type int NOT NULL,
    image_auth int NOT NULL
);

CREATE TABLE scr_relations (
    typebook int NOT NULL,
    typerel int NOT NULL,
    relyear int NOT NULL,
    relmonth int NOT NULL,
    relday int NOT NULL,
    idofic int NOT NULL,
    reldate datetime NOT NULL,
    idunit int NOT NULL,
    nrel int NOT NULL
);

CREATE TABLE scr_reportarch (
    id int NOT NULL,
    id_report int NOT NULL,
    id_arch int NOT NULL
);

CREATE TABLE scr_reportofic (
    id int NOT NULL,
    id_report int NOT NULL,
    id_ofic int NOT NULL
);

CREATE TABLE scr_reportperf (
    id int NOT NULL,
    id_report int NOT NULL,
    id_perf int NOT NULL
);

CREATE TABLE scr_reports (
    id int NOT NULL,
    report varchar(250) NOT NULL,
    type_report int NOT NULL,
    type_arch int NOT NULL,
    all_arch int NOT NULL,
    all_ofics int NOT NULL,
    all_perfs int NOT NULL,
    description varchar(250) NOT NULL,
    data image NOT NULL
);

CREATE TABLE scr_repository (
    id int NOT NULL,
    type int NOT NULL,
    configuration text NOT NULL
);

CREATE TABLE scr_sendmsg (
    id int NOT NULL,
    uid_msg varchar(40) NOT NULL,
    type_msg int NOT NULL,
    file_name varchar(40),
    sender int NOT NULL,
    destination int NOT NULL,
    send_type int NOT NULL,
    gen_date datetime NOT NULL,
    send_date datetime,
    rcv_date datetime,
    send_state int NOT NULL,
    id_ackmsg int,
    send_num int NOT NULL
);

CREATE TABLE scr_sendreg (
    id int NOT NULL,
    id_msg int NOT NULL,
    id_arch int NOT NULL,
    id_fdr int NOT NULL,
    regorig_code varchar(16) NOT NULL,
    regorig_num varchar(20) NOT NULL,
    regorig_date datetime NOT NULL,
    regorig_type int NOT NULL,
    attach_pages int NOT NULL,
    attach_doc int NOT NULL,
    id_ackmsg int,
    rcv_state int NOT NULL,
    id_entreg_dest int NOT NULL,
    rcv_numreg varchar(20),
    rcv_datereg datetime
);

CREATE TABLE scr_sharedfiles (
    fileid int NOT NULL,
    ownerbookid int NOT NULL,
    ownerregid int NOT NULL,
    bookid int NOT NULL,
    regid int NOT NULL
);

CREATE TABLE scr_tmzofic (
    id int NOT NULL,
    tmz int NOT NULL
);

CREATE TABLE scr_translatedfmt (
    id int NOT NULL,
    langid int NOT NULL,
    spa_text varchar(254) NOT NULL,
    trans_text varchar(254) NOT NULL
);

CREATE TABLE scr_tt (
    id int NOT NULL,
    transport varchar(31) NOT NULL
);


CREATE TABLE scr_typeaddress (
    id int NOT NULL,
    description varchar(50) NOT NULL,
    code varchar(2) NOT NULL
);

CREATE TABLE scr_typeadm (
    id int NOT NULL,
    code varchar(1) NOT NULL,
    description varchar(50) NOT NULL
);

CREATE TABLE scr_typedoc (
    id int NOT NULL,
    description varchar(50) NOT NULL,
    type_person int NOT NULL,
    code varchar(1) NOT NULL
);

CREATE TABLE scr_typeofic (
    id int NOT NULL,
    description varchar(50) NOT NULL
);

CREATE TABLE scr_userconfig (
    userid int NOT NULL,
    data text NOT NULL,
	idoficpref int
);

CREATE TABLE scr_userfilter (
    idarch int NOT NULL,
    iduser int NOT NULL,
    filterdef text,
    type_obj int NOT NULL
);


CREATE TABLE scr_usrloc (
    userid int NOT NULL,
    tmstamp datetime NOT NULL,
    address varchar(255),
    city varchar(100),
    zip varchar(10),
    country varchar(100),
    telephone varchar(160),
    fax varchar(160),
    email varchar(255)
);

CREATE TABLE scr_usrofic (
    id int NOT NULL,
    iduser int NOT NULL,
    idofic int NOT NULL
);

CREATE TABLE scr_usrperms (
    id_usr int NOT NULL,
    tmstamp datetime NOT NULL,
    perms int NOT NULL
);

CREATE TABLE scr_valdate (
    id int NOT NULL,
    value datetime,
    oldvalue datetime
);

CREATE TABLE scr_valnum (
    id int NOT NULL,
    value int,
    oldvalue int
);

CREATE TABLE scr_valstr (
    id int NOT NULL,
    value varchar(250),
    oldvalue varchar(250)
);

CREATE TABLE scr_ws (
    id int NOT NULL,
    nombre varchar(50) NOT NULL,
    macaddr varchar(32),
    ipaddr varchar(20),
    idofic int,
    code varchar(4) NOT NULL,
    descripcion varchar(250)
);

CREATE TABLE scr_seqcnt (
	id int IDENTITY,
	userid int
);

CREATE TABLE scr_unitadm (
    userid int NOT NULL,
    objid int NOT NULL,
    objtype int NOT NULL
);

CREATE TABLE scr_lockitems (
    objtype int NOT NULL,
    objid int NOT NULL,
    userid  int NOT NULL,
    lockdate datetime NOT NULL
);

CREATE TABLE scr_versionitems (
    objtype int NOT NULL,
    code varchar(32) NOT NULL,
    objid int NOT NULL,
    version int NOT NULL,
    userid int NOT NULL,
    versiondate datetime NOT NULL
);

CREATE TABLE scr_orgslang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_typeadmlang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(50) NOT NULL
);

CREATE TABLE scr_oficlang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(32) NOT NULL
);

CREATE TABLE scr_calang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_ttlang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(31) NOT NULL
);

CREATE TABLE scr_reportslang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_bookslang (
	id int NOT NULL,
	language int NOT NULL,
	name varchar(250) NOT NULL
);

CREATE TABLE scr_repositoryconf (
	id int NOT NULL,
	data text NOT NULL
);

CREATE TABLE scr_repobooktype (
	book_type int NOT NULL,
	id_rep_conf int NOT NULL
);

CREATE TABLE scr_doclocator
(
	bookID int NOT NULL,
	folderID int NOT NULL,
	pageID int NOT NULL,
	locator varchar(128) NOT NULL
);