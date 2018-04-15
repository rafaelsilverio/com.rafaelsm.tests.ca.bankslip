package com.rafaelsm.tests.ca.bankslip.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rafael on 14/04/2018.
 */
@Entity
@Table(name = "CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<BankSlip> bankSlips = new ArrayList<>();

    public Customer() {

    }

    public Customer(String name) {
        setName(name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BankSlip> getBankSlips() {
        return bankSlips;
    }

    public void setBankSlips(List<BankSlip> bankSlips) {
        this.bankSlips = bankSlips;
    }
}
