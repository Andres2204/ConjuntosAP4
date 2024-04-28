import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class MenuPrincipal extends Menu {

    private ArrayList<Profesor> tiempoCompleto, catedra, ocacional;

    public MenuPrincipal(String title) {
        super(title);
        tiempoCompleto = new ArrayList<>();
        catedra = new ArrayList<>();
        ocacional = new ArrayList<>();
    }

    @Override
    public void menu() {
        // System.out.println(inputDate());
        // String[] colores = {"rojo", "negro", "amarillo", "azul", "majenta"};
        // System.out.println(inputSelect("Selecciona un color", "Colores!", colores));

        /*
            Ingresar minimo 10 datos por cada ArrayList. (Archivo plano)

            - Un profesor de tiempo completo también puede ser de catedra
            - Un profesor Ocasional también puede ser de catedra
            - Un profesor tiempo completo también puede ser ocasional

            1- Listar y contar los profesores que son de tiempo completo solamente
            2- Listar y contar los profesores que son de catedra solamente
            3- Listar y contar los profesores que son ocasionales solamente
            4- Lista y contar el total de profesores
            5- Listar y contar de profesores de tiempo completo y a la vez que sean de catedra
            6- Listar y contar los profesores que son ocasionales y a la vez de catedra
            7- Listar y contar profesores que tengan las 3 condiciones (Catedra, completo y ocasional)
            8- Cantidad de hombre y mujeres por cada tipo de contrato
            9- Listar y contar profesores por cada facultad
            10- Adicionar otra funcion
         */

        cargarDatos();
        while (true) {
            int opt;
            try {
                opt = Integer.parseInt(
                        input("""
                                1- Listar y contar los profesores que son de tiempo completo solamente.
                                2- Listar y contar los profesores que son de catedra solamente.
                                3- Listar y contar los profesores que son ocasionales solamente.
                                4- Lista y contar el total de profesores.
                                5- Listar y contar de profesores de tiempo completo y a la vez que sean de catedra.
                                6- Listar y contar los profesores que son ocasionales y a la vez de catedra.
                                7- Listar y contar profesores que tengan las 3 condiciones (Catedra, completo y ocasional).
                                8- Cantidad de hombre y mujeres por cada tipo de contrato.
                                9- Listar y contar profesores por cada facultad.
                                10- Adicionar otra funcion.
                                0- Salir.
                                """));
            } catch (Exception e) {
                e.printStackTrace();
                // control de la excepcion.
                if (e.toString().contains("Cannot parse null string"))
                    return;
                msg("Se necesita una opcion valida.");
                continue;
            }

            switch (opt) {
                case 0: {
                    return;
                }

                case 1: // Listar y contar los profesores que son de tiempo completo solamente
                    msgScroll(mostrar(tiempoCompleto) + mostrar(catedra) + mostrar(ocacional));
                    break;

                case 2: // Listar y contar los profesores que son de catedra solamente

                    break;

                case 3: // Listar y contar los profesores que son ocasionales solamente

                    break;

                case 4: // Lista y contar el total de profesores

                    break;

                case 5: // Listar y contar de profesores de tiempo completo y a la vez que sean de catedra

                    break;

                case 6: // Listar y contar los profesores que son ocasionales y a la vez de catedra

                    break;

                case 7: // Listar y contar profesores que tengan las 3 condiciones (Catedra, completo y ocasional)

                    break;

                case 8: // Cantidad de hombre y mujeres por cada tipo de contrato

                    break;

                case 9: // Listar y contar profesores por cada facultad

                    break;

                case 10: // F(x) ?

                    break;

                default:
                    msg("Opcion invalida.");
                    break;
            }
        }
    }

    public String mostrar(ArrayList<Profesor> d) {
        Iterator<Profesor> i = d.iterator();
        StringBuilder s = new StringBuilder();
        while (i.hasNext()) {
            Profesor p = i.next();
            s.append("CC: " + p.getCC() + "\n");
            s.append("Nombre Completo: " + p.getNombreCompleto() + "\n");
            s.append("Facultad: " + p.getFacultad() + "\n");
            s.append("Titulo: " + p.getTitulo() + "\n");
            s.append("Sexo: " + p.getSexo() + "\n");
            s.append("Asignaturas Dictadas: " + p.getAsignaturasDicta() + "\n");
            s.append("Horas Dictadas: " + p.getHorasDictadas() + "\n");
            s.append("Fecha Nacimiento: " + (p.getFechaNacimiento().toLocaleString().split(",")[0]) + "\n\n");
        }
        return s.toString();
    }

    public void cargarDatos() {
        try {
            FileManager f = new FileManager("Profesores.txt");

            ArrayList<String> lineas = f.readFileArrayList();
            Iterator<String> i = lineas.iterator();

            while (i.hasNext()) {
                String[] p = i.next().split(",");
                Profesor profesor = new Profesor(
                        p[1], // String CC
                        p[2], // String nombreCompleto
                        p[4], // String facultad
                        p[5], // String titulo
                        p[3].charAt(0), // char sexo
                        Integer.parseInt(p[6]), // int asignaturasDicta
                        Integer.parseInt(p[7]), // int horasDictadas
                        new SimpleDateFormat("yyyy-MM-dd").parse(p[8]) // Date fechaNacimiento
                );

                if (p[0].contains("t")) {
                    tiempoCompleto.add(profesor);
                }
                if (p[0].contains("c")) {
                    catedra.add(profesor);
                }
                if (p[0].contains("o")) {
                    ocacional.add(profesor);
                }
                System.out.println("[+] Añadiendo a: " + (Arrays.stream(p).toList()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
