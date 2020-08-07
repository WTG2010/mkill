package com.wtg.mkill.consumer;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.wtg.mkill.bean.Order;
import com.wtg.mkill.bean.config.MQConfig;
import com.wtg.mkill.server.OrderServer;
import com.wtg.mkill.server.ProductServer;

//消费信息
@Component
public class Consumer {
	
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 解决Redis乱码问题
     * @param redisTemplate
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
	
	@Autowired
	private ProductServer productServer;
	@Autowired
	private OrderServer orderServer;
	
	@RabbitListener(queues= {MQConfig.MKILL_QUEUE})
	public void consumeKillMsg(String msg) {
		
		String userPhone = msg.split("/")[0];
		String productId = msg.split("/")[1];
		
		System.out.println("处理：用户"+userPhone+"秒杀的"+productId+"商品");
		
		//创建一个订单信息
		Order order = new Order();
		order.setCreateTime(new Date());
		order.setProductName(productId);
		order.setUserPhone(userPhone);
		order.setKillstatus(true);
		
		//缓存减库存
		Long decr = redisTemplate.opsForValue().decrement("server_kill_"+productId, 1);
		
		if (decr < 0 ) {
			System.out.println("redis:商品已经秒杀完了");
			order.setKillstatus(false);
		}else {
			
		//数据库减库存
		long count = productServer.decrNumById(productId);
		orderServer.creatOrder(order);
		}
	}
}
