CREATE OR REPLACE
PACKAGE invesDoc IS

procedure Preferencias;
procedure CreatePolicy( Tabla in char, 
                        Columna in char, 
                        TextKey in char,
                        Vacias in char,
                        Especiales in char,
                        Lematiza in integer);
procedure DropPolicy( Tabla in char, 
                      Columna in char, 
                      Vacias in char,
                      Especiales in char);
procedure OptimizePolicy( Tabla in char, 
                          Columna in char);
procedure CreateKey( Tabla in char, Key in char);
procedure CreatePolicyFile;
procedure DropPolicyFile;
procedure OptimizePolicyFile;
procedure CreatePolicyDoc;
procedure DropPolicyDoc;
procedure OptimizePolicyDoc;

/* 
* Prodedimientos para la visualización y marcada de documentos
* en formato HTML.
*/
procedure markup( IndexName in char, Key in char, Query in char);
function lengthmark( Key in char) return integer;
function readmark( Key in char, 
                   Offset in integer, 
                   Length in integer,
                   Buffer out nocopy varchar) return integer;
procedure dropmark( Key in char);
END invesDoc;
/
