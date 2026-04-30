package com.example.haedal.service;


import com.example.haedal.domain.User;
import com.example.haedal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ImageService {
    private final UserRepository userRepository; //아래와 같은 private final이지만 역할이 다름
    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "src/main/resources/static");
    // System.getProperty("user.dir"): 현재작업디렉토리의 절대경로를 가져옴.

    @Autowired
    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String updateUserImage(User user, MultipartFile image) throws IOException { //IOException: 따로 예외처리 필요
        // 현재 시간을 기준으로 고유한 이미지 이름 생성
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")); //.now: 현재시간 받아오기
        String uniqueImageName = timestamp + "_" + image.getOriginalFilename();

        Path filePath = uploadDir.resolve("userImages").resolve(uniqueImageName);
        Files.createDirectories(filePath.getParent());  // 경로폴더 없으면 생성
        image.transferTo(filePath.toFile()); //경로에 이미지 전송

        user.setImageUrl("userImages/" + uniqueImageName);
        userRepository.save(user);

        return "userImages/" + uniqueImageName; //이미지 저장 경로 string으로 return해줌
    }

    public String encodeImageToBase64(String imagePath) { //Base64로 encode를 해줌
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes); //getEncoder: 자체적으로 있는..
        } catch (IOException e) {
            return null;
        }
    }


    public String savePostImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("비어있는 이미지 파일입니다..");
        }

        // 현재 시간을 기준으로 고유한 이미지 이름 생성
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String uniqueImageName = timestamp + "_" + image.getOriginalFilename();

        Path filePath = uploadDir.resolve("postImages").resolve(uniqueImageName);
        Files.createDirectories(filePath.getParent());  // 경로폴더 없으면 생성
        image.transferTo(filePath.toFile());

        return "postImages/" + uniqueImageName;  // 상대 경로 반환
    }
}
