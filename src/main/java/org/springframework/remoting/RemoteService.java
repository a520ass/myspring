package org.springframework.remoting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.rmi.registry.Registry;

import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemoteService {

    ServiceType serviceType() default ServiceType.HTTPINVOKER;

    Class<?> serviceInterface();
    
    /**
     * rmi才会用到
     */
    int registryPort() default Registry.REGISTRY_PORT;
}
