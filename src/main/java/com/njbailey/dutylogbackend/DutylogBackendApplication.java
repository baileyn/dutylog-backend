package com.njbailey.dutylogbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.njbailey.dutylogbackend.model.User;
import com.njbailey.dutylogbackend.model.security.Authority;
import com.njbailey.dutylogbackend.model.security.AuthorityName;
import com.njbailey.dutylogbackend.repository.AuthorityRepository;
import com.njbailey.dutylogbackend.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class DutylogBackendApplication implements CommandLineRunner  {
    public static void main(String[] args) {
        SpringApplication.run(DutylogBackendApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
//    	userRepository.deleteAllInBatch();
//    	authorityRepository.deleteAllInBatch();
//    	
//    	User user = new User("baileyn", "password", "Nicholas", "Bailey", "baileyn18@mycu.concord.edu");
//    	Authority authority = new Authority(AuthorityName.ROLE_ADMIN);
//    	
//    	authority.getUsers().add(user);
//    	user.getAuthorities().add(authority);
//    	
//    	userRepository.save(user);
    }
}
