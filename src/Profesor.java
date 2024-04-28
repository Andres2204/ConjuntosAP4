import java.util.Date;

public class Profesor {

    /* (8 vars)
    - Cedula (validar con expresión regular de solo números)
    - Nombre completo ( Validar con expresión regular solo letras)
    - Sexo (Validar lista desplegable Masculino o Femenino)
    - Facultad (Ingenieria, Deportes, Comunicación, Administracion, Idiomas, Ciencias
        Basicas, con una lista desplegable)
    - Titulo (Pregrado, Especialista, maestria, Doctorado, con lista desplegable)
    - Cantidad de asignaturas que dicta- Validar con expresion regular-rango de 1-10
    - Cantidad de horas dictadas por semana- Validar con expresion regular-rango de 1-20
    - Fecha nacimiento (Validar con expresión regular fecha valida)
    */

    private String CC, nombreCompleto, facultad, titulo;
    private char sexo;
    private int asignaturasDicta, horasDictadas;
    private Date fechaNacimiento;

    public Profesor(String CC, String nombreCompleto, String facultad, String titulo, char sexo, int asignaturasDicta, int horasDictadas, Date fechaNacimiento) {
        this.CC = CC;
        this.nombreCompleto = nombreCompleto;
        this.facultad = facultad;
        this.titulo = titulo;
        this.sexo = sexo;
        this.asignaturasDicta = asignaturasDicta;
        this.horasDictadas = horasDictadas;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getAsignaturasDicta() {
        return asignaturasDicta;
    }

    public void setAsignaturasDicta(int asignaturasDicta) {
        this.asignaturasDicta = asignaturasDicta;
    }

    public int getHorasDictadas() {
        return horasDictadas;
    }

    public void setHorasDictadas(int horasDictadas) {
        this.horasDictadas = horasDictadas;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
