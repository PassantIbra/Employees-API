package com.employees.challenge.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.UpdateEmployeReqDto;

@EnableKafka
@Configuration
public class KafkaConfiguration {
	final String bootstrapAddress = "localhost:9092";

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<String, String>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, AddEmployeeReqDto> addEmployeeConsumerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "add_employee");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, AddEmployeeReqDto>(config, new StringDeserializer(),
				new JsonDeserializer<AddEmployeeReqDto>(AddEmployeeReqDto.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AddEmployeeReqDto> addEmployeeListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, AddEmployeeReqDto> factory = new ConcurrentKafkaListenerContainerFactory<String, AddEmployeeReqDto>();
		factory.setConsumerFactory(addEmployeeConsumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, UpdateEmployeReqDto> updateEmployeeConsumerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "update_employee");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, UpdateEmployeReqDto>(config, new StringDeserializer(),
				new JsonDeserializer<UpdateEmployeReqDto>(UpdateEmployeReqDto.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UpdateEmployeReqDto> updateEmployeeListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UpdateEmployeReqDto> factory = new ConcurrentKafkaListenerContainerFactory<String, UpdateEmployeReqDto>();
		factory.setConsumerFactory(updateEmployeeConsumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, AddEmployeeReqDto> AddEmployeeProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<String, AddEmployeeReqDto>(config);
	}

	@Bean
	public ProducerFactory<String, UpdateEmployeReqDto> updateEmployeeProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<String, UpdateEmployeReqDto>(config);
	}

	@Bean
	public KafkaTemplate<String, AddEmployeeReqDto> addEmployeekafkaTemplate() {
		return new KafkaTemplate<String, AddEmployeeReqDto>(AddEmployeeProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, UpdateEmployeReqDto> updateEmployeekafkaTemplate() {
		return new KafkaTemplate<String, UpdateEmployeReqDto>(updateEmployeeProducerFactory());
	}

	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("add_employee").build();

	}

	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name("update_employee").build();
	}

}
