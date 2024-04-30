import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

public class MenuPrincipal extends Menu {

    private ArrayList<Profesor> tiempoCompleto, catedra, ocacional;

    private final Operacion<Profesor> OPERACION_UNION = (s1, s2) -> {
        Set<Profesor> union = new HashSet<>(s1);
        union.addAll(new HashSet<>(s2));
        return union;
    };

    private final Operacion<Profesor> OPERACION_INTERSECION = (s1, s2) -> {
        Set<Profesor> inte = new HashSet<>(s1);
        inte.retainAll(new HashSet<>(s2));
        return inte;
    };

    private final Operacion<Profesor> OPERACION_DIFERENCIA = (s1, s2) -> {
        Set<Profesor> dif = new HashSet<>(s1);
        dif.removeAll(new HashSet<>(s2));
        return dif;
    };

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
                    /*
                    * OPERACION:
                    *   (TIEMPO_COMPLETO - CATEDRA) - OCACIONALES
                    *
                    */

                    ArrayList<Profesor> soloProfesores = diferencia(diferencia(tiempoCompleto, catedra), ocacional);
                    System.out.println(mostrar(soloProfesores));
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

                case 10: // F(x) ? -> Realizar una operacion sugerida basandose en los atributos del Docente.

                    /*
                    *
                    * Pedir datos:
                    *   Conjuntos,
                    *   Operacion
                    *
                    * Crear Condicional:
                    *   Ver si es posible utilizar numero y mayores o iguales
                    *
                    * Invocar funcion maestra.
                    */

                    // Selecionar op:

                    String[] opts = {"Union", "Intersecion", "Diferencia"};
                    String selec = (String) inputSelect("Que operacion desea realizar?", "Operacion?", opts);

                    Operacion<Profesor> OPERACION_SELECIONADA;
                    if (selec.equals(opts[0])) { // union
                        OPERACION_SELECIONADA = OPERACION_UNION;
                    } else if (selec.equals(opts[1])) { // intersecion
                        OPERACION_SELECIONADA = OPERACION_INTERSECION;
                    } else if (selec.equals(opts[2])) { // diferencia
                        OPERACION_SELECIONADA = OPERACION_DIFERENCIA;
                    } else {
                        System.out.println("[!] De donde chota se colo la opcion" + selec);
                        continue;
                    }

                    // Selecionar conjuntos.

                    // Crear condicional.


                    // para test
                    Condicion<Profesor> cond = (prof) -> {
                        if (prof.getSexo() == 'M') return true;
                        return false;
                    };

                    // INVOCAR FUNCION MAESTRA'

                    // para test
                    msgScroll(mostrar(funcionMaestra(tiempoCompleto, catedra, cond, OPERACION_SELECIONADA)));
                    break;

                default:
                    msg("Opcion invalida.");
                    break;
            }
        }
    }

    public ArrayList<Profesor> funcionMaestra(ArrayList<Profesor> c1, ArrayList<Profesor> c2, Condicion<Profesor> condicion, Operacion<Profesor> operacion) {
        Set<Profesor> filtro1 = new HashSet<>();
        for (Profesor p : c1) if (condicion.probar(p)) filtro1.add(p);

        Set<Profesor> filtro2 = new HashSet<>();
        for (Profesor p : c2) if (condicion.probar(p)) filtro2.add(p);

        return new ArrayList<>(operacion.realizar(filtro1, filtro2));
    }

    public static ArrayList<Profesor> union(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set = new HashSet<>(lists1);
        set.addAll(lista2);
        return new ArrayList<>(set);
    }


    public static ArrayList<Profesor> intersecion(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set1 = new HashSet<>(lists1);
        Set<Profesor> set2 = new HashSet<>(lista2);
        set1.retainAll(set2);
        return new ArrayList<>(set1);
    }

    public static ArrayList<Profesor> diferencia(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set1 = new HashSet<>(lists1);
        Set<Profesor> set2 = new HashSet<>(lista2);
        set1.removeAll(set2);
        return new ArrayList<>(set1);
    }

    public static boolean subconjuntoDe(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set1 = new HashSet<>(lists1);
        Set<Profesor> set2 = new HashSet<>(lista2);
        return set1.containsAll(set2);
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
