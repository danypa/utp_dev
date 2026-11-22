package pe.edu.magistra.app;

import pe.edu.magistra.controller.AuthController;
import pe.edu.magistra.dao.impl.UsuarioDAOImpl;
import pe.edu.magistra.view.LoginView;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new LoginView(new AuthController(new UsuarioDAOImpl())).setVisible(true);
        });
    }
}
