package com.rafaelsm.tests.ca.bankslip.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsm.tests.ca.bankslip.bo.BankSlipBOImpl;
import com.rafaelsm.tests.ca.bankslip.controller.BankSlipController;
import com.rafaelsm.tests.ca.bankslip.domain.ResponseMessage;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import com.rafaelsm.tests.ca.bankslip.entity.Customer;
import com.rafaelsm.tests.ca.bankslip.enums.BankSlipStatus;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rafael on 15/04/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankSlipController.class)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveBankSlipValidationMessageEmptyEntity() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankSlip bankSlip = new BankSlip();
        String jsonParam = mapper.writeValueAsString(bankSlip);
        this.mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON).content(jsonParam))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("message").value(BankSlipBOImpl.SAVE_EMPTY));
    }

    @Test
    public void saveBankSlipValidationMessageEmptyValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankSlip bankSlip = new BankSlip(new Date(), null, null, null);
        String jsonParam = mapper.writeValueAsString(bankSlip);
        String expectedErrorMesaage = String.format(BankSlipBOImpl.INVALID_FIELDS, StringUtils.joinWith(",", "customer", "total_in_cents", "status"));
        this.mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON).content(jsonParam))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(expectedErrorMesaage));
    }

    @Test
    public void payBankSlipSuccessfully() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BankSlip bankSlip = new BankSlip(new Date(), 1000, BankSlipStatus.PENDING, new Customer("Foo Bar"));
        String jsonParam = mapper.writeValueAsString(bankSlip);

        //Persist BankSlip
        this.mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON).content(jsonParam))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value(BankSlipBOImpl.SAVE_SUCCESS));

        //Get the list of persisted BankSlips
        ResultActions resultActions = this.mockMvc.perform(get("/rest/bankslips"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value(BankSlipBOImpl.REQUEST_SUCCESS));
        ResponseMessage responseMessage = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ResponseMessage.class);

        //Pay the first one
        String firsBankSlipId = responseMessage.getBankSlipList().get(0).getId().toString();
        this.mockMvc.perform(put("/rest/bankslips/" + firsBankSlipId + "/pay"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
