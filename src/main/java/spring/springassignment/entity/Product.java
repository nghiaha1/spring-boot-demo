package spring.springassignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category categories;

    @Enumerated(EnumType.ORDINAL)
    private ProductSimpleStatus status;
}
