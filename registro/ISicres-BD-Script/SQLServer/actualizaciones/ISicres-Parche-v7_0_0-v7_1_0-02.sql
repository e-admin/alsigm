-- Crear una relación un localizador y el documento compulsado
CREATE TABLE scr_doclocator
(
	bookID int NOT NULL,
	folderID int NOT NULL,
	pageID int NOT NULL,
	locator varchar(128) NOT NULL
);

CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator (locator);
