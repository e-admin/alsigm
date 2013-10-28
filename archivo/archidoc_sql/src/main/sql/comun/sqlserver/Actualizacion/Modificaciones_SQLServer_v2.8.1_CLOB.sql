--Proyecto: producto  Fecha Generación: Thu Jun 05 12:12:02 CEST 2008


/**************************************
Version 2.5
***************************************/


/**************************************
Version 2.5.1
***************************************/


--Ficha: 'ISAAR(CPF)'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.5.1">
  <Informacion_Campos>
    <Area> <!--Area de identificación-->
      <Id_Area_Asociada>9</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Nombre -->
          <Id>-5</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Tipo de entidad-->
          <Id>100</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_TIPOENTIDAD</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"> <!--Formas(s) autorizada(s) del nombre-->
          <Id>101</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Formas paralelas de nombre-->
          <Id>102</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Formas normalizadas del nombre según otras reglas-->
          <Id>103</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Otras formas de nombre-->
          <Id>104</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Identificadores para instituciones-->
          <Id>105</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descripción-->
      <Id_Area_Asociada>10</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Fechas de existencia-->
          <Id>106</Id>
          <Tipo>3</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"> <!--Historia-->
          <Id>107</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lugar(es)-->
          <Id>108</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras>ID_LIST_CONCEJO,ID_LIST_POBLACION,ID_LIST_GEOGRAFICO</Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Estatuto jurídico-->
          <Id>109</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Funciones, ocupaciones y actividades-->
          <Id>110</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Atribución(es) / Fuente(s) legal(es)-->
          <Id>111</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Estructura(s) interna(s) / Genealogía-->
          <Id>112</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Contexto general-->
          <Id>113</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de relaciones-->
      <Id_Area_Asociada>11</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Tabla"><!--Entidades relacionadas-->
          <Id>100</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre/Identificador-->
              <Id>114</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>3</Tipo_Referencia>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"> <!--Naturaleza de la relación-->
              <Id>115</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_NRELACION</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Descripción-->
              <Id>116</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--F. Inicio-->
              <Id>117</Id>
              <Tipo>3</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--F. Fin-->
              <Id>118</Id>
              <Tipo>3</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de control-->
      <Id_Area_Asociada>12</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Identificador del registro de la autoridad-->
          <Id>119</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"> <!--Identificador de la institución-->
          <Id>120</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Reglas y/o convenciones-->
          <Id>121</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Estado de elaboración-->
          <Id>122</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_ESTADOELAB</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nivel de detalle-->
          <Id>123</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_NDETALLE</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha creación registro autoridad-->
          <Id>124</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas y escrituras-->
          <Id>127</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fuentes-->
          <Id>128</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Notas de mantenimiento-->
          <Id>129</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Relación de instituciones, personas y familias con documentos de archivo y otros recursos-->
      <Id_Area_Asociada>13</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Tabla"><!--Recursos relacionados-->
          <Id>101</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Título-->
              <Id>130</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"> <!--Identificador-->
              <Id>131</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Tipo-->
              <Id>132</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_TIPORECURSO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Naturaleza-->
              <Id>133</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_TIPORELACION</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Fecha-->
              <Id>134</Id>
              <Tipo>3</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Clase_Generar_Automaticos/>
  <Eventos></Eventos>
</Definicion_Ficha>' WHERE  ID='5';

--Formato: 'Consulta de ficha ISAAR'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.5.1">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de identificación"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-5">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Tipo de entidad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="100">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="101">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="102">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="103">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="104">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="105">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de existencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="106">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="107">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lugar(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="108">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="109">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="110">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="111">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="112">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Contexto general:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="113">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de relaciones"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>100</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Entidades relacionadas"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Nombre/Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="114">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="115">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="116">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="117">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="118">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="119">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador de la institución:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="120">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="121">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estado de elaboración:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="122">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de detalle:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="123">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="127">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fuentes:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="128">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="129">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>101</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Recursos relacionados"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Título"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="130">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="131">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Tipo"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="132">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="133">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="134">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='9';

--Formato: 'Edición de ficha ISAAR'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.5.1">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de identificación"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-5">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Tipo de entidad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="100">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="101">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="102">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="103">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="104">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="105">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de existencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="106">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="107">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lugar(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="108">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="109">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="110">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="111">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="112">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Contexto general:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="113">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de relaciones"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>100</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Entidades relacionadas"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Nombre/Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="114">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="115">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="116">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="117">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="118">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="119">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador de la institución:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="120">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="121">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estado de elaboración:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="122">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de detalle:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="123">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="127">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fuentes:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="128">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="129">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>101</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Recursos relacionados"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Título"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="130">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="131">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Tipo"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="132">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="133">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="134">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='10';

--Formato: 'Consulta de ficha ISAAR'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.5.1">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de identificación"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-5">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Tipo de entidad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="100">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="101">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="102">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="103">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="104">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="105">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de existencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="106">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="107">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lugar(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="108">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="109">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="110">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="111">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="112">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Contexto general:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="113">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de relaciones"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>100</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Entidades relacionadas"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Nombre/Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="114">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="115">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="116">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="117">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="118">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="119">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador de la institución:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="120">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="121">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estado de elaboración:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="122">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de detalle:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="123">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de control:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Auditoría"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40002]]></Url>
          </Vinculo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="127">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fuentes:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="128">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="129">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>101</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Recursos relacionados"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Título"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="130">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="131">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Tipo"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="132">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="133">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="134">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='19';

--Formato: 'Edición de ficha ISAAR'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.5.1">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de identificación"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-5">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Tipo de entidad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="100">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="101">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="102">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="103">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="104">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="105">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de existencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="106">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="107">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lugar(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="108">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="109">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="110">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="111">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="112">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Contexto general:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="113">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de relaciones"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>100</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Entidades relacionadas"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Nombre/Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="114">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="115">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="116">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="117">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="F. Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="118">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="119">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Identificador de la institución:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="120">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="121">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Estado de elaboración:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="122">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de detalle:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="123">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Fechas de control:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Auditoría"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40002]]></Url>
          </Vinculo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="127">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fuentes:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="128">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="129">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>101</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Recursos relacionados"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Título"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="130">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Identificador"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="131">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Tipo"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="132">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Naturaleza"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="133">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="134">
                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='20';

--Clasificador: 'Ficha de Series'
UPDATE ADOCFICHACLF SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.5.1">
  <Clasificadores_Predeterminados>
    <Clasificador>
      <Nombre>Estudios de Valoración</Nombre>
      <Tipo>1</Tipo>
      <Descripcion>Clasificador para los documentos de las distintas valoraciones de la serie.</Descripcion>
    </Clasificador>
    <Clasificador>
      <Nombre>Actas de Destrucción</Nombre>
      <Tipo>1</Tipo>
      <Descripcion>Clasificador para las actas de la destrucción de unidades documentales.</Descripcion>
    </Clasificador>
  </Clasificadores_Predeterminados>
</Definicion_Ficha>' WHERE  ID='ID_FICHA_DOCS_SERIES';

UPDATE ASGTMAPDESCRUDOC SET INFO='<?xml version="1.0" encoding="ISO-8859-1" ?>
<MAP_UDOC_REL_A_DESCR Version="2.5.1">
   <DATOS_SIMPLES>
      <DATO TIPO="1" ID="14" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>
      </DATO>
		<DATO TIPO="3" ID="3" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>
      </DATO>
      <DATO TIPO="3" ID="4" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>
      </DATO>
		<DATO TIPO="5" ID="16" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>
		</DATO>
      <DATO TIPO="1" ID="15" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>
         <TRANSFORMA_VALOR>
            <VALOR ORG="S">Si</VALOR>
            <VALOR ORG="N">No</VALOR>
         </TRANSFORMA_VALOR>
      </DATO>
      <DATO TIPO="1" ID="1" TIPOPARAM="3">
         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM>
      </DATO>
   </DATOS_SIMPLES
   <DATOS_TABLA>
		<TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">
            <DATO TIPO="5" ID="9" TIPOPARAM="1">
               <PARAM>IDENTIDAD</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>
                  <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>
                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>
               </DESCRIPTOR>
            </DATO>
				<DATO TIPO="1" ID="10" TIPOPARAM="1">
               <PARAM>NUM_IDENTIDAD</PARAM>
            </DATO>
            <DATO TIPO="1" ID="11" TIPOPARAM="1">
               <PARAM>ROL</PARAM>
            </DATO>
             <DATO TIPO="1" ID="12" TIPOPARAM="1">
               <PARAM>VALIDADO_EN_TERCEROS</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
           <DATO TIPO="1" ID="51" TIPOPARAM="1">
               <PARAM>ID_EN_TERCEROS</PARAM>
            </DATO>
           <DATO TIPO="1" ID="213" TIPOPARAM="1">
               <PARAM>PRINCIPAL</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">
            <DATO TIPO="5" ID="2" TIPOPARAM="1">
               <PARAM>PAIS</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="39" TIPOPARAM="1">
               <PARAM>PROVINCIA</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
				<DATO TIPO="5" ID="40" TIPOPARAM="1">
               <PARAM>CONCEJO</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="41" TIPOPARAM="1">
               <PARAM>POBLACION</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="1" ID="42" TIPOPARAM="1">
               <PARAM>LOCALIZACION</PARAM>
            </DATO>
            <DATO TIPO="1" ID="212" TIPOPARAM="1">
               <PARAM>VALIDADO</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">
            <DATO TIPO="1" ID="5" TIPOPARAM="1">
               <PARAM>FORMATO</PARAM>
            </DATO>
            <DATO TIPO="1" ID="6" TIPOPARAM="1">
               <PARAM>TIPO</PARAM>
            </DATO>
            <DATO TIPO="4" ID="7" TIPOPARAM="1">
               <PARAM>NUM_DOCS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">
            <DATO TIPO="1" ID="19" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="43" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">
            <DATO TIPO="1" ID="49" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="50" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
   </DATOS_TABLA>
</MAP_UDOC_REL_A_DESCR>' WHERE  IDFICHA='1';

UPDATE ASGTMAPDESCRUDOC SET INFO='<?xml version="1.0" encoding="ISO-8859-1" ?>
<MAP_UDOC_REL_A_DESCR Version="2.5.1">
   <DATOS_SIMPLES>
      <DATO TIPO="1" ID="14" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>
      </DATO>
		<DATO TIPO="3" ID="3" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>
      </DATO>
      <DATO TIPO="3" ID="4" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>
      </DATO>
		<DATO TIPO="5" ID="16" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>
		</DATO>
      <DATO TIPO="1" ID="15" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>
         <TRANSFORMA_VALOR>
            <VALOR ORG="S">Si</VALOR>
            <VALOR ORG="N">No</VALOR>
         </TRANSFORMA_VALOR>
      </DATO>
      <DATO TIPO="1" ID="1" TIPOPARAM="3">
         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM>
      </DATO>
   </DATOS_SIMPLES>
   <DATOS_TABLA>
		<TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">
            <DATO TIPO="5" ID="9" TIPOPARAM="1">
               <PARAM>IDENTIDAD</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>
                  <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>
                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>
               </DESCRIPTOR>
            </DATO>
				<DATO TIPO="1" ID="10" TIPOPARAM="1">
               <PARAM>NUM_IDENTIDAD</PARAM>
            </DATO>
            <DATO TIPO="1" ID="11" TIPOPARAM="1">
               <PARAM>ROL</PARAM>
            </DATO>
             <DATO TIPO="1" ID="12" TIPOPARAM="1">
               <PARAM>VALIDADO_EN_TERCEROS</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
            <DATO TIPO="1" ID="51" TIPOPARAM="1">
               <PARAM>ID_EN_TERCEROS</PARAM>
            </DATO>
           <DATO TIPO="1" ID="213" TIPOPARAM="1">
               <PARAM>PRINCIPAL</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">
            <DATO TIPO="5" ID="2" TIPOPARAM="1">
               <PARAM>PAIS</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="39" TIPOPARAM="1">
               <PARAM>PROVINCIA</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
				<DATO TIPO="5" ID="40" TIPOPARAM="1">
               <PARAM>CONCEJO</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="41" TIPOPARAM="1">
               <PARAM>POBLACION</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="1" ID="42" TIPOPARAM="1">
               <PARAM>LOCALIZACION</PARAM>
            </DATO>
            <DATO TIPO="1" ID="212" TIPOPARAM="1">
               <PARAM>VALIDADO</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">
            <DATO TIPO="1" ID="5" TIPOPARAM="1">
               <PARAM>FORMATO</PARAM>
            </DATO>
            <DATO TIPO="1" ID="6" TIPOPARAM="1">
               <PARAM>TIPO</PARAM>
            </DATO>
            <DATO TIPO="4" ID="7" TIPOPARAM="1">
               <PARAM>NUM_DOCS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">
            <DATO TIPO="1" ID="19" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="43" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">
            <DATO TIPO="1" ID="49" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="50" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
          <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/RANGOS/RANGO">
              <DATO TIPO="1" ID="201" TIPOPARAM="1">
                <PARAM>DESDE</PARAM>
              </DATO>
              <DATO TIPO="1" ID="202" TIPOPARAM="1">
                <PARAM>HASTA</PARAM>
              </DATO>
          </FILA>
      </TABLA>
   </DATOS_TABLA>
</MAP_UDOC_REL_A_DESCR>' WHERE  IDFICHA='6';

/**************************************
Version 2.6
***************************************/


--Ficha: 'ISAD(G) Nivel de Descripción Unidad Documental'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.6">
  <Informacion_Campos>
    <Area> <!--Area de Identidad -->
      <Id_Area_Asociada>1</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Codigo Referencia -->
          <Id>-1</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"> <!--Identificacion -->
          <Id>1</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Numero expediente -->
          <Id>-2</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Especial"><!--Titulo -->
          <Id>-3</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Tabla"><!-- Emplazamiento -->
          <Id>3</Id>
          <Editable>S</Editable>
          <Informacion_Especifica>
            <Validacion>
              <Sistema_Externo>2</Sistema_Externo>
            </Validacion>
          </Informacion_Especifica>
          <Columnas>
            <Campo Tipo="Dato"><!--Pais -->
              <Id>2</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Provincia -->
              <Id>39</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Concejo -->
              <Id>40</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Poblacion -->
              <Id>41</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Localizacion -->
              <Id>42</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Validado -->
              <Id>212</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha inicial -->
          <Id>3</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha Final -->
          <Id>4</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Nivel de descripcion -->
          <Id>-4</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!--Volumen y soporte -->
          <Id>1</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Soporte -->
              <Id>6</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Formato -->
              <Id>5</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Cantidad -->
              <Id>7</Id>
              <Tipo>4</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Numerico>1</Tipo_Numerico>
                <Validacion>
                  <Rango Minimo="0" Maximo=""></Rango>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Tabla"><!--Interesados -->
          <Id>2</Id>
          <Editable>S</Editable>
           <Informacion_Especifica>
            <Validacion>
              <Sistema_Externo>1</Sistema_Externo>
            </Validacion>
          </Informacion_Especifica>
          <Columnas>
            <Campo Tipo="Dato"><!--Principal -->
              <Id>213</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Identidad -->
              <Id>9</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Numero de identidad -->
              <Id>10</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Rol -->
              <Id>11</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Validado -->
              <Id>12</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--IdTercero-->
              <Id>51</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Nombre sistema productor -->
          <Id>14</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Publicacion SAC -->
          <Id>15</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>No</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Contexto -->
      <Id_Area_Asociada>2</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Productor -->
          <Id>16</Id>
          <Tipo>5</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia institucional -->
          <Id>17</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia archivistica -->
          <Id>18</Id>
          <Tipo>2</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Ingreso por -->
          <Id>203</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Código de transferencia -->
          <Id>204</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Referencias de ingreso -->
          <Id>205</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Hoja de Entrega: -->
          <Id>206</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Alcance y Contenido -->
      <Id_Area_Asociada>3</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descripcion Contenido -->
          <Id>13</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
        </Campo>
        <Campo Tipo="Tabla"><!--Alcance y contenido -->
          <Id>4</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre del documento -->
              <Id>19</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Descripcion del documento -->
              <Id>43</Id>
              <Tipo>2</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Tabla"><!--Documentos electronicos -->
          <Id>6</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre del documento -->
              <Id>49</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Descripcion del documento -->
              <Id>50</Id>
              <Tipo>2</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->
          <Id>20</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nuevos ingresos -->
          <Id>21</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>2</Tipo_Referencia>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Condiciones de Acceso y Seguridad -->
      <Id_Area_Asociada>4</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Condiciones de acceso -->
          <Id>22</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->
          <Id>23</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas -->
          <Id>24</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->
          <Id>25</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Instrumentos descripcion -->
          <Id>26</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Documentacion Asociada -->
      <Id_Area_Asociada>5</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Existencia de originales -->
          <Id>27</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->
          <Id>28</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Existencia de copias -->
          <Id>29</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->
          <Id>30</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Unidades relacionadas -->
          <Id>31</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>2</Tipo_Referencia>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nota publicacion -->
          <Id>32</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Notas -->
      <Id_Area_Asociada>6</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Notas -->
          <Id>35</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de control de la descripcion -->
      <Id_Area_Asociada>7</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Normas utilizadas -->
          <Id>33</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>ISAD(G)</Valor_Inicial>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descriptores -->
      <Id_Area_Asociada>8</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descriptor -->
          <Id>44</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Eventos>
    <Evento>
        <Tipo>2</Tipo>
        <Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>
    </Evento>
    <Evento>
        <Tipo>2</Tipo>
        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>
    </Evento>
  </Eventos>
</Definicion_Ficha>' WHERE  ID='1';

--Ficha: 'ISAD(G) Nivel de Descripción Serie'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.6">
  <Informacion_Campos>
    <Area> <!--Area de Identidad -->
      <Id_Area_Asociada>1</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Codigo Referencia -->
          <Id>-1</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Titulo -->
          <Id>-3</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha inicial -->
          <Id>3</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha Final -->
          <Id>4</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Nivel de descripcion -->
            <Id>-4</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!--Volumen y soporte -->
          <Id>7</Id>
          <Editable>N</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Cantidad -->
              <Id>214</Id>
              <Tipo>4</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Numerico>1</Tipo_Numerico>
                <Validacion>
                    <Rango Minimo="1" Maximo="10000000"></Rango>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Soporte Documental -->
              <Id>8</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Nombre sistema productor -->
          <Id>14</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Publicacion SAC -->
          <Id>15</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>No</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Contexto -->
      <Id_Area_Asociada>2</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Tabla"><!--Productores -->
          <Id>5</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre productor -->
              <Id>34</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                      <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Fecha inicio -->
              <Id>36</Id>
              <Tipo>3</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Fecha fin -->
              <Id>37</Id>
              <Tipo>3</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Historia institucional -->
          <Id>17</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia archivistica -->
          <Id>18</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Alcance y Contenido -->
      <Id_Area_Asociada>3</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Alcance y contenido -->
          <Id>38</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Valoración Serie -->
          <Id>46</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->
          <Id>47</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Condiciones de Acceso y Seguridad -->
      <Id_Area_Asociada>4</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Condiciones de acceso -->
          <Id>22</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->
          <Id>23</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas -->
          <Id>24</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->
          <Id>25</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Instrumentos descripcion -->
          <Id>26</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Documentacion Asociada -->
      <Id_Area_Asociada>5</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Existencia de originales -->
          <Id>27</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->
          <Id>28</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Existencia de copias -->
          <Id>29</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->
          <Id>30</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nota publicacion -->
          <Id>32</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Notas -->
      <Id_Area_Asociada>6</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Notas -->
          <Id>35</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de control de la descripcion -->
      <Id_Area_Asociada>7</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Normas utilizadas -->
          <Id>33</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>ISAD(G)</Valor_Inicial>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descriptores -->
      <Id_Area_Asociada>8</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descriptor -->
          <Id>44</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosSerieImpl</Clase_Generar_Automaticos>
  <Eventos>
    <Evento>
        <Tipo>2</Tipo>
        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>
    </Evento>
  </Eventos>
</Definicion_Ficha>' WHERE  ID='2';

--Ficha: 'ISAD(G) Nivel de Descripción Sección/Función'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.6">
  <Informacion_Campos>
    <Area> <!--Area de Identidad -->
      <Id_Area_Asociada>1</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Codigo Referencia -->
          <Id>-1</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Titulo -->
          <Id>-3</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha inicial -->
          <Id>3</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha Final -->
          <Id>4</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Nivel de descripcion -->
          <Id>-4</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!--Volumen y soporte documental -->
          <Id>7</Id>
          <Editable>N</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Cantidad -->
              <Id>214</Id>
              <Tipo>4</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Soporte Documental -->
              <Id>8</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Sistemas productores -->
          <Id>14</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Publicacion SAC -->
          <Id>15</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>Si</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Contexto -->
      <Id_Area_Asociada>2</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Productor -->
          <Id>16</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia institucional -->
          <Id>17</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia archivistica -->
          <Id>18</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Alcance y Contenido -->
      <Id_Area_Asociada>3</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Alcance y contenido -->
          <Id>38</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Valoración porcentaje -->
          <Id>48</Id>
          <Tipo>4</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Numerico>2</Tipo_Numerico>
            <Validacion>
              <Rango Minimo="0" Maximo="100"></Rango>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->
          <Id>47</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>Si</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Condiciones de Acceso y Seguridad -->
      <Id_Area_Asociada>4</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Condiciones de acceso -->
          <Id>22</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->
          <Id>23</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas -->
          <Id>24</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->
          <Id>25</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Instrumentos descripcion -->
          <Id>26</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Documentacion Asociada -->
      <Id_Area_Asociada>5</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Existencia de originales -->
          <Id>27</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->
          <Id>28</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Existencia de copias -->
          <Id>29</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->
          <Id>30</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nota publicacion -->
          <Id>32</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Notas -->
      <Id_Area_Asociada>6</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Notas -->
          <Id>35</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de control de la descripcion -->
      <Id_Area_Asociada>7</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Normas utilizadas -->
          <Id>33</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>ISAD(G)</Valor_Inicial>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descriptores -->
      <Id_Area_Asociada>8</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descriptor -->
          <Id>44</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosClasificadorSeriesImpl</Clase_Generar_Automaticos>
</Definicion_Ficha>' WHERE  ID='3';

--Ficha: 'ISAD(G) Nivel de Descripción Fondo'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.6">
  <Informacion_Campos>
    <Area> <!--Area de Identidad -->
      <Id_Area_Asociada>1</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Codigo Referencia -->
          <Id>-1</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Titulo -->
          <Id>-3</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha inicial -->
          <Id>3</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha Final -->
          <Id>4</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Nivel de descripcion -->
          <Id>-4</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!--Volumen y soporte documental -->
          <Id>7</Id>
          <Editable>N</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Cantidad -->
              <Id>214</Id>
              <Tipo>4</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Soporte Documental -->
              <Id>8</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>N</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Sistemas productores -->
          <Id>14</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Publicacion SAC -->
          <Id>15</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>Si</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Contexto -->
      <Id_Area_Asociada>2</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Productor -->
          <Id>16</Id>
          <Tipo>5</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>N</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia institucional -->
          <Id>17</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia archivistica -->
          <Id>18</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Alcance y Contenido -->
      <Id_Area_Asociada>3</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Alcance y contenido -->
          <Id>38</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Valoración porcentaje -->
          <Id>48</Id>
          <Tipo>4</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Numerico>2</Tipo_Numerico>
            <Validacion>
              <Rango Minimo="0" Maximo="100"></Rango>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->
          <Id>47</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>Si</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Condiciones de Acceso y Seguridad -->
      <Id_Area_Asociada>4</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Condiciones de acceso -->
          <Id>22</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->
          <Id>23</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas -->
          <Id>24</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->
          <Id>25</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Instrumentos descripcion -->
          <Id>26</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Documentacion Asociada -->
      <Id_Area_Asociada>5</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Existencia de originales -->
          <Id>27</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->
          <Id>28</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Existencia de copias -->
          <Id>29</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->
          <Id>30</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nota publicacion -->
          <Id>32</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Notas -->
      <Id_Area_Asociada>6</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Notas -->
          <Id>35</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de control de la descripcion -->
      <Id_Area_Asociada>7</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Normas utilizadas -->
          <Id>33</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>ISAD(G)</Valor_Inicial>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descriptores -->
      <Id_Area_Asociada>8</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descriptor -->
          <Id>44</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosFondoImpl</Clase_Generar_Automaticos>
</Definicion_Ficha>' WHERE  ID='4';

--Ficha: 'ISAD(G) Nivel de Descripción Fracción de Serie'
UPDATE ADFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_Ficha Version="2.6">
  <Informacion_Campos>
    <Area> <!--Area de Identidad -->
      <Id_Area_Asociada>1</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Especial"><!--Codigo Referencia -->
          <Id>-1</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"> <!--Identificacion -->
          <Id>1</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!-- Rangos de expedientes -->
          <Id>102</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Desde -->
              <Id>201</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Hasta -->
              <Id>202</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Especial"><!--Titulo -->
          <Id>-3</Id>
          <Descripcion></Descripcion>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Tipo>1</Tipo>
        </Campo>
        <Campo Tipo="Tabla"><!-- Emplazamiento -->
          <Id>3</Id>
          <Editable>S</Editable>
          <Informacion_Especifica>
            <Validacion>
              <Sistema_Externo>2</Sistema_Externo>
            </Validacion>
          </Informacion_Especifica>
          <Columnas>
            <Campo Tipo="Dato"><!--Pais -->
              <Id>2</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Provincia -->
              <Id>39</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Concejo -->
              <Id>40</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Poblacion -->
              <Id>41</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Localizacion -->
              <Id>42</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Validado -->
              <Id>212</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha inicial -->
          <Id>3</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Fecha Final -->
          <Id>4</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
                <Validacion>
                  <Formatos>
                    <Formato Tipo="AAAA" Sep="" />
                    <Formato Tipo="MMAAAA" Sep="/" />
                    <Formato Tipo="DDMMAAAA" Sep="/" />
                    <Formato Tipo="S" Sep="" />
                  </Formatos>
                </Validacion>
              </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Especial"><!--Nivel de descripcion -->
          <Id>-4</Id>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Tabla"><!--Volumen y soporte -->
          <Id>1</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Soporte -->
              <Id>6</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Formato -->
              <Id>5</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Cantidad -->
              <Id>7</Id>
              <Tipo>4</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>S</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Numerico>1</Tipo_Numerico>
                <Validacion>
                  <Rango Minimo="0" Maximo=""></Rango>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
         <Campo Tipo="Tabla"><!--Interesados -->
          <Id>2</Id>
          <Editable>S</Editable>
           <Informacion_Especifica>
            <Validacion>
              <Sistema_Externo>1</Sistema_Externo>
            </Validacion>
          </Informacion_Especifica>
          <Columnas>
            <Campo Tipo="Dato"><!--Principal -->
              <Id>213</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>

            <Campo Tipo="Dato"><!--Identidad -->
              <Id>9</Id>
              <Tipo>5</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Tipo_Referencia>1</Tipo_Referencia>
                <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Numero de identidad -->
              <Id>10</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Rol -->
              <Id>11</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Validado -->
              <Id>12</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica>
                <Validacion>
                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
                </Validacion>
              </Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--IdTercero-->
              <Id>51</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Nombre sistema productor -->
          <Id>14</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Publicacion SAC -->
          <Id>15</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>No</Valor_Inicial>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Contexto -->
      <Id_Area_Asociada>2</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Productor -->
          <Id>16</Id>
          <Tipo>5</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>S</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
                  <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia institucional -->
          <Id>17</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Historia archivistica -->
          <Id>18</Id>
          <Tipo>2</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Ingreso por -->
          <Id>203</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Código de transferencia -->
          <Id>204</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Referencias de ingreso -->
          <Id>205</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Hoja de Entrega: -->
          <Id>206</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Alcance y Contenido -->
      <Id_Area_Asociada>3</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descripcion Contenido -->
          <Id>13</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
        </Campo>
        <Campo Tipo="Tabla"><!--Alcance y contenido -->
          <Id>4</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre del documento -->
              <Id>19</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Descripcion del documento -->
              <Id>43</Id>
              <Tipo>2</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Tabla"><!--Documentos electronicos -->
          <Id>6</Id>
          <Editable>S</Editable>
          <Columnas>
            <Campo Tipo="Dato"><!--Nombre del documento -->
              <Id>49</Id>
              <Tipo>1</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
            <Campo Tipo="Dato"><!--Descripcion del documento -->
              <Id>50</Id>
              <Tipo>2</Tipo>
              <Multivalor>S</Multivalor>
              <Editable>S</Editable>
              <Obligatorio>N</Obligatorio>
              <Informacion_Especifica></Informacion_Especifica>
              <Descripcion></Descripcion>
            </Campo>
          </Columnas>
        </Campo>
        <Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->
          <Id>20</Id>
          <Tipo>3</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Formatos>
                <Formato Tipo="AAAA" Sep="" />
                <Formato Tipo="MMAAAA" Sep="/" />
                <Formato Tipo="DDMMAAAA" Sep="/" />
                <Formato Tipo="S" Sep="" />
              </Formatos>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nuevos ingresos -->
          <Id>21</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>2</Tipo_Referencia>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Condiciones de Acceso y Seguridad -->
      <Id_Area_Asociada>4</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Condiciones de acceso -->
          <Id>22</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->
          <Id>23</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Lenguas -->
          <Id>24</Id>
          <Tipo>1</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->
          <Id>25</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Instrumentos descripcion -->
          <Id>26</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de Documentacion Asociada -->
      <Id_Area_Asociada>5</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Existencia de originales -->
          <Id>27</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->
          <Id>28</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Existencia de copias -->
          <Id>29</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Validacion>
              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->
          <Id>30</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Unidades relacionadas -->
          <Id>31</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>2</Tipo_Referencia>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
        <Campo Tipo="Dato"><!--Nota publicacion -->
          <Id>32</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de Notas -->
      <Id_Area_Asociada>6</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Notas -->
          <Id>35</Id>
          <Tipo>2</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area><!--Area de control de la descripcion -->
      <Id_Area_Asociada>7</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Normas utilizadas -->
          <Id>33</Id>
          <Tipo>1</Tipo>
          <Multivalor>N</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Valor_Inicial>ISAD(G)</Valor_Inicial>
          <Informacion_Especifica></Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
    <Area> <!--Area de descriptores -->
      <Id_Area_Asociada>8</Id_Area_Asociada>
      <Campos>
        <Campo Tipo="Dato"><!--Descriptor -->
          <Id>44</Id>
          <Tipo>5</Tipo>
          <Multivalor>S</Multivalor>
          <Editable>S</Editable>
          <Obligatorio>N</Obligatorio>
          <Informacion_Especifica>
            <Tipo_Referencia>1</Tipo_Referencia>
            <Validacion>
              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>
            </Validacion>
          </Informacion_Especifica>
          <Descripcion></Descripcion>
        </Campo>
      </Campos>
    </Area>
  </Informacion_Campos>
  <Eventos>
    <Evento>
        <Tipo>2</Tipo>
        <Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>
    </Evento>
    <Evento>
        <Tipo>2</Tipo>
        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>
    </Evento>
	<Evento>
		<Tipo>3</Tipo>
		<Clase>descripcion.model.eventos.rangos.EventoNormalizarRangosFichaImpl</Clase>
	</Evento>
	<Evento>
		<Tipo>4</Tipo>
		<Clase>descripcion.model.eventos.rangos.EventoEliminarRangosNormalizadosFichaImpl</Clase>
	</Evento>
  </Eventos>
</Definicion_Ficha>' WHERE  ID='6';

--Formato: 'Consulta de ficha de unidad documental'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Número de expediente:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-2">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="212">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='1';

--Formato: 'Edición de ficha de unidad documental'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Número de expediente:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-2">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="212">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='2';

--Formato: 'Consulta de ficha de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistema Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>5</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Productores"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Productor"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="34">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="36">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="37">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="46">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='3';

--Formato: 'Edición de ficha de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistema Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>5</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Productores"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Productor"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="34">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="36">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="37">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="46">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='4';

--Formato: 'Consulta de ficha de clasificador de series'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='5';

--Formato: 'Edición de ficha de clasificador de series'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='6';

--Formato: 'Consulta de ficha de fondo'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='7';

--Formato: 'Edición de ficha de fondo'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='8';

--Formato: 'Consulta de ficha de unidad documental'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Número de expediente:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-2">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="212">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo
							Predeterminado="Relaciones de Entrega">
						</Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Control de cambios:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Auditoría"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>
					</Vinculo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='11';

--Formato: 'Edición de ficha de unidad documental'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Número de expediente:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-2">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="212">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo
							Predeterminado="Relaciones de Entrega">
						</Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Control de cambios:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Auditoría"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>
					</Vinculo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='12';

--Formato: 'Consulta de ficha de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistema Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>5</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Productores"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Productor"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="34">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="36">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="37">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="46">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=4]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='13';

--Formato: 'Edición de ficha de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistema Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Tabla">
          <Id_CampoTbl>5</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Productores"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Productor"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="34">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Inicio"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="36">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Fecha Fin"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="37">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="46">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=4]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='14';

--Formato: 'Consulta de ficha de clasificador de series'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=3]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='15';

--Formato: 'Edición de ficha de clasificador de series'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=3]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='16';

--Formato: 'Consulta de ficha de fondo'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=2]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='17';

--Formato: 'Edición de ficha de fondo'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
  <Editable>N</Editable>
  <Automaticos>S</Automaticos>
  <Elementos>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>
        <Estilo></Estilo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Código de Referencia:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-1">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Título:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-3">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha inicial:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="3">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Fecha final:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="4">
            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="-4">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="TablaTextual">
          <Id_CampoTbl>7</Id_CampoTbl>
          <Etiqueta>
            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>
          <Tabla_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Cantidad"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="214">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Soporte"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="8">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Tabla_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Sistemas Productores:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="14">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Publicación SAC:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="15">
            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de contexto"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Productor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="16">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia institucional:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="17">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Historia archivística:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="18">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Alcance y contenido:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="38">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="48">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="47">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="22">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="23">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Lenguas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="24">
            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Características físicas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="25">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="26">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de documentación asociada"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Originales"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="27">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="28">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="Cabecera">
          <Etiqueta>
            <Titulo Predeterminado="Copias"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Cabecera_Elementos>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Existencia:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="29">
                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
                <Estilo></Estilo>
              </Campo>
            </Elemento>
            <Elemento Tipo="EtiquetaDato">
              <Etiqueta>
                <Titulo Predeterminado="Descripción:"></Titulo>
                <Estilo></Estilo>
              </Etiqueta>
              <Campo Id="30">
                <Estilo></Estilo>
              </Campo>
            </Elemento>
          </Cabecera_Elementos>
        </Elemento>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Nota de publicación:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="32">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de notas"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Notas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="35">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de control de la descripción"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Normas utilizadas:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="33">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
        <Elemento Tipo="Hipervinculo">
          <Etiqueta>
            <Titulo Predeterminado="Forma de ingreso:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Vinculo>
            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>
            <Estilo></Estilo>
            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=2]]></Url>
          </Vinculo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
    <Elemento Tipo="Seccion">
      <Desplegada>S</Desplegada>
      <Etiqueta>
        <Titulo Predeterminado="Area de descriptores"></Titulo>
      </Etiqueta>
      <Seccion_Elementos>
        <Elemento Tipo="EtiquetaDato">
          <Etiqueta>
            <Titulo Predeterminado="Descriptor:"></Titulo>
            <Estilo></Estilo>
          </Etiqueta>
          <Campo Id="44">
            <Estilo></Estilo>
          </Campo>
        </Elemento>
      </Seccion_Elementos>
    </Elemento>
  </Elementos>
</Definicion_FmtFicha>' WHERE  ID='18';

--Formato: 'Consulta de ficha de fracción de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>102</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Rangos de expedientes:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Desde">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="201">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Hasta">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="202">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='21';

--Formato: 'Edición de ficha de fracción de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>102</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Rangos de expedientes:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Desde">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="201">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Hasta">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="202">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='22';

--Formato: 'Consulta de ficha de fracción de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>102</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Rangos de expedientes:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Desde">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="201">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Hasta">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="202">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo
							Predeterminado="Relaciones de Entrega">
						</Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Control de cambios:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Auditoría"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>
					</Vinculo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='23';

--Formato: 'Edición de ficha de fracción de serie'
UPDATE ADFMTFICHA SET DEFINICION='<?xml version="1.0" encoding="ISO-8859-1"?>
<Definicion_FmtFicha Version="2.6">
	<Editable>N</Editable>
	<Elementos>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de mención de la identidad">
				</Titulo>
				<Estilo></Estilo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Código de Referencia:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-1">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Título"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Asunto:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="-3">
								<Estilo></Estilo>
								<Multilinea>S</Multilinea>
							</Campo>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>102</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Rangos de expedientes:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Desde">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="201">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Hasta">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="202">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>3</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Emplazamiento:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="País"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="2">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Provincia">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="39">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Municipio">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="40">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Población">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="41">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Localización">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="42">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>2</Id_CampoTbl>
							<Etiqueta>
								<Titulo Predeterminado="Interesados:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Principal">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="213">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="9">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Num. Identidad">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="10">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo Predeterminado="Rol"></Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="11">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Validado">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="12">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="IdTercero">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="51">
										<Estilo>width:100%</Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha inicial:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="3">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Fecha final:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="4">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Nivel de Descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="-4">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="TablaTextual">
					<Id_CampoTbl>1</Id_CampoTbl>
					<Etiqueta>
						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>
					<Tabla_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Cantidad"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="7">
								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>
								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Formato"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="5">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Soporte"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="6">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Tabla_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Sistema Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="14">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Publicación SAC:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="15">
						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de contexto"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Productor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="16">
						<Estilo></Estilo>
						<!--<Iconos>
							<Icono valor="1" src="/imagenes/1.gif">
							<Iconos>-->
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia institucional:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="17">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Historia archivística:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="18">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Ingreso por:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="203">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Código de transferencia:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="204">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Referencias de ingreso:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="205">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo
									Predeterminado="Hoja de Entrega:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="206">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Descripción Contenido:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="13">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Alcance y contenido"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>4</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Físicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="19">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="43">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
						<Elemento Tipo="Tabla">
							<Id_CampoTbl>6</Id_CampoTbl>
							<Etiqueta>
								<Titulo
									Predeterminado="Documentos Electrónicos:">
								</Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Tabla_Elementos>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Nombre">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="49">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
								<Elemento Tipo="EtiquetaDato">
									<Etiqueta>
										<Titulo
											Predeterminado="Descripción">
										</Titulo>
										<Estilo></Estilo>
									</Etiqueta>
									<Campo Id="50">
										<Estilo></Estilo>
									</Campo>
								</Elemento>
							</Tabla_Elementos>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Archivos Digitales:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Archivos"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>
						<Target>_top</Target>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Valoración, selección, eliminación:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="20">
						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="21">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de condiciones de acceso y seguridad">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de acceso:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="22">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Condiciones de reproducción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="23">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Lenguas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="24">
						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Características físicas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="25">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Instrumentos de descripción:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="26">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de documentación asociada">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Originales"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="27">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="28">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="Cabecera">
					<Etiqueta>
						<Titulo Predeterminado="Copias"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Cabecera_Elementos>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Existencia:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="29">
								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>
								<Estilo></Estilo>
							</Campo>
						</Elemento>
						<Elemento Tipo="EtiquetaDato">
							<Etiqueta>
								<Titulo Predeterminado="Descripción:"></Titulo>
								<Estilo></Estilo>
							</Etiqueta>
							<Campo Id="30">
								<Estilo></Estilo>
							</Campo>
						</Elemento>
					</Cabecera_Elementos>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo
							Predeterminado="Unidades relacionadas:">
						</Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="31">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Nota de publicación:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="32">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de notas"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Notas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="35">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo
					Predeterminado="Area de control de la descripción">
				</Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Normas utilizadas:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="33">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Forma de ingreso:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo
							Predeterminado="Relaciones de Entrega">
						</Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>
					</Vinculo>
				</Elemento>
				<Elemento Tipo="Hipervinculo">
					<Etiqueta>
						<Titulo Predeterminado="Control de cambios:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Vinculo>
						<Titulo Predeterminado="Auditoría"></Titulo>
						<Estilo></Estilo>
						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>
					</Vinculo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
		<Elemento Tipo="Seccion">
			<Desplegada>S</Desplegada>
			<Etiqueta>
				<Titulo Predeterminado="Area de descriptores"></Titulo>
			</Etiqueta>
			<Seccion_Elementos>
				<Elemento Tipo="EtiquetaDato">
					<Etiqueta>
						<Titulo Predeterminado="Descriptor:"></Titulo>
						<Estilo></Estilo>
					</Etiqueta>
					<Campo Id="44">
						<Estilo></Estilo>
					</Campo>
				</Elemento>
			</Seccion_Elementos>
		</Elemento>
	</Elementos>
</Definicion_FmtFicha>' WHERE  ID='24';

/**************************************
Version 2.7
***************************************/


/**************************************
Version 2.8
***************************************/
