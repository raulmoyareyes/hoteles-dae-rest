package es.ujaen.dae.gabri_raul.hoteles.anotacionaop;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Definicion de la clase AOP para interceptar llamadas
 *
 * @author Raúl & Gabri
 */
@Aspect
public class AOP {

    public AOP() {

    }

    @Around("@annotation(CatchExceptionAOP)")
    public void interceptor(ProceedingJoinPoint join) throws Throwable {
        try {
            join.proceed();
        } catch (Exception e) {
            Class c = join.getSignature().getDeclaringType();
            CatchExceptionAOP a = null;
            for(Method m : c.getDeclaredMethods()){
                if(m.getName().equals(join.getSignature().getName())){
                    a = m.getAnnotation(CatchExceptionAOP.class);
                }
            }
            throw a.exception().newInstance();
        }
    }
}
