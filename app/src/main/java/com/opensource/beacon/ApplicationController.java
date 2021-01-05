package com.opensource.beacon;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.opensource.beacon.activity.MainActivity;
import com.opensource.beacon.location.RegionInteractor;
import com.opensource.beacon.notification.NotificationInteractor;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.altbeacon.bluetooth.BluetoothMedic;


public class ApplicationController extends Application implements BootstrapNotifier, BeaconDistanceListener {
    private static final String TAG = ApplicationController.class.getSimpleName();

    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    private MainActivity mainActivity = null;
    private String cumulativeLog = "";
    private float mDistance;


    private static ApplicationController mInstance;

    public static synchronized ApplicationController getInstance() {
        return mInstance;
    }

    public void onCreate() {

        super.onCreate();

        mInstance = this;

        BluetoothMedic medic = BluetoothMedic.getInstance();
        medic.enablePowerCycleOnFailures(ApplicationController.getInstance());
        medic.enablePeriodicTests(ApplicationController.getInstance(), BluetoothMedic.SCAN_TEST | BluetoothMedic.TRANSMIT_TEST);


        SharedPreferenceManager.init(mInstance);


        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb.  To find the proper
        // layout expression for other beacon types, do a web search for "setBeaconLayout"
        // including the quotes.
        //
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.setDebug(true);
        beaconManager.setRegionExitPeriod(2000);


        // Uncomment the code below to use a foreground service to scan for beacons. This unlocks
        // the ability to continually scan for long periods of time in the background on Andorid 8+
        // in exchange for showing an icon at the top of the screen and a always-on notification to
        // communicate to users that your app is using resources in the background.
        //

        /*Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Scanning for Beacons");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
                    "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My Notification Channel Description");
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }
        beaconManager.enableForegroundServiceScanning(builder.build(), 456);


        // For the above foreground scanning service to be useful, you need to disable
        // JobScheduler-based scans (used on Android 8+) and set a fast background scan
        // cycle that would otherwise be disallowed by the operating system.
        //

        beaconManager.setEnableScheduledScanJobs(false);
        beaconManager.setBackgroundBetweenScanPeriod(0);
        beaconManager.setBackgroundScanPeriod(1100);*/

        beaconManager.setBackgroundMode(true);

        beaconManager.setBackgroundBetweenScanPeriod(3600000l);
        beaconManager.setForegroundBetweenScanPeriod(0);
        beaconManager.setBackgroundScanPeriod(1000l);
        beaconManager.setForegroundScanPeriod(11000l);


        Log.d(TAG, "setting up background monitoring for beacons and power saving");
        // wake up the app when a beacon is seen
//        Region region = new Region("backgroundRegion", Identifier.parse(Constants.BEACON_UUID), Identifier.parse(Constants.BEACON_MAJOR), Identifier.parse(Constants.BEACON_MINOR));
//        regionBootstrap = new RegionBootstrap(this, region);

        // simply constructing this class and holding a reference to it in your custom Application
        // class will automatically cause the BeaconLibrary to save battery whenever the application
        // is not visible.  This reduces bluetooth power usage by about 60%

        backgroundPowerSaver = new BackgroundPowerSaver(this);

        // If you wish to test beacon detection in the Android Emulator, you can use code like this:
        // BeaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
        // ((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
    }

    public void disableMonitoring() {
        if (regionBootstrap != null) {
            regionBootstrap.disable();
            regionBootstrap = null;
        }
    }


    public void enableMonitoring() {
        Region region = new Region("backgroundRegion", Identifier.parse(Constants.BEACON_UUID), Identifier.parse(Constants.BEACON_MAJOR), Identifier.parse(Constants.BEACON_MINOR));
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void didExitRegion(Region region) {
        Toast.makeText(mInstance, "Out of Range", Toast.LENGTH_SHORT).show();
        logToDisplay("I no longer see a beacon.");
        callAirshipRegionAPI();

//        NotificationInteractor notificationInteractor = new NotificationInteractor();
//        notificationInteractor.sendPush("Exit");
    }

    @Override
    public void didEnterRegion(Region arg0) {
        Toast.makeText(mInstance, "Beacon Matched", Toast.LENGTH_SHORT).show();
        logToDisplay("I see a beacon.");
        callAirshipRegionAPI();

        NotificationInteractor notificationInteractor = new NotificationInteractor();
        notificationInteractor.sendPush("Enter");

//        ScheduleNotificationInteractor scheduleNotificationInteractor = new ScheduleNotificationInteractor();
//        scheduleNotificationInteractor.sendPush("Enter");


        /*// In this example, this class sends a notification to the user whenever a Beacon
        // matching a Region (defined above) are first seen.

        if (!haveDetectedBeaconsSinceBoot)
        {
            Log.d(TAG, "auto launching MainActivity");

            // The very first time since boot that we detect an beacon, we launch the
            // MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Important:  make sure to add android:launchMode="singleInstance" in the manifest
            // to keep multiple copies of this activity from getting created if the user has
            // already manually launched the app.
            this.startActivity(intent);
            haveDetectedBeaconsSinceBoot = true;
        } else {
            if (mainActivity != null) {
                // If the Monitoring Activity is visible, we log info about the beacons we have
                // seen on its display
                logToDisplay("I see a beacon again" );
            } else {
                // If we have already seen beacons before, but the monitoring activity is not in
                // the foreground, we send a notification to the user on subsequent detections.
                Log.d(TAG, "Sending notification.");
                sendNotification();
            }
        }*/
    }

    private void callAirshipRegionAPI() {
        RegionInteractor regionInteractor = new RegionInteractor();
        regionInteractor.callRegionApi(this);
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        logToDisplay("Current region state is: " + (state == 1 ? "INSIDE" : "OUTSIDE (" + state + ")"));
    }

    /*private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Beacon Reference Application")
                        .setContentText("An beacon is nearby.")
                        .setSmallIcon(R.drawable.ic_launcher_foreground);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, MainActivity.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }*/

    public void setMonitoringActivity(MainActivity activity) {
        this.mainActivity = activity;
    }

    private void logToDisplay(String line) {
        Log.i(TAG, "_log logToDisplay : " + line);
        cumulativeLog += (line + "\n");
        if (this.mainActivity != null) {
            this.mainActivity.updateLog(cumulativeLog);
        }
    }

    public String getLog() {
        return cumulativeLog;
    }

    @Override
    public void beaconDistance(float distance) {
        Log.i(TAG, "_log beaconDistance : " + distance);
        this.mDistance = distance;
    }
}
