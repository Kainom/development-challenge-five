package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.CepApiDTO;
import com.medcloud.challenge.exceptions.err.CepInvalidException;

/**
 * 
 *      This class is responsible for retrieving address information
 *      from the ViaCEP API using a provided cep (postal code).
 * @see RestTemplate object for making HTTP requests
 *      VIA_CEP_URL url  for the ViaCEP API endpoint
 *
 */
@Service
public class CepService {


    @Autowired
    private RestTemplate restTemplate;

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    /**
     * @param cep cep to get address
     * @return AddressDTO object with address information
     * @throws CepInvalidException if the provided cep is invalid
     *  This method retrieves address information from the ViaCEP API
     *              using the provided cep.
     */
    public CepApiDTO getAddressByCep(String cep) {
        CepApiDTO cepApiDTO = restTemplate.getForObject(VIA_CEP_URL.replace("{cep}", cep), CepApiDTO.class);
        if (cepApiDTO != null && Boolean.TRUE.equals(cepApiDTO.getErro())) {
            throw new CepInvalidException("Invalid CEP provided.");
        }
        return cepApiDTO;
    }
}