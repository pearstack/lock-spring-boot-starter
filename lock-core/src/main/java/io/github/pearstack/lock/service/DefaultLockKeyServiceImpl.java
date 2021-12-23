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

/**
 * 默认分布式锁key生成方法
 *
 * @author lihao3
 */
@Service
public class DefaultLockKeyServiceImpl implements GetLockKeyService {

  /** 参数名称解析器 */
  private static final ParameterNameDiscoverer NAME_DISCOVERER =
      new DefaultParameterNameDiscoverer();
  /** 表达式解析器 */
  private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

  @Override
  public String getKey(JoinPoint joinPoint, String name, String[] keys, String separator) {
    ExpressionRootObject root =
        new ExpressionRootObject(joinPoint.getTarget(), joinPoint.getArgs());
    EvaluationContext context =
        new MethodBasedEvaluationContext(
            root,
            ((MethodSignature) joinPoint.getSignature()).getMethod(),
            joinPoint.getArgs(),
            NAME_DISCOVERER);

    if (ObjectUtil.isEmpty(name)) {
      name =
          StrUtil.format(
              "{}.{}()",
              joinPoint.getSignature().getDeclaringTypeName(),
              joinPoint.getSignature().getName());
    }
    if (ObjectUtil.isNotEmpty(keys)) {
      StringBuilder nameBuilder = new StringBuilder(name);
      for (String key : keys) {
        if (ObjectUtil.isNotEmpty(key)) {
          nameBuilder
              .append(separator)
              .append(EXPRESSION_PARSER.parseExpression(key).getValue(context, String.class));
        }
      }
      name = nameBuilder.toString();
    }
    return name;
  }
}
