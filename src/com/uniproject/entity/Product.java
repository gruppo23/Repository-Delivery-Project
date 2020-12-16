package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;
import com.uniproject.dao.Key;

@AliasTableDAO(alias = "p", tableName = "product")
public class Product {

	@AliasFieldDAO(alias = "p", as = "id")
	@Key()
	private int id;
	
	@AliasFieldDAO(alias = "p", as = "name_product")
	private String name_product;
	
	@AliasFieldDAO(alias = "p", as = "price")
	private double price;
	
	@AliasFieldDAO(alias = "p", as = "vat_number")
	private double vat_number;

	@AliasFieldDAO(alias = "p", as = "img_path")
	public String img_path;
	
	@AliasFieldDAO(alias = "p", as = "manage_quantity")
	public boolean manage_quantity;
	
	/**
	 * 
	 * @return
	 */
	public boolean isManage_quantity() {
		return manage_quantity;
	}

	/**
	 * 
	 * @param manage_quantity
	 */
	public void setManage_quantity(boolean manage_quantity) {
		this.manage_quantity = manage_quantity;
	}

	/**
	 * 
	 * @return
	 */
	public double getVat_number() {
		return vat_number;
	}

	/**
	 * 
	 * @param vat_number
	 */
	public void setVat_number(double vat_number) {
		this.vat_number = vat_number;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name_product;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name_product = name;
	}

	/**
	 * 
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}


	/**
	 * 
	 * @return
	 */
	public String getImg_path() {
		return img_path;
	}

	/**
	 * 
	 * @param img_path
	 */
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
}
