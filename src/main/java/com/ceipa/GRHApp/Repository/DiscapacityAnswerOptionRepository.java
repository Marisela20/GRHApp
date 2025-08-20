package com.ceipa.GRHApp.Repository;

import com.ceipa.GRHApp.DatabaseEntity.DiscapacityAnswerOptionEntity;
import com.ceipa.GRHApp.DatabaseEntity.DiscapacityQuestionEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiscapacityAnswerOptionRepository extends JpaRepository<DiscapacityAnswerOptionEntity, Integer> {

    List<DiscapacityAnswerOptionEntity> findByQuestion_Id(int questionId);

    List<DiscapacityAnswerOptionEntity> findByQuestion(DiscapacityQuestionEntity question);

    // ✅ Método que necesitas para mostrar las opciones ordenadas por score
    List<DiscapacityAnswerOptionEntity> findByQuestion_IdOrderByScoreAsc(Integer questionId);

    // 🔹 Borrar todas las opciones de un conjunto de preguntas
    @Modifying
    @Query("delete from DiscapacityAnswerOptionEntity o where o.question.id in :qids")
    void deleteByQuestion_IdIn(@Param("qids") List<Integer> qids);
}
