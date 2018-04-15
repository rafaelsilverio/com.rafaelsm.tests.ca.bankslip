package com.rafaelsm.tests.ca.bankslip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by Rafael on 14/04/2018.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("httpStatus")
public class ResponseMessage {
    private HttpStatus httpStatus;
    private String message;

    @JsonProperty("bank_slip_detail")
    private BankSlip bankSlip;

    @JsonProperty("bank_slip_list")
    private List<BankSlip> bankSlipList;

    public ResponseMessage() {

    }

    public ResponseMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return httpStatus.toString();
    }

    public void setCode(String code) {
        httpStatus = HttpStatus.valueOf(new Integer(code));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BankSlip getBankSlip() {
        return bankSlip;
    }

    public void setBankSlip(BankSlip bankSlip) {
        this.bankSlip = bankSlip;
    }

    public List<BankSlip> getBankSlipList() {
        return bankSlipList;
    }

    public void setBankSlipList(List<BankSlip> bankSlipList) {
        this.bankSlipList = bankSlipList;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
