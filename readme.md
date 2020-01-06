## dubbo filter 机制 + spi 的简单实现

运行 `cc.sitec.Test.main`



![img1](doc/img1.png)

![img1](doc/img2.png)

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


### 类似的javascript洋葱实现

```js
let middleware = []
middleware.push((next) => {
	console.log(1)
	next()
	console.log(2)
})
middleware.push((next) => {
	console.log(3)
	next()
	console.log(4)
})
middleware.push(() => {
    console.log("你好")
})

let compose = function(middleware) {
    return async function() {
        let args = arguments
        let dispatch = async function(i){
            const fn = middleware[i];
            if(fn==null) return null;
            return await fn(()=>dispatch(i+1), ...args);
        }
        return dispatch(0);
    }
}
let fn = compose(middleware)
fn()
```

##  小结
js可以把函数当参数，但java不行，java的参数只能是变量，所以引入了Invoker类来包装函数的实现.