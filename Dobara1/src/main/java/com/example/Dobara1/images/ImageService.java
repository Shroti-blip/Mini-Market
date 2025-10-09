package com.example.Dobara1.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

public void saveImage(ImageEntity image){
    imageRepository.save(image);
}
}
