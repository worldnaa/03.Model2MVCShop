package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;

public class ProductServiceImpl implements ProductService {
	//Field
	private ProductDao productDao;
	
	//Constructor
	public ProductServiceImpl() {
		productDao = new ProductDao();
	}

	//Method
	public void addProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : addProduct() 실행 >>>>>");
		productDao.insertProduct(product);
	}

	
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : getProduct() 실행 >>>>>");
		return productDao.findProduct(prodNo);
	}

	
	public Map<String, Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : getProductList() 실행 >>>>>");
		return productDao.getProductList(search);
	}

	
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : updateProduct() 실행 >>>>>");
		productDao.updateProduct(product);
	}

}//end of class
