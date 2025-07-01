package com.r2l.userService.config;

import com.r2l.userService.models.dto.request.UserProfileCreationDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String USER_CREATED_QUEUE = "user.created.queue";
  public static final String USER_CREATED_EXCHANGE = "user.created.exchange";
  public static final String USER_CREATED_ROUTING_KEY = "user.created.routingkey";

  @Bean
  public Queue userCreatedQueue() {
    return QueueBuilder.durable(USER_CREATED_QUEUE).build();
  }

  @Bean
  public DirectExchange userCreatedExchange() {
    return new DirectExchange(USER_CREATED_EXCHANGE);
  }

  @Bean
  public Binding userCreatedBinding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(USER_CREATED_ROUTING_KEY);
  }

  @Bean
  public Jackson2JsonMessageConverter jsonConverter() {
    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
    converter.setClassMapper(
        new ClassMapper() {
          @Override
          public Class<?> toClass(MessageProperties properties) {
            return UserProfileCreationDTO.class;
          }

          @Override
          public void fromClass(Class<?> clazz, MessageProperties properties) {}
        });
    return converter;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter);
    return template;
  }
}
