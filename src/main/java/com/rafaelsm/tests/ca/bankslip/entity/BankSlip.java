package com.rafaelsm.tests.ca.bankslip.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rafaelsm.tests.ca.bankslip.enums.BankSlipStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rafael on 13/04/2018.
 */
@Entity
@Table(name = "BANKSLIP", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("customer_object")
public class BankSlip {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    @JsonProperty("due_date")
    private Date dueDate;

    @Column(name = "VALUE")
    @JsonProperty("total_in_cents")
    private Integer value;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private BankSlipStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonProperty("customer_object")
    private Customer customer;

    @Transient
    @JsonProperty("customer")
    private String customerName;

    public BankSlip() {

    }

    public BankSlip(Date dueDate, Integer value, BankSlipStatus status, Customer customer) {
        setDueDate(dueDate);
        setValue(value);
        setStatus(status);
        setCustomer(customer);
    }

    public Long getFine() {
        if (value != null && dueDate != null) {
            Date currentDate = new Date();
            long timeDiff = currentDate.getTime() - dueDate.getTime();
            long numberOfDiffDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if (numberOfDiffDays <= 0) {
                return null;
            } else if (numberOfDiffDays <= 10) {
                return ((long) (value * 0.005 * numberOfDiffDays));
            } else if (numberOfDiffDays > 10) {
                return ((long) (value * 0.01 * numberOfDiffDays));
            }
        }
        return null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public BankSlipStatus getStatus() {
        return status;
    }

    public void setStatus(BankSlipStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer == null ? "" : customer.getName();
    }

    public String getCustomerNameJsonField() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
