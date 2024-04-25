package me.gb2022.commons.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface MathCollections {
    static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> ret = new HashSet<>(a);
        ret.addAll(b);
        return ret;
    }

    static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> ret = new HashSet<>(a);
        ret.removeIf((item) -> !b.contains(item));
        return ret;
    }

    static <T> Set<T> complementSet(Set<T> a, Set<T> complementary) {
        Set<T> ret = new HashSet<>(complementary);
        ret.removeAll(a);
        return ret;
    }

    static <T> boolean contains(Set<T> a, T item) {
        return a.contains(item);
    }

    static <T> boolean include(Set<T> set, Set<T> parent) {
        return parent.containsAll(set);
    }

    static <T> boolean trueInclude(Set<T> set, Set<T> parent) {
        return parent.containsAll(set) && parent.size() > set.size();
    }


}
