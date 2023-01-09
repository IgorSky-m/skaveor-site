package com.skachko.news.service.functions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Supplier;

//curl localhost:9000/jsonFunc -H "Content-Type: application/json"
public class JsonFunc implements Supplier<List<Employee>> {


    @Override
    public List<Employee> get() {
        return List.of(
                new Employee(1, "James"),
                new Employee(2, "Nick")
        );
    }
}


@Getter
@Setter
@AllArgsConstructor
class Employee{
    private int id;
    private String name;
}
