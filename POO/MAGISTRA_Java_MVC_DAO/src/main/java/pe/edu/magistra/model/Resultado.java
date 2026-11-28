package pe.edu.magistra.model;

import java.io.Serial;
import java.io.Serializable;

public class Resultado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long idResultado, idIntento;
    private int correctas, incorrectas, totalPreguntas, tiempoTotalSegundos;
    private double puntaje, porcentaje;

    public long getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(long v) {
        idResultado = v;
    }

    public long getIdIntento() {
        return idIntento;
    }

    public void setIdIntento(long v) {
        idIntento = v;
    }

    public int getCorrectas() {
        return correctas;
    }

    public void setCorrectas(int v) {
        correctas = v;
    }

    public int getIncorrectas() {
        return incorrectas;
    }

    public void setIncorrectas(int v) {
        incorrectas = v;
    }

    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    public void setTotalPreguntas(int v) {
        totalPreguntas = v;
    }

    public int getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(int v) {
        tiempoTotalSegundos = v;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double v) {
        puntaje = v;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double v) {
        porcentaje = v;
    }
}
