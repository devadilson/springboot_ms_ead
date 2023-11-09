package com.ead.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.course.models.CourseModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

}
