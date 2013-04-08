--
-- Creación del usuario 'REGISTRO_TELEMATICO' para el proceso de consolidación
--

INSERT INTO iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) 
	VALUES (6, 'REGISTRO_TELEMATICO', '2k9E1U4=', 5, 0, 0, 0, 'Usuario de Registro Telemático', 0, getDate(), null, null, 346255, 'N', 'N');

-- INSERT INTO iusernextid (type, id) VALUES (1, 6); --> INSERT INTO iusernextid (type, id) VALUES (1, 7);
UPDATE iusernextid SET id=7 WHERE type=1;

INSERT INTO iusergenperms (dsttype, dstid, prodid, perms) VALUES (1, 6, 3, 0);
INSERT INTO iusergenperms (dsttype, dstid, prodid, perms) VALUES (1, 6, 4, 0);
INSERT INTO iusergenperms (dsttype, dstid, prodid, perms) VALUES (1, 6, 5, 0);

INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 1, 0);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 2, 0);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 3, 1);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 4, 1);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 5, 3);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 6, 0);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 7, 0);
INSERT INTO iuserusertype (userid, prodid, type) VALUES (6, 8, 0);

INSERT INTO scr_userconfig (userid, data, idoficpref) VALUES (6, '<Configuration></Configuration>', 3);
INSERT INTO scr_usrident (userid, tmstamp, first_name, second_name, surname) 
	VALUES (6, getDate(), '-', '-', 'REGISTRO TELEMATICO');
INSERT INTO scr_usrloc (userid, tmstamp, address, city, zip, country, telephone, fax, email) 
	VALUES (6, getDate(), '-', '-', '', '', '', '', '');
