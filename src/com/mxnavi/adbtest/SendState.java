package com.mxnavi.adbtest;

public abstract class SendState {
	enum SendStateResult {
		CONTINUE,
		FAILED,
		COMPLETED
	}
	public abstract SendStateResult Handle(SendStateContext context);
}
