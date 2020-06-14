package com.github.viniciusfcf.ifood.cli;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class MainDoQuarkus implements QuarkusApplication {

    @Inject
    @Channel("parametros")
    Emitter<String> emitter;

    @Override
    public int run(String... args) throws Exception {
        Stream.of(args).forEach(s -> emitter.send(s));
        System.out.println("MainDoQuarkus.run() " + Arrays.toString(args));
        Quarkus.waitForExit();
        return 0;
    }

}

//@QuarkusMain
//public class MainDoQuarkus {
//
//    public static void main(String[] args) {
//        Quarkus.run(MainDoQuarkus2.class, args);
//    }
//
//}
//
//class MainDoQuarkus2 implements QuarkusApplication {
//
//    @Override
//    public int run(String... args) throws Exception {
//        System.out.println("MainDoQuarkus.run(2) " + Arrays.toString(args));
//        Quarkus.waitForExit();
//        return 0;
//    }
//
//}
