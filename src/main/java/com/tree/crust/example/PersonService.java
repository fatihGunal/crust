package com.tree.crust.example;

import com.tree.crust.fw.annotations.Component;

@Component
public class PersonService {
    public void printService() {
        System.out.println("PersonService.printService()");
    }
}
