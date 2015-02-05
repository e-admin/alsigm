--Inserta una nueva dirección telemática de personas
INSERT INTO scr_typeaddress (id, description, code) VALUES (4, 'Dirección electrónica única', 'DU');

--Insertar el contador para crear departamentos desde la administración
DELETE FROM SCR_CONTADOR WHERE TABLAID = 'IUSERDEPTHDR';
INSERT INTO SCR_CONTADOR (TABLAID,CONTADOR) VALUES ('IUSERDEPTHDR',(SELECT MAX(ID) FROM IUSERDEPTHDR));

-- Insertar Tipos de Asunto necesarios en Registro Presencial para los 10 nuevos tramites del telematico
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (4,'TCTLA','CAMBIO TITULARIDAD LICENCIA DE APERTURA',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (5,'TCAPN','CONTRATO NEGOCIADO',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (6,'TEXS','EXPEDIENTE SANCIONADOR',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (7,'TLAAC','LICENCIA APERTURA ACTIVIDAD CLASIFICADA',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (8,'TLAANC','LICENCIA APERTURA ACTIVIDAD NO CLASIFICADA',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (9,'TSLV','LICENCIA DE VADO',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (10,'TSRT','RECLAMACION DE TRIBUTOS',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (11,'TSCU','CERTIFICADO URBANISTICO',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (12,'TSTEM','TARJETA ESTACIONAMIENTO MUNUSVALIDOS',1,1,1,0,'2009-07-09 15:00:36',null,1,0);
INSERT INTO SCR_CA (id, code, matter, for_ereg, for_sreg, all_ofics, id_arch, creation_date, disable_date, enabled, id_org) VALUES (13,'TSAA','SOLICITUD DE ACOMETIDA DE AGUA',1,1,1,0,'2009-07-09 15:00:36',null,1,0);

-- Actualizar el contador de la tabla
UPDATE SCR_CONTADOR SET CONTADOR = (SELECT MAX(ID) FROM scr_ca) WHERE TABLAID='SCR_CA';