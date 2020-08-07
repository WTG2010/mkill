package com.wtg.mkill.bean.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//初始化一个一对一的消息队列

@Configuration
public class MQConfig {
	
	public static final String MKILL_QUEUE = "mkill_queue";
	public static final String MKILL_EXC = "mkill_exc";
	public static final String MKILL_ROUTING = "mkill_rout";
	
	@Bean
	public Queue mkill_queue() {
		return new Queue(MKILL_QUEUE, true, false, false);
	}
	
	@Bean
	public DirectExchange mkill_exc() {
		return new DirectExchange(MKILL_EXC, true, false);
	}
	
	@Bean
	public Binding bindQE() {
		return BindingBuilder.bind(mkill_queue()).to(mkill_exc()).with(MKILL_ROUTING);
	}

}
