package com.iqmsoft.creditcard;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.iqmsoft.address.Address;
import com.iqmsoft.customer.Customer;

public interface CreditCardRepository extends PagingAndSortingRepository<CreditCard, Long> {

	List<CreditCard> findById(Long id);

}
