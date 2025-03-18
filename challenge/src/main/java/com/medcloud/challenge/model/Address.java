package com.medcloud.challenge.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String street;

    @Column(nullable = false, length = 60)
    private String city;
    // state is a reserved key in sql
    @Column(name = "state_", nullable = false, length = 60)
    private String state;

    @Column(nullable = false, length = 60)
    private String country;

    @Column(nullable = false, length = 60)
    private String neighborhood;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Column(name = "number_house",nullable = false)
    private Integer number;

    @OneToOne(mappedBy = "address",fetch = FetchType.LAZY)
    private Patient patient;

}
