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