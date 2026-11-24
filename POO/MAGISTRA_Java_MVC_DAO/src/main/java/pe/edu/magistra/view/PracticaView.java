package pe.edu.magistra.view;

import pe.edu.magistra.controller.PracticaController;
import pe.edu.magistra.model.Alternativa;
import pe.edu.magistra.model.Pregunta;
import pe.edu.magistra.model.Resultado;
import pe.edu.magistra.model.Tema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PracticaView extends JDialog {

    private final PracticaController practica;
    private final Tema tema;
    private final JLabel reloj = UI.texto("00:00:00", 17, Font.BOLD, UI.AZUL);
    private final JLabel contador = UI.texto(" ", 13, Font.BOLD, UI.AZUL);
    private final JTextArea enunciado = UI.parrafo("", 17, Font.BOLD, UI.TINTA);
    private final JPanel opciones = UI.panel(new GridBagLayout());
    private final JButton siguiente = UI.boton("SIGUIENTE", UI.Estilo.PRINCIPAL);
    private final JScrollPane desplazamiento;
    private final Timer temporizador;
    private List<Pregunta> preguntas;
    private ButtonGroup grupo;
    private int indice;
    private long inicioExamen, inicioPregunta;
    private boolean ultimaRespondida;

    public PracticaView(PracticaController practica, Tema tema) {
        this(null, practica, tema);
    }

    PracticaView(Window propietario, PracticaController practica, Tema tema) {
        super(propietario, "MAGISTRA · Práctica de examen");
        this.practica = practica;
        this.tema = tema;
        UI.prepararDialogo(this, 960, 545);
        setMinimumSize(new Dimension(620, 430));

        JPanel cuerpo = UI.panel(new BorderLayout(0, 16));
        cuerpo.setBorder(UI.margen(18, 30, 10, 36));
        JPanel detalles = UI.panel(new BorderLayout(16, 0));
        JPanel titulo = UI.panel(new GridBagLayout());
        UI.fila(titulo, UI.parrafo("Tema: " + tema.getNombre(), 16, Font.BOLD, UI.TINTA), 0);
        detalles.add(titulo, BorderLayout.CENTER);
        JPanel tiempo = UI.superficie(new BorderLayout(), UI.AZUL_CLARO, new Color(0xB6CFF7), 12);
        tiempo.setBorder(UI.margen(8, 22, 8, 22));
        tiempo.add(reloj);
        reloj.getAccessibleContext().setAccessibleName("Tiempo transcurrido");
        detalles.add(tiempo, BorderLayout.EAST);
        cuerpo.add(detalles, BorderLayout.NORTH);

        JPanel tarjeta = UI.superficie(new BorderLayout(), Color.WHITE, UI.LINEA, 18);
        tarjeta.setBorder(UI.margen(21, 21, 16, 21));
        JPanel pregunta = UI.panel(new GridBagLayout());
        UI.fila(pregunta, contador, 0);
        UI.fila(pregunta, enunciado, 10);
        UI.fila(pregunta, opciones, 20);
        desplazamiento = UI.desplazar(pregunta);
        tarjeta.add(desplazamiento);
        cuerpo.add(tarjeta, BorderLayout.CENTER);

        JPanel acciones = UI.panel(new FlowLayout(FlowLayout.TRAILING, 0, 0));
        siguiente.setPreferredSize(new Dimension(196, 38));
        siguiente.setEnabled(false);
        siguiente.addActionListener(e -> avanzar());
        acciones.add(siguiente);
        cuerpo.add(acciones, BorderLayout.SOUTH);
        setContentPane(UI.marco("Práctica de examen", "", cuerpo));
        getRootPane().setDefaultButton(siguiente);

        temporizador = new Timer(1000, e -> reloj.setText(UI.formatoTiempo(segundosDesde(inicioExamen))));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                iniciar();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                temporizador.stop();
            }
        });
    }

    private void iniciar() {
        try {
            preguntas = practica.iniciar(tema, 10);
            if (preguntas.isEmpty()) {
                throw new IllegalStateException("No hay preguntas cargadas para este tema.");
            }
            if (preguntas.stream().anyMatch(p -> p.getAlternativas().isEmpty())) {
                throw new IllegalStateException("Hay una pregunta sin alternativas en este tema.");
            }
            inicioExamen = System.nanoTime();
            mostrarPregunta();
            temporizador.start();
        } catch (Exception e) {
            UI.error(this, "No se pudo iniciar la práctica", e);
            dispose();
        }
    }

    private void mostrarPregunta() {
        Pregunta pregunta = preguntas.get(indice);
        contador.setText("Pregunta " + (indice + 1) + " de " + preguntas.size());
        enunciado.setText(pregunta.getEnunciado());
        enunciado.setCaretPosition(0);
        opciones.removeAll();
        grupo = new ButtonGroup();
        for (Alternativa alternativa : pregunta.getAlternativas()) {
            int posicion = grupo.getButtonCount();
            String letra = posicion < 26 ? String.valueOf((char) ('A' + posicion)) : Integer.toString(posicion + 1);
            JRadioButton opcion = UI.alternativa(letra + ")  " + alternativa.getTexto(), alternativa.getIdAlternativa());
            grupo.add(opcion);
            opcion.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    siguiente.setEnabled(true);
                }
            });
            UI.fila(opciones, opcion, posicion == 0 ? 0 : 5);
        }
        siguiente.setText(indice + 1 == preguntas.size() ? "FINALIZAR" : "SIGUIENTE");
        siguiente.setEnabled(false);
        opciones.revalidate();
        opciones.repaint();
        SwingUtilities.invokeLater(() -> desplazamiento.getVerticalScrollBar().setValue(0));
        inicioPregunta = System.nanoTime();
    }

    private void avanzar() {
        ButtonModel seleccion = grupo.getSelection();
        if (seleccion == null) {
            return;
        }
        if (!ultimaRespondida) {
            int respuesta = Integer.parseInt(seleccion.getActionCommand());
            practica.responder(preguntas.get(indice), respuesta, segundosDesde(inicioPregunta));
            if (indice + 1 < preguntas.size()) {
                indice++;
                mostrarPregunta();
                return;
            }
            ultimaRespondida = true;
            temporizador.stop();
            for (Component opcion : opciones.getComponents()) {
                opcion.setEnabled(false);
            }
        }
        try {
            Resultado resultado = practica.finalizar();
            siguiente.setEnabled(false);
            String mensaje = String.format("%d de %d respuestas correctas%nPuntaje: %.1f %%%nTiempo: %s",
                    resultado.getCorrectas(), resultado.getTotalPreguntas(), resultado.getPorcentaje(),
                    UI.formatoTiempo(resultado.getTiempoTotalSegundos()));
            Aviso.mostrar(this, Aviso.Tipo.INFORMACION, "Resultado de la práctica", mensaje);
            dispose();
        } catch (Exception e) {
            siguiente.setText("REINTENTAR GUARDADO");
            UI.error(this, "No se pudo finalizar la práctica", e);
        }
    }

    private static int segundosDesde(long inicio) {
        return (int) TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - inicio);
    }
}
