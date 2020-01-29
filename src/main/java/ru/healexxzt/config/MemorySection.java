package ru.healexxzt.config;

import java.util.*;

public class MemorySection implements ConfigurationSection
{
    protected final Map<String, Object> map;
    private final Configuration root;
    private final ConfigurationSection parent;
    private final String path;
    private final String fullPath;
    
    protected MemorySection() {
        this.map = new LinkedHashMap<String, Object>();
        if (!(this instanceof Configuration)) {
            throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
        }
        this.path = "";
        this.fullPath = "";
        this.parent = null;
        this.root = (Configuration)this;
    }
    
    public MemorySection(final ConfigurationSection parent, final String path) {
        this.map = new LinkedHashMap<String, Object>();
        this.path = path;
        this.parent = parent;
        this.root = parent.getRoot();
        this.fullPath = createPath(parent, path);
    }
    
    @Override
    public Set<String> getKeys(final boolean deep) {
        final Set<String> result = new LinkedHashSet<String>();
        final Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            final ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.addAll(defaults.getKeys(deep));
            }
        }
        this.mapChildrenKeys(result, this, deep);
        return result;
    }
    
    @Override
    public Map<String, Object> getValues(final boolean deep) {
        final Map<String, Object> result = new LinkedHashMap<String, Object>();
        final Configuration root = this.getRoot();
        if (root != null && root.options().copyDefaults()) {
            final ConfigurationSection defaults = this.getDefaultSection();
            if (defaults != null) {
                result.putAll(defaults.getValues(deep));
            }
        }
        this.mapChildrenValues(result, this, deep);
        return result;
    }
    
    @Override
    public boolean contains(final String path) {
        return this.get(path) != null;
    }
    
    @Override
    public boolean isSet(final String path) {
        final Configuration root = this.getRoot();
        if (root == null) {
            return false;
        }
        if (root.options().copyDefaults()) {
            return this.contains(path);
        }
        return this.get(path, null) != null;
    }
    
    @Override
    public String getCurrentPath() {
        return this.fullPath;
    }
    
    @Override
    public String getName() {
        return this.path;
    }
    
    @Override
    public Configuration getRoot() {
        return this.root;
    }
    
    @Override
    public ConfigurationSection getParent() {
        return this.parent;
    }
    
    @Override
    public void addDefault(final String path, final Object value) {
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot add default without root");
        }
        if (root == this) {
            throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
        }
        root.addDefault(createPath(this, path), value);
    }
    
    @Override
    public ConfigurationSection getDefaultSection() {
        final Configuration root = this.getRoot();
        final Configuration defaults = (root == null) ? null : root.getDefaults();
        if (defaults != null && defaults.isConfigurationSection(this.getCurrentPath())) {
            return defaults.getConfigurationSection(this.getCurrentPath());
        }
        return null;
    }
    
    @Override
    public void set(final String path, final Object value) {
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot use section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            }
            else {
                section = subSection;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            if (value == null) {
                this.map.remove(key);
            }
            else {
                this.map.put(key, value);
            }
        }
        else {
            section.set(key, value);
        }
    }
    
    @Override
    public Object get(final String path) {
        return this.get(path, this.getDefault(path));
    }
    
    @Override
    public Object get(final String path, final Object def) {
        if (path.length() == 0) {
            return this;
        }
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot access section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            section = section.getConfigurationSection(path.substring(i2, i1));
            if (section == null) {
                return def;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            final Object result = this.map.get(key);
            return (result == null) ? def : result;
        }
        return section.get(key, def);
    }
    
    @Override
    public ConfigurationSection createSection(final String path) {
        final Configuration root = this.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create section without a root");
        }
        final char separator = root.options().pathSeparator();
        int i1 = -1;
        ConfigurationSection section = this;
        int i2;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            }
            else {
                section = subSection;
            }
        }
        final String key = path.substring(i2);
        if (section == this) {
            final ConfigurationSection result = new MemorySection(this, key);
            this.map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }
    
    @Override
    public ConfigurationSection createSection(final String path, final Map<?, ?> map) {
        final ConfigurationSection section = this.createSection(path);
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                section.createSection(entry.getKey().toString(), (Map<?, ?>)entry.getValue());
            }
            else {
                section.set(entry.getKey().toString(), entry.getValue());
            }
        }
        return section;
    }
    
    @Override
    public String getString(final String path) {
        final Object def = this.getDefault(path);
        return this.getString(path, (def != null) ? def.toString() : null);
    }
    
    @Override
    public String getString(final String path, final String def) {
        final Object val = this.get(path, def);
        return (val != null) ? val.toString() : def;
    }
    
    @Override
    public boolean isString(final String path) {
        final Object val = this.get(path);
        return val instanceof String;
    }
    
    @Override
    public int getInt(final String path) {
        final Object def = this.getDefault(path);
        return this.getInt(path, (def instanceof Number) ? ((int)def) : 0);
    }
    
    @Override
    public int getInt(final String path, final int def) {
        final Object val = this.get(path, def);
        return (int)((val instanceof Number) ? val : Integer.valueOf(def));
    }
    
    @Override
    public boolean isInt(final String path) {
        final Object val = this.get(path);
        return val instanceof Integer;
    }
    
    @Override
    public boolean getBoolean(final String path) {
        final Object def = this.getDefault(path);
        return this.getBoolean(path, def instanceof Boolean && (boolean)def);
    }
    
    @Override
    public boolean getBoolean(final String path, final boolean def) {
        final Object val = this.get(path, def);
        return (boolean)((val instanceof Boolean) ? val : Boolean.valueOf(def));
    }
    
    @Override
    public boolean isBoolean(final String path) {
        final Object val = this.get(path);
        return val instanceof Boolean;
    }
    
    @Override
    public double getDouble(final String path) {
        final Object def = this.getDefault(path);
        return this.getDouble(path, (def instanceof Number) ? ((double)def) : 0.0);
    }
    
    @Override
    public double getDouble(final String path, final double def) {
        final Object val = this.get(path, def);
        return (double)((val instanceof Number) ? val : Double.valueOf(def));
    }
    
    @Override
    public boolean isDouble(final String path) {
        final Object val = this.get(path);
        return val instanceof Double;
    }
    
    @Override
    public long getLong(final String path) {
        final Object def = this.getDefault(path);
        return this.getLong(path, (def instanceof Number) ? ((long)def) : 0L);
    }
    
    @Override
    public long getLong(final String path, final long def) {
        final Object val = this.get(path, def);
        return (long)((val instanceof Number) ? val : Long.valueOf(def));
    }
    
    @Override
    public boolean isLong(final String path) {
        final Object val = this.get(path);
        return val instanceof Long;
    }
    
    @Override
    public List<?> getList(final String path) {
        final Object def = this.getDefault(path);
        return this.getList(path, (def instanceof List) ? ((List)def) : null);
    }
    
    @Override
    public List<?> getList(final String path, final List<?> def) {
        final Object val = this.get(path, def);
        return (List<?>)((val instanceof List) ? val : def);
    }
    
    @Override
    public boolean isList(final String path) {
        final Object val = this.get(path);
        return val instanceof List;
    }
    
    @Override
    public List<String> getStringList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<String>(0);
        }
        final List<String> result = new ArrayList<String>();
        for (final Object object : list) {
            if (object instanceof String || this.isPrimitiveWrapper(object)) {
                result.add(String.valueOf(object));
            }
        }
        return result;
    }
    
    @Override
    public List<Integer> getIntegerList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Integer>(0);
        }
        final List<Integer> result = new ArrayList<Integer>();
        for (final Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((int)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).intValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Boolean> getBooleanList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Boolean>(0);
        }
        final List<Boolean> result = new ArrayList<Boolean>();
        for (final Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean)object);
            }
            else {
                if (!(object instanceof String)) {
                    continue;
                }
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                }
                else {
                    if (!Boolean.FALSE.toString().equals(object)) {
                        continue;
                    }
                    result.add(false);
                }
            }
        }
        return result;
    }
    
    @Override
    public List<Double> getDoubleList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Double>(0);
        }
        final List<Double> result = new ArrayList<Double>();
        for (final Object object : list) {
            if (object instanceof Double) {
                result.add((Double)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Double.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((double)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).doubleValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Float> getFloatList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Float>(0);
        }
        final List<Float> result = new ArrayList<Float>();
        for (final Object object : list) {
            if (object instanceof Float) {
                result.add((Float)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Float.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((float)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).floatValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Long> getLongList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Long>(0);
        }
        final List<Long> result = new ArrayList<Long>();
        for (final Object object : list) {
            if (object instanceof Long) {
                result.add((Long)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Long.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((long)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).longValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Byte> getByteList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Byte>(0);
        }
        final List<Byte> result = new ArrayList<Byte>();
        for (final Object object : list) {
            if (object instanceof Byte) {
                result.add((Byte)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Byte.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((byte)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).byteValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Character> getCharacterList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Character>(0);
        }
        final List<Character> result = new ArrayList<Character>();
        for (final Object object : list) {
            if (object instanceof Character) {
                result.add((Character)object);
            }
            else if (object instanceof String) {
                final String str = (String)object;
                if (str.length() != 1) {
                    continue;
                }
                result.add(str.charAt(0));
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add((char)((Number)object).intValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Short> getShortList(final String path) {
        final List<?> list = this.getList(path);
        if (list == null) {
            return new ArrayList<Short>(0);
        }
        final List<Short> result = new ArrayList<Short>();
        for (final Object object : list) {
            if (object instanceof Short) {
                result.add((Short)object);
            }
            else if (object instanceof String) {
                try {
                    result.add(Short.valueOf((String)object));
                }
                catch (Exception ex) {}
            }
            else if (object instanceof Character) {
                result.add((short)(char)object);
            }
            else {
                if (!(object instanceof Number)) {
                    continue;
                }
                result.add(((Number)object).shortValue());
            }
        }
        return result;
    }
    
    @Override
    public List<Map<?, ?>> getMapList(final String path) {
        final List<?> list = this.getList(path);
        final List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();
        if (list == null) {
            return result;
        }
        for (final Object object : list) {
            if (object instanceof Map) {
                result.add((Map<?, ?>)object);
            }
        }
        return result;
    }
    
    @Override
    public ConfigurationSection getConfigurationSection(final String path) {
        Object val = this.get(path, null);
        if (val != null) {
            return (val instanceof ConfigurationSection) ? ((ConfigurationSection)val) : null;
        }
        val = this.get(path, this.getDefault(path));
        return (val instanceof ConfigurationSection) ? this.createSection(path) : null;
    }
    
    @Override
    public boolean isConfigurationSection(final String path) {
        final Object val = this.get(path);
        return val instanceof ConfigurationSection;
    }
    
    protected boolean isPrimitiveWrapper(final Object input) {
        return input instanceof Integer || input instanceof Boolean || input instanceof Character || input instanceof Byte || input instanceof Short || input instanceof Double || input instanceof Long || input instanceof Float;
    }
    
    protected Object getDefault(final String path) {
        final Configuration root = this.getRoot();
        final Configuration defaults = (root == null) ? null : root.getDefaults();
        return (defaults == null) ? null : defaults.get(createPath(this, path));
    }
    
    protected void mapChildrenKeys(final Set<String> output, final ConfigurationSection section, final boolean deep) {
        if (section instanceof MemorySection) {
            final MemorySection sec = (MemorySection)section;
            for (final Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.add(createPath(section, entry.getKey(), this));
                if (deep && entry.getValue() instanceof ConfigurationSection) {
                    final ConfigurationSection subsection = (ConfigurationSection) entry.getValue();
                    this.mapChildrenKeys(output, subsection, deep);
                }
            }
        }
        else {
            final Set<String> keys = section.getKeys(deep);
            for (final String key : keys) {
                output.add(createPath(section, key, this));
            }
        }
    }
    
    protected void mapChildrenValues(final Map<String, Object> output, final ConfigurationSection section, final boolean deep) {
        if (section instanceof MemorySection) {
            final MemorySection sec = (MemorySection)section;
            for (final Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
                if (entry.getValue() instanceof ConfigurationSection && deep) {
                    this.mapChildrenValues(output, (ConfigurationSection) entry.getValue(), deep);
                }
            }
        }
        else {
            final Map<String, Object> values = section.getValues(deep);
            for (final Map.Entry<String, Object> entry : values.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
            }
        }
    }
    
    public static String createPath(final ConfigurationSection section, final String key) {
        return createPath(section, key, (section == null) ? null : section.getRoot());
    }
    
    public static String createPath(final ConfigurationSection section, final String key, final ConfigurationSection relativeTo) {
        final Configuration root = section.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create path without a root");
        }
        final char separator = root.options().pathSeparator();
        final StringBuilder builder = new StringBuilder();
        if (section != null) {
            for (ConfigurationSection parent = section; parent != null && parent != relativeTo; parent = parent.getParent()) {
                if (builder.length() > 0) {
                    builder.insert(0, separator);
                }
                builder.insert(0, parent.getName());
            }
        }
        if (key != null && key.length() > 0) {
            if (builder.length() > 0) {
                builder.append(separator);
            }
            builder.append(key);
        }
        return builder.toString();
    }
    
    @Override
    public String toString() {
        final Configuration root = this.getRoot();
        return String.valueOf(this.getClass().getSimpleName()) + "[path='" + this.getCurrentPath() + "', root='" + ((root == null) ? null : root.getClass().getSimpleName()) + "']";
    }
}
