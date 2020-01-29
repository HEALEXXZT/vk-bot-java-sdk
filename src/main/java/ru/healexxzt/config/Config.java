package ru.healexxzt.config;

import java.io.*;
import java.util.*;

public class Config
{
    private FileConfiguration fconfig;
    private String path;
    
    public Config(final String path) {
        this.path = String.valueOf(System.getProperty("user.dir")) + path;
        this.createFile();
        this.fconfig = YamlConfiguration.loadConfiguration(new File(String.valueOf(System.getProperty("user.dir")) + path));
    }
    
    public void load() {
        this.createFile();
        try {
            this.fconfig.load(this.path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        this.createFile();
        try {
            this.fconfig.save(this.path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createFile() {
        final File file = new File(this.path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getString(final String key, final String def) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getString(key);
        }
        this.fconfig.set(key, def);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return def;
    }
    
    public String getString(final String key) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getString(key);
        }
        return null;
    }
    
    public int getInt(final String key, final int def) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getInt(key);
        }
        this.fconfig.set(key, def);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return def;
    }
    
    public int getInt(final String key) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getInt(key);
        }
        return 0;
    }
    
    public boolean getBoolean(final String key, final boolean def) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getBoolean(key);
        }
        this.fconfig.set(key, def);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return def;
    }
    
    public List<String> getListString(final String key, final List<String> def) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getStringList(key);
        }
        this.fconfig.set(key, def);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return def;
    }
    
    public List<String> getListString(final String key) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getStringList(key);
        }
        return null;
    }
    
    public double getDouble(final String key, final double def) {
        if (this.fconfig.contains(key)) {
            return this.fconfig.getDouble(key);
        }
        this.fconfig.set(key, def);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return def;
    }
    
    public void setString(final String key, final String val) {
        this.fconfig.set(key, val);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setInt(final String key, final int val) {
        this.fconfig.set(key, val);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setBool(final String key, final boolean val) {
        this.fconfig.set(key, val);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setListString(final String key, final List<String> val) {
        this.fconfig.set(key, val);
        try {
            this.fconfig.save(this.path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> getSubNodes(final String node) {
        final List<String> ret = new ArrayList<String>();
        try {
            for (final Object o : this.fconfig.getConfigurationSection(node).getKeys(false)) {
                ret.add((String)o);
            }
        }
        catch (Exception ex) {}
        return ret;
    }
}
