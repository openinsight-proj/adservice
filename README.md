# NOTE: The overall helm chart 

https://github.com/openinsight-proj/openinsight-helm-charts


# adservice-springcloud

This repo re-implements opentelemetry-demo-webstore's adservice with nacos registry and sentinel.

## integrate list

- [x] nacos 
- [x] sentinel

## curl
```shell
grpcurl -plaintext -d '{"context_keys": ["binoculars","telescopes"]}' localhost:8080 hipstershop.AdService/GetAds
```

## metrics

adservice-springcloud will emit two metrics:

| Name                  | Description              | Unit | Type      |
| --------------------- | ------------------------ | ---- | --------- |
| grpc_call_total       | record grpc call totals  | N/A  | Counter   |
| grpc_duration_seconds | record grpc call latency | ms   | histogram |

## Integrate to Nacos and Sentinel
* Please notes the application.yml file:
  ```
  adservice/src/main/resources/application.yml
  ```
* Make sure your Dockerfile has: `JAVA_OPTS` env:
  ```
  $JAVA_OPTS -jar /bin/xxx.jar
  ```
* Update the `-D` parms as you want, example like below:
  ```
  # Export Sentinel Prometheus metrics
  -javaagent:./jmx_prometheus_javaagent-0.17.0.jar=12345:./prometheus-jmx-config.yaml \
  # Enable Nacos integration
  -Dspring.cloud.nacos.discovery.enabled=true \
  # Nacos configuration server address
  -Dspring.cloud.nacos.config.server-addr=nacos-test.skoala-test:8848 \
  # Nacos service name
  -Dspring.application.name=adservice-springcloud \
  # Nacos registry server address
  -Dspring.cloud.nacos.discovery.server-addr=nacos-test.skoala-test:8848 \
  # For DCE 5.0 need below Kubernetes metadata
  -Dspring.cloud.nacos.discovery.metadata.k8s_cluster_id=xxx \
  -Dspring.cloud.nacos.discovery.metadata.k8s_cluster_name=xxx \
  -Dspring.cloud.nacos.discovery.metadata.k8s_namespace_name=xxx \
  -Dspring.cloud.nacos.discovery.metadata.k8s_workload_type=deployment \
  -Dspring.cloud.nacos.discovery.metadata.k8s_workload_name=adservice-springcloud \
  -Dspring.cloud.nacos.discovery.metadata.k8s_service_name=adservice-springcloud \
  -Dspring.cloud.nacos.discovery.metadata.k8s_pod_name=${HOSTNAME} \
  # Enable Sentinel integration
  -Dspring.cloud.sentinel.enabled=true \
  # Sentinel dashboard address
  -Dspring.cloud.sentinel.transport.dashboard=nacos-test-sentinel.skoala-test:8080
  ```

