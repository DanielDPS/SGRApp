package gcode.baseproject.domain.model.general;

import androidx.annotation.Nullable;

public class NetworkResponse<T>  {


    private Status status;

    @Nullable
    private Throwable error;

    @Nullable
    private String errorMessage;

    private T result;

    public static class Builder<T> {

        protected Status status = Status.LOADING;

        @Nullable
        protected Throwable error = null;

        protected String errorMessage  = null;

        @Nullable
        protected T result;


        public NetworkResponse build() {
            return new NetworkResponse(this);
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withError(Throwable error) {
            this.error = error;
            return this;
        }

        public Builder withErrorMessage(String message) {
            this.errorMessage = message;
            return this;
        }

        public Builder withResult(T result) {
            this.result = result;
            return this;
        }

    }

    private NetworkResponse(Builder<T> builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.result = builder.result;
        this.errorMessage = builder.errorMessage;
    }

    public Status status() {
        return status;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    @Nullable
    public Throwable error() {
        return error;
    }

    @Nullable
    public T getResult() {
        return result;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

}
