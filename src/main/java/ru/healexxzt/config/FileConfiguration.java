package ru.healexxzt.config;

import java.io.*;

public abstract class FileConfiguration extends MemoryConfiguration
{
    public FileConfiguration() {
    }
    
    public FileConfiguration(final Configuration defaults) {
        super(defaults);
    }
    
    public void save(final File file) throws IOException {
        file.getParentFile().mkdirs();
        final String data = this.saveToString();
        final FileWriter writer = new FileWriter(file);
        try {
            writer.write(data);
        }
        finally {
            writer.close();
        }
        writer.close();
    }
    
    public void save(final String file) throws IOException {
        this.save(new File(file));
    }
    
    public abstract String saveToString();
    
    public void load(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.load(new FileInputStream(file));
    }
    
    public void load(final InputStream stream) throws IOException, InvalidConfigurationException {
        final InputStreamReader reader = new InputStreamReader(stream);
        final StringBuilder builder = new StringBuilder();
        final BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }
        finally {
            input.close();
        }
        input.close();
        this.loadFromString(builder.toString());
    }
    
    public void load(final String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.load(new File(file));
    }
    
    public abstract void loadFromString(final String p0) throws InvalidConfigurationException;
    
    protected abstract String buildHeader();
    
    @Override
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }
        return (FileConfigurationOptions)this.options;
    }
}
