import java.util.*;

public class Main {
    public static void main(String[] args) {

        Character[] c1 = {'a', 'b', 'c', 'd', 'e'};
        Character[] c2 = {'c', 'd', 'e'};
        ArrayList<Character> lista1 = new ArrayList<>(Arrays.stream(c1).toList());
        ArrayList<Character> lista2 = new ArrayList<>(Arrays.stream(c2).toList());

        System.out.println("Para las listas: \n\t" + lista1.toString() + "\n\t" + lista2.toString());

        // Union
        Set<Character> unionSet = union(lista1, lista2);
        System.out.println("Union entre ellas: " + unionSet);

        // Interseccion
        System.out.println("La interseccion entre ellas: " + intersecion(lista1, lista2));

        // Diferencia
        System.out.println("La diferencia entre ellas: " + diferencia(lista1, lista2));

        // Subconjuntos
        System.out.println("La lista2 es un subconjunto de la lista1?: " + (subconjuntoDe(lista1, lista2) ? "Si" : "No"));
    }

    public static Set<Character> union(ArrayList<Character> lista1, ArrayList<Character> lista2) {
        Set<Character> set = new HashSet<>(lista1);
        set.addAll(lista2);
        return set;
    }

    public static Set<Character> intersecion(ArrayList<Character> lista1, ArrayList<Character> lista2) {
        Set<Character> set1 = new HashSet<>(lista1);
        Set<Character> set2 = new HashSet<>(lista2);
        set1.retainAll(set2);
        return set1;
    }

    public static Set<Character> diferencia(ArrayList<Character> lista1, ArrayList<Character> lista2) {
        Set<Character> set1 = new HashSet<>(lista1);
        Set<Character> set2 = new HashSet<>(lista2);
        set1.removeAll(set2);
        return set1;
    }

    public static boolean subconjuntoDe(ArrayList<Character> lista1, ArrayList<Character> lista2) {
        Set<Character> set1 = new HashSet<>(lista1);
        Set<Character> set2 = new HashSet<>(lista2);
        return set1.containsAll(set2);
    }
}