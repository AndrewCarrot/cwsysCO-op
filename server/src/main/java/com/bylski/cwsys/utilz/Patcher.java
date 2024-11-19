package com.bylski.cwsys.utilz;


import com.bylski.cwsys.exception.IncompatibleClassException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
@Component
public class Patcher {

    public  static <T> void objectPatcher(T existing, T incomplete) throws IllegalAccessException {
        if(existing.getClass() != incomplete.getClass())
            throw new IncompatibleClassException("Cannot use patcher on objects from different classes");

        Class<?> objectClass = existing.getClass();
        Field[] objectFields = objectClass.getDeclaredFields();

        for(Field field:objectFields){
            field.setAccessible(true);

            Object value = field.get(incomplete);
            if(value!=null){
                field.set(existing,value);
            }
            field.setAccessible(false);
        }
    }
}
