package pe.edu.magistra.view;

import pe.edu.magistra.controller.HistorialController;
import pe.edu.magistra.model.IntentoPractica;
import pe.edu.magistra.model.Resultado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HistorialView extends JDialog {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ROOT);

    public HistorialView(HistorialController controlador) {
        this(null, controlador);
    }

    HistorialView(Window propietario, HistorialController controlador) {
        super(propietario, "MAGISTRA · " + TablaPracticas.HISTORIAL.titulo);
        UI.prepararDialogo(this, 880, 420);
        setMinimumSize(new Dimension(640, 280));
        DefaultTableModel modelo = TablaPracticas.HISTORIAL.crearModelo();
        String resumen = "";
        String mensaje = "Tus intentos aparecerán aquí cuando finalices una práctica.";
        try {
            for (IntentoPractica intento : controlador.intentos()) {
                Resultado resultado = controlador.resultado(intento.getIdIntento()).orElse(null);
                if (resultado != null) {
                    modelo.addRow(new Object[]{
                            FECHA.format(intento.getFechaInicio()), controlador.tema(intento.getIdIntento()),
                            resultado.getCorrectas(), resultado.getTotalPreguntas(),
                            String.format("%.1f", resultado.getPuntaje()),
                            UI.formatoTiempo(resultado.getTiempoTotalSegundos())
                    });
                }
            }
            int cantidad = modelo.getRowCount();
            resumen = cantidad == 1 ? "1 intento finalizado" : cantidad + " intentos finalizados";
        } catch (Exception e) {
            mensaje = "No se pudo cargar el historial";
            UI.error(this, mensaje, e);
        }

        setContentPane(TablaPracticas.HISTORIAL.panel(modelo, resumen, mensaje));
    }
}
