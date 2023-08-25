package ntq.uet.server.common.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface BeanMapper<T, R> {
    R map(T source);

    void mapTo(T soruce, @MappingTarget R target);

    default List<R> map(List<T> sources) {
        return sources.stream().map(this::map).toList();
    }

    default void mapTo(List<T> sources, @MappingTarget List<R> targets) {
        if (sources.size() != targets.size()) {
            throw new IllegalArgumentException("sources and targets must be same size");
        }
    }
}
