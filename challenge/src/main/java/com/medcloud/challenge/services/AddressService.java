package com.medcloud.challenge.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.model.Address;
import com.medcloud.challenge.patterns.adapters.Adapter;
import com.medcloud.challenge.repository.AddressRepository;

@Service
public class AddressService {

    // Implement address related services here
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Adapter adapter;

    public AddressDTO getAddressByZipCode(String zipCode) {
        return adapter.addressModelToDto(
                addressRepository.findByZipCode(zipCode));
    }

    public AddressDTO upateAddress(AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressDTO.id()).get();

        address.setStreet(addressDTO.street());
        return adapter.addressModelToDto(
                addressRepository.save(adapter.addressDtoToModel(addressDTO)));

    }

}
