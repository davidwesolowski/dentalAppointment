package com.dental.repository;

import java.util.Optional;

public interface AvatarPathRepository<E, K> {
    Optional<E> find(K path);
    void update(E avatar, K path);
    void create(E avatar, K path);
    void delete(K path);
}
