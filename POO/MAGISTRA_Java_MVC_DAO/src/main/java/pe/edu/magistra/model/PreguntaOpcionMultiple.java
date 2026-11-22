package pe.edu.magistra.model;

public class PreguntaOpcionMultiple extends Pregunta {

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
