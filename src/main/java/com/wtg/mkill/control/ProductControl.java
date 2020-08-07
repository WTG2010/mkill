package com.wtg.mkill.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wtg.mkill.bean.Product;
import com.wtg.mkill.server.ProductServer;

@RestController
public class ProductControl {


	@Autowired
	private ProductServer productServer;
	
	@PostMapping("/product")
	public void addProduct(Product product) {
		productServer.addProduct(product);
	}
	
	@PutMapping("/product")
	public String putProduct2Redis() {
		return productServer.putProduct2Redis();
	}
	
}
