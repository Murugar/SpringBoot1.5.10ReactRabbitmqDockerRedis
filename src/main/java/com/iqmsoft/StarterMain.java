package com.iqmsoft;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.iqmsoft.account.Account;
import com.iqmsoft.address.Address;
import com.iqmsoft.customer.Customer;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class StarterMain {

	public static void main(String[] args) {
		SpringApplication.run(StarterMain.class, args);
	}

	@Autowired private RepositoryRestConfiguration repositoryRestConfiguration;

    @PostConstruct
    public void exposeIds() {
        this.repositoryRestConfiguration.exposeIdsFor(new Class[] {Customer.class,Address.class,Account.class});
        this.repositoryRestConfiguration.setDefaultPageSize(10);
        
    }

}
