package com.example.HannipmanApp.FoodDTO;

public class FoodDiaryFeedResponse {
    private Long id;
    private String photoPath;
    private Boolean heart; // ✅ 하트 상태 추가

    public FoodDiaryFeedResponse(Long id, String photoPath, Boolean heart) {
        this.id = id;
        this.photoPath = photoPath;
        this.heart = heart;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public Boolean getHeart() { return heart; }
    public void setHeart(Boolean heart) { this.heart = heart; }
}