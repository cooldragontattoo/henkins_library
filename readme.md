# Henkins Library 

## About 📖 

The Henkins Library is to be used with the [Henkins](https://github.com/cooldragontattoo/henkins) Jenkins deployment.  It is a standard [Jenkins Shared Library](https://www.jenkins.io/doc/book/pipeline/shared-libraries/) and it connected to Henkins via JCASC Code that can be seen [here](https://github.com/cooldragontattoo/henkins/blob/fd6380e3d0cfa2fddd5554ddcbbf8da6900afcbd/values.yaml#L34-L47).  To include Library functions in your code, include it in your pipeline like [this](https://github.com/cooldragontattoo/henkins_jobs/blob/f9d976885d1e4ca65627bcfe82b2db17329cf633/examples/random_fruit.groovy#L1). 

## Library Functions 🧰

#### [kubeMetrics](./vars/kubeMetrics.groovy)

Sends the job status and how long the job took to Prometheus' PushGateway. 


#### [randomFruit](./vars/randomFruit.groovy)

Returns a random fruit.  This job was build for testing purproses.  