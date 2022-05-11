package com.njbailey.dutylogbackend;

import com.njbailey.dutylogbackend.model.TodoTask;
import com.njbailey.dutylogbackend.repository.TodoTaskRepository;
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

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

@SpringBootApplication
@EnableJpaAuditing
public class DutylogBackendApplication implements CommandLineRunner  {
	@Autowired
	private UserRepository userRepository;

	@Autowired
    private TodoTaskRepository todoTaskRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    public static void main(String[] args) {
        SpringApplication.run(DutylogBackendApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
    	final String[] tasks = new String[] { "Organize 211.", "Hang up posters.", "Program an app for duty rounds.",
                "Put orange cones out around the parking lot.", "Organize 211.", "Mop up the water that's leaking at subway sides." };
    	final int[] offets = new int[] { 0, 0, 1, 1, 1, 0 };

    	for(int i = 0; i < tasks.length; i++) {
    		String task = tasks[i];
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_MONTH, offets[i]);

			TodoTask todoTask = new TodoTask();
			todoTask.setDescription(task);
			todoTask.setDate(c.getTime());

			todoTaskRepository.save(todoTask);
    	}

    	User user = new User();
    	user.setUsername("774156180");
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
