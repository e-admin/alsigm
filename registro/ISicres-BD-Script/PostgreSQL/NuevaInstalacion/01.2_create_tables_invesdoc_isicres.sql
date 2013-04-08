----------------------------------
--       InvesDoc               --
----------------------------------

CREATE TABLE idocappevent (
    appid integer NOT NULL,
    eventid integer NOT NULL,
    macroid integer NOT NULL
);

CREATE TABLE idocarchdet (
    archid integer NOT NULL,
    dettype integer NOT NULL,
    detval text NOT NULL
);

CREATE TABLE idocarchhdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    tblprefix character varying(16) NOT NULL,
    type integer NOT NULL,
    flags integer NOT NULL,
    remarks character varying(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocautonumctlg (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    tblname character varying(32) NOT NULL,
    info character varying(254) NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocbtblctlg (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    def text NOT NULL,
    flags integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idoccompusg (
    compid integer NOT NULL,
    comptype integer NOT NULL,
    usrid integer NOT NULL,
    usrtype integer NOT NULL,
    archid integer NOT NULL
);


CREATE TABLE idocdatnode (
    id integer NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL
);

CREATE TABLE idocdbctlg (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    dbname character varying(32) NOT NULL,
    dbuser character varying(32) NOT NULL,
    dbpassword character varying(34) NOT NULL,
    remarks character varying(254)
);

CREATE TABLE idocdbinfo (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    graldbname character varying(32),
    graldbuser character varying(32),
    graldbpassword character varying(34),
    misc text NOT NULL
);


CREATE TABLE idocdirhdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    type integer NOT NULL,
    flags integer NOT NULL,
    remarks character varying(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocfdrlink (
    id integer NOT NULL,
    name character varying(32),
    cntrdbid integer NOT NULL,
    cntrarchid integer NOT NULL,
    cntrfdrid integer NOT NULL,
    cntrclfid integer NOT NULL,
    srvrdbid integer NOT NULL,
    srvrarchid integer NOT NULL,
    srvrfdrid integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL
);

CREATE TABLE idocfdrlinkx (
    id integer NOT NULL,
    name character varying(32),
    cntrdbid integer NOT NULL,
    cntrarchid integer NOT NULL,
    cntrfdrid integer NOT NULL,
    cntrclfid integer NOT NULL,
    srvrdbid integer NOT NULL,
    srvrarchid integer NOT NULL,
    srvrfdrid integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL
);

CREATE TABLE idocfdrstat (
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    stat integer NOT NULL,
    userid integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    flags integer NOT NULL
);

CREATE TABLE idocfiauxtblctlg (
    id integer NOT NULL,
    name character varying(16) NOT NULL,
    userid integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL
);

CREATE TABLE idocfmt (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    archid integer NOT NULL,
    type integer NOT NULL,
    subtype integer NOT NULL,
    data text NOT NULL,
    remarks character varying(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocglbdoch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name character varying(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title character varying(128),
    author character varying(64),
    keywords character varying(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks character varying(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone,
    accrid integer NOT NULL,
    accdate timestamp without time zone NOT NULL,
    acccount integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL
);

CREATE TABLE idocmacro (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    type integer NOT NULL,
    language integer NOT NULL,
    text text NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocnextid (
    type integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE idocpageanns (
    id integer NOT NULL,
    data text NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocpict (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    type integer NOT NULL,
    data bytea NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocpreffmt (
    archid integer NOT NULL,
    fmttype integer NOT NULL,
    userid integer NOT NULL,
    fmtid integer NOT NULL
);

CREATE TABLE idocprefwfmt (
    archid integer NOT NULL,
    fmttype integer NOT NULL,
    userid integer NOT NULL,
    fmtid integer NOT NULL
);

CREATE TABLE idocrpt (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    archid integer NOT NULL,
    edittype integer NOT NULL,
    data bytea NOT NULL,
    remarks character varying(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocrptauxtbl (
    fdrid integer NOT NULL
);

CREATE TABLE idocsrvstatedet (
    id integer NOT NULL,
    sessionid integer NOT NULL,
    parentid integer NOT NULL,
    info text NOT NULL
);

CREATE TABLE idocsrvstatehdr (
    id integer NOT NULL,
    prodid integer NOT NULL,
    appid integer NOT NULL,
    userid integer NOT NULL,
    aliasid integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    random integer NOT NULL,
    info text NOT NULL
);

CREATE TABLE idocssnextid (
    id integer NOT NULL
);

CREATE TABLE idocvtblctlg (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    btblid integer NOT NULL,
    type integer NOT NULL,
    info text NOT NULL,
    flags integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocwmacro (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    text text NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE idocxnextid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE itdsmsnextid (
    cid integer NOT NULL
);

CREATE TABLE itdsmssess (
    cid integer NOT NULL,
    cappid character varying(64) NOT NULL,
    cuserid integer NOT NULL,
    ccrtts timestamp without time zone NOT NULL
);

CREATE TABLE iusercurrcnt (
    id integer NOT NULL,
    userid integer NOT NULL,
    prodid integer NOT NULL,
    appid integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL
);

CREATE TABLE iuserdepthdr (
    id integer NOT NULL,
    name character varying(128) NOT NULL,
    parentid integer NOT NULL,
    mgrid integer NOT NULL,
    type integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE iusergenperms (
    dsttype integer NOT NULL,
    dstid integer NOT NULL,
    prodid integer NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE iusergrouphdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    mgrid integer NOT NULL,
    type integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE iusergroupuser (
    groupid integer NOT NULL,
    userid integer NOT NULL
);

CREATE TABLE iuserldapgrphdr (
    id integer NOT NULL,
    ldapguid character varying(36) NOT NULL,
    ldapfullname character varying(254) NOT NULL,
    type integer NOT NULL
);

CREATE TABLE iuserldapuserhdr (
    id integer NOT NULL,
    ldapguid character varying(36) NOT NULL,
    ldapfullname character varying(254) NOT NULL
);

CREATE TABLE iuserlicences (
    info text NOT NULL
);

CREATE TABLE iusernextid (
    type integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE iuserobjhdr (
    id integer NOT NULL,
    prodid integer NOT NULL,
    type integer NOT NULL,
    extid1 integer NOT NULL,
    extid2 integer NOT NULL,
    extid3 integer NOT NULL,
    ownertype integer NOT NULL,
    ownerid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE iuserobjperm (
    dsttype integer NOT NULL,
    dstid integer NOT NULL,
    objid integer NOT NULL,
    aperm integer NOT NULL
);

CREATE TABLE iuserremuser (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    remarks character varying(254),
    remdate timestamp without time zone NOT NULL
);

CREATE TABLE iuseruserhdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    password character varying(68) NOT NULL,
    deptid integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    numbadcnts integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone,
    pwdlastupdts double precision NOT NULL,
    pwdmbc character varying(1) NOT NULL,
    pwdvpcheck character varying(1) NOT NULL
);

CREATE TABLE iuserusermap (
    srcdbid integer NOT NULL,
    srcuserid integer NOT NULL,
    dstdbid integer NOT NULL,
    dstuserid integer NOT NULL
);

CREATE TABLE iuseruserpwds (
    userid integer NOT NULL,
    password character varying(68) NOT NULL,
    updrid integer,
    upddate integer
);

CREATE TABLE iuserusersys (
    maxbadcnts integer NOT NULL,
    pwdvp double precision NOT NULL,
    pwdmbc character varying(1) NOT NULL,
    pwdminlen smallint NOT NULL,
    pwdexpiredap double precision NOT NULL,
    numpwdlock smallint NOT NULL,
    flags integer NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE iuserusertype (
    userid integer NOT NULL,
    prodid integer NOT NULL,
    type integer NOT NULL
);

CREATE TABLE ivolactdir (
    volid integer NOT NULL,
    actdir integer NOT NULL,
    numfiles integer NOT NULL
);

CREATE TABLE ivolarchlist (
    archid integer NOT NULL,
    listid integer NOT NULL
);

CREATE TABLE ivolfilefts (
    id integer NOT NULL,
    extid1 integer NOT NULL,
    extid2 integer NOT NULL,
    extid3 integer NOT NULL,
    extid4 integer NOT NULL,
    path character varying(254) NOT NULL,
    "timestamp" timestamp without time zone NOT NULL
);

CREATE TABLE ivolfilehdr (
    id integer NOT NULL,
    volid integer NOT NULL,
    loc character varying(254) NOT NULL,
    extid1 integer NOT NULL,
    extid2 integer NOT NULL,
    extid3 integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    "timestamp" timestamp without time zone,
    filesize integer NOT NULL
);

CREATE TABLE ivollisthdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE ivollistvol (
    listid integer NOT NULL,
    volid integer NOT NULL,
    sortorder integer NOT NULL
);

CREATE TABLE ivolnextid (
    type integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE ivolrephdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    type integer NOT NULL,
    info text NOT NULL,
    stat integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE ivolvolhdr (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    repid integer NOT NULL,
    info text NOT NULL,
    itemp integer NOT NULL,
    actsize character varying(32) NOT NULL,
    numfiles integer NOT NULL,
    stat integer NOT NULL,
    remarks character varying(254),
    crtrid integer NOT NULL,
    crtndate timestamp without time zone NOT NULL,
    updrid integer,
    upddate timestamp without time zone
);

CREATE TABLE ivolvoltbl (
    locid character varying(32) NOT NULL,
    extid1 integer NOT NULL,
    extid2 integer NOT NULL,
    extid3 integer NOT NULL,
    extid4 integer NOT NULL,
    fileext character varying(10) NOT NULL,
    fileval bytea NOT NULL
);

-- VISTAS

CREATE VIEW scr_lista_oficinas AS
    SELECT ofic.id, ofic.deptid, ofic.code, ofic.name AS nameor, ofic.acron, orgs.name AS nameer, ofic.creation_date, ofic.disable_date FROM scr_ofic ofic, scr_orgs orgs WHERE (ofic.id_orgs = orgs.id);

CREATE VIEW scr_lista_usuarios AS
    SELECT u.id, u.name, o.code, t.type, i.first_name, i.second_name, i.surname, u.deptid FROM scr_ofic o, iuserusertype t, (iuseruserhdr u LEFT JOIN scr_usrident i ON ((i.userid = u.id))) WHERE ((((u.id = t.userid) AND (o.deptid = u.deptid)) AND (t.prodid = 5)) AND (t.type <> 0)) ORDER BY u.name;

CREATE VIEW scr_users AS
    SELECT a.id, a.name, a.password, a.deptid, a.stat, a.remarks, b.first_name, b.second_name, b.surname FROM (iuseruserhdr a LEFT JOIN scr_usrident b ON ((b.userid = a.id)));


CREATE VIEW scr_orgs_eu (
			 id
			,code
			,id_father
			,acron
			,name
			,creation_date
			,disable_date
			,type
			,enabled
			,cif
) AS
	SELECT A.id
		 , A.code
		 , A.id_father
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.type
		 , A.enabled
		 , A.cif
	  FROM scr_orgs A LEFT OUTER JOIN scr_orgslang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_orgs_ct (
			 id
			,code
			,id_father
			,acron
			,name
			,creation_date
			,disable_date
			,type
			,enabled
			,cif
) AS
	SELECT A.id
		 , A.code
		 , A.id_father
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.type
		 , A.enabled
		 , A.cif
	  FROM scr_orgs A LEFT OUTER JOIN scr_orgslang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_orgs_gl (
			 id
			,code
			,id_father
			,acron
			,name
			,creation_date
			,disable_date
			,type
			,enabled
			,cif
) AS
	SELECT A.id
		 , A.code
		 , A.id_father
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.type
		 , A.enabled
		 , A.cif
	  FROM scr_orgs A LEFT OUTER JOIN scr_orgslang B ON B.id = A.id AND B.language=86;



CREATE VIEW scr_typeadm_eu (id, code, description) AS
	SELECT A.id
		 , A.code
		 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
	  FROM scr_typeadm A LEFT OUTER JOIN scr_typeadmlang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_typeadm_ct (id,code,description) AS
	SELECT A.id
		 , A.code
		 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
	  FROM scr_typeadm A LEFT OUTER JOIN scr_typeadmlang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_typeadm_gl (id,code,description) AS
	SELECT A.id
		 , A.code
		 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
	  FROM scr_typeadm A LEFT OUTER JOIN scr_typeadmlang B ON B.id = A.id AND B.language=86;


CREATE VIEW scr_ofic_eu (
			 id
			,code
			,acron
			,name
			,creation_date
			,disable_date
			,id_orgs
			,stamp
			,deptid
			,type
) AS
	SELECT A.id
		 , A.code
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.id_orgs
		 , A.stamp
		 , A.deptid
		 , A.type
	  FROM scr_ofic A LEFT OUTER JOIN scr_oficlang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_ofic_ct (
			 id
			,code
			,acron
			,name
			,creation_date
			,disable_date
			,id_orgs
			,stamp
			,deptid
			,type
) AS
	SELECT A.id
		 , A.code
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.id_orgs
		 , A.stamp
		 , A.deptid
		 , A.type
	  FROM scr_ofic A LEFT OUTER JOIN scr_oficlang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_ofic_gl (
			 id
			,code
			,acron
			,name
			,creation_date
			,disable_date
			,id_orgs
			,stamp
			,deptid
			,type
) AS
	SELECT A.id
		 , A.code
		 , A.acron
		 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
		 , A.creation_date
		 , A.disable_date
		 , A.id_orgs
		 , A.stamp
		 , A.deptid
		 , A.type
	  FROM scr_ofic A LEFT OUTER JOIN scr_oficlang B ON B.id = A.id AND B.language=86;



CREATE VIEW scr_ca_eu (
			 id
			,code
			,matter
			,for_ereg
			,for_sreg
			,all_ofics
			,id_arch
			,creation_date
			,disable_date
			,enabled
			,id_org
) AS
		SELECT A.id
			 , A.code
			 , CASE WHEN B.name IS NULL THEN A.matter ELSE B.name END AS matter
			 , A.for_ereg
			 , A.for_sreg
			 , A.all_ofics
			 , A.id_arch
			 , A.creation_date
			 , A.disable_date
			 , A.enabled
			 , A.id_org
		  FROM scr_ca A LEFT OUTER JOIN scr_calang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_ca_ct (
			 id
			,code
			,matter
			,for_ereg
			,for_sreg
			,all_ofics
			,id_arch
			,creation_date
			,disable_date
			,enabled
			,id_org
) AS
		SELECT A.id
			 , A.code
			 , CASE WHEN B.name IS NULL THEN A.matter ELSE B.name END AS matter
			 , A.for_ereg
			 , A.for_sreg
			 , A.all_ofics
			 , A.id_arch
			 , A.creation_date
			 , A.disable_date
			 , A.enabled
			 , A.id_org
		  FROM scr_ca A LEFT OUTER JOIN scr_calang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_ca_gl (
			 id
			,code
			,matter
			,for_ereg
			,for_sreg
			,all_ofics
			,id_arch
			,creation_date
			,disable_date
			,enabled
			,id_org
) AS
		SELECT A.id
			 , A.code
			 , CASE WHEN B.name IS NULL THEN A.matter ELSE B.name END AS matter
			 , A.for_ereg
			 , A.for_sreg
			 , A.all_ofics
			 , A.id_arch
			 , A.creation_date
			 , A.disable_date
			 , A.enabled
			 , A.id_org
		  FROM scr_ca A LEFT OUTER JOIN scr_calang B ON B.id = A.id AND B.language=86;



CREATE VIEW scr_tt_eu (id, transport) AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.transport ELSE B.name END AS transport
		  FROM scr_tt A LEFT OUTER JOIN scr_ttlang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_tt_ct (id, transport)AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.transport ELSE B.name END AS transport
		  FROM scr_tt A LEFT OUTER JOIN scr_ttlang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_tt_gl (id, transport)AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.transport ELSE B.name END AS transport
		  FROM scr_tt A LEFT OUTER JOIN scr_ttlang B ON B.id = A.id AND B.language=86;



CREATE VIEW scr_reports_eu (
			 id
			,report
			,type_report
			,type_arch
			,all_arch
			,all_ofics
			,all_perfs
			,description
			,data
) AS
		SELECT A.id
			 , A.report
			 , A.type_report
			 , A.type_arch
			 , A.all_arch
			 , A.all_ofics
			 , A.all_perfs
			 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
			 , A.data
		  FROM scr_reports A LEFT OUTER JOIN scr_reportslang B ON B.id = A.id AND B.language=45;

CREATE VIEW scr_reports_ct (
			 id
			,report
			,type_report
			,type_arch
			,all_arch
			,all_ofics
			,all_perfs
			,description
			,data
) AS
		SELECT A.id
			 , A.report
			 , A.type_report
			 , A.type_arch
			 , A.all_arch
			 , A.all_ofics
			 , A.all_perfs
			 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
			 , A.data
		  FROM scr_reports A LEFT OUTER JOIN scr_reportslang B ON B.id = A.id AND B.language=3;

CREATE VIEW scr_reports_gl (
			 id
			,report
			,type_report
			,type_arch
			,all_arch
			,all_ofics
			,all_perfs
			,description
			,data
) AS
		SELECT A.id
			 , A.report
			 , A.type_report
			 , A.type_arch
			 , A.all_arch
			 , A.all_ofics
			 , A.all_perfs
			 , CASE WHEN B.name IS NULL THEN A.description ELSE B.name END AS description
			 , A.data
		  FROM scr_reports A LEFT OUTER JOIN scr_reportslang B ON B.id = A.id AND B.language=86;



CREATE VIEW idocarchhdr_eu (
			 id
			,name
			,tblprefix
			,type
			,flags
			,remarks
			,accesstype
			,acsid
			,crtrid
			,crtndate
			,updrid
			,upddate
) AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
			 , A.tblprefix
			 , A.type
			 , A.flags
			 , A.remarks
			 , A.accesstype
			 , A.acsid
			 , A.crtrid
			 , A.crtndate
			 , A.updrid
			 , A.upddate
		 FROM idocarchhdr A LEFT OUTER JOIN scr_bookslang B ON B.id = A.id AND B.language=45;

CREATE VIEW idocarchhdr_ct (
			 id
			,name
			,tblprefix
			,type
			,flags
			,remarks
			,accesstype
			,acsid
			,crtrid
			,crtndate
			,updrid
			,upddate
) AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
			 , A.tblprefix
			 , A.type
			 , A.flags
			 , A.remarks
			 , A.accesstype
			 , A.acsid
			 , A.crtrid
			 , A.crtndate
			 , A.updrid
			 , A.upddate
		 FROM idocarchhdr A LEFT OUTER JOIN scr_bookslang B ON B.id = A.id AND B.language=3;

CREATE VIEW idocarchhdr_gl (
			 id
			,name
			,tblprefix
			,type
			,flags
			,remarks
			,accesstype
			,acsid
			,crtrid
			,crtndate
			,updrid
			,upddate
) AS
		SELECT A.id
			 , CASE WHEN B.name IS NULL THEN A.name ELSE B.name END as name
			 , A.tblprefix
			 , A.type
			 , A.flags
			 , A.remarks
			 , A.accesstype
			 , A.acsid
			 , A.crtrid
			 , A.crtndate
			 , A.updrid
			 , A.upddate
		 FROM idocarchhdr A LEFT OUTER JOIN scr_bookslang B ON B.id = A.id AND B.language=86;


