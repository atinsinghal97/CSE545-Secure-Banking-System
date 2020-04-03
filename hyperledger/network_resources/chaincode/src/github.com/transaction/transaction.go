/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * The sample smart contract for documentation topic:
 * Writing Your First Blockchain Application
 */

package main

/* Imports
 * 4 utility libraries for formatting, handling bytes, reading and writing JSON, and string manipulation
 * 2 specific Hyperledger Fabric specific libraries for Smart Contracts
 */
import (
	"bytes"
	"encoding/json"
	"fmt"
	"strconv"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

// Define the Smart Contract structure
type SmartContract struct {
}

// Define the Transaction structure, with 4 properties.  Structure tags are used by encoding/json library
type Transaction struct {
	DecisionDate  string `json:"decisiondate"`
	FromAccount string `json:"fromaccount"`
	ToAccount  string `json:"toaccount"`
	Amount   string `json:"amount"`
	TransactionType string `json:"transactiontype"`
}

/*
 * The Init method is called when the Smart Contract "fabTransaction" is instantiated by the blockchain network
 * Best practice is to have any Ledger initialization in separate function -- see initLedger()
 */
func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
	return shim.Success(nil)
}

/*
 * The Invoke method is called as a result of an application request to run the Smart Contract "fabTransaction"
 * The calling application program has also specified the particular smart contract function to be called, with arguments
 */
func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

	// Retrieve the requested Smart Contract function and arguments
	function, args := APIstub.GetFunctionAndParameters()
	// Route to the appropriate handler function to interact with the ledger appropriately
	if function == "queryTransaction" {
		return s.queryTransaction(APIstub, args)
	} else if function == "initLedger" {
		return s.initLedger(APIstub)
	} else if function == "createTransaction" {
		return s.createTransaction(APIstub, args)
	} else if function == "queryAllTransactions" {
		return s.queryAllTransactions(APIstub)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

func (s *SmartContract) queryTransaction(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	if len(args) != 1 {
		return shim.Error("Incorrect number of arguments. Expecting 1")
	}

	TransactionAsBytes, _ := APIstub.GetState(args[0])
	return shim.Success(TransactionAsBytes)
}

func (s *SmartContract) initLedger(APIstub shim.ChaincodeStubInterface) sc.Response {
	Transactions := []Transaction{
		Transaction{DecisionDate: "2020-03-25 14:33:22", FromAccount: "4539309266608", ToAccount: "5379148778000788", Amount: "100", TransactionType: "transfer"},
		Transaction{DecisionDate: "2020-03-26 14:33:22", FromAccount: "4539309266609", ToAccount: "5379148778000789", Amount: "200", TransactionType: "transfer"},
		Transaction{DecisionDate: "2020-03-27 14:33:22", FromAccount: "4539309266607", ToAccount: "5379148778000787", Amount: "300", TransactionType: "transfer"},
	}

	i := 0
	for i < len(Transactions) {
		fmt.Println("i is ", i)
		TransactionAsBytes, _ := json.Marshal(Transactions[i])
		APIstub.PutState(strconv.Itoa(i), TransactionAsBytes)
		fmt.Println("Added", Transactions[i])
		i = i + 1
	}

	return shim.Success(nil)
}

func (s *SmartContract) createTransaction(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

	if len(args) != 6 {
		return shim.Error("Incorrect number of arguments. Expecting 6")
	}

	var Transaction = Transaction{DecisionDate: args[1], FromAccount: args[2], ToAccount: args[3], Amount: args[4],  TransactionType: args[5]}

	TransactionAsBytes, _ := json.Marshal(Transaction)
	APIstub.PutState(args[0], TransactionAsBytes)

	return shim.Success(nil)
}

func (s *SmartContract) queryAllTransactions(APIstub shim.ChaincodeStubInterface) sc.Response {

	startKey := "1"
	endKey := "999"

	resultsIterator, err := APIstub.GetStateByRange(startKey, endKey)
	if err != nil {
		return shim.Error(err.Error())
	}
	defer resultsIterator.Close()

	// buffer is a JSON array containing QueryResults
	var buffer bytes.Buffer
	buffer.WriteString("[")

	bArrayMemberAlreadyWritten := false
	for resultsIterator.HasNext() {
		queryResponse, err := resultsIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		// Add a comma before array members, suppress it for the first array member
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString("{\"Key\":")
		buffer.WriteString("\"")
		buffer.WriteString(queryResponse.Key)
		buffer.WriteString("\"")

		buffer.WriteString(", \"Record\":")
		// Record is a JSON object, so we write as-is
		buffer.WriteString(string(queryResponse.Value))
		buffer.WriteString("}")
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString("]")

	fmt.Printf("- queryAllTransactions:\n%s\n", buffer.String())

	return shim.Success(buffer.Bytes())
}

// The main function is only relevant in unit test mode. Only included here for completeness.
func main() {

	// Create a new Smart Contract
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error creating new Smart Contract: %s", err)
	}
}
