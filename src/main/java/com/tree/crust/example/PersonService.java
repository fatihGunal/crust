package com.tree.crust.example;


import com.tree.crust.IoC.annotations.Component;

@Component
public class PersonService {
    public void printService() {
        System.out.println("Dependency injected!");
    }
}
