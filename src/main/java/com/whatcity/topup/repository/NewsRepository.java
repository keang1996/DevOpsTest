package com.whatcity.topup.repository;

import com.whatcity.topup.model.dto.IAdminNews;
import com.whatcity.topup.model.table.AdminNews;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<AdminNews, Long> {

  List<AdminNews> findNewsByNewsCategoryId(Long id);

  @Query(value = " select an.news_id , an.topic, an.cover_image , "
    + " an.description , "
    + " an.news_category_id , an.create_date , "
    + " an.news_status "
    + " from admin_news an "
    + " join category_news cn on an.news_category_id = cn.news_category_id "
    + " where cn.status=1 and an.news_status=1 order by an.create_date desc ", nativeQuery = true)
  List<AdminNews> getAllNewsAvaliable();

  @Query(value =
    " select an.news_id newsId , an.topic topic , an.cover_image coverImage , "
      + " an.description description, "
      + " an.news_category_id newsCategoryId, an.create_date createDate, an.news_status newsStatus, "
      + " cn.news_category newsCategory "
      + " from admin_news an "
      + "    join category_news cn on an.news_category_id = cn.news_category_id "
      + "    where cn.status=1 and an.news_status=1 and an.news_category_id = :id "
      + " order by an.create_date desc ", nativeQuery = true)
  List<IAdminNews> getNewsAvaliableByNewsCategoryId(@Param("id") Long id);

  @Query(value =
    " select an.news_id newsId , an.topic topic , an.cover_image coverImage , "
      + " an.description description, "
      + " an.news_category_id newsCategoryId, an.create_date createDate, an.news_status newsStatus, "
      + " cn.news_category newsCategory , "
      + " (select "
      + "    count(*) "
      + "  from admin_news a join category_news c on a.news_category_id = c.news_category_id "
      + "    where c.status=1 and a.news_status=1 and a.news_category_id = :id "
      + "  ) count "
      + " from admin_news an "
      + "    join category_news cn on an.news_category_id = cn.news_category_id "
      + "    where cn.status=1 and an.news_status=1 and an.news_category_id = :id "
      + " order by an.create_date desc "
      + " LIMIT :offset,:itemPerPage ", nativeQuery = true)
  List<IAdminNews> getNewsAvaliableByNewsCategoryIdLimit(@Param("id") Long id,
    @Param("offset") Long offset, @Param("itemPerPage") Long itemPerPage);

  @Query(value =
    "select " +
      " an.news_id newsId, " +
      " an.topic topic , " +
      " an.cover_image coverImage , " +
      " an.description description, " +
      " an.news_category_id newsCategoryId, " +
      " an.create_date createDate, " +
      " an.news_status newsStatus, " +
      " cn.news_category newsCategory, " +
      " cn.create_date catagoryCreateDate, " +
      " cn.update_date catagoryUpdateDate " +
      "from " +
      " admin_news an " +
      "inner join category_news cn  " +
      "on " +
      " an.news_category_id = cn.news_category_id " +
      "where " +
      " cn.status = 1 " +
      " and " +
      " an.news_status = 1 " +
      "order by " +
      " an.create_date desc", nativeQuery = true)
  List<IAdminNews> getAllNews();

  @Query(value = "select  "
    + "       an.news_id ,  "
    + "       an.topic,  "
    + "       an.cover_image ,  "
    + "       an.description ,  "
    + "       an.news_category_id ,  "
    + "       an.create_date ,  "
    + "       an.news_status  "
    + " from admin_news an where an.news_status = '1' order by an.create_date desc limit 4 ", nativeQuery = true)
  List<AdminNews> getLatestNews();

  @Query(value =
    "select an.topic, an.cover_image coverImage, an.create_date createDate from admin_news an  "
      + "    inner join category_news cn on an.news_category_id = cn.news_category_id  "
      + " where cn.news_category_id = '67' and an.news_status = '1' order by an.create_date desc ", nativeQuery = true)
  List<IAdminNews> getMainEventCoverImage();
}
