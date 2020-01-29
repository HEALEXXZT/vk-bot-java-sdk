package ru.healexxzt.config;

public class ConfigurationOptions
{
    private char pathSeparator;
    private boolean copyDefaults;
    private final Configuration configuration;
    
    protected ConfigurationOptions(final Configuration configuration) {
        this.pathSeparator = '.';
        this.copyDefaults = false;
        this.configuration = configuration;
    }
    
    public Configuration configuration() {
        return this.configuration;
    }
    
    public char pathSeparator() {
        return this.pathSeparator;
    }
    
    public ConfigurationOptions pathSeparator(final char value) {
        this.pathSeparator = value;
        return this;
    }
    
    public boolean copyDefaults() {
        return this.copyDefaults;
    }
    
    public ConfigurationOptions copyDefaults(final boolean value) {
        this.copyDefaults = value;
        return this;
    }
}
