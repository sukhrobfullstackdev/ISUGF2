package api.components;import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityMapper {
    void assign(@MappingTarget RestfullIn restfullIn, RestfullInUpdate data);

    void assign(@MappingTarget RestfullIn restfullIn, RestfullInUpdate data);

    void assign(@MappingTarget RestfullIn restfullIn, RestfullInUpdate data);
}