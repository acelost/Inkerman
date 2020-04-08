package com.acelost.inkermandemo;

import com.acelost.inkerman.Ink;

public class Samples {

    public void printHelloWorld() {
        Ink.message("Hello, world!").print(); // Simple message
    }

    public void printFooBarBaz(String foo, Object bar, int baz) {
        Ink.message(foo, bar, baz).print(); // Composite message
    }

    public void printFooBarBazVariables(String foo, Object bar, int baz) {
        Ink.message("Values of classic variables") // List of variables
                .variable("foo", foo)
                .variable("bar", bar)
                .variable("baz", baz)
                .print();
    }

    public void printWhereAmIFrom() {
        Ink.trace(10).print(); // Print ten call trace
    }

    public void compoundLetter() {
        Ink.message("This is", "sample message")
                .message("printed by")
                .message("Inkerman!")
                .variable("github", "https://github.com/acelost/Inkerman")
                .variable("bintray", "https://bintray.com/beta/#/acelost/Ink")
                .variable("version", "0.0.3")
                .trace(3)
                .print();
    }

}
