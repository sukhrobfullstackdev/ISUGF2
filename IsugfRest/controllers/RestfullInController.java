package api.controllers;import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author Kabulov Khusayn
 * Create at 2022-02-22 10:54
 */
@RestController
@RequestMapping("restfull/ins")
@Tag(name = "")
public class RestfullInController {

    private final TypedRepository repository;

    private final EntityMapper entityMapper;

    private final DataMapper dataMapper;

    public RestfullInController(TypedRepository repository, EntityMapper entityMapper, DataMapper dataMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
        this.dataMapper = dataMapper;
    }

    @GetMapping
    @Operation(operationId = "getRestfullIns", summary = "查询列表")
    public List<RestfullInInfo> get(RestfullInCriteria criteria) {
        return repository.findAll(criteria).map(dataMapper::of);
    }

    @GetMapping("{id}")
    @Operation(operationId = "getRestfullIn", summary = "查询单条")
    public RestfullInInfo get(@PathVariable String id) {
        return repository.loadRestfullIn(id).map(dataMapper::of).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @Transactional
    @Operation(operationId = "addRestfullIn", summary = "新增")
    public RestfullInInfo add(@Validated @RequestBody RestfullInUpdate data) {
        var restfullIn = new RestfullIn(UUID.randomUUID().toString());
        entityMapper.assign(restfullIn, data);
        repository.add(restfullIn);
        return dataMapper.of(restfullIn);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(operationId = "saveRestfullIn", summary = "更新")
    public void update(@PathVariable String id, @Validated @RequestBody RestfullInUpdate data) {
        var restfullIn = repository.loadRestfullIn(id).orElse(new RestfullIn(id));
        entityMapper.assign(restfullIn, data);
        repository.add(restfullIn);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(operationId = "updateRestfullIn", summary = "局部更新")
    public void update(@PathVariable String id, @Validated @RequestBody MergePatch<RestfullInUpdate> patch) {
        var restfullIn = repository.loadRestfullIn(id).orElseThrow(ResourceNotFoundException::new);
        var info = dataMapper.toUpdate(restfullIn);
        var patchInfo = patch.apply(info);
        entityMapper.assign(restfullIn, patchInfo);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(operationId = "deleteRestfullIn", summary = "删除")
    public void delete(@PathVariable String id) {
        var restfullIn = repository.loadRestfullIn(id).orElseThrow(ResourceNotFoundException::new);
        repository.remove(restfullIn);
    }

}