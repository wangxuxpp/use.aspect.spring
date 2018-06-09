package use.aspect.interfacep.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 项目名称:use.aspect.spring
 * 类型名称:ReturnJson
 * 类型描述:返回json包装对象
 * 作者:wx
 * 创建时间:2018年6月9日
 * @version:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnJson {

	/**
	 * 错误信息
	 * @return
	 */
	public String errorMessage() default "错误";
	/**
	 * 成功信息
	 * @return
	 */
	public String successMessage() default "";
	/**
	 * json key
	 * @return
	 */
	public String mapKey() default "result";
}
