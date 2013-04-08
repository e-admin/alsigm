
-- Crear una relación un localizador y el documento compulsado
CREATE TABLE scr_doclocator
(
 	bookID number NOT NULL,
	folderID number NOT NULL,
	pageID number NOT NULL,
	locator varchar2(128 CHAR) NOT NULL
);

CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator (locator);