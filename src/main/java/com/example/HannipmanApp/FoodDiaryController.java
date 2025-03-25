package com.example.HannipmanApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fooddiary")
public class FoodDiaryController {

    @Autowired
    private FoodDiaryService foodDiaryService;

    // 특정 식당의 일기 가져오기
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<FoodDiaryResponse>> getDiaries(@PathVariable Long restaurantId) {
        List<FoodDiary> diaries = foodDiaryService.getDiariesByRestaurant(restaurantId);

        if (diaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<FoodDiaryResponse> responseList = diaries.stream()
                .map(diary -> new FoodDiaryResponse(
                        diary.getId(),
                        diary.getDiaryText(),
                        diary.getCreatedAt(),
                        diary.getRestaurant().getId(),
                        diary.getPhotoPath()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 새로운 일기 저장
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> addDiary(
            @RequestParam Long restaurantId,
            @RequestParam String diaryText,
            @RequestParam(required = false) MultipartFile photo) throws IOException {

        byte[] photoBytes = null;
        if (photo != null && !photo.isEmpty()) {
            photoBytes = photo.getBytes();
        }

        foodDiaryService.saveDiary(restaurantId, diaryText, photoBytes);

        return ResponseEntity.ok("일기가 성공적으로 추가되었습니다.");
    }

    // 일기 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<String> updateDiary(
            @PathVariable Long diaryId,
            @RequestParam String diaryText,
            @RequestParam(required = false) MultipartFile photo) throws IOException {

        byte[] photoBytes = null;
        if (photo != null && !photo.isEmpty()) {
            photoBytes = photo.getBytes();
        }

        foodDiaryService.updateDiary(diaryId, diaryText, photoBytes);

        return ResponseEntity.ok("일기가 성공적으로 수정되었습니다.");
    }

    // 일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId) throws IOException {
        foodDiaryService.deleteDiary(diaryId);
        return ResponseEntity.ok("일기가 성공적으로 삭제되었습니다.");
    }

    // 전체 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllDiaries() throws IOException {
        foodDiaryService.deleteAllDiaries();
        return ResponseEntity.ok("모든 일기가 성공적으로 삭제되었습니다.");
    }

    // 자동 증가 값 초기화
    @DeleteMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        foodDiaryService.resetAutoIncrement();
        return ResponseEntity.ok("자동 증가 값이 성공적으로 초기화되었습니다.");
    }
}




