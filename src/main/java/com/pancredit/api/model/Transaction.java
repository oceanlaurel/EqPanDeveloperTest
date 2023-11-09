package com.pancredit.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    @JsonProperty("Id")
    private UUID id;
    @JsonProperty("ApplicationId")
    private Integer applicationId;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Summary")
    private String summary;
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("PostingDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postingDate;
    @JsonProperty("IsCleared")
    private Boolean isCleared;
    @JsonProperty("ClearedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime clearedDate;
    private TransactionStatus status;

    public Transaction() {
	super();
    }

    public Transaction(UUID id, Integer applicationId, String type,
	    String summary, BigDecimal amount, LocalDateTime postingDate,
	    Boolean isCleared, LocalDateTime clearedDate,
	    TransactionStatus status) {
	super();
	this.id = id;
	this.applicationId = applicationId;
	this.type = type;
	this.summary = summary;
	this.amount = amount;
	this.postingDate = postingDate;
	this.isCleared = isCleared;
	this.clearedDate = clearedDate;
	this.status = status;
    }

    // We might want to use a method to set the status based on the isCleared
    // field
    public void determineStatus() {
	if (this.isCleared != null) {
	    this.status = this.isCleared ? TransactionStatus.COMPLETED
		    : TransactionStatus.PENDING;
	}
    }

    // Assuming TransactionStatus is an inner enum as previously
    public enum TransactionStatus {
	PENDING, COMPLETED
    }

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public Integer getApplicationId() {
	return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
	this.applicationId = applicationId;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getSummary() {
	return summary;
    }

    public void setSummary(String summary) {
	this.summary = summary;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public LocalDateTime getPostingDate() {
	return postingDate;
    }

    public void setPostingDate(LocalDateTime postingDate) {
	this.postingDate = postingDate;
    }

    public Boolean getIsCleared() {
	return isCleared;
    }

    public void setIsCleared(Boolean isCleared) {
	this.isCleared = isCleared;
    }

    public LocalDateTime getClearedDate() {
	return clearedDate;
    }

    public void setClearedDate(LocalDateTime clearedDate) {
	this.clearedDate = clearedDate;
    }

    public TransactionStatus getStatus() {
	return status;
    }

    public void setStatus(TransactionStatus status) {
	this.status = status;
    }

}