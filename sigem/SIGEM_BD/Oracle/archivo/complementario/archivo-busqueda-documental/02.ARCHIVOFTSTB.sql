CREATE OR REPLACE
package body archivo is

procedure Preferencias is
  Error integer;
begin

   Error := -1;
   begin
      ctx_ddl.create_preference( 'ARCHIVO_LEXER', 'BASIC_LEXER');
      -- Normalización de  caracteres acentuados 
      ctx_ddl.set_attribute( 'ARCHIVO_LEXER', 'BASE_LETTER', 'YES');
      -- Caracteres que unen palabras 
      ctx_ddl.set_attribute( 'ARCHIVO_LEXER', 'PRINTJOINS', '-/');
      -- Caracteres finalizadores de una frase 
      ctx_ddl.set_attribute( 'ARCHIVO_LEXER', 'PUNCTUATIONS', '.?!:');
      -- Carácter continuador de una palabra al final de una línea 
      ctx_ddl.set_attribute( 'ARCHIVO_LEXER', 'CONTINUATION', '-'); 
      ctx_ddl.set_attribute( 'ARCHIVO_LEXER', 'INDEX_THEMES', 'NO'); 
   exception
      when others then Error := 1;
   end;
   begin
      -- Lematización de palabras del castellano 
      ctx_ddl.create_preference( 'ARCHIVO_WORD', 'BASIC_WORDLIST');
      ctx_ddl.set_attribute( 'ARCHIVO_WORD', 'STEMMER', 'SPANISH');
   exception
      when others then Error := 2;
   end;
   begin
      -- Inso filter
      ctx_ddl.create_preference( 'ARCHIVO_FILTER', 'USER_FILTER');
      ctx_ddl.set_attribute( 'ARCHIVO_FILTER', 'COMMAND', 'filter.bat');
   exception
      when others then Error := 3;
   end;   
   -- Se ha creado esta propiedad porque no hace caso de las palabras vacías por defecto. 
   begin
     ctx_ddl.create_stoplist( 'ARCHIVO_STOP');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'a');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'acá');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ahí');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ajena');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ajenas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ajeno');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ajenos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'al');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'algún');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'algo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'alguna');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'algunas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'alguno');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'algunos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'allá');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'allí');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquel');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquella');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquellas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquello');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquellos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'aquí');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cómo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cada');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cierta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ciertas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cierto');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ciertos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'como');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'con');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'conmigo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'consigo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'contigo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cualquier');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cualquiera');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cualquieras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuan');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuanta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuantas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuanto');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuantos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuán');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuánta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuántas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuánto');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'cuántos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'de');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'dejar');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'del');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'demasiada');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'demasiadas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'demasiado');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'demasiados');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'demás');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'el');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ella');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ellas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ellos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'esa');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'esas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ese');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'esos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'esta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'estar');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'estas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'este');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'estos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'hacer');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'hasta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'jamás');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'junto');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'juntos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'la');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'las');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'lo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'los');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'me');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'menos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mientras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'misma');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mismas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mismo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mismos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'más');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mucha');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mucho');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchísima');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchísimas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchísimo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muchísimos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'muy');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mía');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'mío');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nada');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ni');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ninguna');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ningunas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ninguno');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ningunos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'no');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nosotras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nosotros');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nuestra');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nuestras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nuestro');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nuestros');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'nunca');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'os');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'otra');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'otras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'otro');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'otros');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'para');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'parecer');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'poca');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'pocas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'poco');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'pocos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'por');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'porque');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'que');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'querer');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'quien');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'quienes');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'quienesquiera');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'quienquiera');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'quién');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'qué');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ser');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'si');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'siempre');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'Sr');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'Sra');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'Sres');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'Sta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'suya');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'suyas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'suyo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'suyos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'sí');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'sín');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tú');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tal');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tales');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tan');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tanta');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tantas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tanto');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tantos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'te');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tener');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ti');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'toda');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'todas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'todo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'todos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tomar');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tuya');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'tuyo');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'él');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'un');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'una');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'unas');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'unos');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'usted');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'ustedes');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'varias');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'varios');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vosotras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vosotros');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vuestra');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vuestras');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vuestro');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'vuestros');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'y');
     ctx_ddl.add_stopword( 'ARCHIVO_STOP', 'yo');
   exception
      when others then Error := 5;
   end;
end;

procedure CreatePolicy( Tabla in char, 
                        Columna in char, 
                        TextKey in char,
                        Vacias in char,
                        Especiales in char,
                        Lematiza in integer) is

   Policy      varchar2(64);      -- Nombre de la política 
   Lexer       varchar2(64);
   WordList    varchar2(64);
   StopList    varchar2(64);
   Palabra     varchar2(10);
   sSQL        varchar2(256);
   Puntero     integer;
   Void        integer;
   n           integer;
   Error       integer;
begin  
    
   Error := -1;
   Policy := 'CTX_' || Tabla || '_' || Columna;
   -- Elimina el índice si ya existe 
   begin
     execute immediate 'drop index ' || Policy;
   exception
     when others then Error := 1;
   end;
   Lexer := 'ARCHIVO_LEXER';
   StopList := 'CTXSYS.EMPTY_STOPLIST';
   WordList := 'ARCHIVO_WORD';
   -- Crea la categoría GENERIC STOP LIST 
   if Vacias is not null then
     StopList :=  Tabla || '_' || Columna || '_STOP';
	 -- Elimina la preferencia si ya existe 
     begin
	   ctx_ddl.drop_stoplist( StopList);
	 exception
	   when others then Error := 2;
	 end;
     ctx_ddl.create_stoplist( StopList);
     sSQL := 'select * from ' || Vacias;
     Puntero := DBMS_SQL.OPEN_CURSOR;
     DBMS_SQL.PARSE( Puntero, sSQL, DBMS_SQL.V7);
     DBMS_SQL.DEFINE_COLUMN( Puntero, 1, Palabra, 10);
     Void := DBMS_SQL.EXECUTE( Puntero);
     n := 1;
     while DBMS_SQL.FETCH_ROWS( Puntero) > 0 loop
       DBMS_SQL.COLUMN_VALUE( Puntero, 1, Palabra);
       if Palabra is not null then 
         ctx_ddl.add_stopword( StopList, Palabra);
       end if;
     end loop;
     DBMS_SQL.CLOSE_CURSOR( Puntero);
   end if;
   Lexer :=  Tabla || '_' || Columna || '_LEXER';
   begin
     ctx_ddl.drop_preference( Lexer);
   exception
     when others then Error := 3;
   end;
   -- Por defecto 
   Lexer := 'ARCHIVO_LEXER';
   -- Crea la categoría Lexer 
   if Especiales is not null then
     Lexer :=  Tabla || '_' || Columna || '_LEXER';
     ctx_ddl.create_preference( Lexer, 'BASIC_LEXER');
     -- Normalización de  caracteres acentuados 
     ctx_ddl.set_attribute( Lexer, 'BASE_LETTER', 'YES');
     -- Caracteres que unen palabras 
     ctx_ddl.set_attribute( Lexer, 'PRINTJOINS', Especiales);
     -- Caracteres finalizadores de una frase 
     ctx_ddl.set_attribute( Lexer, 'PUNCTUATIONS', '.¿?¡!:');
     -- Carácter continuador de una palabra al final de una línea 
     ctx_ddl.set_attribute( Lexer, 'CONTINUATION', '-');  
     ctx_ddl.set_attribute( Lexer, 'INDEX_THEMES', 'NO'); 
   end if;
   -- Lematización 
   if Lematiza = 1 then
     begin
	   execute immediate 'create index ' || Policy ||
		                 ' on ' || Tabla || '(' || Columna || ')'||
		                 ' indextype is ctxsys.context' ||
		                 ' parameters( ''' ||
						 ' lexer ' || Lexer ||
						 ' stoplist ' || StopList ||
						 ' wordlist ' || WordList || ''')';
     exception
       when others then Error := 4;
     end;
   else
     begin
	   execute immediate 'create index ' || Policy ||
		                 ' on ' || Tabla || '(' || Columna || ')'||
		                 ' indextype is ctxsys.context' ||
		                 ' parameters( ''' ||
						 ' lexer ' || Lexer ||
						 ' stoplist ' || StopList || ''')';
	 exception
	   when others then Error := 5;
	 end;
   end if;
end CreatePolicy;

procedure DropPolicy( Tabla in char, 
                      Columna in char, 
                      Vacias in char,
                      Especiales in char) is

   Policy   varchar2(64);    -- Nombre de la directiva 
   Lexer    varchar2(64);    -- Lexer asociado a la directica 
   WordList varchar2(64);    -- Word list asociado a la directiva 
   StopList varchar2(64);    -- Stop list asociado a la directiva 
   Error    integer;
begin  
    
   Error := -1;
   Policy := 'CTX_' || Tabla || '_' || Columna;
   -- Elimina el índice si ya existe 
   begin
     execute immediate 'drop index ' || Policy;
   exception
     when others then Error := 1;
   end;
   -- Elimina la categoría STOP LIST si existe 
   StopList :=  Tabla || '_' || Columna || '_STOP';
   begin
     ctx_ddl.drop_stopList( StopList);
   exception
     when others then Error := 2;
   end;
   -- Elimina la categoría Lexer si existe 
   Lexer :=  Tabla || '_' || Columna || '_LEXER';
   begin
     ctx_ddl.drop_preference( Lexer);
   exception
     when others then Error := 3;
   end;
end DropPolicy;

procedure OptimizePolicy( Tabla in char, 
                          Columna in char) is

   Policy varchar2(64);    -- Nombre de la directiva 
   Error  integer;
begin  
    
   Error := -1;
   Policy := 'CTX_' || Tabla || '_' || Columna;

   begin
	 execute immediate 'alter index ' || Policy || ' rebuild parameters(''optimize fast'')';
   exception
     when others then Error := 1;
   end;
end OptimizePolicy;

procedure CreateKey( Tabla in char, Key in char) is

   Error  integer;
begin  

   begin
     execute immediate 'alter table ' || Tabla ||
		               ' add primary key(' || Key || ')';
   exception
     when others then Error := 1;
   end;
end CreateKey;

end archivo;
/

begin
	archivo.preferencias;
	archivo.createkey( 'ADVCTEXTCF', 'IDELEMENTOCF,IDCAMPO,ORDEN');
	archivo.createPolicy('ADVCTEXTCF','VALOR','IDELEMENTOCF,IDCAMPO,ORDEN','','-/',0);
	archivo.createkey( 'ADVCTEXTLCF', 'IDELEMENTOCF,IDCAMPO,ORDEN');
	archivo.createPolicy('ADVCTEXTLCF','VALOR','IDELEMENTOCF,IDCAMPO,ORDEN','','-/',0);
	archivo.createkey( 'ADVCTEXTDESCR', 'IDDESCR, IDCAMPO, ORDEN');
	archivo.createPolicy('ADVCTEXTDESCR','VALOR','IDDESCR, IDCAMPO, ORDEN','','-/',0);
	archivo.createkey( 'ADVCTEXTLDESCR', 'IDDESCR, IDCAMPO, ORDEN');
	archivo.createPolicy('ADVCTEXTLDESCR','VALOR','IDDESCR, IDCAMPO, ORDEN','','-/',0);
	archivo.createkey( 'ASGFELEMENTOCF', 'ID,TIPO');
	archivo.createPolicy('ASGFELEMENTOCF','TITULO','ID,TIPO','','-/',0);
	archivo.createkey( 'ADDESCRIPTOR', 'ID');
	archivo.createPolicy('ADDESCRIPTOR','NOMBRE','ID','','-/',0);
end;
/