package orders;

import model.Order;
import model.OrderRow;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;


    public Order getOrderById(long id) {

        TypedQuery<Order> query = em.createQuery("select o from Order o where o.id = :id", Order.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Transactional
    public long addOrderWithResponse(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }

        return order.getId();

    }

    public List<Order> findAllOrders() {
        return em.createQuery("select o from Order o").getResultList();


    }

    public void deleteOrderById(Long id) {

        Order order = em.find(Order.class, 1L);
        if(order != null) {
            em.remove(order);
        }

    }

    private class OrderRowHandler implements RowCallbackHandler {

        private Order order = null;
        private List<OrderRow> rowToInsert = new ArrayList<>();

        public void processRow(ResultSet rs) throws SQLException {
            OrderRow orderRow = new OrderRow(rs.getString("itemName"), rs.getInt("quantity"), rs.getInt("price"));
            rowToInsert.add(orderRow);
            order =  new Order(rs.getLong("id"), rs.getString("orderNumber"), null);
        }

        public Order getResult() {
            return new Order(order.getId(), order.getOrderNumber(), rowToInsert);
        }
    }

    private class OrderMapper implements RowMapper {

        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(Long.parseLong(rs.getString("id")));
            order.setOrderNumber(rs.getString("orderNumber"));
            return order;
        }
    }

}
