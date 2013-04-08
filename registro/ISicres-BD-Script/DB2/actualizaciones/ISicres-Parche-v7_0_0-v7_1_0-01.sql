-- Tablas para integrar registro con gestores documentales
CREATE TABLE scr_repositorydocs (
    ID INTEGER NOT NULL,
    docuid VARCHAR(250) NOT NULL
);
CREATE INDEX scr_repositorydoc0 ON scr_repositorydocs(id);
CREATE UNIQUE INDEX scr_repositorydoc1 ON scr_repositorydocs (ID);
ALTER TABLE scr_repositorydocs ADD CONSTRAINT pk_repositorydocs PRIMARY KEY (ID);

CREATE TABLE scr_repositoryconf (
    ID INTEGER NOT NULL,
    DATA CLOB NOT NULL
);
CREATE INDEX scr_repoconf0 ON scr_repositoryconf(id);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf (ID);
ALTER TABLE scr_repositoryconf ADD CONSTRAINT pk_repositoryconf PRIMARY KEY (ID);


CREATE TABLE scr_repobooktype (
    book_type INTEGER NOT NULL,
    id_rep_conf INTEGER NOT NULL
);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype (book_type);
CREATE INDEX scr_repbooktype0 ON scr_repobooktype (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT fk_repobooktype FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(ID);

