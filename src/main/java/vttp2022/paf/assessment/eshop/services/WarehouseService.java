package vttp2022.paf.assessment.eshop.services;

import java.io.StringReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.stream.JsonParser;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

@Service
public class WarehouseService {

	private static final String DISPATCHED = "dispatched";
	private static final String PENDING = "pending";

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		// TODO: Task 4
		OrderStatus os = new OrderStatus();

		JsonObject orderToBeSent = null;
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("orderId", order.getOrderId());
		builder.add("name", order.getName());
		builder.add("address", order.getAddress());
		builder.add("email", order.getEmail());
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
      
        for (LineItem item : order.getLineItems()){
			arrBuilder.add(Json.createObjectBuilder()
			.add("item", item.getItem())
			.add("quantity", item.getQuantity().toString()));
		}
        
		builder.add("lineitems", arrBuilder.build());

        orderToBeSent = builder.build();

		ResponseEntity<String> responseResult = ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderToBeSent.toString());

		System.out.println(responseResult.getBody());

		// Dispatch Success
		if (responseResult.getStatusCode().is2xxSuccessful()){
			JsonReader jsonReader = Json.createReader(new StringReader(responseResult.getBody()));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();

			String orderId = object.getString("orderId");
			String deliveryId = object.getString("deliveryId");

			os.setOrderId(orderId);
			os.setDeliveryId(deliveryId);
			os.setStatus(DISPATCHED);
		
		// Dispatch Unsuccessful
		} else{
			os.setOrderId(order.getOrderId());
			os.setDeliveryId(order.getDeliveryId());
			os.setStatus(PENDING);
		}
		
	return os;
	}

}
