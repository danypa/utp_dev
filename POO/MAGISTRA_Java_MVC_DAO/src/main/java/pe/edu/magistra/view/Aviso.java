package pe.edu.magistra.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class Aviso extends JDialog {

    enum Tipo {
        ATENCION("Aviso", "!", new Color(0x855114), new Color(0xFFF1DF)),
        ERROR("Error", "×", new Color(0xA43832), new Color(0xFCECE9)),
        EXITO("Confirmación", "✓", new Color(0x206950), new Color(0xE7F4EE)),
        INFORMACION("Información", "i", UI.AZUL, UI.AZUL_CLARO);

        final String seccion, simbolo;
        final Color tinta, fondo;

        Tipo(String seccion, String simbolo, Color tinta, Color fondo) {
            this.seccion = seccion;
            this.simbolo = simbolo;
            this.tinta = tinta;
            this.fondo = fondo;
        }
    }

    static void mostrar(Component origen, Tipo tipo, String titulo, String mensaje) {
        Window propietario = null;
        if (origen instanceof Window ventana) {
            propietario = ventana;
        } else if (origen != null) {
            propietario = SwingUtilities.getWindowAncestor(origen);
        }
        Aviso aviso = new Aviso(propietario, tipo, titulo, mensaje);
        aviso.setLocationRelativeTo(origen);
        aviso.setVisible(true);
    }

    Aviso(Window propietario, Tipo tipo, String titulo, String mensaje) {
        super(propietario, "MAGISTRA · " + titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel cuerpo = UI.panel(new BorderLayout(0, 20));
        cuerpo.setBorder(UI.margen(20, 20, 16, 20));
        JPanel fila = UI.panel(new BorderLayout(14, 0));
        JPanel icono = UI.superficie(new GridBagLayout(), tipo.fondo, null, 40);
        icono.setPreferredSize(new Dimension(40, 40));
        icono.add(UI.texto(tipo.simbolo, 22, Font.BOLD, tipo.tinta));
        JPanel lateral = UI.panel(new BorderLayout());
        lateral.add(icono, BorderLayout.NORTH);
        fila.add(lateral, BorderLayout.WEST);

        JPanel textos = UI.panel(new BorderLayout(0, 8));
        textos.add(UI.texto(titulo, 18, Font.BOLD, UI.TINTA), BorderLayout.NORTH);
        textos.add(detalle(mensaje), BorderLayout.CENTER);
        fila.add(textos, BorderLayout.CENTER);
        cuerpo.add(fila, BorderLayout.CENTER);

        JButton aceptar = UI.boton("Aceptar", UI.Estilo.PRINCIPAL);
        aceptar.setBorder(UI.margen(9, 24, 9, 24));
        aceptar.addActionListener(e -> dispose());
        JPanel acciones = UI.panel(new FlowLayout(FlowLayout.TRAILING, 0, 0));
        acciones.add(aceptar);
        cuerpo.add(acciones, BorderLayout.SOUTH);

        setContentPane(UI.marco(tipo.seccion, "", cuerpo));
        getAccessibleContext().setAccessibleDescription(mensaje);
        getRootPane().setDefaultButton(aceptar);
        getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                aceptar.requestFocusInWindow();
            }
        });
        pack();
    }

    private JScrollPane detalle(String mensaje) {
        JTextArea texto = new JTextArea(mensaje);
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(UI.FONT);
        texto.setForeground(UI.SECUNDARIO);
        texto.setOpaque(false);
        texto.setBorder(null);
        texto.setMargin(new Insets(0, 0, 0, 0));
        texto.setCaretPosition(0);
        // En un mensaje de solo lectura, Enter debe activar el botón predeterminado.
        texto.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none");
        int ancho = 324;
        texto.setSize(ancho, Short.MAX_VALUE);
        int alto = Math.min(texto.getPreferredSize().height, 160);
        JScrollPane lectura = new JScrollPane(texto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lectura.setBorder(null);
        lectura.setViewportBorder(null);
        lectura.setOpaque(false);
        lectura.getViewport().setOpaque(false);
        lectura.setPreferredSize(new Dimension(ancho, alto));
        return lectura;
    }
}
