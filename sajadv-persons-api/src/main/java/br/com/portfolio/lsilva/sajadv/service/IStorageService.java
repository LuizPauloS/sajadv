package br.com.portfolio.lsilva.sajadv.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    byte[] readFile(Integer id);

    String saveFile(MultipartFile file, Integer id);
}
