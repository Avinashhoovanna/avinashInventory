package com.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.InventoryDTO;
import com.inventory.dto.ProductDTO;
import com.inventory.dto.ReturnInventoryDTO;
import com.inventory.exception.InventoryException;
import com.inventory.model.Inventory;
import com.inventory.restclients.SalesClient;
import com.inventory.service.InventoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Validated
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryService service;
	@Autowired
	SalesClient salesClient;

	private static final Logger log = LogManager.getLogger(InventoryController.class);
	//
	//method for adding Inventory along with Raw materials need for Inventory
	@PostMapping("/addInventory")
	@ApiOperation(value = "adding inventory..", notes = "Able to Add items to inventory..", response = String.class)
	public String addInventory(
			@ApiParam(value = "Inventory details for Adding.", required = true) @RequestBody InventoryDTO inventoryDTO) {
		String message = "";
		try {
			log.debug("Inside addInventory method");
			boolean result = service.addInventory(inventoryDTO);
			if (result) {
				message = "Successfully added the Inventory";
			} else {
				message = "We are Having trouble in Adding Inventory try after sometime...";
			}
		} catch (Exception ex) {
			throw new InventoryException("Not able to add Inventory!!!", HttpStatus.BAD_REQUEST);
		}
		return message;
	}

	@GetMapping("/getInventoryByName/{name}")
	public ReturnInventoryDTO getInventoryByName(
			@PathVariable @Valid @Size(min = 2, max = 10, message = "Please provide Name") String name) {
		ReturnInventoryDTO returninventoryDTO = null;
		try {
			log.info("Inside getInventoryByName method");
			Inventory inventory = service.getInventoryByName(name);
			returninventoryDTO = new ReturnInventoryDTO();
			if (null != inventory) {
				returninventoryDTO.setName(inventory.getName());
				returninventoryDTO.setPrice(inventory.getPrice());
				returninventoryDTO.setDemand(inventory.getDemand());
				returninventoryDTO.setStock(inventory.getStock());
				returninventoryDTO.setUnits(inventory.getUnits());
				returninventoryDTO.setAvailability(inventory.getAvailability());
			}
		} catch (Exception ex) {
			throw new InventoryException("Inventory Not Found!!!!", HttpStatus.NOT_FOUND);
		}
		return returninventoryDTO;
	}

	@GetMapping("/getAllInventory")
	public List<ReturnInventoryDTO> getAllInventory() {
		List<ReturnInventoryDTO> returnResult = null;
		try {
			List<Inventory> inventory = service.getAllInventory();
			returnResult = new ArrayList<>();
			if (null != inventory) {
				for (Inventory result : inventory) {
					ReturnInventoryDTO returninventoryDTO = new ReturnInventoryDTO();
					returninventoryDTO.setName(result.getName());
					returninventoryDTO.setPrice(result.getPrice());
					returninventoryDTO.setDemand(result.getDemand());
					returninventoryDTO.setStock(result.getStock());
					returninventoryDTO.setUnits(result.getUnits());
					returninventoryDTO.setAvailability(result.getAvailability());
					returnResult.add(returninventoryDTO);
				}
			}
		} catch (Exception ex) {
			throw new InventoryException();
		}
		return returnResult;

	}

	@PutMapping("/updateInventoryForSale/{name}")
	public String updateInventory(@PathVariable String name, @RequestBody InventoryDTO inventoryDTO) {
		String message = "";
		try {
			//Checking Inventory already exists in database
			Inventory inventorydb = service.getInventoryByName(name);
			if (0 != inventoryDTO.getUnits())
				inventorydb.setUnits(inventorydb.getUnits() - inventoryDTO.getUnits());
			if (null != inventoryDTO.getName())
				inventorydb.setName(inventoryDTO.getName());
			if (null != inventorydb.getAvailability() || "NO".equals(inventorydb.getAvailability()))
				inventorydb.setAvailability("YES");
			service.updateInventoryForSales(inventorydb.getName(), inventorydb.getUnits(),
					inventorydb.getAvailability());
			ProductDTO product = new ProductDTO();
			product.setName(name);
			product.setPrice(inventorydb.getPrice());
			//calling sales microservice to add product
			salesClient.postProduct(product);
			message = "Inventory Available for SALE.......";
		} catch (Exception ex) {
			throw new InventoryException("Inventory Not Found!!!!", HttpStatus.NOT_FOUND);
		}

		return message;

	}

	@DeleteMapping("/deleteInventory/{name}")
	public String deleteInventory(@PathVariable String name) {
		String message = "";
		try {
			Inventory inventory = service.getInventoryByName(name);
			if (null != inventory) {
				int inventoryId = inventory.getId();
				service.deleteItemsByInventory(inventoryId);
				service.deleteInventoryByName(name);
				message = "Successfully deleted the Inventory......";
			} else {
				message = "Inventory Not Found...";
			}
		} catch (Exception ex) {
			throw new InventoryException("Inventory Not Found!!!!", HttpStatus.NOT_FOUND);
		}

		return message;
	}

}
