package com.inventory.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.dto.InventoryDTO;
import com.inventory.model.Inventory;
import com.inventory.model.Item;
import com.inventory.repository.InventoryRepository;
import com.inventory.repository.ItemRepository;

@Service
public class InventoryService {

	@Autowired
	InventoryRepository repository;

	@Autowired
	ItemRepository itemRepository;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Transactional
	public boolean addInventory(InventoryDTO inventoryDTO) {
		Inventory inventorydb;

		if (null != inventoryDTO) {
			inventoryDTO.setAvailability("NO");
			String name = inventoryDTO.getName();
			inventorydb = repository.findByName(name);
			if (null != inventorydb) {
				inventoryDTO.setUnits(inventoryDTO.getUnits() + inventorydb.getUnits());
				if (inventoryDTO.getUnits() >= 10) {
					inventoryDTO.setDemand("low");
					inventoryDTO.setStock("high");
				} else {
					inventoryDTO.setDemand("high");
					inventoryDTO.setStock("low");
				}
				repository.updateInventoryStockDemandById(inventoryDTO.getStock(), inventoryDTO.getDemand(),
						inventoryDTO.getUnits(), inventorydb.getId());
			} else {
				inventoryDTO.setDemand("high");
				inventoryDTO.setStock("low");
				Inventory inventory = getModelMapper().map(inventoryDTO, Inventory.class);
				for (Item itemsinList : inventory.getItems()) {
					itemsinList.setInventory(inventory);
				}
				repository.save(inventory);
			}

		}

		return true;

	}

	public Inventory getInventoryByName(String name) {
		return repository.findByName(name);
	}

	public List<Inventory> getAllInventory() {
		return (List<Inventory>) repository.findAll();

	}

	@Transactional
	public int updateInventoryForSales(String name, int units, String availability) {
		return repository.updateInventoryForSales(availability, units, name);
	
	}

	@Transactional
	public int deleteInventoryByName(String name) {
		return repository.deleteInventoryByName(name);
	}

	@Transactional
	public int deleteItemsByInventory(int inventoryId) {
		return  itemRepository.deleteItemsByInventory(inventoryId);
		

	}

}
