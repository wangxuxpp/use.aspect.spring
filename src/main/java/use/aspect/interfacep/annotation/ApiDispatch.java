package use.aspect.interfacep.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDispatch {
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
