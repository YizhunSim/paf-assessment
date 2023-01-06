package vttp2022.paf.assessment.eshop.respositories;

import java.sql.PreparedStatement;
import java.util.Optional;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;

@Repository
public class CustomerRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// You cannot change the method's signature
	public Optional<Customer> findCustomerByName(String name) {
		// TODO: Task 3 

        SqlRowSet result = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMER_BY_NAME, name);
        
		if (!result.next()) {
            return Optional.empty();
        } else{
			return Optional.of(Customer.create(result));
		}
	}

	public Optional<SqlRowSet> findCustomerByNameGetId(String name){
		SqlRowSet result = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMER_BY_NAME_GET_ID, name);
        
		if (!result.next()) {
			System.out.println("No result queried");
            return Optional.empty();
        } else{
			System.out.println("findCustomerByNameGetId:" + Optional.of(result).get());
			return Optional.of(result);
		}
	}

	public Optional<SqlRowSet> getTotalDispatchedByCustomer(String name){
		SqlRowSet result = jdbcTemplate.queryForRowSet(SQL_SELECT_DISPATCHED_PENDING_ORDERS_BY_CUSTOMER, name)
		if (!result.next()) {
			System.out.println("No result queried");
            return Optional.empty();
        } else{
			System.out.println("getTotalDispatchedByCustomer:" + Optional.of(result).get());
			return Optional.of(result);
		}
	}

	public Optional<SqlRowSet> getTotalPendingByCustomer(String name){
		SqlRowSet result = jdbcTemplate.queryForRowSet(SQL_SELECT_DISPATCHED_PENDING_ORDERS_BY_CUSTOMER, name)
		if (!result.next()) {
			System.out.println("No result queried");
            return Optional.empty();
        } else{
			System.out.println("getTotalDispatchedByCustomer:" + Optional.of(result).get());
			return Optional.of(result);
		}
	}


}
