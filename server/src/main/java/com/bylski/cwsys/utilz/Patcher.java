package com.bylski.cwsys.utilz;

import com.bylski.cwsys.model.Climber;
import com.bylski.cwsys.model.dto.ClimberDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
@Component
public class Patcher {
    public static void climberPatcher(Climber existing, Climber incomplete) throws IllegalAccessException {
        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> climberClass= Climber.class;
        Field[] climberFields=climberClass.getDeclaredFields();

        for(Field field : climberFields){

            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            Object value=field.get(incomplete);
            if(value!=null){
                field.set(existing,value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }
    }
}
