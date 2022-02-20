package com.account.bank.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private Long balance;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Operation> operations;

    public Account() {
    }

    public Account(Long accountId, Long balance, Set<Operation> operations) {
        this.balance = balance;
        this.operations = operations;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

}
