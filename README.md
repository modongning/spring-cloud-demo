# spring-cloud-demo
spring cloud / spring cloud alibaba 的模板案例

详细搭建教程请参照：

- [[微服务] 一、搭建Eureka注册中心](http://morning.otoomo.com/article/41)
- [[微服务] 二、使用Ribbon实现负载均衡](http://morning.otoomo.com/article/42)
- [[微服务] 三、集成Hystrix 熔断器](http://morning.otoomo.com/article/43)
- [[微服务] 四、Hystrix Dashboard 断路监控仪表盘](http://morning.otoomo.com/article/44)
- [[微服务] 五、Hystrix Turbine 聚合监控](http://morning.otoomo.com/article/45)
- [[微服务] 六、集成Feign客户端](http://morning.otoomo.com/article/47)
- [[微服务] 七、集成配置中心](http://morning.otoomo.com/article/47)
- [[微服务] 八、链路追踪：Sluath + zipkin](http://morning.otoomo.com/article/49)
- [[微服务] 九、Spring Cloud OAuth2服务安全认证](http://morning.otoomo.com/article/50)


## 使用dockerfile-maven-plugin插件构建镜像及启动容器

### pom.xml添加插件

这里使用的是mac版本的Decker Desktop。

如果是其他的需要配置环境变量：
```shell
# tcp://{容器ip}:{容器访问端口}
export DOCKER_HOST=tcp://localhost:2375
```


```xml
<build>
<finalName>eureka_server</finalName>
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <!--跳过测试-->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <skipTests>true</skipTests>
        </configuration>
    </plugin>
    <!--
    dockerfile构建容器:
        mvn dockerfile:build / mvn package
        mvn dockerfile:push / mvn deploy
    -->
    <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>dockerfile-maven-plugin</artifactId>
        <version>1.4.13</version>
        <executions>
            <execution>
                <id>default</id>
                <goals>
                    <goal>build</goal>
                    <goal>push</goal>
                </goals>
            </execution>
        </executions>
        <!--image的信息
        f1c2a763-270a-4f22-8502-0b86c5019f25
        -->
        <configuration>
            <username>modongning</username>
            <password>123456</password>
            <!--镜像名称-->
            <repository>easycard/${project.build.finalName}</repository>
            <useMavenSettingsForAuth>true</useMavenSettingsForAuth>
            <tag>${project.version}</tag>
            <!--
                docker构建镜像过程中的参数，
                此处定义的JAR_FILE参数在执行docker build的时候会消费
            -->
            <buildArgs>
                <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
            </buildArgs>
        </configuration>
    </plugin>
</plugins>
</build>
```

### 编写Dockerfile

```text
FROM java:8
# 作者信息
MAINTAINER modongning<modongning@163.com>

#定义参数名称
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]
```

### 执行mvn打包

```shell
mvn package
```

### 查看docker镜像是否打包成功

```shell
docker images
```
看到有一个image为"easycard/eureka_server"的说明成功了

###  启动容器

```shell
docker run -d --name eureka-server \
  -p 8761:8761 \
  easycard/eureka_server:0.0.1-SNAPSHOT 
```

### 访问

http://127.0.0.1:8761

## 把镜像上传仓库
在上一个《使用dockerfile-maven-plugin插件构建镜像及启动容器》的基础上修改；

先去https://hub.docker.com中添加一个镜像仓库

这里设置 仓库名为：<font color='red'>modonging/eureka_server</font>

这个名字很关键，一定要记住；

### 修改pom.xml配置

修改pom的地方：`<repository>...r</repository>`

以下是全部配置：

```xml
<build>
<finalName>eureka_server</finalName>
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <!--跳过测试-->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <skipTests>true</skipTests>
        </configuration>
    </plugin>
    <!--
    dockerfile构建容器:
        mvn dockerfile:build / mvn package
        mvn dockerfile:push / mvn deploy
    -->
    <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>dockerfile-maven-plugin</artifactId>
        <version>1.4.13</version>
        <executions>
            <execution>
                <id>default</id>
                <goals>
                    <goal>build</goal>
                    <goal>push</goal>
                </goals>
            </execution>
        </executions>
        <!--image的信息
        f1c2a763-270a-4f22-8502-0b86c5019f25
        -->
        <configuration>
            <username>modongning</username>
            <password>123456</password>
            <!--
                这里改为docker.io的仓库名称
            -->
            <repository>modongning/eureka_server</repository>
            <useMavenSettingsForAuth>true</useMavenSettingsForAuth>
            <tag>${project.version}</tag>
            <!--
                docker构建镜像过程中的参数，
                此处定义的JAR_FILE参数在执行docker build的时候会消费
            -->
            <buildArgs>
                <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
            </buildArgs>
        </configuration>
    </plugin>
</plugins>
</build>
```

### 镜像上传仓库

```shell
mvn dockerfile:push
```

### docker.io仓库查看镜像

https://hub.docker.com
