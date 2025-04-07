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

/**
 * This class is responsible for managing address-related
 * operations,
 * including retrieving address information and validating address
 * data.
 * 
 * @see CepService object for retrieving address information from
 *      the ViaCEP API
 * @see Adapter object for converting between Address and AddressDTO
 *
 */
@Service
public class AddressService {

    // Implement address related services here
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CepService cepService;

    @Autowired
    private Adapter adapter;

    /**
     * @param zipCode cep to get address
     * @return AddressDTO object with address information
     *         This method retrieves address information from the database by zip
     *         code
     *         and converts it to AddressDTO using the adapter.
     */
    public AddressDTO getAddressByZipCode(String zipCode) {
        return adapter.addressModelToDto(
                addressRepository.findByZipCode(zipCode));
    }

    /**
     * @param addressDTO AddressDTO object with new values to update
     * @param address    Address object to be updated
     * @return Address object with updated values
     *         This method updates the address object with new values from the
     *         AddressDTO object. It checks if the new values are not null and
     *         updates only the fields that are not null. It also checks if the
     *         address is valid by calling the checkFieldOfAddress method.
     *         If the address is not valid, it throws an AddressInvalidException.
     * @throws AddressInvalidException if the address is not valid
     * @see checkFieldOfAddress method to check if the address is valid
     *      This update address of patient
     */
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

        // verifica se o cep esta correto e se condiz com os dados da
        // city,state,neighborhood e street
        if (!checkFieldOfAddress(addressDTO.zipCode(), addressDTO)) {
            throw new AddressInvalidException("Invalid address");
        }

        return address;

    }

    /**
     * 
     * @param cep        cep to get address
     * @param addressDTO AddressDTO object with address information
     * @return Boolean true if the address is valid, false otherwise
     *         This method checks if the address fields (street, neighborhood,
     *         city, state) match the information retrieved from the ViaCEP API
     *         for the provided cep.
     */
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
