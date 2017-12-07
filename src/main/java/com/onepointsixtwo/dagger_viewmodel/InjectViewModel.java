package com.onepointsixtwo.dagger_viewmodel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectViewModel {
    boolean useActivityScope() default false;
}
