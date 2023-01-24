package pl.mantiscrab.budgetr.registration;

import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryDummyCrudRepository<T, ID> implements CrudRepository<T, ID> {
    protected final Map<ID, T> map;
    protected Long index;

    InMemoryDummyCrudRepository() {
        this.map = new HashMap<>();
        this.index = 1L;
    }


    @Override
    public <S extends T> S save(S entity) {
        throw new MethodNotImplementedException();
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        throw new MethodNotImplementedException();
    }

    @Override
    public Optional<T> findById(ID id) {
        throw new MethodNotImplementedException();
    }

    @Override
    public boolean existsById(ID id) {
        throw new MethodNotImplementedException();
    }

    @Override
    public Iterable<T> findAll() {
        throw new MethodNotImplementedException();
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        throw new MethodNotImplementedException();
    }

    @Override
    public long count() {
        throw new MethodNotImplementedException();
    }

    @Override
    public void deleteById(ID id) {
        throw new MethodNotImplementedException();
    }

    @Override
    public void delete(T entity) {
        throw new MethodNotImplementedException();
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new MethodNotImplementedException();
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new MethodNotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new MethodNotImplementedException();
    }
}
