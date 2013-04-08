@echo off
rem -----------------------------------------------------------
rem --- Actualiza los campos de Tipo CLOB
rem                      VERSIÓN: 1.0
rem -----------------------------------------------------------

setlocal
if "%3"=="" GOTO PARAMS
SET US=%1
SET PW=%2
SET BD=%3

cd clobs_data

rem Definiciones de fichas                                                                                                               
..\ADfichero2CLOB xml-fichas\def_isadg_udoc.xml                				%US% %PW% %BD% ID '1' DEFINICION ADFICHA                          
..\ADfichero2CLOB xml-fichas\def_isadg_serie.xml               				%US% %PW% %BD% ID '2' DEFINICION ADFICHA                          
..\ADfichero2CLOB xml-fichas\def_isadg_clasificador_series.xml 				%US% %PW% %BD% ID '3' DEFINICION ADFICHA                          
..\ADfichero2CLOB xml-fichas\def_isadg_fondo.xml               				%US% %PW% %BD% ID '4' DEFINICION ADFICHA                          
..\ADfichero2CLOB xml-fichas\def_isaar.xml                     				%US% %PW% %BD% ID '5' DEFINICION ADFICHA                          
..\ADfichero2CLOB xml-fichas\def_isadg_fraccion_serie.xml      				%US% %PW% %BD% ID '6' DEFINICION ADFICHA                          
                                                                    			                                                                
rem Formatos de fichas                                              			                                                                
..\ADfichero2CLOB xml-formatos\fmt_isadg_udoc_consulta.xml					%US% %PW% %BD% ID '1' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_udoc.xml							%US% %PW% %BD% ID '2' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_serie.xml							%US% %PW% %BD% ID '3' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_serie.xml							%US% %PW% %BD% ID '4' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_clasificador_series.xml			%US% %PW% %BD% ID '5' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_clasificador_series.xml			%US% %PW% %BD% ID '6' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_fondo.xml							%US% %PW% %BD% ID '7' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isadg_fondo.xml							%US% %PW% %BD% ID '8' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isaar.xml								%US% %PW% %BD% ID '9' DEFINICION ADFMTFICHA                       
..\ADfichero2CLOB xml-formatos\fmt_isaar.xml								%US% %PW% %BD% ID '10' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_udoc_archivo_consulta.xml			%US% %PW% %BD% ID '11' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_udoc_archivo.xml					%US% %PW% %BD% ID '12' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_serie_archivo.xml					%US% %PW% %BD% ID '13' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_serie_archivo.xml					%US% %PW% %BD% ID '14' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_clasificador_series_archivo.xml	%US% %PW% %BD% ID '15' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_clasificador_series_archivo.xml	%US% %PW% %BD% ID '16' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fondo_archivo.xml					%US% %PW% %BD% ID '17' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fondo_archivo.xml					%US% %PW% %BD% ID '18' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isaar_archivo.xml						%US% %PW% %BD% ID '19' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isaar_archivo.xml						%US% %PW% %BD% ID '20' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fraccion_serie_consulta.xml		%US% %PW% %BD% ID '21' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fraccion_serie.xml					%US% %PW% %BD% ID '22' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fraccion_serie_archivo_consulta.xml	%US% %PW% %BD% ID '23' DEFINICION ADFMTFICHA                      
..\ADfichero2CLOB xml-formatos\fmt_isadg_fraccion_serie_archivo.xml			%US% %PW% %BD% ID '24' DEFINICION ADFMTFICHA                      
                                                                                                                                    
                                                                                                                                   
rem Clasificadores documentales                                                                                                           
..\ADfichero2CLOB xml-clasificadores\clasificador_series.xml   				%US% %PW% %BD% ID 'ID_FICHA_DOCS_SERIES' DEFINICION ADOCFICHACLF

rem Mapeos de Fichas
..\ADfichero2CLOB xml-mapeos/unidadDocumental.xml    	%US% %PW% %BD% IDFICHA '1' INFO ASGTMAPDESCRUDOC
..\ADfichero2CLOB xml-mapeos/fraccionSerie.xml       	%US% %PW% %BD% IDFICHA '6' INFO ASGTMAPDESCRUDOC




GOTO FIN


:PARAMS
REM echo.
REM echo SINTAXIS: %0 {usuario} {clave} {bd}
REM echo.
:FIN

endlocal
