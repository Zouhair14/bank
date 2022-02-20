package com.account.bank.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long operationId;
    @Column(nullable = false, updatable = false)
    private Long amount;
    @Column(nullable = false, updatable = false)
    private Date dateOfOperation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Operation() {
    }

    public Operation(Long operationId, Long amount, Date dateOfOperation, Account account) {
        this.amount = amount;
        this.dateOfOperation = dateOfOperation;
        this.account = account;
    }

    public Long getOperationId() {
        return operationId;
    }

    public Long getAmount() {
        return amount;
    }

    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    public Account getAccount() {
        return account;
    }
}
