package br.com.softplan.sajadv.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    byte[] readFile(String urlFile);

    String saveFile(MultipartFile file, Integer id);
}
