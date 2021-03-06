package com.inventory;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

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
import com.inventory.repository.ItemRepository;
import com.inventory.service.InventoryService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

	@InjectMocks
	private InventoryService service;

	@Mock
	InventoryRepository repository;
	
	@Mock
	ItemRepository itemRepository;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	public List<Item> getItem() {
		Item item = new Item();
		item.setId(1);
		item.setName("battery");
		item.setPrice(2000.00);
		item.setDescription("2000mah");
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(item);
		return list;

	}
	
	public InventoryDTO getInventoryDTO() {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setId(1);
		inventoryDTO.setName("Nokia");
		inventoryDTO.setPrice(15000.00);
		inventoryDTO.setDemand("low");
		inventoryDTO.setStock("high");
		inventoryDTO.setUnits(10);
		inventoryDTO.setAvailability("NO");
		inventoryDTO.setItems(getItem());
		return inventoryDTO;
	}

	public Inventory getInventory() {
		Inventory inventory = getModelMapper().map(getInventoryDTO(), Inventory.class);
		return inventory;
	}

	@Test
	public void addInventoryMoreUnitServiceExistsTest() {
		when(repository.findByName("Nokia")).thenReturn(getInventory());
		boolean actual = service.addInventory(getInventoryDTO());
		assertEquals(true, actual);
	}
	
	@Test
	public void addInventoryLessUnitServiceExistsTest() {
		when(repository.findByName("Nokia")).thenReturn(getInventory());
		InventoryDTO inventoryDTO = getInventoryDTO();
		inventoryDTO.setUnits(0);
		boolean actual = service.addInventory(inventoryDTO);
		assertEquals(true, actual);
	}
	
	@Test
	public void addInventoryServiceNotExistsTest() {
		when(repository.findByName("Nokia")).thenReturn(null);
		boolean actual = service.addInventory(getInventoryDTO());
		assertEquals(true, actual);
	}
	@Test
	public void getInventoryByNameTest() {
		when(repository.findByName("Nokia")).thenReturn(getInventory());
		Inventory inventory = service.getInventoryByName("Nokia");
		assertNotNull(inventory);
	}
	
	@Test
	public void getAllInventoryTest() {
		when(repository.findAll()).thenReturn(new ArrayList<Inventory>());
		List<Inventory> inventoryList = service.getAllInventory();
		assertNotNull(inventoryList);
	}
	
	@Test
	public void updateInventoryForSalesTest() {
		int result = service.updateInventoryForSales("YES", 10, "Nokia");
		assertEquals(0, result);
	}
	
	@Test
	public void deleteInventoryByNameTest() {
		when(repository.deleteInventoryByName("Nokia")).thenReturn(1);
		int result = service.deleteInventoryByName("Nokia");
		assertEquals(1, result);
	}
	
	@Test
	public void deleteItemsByInventoryTest() {
		when(itemRepository.deleteItemsByInventory(1)).thenReturn(1);
		int result = service.deleteItemsByInventory(1);
		assertEquals(1, result);
	}

}
