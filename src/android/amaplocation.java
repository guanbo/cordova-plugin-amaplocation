package com.extensivepro.amaplocation;

import android.location.Location;
import android.os.Bundle;
import android.content.Context;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class amaplocation extends CordovaPlugin implements AMapLocationListener {
    
    private LocationManagerProxy mLocationManagerProxy;
    private CallbackContext mCallbackContext;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCurrentPosition")) {
            this.getCurrentPosition(callbackContext);
            return true;
        }
        return false;
    }

    private void getCurrentPosition(CallbackContext callbackContext) {
        if (null == mLocationManagerProxy) {
            Context context=this.cordova.getActivity().getApplicationContext(); 
            mLocationManagerProxy = LocationManagerProxy.getInstance(context);
        }
        
        mCallbackContext = callbackContext;
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, this);
    }
    
    /**
    * 定位成功后回调函数
    */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
                Double geoLat = amapLocation.getLatitude();
                Double geoLng = amapLocation.getLongitude();
                JSONObject jsonObject = new JSONObject();  
                try {
                    jsonObject.put("lat", geoLat);  
                    jsonObject.put("lng", geoLng);  
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mCallbackContext.success(jsonObject);
            } else {
                mCallbackContext.error("Location error: "+amapLocation.getAMapException().getErrorMessage());
            }
        } else {
            mCallbackContext.error("Location got null");
        }
    }
    
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
