package com.dental.doctor.repository;

import com.dental.datastore.DataStore;
import com.dental.repository.AvatarPathRepository;


import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Dependent
public class AvatarRepository implements AvatarPathRepository<byte[], Path> {

    @Override
    public Optional<byte[]> find(Path path) {

        try {
            Optional<byte[]> avatar = Optional.of(Files.readAllBytes(path));
            return avatar;
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(byte[] avatarBytes, Path path) {
        create(avatarBytes, path);
    }

    @Override
    public void create(byte[] avatarBytes, Path path) {
        try {
            Files.write(path, avatarBytes);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Path path) {
        try {
            Files.deleteIfExists(path);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
