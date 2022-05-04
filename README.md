# KAFKA


## Install
- https://kafka.apache.org/downloads
- https://www.tutorialkart.com/apache-kafka/install-apache-kafka-on-mac/
```shell
vim ~/.bash_profile
# colocar no bash_profile:
alias "start_zookeeper= sh ~/kafka_2.13-3.1.0/bin/zookeeper-server-start.sh ~/kafka_2.13-3.1.0/config/zookeeper.properties" 
alias "start_kafka= sh ~/kafka_2.13-3.1.0/bin/kafka-server-start.sh ~/kafka_2.13-3.1.0/config/server.properties" 
source ~/.bash_profile
```
# Configurando as variáveis de ambiente
- Configurar o seu .bash_profile ou .bashrc
```shell
code ~/.bash_profile

export KAFKA_HOST="localhost:9092"
export KAFKA_TOPIC="NOME_TOPICO"

source ~/.bash_profile
```

# Rodar os comandos
```shell
./build.sh
./start.sh
```

# Enviando e Consumindo mensagens via terminal
```shell
# Lista os tópicos
~/kafka_2.13-3.1.0/bin/kafka-topics.sh --list --bootstrap-server=localhost:9092

# Cria um tópico
~/kafka_2.13-3.1.0/bin/kafka-topics.sh --create --bootstrap-server=localhost:9092 --replication-factor=1 --partitions=1 --topic="NOME_TOPICO"

# Envia mensagem
~/kafka_2.13-3.1.0/bin/kafka-console-producer.sh --broker-list=localhost:9092 --topic="NOME_TOPICO"

# Consome mensagens
~/kafka_2.13-3.1.0/bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic="NOME_TOPICO" 
~/kafka_2.13-3.1.0/bin/kafka-console-consumer.sh --bootstrap-server=localhost:9092 --topic="NOME_TOPICO" --from-beginning  #Lê as mensagens desde o início

# Deleta mensagem de um tópico
echo '{ "partitions": [ { "topic": "NOME_TOPICO", "partition": 0, "offset": 1 } ],  "version": 1 }' > delete-records.json
~/kafka_2.13-3.1.0/bin/kafka-delete-records.sh --bootstrap-server localhost:9092 --offset-json-file delete-records.json

# Deleta um tópico
~/kafka_2.13-3.1.0/bin/kafka-topics.sh --topic="EXEMPLO_TOPICO" --delete --bootstrap-server=1localhost:9092

```


# AWS SES

## Criando projeto
mvn archetype:generate -DgroupId=br.com.ses_sender -DartifactId=ses_sender -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

## Doc MVN
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

### Gerar com manifest
https://www.sohamkamani.com/java/cli-app-with-maven/

## Exemplo de Code Base
https://github.com/awsdocs/aws-doc-sdk-examples/tree/main/javav2

### MVN Options
- mvn validate: validate the project is correct and all necessary information is available
- mvn compile: compile the source code of the project
- mvn test: test the compiled source code using a suitable unit testing framework. These tests should not require the code be - packaged or deployed
- mvn package: take the compiled code and package it in its distributable format, such as a JAR.
- mvn integration-test: process and deploy the package if necessary into an environment where integration tests can be run
- mvn verify: run any checks to verify the package is valid and meets quality criteria
- mvn install: install the package into the local repository, for use as a dependency in other projects locally
- mvn deploy: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.

## Configurando as variáveis de ambiente
- configurar o .bash_profile ou .bashrc
```shell
code ~/.bash_profile

export AWS_ACCESS_KEY="SUA_ACCESS_KEY"
export AWS_SECRET_KEY="SUA_SECRET_KEY"

source ~/.bash_profile
```
