package com.tree.crust.fw;

import com.tree.crust.example.PersonController;
import com.tree.crust.fw.annotations.Autowired;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OrbFactory
 *
 * Accessing orbs in container
 */
public class LeafFactory {

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    public void createContainer(HashSet<String> clazzNames) throws IllegalAccessException {
        InstantiationStrategy strategy = new InstantiationStrategy();
        this.singletonObjects = strategy.instantiateMap(clazzNames);

        // Start Autowire all objects into all classes.
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
    }

    // Move and adjust to unit test later
    public void manualTestOne() {
        System.out.println("manual test 1");
        PersonController person = (PersonController) this.singletonObjects.get("com.tree.crust.example.PersonController");
        person.getWebService();
        System.out.println("manual test passed!");
    }

    public void manualTestTwo() {
        System.out.println("manual test 2");
        PersonController person = (PersonController) singletonObjects.get("com.tree.crust.example.PersonController");
        person.getWebService();
        System.out.println("manual test passed!");
    }
}
