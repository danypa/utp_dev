package pe.edu.magistra.util;

import pe.edu.magistra.model.Postulante;

public final class Session {

    private static Postulante usuario;

    private Session() {
    }

    public static void iniciar(Postulante u) {
        usuario = u;
    }

    public static Postulante get() {
        return usuario;
    }

    public static void cerrar() {
        usuario = null;
    }

    public static boolean activa() {
        return usuario != null;
    }
}
