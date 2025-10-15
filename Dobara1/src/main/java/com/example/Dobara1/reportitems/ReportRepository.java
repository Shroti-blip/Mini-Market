package com.example.Dobara1.reportitems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity , Integer>{

    List<ReportEntity> findByListing_User_Id(Integer userId);
}
