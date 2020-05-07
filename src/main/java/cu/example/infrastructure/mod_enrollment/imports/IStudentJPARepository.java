package cu.example.infrastructure.mod_enrollment.imports;

import cu.example.infrastructure.mod_enrollment.persistence.mapping.StudentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentJPARepository extends JpaRepository<StudentTable, Long>, PagingAndSortingRepository<StudentTable, Long> {
}
