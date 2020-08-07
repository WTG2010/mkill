package com.wtg.mkill.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wtg.mkill.bean.Order;
import com.wtg.mkill.dao.OrderDao;

@Service
public class OrderServer {
	
	@Autowired
	private OrderDao orderDao;

	public void creatOrder(Order order) {
		orderDao.save(order);
	}

}
