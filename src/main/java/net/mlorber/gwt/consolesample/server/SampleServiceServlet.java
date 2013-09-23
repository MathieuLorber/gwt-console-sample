package net.mlorber.gwt.consolesample.server;

import net.mlorber.gwt.consolesample.shared.SampleException;
import net.mlorber.gwt.consolesample.shared.SampleRemoteService;
import net.mlorber.gwt.consolesample.shared.SampleRuntimeException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SampleServiceServlet extends RemoteServiceServlet implements SampleRemoteService {

	@Override
	public String hey() throws SampleException {
		throw new SampleException("SampleException message");
	}

	@Override
	public String boum() {
		throw new SampleRuntimeException("SampleRuntimeException message");
	}

	@Override
	public String houstonWeHaveAProblem() {
		throw new RuntimeException("This is the day the whole world went away");
	}

}
