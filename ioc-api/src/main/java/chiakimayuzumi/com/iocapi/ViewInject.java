package chiakimayuzumi.com.iocapi;

/**
 * Created by yyy on 2018/10/31.
 */
public interface ViewInject<T> {
  void inject(T target, Object source);
}