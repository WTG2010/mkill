package com.wtg.mkill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtg.mkill.bean.Order;

public interface OrderDao extends JpaRepository<Order, String>{

}
