package spring.springassignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import spring.springassignment.entity.base.BaseEntity;
import spring.springassignment.entity.enums.ProductSimpleStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET status = 2 WHERE id = ?")
@Where(clause = "status = 1")
public class Product extends BaseEntity {
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    @Lob
    private String description;
    @Lob
    private String detail;
    private String thumbnails;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category categories;

    @Enumerated(EnumType.ORDINAL)
    private ProductSimpleStatus status;
}
