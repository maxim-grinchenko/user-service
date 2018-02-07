package our.courses.brovary.com.repository.impl;

import lombok.extern.slf4j.Slf4j;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.infra.util.DateUtil;
import our.courses.brovary.com.repository.UserRepository;
import our.courses.brovary.com.repository.db.DataSourceUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private static final String DELETE_BY_ID_SQL = "DELETE FROM UNIVERSITY.USERS_TABLE WHERE ID = ?";
    private static final String DELETE_ALL_SQL = "TRUNCATE UNIVERSITY.USERS_TABLE";
    private static final String FIND_ALL_SQL = "SELECT * FROM UNIVERSITY.USERS_TABLE";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM UNIVERSITY.USERS_TABLE WHERE ID = ?";
    private static final String SAVE_SQL = "INSERT INTO UNIVERSITY.USERS_TABLE (NAME, LAST_NAME, MIDDLE_NAME, PHONE, EMAIL, " +
            "LOGIN, PASSWORD, ROLE_ID, CREATED_AT) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE UNIVERSITY.users_table USERS_TABLE SET NAME = ?, LAST_NAME = ?, " +
            "MIDDLE_NAME = ?, PHONE = ?, EMAIL = ?, LOGIN = ?, PASSWORD = ?, ROLE_ID = ?, UPDATED_AT = ? WHERE ID = ?";

    @Override
    public void deleteById(final long id) {
        try (Connection connection = DataSourceUtil.get().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_SQL)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DataSourceUtil.get().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_ALL_SQL)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(final long id) {
        return findById(id) != null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSourceUtil.get().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt(1));
                        user.setName(rs.getString(2));
                        user.setLastName(rs.getString(3));
                        user.setMiddleName(rs.getString(4));
                        user.setPhone(rs.getString(5));
                        user.setEmail(rs.getString(6));
                        user.setLogin(rs.getString(7));
                        user.setPassword(rs.getString(8));
                        user.setRoleId(rs.getInt(9));
                        user.setCreatedAt(DateUtil.timeToLocalDateTime(rs.getTimestamp(10).getTime()));

                        Timestamp updateTime = rs.getTimestamp(11);
                        if (updateTime != null) user.setUpdatedAt(DateUtil.timeToLocalDateTime(updateTime.getTime()));

                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public User findById(long id) {
        try (Connection con = DataSourceUtil.get().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL)) {
                ps.setLong(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt(1));
                        user.setName(rs.getString(2));
                        user.setLastName(rs.getString(3));
                        user.setMiddleName(rs.getString(4));
                        user.setPhone(rs.getString(5));
                        user.setEmail(rs.getString(6));
                        user.setLogin(rs.getString(7));
                        user.setPassword(rs.getString(8));
                        user.setRoleId(rs.getInt(9));
                        user.setCreatedAt(DateUtil.timeToLocalDateTime(rs.getTimestamp(10).getTime()));

                        Timestamp updateTime = rs.getTimestamp(11);
                        if (updateTime != null) user.setUpdatedAt(DateUtil.timeToLocalDateTime(updateTime.getTime()));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User save(final User user) {
        long id = user.getId();

        try (Connection con = DataSourceUtil.get().getConnection()) {
            if (id > 0) {
                try (PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getMiddleName());
                    ps.setString(4, user.getPhone());
                    ps.setString(5, user.getEmail());
                    ps.setString(6, user.getLogin());
                    ps.setString(7, user.getPassword());
                    ps.setInt(8, user.getRoleId());

                    final LocalDateTime now = LocalDateTime.now();
                    ps.setObject(9, now);
                    ps.setLong(10, id);

                    ps.executeUpdate();

                    user.setUpdatedAt(now);
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getMiddleName());
                    ps.setString(4, user.getPhone());
                    ps.setString(5, user.getEmail());
                    ps.setString(6, user.getLogin());
                    ps.setString(7, user.getPassword());
                    ps.setInt(8, user.getRoleId());

                    final LocalDateTime now = LocalDateTime.now();
                    ps.setObject(9, now);

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) id = rs.getLong(1);

                    user.setCreatedAt(now);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        user.setId(id);
        return user;
    }

    @Override
    public void saveAll(List<User> users) {
        users.forEach(this::save);
    }
}