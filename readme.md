## dubbo filter 机制 + spi 的简单实现

运行 `cc.sitec.Test.main`
```java
public class FilterWrapper {
    private static List<Filter> filterList = new ArrayList<>();
    static {
        ServiceLoader.load(Filter.class).forEach(filterList::add);
    }
    private static Invoker buildChain(){
        return build(0);
    }
    private static Invoker build(int index) {
        // 最后一次返回空Invoker实现
        if(index == filterList.size()) return () -> {};
        //Invoker的方法实现 = Filter调用invoke(参数是下一个Invoker)
        return ()->filterList.get(index).invoke(build(index + 1));
    }

    public static void refer(){
        buildChain().invoke();
    }
}
```

![img1](doc/img1.png)

![img1](doc/img2.png)