package com.proyectofinal.jrmg.model;

import lombok.Getter;

@Getter
public enum Departamento {
    GUATEMALA("Guatemala"),
    EL_PROGRESO("El Progreso"),
    SACATEPEQUEZ("Sacatepéquez"),
    CHIMALTENANGO("Chimaltenango"),
    ESCUINTLA("Escuintla"),
    SANTA_ROSA("Santa Rosa"),
    SOLOLA("Sololá"),
    TOTONICAPAN("Totonicapán"),
    QUETZALTENANGO("Quetzaltenango"),
    SUCHITEPEQUEZ("Suchitepéquez"),
    RETALHULEU("Retalhuleu"),
    SAN_MARCOS("San Marcos"),
    HUEHUETENANGO("Huehuetenango"),
    QUICHE("Quiché"),
    BAJA_VERAPAZ("Baja Verapaz"),
    ALTA_VERAPAZ("Alta Verapaz"),
    PETEN("Petén"),
    IZABAL("Izabal"),
    ZACAPA("Zacapa"),
    CHIQUIMULA("Chiquimula"),
    JALAPA("Jalapa"),
    JUTIAPA("Jutiapa");

    private final String nombreLegible;

    Departamento(String nombreLegible) {
        this.nombreLegible = nombreLegible;
    }
}
