package com.ambulance.Model;

import java.util.List;

/**
 * Created by sumit on 25-Jan-18.
 */

public class FCMResponse {

    public long multiCastId;
    public int success;
    public int failure;
    public int canonicalIds;
    public List<Result> results;

    public FCMResponse() {
    }

    public FCMResponse(long multiCastId, int success, int failure, int canonicalIds, List<Result> results) {
        this.multiCastId = multiCastId;
        this.success = success;
        this.failure = failure;
        this.canonicalIds = canonicalIds;
        this.results = results;
    }

    public long getMultiCastId() {
        return multiCastId;
    }

    public void setMultiCastId(long multiCastId) {
        this.multiCastId = multiCastId;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(int canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
