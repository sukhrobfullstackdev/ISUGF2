package api.components;import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DataMapper {
    RestfullInInfo of(RestfullIn restfullIn);

    RestfullInUpdate toUpdate(RestfullIn restfullIn);

    RestfullInInfo of(RestfullIn restfullIn);

    RestfullInUpdate toUpdate(RestfullIn restfullIn);

    RestfullInInfo of(RestfullIn restfullIn);

    RestfullInUpdate toUpdate(RestfullIn restfullIn);
}