package ru.healexxzt.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface DelegateDeserialization {
    Class<? extends ConfigurationSerializable> value();
}
