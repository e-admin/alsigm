update scr_contador set contador=4897 where tablaid='SCR_ORGS';
update scr_contador set contador=12 where tablaid='SCR_TYPEADM';
INSERT INTO scr_typeadm (id,code,description) values (12,'I','INTERCAMBIO REGISTRAL');

INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4891,'O00002684',NULL ,'RGAD','REGISTRO GENERAL DEL AYUNTAMIENTO DE DONOSTIA-SAN SEBASTIAN', '2012-06-15 11:03:57.378',NULL, 12,1,'' );
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4892, 'O00002683', NULL, 'RGDPC', 'REGISTRO GENERAL DE LA DIPUTACION PROVINCIAL DE CACERES', '2012-06-15 11:05:15.828' ,NULL, 12,1,'');
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4893, 'O00002101', NULL, 'RGDPCR','REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE CIUDAD REAL', '2012-06-15 11:06:03.613',NULL, 12,1,'' );
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4894, 'O00002681', NULL, 'RGAT',   'REGISTRO GENERAL DEL AYUNTAMIENTO DE TARRAGONA', '2012-06-15 11:06:44.158',NULL, 12,1,'' );
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4895, 'O00002682', NULL, 'RGAB', 'REGISTRO GENERAL DEL AYUNTAMIENTO DE BADAJOZ', '2012-06-15 11:07:58.554',NULL, 12,1,'' );
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4896, 'O00002685', NULL, 'RGUPC', 'REGISTRO GENERAL DE LA UNIVERSIDAD POLITÉCNICA DE CARTAGENA', '2012-06-15 11:08:34.515',NULL, 12,1,'' );
INSERT INTO scr_orgs (id, code, id_father, acron, name, creation_date, disable_date, type, enabled, cif) values  (4897, 'O00002686', NULL, 'RGUAM', 'REGISTRO GENERAL DE LA UNIVERSIDAD AUTÓNOMA DE MADRID', '2012-06-15 11:09:13.82',NULL, 12,1,'' );


insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (1,'L02000010', 'DIPUTACION PROVINCIAL DE CACERES', 'O00002683', 'REGISTRO GENERAL DE LA DIPUTACION PROVINCIAL DE CACERES',4892);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (2, 'L02000013','DIPUTACIÓN PROVINCIAL DE CIUDAD REAL', 'O00002101',   'REGISTRO GENERAL DE LA DIPUTACIÓN PROVINCIAL DE CIUDAD REAL',4893);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (3, 'A13006142', 'UNIVERSIDAD AUTÓNOMA DE MADRID',  'O00002686',   'REGISTRO GENERAL DE LA UNIVERSIDAD AUTÓNOMA DE MADRID' , 4897);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (4, 'A14006141', 'UNIVERSIDAD POLITECNICA DE CARTAGENA',  'O00002685',   'REGISTRO GENERAL DE LA UNIVERSIDAD POLITÉCNICA DE CARTAGENA', 4896);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (5, 'L01060153',  'AYUNTAMIENTO DE BADAJOZ', 'O00002682',   'REGISTRO GENERAL DEL AYUNTAMIENTO DE BADAJOZ', 4895);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (6, 'L01200697',  'AYUNTAMIENTO DE DONOSTIA-SAN SEBASTIAN', 'O00002684', 'REGISTRO GENERAL DEL AYUNTAMIENTO DE DONOSTIA-SAN SEBASTIAN', 4891);
insert into scr_tramunit (id, code_tramunit, name_tramunit,code_entity,name_entity, id_orgs) values (7, 'L01431482',  'AYUNTAMIENTO DE TARRAGONA', 'O00002681', 'REGISTRO GENERAL DEL AYUNTAMIENTO DE TARRAGONA', 4894);

--SELECT * FROM scr_orgs WHERE id>=4890 order by id;
--SELECT * FROM scr_typeadm where id=12;
