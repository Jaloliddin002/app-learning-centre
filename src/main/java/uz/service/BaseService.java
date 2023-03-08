package uz.service;

/*
   T - requestDto
   R - responseDto
   I - Id
 */
import org.springframework.stereotype.Component;
import uz.dto.response.RestAPIResponse;

@Component
public interface BaseService<T,P,I> {

    RestAPIResponse create(T t);

    RestAPIResponse update(I id,T t);

    RestAPIResponse delete(I id);

    RestAPIResponse get(I id);

    RestAPIResponse getAll(P p);
}
