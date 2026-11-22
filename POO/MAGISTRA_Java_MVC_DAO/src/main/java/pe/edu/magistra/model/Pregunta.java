package pe.edu.magistra.model;

import java.util.*;

public abstract class Pregunta {

    protected int idPregunta, idTema;
    protected String enunciado, explicacion, dificultad;
    protected boolean estado;
    protected List<Alternativa> alternativas = new ArrayList<>();

    public int getIdPregunta() {
        return idPregunta;
    }

    public int getIdTema() {
        return idTema;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> a) {
        alternativas = a;
    }

    public abstract boolean validarRespuesta(int idAlternativa);
}
