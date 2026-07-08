package codesgesture.app.askhoardingv2.Services;

import org.json.JSONException;

public interface JsonCallbacks
{
   void onPostSuceess(String json, String method) throws JSONException;
   void onPostError(String msg);
}
