package com.tree.crust.IoC;

import com.tree.crust.CrustApplication;
import com.tree.crust.example.PersonController;
import org.junit.Test;


public class ContainerTest {

    @Test
    public void testInitializeContainer() {
        Container container = Container.initialize(CrustApplication.class);

        PersonController controller = (PersonController) container
                .getSingletonObjects()
                .get("com.tree.crust.example.PersonController");
        controller.getWebService();
    }
}