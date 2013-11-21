-- TABLA scr_bookofic

-- TABLA scr_booktypeconfig
INSERT INTO scr_booktypeconfig (booktype, options) VALUES (1, '<?xml version="1.0"?>
<Configuration><InfoStamping FontName="Arial" FontSize="20"><Fld id="1" Left="0" Top="0"><Label/></Fld><Fld id="2" Left="250" Top="0"><Label/></Fld><Fld id="3" Left="550" Top="0"><Label/></Fld><Fld id="5" Left="750" Top="0"><Label/></Fld><Fld id="3000" Left="850" Top="0"><Label/></Fld><Fld id="3002" Left="1150" Top="0"><Label/></Fld></InfoStamping></Configuration>
');
INSERT INTO scr_booktypeconfig (booktype, options) VALUES (2, '<?xml version="1.0"?>
<Configuration><InfoStamping FontName="Arial" FontSize="20"><Fld id="1" Left="0" Top="0"><Label/></Fld><Fld id="2" Left="250" Top="0"><Label/></Fld><Fld id="3" Left="550" Top="0"><Label/></Fld><Fld id="5" Left="750" Top="0"><Label/></Fld><Fld id="3000" Left="850" Top="0"><Label/></Fld><Fld id="3002" Left="1150" Top="0"><Label/></Fld></InfoStamping></Configuration>
');


-- TABLA scr_configuration
INSERT INTO scr_configuration (id, version, options) VALUES (1, '6.6', '<?xml version="1.0"?>
<Configuration><General><AlarmOnIncompleteReg>0</AlarmOnIncompleteReg><ModifySystemDate>0</ModifySystemDate><RepositorySystem>0</RepositorySystem><PopulationPartition>0</PopulationPartition></General><Numeration><Format type="0"/><Format type="1"/><Format type="2"/></Numeration><Distribution><TimeOut>0</TimeOut><AlarmOnNewDist>0</AlarmOnNewDist><AutoArchiving>0</AutoArchiving><AlarmOnRejected>0</AlarmOnRejected><ViewAcceptedRegisters>0</ViewAcceptedRegisters><DistSRegisters>0</DistSRegisters><CanChangeDestWithoutList>0</CanChangeDestWithoutList></Distribution><Extension><Validation/></Extension></Configuration>
');

-- TABLA scr_contador
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_BOOKOFIC', 0);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_CA', 0);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_CITIES', 8067);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_DELTAS', 0);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_DISTLIST', 0);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_OFIC', 0);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_ORGS', 5000);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_PROV', 52);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_REGSTATE', 2);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_REPORTS', 8);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_TYPEADM', 11);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_TYPEOFIC', 1);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_WS', 1);
INSERT INTO scr_contador (tablaid, contador) VALUES ('PERSONS', 1);
INSERT INTO scr_contador (tablaid, contador) VALUES ('SCR_ADDRESS', 1);

-- TABLA scr_entregsnd
INSERT INTO scr_entregsnd (id_entreg, transport_type, encoding_msg, certificate_issuer, certificate_nserial) VALUES (1, 0, 0, '', '');


-- TABLA scr_fldaccperms
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 1, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 2, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 3, 0, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 4, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 5, 1536, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 6, 1792, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 7, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 8, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 9, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 10, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 11, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 12, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 13, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 14, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 15, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 16, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (1, 17, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 1, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 2, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 3, 0, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 4, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 5, 1536, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 6, 1792, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 7, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 8, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 9, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 10, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 11, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 12, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (2, 13, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 1, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 2, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 3, 0, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 4, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 5, 1536, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 6, 1792, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 7, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 8, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 9, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 10, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 11, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 12, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 13, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 14, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 15, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 16, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (3, 17, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 1, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 2, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 3, 0, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 4, 3840, 3840, 3840);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 5, 1536, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 6, 1792, 1792, 1792);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 7, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 8, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 9, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 10, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 11, 4080, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 12, 4016, 4080, 4080);
INSERT INTO scr_fldaccperms (archivetype, idfld, userperms, adminperms, suserperms) VALUES (4, 13, 4016, 4080, 4080);


-- TABLA scr_genperms
INSERT INTO scr_genperms (id, perms) VALUES (1, 1);
INSERT INTO scr_genperms (id, perms) VALUES (2, 2);
INSERT INTO scr_genperms (id, perms) VALUES (3, 32);
INSERT INTO scr_genperms (id, perms) VALUES (4, 4);
INSERT INTO scr_genperms (id, perms) VALUES (5, 8);
INSERT INTO scr_genperms (id, perms) VALUES (6, 16);
INSERT INTO scr_genperms (id, perms) VALUES (7, 64);
INSERT INTO scr_genperms (id, perms) VALUES (8, 128);
INSERT INTO scr_genperms (id, perms) VALUES (9, 256);
INSERT INTO scr_genperms (id, perms) VALUES (10, 512);
INSERT INTO scr_genperms (id, perms) VALUES (11, 1024);
INSERT INTO scr_genperms (id, perms) VALUES (12, 2048);


-- TABLA scr_typeaddress
INSERT INTO scr_typeaddress (id, description, code) VALUES (1, 'Teléfono (fijo)', 'TF');
INSERT INTO scr_typeaddress (id, description, code) VALUES (2, 'Correo electrónico', 'CE');
INSERT INTO scr_typeaddress (id, description, code) VALUES (3, 'Fax', 'FX');
INSERT INTO scr_typeaddress (id, description, code) VALUES (4, 'Dirección electrónica única', 'DU');
INSERT INTO scr_typeaddress (id, description, code) VALUES (5, 'Teléfono (móvil)', 'TM');
INSERT INTO scr_typeaddress (id, description, code) VALUES (6, 'Comparecencia Electrónica', 'TE');


-- TABLA scr_typedoc
INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (1, 'CIF', 2, 'C');
INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (2, 'NIF', 1, 'N');
INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (3, 'Pasaporte', 1, 'P');
INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (4, 'Documento de identificación de extranjeros', 1, 'E');
INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (5, 'Otros', 0, 'X');

-- TABLA scr_typeofic
INSERT INTO scr_typeofic (id, description) VALUES (1, 'Registro central');
INSERT INTO scr_typeofic (id, description) VALUES (2, 'Registro auxiliar');


-- TABLA scr_ofic

-- TABLA scr_regstate

-- TABLA scr_userfilter

-- TABLA itdsmsnextid
INSERT INTO itdsmsnextid (cid) VALUES (1);

--TABLA SCR_PAGETYPE
INSERT INTO scr_pagetype VALUES(0,'Sin tipo');
INSERT INTO scr_pagetype VALUES(1,'Documento a compulsar');
INSERT INTO scr_pagetype VALUES(2,'Firma documento a compulsar');
INSERT INTO scr_pagetype VALUES(3,'Documento localizador de fichero a compulsar');

