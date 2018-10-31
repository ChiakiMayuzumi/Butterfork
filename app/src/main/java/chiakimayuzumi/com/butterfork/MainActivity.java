package chiakimayuzumi.com.butterfork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import chiakimayuzumi.com.iocannotation.*;
import chiakimayuzumi.com.iocapi.ViewInjector;

/**
 * Created by yyy on 2018/10/31.
 */
@ContentView(R.layout.main_activity)
public class MainActivity extends AppCompatActivity {

  @BindView(R.id.textView)
  public TextView textView;

  @BindView(R.id.imageView)
  ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewInjector.injectView(this);
    textView.setText("hahahahaha");
  }
}