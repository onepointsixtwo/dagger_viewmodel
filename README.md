# Dagger ViewModel


## Overview

Dagger ViewModel provides a seamless way of integrating the ViewModels from Android's architecture packages, with the depedency injection of Dagger2 without having to have Fragment subclasses which fetch their own ViewModels. It allows the declarative style of using an annotation for the injectable field so that the Fragment or Activity to which the ViewModel belongs can inject its viewmodel without having to override a lifecycle method to do so.

There are many places which suggest a 'half-and-half' approach when using Dagger2 by using dagger to inject the factory which knows how to build the ViewModel subclasses, and then writing code in a lifecycle method of the Fragment or Activity to set the ViewModel field. However, this library makes it as simple as using one annotation: the `@InjectViewModel` annotation.

### Recently updated!

Now supports AndroidX / Kotlin in V 2.0.0

## Quick start guide

There are a few parts that have to be put together to get up and running with Dagger ViewModel. These are gradle, the Dagger2 module and finally the annotations.


### Gradle

Dagger ViewModel can be added as a dependency to the project using:

```
    implementation 'com.onepointsixtwo:dagger_viewmodel:2.0.0'
    implementation 'com.onepointsixtwo:dagger_viewmodel_android:2.0.0'
    annotationProcessor 'com.onepointsixtwo:dagger_viewmodel_processor:2.0.0'
```

Older versions of gradle may use 'compile' in the place of 'implementation'. 

The first dependency is the annotation itself, the second is the Android classes required for setting up Dagger ViewModel integration, and the third is the annotation processor, to generate the Java code within your build directory from the annotations within your codebase.


### Dagger2 Module

To allow injecting ViewModel subclasses we need to create a module from which our ViewModels can be provided. The module should look something like this:

```
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyFragmentViewModel.class)
    abstract fun bindScoreFreeViewModel(MyFragmentViewModel viewModel): ViewModel;

    @Binds
    abstract fun bindFactory(ViewModelFactory factory): ViewModelProvider.Factory;
}
```

The last part of the module is a necessity: the module must provide the method to bind factory. This allows for the creation of a Dagger2-backed factory for creating the ViewModel subclasses complete with all their dependencies injected. Each of the ViewModels included should follow the same pattern as the one shown here; it should use the three annotations with the `@ViewModelKey` annotation using the class of the ViewModel subclass as the key.

This will inject the ViewModel subclasses with their dependencies as normal with Dagger2, so the ViewModel subclass could look something like this:

```
class MyFragmentViewModel @Inject constructor(): ViewModel {

    @Inject
    lateinit var requiredDependency: MyRequiredDependency
}
```


### @InjectViewModel annotations

The @InjectViewModel annotation itself can only be used within either an android.app.Fragment, an android.support.v4.app.Fragment, an Activity, or an ActivityCompat. It will inject the ViewModel into the given subclass, and has an optional argument for whether to use the Activity scope rather than the fragment's scope when injecting the ViewModel. Using its parent Activity may be useful if you want to be able to share a ViewModel between Fragments, as described in the documentation for the [Android lifecycle components](https://developer.android.com/topic/libraries/architecture/lifecycle.html).

Currently the injected ViewModel field _has to be public_ though this should hopefully get updated in a later version of the library so that it can be a package-local field instead.

```
@field:InjectViewModel(useActivityScope = true)
lateinit var viewModel: MyFragmentViewModel
```

### ViewModelInjectors

When your Android application is built, the annotation processor will generate a file called ViewModelInjectors and this can be used as follows:

```
fun injectFragment(Fragment fragment, ViewModelFactory factory) {
	ViewModelInejectors.inject(frag, viewModelFactory);
}
```

Where the ViewModel factory is injected directly by Dagger2. The power of this, is that it does not have to be called by the individual fragment or Activity being injected. It can be handled by a class which controls dependency injection in a single place within your application.


## License

This library is provided under the MIT license. Please see [here](https://github.com/onepointsixtwo/dagger_viewmodel/blob/master/LICENSE) for details.
