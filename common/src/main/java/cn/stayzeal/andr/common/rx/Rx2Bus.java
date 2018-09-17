package cn.stayzeal.andr.common.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxJava2版本的RxBus
 */
public class Rx2Bus {

    private static volatile Rx2Bus defaultInstance;
    // 主题
    private final Subject bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private Rx2Bus() {
        bus = PublishSubject.create().toSerialized();
    }


    /**
     * 使用PublicSubject
     *
     * @return
     */
    public static Rx2Bus getDefault() {
        if (defaultInstance == null) {
            synchronized (Rx2Bus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new Rx2Bus();
                }
            }
        }
        return defaultInstance;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
