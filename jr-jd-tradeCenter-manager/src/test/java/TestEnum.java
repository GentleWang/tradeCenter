import jd.jr.workFlow.enums.OperateEnum;
import org.junit.Test;

/**
 * @Autor wangshuo7
 * @Date 2016/7/22 18:48
 */
public class TestEnum {


    @Test
    public void testEnumValueOf(){


       OperateEnum operateEnum =  OperateEnum.valueOf("AGREE");
        System.out.println(operateEnum.getName()+operateEnum.getCode());
    }
}
