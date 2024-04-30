import java.util.Set;

@FunctionalInterface
public interface Operacion<O> {
    Set<O> realizar(Set<O> s1, Set<O> s2);
}
