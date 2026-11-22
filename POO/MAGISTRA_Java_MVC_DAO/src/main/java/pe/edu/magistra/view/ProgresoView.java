package pe.edu.magistra.view;

import pe.edu.magistra.controller.ProgresoController;
import pe.edu.magistra.model.Progreso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProgresoView extends JFrame {

    public ProgresoView(ProgresoController c) {
        UI.base(this, "MAGISTRA - Progreso", 780, 430);
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Tema", "Intentos", "Mejor puntaje", "Promedio", "Mejor tiempo (s)"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        add(new JScrollPane(new JTable(m)));
        try {
            for (Progreso p : c.listar()) {
                m.addRow(new Object[]{p.getTema(), p.getTotalIntentos(), String.format("%.1f", p.getMejorPuntaje()), String.format("%.1f", p.getPromedioPuntaje()), p.getMejorTiempoSegundos()});
            }
        } catch (Exception e) {
            UI.error(this, "No se pudo cargar el progreso", e);
        }
    }
}
