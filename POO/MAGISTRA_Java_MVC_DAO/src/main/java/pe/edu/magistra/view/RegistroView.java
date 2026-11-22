package pe.edu.magistra.view;

import pe.edu.magistra.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistroView extends JFrame {

    private final AuthController auth;
    private final Runnable mostrarAcceso;
    private final JTextField nombres = new JTextField();
    private final JTextField apellidos = new JTextField();
    private final JTextField correo = new JTextField();
    private final JPasswordField contrasena = new JPasswordField();
    private final JTextField especialidad = new JTextField();
    private final JTextField region = new JTextField();

    public RegistroView(AuthController auth) {
        this(auth, () -> new LoginView(auth).setVisible(true));
    }

    RegistroView(AuthController auth, Runnable mostrarAcceso) {
        this.auth = auth;
        this.mostrarAcceso = mostrarAcceso;
        UI.base(this, "MAGISTRA · Registro", 836, 505);
        setMinimumSize(new Dimension(660, 420));

        JPanel cuerpo = UI.panel(new BorderLayout(26, 0));
        cuerpo.setBorder(UI.margen(16, 14, 18, 24));
        cuerpo.add(UI.lateralAcceso("¿Ya tienes cuenta?",
                "Ingresa con tu correo y contraseña para continuar tu práctica.",
                "Iniciar sesión", this::volverAlAcceso), BorderLayout.WEST);

        JPanel formulario = UI.panel(new BorderLayout(0, 16));
        formulario.setBorder(UI.margen(8, 0, 4, 2));
        formulario.setFocusTraversalPolicyProvider(true);
        formulario.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
        formulario.add(UI.texto("Crea tu cuenta", 22, Font.BOLD, UI.TINTA), BorderLayout.NORTH);

        JPanel campos = UI.panel(new GridBagLayout());
        UI.fila(campos, par(UI.campo("Nombres", nombres), UI.campo("Apellidos", apellidos)), 0);
        UI.fila(campos, par(UI.campo("Correo electrónico", correo), UI.campo("Contraseña", contrasena)), 14);
        String requisito = "Al menos 6 caracteres";
        contrasena.getAccessibleContext().setAccessibleDescription(requisito);
        UI.fila(campos, par(Box.createHorizontalGlue(), UI.texto(requisito, 12, Font.PLAIN, UI.SECUNDARIO)), 4);
        UI.fila(campos, par(UI.campo("Especialidad (opcional)", especialidad),
                UI.campo("Región (opcional)", region)), 14);
        formulario.add(UI.desplazar(campos), BorderLayout.CENTER);

        JButton crear = UI.boton("Crear cuenta", UI.Estilo.PRINCIPAL);
        crear.addActionListener(e -> guardar());
        formulario.add(crear, BorderLayout.SOUTH);
        cuerpo.add(formulario, BorderLayout.CENTER);
        setContentPane(UI.marco("Registro", "", cuerpo));
        getRootPane().setDefaultButton(crear);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                nombres.requestFocusInWindow();
            }
        });
    }

    private JPanel par(Component izquierda, Component derecha) {
        JPanel fila = UI.panel(new GridLayout(1, 2, 16, 0));
        fila.add(izquierda);
        fila.add(derecha);
        return fila;
    }

    private void volverAlAcceso() {
        dispose();
        mostrarAcceso.run();
    }

    private void guardar() {
        try {
            auth.registrar(nombres.getText(), apellidos.getText(), correo.getText(),
                    new String(contrasena.getPassword()), especialidad.getText(), region.getText());
        } catch (IllegalArgumentException e) {
            Aviso.mostrar(this, Aviso.Tipo.ATENCION, "Revisa los datos del registro", e.getMessage());
            return;
        } catch (Exception e) {
            UI.error(this, "No se pudo crear la cuenta", e);
            return;
        }
        Aviso.mostrar(this, Aviso.Tipo.EXITO, "Cuenta creada", "Ya puedes iniciar sesión con tu correo y contraseña.");
        volverAlAcceso();
    }
}
