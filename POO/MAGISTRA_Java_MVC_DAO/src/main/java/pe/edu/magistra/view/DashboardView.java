package pe.edu.magistra.view;

import pe.edu.magistra.controller.*;
import pe.edu.magistra.dao.impl.*;
import pe.edu.magistra.model.Categoria;
import pe.edu.magistra.model.Progreso;
import pe.edu.magistra.model.Tema;
import pe.edu.magistra.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashboardView extends JFrame {

    private final AuthController auth;
    private final DashboardController dashboard;
    private final ProgresoController progreso;
    private final JPanel contenido = UI.panel(new BorderLayout());
    private final Map<Categoria, JButton> accesos = new LinkedHashMap<>();
    private final JButton inicio = botonMenu("Inicio");
    private JButton activo;

    public DashboardView(AuthController auth, DashboardController dashboard) {
        this(auth, dashboard, new ProgresoController(new ProgresoDAOImpl()));
    }

    DashboardView(AuthController auth, DashboardController dashboard, ProgresoController progreso) {
        this.auth = auth;
        this.dashboard = dashboard;
        this.progreso = progreso;
        UI.base(this, "MAGISTRA · Panel de práctica", 960, 545);
        setMinimumSize(new Dimension(760, 475));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel cuerpo = UI.panel(new BorderLayout(30, 0));
        cuerpo.setBorder(UI.margen(16, 14, 18, 30));
        cuerpo.add(crearMenu(), BorderLayout.WEST);
        contenido.setBorder(UI.margen(6, 0, 10, 6));
        cuerpo.add(contenido, BorderLayout.CENTER);
        setContentPane(UI.marco("Panel de práctica", Session.get().getNombreCompleto(), cuerpo));
        mostrarInicio();
    }

    private JPanel crearMenu() {
        JPanel barra = UI.superficie(new BorderLayout(0, 10), UI.MARINO, null, 16);
        barra.setPreferredSize(new Dimension(160, 0));
        barra.setBorder(UI.margen(13, 8, 13, 8));
        JPanel enlaces = UI.panel(new GridBagLayout());
        inicio.addActionListener(e -> mostrarInicio());
        UI.fila(enlaces, inicio, 0);
        try {
            for (Categoria categoria : dashboard.categorias()) {
                JButton boton = botonMenu(categoria.getNombre());
                boton.addActionListener(e -> mostrarTemas(categoria, boton));
                accesos.put(categoria, boton);
                UI.fila(enlaces, boton, 4);
            }
        } catch (Exception e) {
            UI.error(this, "No se pudieron cargar las categorías", e);
        }
        agregarEnlace(enlaces, "Historial", () -> abrir(new HistorialView(new HistorialController(new IntentoDAOImpl()))));
        agregarEnlace(enlaces, "Progreso", () -> abrir(new ProgresoView(progreso)));
        agregarEnlace(enlaces, "Recursos", () -> abrir(new RecursosView(new RecursoController(new RecursoDAOImpl()))));
        JButton salir = botonMenu("Cerrar sesión");
        salir.setForeground(new Color(0xFFDFCE));
        salir.addActionListener(e -> {
            auth.logout();
            dispose();
            new LoginView(auth).setVisible(true);
        });
        UI.fila(enlaces, salir, 6);
        barra.add(UI.desplazar(enlaces), BorderLayout.CENTER);
        return barra;
    }

    private static JButton botonMenu(String nombre) {
        JButton boton = UI.boton(nombre.toUpperCase(Locale.ROOT), UI.Estilo.MENU);
        boton.setFont(UI.FONT.deriveFont(Font.BOLD, 12f));
        boton.setHorizontalAlignment(SwingConstants.LEADING);
        boton.setBorder(UI.margen(11, 12, 11, 10));
        return boton;
    }

    private void agregarEnlace(JPanel enlaces, String nombre, Runnable accion) {
        JButton boton = botonMenu(nombre);
        boton.addActionListener(e -> accion.run());
        UI.fila(enlaces, boton, 4);
    }

    private void abrir(JFrame ventana) {
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);
    }

    private void mostrar(JComponent pagina, JButton enlace) {
        if (activo != null) {
            activo.setSelected(false);
        }
        activo = enlace;
        activo.setSelected(true);
        contenido.removeAll();
        contenido.add(pagina);
        contenido.revalidate();
        contenido.repaint();
    }

    private JPanel cabecera(String titulo, String indicacion) {
        JPanel cabecera = UI.panel(new GridBagLayout());
        UI.fila(cabecera, UI.parrafo(titulo, 22, Font.BOLD, UI.TINTA), 0);
        UI.fila(cabecera, UI.parrafo(indicacion, 13, Font.PLAIN, UI.SECUNDARIO), 8);
        return cabecera;
    }

    private void mostrarInicio() {
        JPanel secciones = UI.panel(new GridBagLayout());
        UI.fila(secciones, cabecera("¿Qué deseas practicar hoy?", "Selecciona una categoría y el tema de tu próxima práctica."), 0);
        JPanel tarjetas = UI.panel(new GridLayout(0, accesos.size() == 1 ? 1 : 2, 18, 14));
        for (Map.Entry<Categoria, JButton> entrada : accesos.entrySet()) {
            Categoria categoria = entrada.getKey();
            JButton tarjeta = tarjetaCategoria(categoria);
            tarjeta.addActionListener(e -> mostrarTemas(categoria, entrada.getValue()));
            tarjetas.add(tarjeta);
        }
        if (accesos.isEmpty()) {
            UI.fila(secciones, UI.texto("No hay categorías disponibles.", 14, Font.PLAIN, UI.SECUNDARIO), 30);
        } else {
            UI.fila(secciones, tarjetas, 32);
        }
        UI.fila(secciones, resumenProgreso(), 24);
        mostrar(UI.desplazar(secciones), inicio);
    }

    private static String nombreCategoria(Categoria categoria) {
        String nombre = categoria.getNombre();
        if ("Ascenso".equalsIgnoreCase(nombre) || "Nombramiento".equalsIgnoreCase(nombre)) {
            return nombre + " docente";
        }
        return nombre;
    }

    private JButton tarjetaCategoria(Categoria categoria) {
        JButton tarjeta = UI.boton("", UI.Estilo.TARJETA);
        tarjeta.setLayout(new BorderLayout(0, 10));
        tarjeta.setBorder(UI.margen(16, 18, 14, 18));
        tarjeta.getAccessibleContext().setAccessibleName("Ver temas de " + categoria.getNombre());

        JPanel etiqueta = UI.superficie(new BorderLayout(), UI.AZUL_CLARO, null, 12);
        etiqueta.setBorder(UI.margen(5, 10, 5, 10));
        etiqueta.add(UI.texto("CATEGORÍA", 11, Font.BOLD, UI.AZUL));
        JPanel filaEtiqueta = UI.panel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        filaEtiqueta.add(etiqueta);

        JPanel datos = UI.panel(new GridBagLayout());
        UI.fila(datos, filaEtiqueta, 0);
        UI.fila(datos, UI.parrafo(nombreCategoria(categoria).toUpperCase(Locale.ROOT), 16, Font.BOLD, UI.TINTA), 14);
        UI.fila(datos, UI.parrafo(categoria.getDescripcion(), 12, Font.PLAIN, UI.SECUNDARIO), 6);
        tarjeta.add(datos, BorderLayout.NORTH);
        tarjeta.add(UI.parrafo("Ver temas disponibles  →", 13, Font.BOLD, UI.AZUL), BorderLayout.SOUTH);
        return tarjeta;
    }

    private JPanel resumenProgreso() {
        JPanel resumen = UI.superficie(new GridLayout(1, 0, 12, 0), new Color(0xFFF4E8), new Color(0xF1D1AD), 15);
        resumen.setBorder(UI.margen(10, 18, 10, 18));
        try {
            int intentos = 0;
            double mejor = 0;
            double sumaPuntajes = 0;
            for (Progreso tema : progreso.listar()) {
                intentos += tema.getTotalIntentos();
                mejor = Math.max(mejor, tema.getMejorPuntaje());
                sumaPuntajes += tema.getPromedioPuntaje() * tema.getTotalIntentos();
            }
            resumen.add(UI.texto("Tu progreso", 14, Font.BOLD, UI.TINTA));
            resumen.add(indicador("Intentos", Integer.toString(intentos)));
            resumen.add(indicador("Mejor puntaje", intentos == 0 ? "—" : String.format("%.1f %%", mejor)));
            resumen.add(indicador("Promedio", intentos == 0 ? "—" : String.format("%.1f %%", sumaPuntajes / intentos)));
        } catch (Exception e) {
            resumen.add(UI.texto("No se pudo cargar tu progreso.", 14, Font.PLAIN, UI.TINTA));
            resumen.setToolTipText(e.getMessage());
        }
        return resumen;
    }

    private JPanel indicador(String nombre, String valor) {
        JPanel indicador = UI.panel(new GridLayout(2, 1));
        indicador.add(UI.texto(valor, 17, Font.BOLD, UI.TINTA));
        indicador.add(UI.texto(nombre, 12, Font.PLAIN, new Color(0x735B45)));
        return indicador;
    }

    private void mostrarTemas(Categoria categoria, JButton enlace) {
        JPanel pagina = UI.panel(new BorderLayout(0, 22));
        pagina.add(cabecera(nombreCategoria(categoria), "Elige un tema para iniciar tu práctica."), BorderLayout.NORTH);
        JPanel temas = UI.panel(new GridBagLayout());
        try {
            List<Tema> disponibles = dashboard.temas(categoria.getIdCategoria());
            for (Tema tema : disponibles) {
                JButton tarjeta = UI.boton("", UI.Estilo.TARJETA);
                tarjeta.setBorder(UI.margen(16, 18, 16, 18));
                tarjeta.setLayout(new BorderLayout(16, 0));
                tarjeta.getAccessibleContext().setAccessibleName("Practicar " + tema.getNombre());
                JPanel detalle = UI.panel(new GridBagLayout());
                UI.fila(detalle, UI.parrafo(tema.getNombre(), 16, Font.BOLD, UI.TINTA), 0);
                UI.fila(detalle, UI.parrafo(tema.getDescripcion(), 13, Font.PLAIN, UI.SECUNDARIO), 5);
                tarjeta.add(detalle, BorderLayout.CENTER);
                tarjeta.add(UI.texto("Practicar  →", 14, Font.BOLD, UI.AZUL), BorderLayout.EAST);
                tarjeta.addActionListener(e -> practicar(tema));
                UI.fila(temas, tarjeta, temas.getComponentCount() == 0 ? 0 : 10);
            }
            if (disponibles.isEmpty()) {
                UI.fila(temas, UI.texto("Aún no hay temas disponibles.", 14, Font.PLAIN, UI.SECUNDARIO), 0);
            }
        } catch (Exception e) {
            UI.error(this, "No se pudieron cargar los temas", e);
            return;
        }
        pagina.add(UI.desplazar(temas), BorderLayout.CENTER);
        mostrar(pagina, enlace);
    }

    private void practicar(Tema tema) {
        PracticaController practica = new PracticaController(new PreguntaDAOImpl(), new IntentoDAOImpl(), new ProgresoDAOImpl());
        PracticaView ventana = new PracticaView(this, practica, tema);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (isDisplayable() && inicio.isSelected()) {
                    mostrarInicio();
                }
            }
        });
        ventana.setVisible(true);
    }
}
