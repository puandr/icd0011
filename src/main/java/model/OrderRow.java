package model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Embeddable
@Table(name = "order_rows")
public class OrderRow {

    @Column(name = "item_name")
    private String itemName;
    @NotNull
    @Min(1)
    private Integer quantity;
    @NotNull
    @Min(1)
    private Integer price;

    public OrderRow() {}

    public OrderRow(String itemName, Integer quantity, Integer price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderRow{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}
