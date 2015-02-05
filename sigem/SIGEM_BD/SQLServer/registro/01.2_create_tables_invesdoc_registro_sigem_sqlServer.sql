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


----------------------------
-- TABLAS DE ARCHIVADORES --
----------------------------
CREATE TABLE a1clfh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a1clfhx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a1doch (
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

CREATE TABLE a1dochx (
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


CREATE TABLE a1fdrh (
    id int NOT NULL,
    verstat int NOT NULL,
    refcount int NOT NULL,
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    accrid int NOT NULL,
    accdate datetime NOT NULL,
    acccount int NOT NULL
);

CREATE TABLE a1pageh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(64) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a1pagehx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a1sf (
    fdrid int NOT NULL,
    timestamp datetime NOT NULL,
    fld1 varchar(20),
    fld2 datetime,
    fld3 varchar(32),
    fld4 datetime,
    fld5 int,
    fld6 int,
    fld7 int,
    fld8 int,
    fld9 varchar(80),
    fld10 varchar(20),
    fld11 int,
    fld12 datetime,
    fld13 int,
    fld14 varchar(31),
    fld15 varchar(30),
    fld16 int,
    fld17 varchar(240),
    fld19 varchar(50),
	fld20 datetime
);

CREATE TABLE a1xnid (
    type int NOT NULL,
    parentid int NOT NULL,
    id int NOT NULL
);

CREATE TABLE a2clfh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);


CREATE TABLE a2clfhx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a2doch (
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

CREATE TABLE a2dochx (
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


CREATE TABLE a2fdrh (
    id int NOT NULL,
    verstat int NOT NULL,
    refcount int NOT NULL,
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    accrid int NOT NULL,
    accdate datetime NOT NULL,
    acccount int NOT NULL
);

CREATE TABLE a2pageh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(64) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a2pagehx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a2sf (
    fdrid int NOT NULL,
    timestamp datetime NOT NULL,
    fld1 varchar(20),
    fld2 datetime,
    fld3 varchar(32),
    fld4 datetime NOT NULL,
    fld5 int,
    fld6 int,
    fld7 int,
    fld8 int,
    fld9 varchar(80),
    fld10 varchar(31),
    fld11 varchar(30),
    fld12 int,
    fld13 varchar(240),
	fld15 datetime
);

CREATE TABLE a2xnid (
    type int NOT NULL,
    parentid int NOT NULL,
    id int NOT NULL
);

CREATE TABLE a3clfh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a3clfhx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a3doch (
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


CREATE TABLE a3dochx (
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

CREATE TABLE a3fdrh (
    id int NOT NULL,
    verstat int NOT NULL,
    refcount int NOT NULL,
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    accrid int NOT NULL,
    accdate datetime NOT NULL,
    acccount int NOT NULL
);

CREATE TABLE a3pageh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(64) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a3pagehx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a3sf (
    fdrid int NOT NULL,
    timestamp datetime NOT NULL,
    fld1 int,
    fld2 varchar(250),
    fld3 int,
    fld4 int,
    fld5 varchar(250),
    fld6 int,
    fld7 varchar(250),
    fld8 varchar(250),
    fld9 varchar(250),
    fld10 int,
    fld11 varchar(250),
    fld12 int,
    fld13 varchar(250),
    fld14 varchar(250),
    fld15 int
);

CREATE TABLE a3xnid (
    type int NOT NULL,
    parentid int NOT NULL,
    id int NOT NULL
);


CREATE TABLE a4clfh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a4clfhx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    type int NOT NULL,
    parentid int NOT NULL,
    info int NOT NULL,
    stat int NOT NULL,
    refcount int NOT NULL,
    remarks varchar(254),
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime
);

CREATE TABLE a4doch (
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

CREATE TABLE a4dochx (
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


CREATE TABLE a4fdrh (
    id int NOT NULL,
    verstat int NOT NULL,
    refcount int NOT NULL,
    accesstype int NOT NULL,
    acsid int NOT NULL,
    crtrid int NOT NULL,
    crtndate datetime NOT NULL,
    updrid int,
    upddate datetime,
    accrid int NOT NULL,
    accdate datetime NOT NULL,
    acccount int NOT NULL
);

CREATE TABLE a4pageh (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(64) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);

CREATE TABLE a4pagehx (
    id int NOT NULL,
    fdrid int NOT NULL,
    name varchar(32) NOT NULL,
    sortorder int NOT NULL,
    docid int NOT NULL,
    fileid int NOT NULL,
    volid int NOT NULL,
    loc varchar(254) NOT NULL,
    annid int,
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
    acccount int NOT NULL
);


CREATE TABLE a4sf (
    fdrid int NOT NULL,
    timestamp datetime NOT NULL,
    fld1 varchar(250),
    fld2 varchar(250),
    fld3 varchar(250),
    fld4 varchar(250),
    fld5 varchar(250),
    fld7 varchar(250),
    fld8 int,
    fld9 datetime
);

CREATE TABLE a4xnid (
    type int NOT NULL,
    parentid int NOT NULL,
    id int NOT NULL
);

CREATE TABLE a1xf (
    id int NOT NULL,
    fdrid int NOT NULL,
    fldid int NOT NULL,
    text text,
    timestamp datetime NOT NULL
);

CREATE TABLE a2xf (
    id int NOT NULL,
    fdrid int NOT NULL,
    fldid int NOT NULL,
    text text,
    timestamp datetime NOT NULL
);

CREATE TABLE a3xf (
    id int NOT NULL,
    fdrid int NOT NULL,
    fldid int NOT NULL,
    text text,
    timestamp datetime NOT NULL
);


CREATE TABLE a4xf (
    id int NOT NULL,
    fdrid int NOT NULL,
    fldid int NOT NULL,
    text text,
    timestamp datetime NOT NULL
);


