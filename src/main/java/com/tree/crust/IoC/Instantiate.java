package com.tree.crust.IoC;


import com.tree.crust.IoC.annotations.Component;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Instantiate {
    static Map<String, Object> instantiateMap(HashSet<String> clazzNames) {
        Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
        for (String name : clazzNames) {
            singletonObjects.putIfAbsent(name, instantiate(name));
        }
        return singletonObjects;
    }

    private static Object instantiate(String name) {
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
