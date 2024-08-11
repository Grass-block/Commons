package me.gb2022.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.nio.file.Path;
import java.util.function.Consumer;

public interface Annotations {
    static <T extends Annotation> void matchAnnotation(AnnotatedElement element, Class<T> annotation, Consumer<T> accept) {
        if (!hasAnnotation(element, annotation)) {
            return;
        }
        accept.accept(element.getAnnotation(annotation));
    }

    static <T extends Annotation> void matchAnnotation(Object obj, Class<T> annotation, Consumer<T> accept) {
        if (obj instanceof AnnotatedElement element) {
            matchAnnotation(element, annotation, accept);
        } else {
            matchAnnotation(obj.getClass(), annotation, accept);
        }
    }

    static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotation) {
        return element.isAnnotationPresent(annotation);
    }

    static boolean hasAnnotation(Object obj, Class<? extends Annotation> annotation) {
        if (obj instanceof AnnotatedElement element) {
            return hasAnnotation(element, annotation);
        }
        return hasAnnotation(obj.getClass(), annotation);
    }
}
