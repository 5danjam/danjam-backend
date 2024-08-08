package com.danjam.r_tag;

import com.danjam.review.Review;
import com.danjam.tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@ToString
@Table(name = "r_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public Rtag(Review review, Tag tag) {
        this.review = review;
        this.tag = tag;
    }
}
