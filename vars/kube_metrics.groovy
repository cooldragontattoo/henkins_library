def call() {
        def jobName = env.JOB_NAME.replaceAll("/", "_")
        def buildId = env.BUILD_ID
        def rawDuration = currentBuild.duration ?: 0
        def durationSecs = (rawDuration)  
        def statusMap = ["SUCCESS": 1, "FAILURE": 0, "ABORTED": 2, "UNSTABLE": 3, "NOT_BUILT": 4]
        def statusValue = statusMap.get(currentBuild.currentResult ?: "FAILURE", 0) // default to failure

        if (!(durationSecs instanceof Number)) {
            error "Invalid duration: ${durationSecs}"
        }

        def metrics = []
        metrics << """jenkins_job_duration_seconds{job="${jobName}",build="${buildId}"} ${durationSecs}"""
        metrics << """jenkins_job_status{job="${jobName}",build="${buildId}"} ${statusValue}"""

        // Directly push metrics to PushGateway
        def metricData = metrics.join("\n") + "\n"
        echo "Pushing metrics: ${metricData}"

        // Directly push to PushGateway using curl and returnStdout
        def curlCmd = """echo '${metricData}' | curl --data-binary @- http://prafana-prometheus-pushgateway.prometheus.svc.cluster.local:9091/metrics/job/${jobName}/build/${buildId}"""
        sh(script: curlCmd, returnStdout: true)
}