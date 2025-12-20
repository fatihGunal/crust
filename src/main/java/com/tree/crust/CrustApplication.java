package com.tree.crust;

import com.tree.crust.IoC.Container;
import com.tree.crust.example.PersonController;

/**
 * Hello world!
 *
 */
public class CrustApplication {
    public static void main(String[] args) {
        run(CrustApplication.class);
    }

    public static void run(Class<?> primarySource) {
        Container.initialize(primarySource);
//        Container container = Container.initialize(primarySource);
//
//        PersonController controller = (PersonController) container
//                .getSingletonObjects()
//                .get("com.tree.crust.example.PersonController");
//        controller.getWebService();
    }
}
