package pl.mantiscrab.budgetr.registration;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDummyCrudRepository<T, ID> implements CrudRepository<T, ID> {
    protected final Map<ID, T> map;
    protected Long index;

    public InMemoryDummyCrudRepository() {
        this.map = new HashMap<>();
        this.index = 1L;
    }


    @Override
    public <S extends T> S save(S entity) {
        throw new NotImplementedException();
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<T> findById(ID id) {
        throw new NotImplementedException();
    }

    @Override
    public boolean existsById(ID id) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<T> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        throw new NotImplementedException();
    }

    @Override
    public void deleteById(ID id) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(T entity) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException();
    }
}
