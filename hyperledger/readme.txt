Pre-requisites
> Docker
> Docker Compose
> Maven

Before proceeding, make sure Docker is running.


1. Setting up Blockchain Network
To build the blockchain network, the first step is to generate artifacts for peers and channels using cryptogen and configtx. All required artifacts for the peers and channel of the network are already generated and provided to use as-is. Artifacts can be located at:

network_resources/crypto-config
network_resources/config

# To stop the running network, run the following script.
cd network
chmod +x stop.sh
./stop.sh


# To delete the network completely, following script need to execute.
cd network
chmod +x teardown.sh
./teardown.sh


# To build the network, run the following script.
cd network
chmod +x build.sh
./build.sh

---------------------------------------------------------------------------------------
NOTE# You can skip Point 2. I've already generated the JAR file. Continue from Point 3.
---------------------------------------------------------------------------------------

2. Building client based on Fabric Java SDK
Open Terminal, navigate to java directory and run mvn install.

cd ../java
mvn install

cd target
cp blockchain-java-sdk-0.0.1-SNAPSHOT-jar-with-dependencies.jar blockchain-client.jar

cp blockchain-client.jar ../../network_resources


3. Create and Initialize the Channel
cd ../../network_resources
java -cp blockchain-client.jar org.example.network.CreateChannel


4. Deploy & Instantiate the chaincode (This might take some time)
java -cp blockchain-client.jar org.example.network.DeployInstantiateChaincode


5. Register & Enroll Users
java -cp blockchain-client.jar org.example.user.RegisterEnrollUser


6. Add Cron Job
Open cron job editor 
crontab -e 

Put the below line in editor (1 below means that your code will run every minute, this is just for testing. We would ideally have it for 10-15 mins)
*/1 * * * * java -cp (full path to your project directory)/hyperledger/network_resources/blockchain-client.jar org.example.chaincode.invocation.InvokeChaincode


7. Perform Invoke and Query on network
You don’t need to run this 1st command as cron job is already doing it for you (Run this if you haven’t setup cron job)
java -cp blockchain-client.jar org.example.chaincode.invocation.InvokeChaincode

You can check entries into hyperledger by using this command
java -cp blockchain-client.jar org.example.chaincode.invocation.QueryChaincode

8. Make sure you have new approved transactions processed in last 15 mins in order to see new entries being written in hyperledger

