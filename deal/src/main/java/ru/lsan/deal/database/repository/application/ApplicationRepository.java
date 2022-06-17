package ru.lsan.deal.database.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.application.ApplicationEntity;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    @Query("select b from ApplicationEntity b where b.id = :id")
    ApplicationEntity findByAppId(@Param("id") Long id);

}
