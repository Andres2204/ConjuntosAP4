import java.util.function.Predicate;

@FunctionalInterface
public interface Condicion<C>{
    boolean probar(C c);
}
