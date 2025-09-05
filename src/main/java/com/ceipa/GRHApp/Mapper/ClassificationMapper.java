package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.ClassificationEntity;
import com.ceipa.GRHApp.Model.Classification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ClassificationMapper {

    // ÚNICO método elemento -> evita ambigüedad
    Classification toModel(ClassificationEntity entity);

    // Lista (opcional, MapStruct la infiere, pero puedes declararla)
    List<Classification> toModelList(List<ClassificationEntity> entities);
}
