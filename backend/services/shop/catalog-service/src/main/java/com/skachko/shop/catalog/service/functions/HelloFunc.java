package com.skachko.shop.catalog.service.functions;

import java.util.function.Supplier;

//curl localhost:9000/helloFunc -H "Content-Type: text/plain"
public class HelloFunc implements Supplier<String> {

    @Override
    public String get() {
        return "Hello function!!";
    }

}
