package vttp2022.paf.assessment.eshop.respositories;

public class Queries {
    public static final String SQL_SELECT_CUSTOMER_BY_NAME = "SELECT * from customers where name = ?;";

    public static final String SQL_SELECT_CUSTOMER_BY_NAME_GET_ID = "SELECT customer_id FROM customers where name = ?;";

    public static final String SQL_INSERT_INTO_CUSTOMER = "INSERT into customers (name, address, email) values (?, ?, ?);";

    // -- 1. INSERT Order Status *order_id [PRE-REQUISITE]
    public static final String SQL_INSERT_INTO_ORDER_STATUS = "INSERT INTO order_status (order_id, delivery_id, status, status_update) VALUES (?, ?, ?, NOW());";

    // -- 2. INSERT line
    public static final String SQL_INSERT_INTO_LINE = "INSERT INTO line (id) VALUES (?);";

    // -- 3. INSERT line_items
    public static final String SQL_INSERT_INTO_LINE_ITEMS = "INSERT INTO line_items (item, quantity) values (?, ?);";

    // -- 4. INSERT Orders
    public static final String SQL_INSERT_INTO_ORDERS = "INSERT INTO orders (customer_id, order_id, line_items_id, order_date) VALUES (?, ?, ?, NOW());";

    public static final String SQL_SELECT_DISPATCHED_PENDING_ORDERS_BY_CUSTOMER = "SELECT c.name, COUNT(os.status) AS numberOfDispatched, COUNT(os.status) as numberOfPending FROM customers c JOIN orders o ON c.customer_id = o.customer_id JOIN order_status os ON o.order_id = os.order_id WHERE c.name = ?;";
}
