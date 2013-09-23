package net.mlorber.gwt.consolesample.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remoteService")
public interface SampleRemoteService extends RemoteService {

	String hey() throws SampleException;

	String boum() throws SampleRuntimeException;

	String houstonWeHaveAProblem();
}
