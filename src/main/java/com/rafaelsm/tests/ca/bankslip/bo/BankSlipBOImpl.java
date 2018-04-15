package com.rafaelsm.tests.ca.bankslip.bo;

import com.rafaelsm.tests.ca.bankslip.dao.BankSlipDAO;
import com.rafaelsm.tests.ca.bankslip.domain.ResponseMessage;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import com.rafaelsm.tests.ca.bankslip.entity.Customer;
import com.rafaelsm.tests.ca.bankslip.enums.BankSlipStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Rafael on 14/04/2018.
 */
@Component
@ComponentScan("com.rafaelsm.tests.ca.bankslip")
public class BankSlipBOImpl implements BankSlipBO {
    public static final String REQUEST_SUCCESS = "OK.";
    public static  final String SAVE_SUCCESS = "BankSlip created.";
    public static  final String INVALID_FIELDS = "Invalid BankSlip provided: fields (%s) are invalid.";
    public static  final String SAVE_EMPTY = "BankSlip not provided in the request body.";
    public static  final String INVALID_ID = "Invalid id provided - it must be a valid UUID.";
    public static  final String NOT_FOUND = "BankSlip not found with the specified id";
    public static  final String PAID_SUCCESS = "Bankslip paid.";
    public static  final String CANCEL_SUCCESS = "Bankslip canceled.";

    @Autowired
    private BankSlipDAO bankSlipDAO;

    @Override
    public ResponseMessage insertBankSlip(BankSlip bankSlip) {
        bankSlip.setCustomer(new Customer(bankSlip.getCustomerNameJsonField()));
        List<String> invalidFields = new ArrayList<>();
        if (StringUtils.isEmpty(bankSlip.getCustomerNameJsonField())) {
            invalidFields.add("customer");
        }
        if (bankSlip.getDueDate() == null) {
            invalidFields.add("due_date");
        }
        if (bankSlip.getValue() == null) {
            invalidFields.add("total_in_cents");
        }
        if (bankSlip.getStatus() == null) {
            invalidFields.add("status");
        }

        ResponseMessage responseMessage;
        if (!invalidFields.isEmpty()) {

            //None of the required fields were sent, hence UNPROCESSABLE_ENTITY
            if (invalidFields.size() < 4) {
                responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST, String.format(INVALID_FIELDS, StringUtils.join(invalidFields, ",")));
            } else {
                responseMessage = new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY, SAVE_EMPTY);
            }
        } else {
            bankSlipDAO.insert(bankSlip);
            responseMessage = new ResponseMessage(HttpStatus.CREATED, SAVE_SUCCESS);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage findAllBankSlip() {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK, REQUEST_SUCCESS);
        responseMessage.setBankSlipList(bankSlipDAO.find());
        return responseMessage;
    }

    @Override
    public ResponseMessage findDetailedBankSlip(String id) {
        ResponseMessage responseMessage = fillBankSlip(id);
        if (responseMessage.getBankSlip() != null) {
            responseMessage.setHttpStatus(HttpStatus.OK);
            responseMessage.setMessage(REQUEST_SUCCESS);
            responseMessage.setBankSlip(responseMessage.getBankSlip());
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage payBankSlip(String id) {
        ResponseMessage responseMessage = fillBankSlip(id);
        if (responseMessage.getBankSlip() != null) {
            responseMessage.setHttpStatus(HttpStatus.NO_CONTENT);
            responseMessage.setMessage(PAID_SUCCESS);
            responseMessage.getBankSlip().setStatus(BankSlipStatus.PAID);
            bankSlipDAO.update(responseMessage.getBankSlip());
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage cancelBankSlip(String id) {
        ResponseMessage responseMessage = fillBankSlip(id);
        if (responseMessage.getBankSlip() != null) {
            responseMessage.setHttpStatus(HttpStatus.NO_CONTENT);
            responseMessage.setMessage(CANCEL_SUCCESS);
            responseMessage.getBankSlip().setStatus(BankSlipStatus.CANCELED);
            bankSlipDAO.update(responseMessage.getBankSlip());
        }
        return responseMessage;
    }

    /**
     * Method that check if id is a valid UUID and, if valid, corresponds to a persisted
     * BankSlip. If any id is invalid or if is not on the database, a ResponseMessage
     * will be returned filled with the correct message and code. If the BankSlip exists
     * it will be set on the bankSlip attribute of the returned ResponseMessage.
     *
     * @param id
     * @return
     */
    private ResponseMessage fillBankSlip (String id) {
        UUID uuid;
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException exception){
            responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST, INVALID_ID);
            return responseMessage;
        }
        BankSlip bankSlip = bankSlipDAO.findById(uuid);
        if (bankSlip == null) {
            responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND, NOT_FOUND);
        } else {
            responseMessage.setBankSlip(bankSlip);
        }
        return responseMessage;
    }
}
