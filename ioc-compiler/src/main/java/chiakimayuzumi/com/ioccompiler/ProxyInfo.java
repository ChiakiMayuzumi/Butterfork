package chiakimayuzumi.com.ioccompiler;

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by yyy on 2018/10/31.
 */

public class ProxyInfo {

  private String packageName;

  private String proxyClassName;

  private TypeElement typeElement;

  public Map<Integer, VariableElement> injectVariables = new HashMap<>();

  public int contentViewId;

  public static final String PROXY = "ViewInject";

  public ProxyInfo(Elements elementUtils, TypeElement classElement) {
    this.typeElement = classElement;
    PackageElement packageElement = elementUtils.getPackageOf(typeElement);

    String packageName = packageElement.getQualifiedName().toString();
    String className = ClassValidator.getClassName(typeElement, packageName);

    this.packageName = packageName;
    this.proxyClassName = className + "$$" + PROXY;
  }

  public String generateJavaCode() {
    StringBuilder builder = new StringBuilder();
    builder.append("// Generated code. Do not modify!\n");
    builder.append("package ").append(packageName).append(";\n\n");
    //builder.append("import chiakimayuzumi.com.ioccompiler.*;\n");
    builder.append("import chiakimayuzumi.com.iocapi.ViewInject;\n");
    builder.append('\n');

    builder.append("public class ").append(proxyClassName).
        append(" implements ").append(ProxyInfo.PROXY).
        append("<").append(typeElement.getQualifiedName()).append(">");
    builder.append("{\n");

    generateMethods(builder);

    builder.append('\n');
    builder.append("}\n");
    return builder.toString();
  }

  /**
   * 生成inject方法的代码
   */
  private void generateMethods(StringBuilder builder) {
    builder.append("@Override\n");
    builder.append("public void inject(")
        .append(typeElement.getQualifiedName())
        .append(" host, Object source) {\n");

    StringBuilder ifStr = new StringBuilder();
    StringBuilder elseStr = new StringBuilder();

    //遍历所有的BindView注解的信息
    for (int id : injectVariables.keySet()) {
      VariableElement variableElement = injectVariables.get(id);
      String name = variableElement.getSimpleName().toString();
      String type = variableElement.asType().toString();

      ifStr.append("host.").append(name).append(" = ");
      ifStr.append("(")
          .append(type)
          .append(")(((android.app.Activity)source).findViewById(")
          .append(id)
          .append("));");

      elseStr.append("host.").append(name).append(" = ");
      elseStr.append("(")
          .append(type)
          .append(")(((android.view.View)source).findViewById(")
          .append(id)
          .append("));");
    }
    // if
    builder.append(" if(source instanceof android.app.Activity) {\n");
    // 设置ContentView
    if (contentViewId != 0) {
      builder.append("host.setContentView(").append(contentViewId).append(");\n");
    }
    builder.append(ifStr);
    // else
    // 如果是View类型，不用设置ContentView
    builder.append("\n}\nelse {\n");
    builder.append(elseStr);
    builder.append("\n}\n");
    builder.append("};");
  }

  public String getProxyClassFullName() {
    return packageName + "." + proxyClassName;
  }

  public TypeElement getTypeElement() {
    return typeElement;
  }
}
