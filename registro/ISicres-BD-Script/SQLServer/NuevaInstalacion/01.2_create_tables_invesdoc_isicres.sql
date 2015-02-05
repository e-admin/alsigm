------------------------
-- TABLAS DE INVESDOC --
------------------------
CREATE TABLE idocappevent (
    appid int NOT NULL,
    eventid int NOT NULL,
    macroid int NOT NULL
);

CREATE TABLE idocarchhdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    tblprefix varchar(16) NOT NULL,
    type int NOT NULL,
    flags int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocautonumctlg (
    id int NOT NULL,
    name varchar(64) NOT NULL,
    tblname varchar(32) NOT NULL,
    info varchar(254) NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);



CREATE TABLE idoccompusg (
    compid int NOT NULL,
    comptype int NOT NULL,
    usrid int NOT NULL,
    usrtype int NOT NULL,
    archid int NOT NULL
);


CREATE TABLE idocdatnode (
    id int NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL
);

CREATE TABLE idocdbctlg (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    dbname varchar(32) NOT NULL,
    dbuser varchar(32) NOT NULL,
    dbpassword varchar(34) NOT NULL,
    remarks varchar(254)
);

CREATE TABLE idocdirhdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    flags int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocfdrlink (
    id int NOT NULL,
    name varchar(32),
    cntrdbid int NOT NULL,
    cntrarchid int NOT NULL,
    cntrfdrid int NOT NULL,
    cntrclfid int NOT NULL,
    srvrdbid int NOT NULL,
    srvrarchid int NOT NULL,
    srvrfdrid int NOT NULL,
    flags int NOT NULL,
    stat int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL
);

CREATE TABLE idocfdrlinkx (
    id int NOT NULL,
    name varchar(32),
    cntrdbid int NOT NULL,
    cntrarchid int NOT NULL,
    cntrfdrid int NOT NULL,
    cntrclfid int NOT NULL,
    srvrdbid int NOT NULL,
    srvrarchid int NOT NULL,
    srvrfdrid int NOT NULL,
    flags int NOT NULL,
    stat int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL
);

CREATE TABLE idocfdrstat (
    fdrid int NOT NULL,
    archid int NOT NULL,
    stat int NOT NULL,
    userid int NOT NULL,
    timestamp datetime NOT NULL,
    flags int NOT NULL
);


CREATE TABLE idocfiauxtblctlg (
    id int NOT NULL,
    name varchar(16) NOT NULL,
    userid int NOT NULL,
    timestamp datetime NOT NULL
);

CREATE TABLE idocglbdoch (
    id int NOT NULL,
    fdrid int NOT NULL,
    archid int NOT NULL,
    name varchar(32) NOT NULL,
    clfid int NOT NULL,
    type int NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    accrid int NOT NULL,
    accdate datetime NOT NULL,
    acccount int NOT NULL,
    timestamp datetime NOT NULL
);

CREATE TABLE idocnextid (
    type int NOT NULL,
    id int NOT NULL
);

CREATE TABLE idocpreffmt (
    archid int NOT NULL,
    fmttype int NOT NULL,
    userid int NOT NULL,
    fmtid int NOT NULL
);

CREATE TABLE idocprefwfmt (
    archid int NOT NULL,
    fmttype int NOT NULL,
    userid int NOT NULL,
    fmtid int NOT NULL
);


CREATE TABLE idocrptauxtbl (
    fdrid int NOT NULL
);

CREATE TABLE idocssnextid (
    id int NOT NULL
);

CREATE TABLE idocxnextid (
    type int NOT NULL,
    parentid int NOT NULL,
    id int NOT NULL
);



CREATE TABLE idocsrvstatedet (
    id int NOT NULL,
    sessionid int NOT NULL,
    parentid int NOT NULL,
    info text NOT NULL
);

CREATE TABLE idocsrvstatehdr (
    id int NOT NULL,
    prodid int NOT NULL,
    appid int NOT NULL,
    userid int NOT NULL,
    aliasid int NOT NULL,
    timestamp datetime NOT NULL,
    random int NOT NULL,
    info text NOT NULL
);

CREATE TABLE idocvtblctlg (
    id int NOT NULL,
    name varchar(64) NOT NULL,
    btblid int NOT NULL,
    type int NOT NULL,
    info text NOT NULL,
    flags int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocpageanns (
    id int NOT NULL,
    data text NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocwmacro (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    text text NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocarchdet (
    archid int NOT NULL,
    dettype int NOT NULL,
    detval text NOT NULL
);

CREATE TABLE idocbtblctlg (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    def text NOT NULL,
    flags int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocdbinfo (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    graldbname varchar(32),
    graldbuser varchar(32),
    graldbpassword varchar(34),
    misc text NOT NULL
);


CREATE TABLE idocfmt (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    archid int NOT NULL,
    type int NOT NULL,
    subtype int NOT NULL,
    data text NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);



CREATE TABLE idocmacro (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    language int NOT NULL,
    text text NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocpict (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    data image NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE idocrpt (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    archid int NOT NULL,
    edittype int NOT NULL,
    data image NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);


CREATE TABLE itdsmsnextid (
    cid int NOT NULL
);

CREATE TABLE itdsmssess (
    cid int NOT NULL,
    cappid varchar(64) NOT NULL,
    cuserid int NOT NULL,
    ccrtts datetime NOT NULL
);


CREATE TABLE iusercurrcnt (
    id int NOT NULL,
    userid int NOT NULL,
    prodid int NOT NULL,
    appid int NOT NULL,
    timestamp datetime NOT NULL
);

CREATE TABLE iuserdepthdr (
    id int NOT NULL,
    name varchar(128) NOT NULL,
    parentid int NOT NULL,
    mgrid int NOT NULL,
    type int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);


CREATE TABLE iusergenperms (
    dsttype int NOT NULL,
    dstid int NOT NULL,
    prodid int NOT NULL,
    perms int NOT NULL
);

CREATE TABLE iusergrouphdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    mgrid int NOT NULL,
    type int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE iusergroupuser (
    groupid int NOT NULL,
    userid int NOT NULL
);

CREATE TABLE iuserldapgrphdr (
    id int NOT NULL,
    ldapguid varchar(36) NOT NULL,
    ldapfullname varchar(254) NOT NULL,
    type int NOT NULL
);

CREATE TABLE iuserldapuserhdr (
    id int NOT NULL,
    ldapguid varchar(36) NOT NULL,
    ldapfullname varchar(254) NOT NULL
);

CREATE TABLE iusernextid (
    type int NOT NULL,
    id int NOT NULL
);

CREATE TABLE iuserobjhdr (
    id int NOT NULL,
    prodid int NOT NULL,
    type int NOT NULL,
    extid1 int NOT NULL,
    extid2 int NOT NULL,
    extid3 int NOT NULL,
    ownertype int NOT NULL,
    ownerid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE iuserobjperm (
    dsttype int NOT NULL,
    dstid int NOT NULL,
    objid int NOT NULL,
    aperm int NOT NULL
);


CREATE TABLE iuserremuser (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    remarks varchar(254),
    remdate datetime NOT NULL
);


CREATE TABLE iuseruserhdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    password varchar(68) NOT NULL,
    deptid int NOT NULL,
    flags int NOT NULL,
    stat int NOT NULL,
    numbadcnts int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    pwdlastupdts float NOT NULL,
    pwdmbc varchar(1) NOT NULL,
    pwdvpcheck varchar(1) NOT NULL
);

CREATE TABLE iuserusermap (
    srcdbid int NOT NULL,
    srcuserid int NOT NULL,
    dstdbid int NOT NULL,
    dstuserid int NOT NULL
);

CREATE TABLE iuseruserpwds (
    userid int NOT NULL,
    password varchar(68) NOT NULL,
    updrid int,
    upddate int
);

CREATE TABLE iuserusersys (
    maxbadcnts int NOT NULL,
    pwdvp float NOT NULL,
    pwdmbc varchar(1) NOT NULL,
    pwdminlen smallint NOT NULL,
    pwdexpiredap float NOT NULL,
    numpwdlock smallint NOT NULL,
    flags int NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE iuserusertype (
    userid int NOT NULL,
    prodid int NOT NULL,
    type int NOT NULL
);


CREATE TABLE iuserlicences (
    info text NOT NULL
);


CREATE TABLE ivolactdir (
    volid int NOT NULL,
    actdir int NOT NULL,
    numfiles int NOT NULL
);

CREATE TABLE ivolarchlist (
    archid int NOT NULL,
    listid int NOT NULL
);

CREATE TABLE ivolfilefts (
    id int NOT NULL,
    extid1 int NOT NULL,
    extid2 int NOT NULL,
    extid3 int NOT NULL,
    extid4 int NOT NULL,
    path varchar(254) NOT NULL,
    timestamp datetime NOT NULL
);

CREATE TABLE ivolfilehdr (
    id int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    extid1 int NOT NULL,
    extid2 int NOT NULL,
    extid3 int NOT NULL,
    flags int NOT NULL,
    stat int NOT NULL,
    timestamp datetime,
    filesize int NOT NULL
);

CREATE TABLE ivollisthdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    flags int NOT NULL,
    stat int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE ivollistvol (
    listid int NOT NULL,
    volid int NOT NULL,
    sortorder int NOT NULL
);

CREATE TABLE ivolnextid (
    type int NOT NULL,
    id int NOT NULL
);

CREATE TABLE ivolvoltbl (
   locid varchar(32) NOT NULL,
   extid1 int NOT NULL,
   extid2 int NOT NULL,
   extid3 int NOT NULL,
   extid4 int NOT NULL,
   fileext varchar(10) NOT NULL,
   fileval image NOT NULL
);



CREATE TABLE ivolrephdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    info text NOT NULL,
    stat int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE ivolvolhdr (
    id int NOT NULL,
    name varchar(32) NOT NULL,
    repid int NOT NULL,
    info text NOT NULL,
    itemp int NOT NULL,
    actsize varchar(32) NOT NULL,
    numfiles int NOT NULL,
    stat int NOT NULL,
    remarks varchar(254),
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE VIEW scr_lista_usuarios AS
      select * from (
		     SELECT u.id
			      , u.name
				  , o.code
				  , t.type
				  , i.first_name
				  , i.second_name
				  , i.surname
				  , u.deptid
			   FROM scr_ofic o
			      , iuserusertype t
				  , (iuseruserhdr u LEFT JOIN scr_usrident i ON ((i.userid = u.id)))
			  WHERE ((((u.id = t.userid)
			    AND    (o.deptid = u.deptid))
			    AND    (t.prodid = 5))
				AND    (t.type <> 0))
			  ) AS A;

CREATE VIEW scr_users AS
    SELECT a.id
	     , a.name
		 , a.password
		 , a.deptid
		 , a.stat
		 , a.remarks
		 , b.first_name
		 , b.second_name
		 , b.surname
     FROM (iuseruserhdr a LEFT JOIN scr_usrident b ON ((b.userid = a.id)));


CREATE VIEW scr_lista_oficinas AS
    SELECT ofic.id
	     , ofic.deptid
		 , ofic.code
		 , ofic.name AS nameor
		 , ofic.acron
		 , orgs.name AS nameer
		 , ofic.creation_date
		 , ofic.disable_date
	  FROM scr_ofic ofic
	     , scr_orgs orgs
	 WHERE (ofic.id_orgs = orgs.id);



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


