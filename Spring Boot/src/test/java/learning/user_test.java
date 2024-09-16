//package learning;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.swing.plaf.SpinnerUI;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class user_test {
//
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,2,3",
//            "3,5,8",
//            "2,3,5"
//    })
//    public void parameterTest(int a,int b,int c){
//        assertEquals(c,a + b);
//    }
//
//
//
//    @ParameterizedTest
//    @ValueSource(strings ={
//            "hello",
//            "world"
//    })
//    public void valuescore(String name){
//    assertEquals("kd",name);
//    }
//
//
//}
