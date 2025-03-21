package com.example.HannipmanApp;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 식당 DB 기본키, 자동증가

    @Column
    private boolean diary;  // 일기 작성 여부

    @Column
    private boolean heart; // 하트 여부

    @Column
    private String name;  // 식당 이름

    @ElementCollection
    private List<String> menu;  // 식당 메뉴 리스트

    @ElementCollection
    private List<Integer> price;  // 식당 메뉴 가격 리스트

    @Column
    private String kategorie;  // 카테고리(한식, 중식, 일식, 양식)

    @Column
    private String phoneNumber;  // 연락처

    @Column
    private String businessHours;  // 영업시간

    @Column
    private String closedDays;  // 휴무일

    @Column
    private String breakTime;  // 브레이크타임

    @Column
    private String kiosk;  // 키오스크 유무

    @Column
    private String place;  // 주소

    @Column
    private Double rating;  // 별점

    @Column
    private Double latitude;  // 위도

    @Column
    private Double longitude;  // 경도

    @Lob
    @Column(columnDefinition = "LONGBLOB")  // MySQL DB 저장시 LONGLOB타입으로 저장
    private byte[] image;  // 식당 이미지
}

interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    boolean existsByName(String name);
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RestaurantDTO {
    private Double latitude;
    private Double longitude;
    private String name;
}
