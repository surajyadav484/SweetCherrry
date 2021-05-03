package com.capgemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SweetCherry {

	/*
	 * @Autowired EmailSenderService mailService;
	 */

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SweetCherry.class, args);

	}/*
		 * @EventListener(ApplicationReadyEvent.class) public void triggerMail() {
		 * mailService.senMail("manishapal145@gmail.com","this is email body","subject")
		 * ;
		 */
	// }

}
