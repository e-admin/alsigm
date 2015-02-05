-- Tablas para integrar registro con gestores documentales
CREATE TABLE scr_repositorydocs (
	id integer NOT NULL,
	docuid character varying(250) NOT NULL
);
CREATE INDEX scr_repositorydoc0 ON scr_repositorydocs USING btree (id);
CREATE UNIQUE INDEX scr_repositorydoc1 ON scr_repositorydocs USING btree (id);
ALTER TABLE ONLY scr_repositorydocs
    ADD CONSTRAINT pk_repositorydocs PRIMARY KEY (id);

CREATE TABLE scr_repositoryconf (
	id integer NOT NULL,
	data text NOT NULL
);
CREATE INDEX scr_repoconf0 ON scr_repositoryconf USING btree (id);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf USING btree (id);
ALTER TABLE ONLY scr_repositoryconf
    ADD CONSTRAINT pk_repositoryconf PRIMARY KEY (id);



CREATE TABLE scr_repobooktype (
	book_type integer NOT NULL,
	id_rep_conf integer NOT NULL
);
CREATE INDEX scr_repbooktype0 ON scr_repobooktype USING btree (book_type);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype USING btree (book_type);
ALTER TABLE ONLY scr_repobooktype
    ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE ONLY scr_repobooktype
    ADD CONSTRAINT fk_repobooktype0 FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(id);

