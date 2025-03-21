package com.example.HannipmanApp;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVDataLoader {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @PostConstruct
    public void loadDataFromCSVAndImages() {
        String csvFilePath = "src/main/resources/data/restaurantDB.csv";
        String imageDirectoryPath = "src/main/resources/images/";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            reader.readNext(); // 헤더 건너뛰기

            while ((line = reader.readNext()) != null) {

                // 식당 이름으로 중복검사
                String restaurantName = line[3];
                if (restaurantRepo.existsByName(restaurantName)) {
                    System.out.println("중복된 레스토랑: " + restaurantName);
                    continue; // 이미 존재하면 해당 데이터를 건너뛰고 다음 데이터로 넘어감
                }

                Restaurant restaurant = new Restaurant();
                restaurant.setHeart(line[1].equals("1")); // heart 값을 1이면 true로 설정
                restaurant.setDiary(line[2].equals("1"));
                restaurant.setName(line[3]);
                restaurant.setMenu(Arrays.asList(line[4].split(",")));
                restaurant.setPrice(parsePrices(line[5]));
                restaurant.setRating(Double.parseDouble(line[6]));
                restaurant.setKategorie(line[7]);
                restaurant.setPhoneNumber(line[8]);
                restaurant.setBusinessHours(line[9]);
                restaurant.setClosedDays(line[10]);
                restaurant.setBreakTime(line[11]);
                restaurant.setKiosk(line[12]);
                restaurant.setPlace(line[13]);
                restaurant.setLatitude(Double.parseDouble(line[14]));
                restaurant.setLongitude(Double.parseDouble(line[15]));

                // 이미지 파일 읽어오기
                String imageFileName = line[0] + ".jpg"; // 예: "1.jpg"
                File imageFile = new File(imageDirectoryPath + imageFileName);
                if (imageFile.exists()) {
                    byte[] imageData = FileCopyUtils.copyToByteArray(new FileInputStream(imageFile));
                    restaurant.setImage(imageData);
                } else {
                    System.out.println(imageFileName + " 파일을 찾을 수 없습니다.");
                }

                restaurantRepo.save(restaurant);
            }
            System.out.println("CSV 데이터와 이미지를 성공적으로 저장했습니다!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Integer> parsePrices(String priceData) {
        String[] prices = priceData.split(",");
        List<Integer> priceList = new ArrayList<>();
        for (String price : prices) {
            try {
                double priceValue = Double.parseDouble(price); // 실수 변환
                priceList.add((int) Math.round(priceValue)); // 반올림 후 정수 변환
            } catch (NumberFormatException e) {
                System.err.println("가격 변환 오류: " + price);
                priceList.add(0); // 오류 발생 시 기본값 0 추가
            }
        }
        return priceList;
    }
}