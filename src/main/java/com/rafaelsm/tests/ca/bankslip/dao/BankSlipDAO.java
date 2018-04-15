package com.rafaelsm.tests.ca.bankslip.dao;

import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;

import java.util.List;
import java.util.UUID;

/**
 * Created by Rafael on 13/04/2018.
 */
public interface BankSlipDAO {
    void insert(BankSlip bankSlip);
    List<BankSlip> find();
    BankSlip findById(UUID id);
    void update(BankSlip bankSlip);
}
