package pe.edu.magistra.controller;

import pe.edu.magistra.dao.*;
import pe.edu.magistra.model.*;
import pe.edu.magistra.util.Session;
import java.time.*;
import java.util.*;

public class PracticaController {

    private final PreguntaDAO pdao;
    private final IntentoDAO idao;
    private final ProgresoDAO prog;
    private Tema tema;
    private List<Pregunta> preguntas;
    private final List<RespuestaUsuario> respuestas = new ArrayList<>();
    private LocalDateTime inicio;
    private Resultado resultadoGuardado;

    public PracticaController(PreguntaDAO p, IntentoDAO i, ProgresoDAO pr) {
        pdao = p;
        idao = i;
        prog = pr;
    }

    public List<Pregunta> iniciar(Tema t, int cantidad) throws Exception {
        tema = t;
        preguntas = pdao.listarAleatoriasPorTema(t.getIdTema(), cantidad);
        respuestas.clear();
        resultadoGuardado = null;
        inicio = LocalDateTime.now();
        return preguntas;
    }

    public void responder(Pregunta q, int alt, int segundos) {
        RespuestaUsuario r = new RespuestaUsuario();
        r.setIdPregunta(q.getIdPregunta());
        r.setIdAlternativa(alt);
        r.setCorrecta(q.validarRespuesta(alt));
        r.setTiempoSegundos(segundos);
        r.setFechaRespuesta(LocalDateTime.now());
        respuestas.add(r);
    }

    public Resultado finalizar() throws Exception {
        if (resultadoGuardado == null) {
            resultadoGuardado = guardarIntento();
        }
        prog.recalcular(Session.get().getIdUsuario(), tema.getIdTema());
        return resultadoGuardado;
    }

    private Resultado guardarIntento() throws Exception {
        LocalDateTime fin = LocalDateTime.now();
        int tiempo = (int) Duration.between(inicio, fin).getSeconds();
        int ok = (int) respuestas.stream().filter(RespuestaUsuario::isCorrecta).count();
        int total = preguntas.size();
        Resultado res = new Resultado();
        res.setCorrectas(ok);
        res.setIncorrectas(total - ok);
        res.setTotalPreguntas(total);
        double porc = total == 0 ? 0 : ok * 100.0 / total;
        res.setPorcentaje(porc);
        res.setPuntaje(porc);
        res.setTiempoTotalSegundos(tiempo);
        IntentoPractica in = new IntentoPractica();
        in.setIdUsuario(Session.get().getIdUsuario());
        in.setIdTema(tema.getIdTema());
        in.setFechaInicio(inicio);
        in.setFechaFin(fin);
        in.setLimiteSegundos(3600);
        in.setTiempoTotalSegundos(tiempo);
        long id = idao.guardarCompleto(in, respuestas, res);
        res.setIdIntento(id);
        return res;
    }
}
