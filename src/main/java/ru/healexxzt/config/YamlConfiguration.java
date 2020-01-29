package ru.healexxzt.config;

import org.yaml.snakeyaml.representer.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import java.io.*;

public class YamlConfiguration extends FileConfiguration
{
    protected static final String COMMENT_PREFIX = "# ";
    protected static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions;
    private final Representer yamlRepresenter;
    private final Yaml yaml;
    
    public YamlConfiguration() {
        this.yamlOptions = new DumperOptions();
        this.yamlRepresenter = new YamlRepresenter();
        this.yaml = new Yaml(new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);
    }
    
    @Override
    public String saveToString() {
        this.yamlOptions.setIndent(this.options().indent());
        this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        final String header = this.buildHeader();
        String dump = this.yaml.dump(this.getValues(false));
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return String.valueOf(header) + dump;
    }
    
    @Override
    public void loadFromString(final String contents) throws InvalidConfigurationException {
        Map<?, ?> input;
        try {
            input = (Map<?, ?>)this.yaml.load(contents);
        }
        catch (YAMLException e) {
            throw new InvalidConfigurationException(e);
        }
        catch (ClassCastException e2) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }
        final String header = this.parseHeader(contents);
        if (header.length() > 0) {
            this.options().header(header);
        }
        if (input != null) {
            this.convertMapsToSections(input, this);
        }
    }
    
    protected void convertMapsToSections(final Map<?, ?> input, final ConfigurationSection section) {
        for (final Map.Entry<?, ?> entry : input.entrySet()) {
            final String key = entry.getKey().toString();
            final Object value = entry.getValue();
            if (value instanceof Map) {
                this.convertMapsToSections((Map<?, ?>)value, section.createSection(key));
            }
            else {
                section.set(key, value);
            }
        }
    }
    
    protected String parseHeader(final String input) {
        final String[] lines = input.split("\r?\n", -1);
        final StringBuilder result = new StringBuilder();
        boolean readingHeader = true;
        boolean foundHeader = false;
        for (int i = 0; i < lines.length && readingHeader; ++i) {
            final String line = lines[i];
            if (line.startsWith("# ")) {
                if (i > 0) {
                    result.append("\n");
                }
                if (line.length() > "# ".length()) {
                    result.append(line.substring("# ".length()));
                }
                foundHeader = true;
            }
            else if (foundHeader && line.length() == 0) {
                result.append("\n");
            }
            else if (foundHeader) {
                readingHeader = false;
            }
        }
        return result.toString();
    }
    
    @Override
    protected String buildHeader() {
        final String header = this.options().header();
        if (this.options().copyHeader()) {
            final Configuration def = this.getDefaults();
            if (def != null && def instanceof FileConfiguration) {
                final FileConfiguration filedefaults = (FileConfiguration)def;
                final String defaultsHeader = filedefaults.buildHeader();
                if (defaultsHeader != null && defaultsHeader.length() > 0) {
                    return defaultsHeader;
                }
            }
        }
        if (header == null) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        final String[] lines = header.split("\r?\n", -1);
        boolean startedHeader = false;
        for (int i = lines.length - 1; i >= 0; --i) {
            builder.insert(0, "\n");
            if (startedHeader || lines[i].length() != 0) {
                builder.insert(0, lines[i]);
                builder.insert(0, "# ");
                startedHeader = true;
            }
        }
        return builder.toString();
    }
    
    @Override
    public YamlConfigurationOptions options() {
        if (this.options == null) {
            this.options = new YamlConfigurationOptions(this);
        }
        return (YamlConfigurationOptions)this.options;
    }
    
    public static YamlConfiguration loadConfiguration(final File file) {
        final YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return config;
    }
    
    public static YamlConfiguration loadConfiguration(final InputStream stream) {
        final YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(stream);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return config;
    }
}
