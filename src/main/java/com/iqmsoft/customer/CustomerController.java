package com.iqmsoft.customer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/customers")
public class CustomerController {

	private final Log log = LogFactory.getLog(getClass());
	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService userService) {
		this.customerService=userService;

	}

	
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteACustomerById(@PathVariable long id) {
		customerService.deleteACustomerById(id);

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void addCustomer(@RequestBody Customer customer)
	{
		
		customerService.addACustomer(customer);
	}
	
	
	
}
