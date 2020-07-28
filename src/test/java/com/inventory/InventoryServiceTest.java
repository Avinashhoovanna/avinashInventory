package com.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import com.inventory.dto.InventoryDTO;
import com.inventory.model.Inventory;
import com.inventory.model.Item;
import com.inventory.repository.InventoryRepository;
import com.inventory.service.InventoryService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

	@InjectMocks
	private InventoryService service;

	@Mock
	InventoryRepository repository;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	public Inventory getInventory() {
		InventoryDTO inventoryDTO = new InventoryDTO("Nokia", 15000.00, "high", "low", 10, "NO",
				new ArrayList<Item>());
		Inventory inventory = getModelMapper().map(inventoryDTO, Inventory.class);
		return inventory;
	}

	public InventoryDTO getInventoryDTO() {
		InventoryDTO inventoryDTO = new InventoryDTO("Nokia", 15000.00, "high", "low", 10, "NO",
				new ArrayList<Item>());
		return inventoryDTO;
	}

	@Test
	public void addInventoryServiceExistsTest() {
		when(repository.findByName("Nokia")).thenReturn(getInventory());
		//when(repository.updateInventoryStockDemandById("low", "high", 10, 1)).thenReturn(1);
		//when(repository.save(getInventory())).thenReturn(getInventory());
		boolean actual = service.addInventory(getInventoryDTO());
		assertEquals(true, actual);
	}
	
	@Test
	public void addInventoryServiceNotExistsTest() {
		when(repository.findByName("Nokia")).thenReturn(null);
		//when(repository.save(getInventory())).thenReturn(getInventory());
		boolean actual = service.addInventory(getInventoryDTO());
		assertEquals(true, actual);
	}

}
