package project.inventory.domain.order;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import project.inventory.domain.product.Product;

@Embeddable
public class OrderItemPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				order != null ? order.getId() : null,
				product != null ? product.getId() : null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		OrderItemPK other = (OrderItemPK) obj;

		Long thisOrderId = order != null ? order.getId() : null;
		Long otherOrderId = other.order != null ? other.order.getId() : null;
		Long thisProductId = product != null ? product.getId() : null;
		Long otherProductId = other.product != null ? other.product.getId() : null;

		return Objects.equals(thisOrderId, otherOrderId) &&
				Objects.equals(thisProductId, otherProductId);
	}
}