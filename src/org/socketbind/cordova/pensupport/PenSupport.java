package org.socketbind.cordova.pensupport;

import android.R;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LinearLayoutSoftKeyboardDetect;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PenSupport extends Plugin implements View.OnTouchListener {
    private static final String TAG = "PenSupport";
    private String callbackId = null;
    private CordovaWebView webView = null;
    private int pointerId = 0;

    @Override
    public PluginResult execute(String action, JSONArray args, String callbackId) {

        if (action.equals("start")) {
            if (this.callbackId != null) {
                return new PluginResult(PluginResult.Status.ERROR, "Pen support already started.");
            }
            this.callbackId = callbackId;

            webView = extractWebView();
            webView.setOnTouchListener(this);

            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            return pluginResult;
        } else if (action.equals("stop")) {
            this.callbackId = null;
            webView.setOnTouchListener(null);
            sendUpdate(new JSONObject(), false);
            return new PluginResult(PluginResult.Status.OK);
        }

        return new PluginResult(PluginResult.Status.INVALID_ACTION);
    }

    private void sendUpdate(JSONObject info, boolean keepCallback) {
        if (this.callbackId != null) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, info);
            result.setKeepCallback(keepCallback);
            this.success(result, this.callbackId);
        }
    }

    private CordovaWebView extractWebView() {
        FrameLayout frameLayout = (FrameLayout) cordova.getActivity().findViewById(R.id.content);
        LinearLayoutSoftKeyboardDetect linearLayout = (LinearLayoutSoftKeyboardDetect) frameLayout.getFocusedChild();
        CordovaWebView webView = (CordovaWebView) linearLayout.getFocusedChild();
        return webView;
    }

    private boolean isPenEvent(MotionEvent event) {
        return event.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isPenEvent(motionEvent)) {
            pointerId = motionEvent.getPointerId(0);
            int action = motionEvent.getAction();

            String actionStr;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    actionStr = "ACTION_DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionStr = "ACTION_MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    actionStr = "ACTION_UP";
                    break;
                default:
                    actionStr = "ACTION_UNKNOWN";
            }

            try {
                JSONObject object = new JSONObject();
                object.put("action", actionStr);
                object.put("x", motionEvent.getX(pointerId));
                object.put("y", motionEvent.getY(pointerId));

                sendUpdate(object, true);
            } catch (JSONException e) {
                Log.e(TAG, "JSON error in onTouch()", e);
            }
        }

        return webView.onTouchEvent(motionEvent);
    }
}

