package pe.edu.magistra.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;

enum TablaPracticas {
    HISTORIAL("Historial de prácticas", 1, 4,
            new String[]{"Fecha", "Tema", "Correctas", "Total", "Puntaje", "Tiempo"},
            110, 320, 92, 62, 84, 98),
    PROGRESO("Progreso por tema", 0, 2,
            new String[]{"Tema", "Intentos", "Mejor puntaje", "Promedio", "Mejor tiempo"},
            320, 90, 122, 98, 120);

    private static final Font NEGRITA = UI.FONT.deriveFont(Font.BOLD);
    private static final Border MARGEN_CELDA = UI.margen(0, 12, 0, 12);
    private static final Border FOCO_CELDA = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UI.AZUL), UI.margen(0, 11, 0, 11));

    final String titulo;
    private final int columnaTema;
    private final int columnaPuntaje;
    private final String[] columnas;
    private final int[] anchos;

    TablaPracticas(String titulo, int columnaTema, int columnaPuntaje, String[] columnas, int... anchos) {
        this.titulo = titulo;
        this.columnaTema = columnaTema;
        this.columnaPuntaje = columnaPuntaje;
        this.columnas = columnas;
        this.anchos = anchos;
    }

    DefaultTableModel crearModelo() {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    JPanel panel(DefaultTableModel modelo, String resumen, String mensajeVacio) {
        JPanel cuerpo = UI.panel(new BorderLayout());
        cuerpo.setBorder(UI.margen(18, 20, 20, 20));
        if (modelo.getRowCount() == 0) {
            JLabel aviso = UI.texto(mensajeVacio, 14, Font.PLAIN, UI.SECUNDARIO);
            aviso.setHorizontalAlignment(SwingConstants.CENTER);
            cuerpo.add(aviso);
        } else {
            cuerpo.add(listado(modelo));
        }
        return UI.marco(titulo, resumen, cuerpo);
    }

    private JScrollPane listado(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.getAccessibleContext().setAccessibleName(titulo);
        tabla.setFont(UI.FONT);
        tabla.setForeground(UI.TINTA);
        tabla.setBackground(Color.WHITE);
        tabla.setSelectionBackground(new Color(0xDCEAFF));
        tabla.setSelectionForeground(UI.TINTA);
        tabla.setRowHeight(42);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFillsViewportHeight(true);
        tabla.setDefaultRenderer(Object.class, new Celda());

        for (int i = 0; i < anchos.length; i++) {
            TableColumn columna = tabla.getColumnModel().getColumn(i);
            columna.setMinWidth(i == columnaTema ? 120 : anchos[i] - 6);
            columna.setPreferredWidth(anchos[i]);
        }

        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setPreferredSize(new Dimension(0, 38));
        cabecera.setBackground(UI.MARINO);
        JLabel etiqueta = UI.texto("", 13, Font.BOLD, Color.WHITE);
        etiqueta.setOpaque(true);
        etiqueta.setBackground(UI.MARINO);
        etiqueta.setBorder(MARGEN_CELDA);
        cabecera.setDefaultRenderer((t, valor, seleccion, foco, fila, columna) -> {
            etiqueta.setText(valor.toString());
            etiqueta.setHorizontalAlignment(alineacion(t.convertColumnIndexToModel(columna)));
            return etiqueta;
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private int alineacion(int columna) {
        return columna <= columnaTema ? SwingConstants.LEADING : SwingConstants.TRAILING;
    }

    private class Celda extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccion,
                                                       boolean foco, int fila, int columna) {
            super.getTableCellRendererComponent(tabla, valor, seleccion, foco, fila, columna);
            int columnaModelo = tabla.convertColumnIndexToModel(columna);
            boolean esPuntaje = columnaModelo == columnaPuntaje;
            if (!seleccion) {
                setBackground(fila % 2 == 0 ? Color.WHITE : UI.FONDO);
                setForeground(esPuntaje ? UI.AZUL : UI.TINTA);
            }
            setFont(esPuntaje ? NEGRITA : tabla.getFont());
            setBorder(foco ? FOCO_CELDA : MARGEN_CELDA);
            setHorizontalAlignment(alineacion(columnaModelo));
            setToolTipText(columnaModelo == columnaTema ? getText() : null);
            return this;
        }
    }
}
