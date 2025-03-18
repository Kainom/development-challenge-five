package com.medcloud.challenge.dtos;

import lombok.Data;

@Data
public class CepApiDTO {
   private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private Boolean erro; 


}
