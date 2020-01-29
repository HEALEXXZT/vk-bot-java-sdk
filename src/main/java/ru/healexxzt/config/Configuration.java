package ru.healexxzt.config;

import java.util.*;

public interface Configuration extends ConfigurationSection
{
    void addDefault(final String p0, final Object p1);
    
    void addDefaults(final Map<String, Object> p0);
    
    void addDefaults(final Configuration p0);
    
    void setDefaults(final Configuration p0);
    
    Configuration getDefaults();
    
    ConfigurationOptions options();
}
