package UrlShorter;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TableRepository extends CrudRepository<TableEntity, Long> {
    List<TableEntity> findByUrl(String url);
    TableEntity findById(long id);
}