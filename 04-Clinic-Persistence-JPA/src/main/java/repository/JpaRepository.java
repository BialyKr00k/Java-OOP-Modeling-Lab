package repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class JpaRepository {

    protected final EntityManager em;
}
