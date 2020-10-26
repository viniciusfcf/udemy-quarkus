package br.com.viniciusfcf.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PessoaService {
    
    public void salvar() {
        Pessoa p = new Pessoa();
        p.nome = "Teste 2";
        p.idade = 10;
        p.persist();
    }
}
