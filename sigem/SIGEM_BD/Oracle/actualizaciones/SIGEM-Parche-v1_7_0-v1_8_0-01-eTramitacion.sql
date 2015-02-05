ALTER TABLE sgmrdedocumentos
RENAME COLUMN hash TO filehash;

ALTER TABLE sgmntinfo_documento
MODIFY cnotiexpe character varying(32);

ALTER TABLE sgmntinfo_notificacion
MODIFY cguid character varying(32);

ALTER TABLE sgmntinfo_notificacion
MODIFY cnumexp character varying(32);