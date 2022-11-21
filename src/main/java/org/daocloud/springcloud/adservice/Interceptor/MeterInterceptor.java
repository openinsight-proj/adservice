package org.daocloud.springcloud.adservice.Interceptor;
import io.grpc.*;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.daocloud.springcloud.adservice.meter.Meter;
import org.springframework.beans.factory.annotation.Autowired;

public class MeterInterceptor implements ServerInterceptor {
    @Autowired
    private Meter meterProvider;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        long start = System.currentTimeMillis();
        Attributes attributes = Attributes.of(AttributeKey.stringKey("operation"), serverCall.getMethodDescriptor().getFullMethodName());
        meterProvider.getGrpcCalls().add(1,attributes);
        return serverCallHandler.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
                    @Override
                    public void sendMessage(RespT message) {
                        long finish = System.currentTimeMillis();
                        long timeElapsed = finish - start;
                        meterProvider.getGrpcLagency().record(timeElapsed,attributes);
                        super.sendMessage(message);
                    }
                },
                metadata);
    }
}
