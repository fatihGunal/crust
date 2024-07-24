package com.tree.crust;

import java.io.IOException;
import java.util.HashSet;
import com.tree.crust.fw.ClassLocator;
import com.tree.crust.fw.LeafFactory;
import com.tree.crust.fw.WebComponent;

/**
 * Hello world!
 *
 */
public class CrustApplication {
    public static void main(String[] args) {
        run(CrustApplication.class);
    }

    public static void run(Class<?> primarySource) {
        try {
            HashSet<String> clazzNames = ClassLocator.getClassNamesFrom(primarySource);

            // TODO: change this to factory instead of create object here
            LeafFactory leafFactory = new LeafFactory();
            leafFactory.createContainer(clazzNames);
            leafFactory.printObjects();
            // leafFactory.manualTestOne();
            // leafFactory.manualTestTwo();

            WebComponent wComponent = new WebComponent();
            wComponent.handleSocket();
        } catch (IOException | IllegalAccessException ex) {
            System.out.println(ex);
        }
    }

    public CrustApplication() {
    }
}
