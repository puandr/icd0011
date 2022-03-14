package model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//TODO: use sequence from Db
//TODO: "hibernate.hbm2ddl.auto" väärtus on "validate".


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    //private String id;
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @ElementCollection (fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_rows",
            joinColumns=@JoinColumn(name = "orders_id", referencedColumnName = "id")
    )
    private List<OrderRow> orderRows;

    public Order() {}

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order (Long id, String orderNumber, List<OrderRow> orderRows) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderRows = orderRows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Valid
    public List<OrderRow> getOrderRows() {
        if (orderRows == null) {
            orderRows = new ArrayList<>();
        }
        return orderRows;
    }

    public void add(OrderRow orderRow) {
        if (orderRows == null) {
            orderRows = new ArrayList<>();
        }

        orderRows.add(orderRow);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderRows=" + orderRows +
                '}';
    }

}
