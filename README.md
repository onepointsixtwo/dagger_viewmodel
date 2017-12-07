# Dagger ViewModel

The Dagger ViewModel library is designed to work with Dagger2 (and I feel I should state that I have no affiation whatsoever with the writers of the wonderful Dagger2 library and that I have simply named the library as this because it's an adequate description of what it _does_) to allow dependency injection of the relatively new Android architecture component of the ViewModel subclass.

The ViewModel subclass cannot be dependency injected directly with Dagger2 in the usual way, because it requires an input only known at runtime - the specific instance of a Fragment or Activity it is tied to. It is quite possible to use Dagger2 to inject the viewmodel by just using a small part of this library - the parts in [dagger_viewmodel_android](https://github.com/onepointsixtwo/dagger_viewmodel_android) which is taken from some example code of Google's and provides a custom factory to provide injected ViewModels. However, it means leaking the factory out into the Activity and Fragment subclasses which strikes me as an ugly way to do it.

This library allows you to inject ViewModels with as little overhead as possible. There are only two parts to it. This description assumes you have prior knowledge of working with Dagger2.

The code is generated at compile time by the annotation processor, the code for which is [here](https://github.com/onepointsixtwo/dagger_viewmodel_processor).


## The Annotation

The annotation provided by this library follows a similar style to Dagger2's standard dependency injection but allows for an optional argument as follows:
```
@InjectViewModel(useActivityScope = true)
public MyFragmentViewModel viewModel;
```

The optional argument describes whether a fragment should use its own lifecycle when requesting an instance of ViewModel or its parent activity. Using its parent Activity can be useful if you want to be able to share a ViewModel between Fragments, as described in the documentation for the [Android lifecycle components](https://developer.android.com/topic/libraries/architecture/lifecycle.html). 


## The Module

We also need to set up a custom module to allow the depedency injection of ViewModels. The module follows a pretty similar pattern to the standard Dagger2 dependency injection library but uses a couple of classes which are largely taken from Google's example code, and provided in the Android part of this library. The module once set up should look roughly as follows:

```
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyFragmentViewModel.class)
    abstract ViewModel bindScoreFreeViewModel(MyFragmentViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindFactory(ViewModelFactory factory);
}
```

This allows for the setup of the custom factory to prodice the view models, and combined with the annotation, can allow a viewmodel to be injected into a fragment or an activity along with all its dependencies from the rest of your Dagger2 modules. 

The ViewModel can use the standard injection model as follows:

```
public class MyFragmentViewModel extends ViewModel {

    @Inject
    MyRequiredDependency requiredDependency;

    @Inject
    public MyFragmentViewModel() {

    }

}
```

## Using this library

At the time of writing, this library can only be used by cloning or downloading the separate components and adding them as modules within your Android gradle project. However, it should soon be available in the central maven repository, and this README will be updated at that time.
