package com.example.HannipmanApp;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String diaryText;

    @Lob
    private String photoPath; // 사진 경로 저장

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}

interface FoodDiaryRepo extends JpaRepository<FoodDiary, Long> {
    List<FoodDiary> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}