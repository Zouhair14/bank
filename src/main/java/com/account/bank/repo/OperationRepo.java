package com.account.bank.repo;

import com.account.bank.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepo extends JpaRepository<Operation,Long> {

    /*@Query("SELECT u FROM Operation u")
    public List<Operation> findAllOperations();*/
}
