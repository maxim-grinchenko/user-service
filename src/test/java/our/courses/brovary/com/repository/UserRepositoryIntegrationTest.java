package our.courses.brovary.com.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import our.courses.brovary.com.domain.entity.User;
import our.courses.brovary.com.repository.db.DataSourceUtil;
import our.courses.brovary.com.repository.impl.UserRepositoryImpl;
import our.courses.brovary.com.util.TestUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        DataSourceUtil.class,
        MysqlDataSource.class
})
@PowerMockIgnore("javax.management.*")
public class UserRepositoryIntegrationTest {

    @Mock
    private MysqlDataSource mockMysqlDataSource;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private Statement mockStatement;
    @Mock
    private Connection mockConnection;
    @Mock
    private ResultSet mockResultSet;
    @Spy
    private List<User> mockListUsers = new ArrayList<>();

    private User user;
    private UserRepository repository;

    @Before
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(DataSourceUtil.class);
        PowerMockito.mockStatic(MysqlDataSource.class);

        PowerMockito.when(DataSourceUtil.get()).thenReturn(mockMysqlDataSource);
        PowerMockito.when(mockMysqlDataSource.getConnection()).thenReturn(mockConnection);
        PowerMockito.when(mockResultSet.next()).thenReturn(true, false);
        PowerMockito.doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());

        user = TestUtil.createUser();
        repository = new UserRepositoryImpl();
    }

    @Test
    public void updateUserTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        PowerMockito.doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
        //Act
        user.setId(1);
        repository.save(user);
        //Assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(7)).setString(anyInt(), anyString());
        verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertNotNull(user);
    }

    @Test
    public void saveUserTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        PowerMockito.when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        PowerMockito.when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        PowerMockito.when(mockResultSet.getInt(mockStatement.RETURN_GENERATED_KEYS)).thenReturn(1);
        //Act
        user = repository.save(user);
        //Assert
        verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStatement, times(7)).setString(anyInt(), anyString());
        verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).getLong(1);
        assertNotNull(user);
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        PowerMockito.doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
        //Act
        repository.deleteById(user.getId());
        //Assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void deleteAllUserTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        //Act
        repository.deleteAll();
        //Assert
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void findByIdTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
        PowerMockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        PowerMockito.when(mockResultSet.next()).thenReturn(true);
        PowerMockito.when(mockResultSet.getTimestamp(anyInt())).thenReturn(Timestamp.valueOf("2018-02-06 00:00:00.0"));
        //Act
        user = repository.findById(user.getId());
        //Assert
        if (mockResultSet.next()) {
            verify(mockPreparedStatement, times(1)).setLong(anyInt(), anyLong());
            verify(mockPreparedStatement, times(1)).executeQuery();
            verify(mockResultSet, times(7)).getString(anyInt());
            verify(mockResultSet, times(2)).getInt(anyInt());
            verify(mockResultSet, times(2)).getTimestamp(anyInt());
            assertNotNull(user);
        } else {
            assertNull(user);
        }
    }

    @Test
    public void findByIdThrowNullTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
        PowerMockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        PowerMockito.when(mockResultSet.next()).thenReturn(false);
        //Act
        user = repository.findById(1);
        //Assert
        assertNull(user);
    }

    @Test
    public void findAllTest() throws SQLException {
        //Arrange
        PowerMockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        PowerMockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        PowerMockito.when(mockResultSet.getTimestamp(anyInt())).thenReturn(Timestamp.valueOf("2018-02-06 00:00:00.0"));
        //Act
        repository.findAll();
        //Assert
        verify(mockPreparedStatement, times(1)).executeQuery();
        if (mockResultSet.next()) {
            verify(mockResultSet, times(7)).getString(anyInt());
            verify(mockResultSet, times(2)).getInt(anyInt());
            verify(mockResultSet, times(2)).getTimestamp(anyInt());
            verify(mockListUsers, times(1)).add(user);
            assertNotNull(mockListUsers);
            assertFalse(mockListUsers.isEmpty());
        } else {
            assertTrue(mockListUsers.isEmpty());
        }
    }
}
