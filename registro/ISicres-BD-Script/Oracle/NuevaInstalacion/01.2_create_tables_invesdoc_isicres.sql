------------------------------------
--          InvesDoc              --
------------------------------------

CREATE TABLE IDOCAPPEVENT
(
  APPID    NUMBER(10) NOT NULL,
  EVENTID  NUMBER(10) NOT NULL,
  MACROID  NUMBER(10) NOT NULL
);

CREATE TABLE IDOCARCHDET
(
  ARCHID   NUMBER(10) NOT NULL,
  DETTYPE  NUMBER(10) NOT NULL,
  DETVAL   CLOB NOT NULL
);

CREATE TABLE IDOCARCHHDR
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  TBLPREFIX   VARCHAR2(16 CHAR) 	NOT NULL,
  TYPE        NUMBER(10) NOT NULL,
  FLAGS       NUMBER(10) NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10) NOT NULL,
  ACSID       NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCAUTONUMCTLG
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(64 CHAR) NOT NULL,
  TBLNAME   VARCHAR2(32 CHAR)  NOT NULL,
  INFO      VARCHAR2(254 CHAR) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCBTBLCTLG
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  DEF       CLOB NOT NULL,
  FLAGS     NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCCOMPUSG
(
  COMPID    NUMBER(10) NOT NULL,
  COMPTYPE  NUMBER(10) NOT NULL,
  USRID     NUMBER(10) NOT NULL,
  USRTYPE   NUMBER(10) NOT NULL,
  ARCHID    NUMBER(10) NOT NULL
);

CREATE TABLE IDOCDATNODE
(
  ID        NUMBER(10) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  PARENTID  NUMBER(10) NOT NULL
);

CREATE TABLE IDOCDBCTLG
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  DBNAME      VARCHAR2(32 CHAR) NOT NULL,
  DBUSER      VARCHAR2(32 CHAR) NOT NULL,
  DBPASSWORD  VARCHAR2(34 CHAR) NOT NULL,
  REMARKS     VARCHAR2(254 CHAR)
);

CREATE TABLE IDOCDBINFO
(
  ID              NUMBER(10) NOT NULL,
  NAME            VARCHAR2(32 CHAR) NOT NULL,
  GRALDBNAME      VARCHAR2(32 CHAR),
  GRALDBUSER      VARCHAR2(32 CHAR),
  GRALDBPASSWORD  VARCHAR2(34 CHAR),
  MISC            CLOB NOT NULL
);

CREATE TABLE IDOCDIRHDR
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  TYPE        NUMBER(10) NOT NULL,
  FLAGS       NUMBER(10) NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10) NOT NULL,
  ACSID       NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCFDRLINK
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR),
  CNTRDBID    NUMBER(10) NOT NULL,
  CNTRARCHID  NUMBER(10) NOT NULL,
  CNTRFDRID   NUMBER(10) NOT NULL,
  CNTRCLFID   NUMBER(10) NOT NULL,
  SRVRDBID    NUMBER(10) NOT NULL,
  SRVRARCHID  NUMBER(10) NOT NULL,
  SRVRFDRID   NUMBER(10) NOT NULL,
  FLAGS       NUMBER(10) NOT NULL,
  STAT        NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL
);

CREATE TABLE IDOCFDRLINKX
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR),
  CNTRDBID    NUMBER(10) NOT NULL,
  CNTRARCHID  NUMBER(10) NOT NULL,
  CNTRFDRID   NUMBER(10) NOT NULL,
  CNTRCLFID   NUMBER(10) NOT NULL,
  SRVRDBID    NUMBER(10) NOT NULL,
  SRVRARCHID  NUMBER(10) NOT NULL,
  SRVRFDRID   NUMBER(10) NOT NULL,
  FLAGS       NUMBER(10) NOT NULL,
  STAT        NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL
);

CREATE TABLE IDOCFDRSTAT
(
  FDRID      NUMBER(10) NOT NULL,
  ARCHID     NUMBER(10) NOT NULL,
  STAT       NUMBER(10) NOT NULL,
  USERID     NUMBER(10) NOT NULL,
  TIMESTAMP  DATE NOT NULL,
  FLAGS      NUMBER(10) NOT NULL
);

CREATE TABLE IDOCFIAUXTBLCTLG
(
  ID         NUMBER(10) NOT NULL,
  NAME       VARCHAR2(16 CHAR) NOT NULL,
  USERID     NUMBER(10) NOT NULL,
  TIMESTAMP  DATE NOT NULL
);

CREATE TABLE IDOCFMT
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  ARCHID      NUMBER(10) NOT NULL,
  TYPE        NUMBER(10) NOT NULL,
  SUBTYPE     NUMBER(10) NOT NULL,
  DATA        CLOB NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10) NOT NULL,
  ACSID       NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCGLBDOCH
(
  ID          NUMBER(10) NOT NULL,
  FDRID       NUMBER(10) NOT NULL,
  ARCHID      NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  CLFID       NUMBER(10) NOT NULL,
  TYPE        NUMBER(10) NOT NULL,
  TITLE       VARCHAR2(128 CHAR),
  AUTHOR      VARCHAR2(64 CHAR),
  KEYWORDS    VARCHAR2(254 CHAR),
  STAT        NUMBER(10) NOT NULL,
  REFCOUNT    NUMBER(10) NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10) NOT NULL,
  ACSID       NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL,
  UPDRID      NUMBER(10) NOT NULL,
  UPDDATE     DATE,
  ACCRID      NUMBER(10) NOT NULL,
  ACCDATE     DATE NOT NULL,
  ACCCOUNT    NUMBER(10) NOT NULL,
  TIMESTAMP   DATE NOT NULL
);

CREATE TABLE IDOCMACRO
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  LANGUAGE  NUMBER(10) NOT NULL,
  TEXT      CLOB NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCNEXTID
(
  TYPE  NUMBER(10) NOT NULL,
  ID    NUMBER(10) NOT NULL
);

CREATE TABLE IDOCPAGEANNS
(
  ID        NUMBER(10) NOT NULL,
  DATA      CLOB NOT NULL,
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCPICT
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  DATA      BLOB NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCPREFFMT
(
  ARCHID   NUMBER(10) NOT NULL,
  FMTTYPE  NUMBER(10) NOT NULL,
  USERID   NUMBER(10) NOT NULL,
  FMTID    NUMBER(10) NOT NULL
);

CREATE TABLE IDOCPREFWFMT
(
  ARCHID   NUMBER(10) NOT NULL,
  FMTTYPE  NUMBER(10) NOT NULL,
  USERID   NUMBER(10) NOT NULL,
  FMTID    NUMBER(10) NOT NULL
);

CREATE TABLE IDOCRPT
(
  ID          NUMBER(10) NOT NULL,
  NAME        VARCHAR2(32 CHAR) NOT NULL,
  ARCHID      NUMBER(10) NOT NULL,
  EDITTYPE    NUMBER(10) NOT NULL,
  DATA        BLOB NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10) NOT NULL,
  ACSID       NUMBER(10) NOT NULL,
  CRTRID      NUMBER(10) NOT NULL,
  CRTNDATE    DATE NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCRPTAUXTBL
(
  FDRID  NUMBER(10) NOT NULL
);

CREATE TABLE IDOCSRVSTATEDET
(
  ID         NUMBER(10) NOT NULL,
  SESSIONID  NUMBER(10) NOT NULL,
  PARENTID   NUMBER(10) NOT NULL,
  INFO       CLOB NOT NULL
);

CREATE TABLE IDOCSRVSTATEHDR
(
  ID         NUMBER(10) NOT NULL,
  PRODID     NUMBER(10) NOT NULL,
  APPID      NUMBER(10) NOT NULL,
  USERID     NUMBER(10) NOT NULL,
  ALIASID    NUMBER(10) NOT NULL,
  TIMESTAMP  DATE NOT NULL,
  RANDOM     NUMBER(10) NOT NULL,
  INFO       CLOB NOT NULL
);

CREATE TABLE IDOCSSNEXTID
(
  ID  NUMBER(10) NOT NULL
);

CREATE TABLE IDOCVTBLCTLG
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(64 CHAR) NOT NULL,
  BTBLID    NUMBER(10) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  INFO      CLOB NOT NULL,
  FLAGS     NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCWMACRO
(
  ID        NUMBER(10)NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  TEXT      CLOB NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCXNEXTID
(
  TYPE      NUMBER(10) NOT NULL,
  PARENTID  NUMBER(10) NOT NULL,
  ID        NUMBER(10) NOT NULL
);

CREATE TABLE ITDSMSNEXTID
(
  CID  NUMBER(10) NOT NULL
);

CREATE TABLE ITDSMSSESS
(
  CID      NUMBER(10) NOT NULL,
  CAPPID   VARCHAR2(64 CHAR) NOT NULL,
  CUSERID  NUMBER(10) NOT NULL,
  CCRTTS   DATE NOT NULL
);

CREATE TABLE IUSERCURRCNT
(
  ID         NUMBER(10) NOT NULL,
  USERID     NUMBER(10) NOT NULL,
  PRODID     NUMBER(10) NOT NULL,
  APPID      NUMBER(10) NOT NULL,
  TIMESTAMP  DATE NOT NULL
);

CREATE TABLE IUSERDEPTHDR
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(128 CHAR) NOT NULL,
  PARENTID  NUMBER(10) NOT NULL,
  MGRID     NUMBER(10) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERGENPERMS
(
  DSTTYPE  NUMBER(10) NOT NULL,
  DSTID    NUMBER(10) NOT NULL,
  PRODID   NUMBER(10) NOT NULL,
  PERMS    NUMBER(10) NOT NULL
);

CREATE TABLE IUSERGROUPHDR
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  MGRID     NUMBER(10) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR) ,
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERGROUPUSER
(
  GROUPID  NUMBER(10) NOT NULL,
  USERID   NUMBER(10) NOT NULL
);

CREATE TABLE IUSERLDAPGRPHDR
(
  ID            NUMBER(10) NOT NULL,
  LDAPGUID      VARCHAR2(36 CHAR) NOT NULL,
  LDAPFULLNAME  VARCHAR2(254 CHAR) NOT NULL,
  TYPE          NUMBER(10) NOT NULL
);

CREATE TABLE IUSERLDAPUSERHDR
(
  ID            NUMBER(10) NOT NULL,
  LDAPGUID      VARCHAR2(36 CHAR)  NOT NULL,
  LDAPFULLNAME  VARCHAR2(254 CHAR)  NOT NULL
);

CREATE TABLE IUSERLICENCES
(
  INFO  CLOB NOT NULL
);

CREATE TABLE IUSERNEXTID
(
  TYPE  NUMBER(10) NOT NULL,
  ID    NUMBER(10) NOT NULL
);

CREATE TABLE IUSEROBJHDR
(
  ID         NUMBER(10) NOT NULL,
  PRODID     NUMBER(10) NOT NULL,
  TYPE       NUMBER(10) NOT NULL,
  EXTID1     NUMBER(10) NOT NULL,
  EXTID2     NUMBER(10) NOT NULL,
  EXTID3     NUMBER(10) NOT NULL,
  OWNERTYPE  NUMBER(10) NOT NULL,
  OWNERID    NUMBER(10) NOT NULL,
  CRTRID     NUMBER(10) NOT NULL,
  CRTNDATE   DATE NOT NULL,
  UPDRID     NUMBER(10),
  UPDDATE    DATE
);

CREATE TABLE IUSEROBJPERM
(
  DSTTYPE  NUMBER(10) NOT NULL,
  DSTID    NUMBER(10) NOT NULL,
  OBJID    NUMBER(10) NOT NULL,
  APERM    NUMBER(10) NOT NULL
);

CREATE TABLE IUSERREMUSER
(
  ID       NUMBER(10) NOT NULL,
  NAME     VARCHAR2(32 CHAR) NOT NULL,
  REMARKS  VARCHAR2(254 CHAR),
  REMDATE  DATE NOT NULL
);

CREATE TABLE IUSERUSERHDR
(
  ID            NUMBER(10) NOT NULL,
  NAME          VARCHAR2(32 CHAR) NOT NULL,
  PASSWORD      VARCHAR2(68 CHAR) NOT NULL,
  DEPTID        NUMBER(10) NOT NULL,
  FLAGS         NUMBER(10) NOT NULL,
  STAT          NUMBER(10) NOT NULL,
  NUMBADCNTS    NUMBER(10) NOT NULL,
  REMARKS       VARCHAR2(254 CHAR),
  CRTRID        NUMBER(10) NOT NULL,
  CRTNDATE      DATE NOT NULL,
  UPDRID        NUMBER(10),
  UPDDATE       DATE,
  PWDLASTUPDTS  NUMBER NOT NULL,
  PWDMBC        VARCHAR2(1 CHAR) NOT NULL,
  PWDVPCHECK    VARCHAR2(1 CHAR) NOT NULL
);

CREATE TABLE IUSERUSERMAP
(
  SRCDBID    NUMBER(10) NOT NULL,
  SRCUSERID  NUMBER(10) NOT NULL,
  DSTDBID    NUMBER(10) NOT NULL,
  DSTUSERID  NUMBER(10) NOT NULL
);

CREATE TABLE IUSERUSERPWDS
(
  USERID    NUMBER(10) NOT NULL,
  PASSWORD  VARCHAR2(68 CHAR) NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERUSERSYS
(
  MAXBADCNTS    NUMBER(10) NOT NULL,
  PWDVP         NUMBER(20,4) NOT NULL,
  PWDMBC        VARCHAR2(1 CHAR) NOT NULL,
  PWDMINLEN     NUMBER(5) NOT NULL,
  PWDEXPIREDAP  NUMBER NOT NULL,
  NUMPWDLOCK    NUMBER(5) NOT NULL,
  FLAGS         NUMBER(10) NOT NULL,
  UPDRID        NUMBER(10),
  UPDDATE       DATE
);

CREATE TABLE IUSERUSERTYPE
(
  USERID  NUMBER(10) NOT NULL,
  PRODID  NUMBER(10) NOT NULL,
  TYPE    NUMBER(10) NOT NULL
);

CREATE TABLE IVOLACTDIR
(
  VOLID     NUMBER(10) NOT NULL,
  ACTDIR    NUMBER(10) NOT NULL,
  NUMFILES  NUMBER(10) NOT NULL
);

CREATE TABLE IVOLARCHLIST
(
  ARCHID  NUMBER(10) NOT NULL,
  LISTID  NUMBER(10) NOT NULL
);

CREATE TABLE IVOLFILEFTS
(
  ID         NUMBER(10) NOT NULL,
  EXTID1     NUMBER(10) NOT NULL,
  EXTID2     NUMBER(10) NOT NULL,
  EXTID3     NUMBER(10) NOT NULL,
  EXTID4     NUMBER(10) NOT NULL,
  PATH       VARCHAR2(254 CHAR) NOT NULL,
  TIMESTAMP  DATE NOT NULL
);

CREATE TABLE IVOLFILEHDR
(
  ID         NUMBER(10) NOT NULL,
  VOLID      NUMBER(10) NOT NULL,
  LOC        VARCHAR2(254 CHAR) NOT NULL,
  EXTID1     NUMBER(10) NOT NULL,
  EXTID2     NUMBER(10) NOT NULL,
  EXTID3     NUMBER(10) NOT NULL,
  FLAGS      NUMBER(10) NOT NULL,
  STAT       NUMBER(10) NOT NULL,
  TIMESTAMP  DATE,
  FILESIZE   NUMBER	NOT NULL
);

CREATE TABLE IVOLLISTHDR
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  FLAGS     NUMBER(10) NOT NULL,
  STAT      NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLLISTVOL
(
  LISTID     NUMBER(10) NOT NULL,
  VOLID      NUMBER(10) NOT NULL,
  SORTORDER  NUMBER(10) NOT NULL
);

CREATE TABLE IVOLNEXTID
(
  TYPE  NUMBER(10) NOT NULL,
  ID    NUMBER(10) NOT NULL
);

CREATE TABLE IVOLREPHDR
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  TYPE      NUMBER(10) NOT NULL,
  INFO      CLOB NOT NULL,
  STAT      NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLVOLHDR
(
  ID        NUMBER(10) NOT NULL,
  NAME      VARCHAR2(32 CHAR) NOT NULL,
  REPID     NUMBER(10) NOT NULL,
  INFO      CLOB NOT NULL,
  ITEMP     NUMBER(10) NOT NULL,
  ACTSIZE   VARCHAR2(32 CHAR) NOT NULL,
  NUMFILES  NUMBER(10) NOT NULL,
  STAT      NUMBER(10) NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10) NOT NULL,
  CRTNDATE  DATE NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLVOLTBL
(
  LOCID		VARCHAR2(32 CHAR) NOT NULL,
  EXTID1	NUMBER(10) NOT NULL,
  EXTID2	NUMBER(10) NOT NULL,
  EXTID3	NUMBER(10) NOT NULL,
  EXTID4	NUMBER(10) NOT NULL,
  FILEEXT	VARCHAR2(10 CHAR) NOT NULL,
  FILEVAL	BLOB NOT NULL
);

-- VISTAS
CREATE VIEW scr_lista_usuarios AS
    select * from (SELECT u.id, u.name, o.code, t.type, i.first_name, i.second_name, i.surname, u.deptid FROM scr_ofic o, iuserusertype t, (iuseruserhdr u LEFT JOIN scr_usrident i ON ((i.userid = u.id))) WHERE ((((u.id = t.userid) AND (o.deptid = u.deptid)) AND (t.prodid = 5)) AND (t.type <> 0)) ORDER BY u.name) A;

CREATE VIEW scr_users AS
    SELECT a.id, a.name, a.password, a.deptid, a.stat, a.remarks, b.first_name, b.second_name, b.surname FROM (iuseruserhdr a LEFT JOIN scr_usrident b ON ((b.userid = a.id)));

CREATE VIEW scr_lista_oficinas AS
    SELECT ofic.id, ofic.deptid, ofic.code, ofic.name AS nameor, ofic.acron, orgs.name AS nameer, ofic.creation_date, ofic.disable_date FROM scr_ofic ofic, scr_orgs orgs WHERE (ofic.id_orgs = orgs.id);


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



-------------------------
--     END VIEWS       --
-------------------------

