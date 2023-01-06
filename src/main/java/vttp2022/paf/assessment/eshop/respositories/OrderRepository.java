package vttp2022.paf.assessment.eshop.respositories;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;

@Repository
public class OrderRepository {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	// TODO: Task 3
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	public String saveOrder(Customer customer, Order order) {
		boolean result = false;

		String customerName = customer.getName();
		System.out.println("OrderRepository: Saving Order for customerName - " + customerName);
		Optional<SqlRowSet> custResult = customerRepository.findCustomerByNameGetId(customerName);

		// String orderId = UUID.randomUUID().toString().substring(0, 8); // Generates random order ID
		// String deliveryId = UUID.randomUUID().toString().substring(0, 8);

		KeyHolder keyholder = new GeneratedKeyHolder();
		BigInteger primaryKeyVal = null;

		Integer customerId = null;
		try {

			if (custResult.isEmpty()){
				System.out.println("OrderRepository: Customer:" + customerName + "Do not exist, inserting into customers");
				jdbcTemplate.update(conn -> {
					PreparedStatement ps = conn.prepareStatement(SQL_INSERT_INTO_CUSTOMER,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, customer.getName());
					ps.setString(2, customer.getAddress());
					ps.setString(3, customer.getEmail());
					return ps;
				}, keyholder);
				primaryKeyVal = (BigInteger) keyholder.getKey();
	
				customerId = primaryKeyVal.intValue();
			}
			
			if (customerId == null){
				customerId = custResult.get().getInt("customer_id");
				System.out.println("OrderRepository: customerId - " +customerId);
			}

			// Insert into order_status
			jdbcTemplate.update(SQL_INSERT_INTO_ORDER_STATUS, order.getOrderId(), order.getDeliveryId(), order.getStatus());
			// jdbcTemplate.update(conn -> {
			// 	PreparedStatement ps = conn.prepareStatement(SQL_INSERT_INTO_ORDER_STATUS,
			// 			Statement.RETURN_GENERATED_KEYS);
			// 	ps.setString(1, order.getOrderId());
			// 	ps.setString(2, order.getDeliveryId());
			// 	ps.setString(3, order.getStatus());
			// 	return ps;
			// }, keyholder);
			// primaryKeyVal = (BigInteger) keyholder.getKey();

			// int line_items_id = primaryKeyVal.intValue();

			// Insert into line
			jdbcTemplate.update(conn -> {
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT_INTO_LINE,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, 0);
				return ps;
			}, keyholder);
			primaryKeyVal = (BigInteger) keyholder.getKey();

			Integer lineId = primaryKeyVal.intValue();
			// Insert into line_items
			for (LineItem o : order.getLineItems()){
				jdbcTemplate.update(SQL_INSERT_INTO_LINE_ITEMS, o.getItem(), o.getQuantity());
			}
			
			// Insert into orders
			result = jdbcTemplate.update(SQL_INSERT_INTO_ORDERS, customerId, order.getOrderId(), lineId) > 0;

		} catch (DataIntegrityViolationException e) {
			return e.getMessage();
		}

		return result ? SUCCESS : FAILED;
	}
}
