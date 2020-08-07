package com.wtg.mkill.control;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wtg.mkill.bean.config.MQConfig;

@RestController
public class KillControl {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping("/startKill/{seckillId}")
	public void startKill(@PathVariable String seckillId) {
		String userPhone="1876678"+new Random().nextInt(9999);
        String msg = userPhone+"/"+seckillId;
		rabbitTemplate.convertAndSend(MQConfig.MKILL_EXC, MQConfig.MKILL_ROUTING, msg);
	}
	
}
