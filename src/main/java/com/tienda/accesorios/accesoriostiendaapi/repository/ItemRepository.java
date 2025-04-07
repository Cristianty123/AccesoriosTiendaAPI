package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Page<Item> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Item> findAllByItemtypeIdOrderByCreatedAtDesc(String itemTypeId, Pageable pageable);
}