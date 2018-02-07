package our.courses.brovary.com.singleton;

import lombok.Getter;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.repository.impl.UserRepositoryImpl;

@Getter
public enum UserRepositoryFactory {
    INSTANCE;
    private UserRepository repository;

    UserRepositoryFactory() {
        //this.repository = new UserInMemoryRepository(new CopyOnWriteArrayList<>());
        this.repository = new UserRepositoryImpl();
    }
}