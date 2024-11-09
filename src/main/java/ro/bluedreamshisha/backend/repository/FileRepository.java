package ro.bluedreamshisha.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.bluedreamshisha.backend.model.file_management.File;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
}
