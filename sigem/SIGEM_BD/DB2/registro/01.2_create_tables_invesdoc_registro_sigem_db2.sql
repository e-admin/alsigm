CREATE TABLE idocarchdet (
    archid integer NOT NULL,
    dettype integer NOT NULL,
    detval clob NOT NULL
);

CREATE TABLE idocbtblctlg (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    def clob NOT NULL,
    flags integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocdbinfo (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    graldbname varchar(32),
    graldbuser varchar(32),
    graldbpassword varchar(34),
    misc clob NOT NULL
);


CREATE TABLE idocfmt (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    archid integer NOT NULL,
    type integer NOT NULL,
    subtype integer NOT NULL,
    data clob NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocmacro (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    language integer NOT NULL,
    text clob NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);


CREATE TABLE a1clfh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a1clfhx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a1doch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE a1dochx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);


CREATE TABLE a1fdrh (
    id integer NOT NULL,
    verstat integer NOT NULL,
    refcount integer NOT NULL,
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a1pageh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(64) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a1pagehx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a1sf (
    fdrid integer NOT NULL,
    timestamp timestamp NOT NULL,
    fld1 varchar(20),
    fld2 timestamp,
    fld3 varchar(32),
    fld4 timestamp,
    fld5 integer,
    fld6 integer,
    fld7 integer,
    fld8 integer,
    fld9 varchar(80),
    fld10 varchar(20),
    fld11 integer,
    fld12 timestamp,
    fld13 integer,
    fld14 varchar(31),
    fld15 varchar(30),
    fld16 integer,
    fld17 varchar(240),
    fld19 varchar(50),
	fld20 timestamp
);

CREATE TABLE a1xnid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE a2clfh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);


CREATE TABLE a2clfhx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a2doch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE a2dochx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);


CREATE TABLE a2fdrh (
    id integer NOT NULL,
    verstat integer NOT NULL,
    refcount integer NOT NULL,
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a2pageh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(64) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a2pagehx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a2sf (
    fdrid integer NOT NULL,
    timestamp timestamp NOT NULL,
    fld1 varchar(20),
    fld2 timestamp,
    fld3 varchar(32),
    fld4 timestamp NOT NULL,
    fld5 integer,
    fld6 integer,
    fld7 integer,
    fld8 integer,
    fld9 varchar(80),
    fld10 varchar(31),
    fld11 varchar(30),
    fld12 integer,
    fld13 varchar(240),
	fld14 timestamp
);

CREATE TABLE a2xnid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE a3clfh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a3clfhx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a3doch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);


CREATE TABLE a3dochx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE a3fdrh (
    id integer NOT NULL,
    verstat integer NOT NULL,
    refcount integer NOT NULL,
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a3pageh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(64) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a3pagehx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a3sf (
    fdrid integer NOT NULL,
    timestamp timestamp NOT NULL,
    fld1 integer,
    fld2 varchar(250),
    fld3 integer,
    fld4 integer,
    fld5 varchar(250),
    fld6 integer,
    fld7 varchar(250),
    fld8 varchar(250),
    fld9 varchar(250),
    fld10 integer,
    fld11 varchar(250),
    fld12 integer,
    fld13 varchar(250),
    fld14 varchar(250),
    fld15 integer
);

CREATE TABLE a3xnid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);


CREATE TABLE a4clfh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a4clfhx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    parentid integer NOT NULL,
    info integer NOT NULL,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE a4doch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE a4dochx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);


CREATE TABLE a4fdrh (
    id integer NOT NULL,
    verstat integer NOT NULL,
    refcount integer NOT NULL,
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a4pageh (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(64) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);

CREATE TABLE a4pagehx (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    name varchar(32) NOT NULL,
    sortorder integer NOT NULL,
    docid integer NOT NULL,
    fileid integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    annid integer,
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL
);


CREATE TABLE a4sf (
    fdrid integer NOT NULL,
    timestamp timestamp NOT NULL,
    fld1 varchar(250),
    fld2 varchar(250),
    fld3 varchar(250),
    fld4 varchar(250),
    fld5 varchar(250),
    fld7 varchar(250),
    fld8 integer,
    fld9 timestamp
);

CREATE TABLE a4xnid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);

CREATE TABLE a1xf (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    fldid integer NOT NULL,
    text clob,
    timestamp timestamp NOT NULL
);

CREATE TABLE a2xf (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    fldid integer NOT NULL,
    text clob,
    timestamp timestamp NOT NULL
);

CREATE TABLE a3xf (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    fldid integer NOT NULL,
    text clob,
    timestamp timestamp NOT NULL
);


CREATE TABLE a4xf (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    fldid integer NOT NULL,
    text clob,
    timestamp timestamp NOT NULL
);

CREATE TABLE idocappevent (
    appid integer NOT NULL,
    eventid integer NOT NULL,
    macroid integer NOT NULL
);

CREATE TABLE idocarchhdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    tblprefix varchar(16) NOT NULL,
    type integer NOT NULL,
    flags integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocautonumctlg (
    id integer NOT NULL,
    name varchar(64) NOT NULL,
    tblname varchar(32) NOT NULL,
    info varchar(254) NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
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
    name varchar(32) NOT NULL,
    dbname varchar(32) NOT NULL,
    dbuser varchar(32) NOT NULL,
    dbpassword varchar(34) NOT NULL,
    remarks varchar(254)
);

CREATE TABLE idocdirhdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    flags integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocfdrlink (
    id integer NOT NULL,
    name varchar(32),
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
    crtndate timestamp NOT NULL
);

CREATE TABLE idocfdrlinkx (
    id integer NOT NULL,
    name varchar(32),
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
    crtndate timestamp NOT NULL
);

CREATE TABLE idocfdrstat (
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    stat integer NOT NULL,
    userid integer NOT NULL,
    timestamp timestamp NOT NULL,
    flags integer NOT NULL
);


CREATE TABLE idocfiauxtblctlg (
    id integer NOT NULL,
    name varchar(16) NOT NULL,
    userid integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE idocglbdoch (
    id integer NOT NULL,
    fdrid integer NOT NULL,
    archid integer NOT NULL,
    name varchar(32) NOT NULL,
    clfid integer NOT NULL,
    type integer NOT NULL,
    title varchar(128),
    author varchar(64),
    keywords varchar(254),
    stat integer NOT NULL,
    refcount integer NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    accrid integer NOT NULL,
    accdate timestamp NOT NULL,
    acccount integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE idocnextid (
    type integer NOT NULL,
    id integer NOT NULL
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


CREATE TABLE idocrptauxtbl (
    fdrid integer NOT NULL
);

CREATE TABLE idocssnextid (
    id integer NOT NULL
);

CREATE TABLE idocxnextid (
    type integer NOT NULL,
    parentid integer NOT NULL,
    id integer NOT NULL
);



CREATE TABLE idocsrvstatedet (
    id integer NOT NULL,
    sessionid integer NOT NULL,
    parentid integer NOT NULL,
    info clob NOT NULL
);

CREATE TABLE idocsrvstatehdr (
    id integer NOT NULL,
    prodid integer NOT NULL,
    appid integer NOT NULL,
    userid integer NOT NULL,
    aliasid integer NOT NULL,
    timestamp timestamp NOT NULL,
    random integer NOT NULL,
    info clob NOT NULL
);

CREATE TABLE idocvtblctlg (
    id integer NOT NULL,
    name varchar(64) NOT NULL,
    btblid integer NOT NULL,
    type integer NOT NULL,
    info clob NOT NULL,
    flags integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocpageanns (
    id integer NOT NULL,
    data clob NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocwmacro (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    text clob NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);
CREATE TABLE idocpict (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    data blob(307200) NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE idocrpt (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    archid integer NOT NULL,
    edittype integer NOT NULL,
    data blob(307200) NOT NULL,
    remarks varchar(254),
    accesstype integer NOT NULL,
    acsid integer NOT NULL,
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);


CREATE TABLE itdsmsnextid (
    cid integer NOT NULL
);

CREATE TABLE itdsmssess (
    cid integer NOT NULL,
    cappid varchar(64) NOT NULL,
    cuserid integer NOT NULL,
    ccrtts timestamp NOT NULL
);


CREATE TABLE iusercurrcnt (
    id integer NOT NULL,
    userid integer NOT NULL,
    prodid integer NOT NULL,
    appid integer NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE iuserdepthdr (
    id integer NOT NULL,
    name varchar(128) NOT NULL,
    parentid integer NOT NULL,
    mgrid integer NOT NULL,
    type integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);


CREATE TABLE iusergenperms (
    dsttype integer NOT NULL,
    dstid integer NOT NULL,
    prodid integer NOT NULL,
    perms integer NOT NULL
);

CREATE TABLE iusergrouphdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    mgrid integer NOT NULL,
    type integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE iusergroupuser (
    groupid integer NOT NULL,
    userid integer NOT NULL
);

CREATE TABLE iuserldapgrphdr (
    id integer NOT NULL,
    ldapguid varchar(36) NOT NULL,
    ldapfullname varchar(254) NOT NULL,
    type integer NOT NULL
);

CREATE TABLE iuserldapuserhdr (
    id integer NOT NULL,
    ldapguid varchar(36) NOT NULL,
    ldapfullname varchar(254) NOT NULL
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
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE iuserobjperm (
    dsttype integer NOT NULL,
    dstid integer NOT NULL,
    objid integer NOT NULL,
    aperm integer NOT NULL
);


CREATE TABLE iuserremuser (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    remarks varchar(254),
    remdate timestamp NOT NULL
);


CREATE TABLE iuseruserhdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    password varchar(68) NOT NULL,
    deptid integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    numbadcnts integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp,
    pwdlastupdts double precision NOT NULL,
    pwdmbc varchar(1) NOT NULL,
    pwdvpcheck varchar(1) NOT NULL
);

CREATE TABLE iuserusermap (
    srcdbid integer NOT NULL,
    srcuserid integer NOT NULL,
    dstdbid integer NOT NULL,
    dstuserid integer NOT NULL
);

CREATE TABLE iuseruserpwds (
    userid integer NOT NULL,
    password varchar(68) NOT NULL,
    updrid integer,
    upddate integer
);

CREATE TABLE iuserusersys (
    maxbadcnts integer NOT NULL,
    pwdvp double precision NOT NULL,
    pwdmbc varchar(1) NOT NULL,
    pwdminlen smallint NOT NULL,
    pwdexpiredap double precision NOT NULL,
    numpwdlock smallint NOT NULL,
    flags integer NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE iuserusertype (
    userid integer NOT NULL,
    prodid integer NOT NULL,
    type integer NOT NULL
);


CREATE TABLE iuserlicences (
    info clob NOT NULL
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
    path varchar(254) NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE ivolfilehdr (
    id integer NOT NULL,
    volid integer NOT NULL,
    loc varchar(254) NOT NULL,
    extid1 integer NOT NULL,
    extid2 integer NOT NULL,
    extid3 integer NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    timestamp timestamp,
    filesize integer NOT NULL
);

CREATE TABLE ivollisthdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    flags integer NOT NULL,
    stat integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
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

CREATE TABLE ivolvoltbl (
   locid varchar(32) NOT NULL,
   extid1 integer NOT NULL,
   extid2 integer NOT NULL,
   extid3 integer NOT NULL,
   extid4 integer NOT NULL,
   fileext varchar(10) NOT NULL,
   fileval blob(1048576) NOT NULL
);

CREATE TABLE ivolrephdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    type integer NOT NULL,
    info clob NOT NULL,
    stat integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);

CREATE TABLE ivolvolhdr (
    id integer NOT NULL,
    name varchar(32) NOT NULL,
    repid integer NOT NULL,
    info clob NOT NULL,
    itemp integer NOT NULL,
    actsize varchar(32) NOT NULL,
    numfiles integer NOT NULL,
    stat integer NOT NULL,
    remarks varchar(254),
    crtrid integer NOT NULL,
    crtndate timestamp NOT NULL,
    updrid integer,
    upddate timestamp
);
