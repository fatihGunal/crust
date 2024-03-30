package com.tree.crust.fw;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassLocator
 *
 **/
public class ClassLocator {

    public static HashSet<String> getClassNamesFrom(Class<?> primarySource) throws IOException {

        // TODO: don't make this JAR dependent.
        File file = new File(primarySource.getProtectionDomain().getCodeSource().getLocation().getFile());
        HashSet<String> classNames = new HashSet<>();

        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }

            return classNames;
        }
    }
}
