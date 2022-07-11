package spring.springassignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import spring.springassignment.entity.base.BaseEntity;
import spring.springassignment.entity.enums.OrderSimpleStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String accountId;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.ORDINAL)
    private OrderSimpleStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;

    public void CalculateTotalPrice() {
        this.totalPrice = new BigDecimal(0);
        if (this.orderDetails != null && this.orderDetails.size() > 0) {
            for (OrderDetail orderDetail :
                 orderDetails) {
                BigDecimal orderDetailTotalPrice = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                this.totalPrice = this.totalPrice.add(orderDetailTotalPrice);
            }
        }
    }
}
