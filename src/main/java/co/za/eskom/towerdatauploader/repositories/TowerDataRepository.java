package co.za.eskom.towerdatauploader.repositories;

import co.za.eskom.towerdatauploader.entities.TowerDataEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TowerDataRepository extends CrudRepository<TowerDataEntity,Integer> {
    @Override
    List<TowerDataEntity> findAll();
}
