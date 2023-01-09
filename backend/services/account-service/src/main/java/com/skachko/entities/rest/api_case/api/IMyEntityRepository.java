package com.skachko.entities.rest.api_case.api;

import com.skachko.entities.rest.api_case.MyEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMyEntityRepository extends JpaRepositoryImplementation<MyEntity, UUID> {
}
