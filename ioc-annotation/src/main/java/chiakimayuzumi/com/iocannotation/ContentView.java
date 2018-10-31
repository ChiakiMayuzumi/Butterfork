package chiakimayuzumi.com.iocannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yyy on 2018/10/31.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ContentView {
  int value();
}