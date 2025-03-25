package com.example.HannipmanApp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TotalDiaryService {
    private final RestaurantRepo restaurantRepo;
    private final FoodDiaryRepo foodDiaryRepo;

    public Map<String, Long> getSummary() {
        long heartCount = restaurantRepo.countByHeartTrue();
        long diaryCount = foodDiaryRepo.count();

        Map<String, Long> result = new HashMap<>();
        result.put("totalHearts", heartCount);
        result.put("totalDiaries", diaryCount);

        return result;
    }
}