package cc.sitec.filter;

import com.sun.tools.javac.util.ServiceLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 凉水
 * @date 2020/1/3 4:08 下午
 **/
public class FilterWrapper {
    private static List<Filter> filterList = new ArrayList<>();
    static {
        ServiceLoader.load(Filter.class).forEach(filterList::add);
    }
    private static Invoker buildChain(){
        return build(0);
    }
    private static Invoker build(int index) {
        // 最后一次返回空函数
        if(index == filterList.size()) return () -> {};
        //Invoker的方法实现 = Filter调用invoke()
        return ()->filterList.get(index).invoke(build(index + 1));
    }

    public static void refer(){
        buildChain().invoke();
    }
}
