package com.tree.crust.example;

import com.tree.crust.fw.annotations.Autowired;
import com.tree.crust.fw.annotations.Component;

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
