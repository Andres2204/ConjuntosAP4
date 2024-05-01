import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

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
         * Ingresar minimo 10 datos por cada ArrayList. (Archivo plano)
         * 
         * - Un profesor de tiempo completo también puede ser de catedra
         * - Un profesor Ocasional también puede ser de catedra
         * - Un profesor tiempo completo también puede ser ocasional
         * 
         * 1- Listar y contar los profesores que son de tiempo completo solamente
         * 2- Listar y contar los profesores que son de catedra solamente
         * 3- Listar y contar los profesores que son ocasionales solamente
         * 4- Lista y contar el total de profesores
         * 5- Listar y contar de profesores de tiempo completo y a la vez que sean de
         * catedra
         * 6- Listar y contar los profesores que son ocasionales y a la vez de catedra
         * 7- Listar y contar profesores que tengan las 3 condiciones (Catedra, completo
         * y ocasional)
         * 8- Cantidad de hombre y mujeres por cada tipo de contrato
         * 9- Listar y contar profesores por cada facultad
         * 10- Adicionar otra funcion
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
                                11- Ingresar profesor.
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
                     * (TIEMPO_COMPLETO - CATEDRA) - OCACIONALES
                     *
                     */

                    ArrayList<Profesor> soloProfesores = diferencia(diferencia(tiempoCompleto, catedra), ocacional);
                    msg("Hay " + soloProfesores.size() + " profesores que son solo de tiempo completo, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 2: // Listar y contar los profesores que son de catedra solamente
                    soloProfesores = diferencia(diferencia(catedra, tiempoCompleto), ocacional);
                    msg("Hay " + soloProfesores.size() + " profesores que son solo de catedra, y estos son...");
                    msgScroll(mostrar(soloProfesores));

                    break;

                case 3: // Listar y contar los profesores que son ocasionales solamente
                    soloProfesores = diferencia(diferencia(ocacional, tiempoCompleto), catedra);
                    msg("Hay " + soloProfesores.size() + " profesores que son solo ocacionales, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 4: // Lista y contar el total de profesores
                    soloProfesores = union(union(tiempoCompleto, ocacional), catedra);
                    msg("Hay " + soloProfesores.size() + " profesores en total, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 5: // Listar y contar de profesores de tiempo completo y a la vez que sean de
                        // catedra
                    soloProfesores = intersecion(tiempoCompleto, catedra);
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son de tiempo completo y a la vez de catedra, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 6: // Listar y contar los profesores que son ocasionales y a la vez de catedra
                    soloProfesores = intersecion(ocacional, catedra);
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son ocasionales y a la vez de catedra, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 7: // Listar y contar profesores que tengan las 3 condiciones (Catedra, completo y
                        // ocasional)
                    soloProfesores = intersecion(intersecion(tiempoCompleto, ocacional), catedra);
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son ocasionales y a la vez de catedra, y estos son...");
                    msgScroll(mostrar(soloProfesores));
                    break;

                case 8: // Cantidad de hombre y mujeres por cada tipo de contrato
                    int[] Mujeres = new int[3], Hombres = new int[3];
                    for (Profesor profesor : tiempoCompleto) {
                        if ((profesor.getSexo() + "").contains("F")) {
                            Mujeres[0]++;
                        } else
                            Hombres[0]++;
                    }
                    for (Profesor profesor : ocacional) {
                        if ((profesor.getSexo() + "").contains("F")) {
                            Mujeres[1]++;
                        } else
                            Hombres[1]++;
                    }
                    for (Profesor profesor : catedra) {
                        if ((profesor.getSexo() + "").contains("F")) {
                            Mujeres[2]++;
                        } else
                            Hombres[2]++;
                    }
                    msg("Tiempo Completo: \n    Hombres: " + Hombres[0] + "\n    Mujeres: " + Mujeres[0]
                            + "\nOcacional: \n    Hombres: " + Hombres[1] + "\n    Mujeres: " + Mujeres[1]
                            + "\nCatedra: \n    Hombres: " + Hombres[2] + "\n    Mujeres: " + Mujeres[2]);
                    break;

                case 9: // Listar y contar profesores por cada facultad
                    int[] Facultades = new int[6];
                    soloProfesores = union(union(tiempoCompleto, ocacional), catedra);
                    for (Profesor profesor : soloProfesores) {
                        switch (profesor.getFacultad()) {
                            case "Ingenieria":
                                Facultades[0]++;
                                break;
                            case "Deportes":
                                Facultades[1]++;
                                break;
                            case "Comunicacion":
                                Facultades[2]++;
                                break;
                            case "Administracion":
                                Facultades[3]++;
                                break;
                            case "Idiomas":
                                Facultades[4]++;
                                break;
                            case "CienciasBasicas":
                                Facultades[5]++;
                                break;
                            default:
                                msg("que a pasao tio");
                                break;
                        }
                    }
                    msg("Ingenieria: " + Facultades[0] + "\n Deportes: " + Facultades[1] + "\nComunicación: "
                            + Facultades[2] + "\nAdministracion: " + Facultades[3] + "\nIdiomas: " + Facultades[4]
                            + "\nCiencias Basicas: " + Facultades[5]);
                    break;

                case 10: // F(x) ? -> Realizar una operacion sugerida basandose en los atributos del
                         // Docente.

                    /*
                     *
                     * Pedir datos:
                     * Conjuntos,
                     * Operacion
                     *
                     * Crear Condicional:
                     * Ver si es posible utilizar numero y mayores o iguales
                     *
                     * Invocar funcion maestra.
                     */

                    // Selecionar op:

                    String[] opts = { "Union", "Intersecion", "Diferencia" };
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
                        if (prof.getSexo() == 'M')
                            return true;
                        return false;
                    };

                    // INVOCAR FUNCION MAESTRA'

                    // para test
                    msgScroll(mostrar(funcionMaestra(tiempoCompleto, catedra, cond, OPERACION_SELECIONADA)));
                    break;
                case 11:
                    String cedula = ValidacionCedula();
                    String Nombre = Validaciones("[a-zA-Z\\s]+", "Ingrese el nombre completo");
                    char sexo = ValidacionSexo();
                    String[] opts2 = { "Ingenieria", "Deportes", "Comunicacion", "Administracion", "Idiomas",
                            "CienciasBasicas" };
                    String Facultad = (String) inputSelect("Seleccione el genero", "genero", opts2);
                    String[] opts3 = { "Pregrado", "Especialista", "Maestria", "Doctorado" };
                    String titulo = (String) inputSelect("Seleccione el genero", "genero", opts3);
                    int CantidadAsiganturas = Integer
                            .parseInt(Validaciones("[1-9]|10", "Ingrese la cantidad de asignaturas que dicta"));
                    int CantidadHoras = Integer
                            .parseInt(Validaciones("[1-9]|1[0-9]|20",
                                    "Ingrese la cantidad de horas de clase dictadas por semana"));
                    Date FechaNacimineto = inputDate();
                    Profesor ProfesorNuevo = new Profesor(cedula, Nombre, Facultad, titulo, sexo, CantidadAsiganturas,
                            CantidadHoras, FechaNacimineto);
                    TipoProfesor(ProfesorNuevo, "Tiempo Completo");
                    TipoProfesor(ProfesorNuevo, "Catedra");
                    TipoProfesor(ProfesorNuevo, "Ocasional");
                    break;
                default:
                    msg("Opcion invalida.");
                    break;
            }
        }
    }

    public ArrayList<Profesor> funcionMaestra(ArrayList<Profesor> c1, ArrayList<Profesor> c2,
            Condicion<Profesor> condicion, Operacion<Profesor> operacion) {
        Set<Profesor> filtro1 = new HashSet<>();
        for (Profesor p : c1)
            if (condicion.probar(p))
                filtro1.add(p);

        Set<Profesor> filtro2 = new HashSet<>();
        for (Profesor p : c2)
            if (condicion.probar(p))
                filtro2.add(p);

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

    private String Validaciones(String patron, String msginput) {// metodo para realizar todas las valdiaciones con
                                                                 // expresiones regulares
        Pattern Patron = Pattern.compile(patron);
        String input;
        while (true) {
            input = input(msginput).trim();
            if (!Patron.matcher(input).matches()) { // validar el formato correcto
                msg("Formato invalido");
            } else
                return input;
        }
    }

    private String ValidacionCedula() {
        String input;
        while (true) {
            input = Validaciones("\\d+", "Ingrese la cedula");
            boolean bandera = true;
            for (Profesor profesor : union(union(tiempoCompleto, ocacional), catedra)) {
                if (profesor.getCC().equals(input)) {
                    bandera = false;
                    msg("La cedula ya esta en uso");
                    break;
                }
            }
            if (bandera)
                return input;
        }
    }

    private char ValidacionSexo() {
        String[] opts = { "Masculino", "Femenino" };
        String input = (String) inputSelect("Seleccione el genero", "genero", opts);
        Pattern PatronM = Pattern.compile("M");
        if (PatronM.matcher(input).find()) {
            return 'M';
        } else
            return 'F';
    }

    private void TipoProfesor(Profesor profesornuevo, String tipo) {
        String[] sino = {"Si","No"};
        String SelectTipo = (String) inputSelect("El profesor es de "+tipo+"?", "tipo de profesor", sino);
        if(SelectTipo.equals(sino[0])){
            if(tipo.equals("Tiempo Completo")) tiempoCompleto.add(profesornuevo);
            else if(tipo.equals("Catedra")) catedra.add(profesornuevo);
            else ocacional.add(profesornuevo);
        }
    }
}
