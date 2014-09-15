package com.mxnavi.adbtest;

import com.mxnavi.adbtest.SendState.SendStateResult;

public class SendStateContext {
	private SendState state;
	
	public SendStateContext(SendState state) {
		this.state = state;
	}

	public SendState getState() {
		return state;
	}

	public void setState(SendState state) {
		this.state = state;
	}
	
    public SendStateResult request() {
        return state.Handle(this);
    }
}
