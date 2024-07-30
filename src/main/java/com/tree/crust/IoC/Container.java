package com.tree.crust.IoC;


import com.tree.crust.IoC.annotations.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Container {

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private HashSet<String> clazzNames = new HashSet<>();

    Container(Map<String, Object> singletonObjects, HashSet<String> clazzNames) {
        this.singletonObjects = singletonObjects;
        this.clazzNames = clazzNames;
        createBeans();
        printObjects();
    }

    public static Container initialize(Class<?> primarySource) {
        if (primarySource == null) {
            throw new IllegalArgumentException("Primary source cannot be null");
        }

        HashSet<String> clazzNames = initializeClassNames(primarySource);
        Map<String, Object> singletonObjects = initializeSingletonObjects(clazzNames);

        return new Container(singletonObjects, clazzNames);
    }

    private static HashSet<String> initializeClassNames(Class<?> primarySource) {
        return ClassLocator.scanClassNamesFrom(primarySource);
    }

    private static Map<String, Object> initializeSingletonObjects(HashSet<String> classNames) {
        return Instantiate.instantiateMap(classNames);
    }

    public void createBeans() {
        try {
            for (Map.Entry<String, Object> entry : singletonObjects.entrySet()) {
                Class<?> clazz = entry.getValue().getClass();
                Field[] fields = clazz.getDeclaredFields();

                if (fields.length > 0) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            Class<?> fieldType = field.getType();
                            if (clazzNames.contains(fieldType.getName())) {
                                Object fieldValue = singletonObjects.get(fieldType.getName());
                                field.setAccessible(true);
                                field.set(entry.getValue(), fieldValue);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }
        System.out.println("Succes created beans!");
    }

    private void printObjects() {
        System.out.println(singletonObjects);
    }

    public Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }
}
