package pe.edu.magistra.view;

import pe.edu.magistra.controller.RecursoController;
import pe.edu.magistra.model.Recurso;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.*;

public class RecursosView extends JFrame {

    private final DefaultListModel<Recurso> m = new DefaultListModel<>();
    private final JList<Recurso> l = new JList<>(m);

    public RecursosView(RecursoController c) {
        UI.base(this, "MAGISTRA - Recursos", 650, 430);
        l.setFont(UI.FONT);
        add(new JScrollPane(l), BorderLayout.CENTER);
        JButton b = UI.btn("Abrir recurso");
        add(b, BorderLayout.SOUTH);
        try {
            for (Recurso r : c.listar()) {
                m.addElement(r);
            }
        } catch (Exception e) {
            UI.error(this, "No se pudieron cargar los recursos", e);
        }
        b.addActionListener(e -> {
            Recurso r = l.getSelectedValue();
            if (r != null)try {
                Desktop.getDesktop().browse(URI.create(r.getUrl()));
            } catch (Exception ex) {
                UI.error(this, "No se pudo abrir el recurso", ex);
            }
        });
    }
}
