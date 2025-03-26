package com.example.HannipmanApp.FoodDTO;

import java.util.Date;

// ✅ 리스트용 DTO (전체 일기 리스트 조회)
public class FoodDiaryResponse {
    private Long id;
    private String diaryText;
    private Date createdAt;
    private Long restaurantId;
    private String photoPath;

    public FoodDiaryResponse(Long id, String diaryText, Date createdAt, Long restaurantId, String photoPath) {
        this.id = id;
        this.diaryText = diaryText;
        this.createdAt = createdAt;
        this.restaurantId = restaurantId;
        this.photoPath = photoPath;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDiaryText() { return diaryText; }
    public void setDiaryText(String diaryText) { this.diaryText = diaryText; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}


