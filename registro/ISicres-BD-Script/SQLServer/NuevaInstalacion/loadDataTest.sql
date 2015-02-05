-- SCRIPT QUE CARGA DATOS BASICOS PARA REALIZAR LAS PRUEBAS DE ISICRES

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

---------------------------------
-- INDEX KEYS ARCHIVADORES     --
---------------------------------
CREATE INDEX A1CLFH1
ON A1CLFH (FDRID, ID)
;
CREATE INDEX A1CLFH2
ON A1CLFH (FDRID)
;
CREATE INDEX A1CLFHX1
ON A1CLFHX (FDRID, ID)
;
CREATE INDEX A1CLFHX2
ON A1CLFHX (FDRID)
;
CREATE INDEX A1DOCH1
ON A1DOCH (ID)
;
CREATE INDEX A1DOCH2
ON A1DOCH (FDRID)
;
CREATE INDEX A1DOCH3
ON A1DOCH (FDRID, CLFID)
;
CREATE INDEX A1DOCHX1
ON A1DOCHX (ID)
;
CREATE INDEX A1DOCHX2
ON A1DOCHX (FDRID)
;
CREATE INDEX A1DOCHX3
ON A1DOCHX (FDRID, CLFID)
;
CREATE UNIQUE INDEX A1FDRH1
ON A1FDRH (ID)
;
CREATE INDEX A1PAGEH1
ON A1PAGEH (FDRID, ID)
;
CREATE INDEX A1PAGEH2
ON A1PAGEH (FDRID)
;
CREATE INDEX A1PAGEH3
ON A1PAGEH (FDRID, DOCID)
;
CREATE INDEX A1PAGEH4
ON A1PAGEH (FILEID)
;
CREATE INDEX A1PAGEHX1
ON A1PAGEHX (FDRID, ID)
;
CREATE INDEX A1PAGEHX2
ON A1PAGEHX (FDRID)
;
CREATE INDEX A1PAGEHX3
ON A1PAGEHX (FDRID, DOCID)
;
CREATE INDEX A1PAGEHX4
ON A1PAGEHX (FILEID)
;
CREATE UNIQUE INDEX A1SF0
ON A1SF (FDRID)
;
CREATE INDEX A1SF1
ON A1SF (FLD1)
;
CREATE INDEX A1SF2
ON A1SF (FLD2)
;
CREATE INDEX A1SF3
ON A1SF (FLD4)
;
CREATE INDEX A1SF4
ON A1SF (FLD6)
;
CREATE INDEX A1SF5
ON A1SF (FLD7)
;
CREATE INDEX A1SF6
ON A1SF (FLD8)
;
CREATE INDEX A1SF7
ON A1SF (FLD9)
;
CREATE INDEX A1SF8
ON A1SF (FLD5)
;
CREATE UNIQUE INDEX A1XF1
ON A1XF (ID)
;
CREATE INDEX A1XF2
ON A1XF (FDRID, FLDID)
;
CREATE INDEX A1XF3
ON A1XF (FDRID)
;
CREATE UNIQUE INDEX A1XNID1
ON A1XNID (TYPE, PARENTID)
;
CREATE INDEX A2CLFH1
ON A2CLFH (FDRID, ID)
;
CREATE INDEX A2CLFH2
ON A2CLFH (FDRID)
;
CREATE INDEX A2CLFHX1
ON A2CLFHX (FDRID, ID)
;
CREATE INDEX A2CLFHX2
ON A2CLFHX (FDRID)
;
CREATE INDEX A2DOCH1
ON A2DOCH (ID)
;
CREATE INDEX A2DOCH2
ON A2DOCH (FDRID)
;
CREATE INDEX A2DOCH3
ON A2DOCH (FDRID, CLFID)
;
CREATE INDEX A2DOCHX1
ON A2DOCHX (ID)
;
CREATE INDEX A2DOCHX2
ON A2DOCHX (FDRID)
;
CREATE INDEX A2DOCHX3
ON A2DOCHX (FDRID, CLFID)
;
CREATE UNIQUE INDEX A2FDRH1
ON A2FDRH (ID)
;
CREATE INDEX A2PAGEH1
ON A2PAGEH (FDRID, ID)
;
CREATE INDEX A2PAGEH2
ON A2PAGEH (FDRID)
;
CREATE INDEX A2PAGEH3
ON A2PAGEH (FDRID, DOCID)
;
CREATE INDEX A2PAGEH4
ON A2PAGEH (FILEID)
;
CREATE INDEX A2PAGEHX1
ON A2PAGEHX (FDRID, ID)
;
CREATE INDEX A2PAGEHX2
ON A2PAGEHX (FDRID)
;
CREATE INDEX A2PAGEHX3
ON A2PAGEHX (FDRID, DOCID)
;
CREATE INDEX A2PAGEHX4
ON A2PAGEHX (FILEID)
;
CREATE UNIQUE INDEX A2SF0
ON A2SF (FDRID)
;
CREATE INDEX A2SF1
ON A2SF (FLD1)
;
CREATE INDEX A2SF2
ON A2SF (FLD2)
;
CREATE INDEX A2SF3
ON A2SF (FLD4)
;
CREATE INDEX A2SF4
ON A2SF (FLD6)
;
CREATE INDEX A2SF5
ON A2SF (FLD7)
;
CREATE INDEX A2SF6
ON A2SF (FLD8)
;
CREATE INDEX A2SF7
ON A2SF (FLD9)
;
CREATE INDEX A2SF8
ON A2SF (FLD5)
;
CREATE UNIQUE INDEX A2XF1
ON A2XF (ID)
;
CREATE INDEX A2XF2
ON A2XF (FDRID, FLDID)
;
CREATE INDEX A2XF3
ON A2XF (FDRID)
;
CREATE UNIQUE INDEX A2XNID1
ON A2XNID (TYPE, PARENTID)
;
---------------------------------
-- END INDEX KEYS ARCHIVADORES --
---------------------------------

-- TABLA idocdirhdr
INSERT INTO idocdirhdr (id, name, type, flags, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (1, 'Invesicres', 0, 0, NULL, 1, 5, 0, getdate(), NULL, NULL);

-- TABLA idocarchhdr
INSERT INTO idocarchhdr (id, name, tblprefix, type, flags, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (1, 'Libro de Entrada', 'A1', 1, 0, NULL, 0, 1, 2, getdate(), 0, getdate());
INSERT INTO idocarchhdr (id, name, tblprefix, type, flags, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (2, 'Libro de Salida', 'A2', 2, 0, NULL, 0, 2, 2, getdate(), 3, getdate());


-- TABLA idocarchdet
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (1, 1, '"01.00"|20|1,"Número de registro",1,20,1,"Fld1",0,0,""|2,"Fecha de registro",9,0,1,"Fld2",0,0,""|3,"Usuario",1,32,1,"Fld3",0,0,""|4,"Fecha de trabajo",7,0,1,"Fld4",0,0,""|5,"Oficina de registro",4,0,1,"Fld5",0,0,""|6,"Estado",4,0,1,"Fld6",0,0,""|7,"Origen",4,0,1,"Fld7",0,0,""|8,"Destino",4,0,1,"Fld8",0,0,""|9,"Remitentes",1,80,1,"Fld9",0,0,""|10,"Nº. registro original",1,20,1,"Fld10",0,0,""|11,"Tipo de registro original",4,0,1,"Fld11",0,0,""|12,"Fecha de registro original",7,0,1,"Fld12",0,0,""|13,"Registro original",4,0,1,"Fld13",0,0,""|14,"Tipo de transporte",1,31,1,"Fld14",0,0,""|15,"Número de transporte",1,30,1,"Fld15",0,0,""|16,"Tipo de asunto",4,0,1,"Fld16",0,0,""|17,"Resumen",1,240,1,"Fld17",0,0,""|18,"Comentario",2,65535,1,"Fld18",0,0,""|19,"Referencia de Expediente",1,50,1,"Fld19",0,0,""|20,"Fecha del documento",7,0,1,"Fld20",0,0,""|8|1,"EREG1",1,1,1|2,"EREG2",0,1,2|3,"EREG3",0,1,4|4,"EREG4",0,1,6|5,"EREG5",0,1,7|6,"EREG6",0,1,8|7,"EREG7",0,1,9|8,"EREG8",0,1,5');
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (1, 2, '"01.01"|""|1,3');
insert into idocarchdet (archid, dettype, detval) values(1,4,'"01.00"|"","",0,0');
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (1, 5, '"01.00"|2147483646|0|11|2|2,9,"-",":"," ",1,1|0,2147483646|4|2,7,"-","","",1,0|0,2147483646|5|1,4,".","",0|0,2147483646|6|1,4,".","",0|0,2147483646|7|1,4,".","",0|0,2147483646|8|1,4,".","",0|0,2147483646|11|1,4,".","",0|0,2147483646|12|2,7,"-","","",1,0|0,2147483646|13|1,4,".","",0|0,2147483646|16|1,4,".","",0|0,2147483646|20|2,7,"-","","",1,0|0,2147483646');
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (2, 1, '"01.00"|15|1,"Número de registro",1,20,1,"Fld1",0,0,""|2,"Fecha de registro",9,0,1,"Fld2",0,0,""|3,"Usuario",1,32,1,"Fld3",0,0,""|4,"Fecha de trabajo",7,0,0,"Fld4",0,0,""|5,"Oficina de registro",4,0,1,"Fld5",0,0,""|6,"Estado",4,0,1,"Fld6",0,0,""|7,"Origen",4,0,1,"Fld7",0,0,""|8,"Destino",4,0,1,"Fld8",0,0,""|9,"Destinatarios",1,80,1,"Fld9",0,0,""|10,"Tipo de transporte",1,31,1,"Fld10",0,0,""|11,"Número de transporte",1,30,1,"Fld11",0,0,""|12,"Tipo de asunto",4,0,1,"Fld12",0,0,""|13,"Resumen",1,240,1,"Fld13",0,0,""|14,"Comentario",2,65535,1,"Fld14",0,0,""|15,"Fecha del documento",7,0,1,"Fld15",0,0,""|8|1,"SREG1",1,1,1|2,"SREG2",0,1,2|3,"SREG3",0,1,4|4,"SREG4",0,1,6|5,"SREG5",0,1,7|6,"SREG6",0,1,8|7,"SREG7",0,1,9|8,"SREG8",0,1,5');
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (2, 2, '"01.01"|""|1,3');
insert into idocarchdet (archid, dettype, detval) values(2,4,'"01.00"|"","",0,0');
INSERT INTO idocarchdet (archid, dettype, detval) VALUES (2, 5, '"01.00"|2147483646|1|4|8|2|2,9,"-",":"," ",1,1|0,2147483646|4|2,7,"-","","",1,0|0,2147483646|5|1,4,".","",0|0,2147483646|6|1,4,".","",0|0,2147483646|7|1,4,".","",0|0,2147483646|8|1,4,".","",0|0,2147483646|12|1,4,".","",0|0,2147483646|14|2,7,"-","","",1,0|0,2147483646');

-- TABLA idocfmt
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (1, 'Predeterminado', 1, 1, 3, '"01.03"|"Carpeta - Buscar",0,0,429,284,"MS Sans Serif",8,38|1001,8,11,94,19,1342177280,130,"Número de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1001",""|1002,98,8,198,112,1344339971,133,"=","",0,0,0,2,1003,2147483646,511,"IDOC_CBOX1002",""|1003,220,8,320,20,1350631552,129,"","",0,0,0,3,1002,1,0,"IDOC_EDIT1003",""|1004,8,27,94,35,1342177280,130,"Fecha de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1004",""|1005,98,24,198,128,1344339971,133,"=","",0,0,0,2,1006,2147483646,383,"IDOC_CBOX1005",""|1006,220,24,320,36,1350631552,129,"","",0,0,0,3,1005,2,0,"IDOC_EDIT1006",""|1007,8,43,94,51,1342177280,130,"Usuario:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1007",""|1008,98,40,198,144,1344339971,133,"=","",0,0,0,2,1009,2147483646,447,"IDOC_CBOX1008",""|1009,220,40,320,52,1350631552,129,"","",0,0,0,3,1008,3,0,"IDOC_EDIT1009",""|1010,8,59,94,67,1342177280,130,"Fecha de trabajo:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1010",""|1011,98,56,198,160,1344339971,133,"=","",0,0,0,2,1012,2147483646,383,"IDOC_CBOX1011",""|1012,220,56,320,68,1350631552,129,"","",0,0,0,3,1011,4,0,"IDOC_EDIT1012",""|1013,8,75,94,83,1342177280,130,"Oficina de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1013",""|1014,98,72,198,176,1344339971,133,"=","",0,0,0,2,1015,2147483646,63,"IDOC_CBOX1014",""|1015,220,72,320,84,1350631552,129,"","",0,0,0,3,1014,5,0,"IDOC_EDIT1015",""|1016,8,91,94,99,1342177280,130,"Estado:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1016",""|1017,98,88,198,192,1344339971,133,"=","",0,0,0,2,1018,2147483646,63,"IDOC_CBOX1017",""|1018,220,88,320,100,1350631552,129,"","",0,0,0,3,1017,6,0,"IDOC_EDIT1018",""|1019,8,107,94,115,1342177280,130,"Origen:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1019",""|1020,98,104,198,208,1344339971,133,"=","",0,0,0,2,1021,2147483646,63,"IDOC_CBOX1020",""|1021,220,104,320,116,1350631552,129,"","",0,0,0,3,1020,7,0,"IDOC_EDIT1021",""|1022,8,123,94,131,1342177280,130,"Destino:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1022",""|1023,98,120,198,224,1344339971,133,"=","",0,0,0,2,1024,2147483646,63,"IDOC_CBOX1023",""|1024,220,120,320,132,1350631552,129,"","",0,0,0,3,1023,8,0,"IDOC_EDIT1024",""|1025,8,139,94,147,1342177280,130,"Remitentes:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1025",""|1026,98,136,198,240,1344339971,133,"=","",0,0,0,2,1027,2147483646,447,"IDOC_CBOX1026",""|1027,220,136,320,148,1350631552,129,"","",0,0,0,3,1026,9,0,"IDOC_EDIT1027",""|1028,8,158,94,166,1342177280,130,"Tipo de Asunto:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1028",""|1029,98,153,198,257,1344339971,133,"=","",0,0,0,2,1030,2147483646,63,"IDOC_CBOX1029",""|1030,220,153,320,166,1350631552,129,"","",0,0,0,3,1029,16,0,"IDOC_EDIT1030",""|1,334,8,384,22,1342242817,128,"Aceptar","",0,0,0,0,0,2147483646,0,"IDOC_BUTTON_OK",""|2,334,24,384,38,1342242816,128,"Cancelar","",0,0,0,0,0,2147483646,0,"IDOC_BUTTON_CANCEL",""|1031,8,174,94,182,1342177280,130,"Resumen:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1031",""|1032,98,172,198,276,1344339971,133,"=","",0,0,0,2,1033,2147483646,511,"IDOC_CBOX1032",""|1033,220,172,320,185,1350631552,129,"","",0,0,0,3,1032,17,0,"IDOC_EDIT1033",""|1034,8,192,94,200,1342177280,130,"Ref. Expediente:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1034",""|1035,98,190,198,294,1344339971,133,"=","",0,0,0,2,1036,2147483646,191,"IDOC_CBOX1035",""|1036,220,190,320,203,1350631552,129,"","",0,0,0,3,1035,19,0,"IDOC_EDIT1036",""|0|0|1,"(@FLD(1) @OPR(1002) @VAL(1003)) AND (@FLD(2) @OPR(1005) @VAL(1006)) AND (@FLD(3) @OPR(1008) @VAL(1009)) AND (@FLD(4) @OPR(1011) @VAL(1012)) AND (@FLD(5) @OPR(1014) @VAL(1015)) AND (@FLD(6) @OPR(1017) @VAL(1018)) AND (@FLD(7) @OPR(1020) @VAL(1021)) AND (@FLD(8) @OPR(1023) @VAL(1024)) AND (@FLD(9) @OPR(1026) @VAL(1027)) AND (@FLD(16) @OPR(1029) @VAL(1030)) AND (@FLD(17) @OPR(1032) @VAL(1033)) AND (@FLD(19) @OPR(1035) @VAL(1036))","","",2147483646|""|0,2147483646|2147483646', NULL, 1, 2147483646, 0, getdate(), 0, getdate());
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (2, 'Predeterminado', 1, 2, 3, '"01.04"|7,31,600,300,9,"MS Sans Serif",8,19|112,"Número de registro",0,1,0|105,"Fecha de registro",0,2,0|57,"Usuario",0,3,0|103,"Fecha de trabajo",0,4,0|108,"Oficina de registro",0,5,0|54,"Estado",0,6,0|52,"Origen",0,7,0|57,"Destino",0,8,0|74,"Remitentes",0,9,0|111,"Nº. registro original",0,10,0|133,"Tipo de registro original",0,11,0|142,"Fecha de registro original",0,12,0|97,"Registro original",0,13,0|109,"Tipo de transporte",0,14,0|125,"Número de transporte",0,15,0|94,"Tipo de asunto",0,16,0|66,"Resumen",0,17,0|74,"Comentario",0,18,0|146,"Referencia de Expediente",0,19,0|1,0|0|0', NULL, 1, 2147483646, 0, getdate(), 0, getdate());
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (3, 'Predeterminado', 1, 3, 3, '"01.05"|"Carpeta",32,1,556,377,3|"Registro",6,32,513,363,"MS Sans Serif",8,40|1001,8,9,88,17,1342177280,130,"Número de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1001",""|1002,88,4,194,17,1350631552,129,"","",0,0,0,3,1,"IDOC_EDIT1002",""|1005,204,9,238,19,1342177280,130,"Usuario:","",0,0,0,1,2147483646,"IDOC_STATIC1005",""|1006,238,4,310,17,1350631552,129,"","",0,0,0,3,3,"IDOC_EDIT1006",""|1073,320,9,350,17,1342177280,130,"Estado:","",0,0,0,1,2147483646,"IDOC_STATIC1073",""|1074,350,4,412,17,1350631552,129,"","",0,0,0,3,6,"IDOC_EDIT1074",""|1003,8,25,88,35,1342177280,130,"Fecha de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1003",""|1004,88,22,194,35,1350631552,129,"","",0,0,0,3,2,"IDOC_EDIT1004",""|1007,278,25,350,33,1342177280,130,"Fecha de trabajo:","MS Sans Serif",8,1,0,1,2147483646,"IDOC_STATIC1007",""|1008,350,22,412,35,1350631552,129,"","",0,0,0,3,4,"IDOC_EDIT1008",""|1075,8,41,82,51,1342177280,130,"Oficina de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1075",""|1076,88,38,152,51,1350631552,129,"","",0,0,0,3,5,"IDOC_EDIT1076",""|1077,166,38,412,51,1350633604,129,"","",0,0,0,10,5,"IDOC_EDIT1077",""|1013,8,57,78,67,1342177280,130,"Origen:","",0,0,0,1,2147483646,"IDOC_STATIC1013",""|1014,88,54,172,67,1350631552,129,"","",0,0,0,3,7,"IDOC_EDIT1014",""|1039,182,54,412,73,1350633604,129,"","",0,0,0,10,7,"IDOC_EDIT1039",""|1015,8,78,78,88,1342177280,130,"Destino:","",0,0,0,1,2147483646,"IDOC_STATIC1015",""|1016,88,75,172,88,1350631552,129,"","",0,0,0,3,8,"IDOC_EDIT1016",""|1040,182,75,412,94,1350633604,129,"","",0,0,0,10,8,"IDOC_EDIT1040",""|1017,8,99,90,108,1342177280,130,"Remitentes:","",0,0,0,1,2147483646,"IDOC_STATIC1017",""|1018,88,96,412,160,1350631552,129,"","",0,0,0,3,9,"IDOC_EDIT1018",""|1052,8,171,72,179,1342177280,130,"Tipo de asunto:","",0,0,0,1,2147483646,"IDOC_STATIC1052",""|1053,88,168,152,182,1350631552,129,"","",0,0,0,3,16,"IDOC_EDIT1053",""|1054,160,168,412,188,1350633604,129,"","",0,0,0,10,16,"IDOC_EDIT1054",""|1047,8,200,66,209,1342177280,130,"Resumen:","",0,0,0,1,2147483646,"IDOC_STATIC1047",""|1048,88,192,412,225,1350631620,129,"","",0,0,0,3,17,"IDOC_EDIT1048",""|1087,8,235,78,243,1342177280,130,"Ref. Expediente:","",0,0,0,1,2147483646,"IDOC_STATIC1087",""|1088,88,230,212,244,1350631552,129,"","",0,0,0,3,19,"IDOC_EDIT1088",""|1055,8,260,88,268,1342177280,130,"Tipo de Reg. original:","",0,0,0,1,2147483646,"IDOC_STATIC1055",""|1056,88,256,166,270,1350631552,129,"","",0,0,0,3,11,"IDOC_EDIT1056",""|1019,224,248,300,257,1342177280,130,"Nº. registro original:","",0,0,0,1,2147483646,"IDOC_STATIC1019",""|1020,322,244,412,259,1350631552,129,"","",0,0,0,3,10,"IDOC_EDIT1020",""|1057,224,264,318,275,1342177280,130,"Fecha de Reg. original:","",0,0,0,1,2147483646,"IDOC_STATIC1057",""|1058,322,262,412,275,1350631552,129,"","",0,0,0,3,12,"IDOC_EDIT1058",""|1062,8,289,88,299,1342177280,130,"Tipo de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1062",""|1063,88,284,200,299,1350631552,129,"","",0,0,0,3,14,"IDOC_EDIT1063",""|1064,224,288,330,299,1342177280,130,"Número de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1064",""|1065,322,284,412,299,1350631552,129,"","",0,0,0,3,15,"IDOC_EDIT1065",""|1089,8,313,82,323,1342308352,130,"Fecha del documento:","",0,0,0,1,2147483646,"IDOC_STATIC1089",""|1090,88,309,200,324,1350631552,129,"","",0,0,0,3,20,"IDOC_EDIT1090",""|1,2147483646,""|"Comentarios",6,32,513,363,"MS Sans Serif",8,2|1035,16,46,84,57,1342177280,130,"Comentario:","",0,0,0,1,2147483646,"IDOC_STATIC1035",""|1036,92,41,408,243,1352728644,129,"","",0,0,0,3,18,"IDOC_EDIT1036",""|1,2147483646,""|"Documentos",6,32,513,363,"MS Sans Serif",8,0|2,2147483646,"2,2|0|2,0,50,0,0,0,1,1,50,0,0,50,1|0.000000,0,0"|0|0|"0"|1,0|0,0|0|2147483646|2147483646|0', NULL, 1, 2147483646, 0, getdate(), 0, getdate());
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (4, 'Predeterminado', 2, 1, 3, '"01.03"|"Carpeta - Buscar",80,0,593,270,"MS Sans Serif",8,38|1001,8,11,92,19,1342177280,130,"Número de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1001",""|1002,92,8,178,112,1344339971,133,"=","",0,0,0,2,1003,2147483646,511,"IDOC_CBOX1002",""|1003,182,8,282,20,1350631552,129,"","",0,0,0,3,1002,1,0,"IDOC_EDIT1003",""|1004,8,27,92,35,1342177280,130,"Fecha de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1004",""|1005,92,24,178,128,1344339971,133,"=","",0,0,0,2,1006,2147483646,383,"IDOC_CBOX1005",""|1006,182,24,282,36,1350631552,129,"","",0,0,0,3,1005,2,0,"IDOC_EDIT1006",""|1007,8,43,80,51,1342177280,130,"Usuario:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1007",""|1008,92,40,178,144,1344339971,133,"=","",0,0,0,2,1009,2147483646,447,"IDOC_CBOX1008",""|1009,182,40,282,52,1350631552,129,"","",0,0,0,3,1008,3,0,"IDOC_EDIT1009",""|1010,8,59,98,67,1342177280,130,"Fecha de trabajo:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1010",""|1011,92,56,178,160,1344339971,133,"=","",0,0,0,2,1012,2147483646,383,"IDOC_CBOX1011",""|1012,182,56,282,68,1350631552,129,"","",0,0,0,3,1011,4,0,"IDOC_EDIT1012",""|1013,8,75,96,83,1342177280,130,"Oficina de registro:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1013",""|1014,92,72,178,176,1344339971,133,"=","",0,0,0,2,1015,2147483646,63,"IDOC_CBOX1014",""|1015,182,72,282,84,1350631552,129,"","",0,0,0,3,1014,5,0,"IDOC_EDIT1015",""|1016,8,91,80,99,1342177280,130,"Estado:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1016",""|1017,92,88,178,192,1344339971,133,"=","",0,0,0,2,1018,2147483646,63,"IDOC_CBOX1017",""|1018,182,88,282,100,1350631552,129,"","",0,0,0,3,1017,6,0,"IDOC_EDIT1018",""|1019,8,107,80,115,1342177280,130,"Origen:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1019",""|1020,92,104,178,208,1344339971,133,"=","",0,0,0,2,1021,2147483646,63,"IDOC_CBOX1020",""|1021,182,104,282,116,1350631552,129,"","",0,0,0,3,1020,7,0,"IDOC_EDIT1021",""|1022,8,123,80,131,1342177280,130,"Destino:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1022",""|1023,92,120,178,224,1344339971,133,"=","",0,0,0,2,1024,2147483646,63,"IDOC_CBOX1023",""|1024,182,120,282,132,1350631552,129,"","",0,0,0,3,1023,8,0,"IDOC_EDIT1024",""|1025,8,139,94,147,1342177280,130,"Destinatarios:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1025",""|1026,92,136,178,240,1344339971,133,"=","",0,0,0,2,1027,2147483646,447,"IDOC_CBOX1026",""|1027,182,136,282,148,1350631552,129,"","",0,0,0,3,1026,9,0,"IDOC_EDIT1027",""|1028,8,155,94,163,1342177280,130,"Tipo de transporte:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1028",""|1029,92,152,178,256,1344339971,133,"=","",0,0,0,2,1030,2147483646,511,"IDOC_CBOX1029",""|1030,182,152,282,164,1350631552,129,"","",0,0,0,3,1029,10,0,"IDOC_EDIT1030",""|1,300,8,350,22,1342242817,128,"Aceptar","",0,0,0,0,0,2147483646,0,"IDOC_BUTTON_OK",""|2,300,25,350,40,1342242816,128,"Cancelar","",0,0,0,0,0,2147483646,0,"IDOC_BUTTON_CANCEL",""|1031,8,172,94,180,1342177280,130,"Resumen:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1031",""|1032,92,168,178,272,1344339971,133,"=","",0,0,0,2,1033,2147483646,511,"IDOC_CBOX1032",""|1033,182,168,282,182,1350631552,129,"","",0,0,0,3,1032,13,0,"IDOC_EDIT1033",""|1034,8,188,94,196,1342177280,130,"Tipo de Asunto:","",0,0,0,1,0,2147483646,0,"IDOC_STATIC1034",""|1035,92,185,178,289,1344339971,133,"=","",0,0,0,2,1036,2147483646,63,"IDOC_CBOX1035",""|1036,182,185,282,198,1350631552,129,"","",0,0,0,3,1035,12,0,"IDOC_EDIT1036",""|0|0|1,"(@FLD(1) @OPR(1002) @VAL(1003)) AND (@FLD(2) @OPR(1005) @VAL(1006)) AND (@FLD(3) @OPR(1008) @VAL(1009)) AND (@FLD(4) @OPR(1011) @VAL(1012)) AND (@FLD(5) @OPR(1014) @VAL(1015)) AND (@FLD(6) @OPR(1017) @VAL(1018)) AND (@FLD(7) @OPR(1020) @VAL(1021)) AND (@FLD(8) @OPR(1023) @VAL(1024)) AND (@FLD(9) @OPR(1026) @VAL(1027)) AND (@FLD(10) @OPR(1029) @VAL(1030)) AND (@FLD(13) @OPR(1032) @VAL(1033)) AND (@FLD(12) @OPR(1035) @VAL(1036))","","",2147483646|"0"|0,2147483646|2147483646', NULL, 1, 2147483646, 0, getdate(), 0, getdate());
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (5, 'Predeterminado', 2, 2, 3, '"01.04"|7,31,819,300,9,"MS Sans Serif",8,11|103,"Número de registro",0,1,1|96,"Fecha de registro",0,2,1|114,"Fecha de trabajo",0,4,1|57,"Usuario",0,3,1|97,"Oficina de registro",0,5,1|88,"Origen",0,7,1|97,"Destino",0,8,1|82,"Destinatarios",0,9,1|54,"Estado",0,6,1|69,"Resumen",0,13,1|94,"Tipo de asunto",0,12,1|1,0|2147483646|0', NULL, 1, 2147483646, 0, getdate(), 0, getdate());
INSERT INTO idocfmt (id, name, archid, type, subtype, data, remarks, accesstype, acsid, crtrid, crtndate, updrid, upddate) VALUES (6, 'Predeterminado', 2, 3, 3, '"01.05"|"Carpeta",44,8,618,392,2|"Registro",6,32,563,371,"MS Sans Serif",8,34|1001,8,11,86,19,1342177280,130,"Número de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1001",""|1002,92,6,192,19,1350631552,129,"","",0,0,0,3,1,"IDOC_EDIT1002",""|1005,202,9,242,19,1342177280,130,"Usuario:","",0,0,0,1,2147483646,"IDOC_STATIC1005",""|1006,244,6,316,19,1350631552,129,"","",0,0,0,3,3,"IDOC_EDIT1006",""|1011,322,9,352,17,1342177280,130,"Estado:","",0,0,0,1,2147483646,"IDOC_STATIC1011",""|1012,354,6,412,19,1350631552,129,"","",0,0,0,3,6,"IDOC_EDIT1012",""|1003,8,27,78,35,1342177280,130,"Fecha de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1003",""|1004,92,24,192,36,1350631552,129,"","",0,0,0,3,2,"IDOC_EDIT1004",""|1007,246,27,326,35,1342177280,130,"Fecha de trabajo:","MS Sans Serif",8,1,0,1,2147483646,"IDOC_STATIC1007",""|1008,324,24,412,36,1350631552,129,"","",0,0,0,3,4,"IDOC_EDIT1008",""|1013,8,43,78,51,1342177280,130,"Origen:","",0,0,0,1,2147483646,"IDOC_STATIC1013",""|1014,92,41,192,54,1350631552,129,"","",0,0,0,3,7,"IDOC_EDIT1014",""|1042,202,41,412,62,1350633604,129,"","",0,0,0,10,7,"IDOC_EDIT1042",""|1015,8,70,78,78,1342177280,130,"Destino:","",0,0,0,1,2147483646,"IDOC_STATIC1015",""|1016,92,67,192,80,1350631552,129,"","",0,0,0,3,8,"IDOC_EDIT1016",""|1043,202,67,412,88,1350633604,129,"","",0,0,0,10,8,"IDOC_EDIT1043",""|1017,8,94,86,102,1342177280,130,"Destinatarios:","",0,0,0,1,2147483646,"IDOC_STATIC1017",""|1018,92,92,412,144,1350631552,129,"","",0,0,0,3,9,"IDOC_EDIT1018",""|1036,8,156,80,164,1342177280,130,"Tipo de asunto:","",0,0,0,1,2147483646,"IDOC_STATIC1036",""|1037,92,153,192,166,1350631552,129,"","",0,0,0,3,12,"IDOC_EDIT1037",""|1046,202,153,412,174,1350633604,129,"","",0,0,0,10,12,"IDOC_EDIT1046",""|1038,8,185,90,193,1342177280,130,"Resumen:","",0,0,0,1,2147483646,"IDOC_STATIC1038",""|1039,92,177,412,217,1350631620,129,"","",0,0,0,3,13,"IDOC_EDIT1039",""|1019,8,225,96,233,1342177280,130,"Tipo de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1019",""|1020,92,224,226,238,1350631552,129,"","",0,0,0,3,10,"IDOC_EDIT1020",""|1034,236,225,334,233,1342177280,130,"Número de transporte:","",0,0,0,1,2147483646,"IDOC_STATIC1034",""|1035,330,224,412,238,1350631552,129,"","",0,0,0,3,11,"IDOC_EDIT1035",""|1040,8,256,80,264,1342177280,130,"Comentario:","",0,0,0,1,2147483646,"IDOC_STATIC1040",""|1041,92,241,412,275,1352728644,129,"","",0,0,0,3,14,"IDOC_EDIT1041",""|1009,8,284,96,292,1342177280,130,"Oficina de registro:","",0,0,0,1,2147483646,"IDOC_STATIC1009",""|1010,8,296,98,307,1350631552,129,"","",0,0,0,3,5,"IDOC_EDIT1010",""|1033,104,296,324,307,1350633604,129,"","",0,0,0,10,5,"IDOC_EDIT1033",""|1047,9,318,95,329,1342308352,130,"Fecha del documento:","",0,0,0,1,2147483646,"IDOC_STATIC1047",""|1048,93,313,214,329,1350631552,129,"","",0,0,0,3,15,"IDOC_EDIT1048",""|1,2147483646,""|"Documentos",6,32,563,371,"MS Sans Serif",8,0|2,2147483646,"2,2|0|2,0,50,0,0,0,1,1,50,0,0,50,1|0.000000,0,0"|0|0|"0"|1,0|0,0|0|2147483646|2147483646|0', NULL, 1, 2147483646, 0, getdate(), 0, getdate());

-- TABLA idocpreffmt
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (1, 1, 0, 1);
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (1, 2, 0, 2);
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (1, 3, 0, 3);
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (2, 1, 0, 4);
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (2, 2, 0, 5);
INSERT INTO idocpreffmt (archid, fmttype, userid, fmtid) VALUES (2, 3, 0, 6);



-- TABLA idocdatnode
INSERT INTO idocdatnode (id, type, parentid) VALUES (1, 1, 0);
INSERT INTO idocdatnode (id, type, parentid) VALUES (1, 2, 1);
INSERT INTO idocdatnode (id, type, parentid) VALUES (2, 2, 1);

-- TABLA idocnextid
UPDATE idocnextid SET id = 2 where type = 1;
UPDATE idocnextid SET id = 3 where type = 2;
UPDATE idocnextid SET id = 7 where type = 10;

-- TABLA idocxnextid
INSERT INTO idocxnextid (type, parentid, id) VALUES (3, 1, 1);
INSERT INTO idocxnextid (type, parentid, id) VALUES (5, 1, 1);
INSERT INTO idocxnextid (type, parentid, id) VALUES (7, 1, 1);
INSERT INTO idocxnextid (type, parentid, id) VALUES (3, 2, 1);
INSERT INTO idocxnextid (type, parentid, id) VALUES (5, 2, 1);
INSERT INTO idocxnextid (type, parentid, id) VALUES (7, 2, 1);

-- TABLA iuserobjhdr
INSERT INTO iuserobjhdr (id, prodid, type, extid1, extid2, extid3, ownertype, ownerid, crtrid, crtndate, updrid, upddate) VALUES (2, 3, 5, 1, 1, 0, 1, 0, 0, getdate(), NULL, NULL);
INSERT INTO iuserobjhdr (id, prodid, type, extid1, extid2, extid3, ownertype, ownerid, crtrid, crtndate, updrid, upddate) VALUES (3, 3, 5, 2, 1, 0, 1, 0, 0, getdate(), NULL, NULL);

-- TABLA scr_regstate
INSERT INTO scr_regstate (id, idarchreg, state, closedate, closeuser, numeration_type, image_auth) VALUES (1, 1, 0, NULL, '', 2, 0);
INSERT INTO scr_regstate (id, idarchreg, state, closedate, closeuser, numeration_type, image_auth) VALUES (2, 2, 0, NULL, '', 2, 0);



-- TABLA scr_orgs
insert into scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values (4887,'001',null,'SRC','SERVICIO DE RELACIONES CON EL CIUDADANO',getdate(),null,1,1,'');
insert into scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values (4888,'002',null,'SERSEC','SERVICIO DE SECRETARIA',getdate(),null,1,1,'');
insert into scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values (4889,'003',null,'STL','SERVICIO DE TRAMITACION DE LICENCIAS',getdate(),null,1,1,'');

-- TABLA iuserdepthdr
insert into iuserdepthdr (id, name, parentid, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (3,'OFICINA DE REGISTRO1',0,0,1,'',0,getdate(),0,getdate());
insert into iuserdepthdr (id, name, parentid, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (5,'OFICINA DE REGISTRO TELEMATICO',0,3,1,'',3,getdate(),3,getdate());
insert into iuserdepthdr (id, name, parentid, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (4,'TRAMITACION',7,0,0,'<ORGANO><CODIGO>2</CODIGO><NIVEL>2</NIVEL><INSTITUCION>N</INSTITUCION></ORGANO>',0,getdate(),0,getdate());
insert into iuserdepthdr (id, name, parentid, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (6,'ARCHIVO',7,0,0,'<ORGANO><CODIGO>3</CODIGO><NIVEL>2</NIVEL><INSTITUCION>N</INSTITUCION></ORGANO>',0,getdate(),0,getdate());
insert into iuserdepthdr (id, name, parentid, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (7,'OFICINA DE GEST.DOC',0,0,0,'<ORGANO><CODIGO>1</CODIGO><NIVEL>1</NIVEL><INSTITUCION>S</INSTITUCION></ORGANO>',0,getdate(),0,getdate());

-- TABLA iusergenperms
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,3,3,1);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,3,5,31);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,3,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,3,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,3,5,31);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,3,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,1,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,1,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,1,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,2,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,2,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,2,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,3,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,3,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (3,3,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,4,3,1);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,4,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,4,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,5,3,1);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,5,5,31);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,5,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,5,3,1);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,5,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (1,5,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,7,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,7,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,7,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,4,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,4,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,4,4,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,6,3,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,6,5,0);
insert into iusergenperms (dsttype, dstid, prodid, perms) values (2,6,4,0);

-- TABLA iusergrouphdr
insert into iusergrouphdr (id, name, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (1,'PROC-RQS',0,0,'Grupo de usuarios tramitadores de expedientes de Reclamaciones Quejas y Sugerencias',0,getdate(),null,null);
insert into iusergrouphdr (id, name, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (2,'PROC-SUB',0,0,'Grupo de usuarios de tramitación de expedientes de Subvención',0,getdate(),null,null);
insert into iusergrouphdr (id, name, mgrid, type, remarks, crtrid, crtndate, updrid, upddate) values (3,'PROC-LOM',0,0,'Grupo de usuarios de tramitación de expedientes de Licencias de Obra Menor',0,getdate(),null,null);

-- TABLA iusergroupuser
insert into iusergroupuser (groupid, userid) values (1,4);
insert into iusergroupuser (groupid, userid) values (2,4);
insert into iusergroupuser (groupid, userid) values (3,4);

-- TABLA iuserlicences
insert into iuserlicences (info) values ('<?xml version="1.0"?> <Información_Licencias Versión="01.00">   <Datos_Firmados>      <Información_Cliente>         <Identificador>XXXXXX</Identificador>         <Nombre>SIGEM</Nombre>      </Información_Cliente>      <Productos>         <invesDoc>            <Número_Licencias_Usuarios_Registrados>1000000</Número_Licencias_Usuarios_Registrados>            <Usuarios_Concurrentes>               <Número_Licencias_Windows>1000000</Número_Licencias_Windows>               <Número_Licencias_Web>1000000</Número_Licencias_Web></Usuarios_Concurrentes></invesDoc><invesSicres><Número_Licencias_Usuarios_Registrados>1000000</Número_Licencias_Usuarios_Registrados><Usuarios_Concurrentes><Número_Licencias_Windows>1000000</Número_Licencias_Windows><Número_Licencias_Web>1000000</Número_Licencias_Web></Usuarios_Concurrentes> </invesSicres> </Productos> </Datos_Firmados> <Firma>MIIFjwYJKoZIhvcNAQcCoIIFgDCCBXwCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCBFkwggRVMIID/6ADAgECAgp8Q937AAAAAABPMA0GCSqGSIb3DQEBBQUAMEwxCzAJBgNVBAYTAkVTMQ8wDQYDVQQKEwZJRUNJU0ExDDAKBgNVBAsTA0NFWDEeMBwGA1UEAxMVVGVjbm9sb2dpYSBEb2N1bWVudGFsMB4XDTAzMDUxMzA5MTE0NFoXDTA0MDUxMzA5MjE0NFowWjELMAkGA1UEBhMCRVMxDzANBgNVBAcTBk1hZHJpZDEPMA0GA1UEChMGSUVDSVNBMQwwCgYDVQQLEwNDRVgxGzAZBgNVBAMTEmludmVzRG9jIENvcnBvcmF0ZTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA+tB9NXtOsd+REP4QCziIzq2zp1Es5gcZQuw6eSHtzUoyT01R24GnA89cuDOp4RmwOOhRtYBjPM1QaAdDPggau30zpjk6vukOTrhssiaOYL9BT/6rHoT7q6vdqa+/LBzl8htm6HDvbUCLW4RYC8w5/PbTNuM8uGJyEGnds3KrNS0CAwEAAaOCAm8wggJrMA4GA1UdDwEB/wQEAwIE8DATBgNVHSUEDDAKBggrBgEFBQcDAjAdBgNVHQ4EFgQU8mq7Sv4mU9wFvWgL3hZJjji7P/gwgYMGA1UdIwR8MHqAFMVv3J/cFlPxSsMsBt4xIIxUBZAKoVCkTjBMMQswCQYDVQQGEwJFUzEPMA0GA1UEChMGSUVDSVNBMQwwCgYDVQQLEwNDRVgxHjAcBgNVBAMTFVRlY25vbG9naWEgRG9jdW1lbnRhbIIQKGpFfLlWmplB14EP8DdTZTCBqwYDVR0fBIGjMIGgME2gS6BJhkdodHRwOi8vcGlzdGEyMDAwLmRtX2ltYWdlbl9udC5lcy9DZXJ0RW5yb2xsL1RlY25vbG9naWElMjBEb2N1bWVudGFsLmNybDBPoE2gS4ZJZmlsZTovL1xccGlzdGEyMDAwLmRtX2ltYWdlbl9udC5lc1xDZXJ0RW5yb2xsXFRlY25vbG9naWElMjBEb2N1bWVudGFsLmNybDCB8AYIKwYBBQUHAQEEgeMwgeAwbQYIKwYBBQUHMAKGYWh0dHA6Ly9waXN0YTIwMDAuZG1faW1hZ2VuX250LmVzL0NlcnRFbnJvbGwvcGlzdGEyMDAwLmRtX2ltYWdlbl9udC5lc19UZWNub2xvZ2lhJTIwRG9jdW1lbnRhbC5jcnQwbwYIKwYBBQUHMAKGY2ZpbGU6Ly9cXHBpc3RhMjAwMC5kbV9pbWFnZW5fbnQuZXNcQ2VydEVucm9sbFxwaXN0YTIwMDAuZG1faW1hZ2VuX250LmVzX1RlY25vbG9naWElMjBEb2N1bWVudGFsLmNydDANBgkqhkiG9w0BAQUFAANBACdd8Bc7KmbgM8cVQk09Y7xds72Uk0cxDT95qpqCu4gEwWNpRbO3brTeuLXVhHIooq3dqDdO0slLrCuCgcnhJ98xgf8wgfwCAQEwWjBMMQswCQYDVQQGEwJFUzEPMA0GA1UEChMGSUVDSVNBMQwwCgYDVQQLEwNDRVgxHjAcBgNVBAMTFVRlY25vbG9naWEgRG9jdW1lbnRhbAIKfEPd+wAAAAAATzAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGAOH+vnrz4FkMpTqOZaXl/HhP/imXP6raC9ItqitEJ4b4912utVRA/8w/h1pygjYDXza1Qlnp7P34cz4Ff1Y0+KNA2Hrks6Heu8M11xaRvyOtrWyNj3ULrhGbBvIUsOIJG61lhr2LOMSmKVj22YpGmz/9khn2RH/RVd+hGdT3FRqU=</Firma></Información_Licencias>');

-- TABLA iusernextid
UPDATE iusernextid set id=4 where type = 3;
UPDATE iusernextid set id=6 where type = 4;
UPDATE iusernextid set id=6 where type = 1;
UPDATE iusernextid set id=8 where type = 2;
UPDATE iusernextid set id=121 where type = 5;

-- TABLA iuserobjhdr
insert into iuserobjhdr (id, prodid, type, extid1, extid2, extid3, ownertype, ownerid, crtrid, crtndate, updrid, upddate) values (5,3,4,1,0,0,1,0,0,getdate(),null,null);

-- TABLA iuseruserhdr
insert into iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) values (4,'tramitador','NECKEEc=',4,0,0,0,'Usuario de Tramitación',0,getdate(),null,null,322525,'N','N');
insert into iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) values (5,'archivo','OlGLWUk=',6,0,0,0,'',0,getdate(),null,null,322664,'N','N');
insert into iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) values (3,'registro','jubYgrh777k=',3,0,0,0,'',0,getdate(),0,getdate(),322500,'N','N');
--insert into iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) values (0,'SYSSUPERUSER','ADd6PK3AWOSc5e4=',0,0,0,0,'',0,getdate(),null,null,320458,'N','N');

-- TABLA iuserusersys
insert into iuserusersys (maxbadcnts, pwdvp, pwdmbc, pwdminlen, pwdexpiredap, numpwdlock, flags, updrid, upddate) values (3,-1,'N',3,-1,1,0,2147483646,null);

-- TABLA iuserusertype
--insert into iuserusertype (userid, prodid, type) values (0,1,3);
insert into iuserusertype (userid, prodid, type) values (0,2,0);
insert into iuserusertype (userid, prodid, type) values (0,3,0);
insert into iuserusertype (userid, prodid, type) values (0,4,0);
insert into iuserusertype (userid, prodid, type) values (0,5,0);
insert into iuserusertype (userid, prodid, type) values (0,6,0);
insert into iuserusertype (userid, prodid, type) values (0,7,0);
insert into iuserusertype (userid, prodid, type) values (0,8,0);
insert into iuserusertype (userid, prodid, type) values (3,1,0);
insert into iuserusertype (userid, prodid, type) values (3,2,0);
insert into iuserusertype (userid, prodid, type) values (3,3,0);
insert into iuserusertype (userid, prodid, type) values (3,4,0);
insert into iuserusertype (userid, prodid, type) values (3,5,3);
insert into iuserusertype (userid, prodid, type) values (3,6,0);
insert into iuserusertype (userid, prodid, type) values (3,7,0);
insert into iuserusertype (userid, prodid, type) values (3,8,0);
insert into iuserusertype (userid, prodid, type) values (4,1,0);
insert into iuserusertype (userid, prodid, type) values (4,2,0);
insert into iuserusertype (userid, prodid, type) values (4,3,1);
insert into iuserusertype (userid, prodid, type) values (4,4,0);
insert into iuserusertype (userid, prodid, type) values (4,5,0);
insert into iuserusertype (userid, prodid, type) values (4,6,0);
insert into iuserusertype (userid, prodid, type) values (4,7,0);
insert into iuserusertype (userid, prodid, type) values (4,8,0);
insert into iuserusertype (userid, prodid, type) values (5,1,0);
insert into iuserusertype (userid, prodid, type) values (5,2,0);
insert into iuserusertype (userid, prodid, type) values (5,3,1);
insert into iuserusertype (userid, prodid, type) values (5,4,0);
insert into iuserusertype (userid, prodid, type) values (5,5,0);
insert into iuserusertype (userid, prodid, type) values (5,6,0);
insert into iuserusertype (userid, prodid, type) values (5,7,0);
insert into iuserusertype (userid, prodid, type) values (5,8,0);

-- TABLA ivolarchlist
insert into ivolarchlist (archid, listid) values (1,3);
insert into ivolarchlist (archid, listid) values (2,3);

-- TABLA ivollisthdr
insert into ivollisthdr (id, name, flags, stat, remarks, crtrid, crtndate, updrid, upddate) values (3,'ListaRegistro01',0,0,'',0,getdate(),null,null);
insert into ivollisthdr (id, name, flags, stat, remarks, crtrid, crtndate, updrid, upddate) values (4,'ListaTramitador01',0,0,'',0,getdate(),null,null);
insert into ivollisthdr (id, name, flags, stat, remarks, crtrid, crtndate, updrid, upddate) values (5,'ListaTramitadorSegura01',0,0,'',0,getdate(),null,null);
insert into ivollisthdr (id, name, flags, stat, remarks, crtrid, crtndate, updrid, upddate) values (6,'ListaArchivo01',0,0,'',0,getdate(),null,null);

-- TABLA ivolactdir
insert into ivolactdir (volid, actdir, numfiles) values (4,1,0);
insert into ivolactdir (volid, actdir, numfiles) values (5,1,0);
insert into ivolactdir (volid, actdir, numfiles) values (6,1,0);

-- TABLA ivollistvol
insert into ivollistvol (listid, volid, sortorder) values (3,4,1);
insert into ivollistvol (listid, volid, sortorder) values (4,5,1);
insert into ivollistvol (listid, volid, sortorder) values (5,5,1);
insert into ivollistvol (listid, volid, sortorder) values (6,6,1);

-- TABLA ivolrephdr
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (5,'REGISTRO',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/isicres/000_REPOSITORIO_REGISTRO"|1|3|3|0|0',0,'',0,getdate(),null,null);
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (6,'TRAMITACION',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/isicres/000_SIGEM_TRAMITACION"|1|3|3|0|0',0,'',0,getdate(),null,null);
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (7,'ARCHIVO',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/isicres/000_SIGEM_ARCHIVO"|1|3|3|0|0',0,'',0,getdate(),null,null);

-- TABLA ivolvolhdr
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (4,'RegistroVol01',5,'"01.01"|1|"RegistroVol01"|"524288000"|0',0,'0.',100,0,'',0,getdate(),null,null);
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (5,'TramitadorVol01',6,'"01.01"|1|"TramitadorVol01"|"524288000"|0',0,'819360.',15,0,'',0,getdate(),null,null);
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (6,'ArchivoVol01',7,'"01.01"|1|"ArchivoVol01"|"524288000"|0',0,'0',0,0,'',0,getdate(),null,null);

-- TABLA scr_ca
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (1,'TRQS','SOLICITUD DE RECLAMACION, QUEJA Y SUGERENCIAS',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (2,'TSUB','SUBVENCION',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (3,'TLIC','LICENCIA DE OBRA MENOR',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (4,'TCTLA','CAMBIO TITULARIDAD LICENCIA DE APERTURA',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (5,'TCAPN','CONTRATO NEGOCIADO',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (6,'TEXS','EXPEDIENTE SANCIONADOR',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (7,'TLAAC','LICENCIA APERTURA ACTIVIDAD CLASIFICADA',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (8,'TLAANC','LICENCIA APERTURA ACTIVIDAD NO CLASIFICADA',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (9,'TSLV','LICENCIA DE VADO',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (10,'TSRT','RECLAMACION DE TRIBUTOS',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (11,'TSCU','CERTIFICADO URBANISTICO',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (12,'TSTEM','TARJETA ESTACIONAMIENTO MUNUSVALIDOS',1,1,1,0,getdate(),null,1,0);
insert into scr_ca (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) values (13,'TSAA','SOLICITUD DE ACOMETIDA DE AGUA',1,1,1,0,getdate(),null,1,0);

-- TABLA scr_bookofic
insert into scr_bookofic (id, id_book, id_ofic, numeration) values (3,1,2,2);
insert into scr_bookofic (id, id_book, id_ofic, numeration) values (4,2,2,2);
insert into scr_bookofic (id, id_book, id_ofic, numeration) values (5,1,3,2);

-- TABLA scr_ofic
insert into scr_ofic (id, code, acron, name, creation_date, disable_date, id_orgs, stamp, deptid, type) values (2,'001','OR1','OFICINA DE REGISTRO1',getdate(),null,1,'OFICINA DE REGISTRO1',3,1);
insert into scr_ofic (id, code, acron, name, creation_date, disable_date, id_orgs, stamp, deptid, type) values (3,'999','REGTEL','OFICINA DE REGISTRO TELEMATICO',getdate(),null,1,'',5,2);

-- TABLA scr_userfilter
insert into scr_userfilter (idarch, iduser, filterdef, type_obj) values (1,2,'FLD5 = 2 OR FLD5 = 3  $$Oficina de registro|igual a|3|001|o|$Oficina de registro|igual a|3|999||$',2);

-- Actualizamos scr_contador
UPDATE scr_contador set contador=5 WHERE tablaid = 'SCR_BOOKOFIC';
UPDATE scr_contador set contador=13 WHERE tablaid = 'SCR_CA';
UPDATE scr_contador set contador=3 WHERE tablaid = 'SCR_OFIC';
UPDATE scr_contador set contador=2 WHERE tablaid = 'SCR_TYPEOFIC';
UPDATE scr_contador set contador=5 WHERE tablaid = 'SCR_WS';