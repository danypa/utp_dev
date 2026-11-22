package pe.edu.magistra.model;

import java.time.LocalDateTime;

public class Postulante extends Usuario {

    private String especialidad, region;

    public Postulante() {
    }

    public Postulante(int id, String n, String a, String c, String ph, String esp, String reg, LocalDateTime f, boolean e) {
        super(id, n, a, c, ph, f, e);
        especialidad = esp;
        region = reg;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String v) {
        especialidad = v;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String v) {
        region = v;
    }
}
