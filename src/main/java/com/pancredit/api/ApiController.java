/*
 *  PANCREDIT SYSTEMS LTD
 *  (C) Copyright PanCredit Systems Ltd 2021
 *
 *  COPYRIGHT NOTICE
 *  ---------------------------------
 *  The contents of this file are protected by copyright. Any unauthorised
 *  copying, duplication of its contents are in breach of the copyright.
 *
 *  Last Checked In By: $Author$
 *  Date Checked In:    $Date$
 *  Name and Version:   $Id$
 *
 *  Log messages:       $Log$
 * 
 */

package com.pancredit.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancredit.api.model.Transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/ApiServlet")

public class ApiController {

    @Value("${data.file-path}")
    private String DATA_JSON_PATH;

    @Autowired
    private ObjectMapper objectMapper;

    // Read all transactions
    private List<Transaction> readData() throws IOException {
	String jsonData = new String(
		Files.readAllBytes(Paths.get(DATA_JSON_PATH)));
	return objectMapper.readValue(jsonData,
		new TypeReference<List<Transaction>>() {
		});
    }

    // Write all transactions
    private void writeData(List<Transaction> transactions) throws IOException {
	objectMapper.writeValue(Paths.get(DATA_JSON_PATH).toFile(),
		transactions);
    }

    // READ all transactions
    @Operation(summary = "Get all transactions", description = "Returns a list of all transactions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions()
	    throws IOException {
	return ResponseEntity.ok(readData());
    }

    // DELETE a transaction by ID
    @Operation(summary = "Delete a transactions", description = "Remove a target transaction.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted a transaction")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable UUID id)
	    throws IOException {
	List<Transaction> transactions = readData();
	List<Transaction> updatedTransactions = transactions.stream()
		.filter(transaction -> !transaction.getId().equals(id))
		.collect(Collectors.toList());
	if (transactions.size() == updatedTransactions.size()) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	} else {
	    writeData(updatedTransactions);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
    }

    // CREATE a new transaction
    @Operation(summary = "Create a transactions", description = "Create a target transaction.")
    @ApiResponse(responseCode = "200", description = "Successfully created a transaction")
    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(
	    @RequestBody Transaction newTransaction) throws IOException {
	List<Transaction> transactions = readData();
	transactions.add(newTransaction); // Add the new transaction
	writeData(transactions);
	return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    // UPDATE a transaction by ID
    @Operation(summary = "Update a transactions", description = "Update a target transaction.")
    @ApiResponse(responseCode = "200", description = "Successfully updated a transaction")
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable UUID id,
	    @RequestBody Transaction updatedTransaction) throws IOException {
	List<Transaction> transactions = readData();
	for (int i = 0; i < transactions.size(); i++) {
	    if (transactions.get(i).getId().equals(id)) {
		transactions.set(i, updatedTransaction); // Update the
							 // transaction
		writeData(transactions);
		return ResponseEntity.ok(updatedTransaction);
	    }
	}
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreDestroy
    // closed.
    public void destroy() {
	// We can perform cleanup here if needed.
	// do nothing.
    }
}