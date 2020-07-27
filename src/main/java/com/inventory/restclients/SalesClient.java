package com.inventory.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.inventory.dto.ProductDTO;

@FeignClient(name = "salesurl", url = "http://localhost:8082")
public interface SalesClient {
	@PostMapping(path = "/sales/addProductForSale")
	public boolean postProduct(@RequestBody ProductDTO productDTO);
}
