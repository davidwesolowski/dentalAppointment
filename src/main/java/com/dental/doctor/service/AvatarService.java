package com.dental.doctor.service;

import com.dental.doctor.repository.AvatarRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AvatarService {

    private AvatarRepository avatarRepository;

    @Inject
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Optional<byte[]> find(Path path) {
        return avatarRepository.find(path);
    }

    public void create(byte[] avatar, Path path) {
        avatarRepository.create(avatar, path);
    }

    public void update(byte[] avatar, Path path) {
        avatarRepository.update(avatar, path);
    }

    public void delete(Path path) {
        avatarRepository.delete(path);
    }

}
