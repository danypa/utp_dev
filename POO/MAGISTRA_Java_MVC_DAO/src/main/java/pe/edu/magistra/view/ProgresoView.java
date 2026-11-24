package pe.edu.magistra.view;

import pe.edu.magistra.controller.ProgresoController;
import pe.edu.magistra.model.Progreso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProgresoView extends JDialog {

    public ProgresoView(ProgresoController controlador) {
        this(null, controlador);
    }

    ProgresoView(Window propietario, ProgresoController controlador) {
        super(propietario, "MAGISTRA · " + TablaPracticas.PROGRESO.titulo);
        UI.prepararDialogo(this, 880, 360);
        setMinimumSize(new Dimension(640, 280));

        DefaultTableModel modelo = TablaPracticas.PROGRESO.crearModelo();
        String resumen = "";
        String mensaje = "Tu progreso aparecerá aquí cuando finalices una práctica.";
        try {
            for (Progreso tema : controlador.listar()) {
                Integer segundos = tema.getMejorTiempoSegundos();
                modelo.addRow(new Object[]{
                        tema.getTema(), tema.getTotalIntentos(),
                        String.format("%.1f", tema.getMejorPuntaje()), String.format("%.1f", tema.getPromedioPuntaje()),
                        segundos == null ? "—" : UI.formatoTiempo(segundos)
                });
            }
            int cantidad = modelo.getRowCount();
            resumen = cantidad == 1 ? "1 tema" : cantidad + " temas";
        } catch (Exception e) {
            mensaje = "No se pudo cargar el progreso";
            UI.error(this, mensaje, e);
        }
        setContentPane(TablaPracticas.PROGRESO.panel(modelo, resumen, mensaje));
    }
}
