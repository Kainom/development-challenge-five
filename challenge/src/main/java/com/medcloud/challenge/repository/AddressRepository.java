package com.medcloud.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medcloud.challenge.model.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public Address findByZipCode(String zipCode);

}
