package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "product", alias = "p")
public class Relation_RestaurantProduct {

	@AliasFieldDAO(alias = "p", as = "id")
	private int id;
	
	@AliasFieldDAO(alias = "p", as = "name_product")
	private String name_product;
	
	@AliasFieldDAO(alias = "p", as = "price")
	private double price;
	
	@AliasFieldDAO(alias = "p", as = "vat_number")
	private double vat_number;
	
	@AliasFieldDAO(alias = "p", as = "img_path")
	private String img_path;
	
	@AliasFieldDAO(alias = "p", as = "manage_quantity")
	private boolean manage_quantity;
	
	@AliasFieldDAO(alias = "rp", as = "id_relation")
	private int id_relation;
	
	@AliasFieldDAO(alias = "rp", as = "id_product")
	private int id_product;

	@AliasFieldDAO(alias = "rp", as = "id_restaurant")
	private String id_restaurant;
	
	@AliasFieldDAO(alias = "rp", as = "quantity")
	private int quantity;
	
	@AliasFieldDAO(alias = "r", as = "name")
	private String name;

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
	public String getName_product() {
		return name_product;
	}

	/**
	 * 
	 * @param name_product
	 */
	public void setName_product(String name_product) {
		this.name_product = name_product;
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
	public int getId_relation() {
		return id_relation;
	}

	/**
	 * 
	 * @param id_relation
	 */
	public void setId_relation(int id_relation) {
		this.id_relation = id_relation;
	}

	/**
	 * 
	 * @return
	 */
	public int getId_product() {
		return id_product;
	}

	/**
	 * 
	 * @param id_product
	 */
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	/**
	 * 
	 * @return
	 */
	public String getId_restaurant() {
		return id_restaurant;
	}

	/**
	 * 
	 * @param id_restaurant
	 */
	public void setId_restaurant(String id_restaurant) {
		this.id_restaurant = id_restaurant;
	}

	/**
	 * 
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


}
