package com.newfivefour.natcher.networking;

public class ServerOrCachedResponse {
    private boolean mIsCached;

    public boolean isIsCached() {
        return mIsCached;
    }

    public void setFromCache(boolean b) {
        mIsCached = b;
    }
}
