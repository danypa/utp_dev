package pe.edu.magistra.view;

import pe.edu.magistra.controller.HistorialController;
import pe.edu.magistra.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HistorialView extends JFrame {

    public HistorialView(HistorialController c) {
        UI.base(this, "MAGISTRA - Historial", 800, 430);
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Fecha", "Tema", "Correctas", "Total", "Puntaje", "Tiempo (s)"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable t = new JTable(m);
        add(new JScrollPane(t));
        try {
            for (IntentoPractica i : c.intentos()) {
                Resultado r = c.resultado(i.getIdIntento()).orElse(null);
                if (r != null) {
                    m.addRow(new Object[]{i.getFechaInicio(), c.tema(i.getIdIntento()), r.getCorrectas(), r.getTotalPreguntas(), String.format("%.1f", r.getPuntaje()), r.getTiempoTotalSegundos()});
                }
            }
        } catch (Exception e) {
            UI.error(this, "No se pudo cargar el historial", e);
        }
    }
}
