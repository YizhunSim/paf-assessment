package vttp2022.paf.assessment.eshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.services.CustomerService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	public static final String SUCCESS = "SUCCESS";
	public static final String DISPATCHED = "dispatched";
	public static final String PENDING = "pending";

	@Autowired
	private CustomerService customerService;

	@Autowired
	private WarehouseService warehouseService;
	// TODO: Task 3

	@GetMapping(path = "/api/customer/{name}")
	public ResponseEntity<String> getCustomerByName(@PathVariable String name) {
		JsonObject result = null;

		// Query the database for the books
		Optional<Customer> customer = customerService.findCustomerByName(name);

		if (customer.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"error\": \"Customer " + name + " not found\"}");
		}

		// Build the result
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
		objBuilder.add("customer", (customer.get().toJSON()));
		result = objBuilder.build();

		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(result.toString());
	}

	// Task 3
	// Save the order to the database
	@GetMapping(path = { "/api/customer/add" })
	public ResponseEntity<String> saveOrder() {
		System.out.println("Entered!");
		Customer customerToBeSave = new Customer();
		customerToBeSave.setName("Steven");
		customerToBeSave.setAddress("flower-aveneue");
		customerToBeSave.setEmail("steven@gmail.com");

		Order orderToBeSave = new Order();
		orderToBeSave.setOrderId(UUID.randomUUID().toString().substring(0, 8));
		orderToBeSave.setDeliveryId(UUID.randomUUID().toString().substring(0, 8));
		orderToBeSave.setName(customerToBeSave.getName());
		orderToBeSave.setAddress(customerToBeSave.getAddress());
		orderToBeSave.setEmail(customerToBeSave.getEmail());
		orderToBeSave.setStatus("pending");

		LineItem item1 = new LineItem();
		item1.setItem("Macbook");
		item1.setQuantity(5);

		LineItem item2 = new LineItem();
		item2.setItem("Speaker");
		item2.setQuantity(10);

		List<LineItem> items = new LinkedList<>();
		items.add(item1);
		items.add(item2);

		orderToBeSave.setLineItems(items);

		String saveResult = customerService.saveOrder(customerToBeSave, orderToBeSave);

		if (!saveResult.equals(SUCCESS)) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"error\": " + saveResult + "}");
		}

		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body("{\"Success\": \"Order Saved!\"}");
	}

	// Task 4 and 5
	@PostMapping(path = { "/dispatch" })
	public ResponseEntity<String> dispatchOrder(Order o) {
		OrderStatus os = warehouseService.dispatch(o);
		JsonObject result = null;
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();

		if (os.getStatus().equals(DISPATCHED)){
			objBuilder.add("orderId", os.getOrderId());
			objBuilder.add("deliveryId", os.getDeliveryId());
			objBuilder.add("status", os.getStatus());
		}
		else if (os.getStatus().equals(PENDING)){
			objBuilder.add("orderId", os.getOrderId());
			objBuilder.add("status", os.getStatus());
		}
		
		result = objBuilder.build();
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(result.toString());
	}

}
