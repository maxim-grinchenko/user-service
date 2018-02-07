package our.courses.brovary.com.repository.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.aeonbits.owner.ConfigFactory;
import our.courses.brovary.com.config.AppConfig;

import javax.sql.DataSource;

public class DataSourceUtil {
    private static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class);

    public static DataSource get() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(CONFIG.url());
        mysqlDS.setUser(CONFIG.login());
        mysqlDS.setPassword(CONFIG.password());
        return mysqlDS;
    }
}