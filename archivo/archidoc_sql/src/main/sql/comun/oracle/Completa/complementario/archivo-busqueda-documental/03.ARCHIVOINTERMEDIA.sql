CREATE OR REPLACE PROCEDURE  "INTERMEDIA" AS
 BEGIN
 for idx in (select index_name iname FROM ALL_INDEXES WHERE OWNER=USER AND INDEX_TYPE='DOMAIN')
    loop
       DBMS_OUTPUT.PUT_LINE('Indexando indice: '||USER||'.'||idx.iname);
      ctx_ddl.sync_index(USER||'.'||idx.iname);
    END LOOP;

 exception
    when others then
    begin
       DBMS_OUTPUT.PUT_LINE('Error al indexar el indice');
       DBMS_OUTPUT.PUT_LINE('    - SQLCODE: '||SQLCODE);
       DBMS_OUTPUT.PUT_LINE('    - SQLERRM: '||SUBSTR(SQLERRM, 1, 200)||CHR(10));
    end;

END;
/
