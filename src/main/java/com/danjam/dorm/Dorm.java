package com.danjam.dorm;

import com.danjam.d_category.Dcategory;

import com.danjam.users.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "dorm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Dorm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String contact_num;

    private String city;

    private String town;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Dcategory dcategory; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Users user; // 판매자

    @Builder
    public Dorm(String name, String description, String contact_num, String city, String town, String address, Users user, Dcategory dcategory) {
        this.name = name;
        this.description = description;
        this.contact_num = contact_num;
        this.city = city;
        this.town = town;
        this.address = address;
        this.user = user;
        this.dcategory = dcategory;
    }
}
