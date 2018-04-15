package com.rafaelsm.tests.ca.bankslip.bo;

import com.rafaelsm.tests.ca.bankslip.domain.ResponseMessage;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;

/**
 * Created by Rafael on 14/04/2018.
 */
public interface BankSlipBO {
    ResponseMessage insertBankSlip(BankSlip bankSlip);
    ResponseMessage findAllBankSlip();
    ResponseMessage findDetailedBankSlip(String id);
    ResponseMessage payBankSlip(String id);
    ResponseMessage cancelBankSlip(String id);
}
