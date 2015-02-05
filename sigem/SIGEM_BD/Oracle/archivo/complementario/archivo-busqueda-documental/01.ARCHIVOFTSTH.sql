CREATE OR REPLACE
PACKAGE archivo IS

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

END archivo;
/
