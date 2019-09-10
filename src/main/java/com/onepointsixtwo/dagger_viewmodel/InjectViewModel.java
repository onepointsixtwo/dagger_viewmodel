package com.onepointsixtwo.dagger_viewmodel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used in conjunction with the annotation preprocessor in order to support injection of
 * androidx.arch.ViewModel objects
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectViewModel {
    /**
     * Describes whether to use the parent activity as the scope for the ViewModel, or the Fragment itself.
     * @return true if should use parent scope otherwise false
     */
    boolean useActivityScope() default false;
}
