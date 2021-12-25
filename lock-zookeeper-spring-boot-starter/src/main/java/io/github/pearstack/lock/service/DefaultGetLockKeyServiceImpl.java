package io.github.pearstack.lock.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultGetLockKeyServiceImpl implements GetLockKeyService {

  /** 参数名称解析器 */
  private static final ParameterNameDiscoverer NAME_DISCOVERER =
      new DefaultParameterNameDiscoverer();
  /** 表达式解析器 */
  private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

  @Override
  public String getKey(JoinPoint joinPoint, String name, String[] keys) {
    ExpressionRootObject root =
        new ExpressionRootObject(joinPoint.getTarget(), joinPoint.getArgs());
    EvaluationContext context =
        new MethodBasedEvaluationContext(
            root,
            ((MethodSignature) joinPoint.getSignature()).getMethod(),
            joinPoint.getArgs(),
            NAME_DISCOVERER);

    // 判断用户是否自定义name
    // 如果没有则使用默认的全路径加方法名
    if (ObjectUtil.isEmpty(name)) {
      name =
          "/"
              + StrUtil.format(
                  "{}.{}()",
                  joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName());
    }
    // 判断用户是否自定义keys
    // 如果有值的话追加到name上面
    if (ObjectUtil.isNotEmpty(keys)) {
      List<String> keyList = new ArrayList<>();
      for (String key : keys) {
        keyList.add(EXPRESSION_PARSER.parseExpression(key).getValue(context, String.class));
      }
      name = name + "/" + String.join(".", keyList);
    }
    return name;
  }
}
