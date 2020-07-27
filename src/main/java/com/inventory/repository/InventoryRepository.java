package com.inventory.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory.model.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

	public Inventory findByName(String name);

	@Modifying
	@Query("update Inventory set availability = :availability,units = :units where name = :name")
	public int updateInventoryForSales(@Param("availability") String availability, @Param("units") int units,
			@Param("name") String name);

	@Modifying
	@Query("update Inventory set stock = :stock," + "demand = :demand,units = :units " + "where id = :id")
	public int updateInventoryStockDemandById(@Param("stock") String stock, @Param("demand") String demand,
			@Param("units") int units, @Param("id") int id);

	@Modifying
	@Query("delete from Inventory where name = :name")
	public int deleteInventoryByName(@Param("name") String name);

}
