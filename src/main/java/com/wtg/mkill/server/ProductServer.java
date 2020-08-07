package com.wtg.mkill.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.wtg.mkill.bean.Product;
import com.wtg.mkill.dao.ProductDao;

@Service
public class ProductServer {
	
	@Autowired
	private ProductDao productDao;
	
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

	public void addProduct(Product product) {
		productDao.save(product);
	}
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}

	public String putProduct2Redis() {
		List<Product> products = getProducts();
		for (Product product : products) {
			redisTemplate.opsForValue().set("server_kill_"+product.getId(), product.getTotal()+"");
		}
		return "success";
	}

	public Integer decrNumById(String productId) {
		Product product = productDao.findById(productId).get();
		int oldTotal = product.getTotal();
		if (oldTotal == 0) {
			return 0;
		}else {
			product.setTotal(oldTotal-1);
			productDao.save(product);
			return product.getTotal();
		}
	}
}
