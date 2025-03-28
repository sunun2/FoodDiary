package com.example.HannipmanApp;

import com.example.HannipmanApp.FoodDTO.FoodDiaryDetailResponse;
import com.example.HannipmanApp.FoodDTO.FoodDiaryFeedResponse;
import com.example.HannipmanApp.FoodDTO.FoodDiaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fooddiary")
public class FoodDiaryController {

    @Autowired
    private FoodDiaryService foodDiaryService;

    @Autowired
    private FoodDiaryRepo foodDiaryRepo;

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
                        diary.getPhotoPath(),
                        diary.getRestaurant().getHeart()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 새로운 일기 추가/저장
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


    // 전체 일기 조회 API (사진만 보여주자)
    @GetMapping("/feed")
    public ResponseEntity<List<FoodDiaryFeedResponse>> getFeedPhotos(@RequestParam(defaultValue = "latest") String sort) {
        List<FoodDiary> diaries = foodDiaryRepo.findAll();

        // 정렬 조건에 따라 다르게 처리 : 하트순  //..별점순으로 바꾸기
        if (sort.equals("heart")) {
            diaries = diaries.stream()
                    .sorted(Comparator
                            .comparing((FoodDiary d) -> Boolean.TRUE.equals(d.getRestaurant().getHeart())).reversed()
                            .thenComparing(FoodDiary::getCreatedAt, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } else {
            // 기본 정렬: 최신순
            diaries = diaries.stream()
                    .sorted(Comparator.comparing(FoodDiary::getCreatedAt).reversed())
                    .collect(Collectors.toList());
        }

        // DTO 매핑
        List<FoodDiaryFeedResponse> response = diaries.stream()

                .map(d -> new FoodDiaryFeedResponse(
                        d.getId(),
                        d.getPhotoPath(),
                        d.getRestaurant().getHeart()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    // 일기 상세 조회 API (사진 클릭했을 때 전체 일기 보여주기)
    @GetMapping("/detail/{diaryId}")
    public ResponseEntity<FoodDiaryDetailResponse> getDiaryDetail(@PathVariable Long diaryId) {
        FoodDiary diary = foodDiaryRepo.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("일기를 찾을 수 없습니다."));

        Restaurant restaurant = diary.getRestaurant();

        FoodDiaryDetailResponse response = new FoodDiaryDetailResponse(
                diary.getId(),
                diary.getDiaryText(),
                diary.getCreatedAt(),
                diary.getPhotoPath(),
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getPlace(),
                restaurant.getHeart(),
                diary.getRating(),          // 별점 추가
                diary.getMenuName()         // 메뉴 이름 추가
        );


        return ResponseEntity.ok(response);
    }
}




