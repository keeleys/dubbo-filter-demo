package cc.sitec.test;

import cc.sitec.filter.Filter;
import cc.sitec.filter.Invoker;

/**
 * @author 凉水
 * @date 2020/1/3 4:11 下午
 **/
public class Filter1 implements Filter {
    public void invoke(Invoker invoker) {
        System.out.println("test1 before");
        invoker.invoke();
        System.out.println("test1 end");
    }
}
