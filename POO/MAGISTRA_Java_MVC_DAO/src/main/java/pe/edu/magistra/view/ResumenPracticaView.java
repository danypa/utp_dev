package pe.edu.magistra.view;

import pe.edu.magistra.model.Alternativa;
import pe.edu.magistra.model.Pregunta;
import pe.edu.magistra.model.Resultado;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

final class ResumenPracticaView extends JPanel {

    ResumenPracticaView(Resultado resultado, List<Pregunta> preguntas, Map<Integer, Integer> marcadas, JButton cerrar) {
        super(new BorderLayout(0, 12));
        setOpaque(false);
        setBorder(UI.margen(16, 22, 12, 22));

        JPanel cifras = UI.superficie(new GridLayout(1, 3, 12, 0), UI.AZUL_CLARO, null, 14);
        cifras.setBorder(UI.margen(8, 16, 8, 16));
        cifras.add(indicador("Aciertos", resultado.getCorrectas() + " de " + resultado.getTotalPreguntas()));
        cifras.add(indicador("Puntaje", String.format("%.1f %%", resultado.getPorcentaje())));
        cifras.add(indicador("Tiempo", UI.formatoTiempo(resultado.getTiempoTotalSegundos())));
        add(cifras, BorderLayout.NORTH);

        JPanel lista = UI.panel(new GridBagLayout());
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta pregunta = preguntas.get(i);
            int marcada = marcadas.getOrDefault(pregunta.getIdPregunta(), 0);
            UI.fila(lista, tarjetaPregunta(i + 1, pregunta, marcada), i == 0 ? 0 : 10);
        }
        JScrollPane revision = UI.desplazar(lista);
        revision.getAccessibleContext().setAccessibleName("Revisión de las respuestas");
        revision.setFocusable(true);
        add(revision, BorderLayout.CENTER);
        JPanel acciones = UI.panel(new FlowLayout(FlowLayout.TRAILING, 0, 0));
        acciones.add(cerrar);
        add(acciones, BorderLayout.SOUTH);
        SwingUtilities.invokeLater(revision::requestFocusInWindow);
    }

    private static JPanel indicador(String nombre, String valor) {
        JPanel indicador = UI.panel(new BorderLayout(0, 2));
        indicador.add(UI.texto(nombre, 12, Font.PLAIN, UI.SECUNDARIO), BorderLayout.NORTH);
        indicador.add(UI.texto(valor, 17, Font.BOLD, UI.TINTA), BorderLayout.CENTER);
        return indicador;
    }

    private static JPanel tarjetaPregunta(int numero, Pregunta pregunta, int idMarcada) {
        JPanel tarjeta = UI.superficie(new GridBagLayout(), Color.WHITE, UI.LINEA, 16);
        tarjeta.setBorder(UI.margen(12, 16, 14, 16));
        UI.fila(tarjeta, UI.texto("Pregunta " + numero, 12, Font.BOLD, UI.AZUL), 0);
        UI.fila(tarjeta, UI.parrafo(pregunta.getEnunciado(), 15, Font.BOLD, UI.TINTA), 4);

        List<Alternativa> opciones = pregunta.getAlternativas();
        Alternativa marcada = opciones.stream().filter(a -> a.getIdAlternativa() == idMarcada).findFirst().orElse(null);
        if (marcada != null) {
            String etiqueta = marcada.isCorrecta() ? "Tu respuesta · Correcta" : "Tu respuesta · Incorrecta";
            UI.fila(tarjeta, respuesta(opciones, marcada, etiqueta), 8);
            if (marcada.isCorrecta()) {
                return tarjeta;
            }
        } else {
            UI.fila(tarjeta, UI.texto("Sin respuesta marcada", 13, Font.PLAIN, UI.SECUNDARIO), 8);
        }

        Alternativa correcta = opciones.stream().filter(Alternativa::isCorrecta).findFirst().orElse(null);
        if (correcta != null) {
            UI.fila(tarjeta, respuesta(opciones, correcta, "Respuesta correcta"), 5);
        } else {
            UI.fila(tarjeta, UI.texto("Sin respuesta correcta registrada", 13, Font.PLAIN, UI.SECUNDARIO), 5);
        }
        return tarjeta;
    }

    private static JPanel respuesta(List<Alternativa> opciones, Alternativa opcion, String etiqueta) {
        boolean correcta = opcion.isCorrecta();
        Color tinta = correcta ? UI.VERDE : UI.ROJO;
        JPanel fila = UI.superficie(new BorderLayout(10, 0), correcta ? UI.VERDE_CLARO : UI.ROJO_CLARO, null, 12);
        fila.setBorder(UI.margen(7, 12, 8, 12));
        JLabel icono = UI.texto(correcta ? "✓" : "×", 21, Font.BOLD, tinta);
        icono.setPreferredSize(new Dimension(22, icono.getPreferredSize().height));
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setVerticalAlignment(SwingConstants.TOP);
        fila.add(icono, BorderLayout.LINE_START);

        String texto = UI.textoOpcion(opciones.indexOf(opcion), opcion.getTexto());
        JPanel detalle = UI.panel(new BorderLayout(0, 2));
        detalle.add(UI.texto(etiqueta, 12, Font.BOLD, tinta), BorderLayout.NORTH);
        detalle.add(UI.parrafo(texto, 14, Font.PLAIN, UI.TINTA), BorderLayout.CENTER);
        fila.add(detalle, BorderLayout.CENTER);
        fila.getAccessibleContext().setAccessibleName(etiqueta + ". " + texto);
        return fila;
    }
}
