package com.rafaelsm.tests.ca.bankslip.test;
import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rafael on 15/04/2018.
 */
public class EntityTest {

    @Test
    public void bankSlipNoFineCalculation() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setValue(1000);
        bankSlip.setDueDate(new Date());
        assertEquals(null, bankSlip.getFine());
    }

    @Test
    public void bankSlipBefore10DaysFineCalculation() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setValue(1000);
        bankSlip.setDueDate(DateUtils.addDays(new Date(),-5));
        assertEquals(new Long(25), bankSlip.getFine());
    }

    @Test
    public void bankSlipAfter10DaysFineCalculation() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setValue(1000);
        bankSlip.setDueDate(DateUtils.addDays(new Date(),-15));
        assertEquals(new Long(150), bankSlip.getFine());
    }
}
