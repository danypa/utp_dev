package pe.edu.magistra.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class RespuestaUsuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long idRespuesta, idIntento;
    private int idPregunta, idAlternativa, tiempoSegundos;
    private boolean correcta;
    private LocalDateTime fechaRespuesta;

    public long getIdRespuesta() {
        return idRespuesta;
    }

    public long getIdIntento() {
        return idIntento;
    }

    public void setIdIntento(long v) {
        idIntento = v;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int v) {
        idPregunta = v;
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int v) {
        idAlternativa = v;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean v) {
        correcta = v;
    }

    public int getTiempoSegundos() {
        return tiempoSegundos;
    }

    public void setTiempoSegundos(int v) {
        tiempoSegundos = v;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime v) {
        fechaRespuesta = v;
    }
}
