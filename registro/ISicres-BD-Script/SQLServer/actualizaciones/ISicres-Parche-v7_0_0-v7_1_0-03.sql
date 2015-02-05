-- Es necesario la realizacion de un script que inserte en esta tabla
-- los datos id, fdrid y fileid de las tablas aXpageh en la tabla scr_pagerepository.

-- El script debe recorrer cada uno de los libros

-- X es el ID del libro

insert into scr_pagerepository (
	select (select id from scr_regstate where idarchreg = X) as bookid
	     , fdrid
	     , id as pageid
	     , fileid as docuid
	  from aXpageh

)