package com.example.HannipmanApp;

import org.springframework.web.multipart.MultipartFile;

public class FoodDiaryRequest {
    private Long restaurantId;   // 수정할 식당 ID
    private String diaryText;    // 수정할 일기 내용
    private MultipartFile photo; // 수정할 사진 (업로드할 경우)

    // Getter & Setter
    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
