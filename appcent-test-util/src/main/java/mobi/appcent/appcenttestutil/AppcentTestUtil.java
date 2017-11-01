package mobi.appcent.appcenttestutil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MohammadARAFA on 31/10/2017.
 */

public class AppcentTestUtil {
    private  static AppcentTestUtil instance;
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private Application application;
    private  Activity topActivity;

    public static AppcentTestUtil getInstance(Application application,String versionNumber, String prodBaseUrl, String testBaseUrl, AppcentCallback appcentcallback) {
        if (instance == null){
            instance = new AppcentTestUtil(application, versionNumber, prodBaseUrl, testBaseUrl, appcentcallback);
        }
        return instance;
    }

    private AppcentTestUtil(Application application, String versionNumber, String prodBaseUrl, String testBaseUrl, AppcentCallback appcentcallback){
        if (application == null){
            throw new IllegalArgumentException("valid application is required");
        }
        if (!isValidURL(prodBaseUrl)){
            throw new IllegalArgumentException("valid prodBaseUrl is required");
        }
        if (!isValidURL(testBaseUrl)){
            throw new IllegalArgumentException("valid testBaseUrl is required");
        }
        if (appcentcallback == null){
            throw new IllegalArgumentException("valid appcentcallback is required");
        }

        this.application = application;
        application.registerActivityLifecycleCallbacks(new AppcentTestUtilActivityLifecycleCallbacks());
        Constants.PROD_URL = prodBaseUrl;
        Constants.TEST_URL = testBaseUrl;
        Constants.BASE_URL = testBaseUrl;
        Constants.appcentCallback = appcentcallback;
        Constants.VERSION_NUMBER = versionNumber;
        onShake();
    }

    private void onShake(){
        final boolean[] flag = {true};
        mSensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        final CountDownTimer timer = new CountDownTimer(5000,5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                flag[0] = true;
            }
        };
        //LocalBroadcastManager.getInstance(this).registerReceiver(connectionErrorReceiver,new IntentFilter("connection-error"));

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                if (flag[0]){
                    flag[0] = false;
                    timer.start();
                    if (!topActivity.isFinishing()){
                        showUrlPopup();
                    }
                }
            }
        });
    }

    public void setTopActivity(Activity activity){
        topActivity = activity;
    }

    private boolean isValidURL(final String urlStr) {
        boolean validURL = false;
        if (urlStr != null && urlStr.length() > 0) {
            try {
                new URL(urlStr);
                validURL = true;
            }
            catch (MalformedURLException e) {
                validURL = false;
            }
        }
        return validURL;
    }
    private void showUrlPopup(){
        AppcentDevelopmentPopup popup = new AppcentDevelopmentPopup();
        popup.showAppcentDevelopmentPopup(topActivity);
    }

    private final class AppcentTestUtilActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }
        @Override
        public void onActivityStarted(Activity activity) {
        }
        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }
        @Override
        public void onActivityPaused(Activity activity) {

        }
        @Override
        public void onActivityStopped(Activity activity) {
        }
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }
        @Override
        public void onActivityDestroyed(Activity activity) {
            
        }
    }
}
