package com.inventory.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.inventory.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

	@Modifying
	@Query("delete from Item where inventory_id = :inventoryId")
	public int deleteItemsByInventory(@Param("inventoryId") int inventoryId);

}
