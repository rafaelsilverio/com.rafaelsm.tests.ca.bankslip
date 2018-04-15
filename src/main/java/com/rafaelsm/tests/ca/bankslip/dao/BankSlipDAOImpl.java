package com.rafaelsm.tests.ca.bankslip.dao;

import com.rafaelsm.tests.ca.bankslip.entity.BankSlip;
import com.rafaelsm.tests.ca.bankslip.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by Rafael on 13/04/2018.
 */
@Component
public class BankSlipDAOImpl implements BankSlipDAO{
    private Session session;

    private void startSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    private void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public void insert(BankSlip bankSlip) {
        startSession();
        session.getTransaction().begin();
        if (bankSlip.getCustomer() != null) {
            session.persist(bankSlip.getCustomer());
        }
        session.persist(bankSlip);
        session.getTransaction().commit();
        closeSession();
    }

    @Override
    public List<BankSlip> find() {
        startSession();
        List<BankSlip> bankSlip = session.createQuery("from BankSlip").list();
        closeSession();
        return bankSlip;
    }

    @Override
    public BankSlip findById(UUID id) {
        startSession();
        BankSlip persistedBankSlip = session.get(BankSlip.class, id);
        closeSession();
        return persistedBankSlip;
    }

    @Override
    public void update(BankSlip bankSlip) {
        startSession();
        session.getTransaction().begin();
        session.update(bankSlip);
        session.getTransaction().commit();
        closeSession();
    }
}
