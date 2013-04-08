--nuevo campo DOCTYPEID en SCR_PAGEREPOSITORY
ALTER TABLE scr_pagerepository ADD id_pagetype NUMBER DEFAULT 0 NOT NULL;

CREATE TABLE scr_pagetype (
    id NUMBER NOT NULL,
    name varchar(100 CHAR) NOT NULL
);

--TABLA scr_pagetype
ALTER TABLE scr_pagetype ADD CONSTRAINT pk_pagetype PRIMARY KEY (id);

--TABLA SCR_PAGETYPE
INSERT INTO scr_pagetype VALUES(0,'Sin tipo');
INSERT INTO scr_pagetype VALUES(1,'Documento a compulsar');
INSERT INTO scr_pagetype VALUES(2,'Firma documento a compulsar');
INSERT INTO scr_pagetype VALUES(3,'Documento localizador de fichero a compulsar');


--TABLA scr_pagerepository
ALTER TABLE scr_pagerepository ADD CONSTRAINT fk_pagerepository0 FOREIGN KEY (id_pagetype) REFERENCES scr_pagetype(id);

