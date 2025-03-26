package com.example.HannipmanApp.FoodDTO;

public class FoodDiaryFeedResponse {
    private Long id;
    private String photoPath;

    public FoodDiaryFeedResponse(Long id, String photoPath) {
        this.id = id;
        this.photoPath = photoPath;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}
