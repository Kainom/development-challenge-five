package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.CepApiDTO;
import com.medcloud.challenge.exceptions.err.CepInvalidException;

@Service
public class CepService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public CepApiDTO getAddressByCep(String cep) {
        CepApiDTO cepApiDTO = restTemplate.getForObject(VIA_CEP_URL.replace("{cep}", cep), CepApiDTO.class);
        if (cepApiDTO != null && Boolean.TRUE.equals(cepApiDTO.getErro())) {
            throw new CepInvalidException("Invalid CEP provided.");
        }
        return cepApiDTO;
    }
}