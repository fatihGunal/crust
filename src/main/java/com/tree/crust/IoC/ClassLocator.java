package com.tree.crust.IoC;

import java.io.File;
import java.net.URL;
import java.util.*;

class ClassLocator {
    static HashSet<String> scanClassNamesFrom(Class<?> primarySource) {
        HashSet<String> classNames = new HashSet<>();
        String packageName = primarySource.getPackage().getName();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = primarySource.getClassLoader();
        URL packageURL = classLoader.getResource(packagePath);

        if (packageURL == null) {
            return classNames;
        }

        classNames = dfsSearchForClassFiles(packageURL.getPath());

        return classNames;
    }

    private static HashSet<String> dfsSearchForClassFiles(String path) {
        HashSet<String> classNames = new HashSet<>();
        Stack<File> stack = new Stack<>();
        stack.push(new File(path));

        while (!stack.isEmpty()) {
            File current = stack.pop();
            if (current.isFile() && current.getName().endsWith(".class")) {
                classNames.add(getClassNameBasePackage(current.getAbsolutePath()));
            } else if (current.isDirectory()) {
                File[] files = current.listFiles();
                if (files != null) {
                    for (File file : files) {
                        stack.push(file);
                    }
                }
            }
        }

        return classNames;
    }

    private static String getClassNameBasePackage(String clazzAbsolutePath) {
        String path = clazzAbsolutePath;
        int targetIndex = path.indexOf("target/classes/");
        if (targetIndex == -1) {
            return "";
        }

        String packageNameWithFile = path.substring(targetIndex + "target/classes/".length());
        String packageName = packageNameWithFile.replace(File.separatorChar, '.').replace(".class", "");

        return packageName;
    }
}
