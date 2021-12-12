package com.whatcity.topup.service;

import com.whatcity.topup.model.dto.IAdminNews;
import com.whatcity.topup.model.table.AdminNews;
import com.whatcity.topup.repository.NewsRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository newsRepository;

  public NewsService(NewsRepository newsRepository) {
    this.newsRepository = newsRepository;
  }

  public List<AdminNews> findNewsAll() {
    return newsRepository.findAll();
  }

  public AdminNews findNewsById(Long id) {
    return newsRepository.findById(id).orElse(null);
  }

  public List<AdminNews> findNewsByNewsCategoryId(Long id) {
    return newsRepository.findNewsByNewsCategoryId(id);
  }

  public List<AdminNews> getAllNewsAvaliable() {
    return newsRepository.getAllNewsAvaliable();
  }

  public List<IAdminNews> getNewsAvaliableByNewsCategoryId(Long id) {
    return newsRepository.getNewsAvaliableByNewsCategoryId(id);
  }

  public List<IAdminNews> getNewsAvaliableByNewsCategoryIdLimit(Long id, Long offset,
    Long itemPerPage) {
    return newsRepository.getNewsAvaliableByNewsCategoryIdLimit(id, offset, itemPerPage);
  }

  public List<IAdminNews> findNews() {
    return newsRepository.getAllNews();
  }

  public List<AdminNews> getLatestNews() {
    return newsRepository.getLatestNews();
  }

  public List<IAdminNews> getMainEventCoverImage() {
    return newsRepository.getMainEventCoverImage();
  }
}
