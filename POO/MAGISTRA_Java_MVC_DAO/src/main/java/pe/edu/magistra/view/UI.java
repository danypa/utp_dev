package pe.edu.magistra.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

final class UI {

    static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    static final Color TINTA = new Color(0x253159);
    static final Color SECUNDARIO = new Color(0x526584);
    static final Color MARINO = new Color(0x27315B);
    static final Color AZUL = new Color(0x3069CE);
    static final Color AZUL_CLARO = new Color(0xEAF2FF);
    static final Color LINEA = new Color(0xCAD7E9);
    private static final Color FONDO = new Color(0xF4F7FB);

    enum Estilo {
        MENU(MARINO, null, new Color(0x3C4A79), null, 10),
        TARJETA(Color.WHITE, new Color(0xABC2E7), new Color(0xF2F7FF), AZUL, 25),
        OPCION(new Color(0xF5F7FB), LINEA, AZUL_CLARO, AZUL, 10),
        PRINCIPAL(AZUL, null, new Color(0x2458B2), null, 14);

        final Color fondo, borde, resaltado, bordeActivo;
        final int arco;

        Estilo(Color fondo, Color borde, Color resaltado, Color bordeActivo, int arco) {
            this.fondo = fondo;
            this.borde = borde;
            this.resaltado = resaltado;
            this.bordeActivo = bordeActivo;
            this.arco = arco;
        }

        void pintar(Graphics g, AbstractButton boton) {
            ButtonModel modelo = boton.getModel();
            boolean activo = modelo.isSelected() || modelo.isPressed();
            Color relleno = activo || modelo.isRollover() ? resaltado : fondo;
            Color contorno = activo ? bordeActivo : borde;
            if (!boton.isEnabled() && this == PRINCIPAL) {
                relleno = new Color(0xDEE6F3);
            }
            if (boton.hasFocus()) {
                contorno = this == MENU || this == PRINCIPAL ? new Color(0xA9C9FF) : AZUL;
            }
            redondear(g, boton, relleno, contorno, arco, activo || boton.hasFocus() ? 2 : 1);
        }
    }

    static void base(JFrame f, String title, int w, int h) {
        f.setTitle(title);
        f.setSize(w, h);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    static JButton btn(String t) {
        JButton b = new JButton(t);
        b.setFont(FONT);
        return b;
    }

    static JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(FONT);
        return l;
    }

    static Border margen(int arriba, int izquierda, int abajo, int derecha) {
        return BorderFactory.createEmptyBorder(arriba, izquierda, abajo, derecha);
    }

    static JLabel texto(String valor, int puntos, int estilo, Color color) {
        JLabel etiqueta = new JLabel();
        etiqueta.putClientProperty("html.disable", true);
        etiqueta.setText(valor);
        etiqueta.setFont(FONT.deriveFont(estilo, (float) puntos));
        etiqueta.setForeground(color);
        return etiqueta;
    }

    static JTextArea parrafo(String valor, int puntos, int estilo, Color color) {
        JTextArea texto = new JTextArea(valor) {
            @Override
            public boolean contains(int x, int y) {
                // El texto envuelto en una tarjeta deja pasar el clic al botón.
                return false;
            }
        };
        texto.setFont(FONT.deriveFont(estilo, (float) puntos));
        texto.setForeground(color);
        texto.setEditable(false);
        texto.setFocusable(false);
        texto.setOpaque(false);
        texto.setBorder(null);
        texto.setMargin(new Insets(0, 0, 0, 0));
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        return texto;
    }

    static JPanel panel(LayoutManager distribucion) {
        JPanel panel = new JPanel(distribucion);
        panel.setOpaque(false);
        return panel;
    }

    static JPanel superficie(LayoutManager distribucion, Color fondo, Color borde, int arco) {
        JPanel panel = new JPanel(distribucion) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                redondear(g, this, fondo, borde, arco, 1);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    static JPanel campo(String nombre, JTextField entrada) {
        JPanel grupo = panel(new BorderLayout(0, 5));
        JLabel etiqueta = texto(nombre, 13, Font.BOLD, TINTA);
        etiqueta.setLabelFor(entrada);
        grupo.add(etiqueta, BorderLayout.NORTH);
        JPanel contorno = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boolean activo = entrada.isFocusOwner();
                redondear(g, this, Color.WHITE, activo ? AZUL : LINEA, 12, activo ? 2 : 1);
            }
        };
        contorno.setOpaque(false);
        entrada.setOpaque(false);
        entrada.setBorder(margen(10, 13, 10, 13));
        entrada.setFont(FONT);
        entrada.setForeground(TINTA);
        entrada.setCaretColor(TINTA);
        entrada.setSelectionColor(AZUL_CLARO);
        entrada.setSelectedTextColor(TINTA);
        entrada.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                contorno.repaint();
                grupo.scrollRectToVisible(new Rectangle(grupo.getSize()));
            }

            @Override
            public void focusLost(FocusEvent e) {
                contorno.repaint();
            }
        });
        contorno.add(entrada);
        grupo.add(contorno, BorderLayout.CENTER);
        return grupo;
    }

    private static void redondear(Graphics g, JComponent componente, Color fondo, Color borde,
                                  int arco, int grosor) {
        Graphics2D pincel = (Graphics2D) g.create();
        try {
            pincel.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            float margen = grosor / 2f;
            Shape forma = new RoundRectangle2D.Float(margen, margen, componente.getWidth() - grosor,
                    componente.getHeight() - grosor, arco, arco);
            pincel.setColor(fondo);
            pincel.fill(forma);
            if (borde != null) {
                pincel.setColor(borde);
                pincel.setStroke(new BasicStroke(grosor));
                pincel.draw(forma);
            }
        } finally {
            pincel.dispose();
        }
    }

    static JButton boton(String titulo, Estilo estilo) {
        JButton boton = new JButton(titulo) {
            @Override
            protected void paintComponent(Graphics g) {
                estilo.pintar(g, this);
                super.paintComponent(g);
            }
        };
        prepararBoton(boton);
        boton.setForeground(estilo == Estilo.TARJETA ? TINTA : Color.WHITE);
        return boton;
    }

    static JRadioButton alternativa(String texto, int id) {
        JRadioButton boton = new JRadioButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Estilo.OPCION.pintar(g, this);
            }
        };
        prepararBoton(boton);
        boton.setLayout(new BorderLayout());
        boton.setBorder(margen(7, 13, 7, 13));
        boton.add(parrafo(texto, 13, Font.PLAIN, TINTA));
        boton.setActionCommand(Integer.toString(id));
        return boton;
    }

    private static void prepararBoton(AbstractButton boton) {
        boton.setFont(FONT.deriveFont(Font.BOLD, 13f));
        boton.setBorder(margen(10, 16, 10, 16));
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setRolloverEnabled(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    static void fila(JPanel panel, JComponent elemento, int espacioSuperior) {
        GridBagConstraints posicion = new GridBagConstraints();
        posicion.gridx = 0;
        posicion.gridy = panel.getComponentCount();
        posicion.fill = GridBagConstraints.HORIZONTAL;
        posicion.weightx = 1;
        posicion.anchor = GridBagConstraints.LINE_START;
        posicion.insets.top = espacioSuperior;
        panel.add(elemento, posicion);
    }

    static JPanel lateralAcceso(String titulo, String descripcion, String accion, Runnable alPulsar) {
        JPanel lateral = superficie(new BorderLayout(0, 14), MARINO, null, 16);
        lateral.setPreferredSize(new Dimension(205, 0));
        lateral.setBorder(margen(22, 18, 18, 18));
        JPanel detalle = panel(new GridBagLayout());
        fila(detalle, parrafo(titulo, 18, Font.BOLD, Color.WHITE), 0);
        fila(detalle, parrafo(descripcion, 13, Font.PLAIN, new Color(0xDFE8FF)), 12);
        lateral.add(detalle, BorderLayout.NORTH);
        JButton boton = boton(accion, Estilo.TARJETA);
        boton.setForeground(AZUL);
        boton.addActionListener(e -> alPulsar.run());
        lateral.add(boton, BorderLayout.SOUTH);
        return lateral;
    }

    static JPanel marco(String seccion, String detalle, JComponent contenido) {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(FONDO);
        raiz.setBorder(margen(8, 14, 12, 14));
        JPanel cabecera = panel(new BorderLayout(18, 0));
        cabecera.setBorder(margen(11, 27, 14, 26));
        cabecera.add(texto("MAGISTRA  •  " + seccion, 18, Font.BOLD, TINTA), BorderLayout.LINE_START);
        if (!detalle.isBlank()) {
            JLabel usuario = texto(detalle, 13, Font.PLAIN, SECUNDARIO);
            usuario.setHorizontalAlignment(SwingConstants.TRAILING);
            usuario.setBorder(margen(0, 0, 0, 5));
            usuario.setToolTipText(detalle);
            cabecera.add(usuario, BorderLayout.CENTER);
        }
        raiz.add(cabecera, BorderLayout.NORTH);

        JPanel cuerpo = superficie(new BorderLayout(), Color.WHITE, LINEA, 30);
        cuerpo.setBorder(margen(1, 1, 1, 1));
        cuerpo.add(contenido);
        raiz.add(cuerpo, BorderLayout.CENTER);
        return raiz;
    }

    // Conserva el ancho de lectura y habilita el desplazamiento al crecer el contenido.
    private static class PanelDesplazable extends JPanel implements Scrollable {
        PanelDesplazable(JComponent contenido) {
            super(new BorderLayout());
            setOpaque(false);
            add(contenido, BorderLayout.NORTH);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override
        public int getScrollableUnitIncrement(Rectangle r, int orientacion, int direccion) { return 18; }
        @Override
        public int getScrollableBlockIncrement(Rectangle r, int orientacion, int direccion) {
            return Math.max(18, r.height - 18);
        }
        @Override
        public boolean getScrollableTracksViewportWidth() { return true; }
        @Override
        public boolean getScrollableTracksViewportHeight() {
            return getParent() instanceof JViewport vista && getPreferredSize().height <= vista.getHeight();
        }
    }

    static JScrollPane desplazar(JComponent contenido) {
        JScrollPane scroll = new JScrollPane(new PanelDesplazable(contenido), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        return scroll;
    }

    static void error(Component origen, String titulo, Exception causa) {
        String detalle = causa.getLocalizedMessage();
        Aviso.mostrar(origen, Aviso.Tipo.ERROR, titulo,
                detalle == null || detalle.isBlank() ? "Inténtalo de nuevo." : detalle);
    }
}
