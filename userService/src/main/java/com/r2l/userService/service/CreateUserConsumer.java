package com.r2l.userService.service;

import com.r2l.userService.config.RabbitMQConfig;
import com.r2l.userService.models.dto.request.UserProfileCreationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CreateUserConsumer {

	@RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
	public void receive(UserProfileCreationDTO payload) {
		System.out.println("Received: " + payload);
	}

}
