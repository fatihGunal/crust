package com.tree.crust.example;


import com.tree.crust.IoC.annotations.Autowired;
import com.tree.crust.IoC.annotations.Component;

/**
 * PersonController
 */
@Component
public class PersonController {

    @Autowired
    private PersonService personService;

    public void getWebService() {
        personService.printService();
    }
}
