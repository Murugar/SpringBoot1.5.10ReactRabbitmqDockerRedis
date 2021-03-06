package com.iqmsoft.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.iqmsoft.address.Address;
import com.iqmsoft.creditcard.CreditCard;



@Projection(name = "inlineAddress", types = { Customer.class })
public interface InlineAddress {

	
	
	String getLastName();
	String getFirstName();
	String getGender();
	String getEmail();	
	String getId();
	String getThumbnail();
	String getLarge();
	String getMedium();
	
	
	
	@Value("#{target.account.id}")
	Long getAccountId();
	@Value("#{target.account.addresses}")
	Address getAddress();
	@Value("#{target.account.creditCards}")
	CreditCard getCreditCards();
	
}
