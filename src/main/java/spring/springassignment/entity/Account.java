package spring.springassignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import spring.springassignment.entity.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(unique = true)
    private String username;
    private String passwordHash;
    private int role;
}