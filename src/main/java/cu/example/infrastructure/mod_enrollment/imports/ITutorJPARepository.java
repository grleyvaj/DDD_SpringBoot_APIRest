package cu.example.infrastructure.mod_enrollment.imports;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.TutorTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITutorJPARepository extends JpaRepository<TutorTable, Long> {
}
