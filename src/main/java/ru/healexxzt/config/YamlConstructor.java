package ru.healexxzt.config;

import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;

public class YamlConstructor extends SafeConstructor
{
    public YamlConstructor() {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
    }
    
    private class ConstructCustomObject extends ConstructYamlMap
    {
        @Override
        public Object construct(final Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
            }
            final Map<?, ?> raw = (Map<?, ?>)super.construct(node);
            if (raw.containsKey("==")) {
                final Map<String, Object> typed = new LinkedHashMap<String, Object>(raw.size());
                for (final Map.Entry<?, ?> entry : raw.entrySet()) {
                    typed.put(entry.getKey().toString(), entry.getValue());
                }
                try {
                    return ConfigurationSerialization.deserializeObject(typed);
                }
                catch (IllegalArgumentException ex) {
                    throw new YAMLException("Could not deserialize object", ex);
                }
            }
            return raw;
        }
        
        @Override
        public void construct2ndStep(final Node node, final Object object) {
            throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
        }
    }
}
