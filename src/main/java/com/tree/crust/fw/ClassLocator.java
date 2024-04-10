package com.tree.crust.fw;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
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
        File file = new File(findTargetDirectory(primarySource));
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

    // Don't use this garbage in the future (see line 20)
    public static String findTargetDirectory(Class<?> clazz) {
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            URL location = codeSource.getLocation();
            if (location != null) {
                File file = new File(location.getPath());
                if (file.isDirectory() && file.getName().equals("classes")) {
                    File[] files = file.getParentFile().listFiles();
                    if (files != null) {
                        for (File _file : files) {
                            if (_file.isFile() && _file.getName().endsWith(".jar")) {
                                return _file.getPath();
                            }
                        }
                    }
                } else if (file.isFile() && file.getName().endsWith(".jar")) {
                    return file.getPath();
                }
            }
        }
        return null;
    }
}
