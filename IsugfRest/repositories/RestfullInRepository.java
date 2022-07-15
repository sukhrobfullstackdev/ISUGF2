package domain.repositories;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestfullInRepository extends JpaRepository<RestfullIn, BigDecimal>, QuerydslPredicateExecutor<RestfullIn> {
}