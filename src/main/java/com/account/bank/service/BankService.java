package com.account.bank.service;

import com.account.bank.exception.UserNotFoundException;
import com.account.bank.model.Account;
import com.account.bank.model.Operation;
import com.account.bank.repo.AccountRepo;
import com.account.bank.repo.OperationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private AccountRepo accountRepo;
    private OperationRepo operationRepo;

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
        accountRepo.deleteById(id);
    }
    public Operation saveMoneyOperation(Operation operation){
        operation.getAccount().setBalance(operation.getAccount().getBalance() + operation.getAmount());
        return operationRepo.save(operation);
    }
    public Operation retrieveMoneyOperation(Operation operation) throws Exception {
        if (operation.getAccount().getBalance() - operation.getAmount()>=0) {
            operation.getAccount().setBalance(operation.getAccount().getBalance() - operation.getAmount());
            return operationRepo.save(operation);
        }else{
            throw new Exception("you do not have enough money");
        }
    }
    public Operation retrieveAllMoneyOperation(Operation operation){
        operation.setAmount(operation.getAccount().getBalance());
        operation.getAccount().setBalance(0l);
        return operationRepo.save(operation);
    }
    public List<Operation> allOperation(){
        return operationRepo.findAll();
    }

}
