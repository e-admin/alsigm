------------------------------------
--          InvesDoc              --
------------------------------------

CREATE TABLE IDOCAPPEVENT
(
  APPID    NUMBER(10)                           NOT NULL,
  EVENTID  NUMBER(10)                           NOT NULL,
  MACROID  NUMBER(10)                           NOT NULL
);

CREATE TABLE IDOCARCHDET
(
  ARCHID   NUMBER(10)                           NOT NULL,
  DETTYPE  NUMBER(10)                           NOT NULL,
  DETVAL   CLOB                                 NOT NULL
);

CREATE TABLE IDOCARCHHDR
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  TBLPREFIX   VARCHAR2(16 CHAR)                 NOT NULL,
  TYPE        NUMBER(10)                        NOT NULL,
  FLAGS       NUMBER(10)                        NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10)                        NOT NULL,
  ACSID       NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCAUTONUMCTLG
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(64 CHAR)                   NOT NULL,
  TBLNAME   VARCHAR2(32 CHAR)                   NOT NULL,
  INFO      VARCHAR2(254 CHAR)                  NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCBTBLCTLG
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  DEF       CLOB                                NOT NULL,
  FLAGS     NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCCOMPUSG
(
  COMPID    NUMBER(10)                          NOT NULL,
  COMPTYPE  NUMBER(10)                          NOT NULL,
  USRID     NUMBER(10)                          NOT NULL,
  USRTYPE   NUMBER(10)                          NOT NULL,
  ARCHID    NUMBER(10)                          NOT NULL
);

CREATE TABLE IDOCDATNODE
(
  ID        NUMBER(10)                          NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  PARENTID  NUMBER(10)                          NOT NULL
);

CREATE TABLE IDOCDBCTLG
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  DBNAME      VARCHAR2(32 CHAR)                 NOT NULL,
  DBUSER      VARCHAR2(32 CHAR)                 NOT NULL,
  DBPASSWORD  VARCHAR2(34 CHAR)                 NOT NULL,
  REMARKS     VARCHAR2(254 CHAR)
);

CREATE TABLE IDOCDBINFO
(
  ID              NUMBER(10)                    NOT NULL,
  NAME            VARCHAR2(32 CHAR)             NOT NULL,
  GRALDBNAME      VARCHAR2(32 CHAR),
  GRALDBUSER      VARCHAR2(32 CHAR),
  GRALDBPASSWORD  VARCHAR2(34 CHAR),
  MISC            CLOB                          NOT NULL
);

CREATE TABLE IDOCDIRHDR
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  TYPE        NUMBER(10)                        NOT NULL,
  FLAGS       NUMBER(10)                        NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10)                        NOT NULL,
  ACSID       NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCFDRLINK
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR),
  CNTRDBID    NUMBER(10)                        NOT NULL,
  CNTRARCHID  NUMBER(10)                        NOT NULL,
  CNTRFDRID   NUMBER(10)                        NOT NULL,
  CNTRCLFID   NUMBER(10)                        NOT NULL,
  SRVRDBID    NUMBER(10)                        NOT NULL,
  SRVRARCHID  NUMBER(10)                        NOT NULL,
  SRVRFDRID   NUMBER(10)                        NOT NULL,
  FLAGS       NUMBER(10)                        NOT NULL,
  STAT        NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL
);

CREATE TABLE IDOCFDRLINKX
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR),
  CNTRDBID    NUMBER(10)                        NOT NULL,
  CNTRARCHID  NUMBER(10)                        NOT NULL,
  CNTRFDRID   NUMBER(10)                        NOT NULL,
  CNTRCLFID   NUMBER(10)                        NOT NULL,
  SRVRDBID    NUMBER(10)                        NOT NULL,
  SRVRARCHID  NUMBER(10)                        NOT NULL,
  SRVRFDRID   NUMBER(10)                        NOT NULL,
  FLAGS       NUMBER(10)                        NOT NULL,
  STAT        NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL
);

CREATE TABLE IDOCFDRSTAT
(
  FDRID      NUMBER(10)                         NOT NULL,
  ARCHID     NUMBER(10)                         NOT NULL,
  STAT       NUMBER(10)                         NOT NULL,
  USERID     NUMBER(10)                         NOT NULL,
  TIMESTAMP  DATE                               NOT NULL,
  FLAGS      NUMBER(10)                         NOT NULL
);

CREATE TABLE IDOCFIAUXTBLCTLG
(
  ID         NUMBER(10)                         NOT NULL,
  NAME       VARCHAR2(16 CHAR)                  NOT NULL,
  USERID     NUMBER(10)                         NOT NULL,
  TIMESTAMP  DATE                               NOT NULL
);

CREATE TABLE IDOCFMT
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  ARCHID      NUMBER(10)                        NOT NULL,
  TYPE        NUMBER(10)                        NOT NULL,
  SUBTYPE     NUMBER(10)                        NOT NULL,
  DATA        CLOB                              NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10)                        NOT NULL,
  ACSID       NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCGLBDOCH
(
  ID          NUMBER(10)                        NOT NULL,
  FDRID       NUMBER(10)                        NOT NULL,
  ARCHID      NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  CLFID       NUMBER(10)                        NOT NULL,
  TYPE        NUMBER(10)                        NOT NULL,
  TITLE       VARCHAR2(128 CHAR),
  AUTHOR      VARCHAR2(64 CHAR),
  KEYWORDS    VARCHAR2(254 CHAR),
  STAT        NUMBER(10)                        NOT NULL,
  REFCOUNT    NUMBER(10)                        NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10)                        NOT NULL,
  ACSID       NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE,
  ACCRID      NUMBER(10)                        NOT NULL,
  ACCDATE     DATE                              NOT NULL,
  ACCCOUNT    NUMBER(10)                        NOT NULL,
  TIMESTAMP   DATE                              NOT NULL
);

CREATE TABLE IDOCMACRO
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  LANGUAGE  NUMBER(10)                          NOT NULL,
  TEXT      CLOB                                NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCNEXTID
(
  TYPE  NUMBER(10)                              NOT NULL,
  ID    NUMBER(10)                              NOT NULL
);

CREATE TABLE IDOCPAGEANNS
(
  ID        NUMBER(10)                          NOT NULL,
  DATA      CLOB                                NOT NULL,
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCPICT
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  DATA      BLOB                                NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCPREFFMT
(
  ARCHID   NUMBER(10)                           NOT NULL,
  FMTTYPE  NUMBER(10)                           NOT NULL,
  USERID   NUMBER(10)                           NOT NULL,
  FMTID    NUMBER(10)                           NOT NULL
);

CREATE TABLE IDOCPREFWFMT
(
  ARCHID   NUMBER(10)                           NOT NULL,
  FMTTYPE  NUMBER(10)                           NOT NULL,
  USERID   NUMBER(10)                           NOT NULL,
  FMTID    NUMBER(10)                           NOT NULL
);

CREATE TABLE IDOCRPT
(
  ID          NUMBER(10)                        NOT NULL,
  NAME        VARCHAR2(32 CHAR)                 NOT NULL,
  ARCHID      NUMBER(10)                        NOT NULL,
  EDITTYPE    NUMBER(10)                        NOT NULL,
  DATA        BLOB                              NOT NULL,
  REMARKS     VARCHAR2(254 CHAR),
  ACCESSTYPE  NUMBER(10)                        NOT NULL,
  ACSID       NUMBER(10)                        NOT NULL,
  CRTRID      NUMBER(10)                        NOT NULL,
  CRTNDATE    DATE                              NOT NULL,
  UPDRID      NUMBER(10),
  UPDDATE     DATE
);

CREATE TABLE IDOCRPTAUXTBL
(
  FDRID  NUMBER(10)                             NOT NULL
);

CREATE TABLE IDOCSRVSTATEDET
(
  ID         NUMBER(10)                         NOT NULL,
  SESSIONID  NUMBER(10)                         NOT NULL,
  PARENTID   NUMBER(10)                         NOT NULL,
  INFO       CLOB                               NOT NULL
);

CREATE TABLE IDOCSRVSTATEHDR
(
  ID         NUMBER(10)                         NOT NULL,
  PRODID     NUMBER(10)                         NOT NULL,
  APPID      NUMBER(10)                         NOT NULL,
  USERID     NUMBER(10)                         NOT NULL,
  ALIASID    NUMBER(10)                         NOT NULL,
  TIMESTAMP  DATE                               NOT NULL,
  RANDOM     NUMBER(10)                         NOT NULL,
  INFO       CLOB                               NOT NULL
);

CREATE TABLE IDOCSSNEXTID
(
  ID  NUMBER(10)                                NOT NULL
);

CREATE TABLE IDOCVTBLCTLG
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(64 CHAR)                   NOT NULL,
  BTBLID    NUMBER(10)                          NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  INFO      CLOB                                NOT NULL,
  FLAGS     NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCWMACRO
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  TEXT      CLOB                                NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IDOCXNEXTID
(
  TYPE      NUMBER(10)                          NOT NULL,
  PARENTID  NUMBER(10)                          NOT NULL,
  ID        NUMBER(10)                          NOT NULL
);

CREATE TABLE ITDSMSNEXTID
(
  CID  NUMBER(10)                               NOT NULL
);

CREATE TABLE ITDSMSSESS
(
  CID      NUMBER(10)                           NOT NULL,
  CAPPID   VARCHAR2(64 CHAR)                    NOT NULL,
  CUSERID  NUMBER(10)                           NOT NULL,
  CCRTTS   DATE                                 NOT NULL
);

CREATE TABLE IUSERCURRCNT
(
  ID         NUMBER(10)                         NOT NULL,
  USERID     NUMBER(10)                         NOT NULL,
  PRODID     NUMBER(10)                         NOT NULL,
  APPID      NUMBER(10)                         NOT NULL,
  TIMESTAMP  DATE                               NOT NULL
);

CREATE TABLE IUSERDEPTHDR
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(128 CHAR)                  NOT NULL,
  PARENTID  NUMBER(10)                          NOT NULL,
  MGRID     NUMBER(10)                          NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERGENPERMS
(
  DSTTYPE  NUMBER(10)                           NOT NULL,
  DSTID    NUMBER(10)                           NOT NULL,
  PRODID   NUMBER(10)                           NOT NULL,
  PERMS    NUMBER(10)                           NOT NULL
);

CREATE TABLE IUSERGROUPHDR
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  MGRID     NUMBER(10)                          NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERGROUPUSER
(
  GROUPID  NUMBER(10)                           NOT NULL,
  USERID   NUMBER(10)                           NOT NULL
);

CREATE TABLE IUSERLDAPGRPHDR
(
  ID            NUMBER(10)                      NOT NULL,
  LDAPGUID      VARCHAR2(36 CHAR)               NOT NULL,
  LDAPFULLNAME  VARCHAR2(254 CHAR)              NOT NULL,
  TYPE          NUMBER(10)                      NOT NULL
);

CREATE TABLE IUSERLDAPUSERHDR
(
  ID            NUMBER(10)                      NOT NULL,
  LDAPGUID      VARCHAR2(36 CHAR)               NOT NULL,
  LDAPFULLNAME  VARCHAR2(254 CHAR)              NOT NULL
);

CREATE TABLE IUSERLICENCES
(
  INFO  CLOB                                    NOT NULL
);

CREATE TABLE IUSERNEXTID
(
  TYPE  NUMBER(10)                              NOT NULL,
  ID    NUMBER(10)                              NOT NULL
);

CREATE TABLE IUSEROBJHDR
(
  ID         NUMBER(10)                         NOT NULL,
  PRODID     NUMBER(10)                         NOT NULL,
  TYPE       NUMBER(10)                         NOT NULL,
  EXTID1     NUMBER(10)                         NOT NULL,
  EXTID2     NUMBER(10)                         NOT NULL,
  EXTID3     NUMBER(10)                         NOT NULL,
  OWNERTYPE  NUMBER(10)                         NOT NULL,
  OWNERID    NUMBER(10)                         NOT NULL,
  CRTRID     NUMBER(10)                         NOT NULL,
  CRTNDATE   DATE                               NOT NULL,
  UPDRID     NUMBER(10),
  UPDDATE    DATE
);

CREATE TABLE IUSEROBJPERM
(
  DSTTYPE  NUMBER(10)                           NOT NULL,
  DSTID    NUMBER(10)                           NOT NULL,
  OBJID    NUMBER(10)                           NOT NULL,
  APERM    NUMBER(10)                           NOT NULL
);

CREATE TABLE IUSERREMUSER
(
  ID       NUMBER(10)                           NOT NULL,
  NAME     VARCHAR2(32 CHAR)                    NOT NULL,
  REMARKS  VARCHAR2(254 CHAR),
  REMDATE  DATE                                 NOT NULL
);

CREATE TABLE IUSERUSERHDR
(
  ID            NUMBER(10)                      NOT NULL,
  NAME          VARCHAR2(32 CHAR)               NOT NULL,
  PASSWORD      VARCHAR2(68 CHAR)               NOT NULL,
  DEPTID        NUMBER(10)                      NOT NULL,
  FLAGS         NUMBER(10)                      NOT NULL,
  STAT          NUMBER(10)                      NOT NULL,
  NUMBADCNTS    NUMBER(10)                      NOT NULL,
  REMARKS       VARCHAR2(254 CHAR),
  CRTRID        NUMBER(10)                      NOT NULL,
  CRTNDATE      DATE                            NOT NULL,
  UPDRID        NUMBER(10),
  UPDDATE       DATE,
  PWDLASTUPDTS  NUMBER                    		NOT NULL,
  PWDMBC        VARCHAR2(1 CHAR)                NOT NULL,
  PWDVPCHECK    VARCHAR2(1 CHAR)                NOT NULL
);

CREATE TABLE IUSERUSERMAP
(
  SRCDBID    NUMBER(10)                         NOT NULL,
  SRCUSERID  NUMBER(10)                         NOT NULL,
  DSTDBID    NUMBER(10)                         NOT NULL,
  DSTUSERID  NUMBER(10)                         NOT NULL
);

CREATE TABLE IUSERUSERPWDS
(
  USERID    NUMBER(10)                          NOT NULL,
  PASSWORD  VARCHAR2(68 CHAR)                   NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IUSERUSERSYS
(
  MAXBADCNTS    NUMBER(10)                      NOT NULL,
  PWDVP         NUMBER(20,4)                    NOT NULL,
  PWDMBC        VARCHAR2(1 CHAR)                NOT NULL,
  PWDMINLEN     NUMBER(5)                       NOT NULL,
  PWDEXPIREDAP  NUMBER		                    NOT NULL,
  NUMPWDLOCK    NUMBER(5)                       NOT NULL,
  FLAGS         NUMBER(10)                      NOT NULL,
  UPDRID        NUMBER(10),
  UPDDATE       DATE
);

CREATE TABLE IUSERUSERTYPE
(
  USERID  NUMBER(10)                            NOT NULL,
  PRODID  NUMBER(10)                            NOT NULL,
  TYPE    NUMBER(10)                            NOT NULL
);

CREATE TABLE IVOLACTDIR
(
  VOLID     NUMBER(10)                          NOT NULL,
  ACTDIR    NUMBER(10)                          NOT NULL,
  NUMFILES  NUMBER(10)                          NOT NULL
);

CREATE TABLE IVOLARCHLIST
(
  ARCHID  NUMBER(10)                            NOT NULL,
  LISTID  NUMBER(10)                            NOT NULL
);

CREATE TABLE IVOLFILEFTS
(
  ID         NUMBER(10)                         NOT NULL,
  EXTID1     NUMBER(10)                         NOT NULL,
  EXTID2     NUMBER(10)                         NOT NULL,
  EXTID3     NUMBER(10)                         NOT NULL,
  EXTID4     NUMBER(10)                         NOT NULL,
  PATH       VARCHAR2(254 CHAR)                 NOT NULL,
  TIMESTAMP  DATE                               NOT NULL
);

CREATE TABLE IVOLFILEHDR
(
  ID         NUMBER(10)                         NOT NULL,
  VOLID      NUMBER(10)                         NOT NULL,
  LOC        VARCHAR2(254 CHAR)                 NOT NULL,
  EXTID1     NUMBER(10)                         NOT NULL,
  EXTID2     NUMBER(10)                         NOT NULL,
  EXTID3     NUMBER(10)                         NOT NULL,
  FLAGS      NUMBER(10)                         NOT NULL,
  STAT       NUMBER(10)                         NOT NULL,
  TIMESTAMP  DATE,
  FILESIZE   NUMBER		                       NOT NULL
);

CREATE TABLE IVOLLISTHDR
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  FLAGS     NUMBER(10)                          NOT NULL,
  STAT      NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLLISTVOL
(
  LISTID     NUMBER(10)                         NOT NULL,
  VOLID      NUMBER(10)                         NOT NULL,
  SORTORDER  NUMBER(10)                         NOT NULL
);

CREATE TABLE IVOLNEXTID
(
  TYPE  NUMBER(10)                              NOT NULL,
  ID    NUMBER(10)                              NOT NULL
);

CREATE TABLE IVOLREPHDR
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                   NOT NULL,
  TYPE      NUMBER(10)                          NOT NULL,
  INFO      CLOB                                NOT NULL,
  STAT      NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLVOLHDR
(
  ID        NUMBER(10)                          NOT NULL,
  NAME      VARCHAR2(32 CHAR)                        NOT NULL,
  REPID     NUMBER(10)                          NOT NULL,
  INFO      CLOB                                NOT NULL,
  ITEMP     NUMBER(10)                          NOT NULL,
  ACTSIZE   VARCHAR2(32 CHAR)                        NOT NULL,
  NUMFILES  NUMBER(10)                          NOT NULL,
  STAT      NUMBER(10)                          NOT NULL,
  REMARKS   VARCHAR2(254 CHAR),
  CRTRID    NUMBER(10)                          NOT NULL,
  CRTNDATE  DATE                                NOT NULL,
  UPDRID    NUMBER(10),
  UPDDATE   DATE
);

CREATE TABLE IVOLVOLTBL
(
  LOCID		VARCHAR2(32 CHAR)                   NOT NULL,
  EXTID1	NUMBER(10)							NOT NULL,
  EXTID2	NUMBER(10)							NOT NULL,
  EXTID3	NUMBER(10)							NOT NULL,
  EXTID4	NUMBER(10)							NOT NULL,
  FILEEXT	VARCHAR2(10 CHAR)     				NOT NULL,
  FILEVAL	BLOB								NOT NULL
);

----------------------------------------
--        Archivador 1                --
----------------------------------------

CREATE TABLE a1clfh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a1clfhx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a1doch (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);

CREATE TABLE a1dochx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);

CREATE TABLE a1fdrh (
    id NUMBER(10) NOT NULL,
    verstat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a1pageh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(64 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a1pagehx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a1sf (
    fdrid NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL,
    fld1 VARCHAR2(20 CHAR),
    fld2 DATE,
    fld3 VARCHAR2(32 CHAR),
    fld4 DATE,
    fld5 NUMBER(10),
    fld6 NUMBER(10),
    fld7 NUMBER(10),
    fld8 NUMBER(10),
    fld9 VARCHAR2(80 CHAR),
    fld10 VARCHAR2(20 CHAR),
    fld11 NUMBER(10),
    fld12 DATE,
    fld13 NUMBER(10),
    fld14 VARCHAR2(31 CHAR),
    fld15 VARCHAR2(30 CHAR),
    fld16 NUMBER(10),
    fld17 VARCHAR2(240 CHAR),
    fld19 VARCHAR2(50 CHAR),
	fld20 DATE
);

CREATE TABLE a1xf (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    fldid NUMBER(10) NOT NULL,
    text CLOB,
    timestamp DATE NOT NULL
);

CREATE TABLE a1xnid (
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    id NUMBER(10) NOT NULL
);


----------------------------------------
--    FIN Archivador 1                --
----------------------------------------


----------------------------------------
--        Archivador 2                --
----------------------------------------
CREATE TABLE a2clfh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);


CREATE TABLE a2clfhx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a2doch (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);

CREATE TABLE a2dochx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);


CREATE TABLE a2fdrh (
    id NUMBER(10) NOT NULL,
    verstat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a2pageh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(64 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a2pagehx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a2sf (
    fdrid NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL,
    fld1 VARCHAR2(20 CHAR),
    fld2 DATE,
    fld3 VARCHAR2(32 CHAR),
    fld4 DATE NOT NULL,
    fld5 NUMBER(10),
    fld6 NUMBER(10),
    fld7 NUMBER(10),
    fld8 NUMBER(10),
    fld9 VARCHAR2(80 CHAR),
    fld10 VARCHAR2(31 CHAR),
    fld11 VARCHAR2(30 CHAR),
    fld12 NUMBER(10),
    fld13 VARCHAR2(240 CHAR),
	fld15 DATE
);

CREATE TABLE a2xf (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    fldid NUMBER(10) NOT NULL,
    text CLOB,
    timestamp DATE NOT NULL
);

CREATE TABLE a2xnid (
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    id NUMBER(10) NOT NULL
);


----------------------------------------
--        FIN Archivador 2            --
----------------------------------------

----------------------------------------
--        Archivador 3                --
----------------------------------------
CREATE TABLE a3clfh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a3clfhx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a3doch (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);


CREATE TABLE a3dochx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);

CREATE TABLE a3fdrh (
    id NUMBER(10) NOT NULL,
    verstat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a3pageh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(64 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a3pagehx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a3sf (
    fdrid NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL,
    fld1 NUMBER(10),
    fld2 VARCHAR2(250 CHAR),
    fld3 NUMBER(10),
    fld4 NUMBER(10),
    fld5 VARCHAR2(250 CHAR),
    fld6 NUMBER(10),
    fld7 VARCHAR2(250 CHAR),
    fld8 VARCHAR2(250 CHAR),
    fld9 VARCHAR2(250 CHAR),
    fld10 NUMBER(10),
    fld11 VARCHAR2(250 CHAR),
    fld12 NUMBER(10),
    fld13 VARCHAR2(250 CHAR),
    fld14 VARCHAR2(250 CHAR),
    fld15 NUMBER(10)
);

CREATE TABLE a3xf (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    fldid NUMBER(10) NOT NULL,
    text CLOB,
    timestamp DATE NOT NULL
);

CREATE TABLE a3xnid (
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    id NUMBER(10) NOT NULL
);

----------------------------------------
--        FIN Archivador 3            --
----------------------------------------

----------------------------------------
--        Archivador 4                --
----------------------------------------

CREATE TABLE a4clfh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a4clfhx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    info NUMBER(10) NOT NULL,
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE
);

CREATE TABLE a4doch (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);

CREATE TABLE a4dochx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    archid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    clfid NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    title VARCHAR2(128 CHAR),
    author VARCHAR2(64 CHAR),
    keywords VARCHAR2(254 CHAR),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL
);


CREATE TABLE a4fdrh (
    id NUMBER(10) NOT NULL,
    verstat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a4pageh (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(64 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);

CREATE TABLE a4pagehx (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    name VARCHAR2(32 CHAR) NOT NULL,
    sortorder NUMBER(10) NOT NULL,
    docid NUMBER(10) NOT NULL,
    fileid NUMBER(10) NOT NULL,
    volid NUMBER(10) NOT NULL,
    loc VARCHAR2(254 CHAR) NOT NULL,
    annid NUMBER(10),
    stat NUMBER(10) NOT NULL,
    refcount NUMBER(10) NOT NULL,
    remarks VARCHAR2(254 CHAR),
    accesstype NUMBER(10) NOT NULL,
    acsid NUMBER(10) NOT NULL,
    crtrid NUMBER(10) NOT NULL,
    crtndate DATE NOT NULL,
    updrid NUMBER(10),
    upddate DATE,
    accrid NUMBER(10) NOT NULL,
    accdate DATE NOT NULL,
    acccount NUMBER(10) NOT NULL
);


CREATE TABLE a4sf (
    fdrid NUMBER(10) NOT NULL,
    timestamp DATE NOT NULL,
    fld1 VARCHAR2(250 CHAR),
    fld2 VARCHAR2(250 CHAR),
    fld3 VARCHAR2(250 CHAR),
    fld4 VARCHAR2(250 CHAR),
    fld5 VARCHAR2(250 CHAR),
    fld7 VARCHAR2(250 CHAR),
    fld8 NUMBER(10),
    fld9 DATE
);

CREATE TABLE a4xnid (
    type NUMBER(10) NOT NULL,
    parentid NUMBER(10) NOT NULL,
    id NUMBER(10) NOT NULL
);

CREATE TABLE a4xf (
    id NUMBER(10) NOT NULL,
    fdrid NUMBER(10) NOT NULL,
    fldid NUMBER(10) NOT NULL,
    text CLOB,
    timestamp DATE NOT NULL
);

----------------------------------------
--        FIN Archivador 4            --
----------------------------------------