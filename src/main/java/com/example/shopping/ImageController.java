package com.example.shopping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ProductRepository productRepository;

    public ImageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/images/product/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer id) {
        var productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        var product = productOpt.get();
        if (product.getImageData() == null || product.getImageData().length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        String contentType = product.getImageContentType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        }
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(product.getImageData().length);
        return new ResponseEntity<>(product.getImageData(), headers, HttpStatus.OK);
    }
}

