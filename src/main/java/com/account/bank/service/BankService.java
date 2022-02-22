package com.account.bank.service;

import com.account.bank.exception.UserNotFoundException;
import com.account.bank.model.Account;
import com.account.bank.model.Operation;
import com.account.bank.repo.AccountRepo;
import com.account.bank.repo.OperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class BankService {
    private final AccountRepo accountRepo;
    private final OperationRepo operationRepo;

    @Autowired
    public BankService(AccountRepo accountRepo, OperationRepo operationRepo) {
        this.accountRepo = accountRepo;
        this.operationRepo = operationRepo;
    }

    public Account addAccount(Account account){
        return accountRepo.save(account);
    }
    public List<Account> allAccount(){
        return accountRepo.findAll();
    }
    public Account oneAccount(Long id){
        return accountRepo.getOneAccountById(id).orElseThrow(()-> new UserNotFoundException("User "+id+" was not found"));
    }
    public void deleteAccount(Long id){
        oneAccount(id);
        accountRepo.deleteById(id);
    }
    public Operation serviceSaveMony(Operation operation){
        Account account = oneAccount(operation.getAccount().getAccountId());
        operation.setAccount(account);
        operation.setDateOfOperation(new Date(System.currentTimeMillis()));
        operation.getAccount().setBalance(account.getBalance() + operation.getAmount());
        addAccount(operation.getAccount());
        return operation;
    }
    public Operation saveMoneyOperation(Operation operation){
        serviceSaveMony(operation);
        return operationRepo.save(operation);
    }
    public Operation retrieveMoneyOperation(Operation operation) throws Exception {
        Account account = oneAccount(operation.getAccount().getAccountId());
        operation.setAccount(account);
        operation.setDateOfOperation(new Date(System.currentTimeMillis()));
        if (operation.getAccount().getBalance() - operation.getAmount()>=0) {
            operation.getAccount().setBalance(operation.getAccount().getBalance() - operation.getAmount());
            addAccount(operation.getAccount());
            return operationRepo.save(operation);
        }else{
            throw new UserNotFoundException("you do not have enough money");
        }
    }
    public Operation retrieveAllMoneyOperation(Operation operation){
        Account account = oneAccount(operation.getAccount().getAccountId());
        operation.setAccount(account);
        operation.setDateOfOperation(new Date(System.currentTimeMillis()));
        operation.setAmount(operation.getAccount().getBalance());
        operation.getAccount().setBalance(0l);
        addAccount(operation.getAccount());
        return operationRepo.save(operation);
    }
    public List<Operation> allOperation(){
        return operationRepo.findAll();
    }
    public List<Operation> findOperationsByAccount(Long idAccount){
        Account account = oneAccount(idAccount);
        return account.getOperations();
    }

}
