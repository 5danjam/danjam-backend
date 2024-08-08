package com.danjam.dorm;

import com.danjam.d_category.Dcategory;

import com.danjam.users.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@ToString
@Table(name = "dorm")
@DynamicInsert // default
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Dorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "contact_num")
    private String contactNum;

    private String city;

    private String town;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Dcategory dcategory; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Users user; // 판매자

    @ColumnDefault("N")
    private String status;

    @Builder
    public Dorm(String name, String description, String contactNum, String city, String town, String address, Users user, Dcategory dcategory, String status) {
        this.name = name;
        this.description = description;
        this.contactNum = contactNum;
        this.city = city;
        this.town = town;
        this.address = address;
        this.user = user;
        this.dcategory = dcategory;
        this.status = status;
    }
}
