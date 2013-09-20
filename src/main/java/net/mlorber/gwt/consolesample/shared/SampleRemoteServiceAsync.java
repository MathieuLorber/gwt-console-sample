package net.mlorber.gwt.consolesample.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleRemoteServiceAsync {

   void hey(AsyncCallback<String> callback);

   void boum(AsyncCallback<String> callback);

   void houstonWeHaveAProblem(AsyncCallback<String> callback);

}
