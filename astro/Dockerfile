FROM quay.io/astronomer/astro-runtime:11.5.0

RUN pip install minio
RUN pip install apache-airflow-providers-apache-spark
RUN pip install delta-spark==2.3.0

# # Install Java
# USER root
# RUN apt-get update && apt-get install -y openjdk-11-jdk && apt-get clean;

# # Set JAVA_HOME environment variable
# ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
# ENV PATH=$JAVA_HOME/bin:$PATH