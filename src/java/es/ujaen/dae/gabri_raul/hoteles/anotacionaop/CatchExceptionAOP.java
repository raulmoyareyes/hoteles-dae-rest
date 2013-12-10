package es.ujaen.dae.gabri_raul.hoteles.anotacionaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definicion de una anotacion para interceptar excepciones
 *
 * @author Raúl & Gabri
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CatchExceptionAOP {

    Class<? extends Exception> exception();
}
