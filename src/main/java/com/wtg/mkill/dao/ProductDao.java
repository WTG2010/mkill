package com.wtg.mkill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtg.mkill.bean.Product;

public interface ProductDao extends JpaRepository<Product, String>{

}
