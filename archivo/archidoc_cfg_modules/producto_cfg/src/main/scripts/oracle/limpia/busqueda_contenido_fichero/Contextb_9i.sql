CREATE OR REPLACE
package body invesDoc is

procedure Preferencias is
  Error integer;
begin

   Error := -1;
   begin
      ctx_ddl.create_preference( 'INVESDOC_LEXER', 'BASIC_LEXER');
      /* Normalización de  caracteres acentuados */
      ctx_ddl.set_attribute( 'INVESDOC_LEXER', 'BASE_LETTER', 'YES');
      /* Caracteres que unen palabras */
      ctx_ddl.set_attribute( 'INVESDOC_LEXER', 'PRINTJOINS', '-/');
      /* Caracteres finalizadores de una frase */
      ctx_ddl.set_attribute( 'INVESDOC_LEXER', 'PUNCTUATIONS', '.?!:');
      /* Carácter continuador de una palabra al final de una línea */
      ctx_ddl.set_attribute( 'INVESDOC_LEXER', 'CONTINUATION', '-'); 
      ctx_ddl.set_attribute( 'INVESDOC_LEXER', 'INDEX_THEMES', 'NO'); 
   exception
      when others then Error := 1;
   end;
   begin
      /* Lematización de palabras del castellano */
      ctx_ddl.create_preference( 'INVESDOC_WORD', 'BASIC_WORDLIST');
      ctx_ddl.set_attribute( 'INVESDOC_WORD', 'STEMMER', 'SPANISH');
   exception
      when others then Error := 2;
   end;
   begin
      /* Inso filter*/
      ctx_ddl.create_preference( 'INVESDOC_FILTER', 'USER_FILTER');
      ctx_ddl.set_attribute( 'INVESDOC_FILTER', 'COMMAND', 'filter.bat');
   exception
      when others then Error := 3;
   end;
   begin
      /* Tabla utilizada para la visualización y marcado de documentos */
      execute immediate 'create table markup (query_id number, Document clob)';
   exception
      when others then Error := 4;
   end;
   /* Se ha creado esta propiedad porque no hace caso de 
      las palabras vacías por defecto. */
   begin
     ctx_ddl.create_stoplist( 'INVESDOC_STOP');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'a');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'acá');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ahí');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ajena');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ajenas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ajeno');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ajenos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'al');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'algún');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'algo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'alguna');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'algunas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'alguno');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'algunos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'allá');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'allí');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquel');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquella');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquellas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquello');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquellos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'aquí');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cómo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cada');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cierta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ciertas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cierto');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ciertos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'como');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'con');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'conmigo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'consigo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'contigo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cualquier');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cualquiera');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cualquieras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuan');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuanta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuantas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuanto');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuantos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuán');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuánta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuántas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuánto');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'cuántos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'de');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'dejar');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'del');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'demasiada');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'demasiadas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'demasiado');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'demasiados');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'demás');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'el');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ella');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ellas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ellos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'esa');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'esas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ese');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'esos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'esta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'estar');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'estas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'este');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'estos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'hacer');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'hasta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'jamás');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'junto');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'juntos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'la');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'las');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'lo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'los');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'me');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'menos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mientras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'misma');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mismas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mismo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mismos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'más');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mucha');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mucho');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchísima');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchísimas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchísimo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muchísimos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'muy');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mía');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'mío');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nada');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ni');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ninguna');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ningunas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ninguno');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ningunos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'no');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nosotras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nosotros');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nuestra');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nuestras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nuestro');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nuestros');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'nunca');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'os');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'otra');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'otras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'otro');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'otros');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'para');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'parecer');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'poca');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'pocas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'poco');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'pocos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'por');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'porque');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'que');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'querer');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'quien');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'quienes');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'quienesquiera');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'quienquiera');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'quién');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'qué');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ser');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'si');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'siempre');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'Sr');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'Sra');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'Sres');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'Sta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'suya');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'suyas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'suyo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'suyos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'sí');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'sín');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tú');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tal');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tales');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tan');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tanta');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tantas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tanto');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tantos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'te');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tener');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ti');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'toda');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'todas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'todo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'todos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tomar');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tuya');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'tuyo');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'él');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'un');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'una');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'unas');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'unos');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'usted');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'ustedes');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'varias');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'varios');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vosotras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vosotros');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vuestra');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vuestras');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vuestro');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'vuestros');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'y');
     ctx_ddl.add_stopword( 'INVESDOC_STOP', 'yo');
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

   Policy      varchar2(64);      /* Nombre de la política */
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
   /* Elimina el índice si ya existe */
   begin
     execute immediate 'drop index ' || Policy;
   exception
     when others then Error := 1;
   end;
   Lexer := 'INVESDOC_LEXER';
   StopList := 'CTXSYS.EMPTY_STOPLIST';
   WordList := 'INVESDOC_WORD';
   /* Crea la categoría GENERIC STOP LIST */
   if Vacias is not null then
     StopList :=  Tabla || '_' || Columna || '_STOP';
	 /* Elimina la preferencia si ya existe */
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
   /* Por defecto */
   Lexer := 'INVESDOC_LEXER';
   /* Crea la categoría Lexer */
   if Especiales is not null then
     Lexer :=  Tabla || '_' || Columna || '_LEXER';
     ctx_ddl.create_preference( Lexer, 'BASIC_LEXER');
     /* Normalización de  caracteres acentuados */
     ctx_ddl.set_attribute( Lexer, 'BASE_LETTER', 'YES');
     /* Caracteres que unen palabras */
     ctx_ddl.set_attribute( Lexer, 'PRINTJOINS', Especiales);
     /* Caracteres finalizadores de una frase */
     ctx_ddl.set_attribute( Lexer, 'PUNCTUATIONS', '.¿?¡!:');
     /* Carácter continuador de una palabra al final de una línea */
     ctx_ddl.set_attribute( Lexer, 'CONTINUATION', '-');  
     ctx_ddl.set_attribute( Lexer, 'INDEX_THEMES', 'NO'); 
   end if;
   /* Lematización */
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
                                                 ' sync (on commit) ' ||
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

   Policy   varchar2(64);    /* Nombre de la directiva */
   Lexer    varchar2(64);    /* Lexer asociado a la directica */
   WordList varchar2(64);    /* Word list asociado a la directiva */
   StopList varchar2(64);    /* Stop list asociado a la directiva */
   Error    integer;
begin  
    
   Error := -1;
   Policy := 'CTX_' || Tabla || '_' || Columna;
   /* Elimina el índice si ya existe */
   begin
     execute immediate 'drop index ' || Policy;
   exception
     when others then Error := 1;
   end;
   /* Elimina la categoría STOP LIST si existe */
   StopList :=  Tabla || '_' || Columna || '_STOP';
   begin
     ctx_ddl.drop_stopList( StopList);
   exception
     when others then Error := 2;
   end;
   /* Elimina la categoría Lexer si existe */
   Lexer :=  Tabla || '_' || Columna || '_LEXER';
   begin
     ctx_ddl.drop_preference( Lexer);
   exception
     when others then Error := 3;
   end;
end DropPolicy;

procedure OptimizePolicy( Tabla in char, 
                          Columna in char) is

   Policy varchar2(64);    /* Nombre de la directiva */
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

procedure CreatePolicyFile is

   Error  integer;
begin  
    
   Error := -1;
   /* Elimina el índice si ya existe */
   begin
     execute immediate 'drop index CTX_IVOLFILEFTS_PATH';
   exception
     when others then Error := 1;
   end;
   begin
     execute immediate 'create index CTX_IVOLFILEFTS_PATH' ||
                       ' on IVOLFILEFTS(PATH)' ||
		               ' indextype is ctxsys.context' ||
		               ' parameters( ''' ||
					   ' lexer INVESDOC_LEXER' ||
                                           ' sync (on commit) ' ||
					   ' stoplist INVESDOC_STOP' ||
                       ' datastore CTXSYS.FILE_DATASTORE' || 
                       ' filter CTXSYS.INSO_FILTER'')';
   exception
     when others then Error := 2;
   end;
end CreatePolicyFile;

procedure DropPolicyFile is

   Policy varchar2(64);      /* Nombre de la política */
   Error  integer;
begin  
    
   Error := -1;
   Policy := 'CTX_IVOLFILEFTS_PATH';
   /* Elimina el índice */
   begin
     execute immediate 'drop index ' || Policy;
   exception
     when others then Error := 1;
   end;
end DropPolicyFile;

procedure OptimizePolicyFile is

   Error  integer;
begin  
    
   Error := -1;
   begin
	 execute immediate 'alter index CTX_IVOLFILEFTS_PATH' || 
	                   ' rebuild parameters(''optimize fast'')';
   exception
     when others then Error := 1;
   end;
end OptimizePolicyFile;

procedure CreatePolicyDoc is

   Error  integer;
begin  
    
   Error := -1;
   /* Elimina los índice si ya existen */
   begin
     execute immediate 'drop index CTX_IDOCGLBDOCH_TITLE';
     execute immediate 'drop index CTX_IDOCGLBDOCH_AUTHOR';
     execute immediate 'drop index CTX_IDOCGLBDOCH_KEYWORDS';
   exception
     when others then Error := 1;
   end;
   begin
     execute immediate 'create index CTX_IDOCGLBDOCH_TITLE' ||
                       ' on IDOCGLBDOCH(TITLE)' ||
		               ' indextype is ctxsys.context' ||
		               ' parameters( ''' ||
					   ' lexer INVESDOC_LEXER'||
                                           ' sync (on commit) ' ||
					   ' stoplist INVESDOC_STOP'')';
     execute immediate 'create index CTX_IDOCGLBDOCH_AUTHOR' ||
                       ' on IDOCGLBDOCH(AUTHOR)' ||
		               ' indextype is ctxsys.context' ||
		               ' parameters( ''' ||
					   ' lexer INVESDOC_LEXER'||
                                           ' sync (on commit) ' ||
					   ' stoplist INVESDOC_STOP'')';
     execute immediate 'create index CTX_IDOCGLBDOCH_KEYWORDS' ||
                       ' on IDOCGLBDOCH(KEYWORDS)' ||
		               ' indextype is ctxsys.context' ||
		               ' parameters( ''' ||
					   ' lexer INVESDOC_LEXER'||
                                           ' sync (on commit) ' ||
					   ' stoplist INVESDOC_STOP'')';
   exception
     when others then Error := 2;
   end;
end CreatePolicyDoc;

procedure DropPolicyDoc is

   Error integer;
begin  
    
   Error := -1;
   /* Elimina el índice */
   begin
     execute immediate 'drop index CTX_IDOCGLBDOCH_TITLE';
     execute immediate 'drop index CTX_IDOCGLBDOCH_AUTHOR';
     execute immediate 'drop index CTX_IDOCGLBDOCH_KEYWORDS';
   exception
     when others then Error := 1;
   end;
end DropPolicyDoc;

procedure OptimizePolicyDoc is

   Error integer;
begin  
    
   Error := -1;

   begin
	 execute immediate 'alter index CTX_IDOCGLBDOCH_TITLE ' ||
	                   'rebuild parameters(''optimize fast'')';
	 execute immediate 'alter index CTX_IDOCGLBDOCH_AUTHOR ' ||
	                   'rebuild parameters(''optimize fast'')';
	 execute immediate 'alter index CTX_IDOCGLBDOCH_KEYWORDS ' ||
	                   'rebuild parameters(''optimize fast'')';
   exception
     when others then Error := 1;
   end;
end OptimizePolicyDoc;

procedure markup( IndexName in char,
                  Key in char, 
                  Query in char) is
  Error integer;
begin  

   begin
     execute immediate 'delete from markup where query_id = ' || Key;
     ctx_doc.markup( index_name => IndexName,                       
                     textkey => Key,
                     text_query => Query,
                     restab => 'markup',
                     query_id => Key,
                     tagset => 'HTML_DEFAULT',
                     starttag => '<font color=blue><b>',
                     endtag => '</b></font>');
   exception
     when others then Error := 1;
   end;
end markup;

function lengthmark( Key in char) return integer is
  Error integer;
  Doc CLOB;
  Length integer;
  sSQL varchar2(64);
begin  

  begin
    select document into Doc from markup where query_id = Key;
    Length := DBMS_LOB.GETLENGTH( Doc);
  exception
    when others then Error := 1;
  end;
  return Length;
end lengthmark;

function readmark( Key in char, 
	               Offset in integer, 
	               Length in integer,
	               Buffer out nocopy varchar) return integer is
  Error integer;
  Doc CLOB;
  Amount BINARY_INTEGER;
begin  

  begin
    Amount := Length;
    select document into Doc from markup where query_id = Key;
    DBMS_LOB.READ( Doc, Amount, Offset, Buffer);
  exception
    when others then Error := 1;
  end;
  return Amount;
end readmark;

procedure dropmark( Key in char) is
  Error integer;
begin  

  begin
     execute immediate 'delete from markup where query_id = ' || Key;
  exception
    when others then Error := 1;
  end;
end dropmark;
end invesDoc;
/
