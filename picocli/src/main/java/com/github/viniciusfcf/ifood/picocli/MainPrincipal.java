package com.github.viniciusfcf.ifood.picocli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = { Ola.class, Tchau.class })
public class MainPrincipal {

}

@Command(name = "ola", description = "Imprime nome do usuario")
class Ola implements Runnable {

    @Option(names = { "-n", "--name" }, description = "Nome Completo do usuario", defaultValue = "Vinicius")
    String nome;

    @Override
    public void run() {
        System.out.println("Ola.run() " + nome);
    }
}

@Command(name = "tchau", description = "Imprime Tchau e o nome do usuario")
class Tchau implements Runnable {

    @Option(names = { "-n", "--name" }, description = "Nome Completo do usuario", required = true)
    String nome;

    @Override
    public void run() {
        System.out.println("Tchau.run() " + nome);
    }
}