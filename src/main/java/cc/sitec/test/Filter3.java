package cc.sitec.test;

import cc.sitec.filter.Filter;
import cc.sitec.filter.Invoker;

/**
 * @author 凉水
 * @date 2020/1/6 10:38 上午
 **/
public class Filter3 implements Filter {
    @Override
    public void invoke(Invoker invoker) {
        System.out.println("filter3");
        invoker.invoke();
    }
}
