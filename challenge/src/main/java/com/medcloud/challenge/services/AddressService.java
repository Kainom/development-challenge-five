package com.medcloud.challenge.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.CepApiDTO;
import com.medcloud.challenge.exceptions.err.AddressInvalidException;
import com.medcloud.challenge.model.Address;
import com.medcloud.challenge.patterns.adapters.Adapter;
import com.medcloud.challenge.repository.AddressRepository;

@Service
public class AddressService {

    // Implement address related services here
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CepService cepService;

    @Autowired
    private Adapter adapter;

    public AddressDTO getAddressByZipCode(String zipCode) {
        return adapter.addressModelToDto(
                addressRepository.findByZipCode(zipCode));
    }

    public Address updateAddress(AddressDTO addressDTO, Address address) {


// verifica os campos para atualizar somente o que nao esta null
        if (addressDTO.city() != null)
            address.setCity(addressDTO.city());
        if (addressDTO.state() != null)
            address.setState(addressDTO.state());
        if (addressDTO.number() != null)
            address.setNumber(addressDTO.number());
        if (addressDTO.neighborhood() != null)
            address.setNeighborhood(addressDTO.neighborhood());
        if (addressDTO.zipCode() != null)
            address.setZipCode(addressDTO.zipCode());
        if (addressDTO.street() != null)
            address.setStreet(addressDTO.street());


// verifica se o cep esta correto e se condiz com os dados da city,state,neighborhood e street
        if (!checkFieldOfAddress(addressDTO.zipCode(), addressDTO)) {
            throw new AddressInvalidException("Invalid address");
        }

        return address;

    }

    public Boolean checkFieldOfAddress(String cep, AddressDTO addressDTO) {
        CepApiDTO check = cepService.getAddressByCep(cep);
        Boolean okay = true;
        if (!check.getLogradouro().equals(addressDTO.street()))
            okay = false;
        if (!check.getBairro().equals(addressDTO.neighborhood()))
            okay = false;
        if (!check.getLocalidade().equals(addressDTO.city()))
            okay = false;
        if (!check.getEstado().equals(addressDTO.state()))
            okay = false;

        return okay;
    }
}
