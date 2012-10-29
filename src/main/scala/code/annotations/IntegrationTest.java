package code.annotations;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 11:47 PM
 * Provides...
 */
public @interface IntegrationTest {
    public String value() default "requires running db";
}
