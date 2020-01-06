package cc.sitec.filter;

import com.sun.tools.javac.util.ServiceLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 凉水
 * @date 2020/1/3 4:08 下午
 **/
public class FilterWrapper {
    private static Invoker buildChain(){
        List<Filter> filterList = new ArrayList<>();
        ServiceLoader.load(Filter.class).forEach(filterList::add);
        // 最后一次啥都不做
        Invoker last = () -> {};
        for(int i=filterList.size()-1;i>=0;i--){
            final Filter filter = filterList.get(i);
            final Invoker next = last;
            last = () -> filter.invoke(next);
        }
        return last;
    }
    public static void refer(){
        buildChain().invoke();
    }
}
