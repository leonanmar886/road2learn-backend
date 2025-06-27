package com.r2l.authService.service;

import com.r2l.authService.config.RabbitMQConfig;
import com.r2l.authService.models.dto.response.CreateUserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {

	private final RabbitTemplate rabbitTemplate;

	public void send(CreateUserProfileDTO payload) {
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.USER_CREATED_EXCHANGE,
				RabbitMQConfig.USER_CREATED_ROUTING_KEY,
				payload
		);
	}
}
