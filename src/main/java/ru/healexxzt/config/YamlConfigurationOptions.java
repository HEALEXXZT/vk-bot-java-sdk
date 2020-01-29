package ru.healexxzt.config;

public class YamlConfigurationOptions extends FileConfigurationOptions
{
    private int indent;
    
    protected YamlConfigurationOptions(final YamlConfiguration configuration) {
        super(configuration);
        this.indent = 2;
    }
    
    @Override
    public YamlConfiguration configuration() {
        return (YamlConfiguration)super.configuration();
    }
    
    @Override
    public YamlConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    @Override
    public YamlConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }
    
    @Override
    public YamlConfigurationOptions header(final String value) {
        super.header(value);
        return this;
    }
    
    @Override
    public YamlConfigurationOptions copyHeader(final boolean value) {
        super.copyHeader(value);
        return this;
    }
    
    public int indent() {
        return this.indent;
    }
    
    public YamlConfigurationOptions indent(final int value) {
        this.indent = value;
        return this;
    }
}
