package com.github.viniciusfcf.ifood.cadastro.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdicionarRestauranteDTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    public String cnpj;

    @Min(5)
    @Max(50)
    public String nomeFantasia;

    @NotNull
    public LocalizacaoDTO localizacao;

}
