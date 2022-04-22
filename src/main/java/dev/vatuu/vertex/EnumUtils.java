package dev.vatuu.vertex;

import java.util.Optional;
import java.util.function.Predicate;

public class EnumUtils {

    public static <E extends Enum<E>> Optional<E> getEnum(Class<E> clazz, Predicate<E> predicate) {
        for(E e : clazz.getEnumConstants())
            if(predicate.test(e))
                return Optional.of(e);
        return Optional.empty();
    }

    public static <E extends Enum<E>> E getEnumOrDefault(Class<E> clazz, E defaultValue, Predicate<E> test) {
        return getEnum(clazz, test).orElse(defaultValue);
    }
}
