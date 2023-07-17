package server.stutino.domain.matirial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.stutino.domain.matirial.entity.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("select m from Material m where m.program.id = :programId")
    List<Material> findAllByProgramId(@Param("programId") Long programId);

    @Query("select m from Material m where m.id = :materialId")
    Material findAllByMaterialId(@Param("materialId") Long materialId);
}