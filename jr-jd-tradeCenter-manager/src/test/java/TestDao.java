import com.jd.jr.tradeCenter.dao.impl.AccountMapper;
import com.jd.jr.tradeCenter.manager.enums.UserEnum;
import com.jd.jr.tradeCenter.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * Created by wangshuo7 on 2016/6/29.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:/spring-config-test.xml")
public class TestDao {

    @Autowired
    private DataSourceTransactionManager dataSource;
    @Autowired
    private AccountMapper accountDao;
    @Test
    public void testDatabase(){
        System.err.println("222");
        System.err.println("111");
        DataSource dataSource1 =  dataSource.getDataSource();
        Connection connection = null;
        Statement statment = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource1.getConnection();
            statment = connection.createStatement();
            resultSet = statment.executeQuery("SELECT * FROM account");
            //+resultSet.getString(2)+":"+resultSet.getString("3")+":"+resultSet.getString("4")
            while (resultSet.next()){
                System.err.println(resultSet.getNString(1)+":"+resultSet.getString(2)+":"+resultSet.getString(3)+":"+resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                statment.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testAccountDao(){
        Account param = new Account();
        List<Account> accountList = accountDao.queryByParam(param);
        for (Account account : accountList) {
            System.err.println(account.toString());
        }
    }

    @Test
    public void testEmum(){
        UserEnum user = UserEnum.valueOf("inputer1");
        System.out.println(user.getRoles());
        System.out.println(user.getUserID());
        System.out.println(user.getUserName());
    }
}
