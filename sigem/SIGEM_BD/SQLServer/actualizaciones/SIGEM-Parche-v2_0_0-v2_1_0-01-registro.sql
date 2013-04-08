-- Tablas para integrar registro con gestores documentales
CREATE TABLE scr_repositorydocs (
	id int NOT NULL,
	docuid character varying(250) NOT NULL
);
CREATE UNIQUE INDEX scr_repositorydoc1 ON scr_repositorydocs (id);
ALTER TABLE scr_repositorydocs ADD CONSTRAINT pk_repositorydocs PRIMARY KEY (id);


CREATE TABLE scr_repositoryconf (
	id int NOT NULL,
	data text NOT NULL
);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf (id);
ALTER TABLE scr_repositoryconf ADD CONSTRAINT pk_repositoryconf PRIMARY KEY (id);


CREATE TABLE scr_repobooktype (
	book_type int NOT NULL,
	id_rep_conf int NOT NULL
);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT fk_repobooktype0 FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(id);




-- Crear una relación un localizador y el documento compulsado
CREATE TABLE scr_doclocator
(
	bookID int NOT NULL,
	folderID int NOT NULL,
	pageID int NOT NULL,
	locator varchar(128) NOT NULL
);

CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator (locator);