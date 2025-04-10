package com.example.HannipmanApp.FoodDTO;

import java.util.Date;

public class FoodDiaryDetailResponse {
    private Long id;
    private String diaryText;
    private Date createdAt;
    private String photoPath;
    private Long restaurantId;
    private String restaurantName;
    private String address;
    private Boolean heart;

    private Float rating;          // 추가
    private String menuName;       // 추가

    public FoodDiaryDetailResponse(Long id, String diaryText, Date createdAt,
                                   String photoPath, Long restaurantId, String restaurantName,
                                   String address, Boolean heart, Float rating, String menuName) {
        this.id = id;
        this.diaryText = diaryText;
        this.createdAt = createdAt;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.heart = heart;
        this.rating = rating; //추가
        this.menuName = menuName; // 추가
    }

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDiaryText() { return diaryText; }
    public void setDiaryText(String diaryText) { this.diaryText = diaryText; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Boolean getHeart() { return heart; }
    public void setHeart(Boolean heart) { this.heart = heart; }

    public Float getRating() { return rating; }         // ⭐️ 추가
    public void setRating(Float rating) { this.rating = rating; }

    public String getMenuName() { return menuName; }    // ⭐️ 추가
    public void setMenuName(String menuName) { this.menuName = menuName; }
}
