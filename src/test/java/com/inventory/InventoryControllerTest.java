package com.inventory;

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

import com.inventory.controller.InventoryController;
import com.inventory.dto.InventoryDTO;
import com.inventory.dto.ReturnInventoryDTO;
import com.inventory.exception.InventoryException;
import com.inventory.model.Inventory;
import com.inventory.model.Item;
import com.inventory.restclients.SalesClient;
import com.inventory.service.InventoryService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTest {

	@InjectMocks
	InventoryController inventoryController;

	@Mock
	private InventoryService service;

	@Mock
	private SalesClient salesClient;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	public InventoryDTO getInventoryDTO() {
		InventoryDTO inventoryDTO = new InventoryDTO(1, "Nokia", 15000.00, "high", "low", 10, "NO",
				new ArrayList<Item>());
		return inventoryDTO;
	}

	public Inventory getInventory() {
		InventoryDTO inventoryDTO = new InventoryDTO(1, "Nokia", 15000.00, "high", "low", 10, "NO",
				new ArrayList<Item>());
		Inventory inventory = getModelMapper().map(inventoryDTO, Inventory.class);
		return inventory;
	}


	@Test
	public void addInventoryTest() {
		
		String message = "We are Having trouble in Adding Inventory try after sometime...";
		//when(service.addInventory(getInventoryDTO())).thenReturn(true);
		String result = inventoryController.addInventory(getInventoryDTO());
		assertEquals(message, result);
	}

	@Test
	public void getAllInventoryTest() {
		List<Inventory> inventoryList = new ArrayList<Inventory>();
		inventoryList.add(getInventory());
		when(service.getAllInventory()).thenReturn(inventoryList);
		List<ReturnInventoryDTO> returnInventoryDTO = inventoryController.getAllInventory();
		assertEquals(1, returnInventoryDTO.size());
	}

	@Test
	public void getInventoryByNameTest() {
		when(service.getInventoryByName("Nokia")).thenReturn(getInventory());
		ReturnInventoryDTO returnInventoryDTO = inventoryController.getInventoryByName("Nokia");
		double price = returnInventoryDTO.getPrice();
		assertEquals(15000.00, price, 0);
	}

	@Test(expected = InventoryException.class)
	public void updateInventoryNotFoundTest() {
		when(service.getInventoryByName("Nokia")).thenReturn(null);
		inventoryController.updateInventory("Nokia", getInventoryDTO());
	}

	@Test
	public void deleteInventoryTest() {
		when(service.getInventoryByName("Nokia")).thenReturn(getInventory());
		when(service.deleteItemsByInventory(1)).thenReturn(1);
		when(service.deleteInventoryByName("Nokia")).thenReturn(1);
		String message = "Successfully deleted the Inventory......";
		String result = inventoryController.deleteInventory("Nokia");
		assertEquals(message, result);
	}

	@Test
	public void updateInventoryTest() {
		when(service.getInventoryByName("Nokia")).thenReturn(getInventory());
		String actual = inventoryController.updateInventory("Nokia", getInventoryDTO());
		String expected = "Inventory Available for SALE.......";
		assertEquals(expected, actual);
	}

}
