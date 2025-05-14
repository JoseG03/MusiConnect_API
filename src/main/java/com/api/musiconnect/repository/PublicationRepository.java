package com.api.musiconnect.repository;
import com.api.musiconnect.model.entity.Publication;
import com.api.musiconnect.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
