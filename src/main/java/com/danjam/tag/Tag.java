package com.danjam.tag;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@ToString
@Table(name = "tag")
@DynamicInsert // default
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String name;

    private String PN;

    @Builder
    public Tag(String name, String PN) {
        this.name = name;
        this.PN = PN;
    }
}
