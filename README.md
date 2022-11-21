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

| Name                         | Description              | Unit | Type      |
| ---------------------------- | ------------------------ | ---- | --------- |
| grpc_call_total              | record grpc call totals  | N/A  | Counter   |
| grpc_duration_seconds_bucket | record grpc call latency | ms   | histogram |

