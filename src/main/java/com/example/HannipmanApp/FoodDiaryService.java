package com.example.HannipmanApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FoodDiaryService {

    @Autowired
    private FoodDiaryRepo foodDiaryRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @PersistenceContext
    private EntityManager entityManager;

    // 특정 식당의 일기 가져오기
    public List<FoodDiary> getDiariesByRestaurant(Long restaurantId) {
        return foodDiaryRepo.findByRestaurantIdOrderByCreatedAtDesc(restaurantId);
    }

    // 새로운 일기 저장
    public void saveDiary(Long restaurantId, String diaryText, byte[] photoBytes) throws IOException {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 식당을 찾을 수 없습니다."));

        FoodDiary diary = new FoodDiary();
        diary.setRestaurant(restaurant);
        diary.setDiaryText(diaryText);
        diary.setCreatedAt(new java.util.Date());
        foodDiaryRepo.save(diary); // 먼저 저장해서 diaryId 생성

        if (photoBytes != null) {
            // 실제 파일 저장 + 경로 반환
            String urlPath = savePhotoToFileSystem(photoBytes, restaurantId, diary.getId());
            diary.setPhotoPath(urlPath);
            foodDiaryRepo.save(diary); // 경로까지 포함해서 다시 저장
        }
    }

    // 일기 수정
    public void updateDiary(Long diaryId, String diaryText, byte[] photoBytes) throws IOException {
        FoodDiary diary = foodDiaryRepo.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기를 찾을 수 없습니다."));

        diary.setDiaryText(diaryText);

        if (photoBytes != null) {
            String urlPath = savePhotoToFileSystem(photoBytes, diary.getRestaurant().getId(), diary.getId());
            diary.setPhotoPath(urlPath);
        }

        foodDiaryRepo.save(diary);
    }

    // 일기 삭제
    public void deleteDiary(Long diaryId) throws IOException {
        FoodDiary diary = foodDiaryRepo.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기를 찾을 수 없습니다."));

        if (diary.getPhotoPath() != null) {
            File photoFile = new File("/home/ec2-user" + diary.getPhotoPath());
            if (photoFile.exists()) {
                photoFile.delete();
            }
        }

        foodDiaryRepo.delete(diary);
    }

    // 모든 일기 삭제
    public void deleteAllDiaries() throws IOException {
        List<FoodDiary> allDiaries = foodDiaryRepo.findAll();

        for (FoodDiary diary : allDiaries) {
            if (diary.getPhotoPath() != null) {
                File photoFile = new File("/home/ec2-user" + diary.getPhotoPath());
                if (photoFile.exists()) {
                    photoFile.delete();
                }
            }
        }

        foodDiaryRepo.deleteAll();
    }

    // 자동 증가 값 초기화
    public void resetAutoIncrement() {
        entityManager.createNativeQuery("ALTER TABLE restaurantdb.food_diary AUTO_INCREMENT = 1").executeUpdate();
    }

    // 사진 저장
    private String savePhotoToFileSystem(byte[] photoBytes, Long restaurantId, Long diaryId) throws IOException {
        String filePath = "/home/ec2-user/uploads/restaurant_" + restaurantId + "/" + diaryId + ".jpg";
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(photoBytes);
        }

        // ✅ 클라이언트가 접근할 수 있는 경로 반환
        return "/uploads/restaurant_" + restaurantId + "/" + diaryId + ".jpg";
    }
}



