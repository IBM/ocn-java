# taken from https://github.com/OpenLiberty/ci.docker
# this is already available on Docker Hub: sdaschner/open-liberty:javaee8-tracing-b1
FROM ibmjava:8-sfj-alpine

LABEL maintainer="Sebastian Daschner"

RUN apk add curl

COPY docker-server /opt/ol/docker/

# Install Open Liberty
ENV LIBERTY_VERSION=19.0.0.5 \
    LIBERTY_RELEASE=2019-05-22_2227 \
    INSTALL_DIR=/opt/ol/wlp \
    CONFIG_DIR=/opt/ol/wlp/usr/servers/defaultServer \
    DEPLOYMENT_DIR=/opt/ol/wlp/usr/servers/defaultServer/dropins \
    PATH=/opt/ol/wlp/bin:/opt/ol/docker/:$PATH \
    LOG_DIR=/logs \
    WLP_OUTPUT_DIR=/opt/ol/wlp/output \
    WLP_SKIP_MAXPERMSIZE=true

RUN curl https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/release/${LIBERTY_RELEASE}/openliberty-${LIBERTY_VERSION}.zip -L -o /tmp/wlp.zip \
 && unzip -q /tmp/wlp.zip -d /opt/ol \
 && rm /tmp/wlp.zip

RUN mkdir /logs \
    && mkdir -p $WLP_OUTPUT_DIR/defaultServer \
    && ln -s $WLP_OUTPUT_DIR/defaultServer /output \
    && ln -s $CONFIG_DIR /config \
    && ln -s /logs $WLP_OUTPUT_DIR/defaultServer/logs

RUN /opt/ol/wlp/bin/server create \
    && rm /config/server.env \
    && rm -rf $WLP_OUTPUT_DIR/.classCache /output/workarea \
    && mkdir /config/configDropins \
    && mkdir /config/configDropins/defaults \
    && echo "<server description=\"Default Server\"><httpEndpoint id=\"defaultHttpEndpoint\" host=\"*\" /></server>" > /config/configDropins/defaults/open-default-port.xml

ADD https://github.com/WASdev/sample.opentracing.zipkintracer/releases/download/1.3/liberty-opentracing-zipkintracer-1.3-sample.zip /
RUN unzip liberty-opentracing-zipkintracer-1.3-sample.zip -d $INSTALL_DIR/usr/

EXPOSE 9080 9443

ENTRYPOINT ["/opt/ol/docker/docker-server"]

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]