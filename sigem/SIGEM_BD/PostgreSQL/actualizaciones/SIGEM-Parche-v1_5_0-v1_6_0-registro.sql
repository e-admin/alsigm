-------------------------------------------
-- SIGEM-Parche-v1_5_0-p1-registro.sql
-------------------------------------------

CREATE TEMPORARY TABLE iuseruser_tmp (
    aplicacion integer,
    valor integer default 0
   ) ON COMMIT DROP;

INSERT INTO iuseruser_tmp (aplicacion) values (1);
INSERT INTO iuseruser_tmp (aplicacion) values (2);
INSERT INTO iuseruser_tmp (aplicacion) values (3);
INSERT INTO iuseruser_tmp (aplicacion) values (4);
INSERT INTO iuseruser_tmp (aplicacion) values (5);
INSERT INTO iuseruser_tmp (aplicacion) values (6);
INSERT INTO iuseruser_tmp (aplicacion) values (7);
INSERT INTO iuseruser_tmp (aplicacion) values (8);

CREATE OR REPLACE FUNCTION insertar(id_usuario INTEGER, id_producto INTEGER, valor INTEGER) RETURNS VOID AS
$$
BEGIN
	LOOP
		BEGIN
			INSERT INTO iuserusertype(userid, prodid, "type") VALUES (id_usuario, id_producto, valor);
			RETURN;
		EXCEPTION WHEN unique_violation THEN
			RETURN;
		END;
	END LOOP;
END;
$$
LANGUAGE plpgsql;

SELECT insertar(s1.id, s2.aplicacion, s2.valor) from iuseruserhdr AS s1, iuseruser_tmp AS s2;

DROP FUNCTION insertar(INTEGER, INTEGER, INTEGER);

COMMIT;


-------------------------------------------
-- SIGEM-Parche-v1_5_0-p3-registro.sql
-------------------------------------------

ALTER TABLE a1pageh ALTER name TYPE character varying(64);
ALTER TABLE a2pageh ALTER name TYPE character varying(64);
ALTER TABLE a3pageh ALTER name TYPE character varying(64);
ALTER TABLE a4pageh ALTER name TYPE character varying(64);

ALTER TABLE ivolfilehdr ALTER filesize TYPE integer;
UPDATE ivolfilehdr SET filesize = 0;
ALTER TABLE ivolfilehdr ALTER COLUMN filesize SET NOT NULL;

ALTER TABLE idocrpt ALTER COLUMN edittype SET NOT NULL;

UPDATE iuserusersys SET flags = 0;
ALTER TABLE iuserusersys ALTER COLUMN flags SET NOT NULL;

ALTER TABLE iuseruserpwds DROP COLUMN upddate;
ALTER TABLE iuseruserpwds ADD COLUMN upddate integer;

CREATE INDEX iuseruserpwds1 ON iuseruserpwds (userid);
CREATE INDEX iuseruserpwds2 ON iuseruserpwds ("password");

CREATE TABLE ivolvoltbl
(
   locid character varying(32) NOT NULL, 
   extid1 integer NOT NULL, 
   extid2 integer NOT NULL, 
   extid3 integer NOT NULL, 
   extid4 integer NOT NULL, 
   fileext character varying(10) NOT NULL, 
   fileval bytea NOT NULL
);

CREATE UNIQUE INDEX ivolvoltbl1 ON ivolvoltbl (locid);