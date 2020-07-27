package com.inventory.dto;

import java.util.List;
import com.inventory.model.Item;

public class InventoryDTO {
	private int id;
	private String name;
	private double price;
	private String demand;
	private String stock;
	private int units;
	private String availability;
	private List<Item> items;

	public InventoryDTO() {
		super();
	}

	public InventoryDTO(int id, String name, double price, String demand, String stock, int units, String availability,
			List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.demand = demand;
		this.stock = stock;
		this.units = units;
		this.availability = availability;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
