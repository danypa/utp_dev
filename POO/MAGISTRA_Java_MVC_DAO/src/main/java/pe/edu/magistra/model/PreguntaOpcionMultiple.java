package pe.edu.magistra.model;

import java.io.Serial;

public class PreguntaOpcionMultiple extends Pregunta {

    @Serial
    private static final long serialVersionUID = 1L;

    public PreguntaOpcionMultiple(int id, int tema, String e, String ex, String d, boolean est) {
        idPregunta = id;
        idTema = tema;
        enunciado = e;
        explicacion = ex;
        dificultad = d;
        estado = est;
    }

    @Override
    public boolean validarRespuesta(int idAlt) {
        return alternativas.stream().anyMatch(a -> a.getIdAlternativa() == idAlt && a.isCorrecta());
    }
}
