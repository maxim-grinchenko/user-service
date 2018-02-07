package our.courses.brovary.com.singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum MapperFactory {
    INSTANCE;

    private ObjectMapper mapper;

    MapperFactory() {
        this.mapper = new ObjectMapper();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}