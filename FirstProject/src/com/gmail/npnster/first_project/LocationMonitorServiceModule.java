package com.gmail.npnster.first_project;

import javax.inject.Named;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import dagger.Module;
import dagger.Provides;

@Module(
		injects = { 
				LocationMonitorService.class,
		},
		library = true,
		addsTo =  ApplicationModule.class,
		complete = false
//		includes = {  ApplicationModule.class, AndroidModule.class }
		)
public class LocationMonitorServiceModule {
	
	private Context mContext;
	
	LocationMonitorServiceModule(Context context) {
		mContext = context;
		System.out.println("location monitor service module");
		
	}
	
	@Provides PendingIntent provideGcmKeepAlivePendingIntent() {
		System.out.println("pending intent provider");
		Intent gcmKeepAliveIntent = new Intent("com.gmail.npnster.first_project.gcmKeepAlive");
		return PendingIntent.getBroadcast(mContext, 0, gcmKeepAliveIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	}
	
	@Provides  AlarmManager provideGcmKeepAliveAlarmManager() {
		return (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
	}
	 
}
