package ru.healexxzt.config;

import java.util.*;

public interface ConfigurationSection
{
    Set<String> getKeys(final boolean p0);
    
    Map<String, Object> getValues(final boolean p0);
    
    boolean contains(final String p0);
    
    boolean isSet(final String p0);
    
    String getCurrentPath();
    
    String getName();
    
    Configuration getRoot();
    
    ConfigurationSection getParent();
    
    Object get(final String p0);
    
    Object get(final String p0, final Object p1);
    
    void set(final String p0, final Object p1);
    
    ConfigurationSection createSection(final String p0);
    
    ConfigurationSection createSection(final String p0, final Map<?, ?> p1);
    
    String getString(final String p0);
    
    String getString(final String p0, final String p1);
    
    boolean isString(final String p0);
    
    int getInt(final String p0);
    
    int getInt(final String p0, final int p1);
    
    boolean isInt(final String p0);
    
    boolean getBoolean(final String p0);
    
    boolean getBoolean(final String p0, final boolean p1);
    
    boolean isBoolean(final String p0);
    
    double getDouble(final String p0);
    
    double getDouble(final String p0, final double p1);
    
    boolean isDouble(final String p0);
    
    long getLong(final String p0);
    
    long getLong(final String p0, final long p1);
    
    boolean isLong(final String p0);
    
    List<?> getList(final String p0);
    
    List<?> getList(final String p0, final List<?> p1);
    
    boolean isList(final String p0);
    
    List<String> getStringList(final String p0);
    
    List<Integer> getIntegerList(final String p0);
    
    List<Boolean> getBooleanList(final String p0);
    
    List<Double> getDoubleList(final String p0);
    
    List<Float> getFloatList(final String p0);
    
    List<Long> getLongList(final String p0);
    
    List<Byte> getByteList(final String p0);
    
    List<Character> getCharacterList(final String p0);
    
    List<Short> getShortList(final String p0);
    
    List<Map<?, ?>> getMapList(final String p0);
    
    ConfigurationSection getConfigurationSection(final String p0);
    
    boolean isConfigurationSection(final String p0);
    
    ConfigurationSection getDefaultSection();
    
    void addDefault(final String p0, final Object p1);
}
