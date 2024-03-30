package com.tree.crust.fw;

import com.tree.crust.fw.annotations.Component;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InstantiationStrategy
 */
public class InstantiationStrategy {

    // private final Map<String, Object> singletonObjects = new
    // ConcurrentHashMap<>(256);

    public Map<String, Object> instantiateMap(HashSet<String> clazzNames) {
        Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

        for (String name : clazzNames) {
            singletonObjects.putIfAbsent(name, instantiate(name));
        }

        return singletonObjects;
    }

    private Object instantiate(String name) {
        try {
            Class<?> clazz = Class.forName(name);
            if (clazz.isAnnotationPresent(Component.class)) {
                Constructor<?> constructor = clazz.getConstructor();
                return constructor.newInstance();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // TODO this should not populate the list singletonObjects!
        return new Object();
    }
}
