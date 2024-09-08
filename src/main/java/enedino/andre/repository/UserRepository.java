package enedino.andre.repository;

import enedino.andre.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User buscarPorCpf(String cpf) {
        Map<String, Object> params = new HashMap<>();
        params.put("cpf", cpf);
        return find("cpf = :cpf", params).firstResult();
    }
}
