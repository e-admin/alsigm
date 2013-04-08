-- Tablas para integrar registro con gestores documentales
CREATE TABLE scr_repositorydocs (
    ID NUMBER(10) NOT NULL,
    docuid VARCHAR2(250 CHAR) NOT NULL
);
CREATE UNIQUE INDEX scr_repositorydoc1 ON scr_repositorydocs (ID);
ALTER TABLE scr_repositorydocs ADD CONSTRAINT pk_repositorydocs PRIMARY KEY (ID);

CREATE TABLE scr_repositoryconf (
    ID NUMBER(10) NOT NULL,
    DATA CLOB NOT NULL
);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf (ID);
ALTER TABLE scr_repositoryconf ADD CONSTRAINT pk_repositoryconf PRIMARY KEY (ID);


CREATE TABLE scr_repobooktype (
    book_type NUMBER(10) NOT NULL,
    id_rep_conf NUMBER(10) NOT NULL
);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT fk_repobooktype FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(ID);

-- Crear una relación un localizador y el documento compulsado
CREATE TABLE scr_doclocator
(
 	bookID number NOT NULL,
	folderID number NOT NULL,
	pageID number NOT NULL,
	locator varchar2(128 CHAR) NOT NULL
);

CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator (locator);
