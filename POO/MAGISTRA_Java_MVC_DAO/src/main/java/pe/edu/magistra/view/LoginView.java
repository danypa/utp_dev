package pe.edu.magistra.view;

import pe.edu.magistra.controller.AuthController;
import pe.edu.magistra.controller.DashboardController;
import pe.edu.magistra.dao.impl.CategoriaDAOImpl;
import pe.edu.magistra.dao.impl.TemaDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame {

    private final AuthController auth;
    private final JTextField correo = new JTextField(24);
    private final JPasswordField contrasena = new JPasswordField(24);

    public LoginView(AuthController auth) {
        this.auth = auth;
        UI.base(this, "MAGISTRA · Iniciar sesión", 720, 422);
        setMinimumSize(new Dimension(550, 390));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton ingresar = UI.boton("Iniciar sesión", UI.Estilo.PRINCIPAL);
        ingresar.addActionListener(e -> login());
        JPanel cuerpo = UI.panel(new BorderLayout(24, 0));
        cuerpo.setBorder(UI.margen(16, 14, 18, 24));
        JPanel centro = UI.panel(new GridBagLayout());
        centro.add(formulario(ingresar));
        cuerpo.add(centro, BorderLayout.CENTER);
        cuerpo.add(UI.lateralAcceso("¿Aún no tienes cuenta?",
                "Regístrate para practicar temas de Ascenso y Nombramiento y guardar tus resultados.",
                "Registrarme", this::abrirRegistro), BorderLayout.WEST);
        setContentPane(UI.marco("Preparación docente", "", cuerpo));
        getRootPane().setDefaultButton(ingresar);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                correo.requestFocusInWindow();
            }
        });
    }

    private JPanel formulario(JButton ingresar) {
        JPanel formulario = UI.panel(new GridBagLayout());
        formulario.setFocusTraversalPolicyProvider(true);
        formulario.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
        UI.fila(formulario, UI.texto("Accede a tu cuenta", 22, Font.BOLD, UI.TINTA), 0);
        UI.fila(formulario, UI.campo("Correo electrónico", correo), 20);
        UI.fila(formulario, UI.campo("Contraseña", contrasena), 14);
        UI.fila(formulario, ingresar, 20);
        return formulario;
    }

    private void abrirRegistro() {
        new RegistroView(this, auth).setVisible(true);
    }

    private void login() {
        try {
            auth.login(correo.getText(), new String(contrasena.getPassword()));
            DashboardController dashboard = new DashboardController(new CategoriaDAOImpl(), new TemaDAOImpl());
            DashboardView panel = new DashboardView(auth, dashboard);
            panel.setLocationRelativeTo(this);
            panel.setVisible(true);
            dispose();
        } catch (IllegalArgumentException e) {
            Aviso.mostrar(this, Aviso.Tipo.ATENCION, "Revisa tus datos de acceso", e.getMessage());
        } catch (Exception e) {
            UI.error(this, "No se pudo iniciar sesión", e);
        }
    }
}
