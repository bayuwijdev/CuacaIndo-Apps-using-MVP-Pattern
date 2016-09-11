package id.co.codelabs.weather.utility;

import android.support.test.espresso.IdlingResource;

/**
 * Created by bayu_wpp on 8/04/2016.
 */
public class EspressoIdlingResource {
    private static SimpleIdlingResource mIdlingResource = new SimpleIdlingResource();

    public static void increment() {
        mIdlingResource.increment();
    }

    public static void decrement() {
        mIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
