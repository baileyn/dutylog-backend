package com.njbailey.dutylogbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.njbailey.dutylogbackend.model.User;
import com.njbailey.dutylogbackend.model.security.Authority;
import com.njbailey.dutylogbackend.model.security.AuthorityName;
import com.njbailey.dutylogbackend.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class DutylogBackendApplication implements CommandLineRunner  {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    public static void main(String[] args) {
        SpringApplication.run(DutylogBackendApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
    	User user = new User();
    	user.setUsername("baileyn");
    	user.setPassword(passwordEncoder.encode("password"));
    	user.setFirstName("Nicholas");
    	user.setLastName("Bailey");
    	user.setEmail("baileyn18@mycu.concord.edu");
    	Authority authority = new Authority();
    	authority.setName(AuthorityName.ROLE_ADMIN);
    	
    	authority.getUsers().add(user);
    	user.getAuthorities().add(authority);
    	
    	userRepository.save(user);
    }
}
