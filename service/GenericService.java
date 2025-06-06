package service;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class GenericService<T> {
    protected Connection conn = DatabaseConfig.getConnection();

    private static GenericService<?> instance = null;

    protected GenericService() throws SQLException {
    }

    @SuppressWarnings("unchecked")
    public static <S extends GenericService<?>> S getInstance(Class<S> clazz) {
        if (instance == null) {
            try {
                instance = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Nu s-a putut crea instanța singleton pentru " + clazz, e);
            }
        }
        return (S) instance;
    }

    public abstract void create(T entity) throws SQLException;
    public abstract T read(int id) throws SQLException;
    public abstract List<T> readAll() throws SQLException;
    public abstract void update(T entity) throws SQLException;
    public abstract void delete(int id) throws SQLException;


    protected PreparedStatement prepare(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }
}