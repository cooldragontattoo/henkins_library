// sends the job status and how long the job took to Prometheus' PushGateway
// NOTE: the PushGateway service must be: http://prafana-prometheus-pushgateway.prometheus.svc.cluster.local:9091

def call() {

        // define variables
        def jobName = env.JOB_NAME.replaceAll("/", "_")
        def buildId = env.BUILD_ID
        def rawDuration = currentBuild.duration ?: 0
        def durationSecs = (rawDuration)  
        def statusMap = ["SUCCESS": 1, "FAILURE": 0, "ABORTED": 2, "UNSTABLE": 3, "NOT_BUILT": 4] // types of Jenkins job statuses
        def statusValue = statusMap.get(currentBuild.currentResult ?: "FAILURE", 0) // default to failure

        // validate duration
        if (!(durationSecs instanceof Number)) {
            error "Invalid duration: ${durationSecs}"
        }

        // build metrics to send to PushGateway
        def metrics = []
        metrics << """jenkins_job_duration_seconds{job="${jobName}",build="${buildId}"} ${durationSecs}"""
        metrics << """jenkins_job_status{job="${jobName}",build="${buildId}"} ${statusValue}"""

        // join metrics into a single string
        def metricData = metrics.join("\n") + "\n" // must include new line for PushGateway to accept metrics data

        // push to PushGateway using curl and returnStdout
        // NOTE: due to this function being called in a post stage, and since
        // we do not have a node to run curl on, we must use sh to run curl
        def curlCmd = """echo '${metricData}' | curl --data-binary @- http://prafana-prometheus-pushgateway.prometheus.svc.cluster.local:9091/metrics/job/${jobName}/build/${buildId}"""
        sh(script: curlCmd, returnStdout: true)
}