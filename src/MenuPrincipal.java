import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class MenuPrincipal extends Menu {

    private final ArrayList<Profesor> tiempoCompleto; 
    private final ArrayList<Profesor> catedra;
    private final ArrayList<Profesor> ocacional;


    // OPERACIONES
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

    // CONSTRUCTOR
    public MenuPrincipal(String title) { //f = 4
        super(title); //1
        tiempoCompleto = new ArrayList<>(); //1
        catedra = new ArrayList<>(); //1
        ocacional = new ArrayList<>(); //1
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

        cargarDatos(); // 10n + 14 + (2n + 12) = 12n + 26
        while (true) { //n
            int opt; // n
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
                                10- Operaciones entre conjuntos.
                                11- Ingresar profesor.
                                0- Salir.
                                """)); // n
            } catch (Exception e) { // n
                e.printStackTrace(); // n
                // control de la excepcion.
                if (e.toString().contains("Cannot parse null string")) // n
                    return; // n
                msg("Se necesita una opcion valida."); // n
                continue; // n
            }

            switch (opt) { // n
                case 0: { // n
                    return;// n
                }

                case 1: // Listar y contar los profesores que son de tiempo completo solamente; n
                    /*
                     * OPERACION:
                     * (TIEMPO_COMPLETO - CATEDRA) - OCACIONALES
                     *
                     */

                    ArrayList<Profesor> soloProfesores = diferencia(diferencia(tiempoCompleto, catedra), ocacional); // (24n^2 + 52n + 28)*2n = 48n^3 + 52n^2 + 28n
                    msg("Hay " + soloProfesores.size() + " profesores que son solo de tiempo completo, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // (10n + 5 + 6)*n = 10n^2 + 11n
                    break; // n ; f total 1 = 48n^3 + 62n^2 + 53n

                case 2: // Listar y contar los profesores que son de catedra solamente; n
                    soloProfesores = diferencia(diferencia(catedra, tiempoCompleto), ocacional); // 48n^3 + 52n^2 + 28n
                    msg("Hay " + soloProfesores.size() + " profesores que son solo de catedra, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n ; f total 2 = 48n^3 + 62n^2 + 53n

                case 3: // Listar y contar los profesores que son ocasionales solamente; n
                    soloProfesores = diferencia(diferencia(ocacional, tiempoCompleto), catedra); // 48n^3 + 52n^2 + 28n
                    msg("Hay " + soloProfesores.size() + " profesores que son solo ocacionales, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n; f total 3 = 48n^3 + 62n^2 + 53n

                case 4: // Lista y contar el total de profesores; n
                    soloProfesores = union(union(tiempoCompleto, ocacional), catedra); // (3n + 4)*2n = 6n^2 + 8n
                    msg("Hay " + soloProfesores.size() + " profesores en total, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n; f total 4 = 16n^2 + 23n

                case 5: // Listar y contar de profesores de tiempo completo y a la vez que sean de; n
                    // catedra
                    soloProfesores = intersecion(tiempoCompleto, catedra); // (4n + 8)*n = 4n^2 + 8n
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son de tiempo completo y a la vez de catedra, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n; f total 5 = 4n^2 + 23n

                case 6: // Listar y contar los profesores que son ocasionales y a la vez de catedra; n
                    soloProfesores = intersecion(ocacional, catedra); // 4n^2 + 8n
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son ocasionales y a la vez de catedra, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n ; f total 6 = 14n^2 + 22n

                case 7: // Listar y contar profesores que tengan las 3 condiciones (Catedra, completo y; n
                    // ocasional)
                    soloProfesores = intersecion(intersecion(tiempoCompleto, ocacional), catedra); // (4n^2 + 16)*2n = 8n^3 +32n
                    msg("Hay " + soloProfesores.size()
                            + " profesores que son ocasionales y a la vez de catedra, y estos son..."); // 2n
                    msgScroll(mostrar(soloProfesores)); // 10n^2 + 11n
                    break; // n; f total 7 = 18n^2 + 56n

                case 8: // Cantidad de hombre y mujeres por cada tipo de contrato; n
                    int[] Mujeres = new int[3], Hombres = new int[3]; // n
                    for (Profesor profesor : tiempoCompleto) { // mn
                        if ((profesor.getSexo() + "").contains("F")) { // mn
                            Mujeres[0]++; // mn
                        } else
                            Hombres[0]++; // mn
                    }
                    for (Profesor profesor : ocacional) { //mn
                        if ((profesor.getSexo() + "").contains("F")) { //mn
                            Mujeres[1]++; // mn
                        } else
                            Hombres[1]++; // mn
                    }
                    for (Profesor profesor : catedra) { // mn
                        if ((profesor.getSexo() + "").contains("F")) { // mn
                            Mujeres[2]++; // mn
                        } else
                            Hombres[2]++; // mn
                    }
                    msg("Tiempo Completo: \n    Hombres: " + Hombres[0] + "\n    Mujeres: " + Mujeres[0]
                            + "\nOcacional: \n    Hombres: " + Hombres[1] + "\n    Mujeres: " + Mujeres[1]
                            + "\nCatedra: \n    Hombres: " + Hombres[2] + "\n    Mujeres: " + Mujeres[2]); // n
                    break; // n ; f total 8 = 12mn + 4n

                case 9: // Listar y contar profesores por cada facultad; n
                    int[] Facultades = new int[6]; // n
                    soloProfesores = union(union(tiempoCompleto, ocacional), catedra); // 6n^2 + 8n
                    for (Profesor profesor : soloProfesores) { // mn
                        switch (profesor.getFacultad()) { // mn
                            case "Ingenieria": // mn
                                Facultades[0]++; // mn
                                break; // mn
                            case "Deportes": // mn
                                Facultades[1]++; // mn
                                break; // mn
                            case "Comunicacion": // mn
                                Facultades[2]++; // mn
                                break; // mn
                            case "Administracion": // mn
                                Facultades[3]++; // mn
                                break; // mn
                            case "Idiomas": // mn
                                Facultades[4]++; // mn
                                break; // mn
                            case "CienciasBasicas": // mn
                                Facultades[5]++; // mn
                                break; // mn
                            default: // mn
                                msg("que a pasao tio"); // mn
                                break;
                        }
                    }
         /* n */    msg("Ingenieria: " + Facultades[0] + "\n Deportes: " + Facultades[1] + "\nComunicación: " + Facultades[2] + "\nAdministracion: " + Facultades[3] + "\nIdiomas: " + Facultades[4] + "\nCiencias Basicas: " + Facultades[5]);
                    break; // n; f total 9 = 6n^2 + 22nm + 10n

                case 10: // n
                    /*
                     * Esta función permite realizar una operación entre dos conjuntos de profesores
                     * basada en un atributo seleccionado y una condición específica.
                     *
                     * Pedir datos:
                     *   - Conjuntos de profesores
                     *   - Operación a realizar (Unión, Intersección, Diferencia)
                     *
                     * Crear Condicional:
                     *   - Aplicar una operación diferente dependiendo de la selección del usuario (igual ==, diferente != , mayor > , menor <).
                     *
                     * Invocar función adaptativa que realiza la operación deseada.
                     *
                     * @return mensaje que indica el resultado de la operación entre los conjuntos de profesores.
                     */

                    // <- SELECIONAR OPERACION ->

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
                    // 12n

                    // <- SELECIONAR CONJUNTOS ->

                    ArrayList<String> conjuntos = new ArrayList<>(Arrays.asList("Profesores de tiempo completo", "Profesores de catedra", "Profesores ocacionales"));

                    // Selecionar primer conjunto
                    String c1 = (String) inputSelect("Seleccione el primer conjunto: ", "Conjunto 1", conjuntos.toArray(new String[conjuntos.size()]));
                    ArrayList<Profesor> conjunto1 = null;
                    if (conjuntos.get(0).equals(c1)) {
                        conjunto1 = tiempoCompleto;
                    } else if (conjuntos.get(1).equals(c1)) {
                        conjunto1 = catedra;
                    } else if (conjuntos.get(2).equals(c1)) {
                        conjunto1 = ocacional;
                    } else System.out.println("xd?");
                    conjuntos.remove(c1);
                    // 11n

                    // Seleccionar segundo conjunto
                    String c2 = (String) inputSelect("Seleccione el segundo conjunto: ", "Conjunto 2", conjuntos.toArray(new String[conjuntos.size()]));
                    ArrayList<Profesor> conjunto2 = null;
                    if (c2.contains("completo")) { // 4n^2 + 6n
                        conjunto2 = tiempoCompleto;
                    } else if (c2.contains("catedra")) { // 4n^2 + 6n
                        conjunto2 = catedra;
                    } else if (c2.contains("ocacionales")) { // 4n^2 + 6n
                        conjunto2 = ocacional;
                    } else System.out.println("xd?"); // 4n^2 + 6n
                    // ->  16n^2 + 28n

                    // <- CREAR CONDICIONAL ->

                    ArrayList<String> atrs = new ArrayList<>(Arrays.asList("Titulo", "Facultad", "Sexo", "Horas que dicta", "Asignaturas que dicta", "Año de nacimiento"));
                    String atr = (String) inputSelect("Selecione atributo por el cual se va a filtrar", "Atributo", atrs.toArray(new String[atrs.size()]));

                    Predicate<Profesor> condicion_final = profesor -> {return true; };
                    boolean numeric = false;

                    /*
                        CONDICONAL SIMPLE PARA ATRIBUTOS STRING
                            - Se captura el valor del atributo
                            - Se crea la condicion para el atributo
                    */
                    if (atrs.get(0).equals(atr)) { // Titulo
                        String titulo = capturarTitulo();
                        condicion_final = profesor -> profesor.getTitulo().equals(titulo);

                    } else if (atrs.get(1).equals(atr)) { // Facultad
                        String facultad = capturarFacultad();
                        condicion_final = profesor -> profesor.getTitulo().equals(facultad);

                    } else if (atrs.get(2).equals(atr)) { // Sexo
                        String[] sexos = {"M", "F"};
                        Character sexo = (Character) inputSelect("Selecione el sexo (Masculino, Femenino)", "Sexo", sexos);
                        condicion_final = profesor -> profesor.getSexo() == sexo;
                    }
                    // 15n

                    /*
                        CONDICIONAL PARA ATRIBUTOS NUMERICOS
                            - Se registra el numero que se va a utilizar en los condicionales
                            - Se describe el nombre de la funcion del getter
                            - La flag numeric se vuelve true
                     */
                    int numero;
                    String atributo;
                    if (atrs.get(3).equals(atr)) { // Horas que dicta
                        numero = capturarHorasDictadas();
                        atributo = "getHorasDictadas";
                        numeric = true;

                    } else if (atrs.get(4).equals(atr)) { // Asignaturas
                        numero = capturarAsignaturasDictadas();
                        atributo = "getAsignaturasDicta";
                        numeric = true;

                    } else if (atrs.get(5).equals(atr)) { // Año de nacimento
                        Pattern m = Pattern.compile("\\d{4}");
                        String num;
                        do {
                            num = input("Escriba el año [yyyy]: ");
                        } while (!m.matcher(num).matches()); // 3m
                        numero = Integer.parseInt(num);
                        atributo = "getAnoNacimiento";
                        numeric = true;

                    } else {
                        numero = 0;
                        atributo = "";
                    }
                    // 3m + 19n

                    /*
                        CAMBIOS EN CONDICIONES
                            - Datos numericos
                                - La condicion verifica que operacion se escogio y la implementa.

                            - Datos no numericos
                                - Se verifica si la condicion sera de igualdad o no

                    */

                    if (numeric) { // Datos numericos
                        String comparacion = cambiarComparacion();
                        condicion_final = profesor -> {
                            try {
                                Method getter = Profesor.class.getMethod(atributo);
                                int valorAtributo = (int) getter.invoke(profesor);
                                switch (comparacion) {
                                    case "<":
                                        return valorAtributo < numero;
                                    case ">":
                                        return valorAtributo > numero;
                                    case "==":
                                        return valorAtributo == (numero);
                                    case "!=":
                                        return valorAtributo != (numero);
                                    default:
                                        System.out.println("Operación no soportada: " + comparacion);
                                        return false;
                                }
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                return false;
                            }
                        };
                    } else { // Datos no numericos
                        ArrayList<String> oprs = new ArrayList<>(Arrays.asList("Igual", "Diferente"));
                        String opr = (String) inputSelect("Bajo que condicion quiere operar?", "Condicion", oprs.toArray(new String[oprs.size()]));
                        if (oprs.get(1).equals(opr)) condicion_final = condicion_final.negate();
                    }
                    // 25n

                    // <- INVOCACION DE LA FUNCION ADAPTABLE + MOSTRAR ->
                    msgScroll(mostrar(funcionAdaptativa(conjunto1, conjunto2, condicion_final, OPERACION_SELECIONADA)));
                    // mostrar = 10n + 5
                    // f adaptativa = 10n + 4
                    // sumatoria + 3 = 25n + 3m + 19n + 16n^2 + 28n + 15n + 23n + 3n


                    break; // n; f total 10 = 16n^2 + 113n + 3m

                case 11:
                    String cedula = ValidacionCedula(); // (6nm^2 +12mn + 7n + 4)*n = 6(nm)^2 + 12mn^2 + 7n^2 + 4n
                    String Nombre = Validaciones("[a-zA-Z\\s]+", "Ingrese el nombre completo"); // (3n + 3)*n = 3n^2 +3n
                    char sexo = ValidacionSexo(); // 7n
                    String[] opts2 = { "Ingenieria", "Deportes", "Comunicacion", "Administracion", "Idiomas",
                            "CienciasBasicas" }; // n
                    String Facultad = (String) inputSelect("Seleccione el genero", "genero", opts2); // 7n
                    String[] opts3 = { "Pregrado", "Especialista", "Maestria", "Doctorado" }; // n
                    String titulo = (String) inputSelect("Seleccione el genero", "genero", opts3); // 7n
                    int CantidadAsiganturas = Integer
                            .parseInt(Validaciones("[1-9]|10", "Ingrese la cantidad de asignaturas que dicta")); // 3n^2 +3n
                    int CantidadHoras = Integer
                            .parseInt(Validaciones("[1-9]|1[0-9]|20",
                                    "Ingrese la cantidad de horas de clase dictadas por semana")); // 3n^2 +3n
                    Date FechaNacimineto = inputDate(); // (4n + 33)*n = 4n^2 + 33n
                    Profesor ProfesorNuevo = new Profesor(cedula, Nombre, Facultad, titulo, sexo, CantidadAsiganturas,
                            CantidadHoras, FechaNacimineto); // 8n
                    TipoProfesor(ProfesorNuevo, "Tiempo Completo"); // 18n
                    TipoProfesor(ProfesorNuevo, "Catedra"); // 18n
                    TipoProfesor(ProfesorNuevo, "Ocasional"); // 18n
                    break; // n
                    // f total 11 =  6(nm)^2 + 12mn^2 + 17n^2 + 124n

                default: // n
                    msg("Opcion invalida."); // n
                    break; // n
            }
        }
    } // f total metodo menu = 15n + sumatoriaCasos = 144n^3+284n^2+6n^2m^2+537n+34nm+12n^2m

    public ArrayList<Profesor> funcionAdaptativa(ArrayList<Profesor> c1, ArrayList<Profesor> c2, // f = 10n + 4
                                                 Predicate<Profesor> condicion, Operacion<Profesor> operacion) {

        System.out.println("[ FUNCION ADAPTABLE ]");
        Set<Profesor> filtro1 = new HashSet<>();
        for (Profesor p : c1) {
            if (condicion.test(p)) {
                filtro1.add(p);
                System.out.println("[!] Filtro 2, coincidencia encontrada" + p.toString());
            } else System.out.println("[!] Invalido: " + p.getCC());
        }

        Set<Profesor> filtro2 = new HashSet<>();
        for (Profesor p : c2) {
            if (condicion.test(p)) {
                filtro2.add(p);
                System.out.println("[!] Filtro 2, coincidencia encontrada" + p.toString());
            } else System.out.println("[!] Invalido: " + p.getCC());
        }

        return new ArrayList<>(operacion.realizar(filtro1, filtro2));
    }

    // OPERACIONES

    /*
     < METODOS USADOS POR LA CLASE HASHSET >

        public boolean contains(Object o) {
            Iterator<E> it = iterator();        1
            if (o==null) {                      1
                while (it.hasNext())            n
                    if (it.next()==null)        n
                        return true;            1
            } else {
                while (it.hasNext())            n
                    if (o.equals(it.next()))    n
                       return true;             1
            }
            return false;                       1
        }
        contains = 4n + 6
    */



    public static ArrayList<Profesor> union(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) { // f = 3n + 4
        Set<Profesor> set = new HashSet<>(lists1);
        set.addAll(lista2);
        return new ArrayList<>(set);


        /*
        public boolean addAll(Collection<? extends E> c) {
            boolean modified = false;   1
            for (E e : c)               n
                if (add(e))             n
                    modified = true;    n
            return modified;            1
        }
        addAll = 3n + 1

        */
    }

    public static ArrayList<Profesor> intersecion(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set1 = new HashSet<>(lists1); 
        Set<Profesor> set2 = new HashSet<>(lista2); 
        set1.retainAll(set2); // 4n + 4 
        return new ArrayList<>(set1); 

        // FRECUENCIA TOTAL DE INTERSECCION = 4n + 8

        /* public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c); 1
            boolean modified = false; 1
            Iterator<E> it = iterator(); 1
            while (it.hasNext()) { n
                if (!c.contains(it.next())) { n
                    it.remove(); n
                    modified = true; n
                }
            }
            return modified; 1
        }
        retainAll = 4n + 4  
        */

    }

    public static ArrayList<Profesor> diferencia(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) { 
        Set<Profesor> set1 = new HashSet<>(lists1);
        Set<Profesor> set2 = new HashSet<>(lista2);
        set1.removeAll(set2); // 24n^2 + 52n + 24
        return new ArrayList<>(set1);

        // FRECUENCIA TOTAL DE DIFERENCIA => 24n^2 + 52n + 28

        /*
        public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);          1
        boolean modified = false;           1

        if (size() > c.size()) {            1
            for (Object e : c)              n
                modified |= remove(e);      n
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {   n
                if (c.contains(i.next())) {                     n
                    i.remove();                                 n
                    modified = true;                            n
                }
            }
        }
        return modified;                                        1
        }
        removeAll = (6n + 4)*(4n + 6) = 24n^2 + 36n + 16n + 24
        -> 24n^2 + 52n + 24
        */

    }

    public static boolean subconjuntoDe(ArrayList<Profesor> lists1, ArrayList<Profesor> lista2) {
        Set<Profesor> set1 = new HashSet<>(lists1);
        Set<Profesor> set2 = new HashSet<>(lista2);
        return set1.containsAll(set2);
        // FRECUENCIA TOTAL DE SUBCONJUNTO DE 8n^2 + 20n + 15
        /*
        contains = 4n + 6
        public boolean containsAll(Collection<?> c) {
        for (Object e : c)      n
            if (!contains(e))   n
                return false;   1
        return true;            1
        }
        constainsAll = (2n + 2)*(4n + 6) = 8n^2 + 20n + 12
        */
    }

    // UTILIDAD

    // captura de datos

    private String capturarFacultad() { // f = 2
        String[] facultades = {"Ingenieria", "Deportes", "Comunicación", "Administracion", "Idiomas", "Ciencias Basicas"};
        return (String) inputSelect("Seleccione la Facultad.", "Facultad", facultades);
    }

    private String capturarTitulo() { // f = 2
        String[] titulos = {"Pregrado", "Especialista", "Maestria", "Doctorado"};
        return (String) inputSelect("Seleccione el Titulo", "Titulo", titulos);
    }

    private int capturarAsignaturasDictadas() { // f = 2n + 3
        Pattern m = Pattern.compile("0*10|0*\\d");
        String num;
        do {
            num = input("Escriba el numero de asignaturas que dicta [1-10]: ");
        } while (!m.matcher(num).matches());
        return Integer.parseInt(num);
    }

    private int capturarHorasDictadas() { // f = 2n + 3
        Pattern m = Pattern.compile("0*20|0*1\\d|0*\\d");
        String num;
        do {
            num = input("Escriba las horas que ha dictadas [1-20]: ");
        } while (!m.matcher(num).matches());
        return Integer.parseInt(num);
    }

    // funcion a
    private String cambiarComparacion() { // f = n
        ArrayList<String> oprs = new ArrayList<>(Arrays.asList("Igual", "Diferente", "Mayor", "Menor"));
        String opr = (String) inputSelect("Bajo que condicion quiere operar?", "Condicion", oprs.toArray(new String[oprs.size()]));
        if (oprs.get(0).equals(opr)) { // igual
            return "==";
        } else if (oprs.get(1).equals(opr)) { // Diferente
            return "!=";
        } else if (oprs.get(2).equals(opr)) { // Mayor
            return ">";
        } else if (oprs.get(3).equals(opr)) { // Menor
            return "<";
        }
        return "==";
    }

    // otras utilidades

    public String mostrar(ArrayList<Profesor> d) { // f = 10n + 5
        Iterator<Profesor> i = d.iterator();
        StringBuilder s = new StringBuilder();
        if (!d.isEmpty()) {
            s.append("Mostrando " + d.size() + " Entradas.\n");
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
        }

        return s.toString();
    }


    public void cargarDatos() { // f = 10n + 14 + (2n + 12)
        try {
            FileManager f = new FileManager("Profesores.txt");
            System.out.println("[ FUNCION: CARGAR DATOS ]");

            ArrayList<String> lineas = f.readFileArrayList(); // 2n + 12
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

    // Validaciones
    private String Validaciones(String patron, String msginput) {// metodo para realizar todas las valdiaciones con; f = 3n + 3
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

    private String ValidacionCedula() { // f = 6nm^2 + 12mn + 7n + 4
        String input; // 1
        while (true) { // n
            input = Validaciones("\\d+", "Ingrese la cedula"); // 3n + 3
            boolean bandera = true; // n
            for (Profesor profesor : union(union(tiempoCompleto, ocacional), catedra)) { // (6m^2 + 8m)*n = 6nm^2 + 8nm
                if (profesor.getCC().equals(input)) { // nm
                    bandera = false; // mn
                    msg("La cedula ya esta en uso"); // mn
                    break; // mn
                }
            }
            if (bandera) // n
                return input; // n
        }
    }

    private char ValidacionSexo() { // f = 7
        String[] opts = { "Masculino", "Femenino" }; // 1
        String input = (String) inputSelect("Seleccione el genero", "genero", opts); // 1
        Pattern PatronM = Pattern.compile("M"); // 1
        if (PatronM.matcher(input).find()) { // 1
            return 'M'; // 1
        } else // 1
            return 'F'; // 1
    }

    private void TipoProfesor(Profesor profesornuevo, String tipo) { // f = 18
        String[] sino = {"Si","No"}; // 1
        String SelectTipo = (String) inputSelect("El profesor es de "+tipo+"?", "tipo de profesor", sino); // 7
        if(SelectTipo.equals(sino[0])){ // 1
            if(tipo.equals("Tiempo Completo")) tiempoCompleto.add(profesornuevo); // 3
            else if(tipo.equals("Catedra")) catedra.add(profesornuevo); // 3
            else ocacional.add(profesornuevo); // 3
        }
    }

}
