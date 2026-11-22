package pe.edu.magistra.model;

public class Progreso {

    private long idProgreso;
    private int idUsuario, idTema, totalIntentos;
    private double mejorPuntaje, promedioPuntaje;
    private Integer mejorTiempoSegundos;
    private String tema;

    public Progreso(long id, int u, int t, String tema, int total, double mejor, double prom, Integer tiempo) {
        idProgreso = id;
        idUsuario = u;
        idTema = t;
        this.tema = tema;
        totalIntentos = total;
        mejorPuntaje = mejor;
        promedioPuntaje = prom;
        mejorTiempoSegundos = tiempo;
    }

    public String getTema() {
        return tema;
    }

    public int getTotalIntentos() {
        return totalIntentos;
    }

    public double getMejorPuntaje() {
        return mejorPuntaje;
    }

    public double getPromedioPuntaje() {
        return promedioPuntaje;
    }

    public Integer getMejorTiempoSegundos() {
        return mejorTiempoSegundos;
    }
}
