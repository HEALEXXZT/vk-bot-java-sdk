package ru.healexxzt.config;

public class FileConfigurationOptions extends MemoryConfigurationOptions
{
    private String header;
    private boolean copyHeader;
    
    protected FileConfigurationOptions(final MemoryConfiguration configuration) {
        super(configuration);
        this.header = null;
        this.copyHeader = true;
    }
    
    @Override
    public FileConfiguration configuration() {
        return (FileConfiguration)super.configuration();
    }
    
    @Override
    public FileConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    @Override
    public FileConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }
    
    public String header() {
        return this.header;
    }
    
    public FileConfigurationOptions header(final String value) {
        this.header = value;
        return this;
    }
    
    public boolean copyHeader() {
        return this.copyHeader;
    }
    
    public FileConfigurationOptions copyHeader(final boolean value) {
        this.copyHeader = value;
        return this;
    }
}
