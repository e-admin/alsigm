#!/bin/sh
#path donde se encuentran los scripts de postgres
SIGEM_BD_DIR=/home/sigem/tmp/BD/PostgreSQL
ARCHIVO_DS_NAME=archivo
ETRAMITACION_DS_NAME=eTramitacion
TRAMITADOR_DS_NAME=tramitador
REGISTRO_DS_NAME=registro
ADMIN_DS_NAME=sigemAdmin
SISNOT_DS_NAME=sisnot
SNTS_DS_NAME=snts
ENTIDAD=000
ENTIDAD_NAME="Entidad por defecto"
#ENTIDAD_NAME="Entidad "$ENTIDAD
TEMPLATE=--template=template_sigem

# Opciones CREATE_ADMIN_DATABASE: S/N
#CREATE_ADMIN_DATABASE=S

dropdb -U postgres $ARCHIVO_DS_NAME"_"$ENTIDAD
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $ARCHIVO_DS_NAME"_"$ENTIDAD
#comentado este script porque en la restauración desde 0 no tiene efecto ya que se ha borrado previamente con dropdb
#psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/00_drop_tables_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/01_create_tables_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/02_create_indexes_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/03_insert_data_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/04_create_functions_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/05_insert_texts_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/06_busqueda_documental/posterior_igual_8.3/06_create_documentary_search_postgres.sql
#comentado este script porque en la restauración desde 0 no tiene efecto ya que se ha borrado previamente con dropdb
#rem psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/07_drop_tables_organizacion_postgres.sql
psql -U postgres -d $ARCHIVO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/archivo/08_create_tables_organizacion_postgres.sql


dropdb -U postgres $ETRAMITACION_DS_NAME"_"$ENTIDAD
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $ETRAMITACION_DS_NAME"_"$ENTIDAD
psql -U postgres -d $ETRAMITACION_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/eTramitacion/eTramitacion.sql

dropdb -U postgres $TRAMITADOR_DS_NAME"_"$ENTIDAD
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $TRAMITADOR_DS_NAME"_"$ENTIDAD
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/01-create_sequences.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/02-create_tables.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/03-create_views.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/04-datos_iniciales.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/05.1-plantillas_iniciales.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/05.2-plantillas_prototipos.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/05.3-plantillas_prototipos_v1.9.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/06.1-datos_prototipos.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/06.2-datos_prototipos_v1.9.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/06.3-informes_estadisticos.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/07-actualizacion_permisos.sql
psql -U postgres -d $TRAMITADOR_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/tramitador/08-configuracion_publicador.sql


dropdb -U postgres $REGISTRO_DS_NAME"_"$ENTIDAD
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $REGISTRO_DS_NAME"_"$ENTIDAD
#comentado este script porque en la restauración desde 0 no tiene efecto ya que se ha borrado previamente con dropdb
#rem psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/00_drop_tables_constraints_views_sequences_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/01.1_create_tables_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/01.2_create_tables_invesdoc_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/02.1_create_indexes_constraints_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/02.2_create_indexes_constraints_invesdoc_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/03.1_insert_data_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/03.2_insert_data_invesdoc_registro_sigem_postgres.sql
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/registro/04.1_insert_clob_registro_sigem_postgres.sql
#repositorios para la maquina virtual
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/repositorios_registro_sigem_postgresql.sql
#usuario para consolidacion de la maquina virtual
psql -U postgres -d $REGISTRO_DS_NAME"_"$ENTIDAD < $SIGEM_BD_DIR/create_user_consolidacion.sql


dropdb -U postgres $ADMIN_DS_NAME
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $ADMIN_DS_NAME
psql -U postgres -d $ADMIN_DS_NAME < $SIGEM_BD_DIR/sigemAdmin/sigemAdmin.sql


dropdb -U postgres $SISNOT_DS_NAME
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $SISNOT_DS_NAME
psql -U postgres -d $SISNOT_DS_NAME < $SIGEM_BD_DIR/sisnot/sisnot.sql

dropdb -U postgres $SNTS_DS_NAME
createdb -U postgres --encoding=LATIN9 --owner=postgres $TEMPLATE $SNTS_DS_NAME
psql -U postgres -d $SNTS_DS_NAME< $SIGEM_BD_DIR/snts/snts.sql
