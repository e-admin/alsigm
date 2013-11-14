--Actualizacion de la version 8.0.2 a la version 8.0.3 M7

-- Tablas

-- TABLA scr_distregstate
ALTER TABLE scr_distreg ADD id_dist_father int;

-- Tabla scr_distribucion_actual
CREATE TABLE scr_distribucion_actual(
    id_dist int NOT NULL,
    dist_actual text
);

ALTER TABLE scr_distribucion_actual ADD CONSTRAINT pk_scr_distribucion_actual PRIMARY KEY (id_dist);