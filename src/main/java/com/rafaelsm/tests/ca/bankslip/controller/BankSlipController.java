package com.rafaelsm.tests.ca.bankslip.controller;

import com.rafaelsm.tests.ca.bankslip.bo.BankSlipBO;
import com.rafaelsm.tests.ca.bankslip.domain.ResponseMessage;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created by Rafael on 13/04/2018.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan("com.rafaelsm.tests.ca.bankslip")
public class BankSlipController {

    @Autowired
    private BankSlipBO bankSlipBO;

    @RequestMapping(value = "/rest/bankslips", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseMessage> createBankSlip(@RequestBody BankSlip bankSlip) {
        ResponseMessage responseMessage = bankSlipBO.insertBankSlip(bankSlip);
        return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpStatus());
    }

    @RequestMapping(value = "/rest/bankslips", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseMessage> findAllBankSlip() {
        ResponseMessage responseMessage = bankSlipBO.findAllBankSlip();
        return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpStatus());
    }

    @RequestMapping(value = "/rest/bankslips/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseMessage> detailedBankSlip(@PathVariable String id) {
        ResponseMessage responseMessage = bankSlipBO.findDetailedBankSlip(id);
        return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpStatus());
    }

    @RequestMapping(value = "/rest/bankslips/{id}/pay", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseMessage> payBankSlip(@PathVariable String id) {
        ResponseMessage responseMessage = bankSlipBO.payBankSlip(id);
        return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpStatus());
    }

    @RequestMapping(value = "/rest/bankslips/{id}/cancel", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseMessage> cancellBankSlip(@PathVariable String id) {
        ResponseMessage responseMessage = bankSlipBO.cancelBankSlip(id);
        return new ResponseEntity<ResponseMessage>(responseMessage, responseMessage.getHttpStatus());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BankSlipController.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
    }
}
