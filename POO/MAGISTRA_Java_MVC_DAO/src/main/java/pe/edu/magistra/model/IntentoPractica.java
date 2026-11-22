package pe.edu.magistra.model;

import java.time.LocalDateTime;

public class IntentoPractica {

    private long idIntento;
    private int idUsuario, idTema, limiteSegundos, tiempoTotalSegundos;
    private LocalDateTime fechaInicio, fechaFin;
    private String estado;

    public long getIdIntento() {
        return idIntento;
    }

    public void setIdIntento(long v) {
        idIntento = v;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int v) {
        idUsuario = v;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int v) {
        idTema = v;
    }

    public int getLimiteSegundos() {
        return limiteSegundos;
    }

    public void setLimiteSegundos(int v) {
        limiteSegundos = v;
    }

    public int getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(int v) {
        tiempoTotalSegundos = v;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime v) {
        fechaInicio = v;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime v) {
        fechaFin = v;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String v) {
        estado = v;
    }
}
