package project.inventory.domain.order;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Integer quantity;

    public OrderItemPK getId() {
        return id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
