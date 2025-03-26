package com.example.HannipmanApp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping
    public ResponseEntity<?> getAllRestaurants(HttpServletRequest request) {
        List<Restaurant> restaurants = restaurantRepo.findAll();

        // HTML 형태로 데이터를 반환
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<html><head><title>Restaurants</title></head><body>");
        htmlResponse.append("<h1>Restaurant List</h1>");
        htmlResponse.append("<ul>");

        for (Restaurant restaurant : restaurants) {
            htmlResponse.append("<li>");
            htmlResponse.append("<h2>").append(restaurant.getName()).append("</h2>");
            if (restaurant.getImage() != null) {
                String imageUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                        + "/restaurants/image/" + restaurant.getId();
                htmlResponse.append("<img src=\"").append(imageUrl).append("\" alt=\"Image\" style=\"width:200px;\"/>");
            } else {
                htmlResponse.append("<p>No image available</p>");
            }
            htmlResponse.append("<p><strong>id:</strong> ").append(restaurant.getId()).append("</p>");
            htmlResponse.append("<p><strong>Menu:</strong> ").append(String.join(", ", restaurant.getMenu())).append("</p>");
            htmlResponse.append("<p><strong>Price:</strong> ").append(restaurant.getPrice()).append("</p>");
            htmlResponse.append("<p><strong>Rating:</strong> ").append(restaurant.getRating()).append("</p>");
            htmlResponse.append("<p><strong>Location:</strong> ")
                    .append(restaurant.getLatitude()).append(", ").append(restaurant.getLongitude())
                    .append("</p>");
            htmlResponse.append("<p><strong>Category:</strong> ").append(restaurant.getKategorie()).append("</p>");
            htmlResponse.append("<p><strong>Phone Number:</strong> ").append(restaurant.getPhoneNumber()).append("</p>");
            htmlResponse.append("<p><strong>Business Hours:</strong> ").append(restaurant.getBusinessHours()).append("</p>");
            htmlResponse.append("<p><strong>Closed Days:</strong> ").append(restaurant.getClosedDays()).append("</p>");
            htmlResponse.append("<p><strong>Break Time:</strong> ").append(restaurant.getBreakTime()).append("</p>");
            htmlResponse.append("<p><strong>Kiosk:</strong> ").append(restaurant.getKiosk()).append("</p>");
            htmlResponse.append("<p><strong>Place:</strong> ").append(restaurant.getPlace()).append("</p>");

            htmlResponse.append("</li>");
        }

        htmlResponse.append("</ul></body></html>");
        return ResponseEntity.ok(htmlResponse.toString());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Restaurant> restaurantOpt = restaurantRepo.findById(id);

        if (restaurantOpt.isPresent() && restaurantOpt.get().getImage() != null) {
            byte[] imageData = restaurantOpt.get().getImage();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/locations")
    public List<RestaurantDTO> getRestaurantLocations() {
        return restaurantRepo.findAll().stream()
                .map(r -> new RestaurantDTO(r.getLatitude(), r.getLongitude(), r.getName()))
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}/heart")
    public ResponseEntity<String> toggleHeart(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("식당을 찾을 수 없습니다."));

        Boolean currentHeart = Boolean.TRUE.equals(restaurant.getHeart());
        restaurant.setHeart(!currentHeart);

        restaurantRepo.save(restaurant);

        return ResponseEntity.ok("하트 상태가 " + (!currentHeart ? "❤️ 좋아요" : "💔 해제") + "로 변경되었습니다.");
    }

}
