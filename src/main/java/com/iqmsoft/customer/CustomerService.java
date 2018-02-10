package com.iqmsoft.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iqmsoft.account.Account;
import com.iqmsoft.account.AccountRepository;
import com.iqmsoft.address.Address;
import com.iqmsoft.address.AddressRepository;
import com.iqmsoft.creditcard.CreditCard;
import com.iqmsoft.creditcard.CreditCardRepository;


import net.andreinc.mockneat.unit.id.UUIDs;

@Service
public class CustomerService {
	private Log log = LogFactory.getLog(getClass());
	
	private String randomUserApi;
	private RestTemplate restTemplate;
	private CustomerRepository customerRepository;
	private AccountRepository accountRepository;
	private AddressRepository addressRepository;
	private CreditCardRepository creditCardRepository;
	private ApplicationEventPublisher applicationEventPublisher;

	public CustomerService(@Value("${randomusersapi.url}") final String randomUserApi, final RestTemplate restTemplate,
			final CustomerRepository customerRepository, final AccountRepository accountRepository,
			final AddressRepository addressRepository, final CreditCardRepository creditCardRepository,
			ApplicationEventPublisher applicationEventPublisher) {
		this.randomUserApi = randomUserApi;
		this.restTemplate = restTemplate;
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.creditCardRepository = creditCardRepository;
		this.accountRepository = accountRepository;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	

	public Account addACustomer(Customer customer) {
		Account account = new Account(new UUIDs().supplier().get());
		account.setCustomer(customer);
		Account savedAccount = accountRepository.save(account);

		Customer accountCustomer = savedAccount.getCustomer();
		accountCustomer.setAccount(savedAccount);
		Customer savedCustomer = customerRepository.save(accountCustomer);
		this.applicationEventPublisher.publishEvent(new CustomerCreationEvent<Customer>(savedCustomer));
		return savedAccount;

	}

	public void deleteACustomerById(long id) {
		List<Customer> customerList = customerRepository.findById(id);
		customerList.stream().forEach(customer -> {
			Long accountId = customer.getAccount().getId();
			accountRepository.delete(accountId);

		});

		Optional.ofNullable(customerList.get(0)).ifPresent(c -> {
			this.applicationEventPublisher.publishEvent(new CustomerDeletionEvent<Customer>(c));
		}

		);

	}

}
