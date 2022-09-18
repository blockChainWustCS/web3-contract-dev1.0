package com.example.scoresystemv2.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.4.
 */
@SuppressWarnings("rawtypes")
public class CourseScoreService extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610c4f806100326000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80638da5cb5b1161005b5780638da5cb5b146103e8578063b446f3b2146103f0578063c3b5051a146104a6578063fa540801146105f657610088565b80630158b4b91461008d57806319045a25146100bb5780631b7d365c1461018257806379495824146102cf575b600080fd5b6100b9600480360360408110156100a357600080fd5b506001600160a01b038135169060200135610613565b005b610166600480360360408110156100d157600080fd5b81359190810190604081016020820135600160201b8111156100f257600080fd5b82018360208201111561010457600080fd5b803590602001918460018302840111600160201b8311171561012557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106c6945050505050565b604080516001600160a01b039092168252519081900360200190f35b6102bb6004803603606081101561019857600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156101c257600080fd5b8201836020820111156101d457600080fd5b803590602001918460018302840111600160201b831117156101f557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561024757600080fd5b82018360208201111561025957600080fd5b803590602001918460018302840111600160201b8311171561027a57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061074a945050505050565b604080519115158252519081900360200190f35b610373600480360360208110156102e557600080fd5b810190602081018135600160201b8111156102ff57600080fd5b82018360208201111561031157600080fd5b803590602001918460018302840111600160201b8311171561033257600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061079c945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103ad578181015183820152602001610395565b50505050905090810190601f1680156103da5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610166610963565b6104946004803603602081101561040657600080fd5b810190602081018135600160201b81111561042057600080fd5b82018360208201111561043257600080fd5b803590602001918460018302840111600160201b8311171561045357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610972945050505050565b60408051918252519081900360200190f35b6100b9600480360360c08110156104bc57600080fd5b6001600160a01b03823581169260208101359260ff60408301351692606083013516919081019060a081016080820135600160201b8111156104fd57600080fd5b82018360208201111561050f57600080fd5b803590602001918460018302840111600160201b8311171561053057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561058257600080fd5b82018360208201111561059457600080fd5b803590602001918460018302840111600160201b831117156105b557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506109e9945050505050565b6104946004803603602081101561060c57600080fd5b5035610b29565b6000546001600160a01b0316331461065c5760405162461bcd60e51b8152600401808060200182810382526026815260200180610bf46026913960400191505060405180910390fd5b60008181526002602090815260409182902080546001600160a01b0319166001600160a01b038616908117909155825190815290810183905281517f09eb531606720520b7aff3a9454e3ef365cf7ae0f2df2ec635b014a5aaa4ce29929181900390910190a15050565b6000806000806106d585610b7a565b92509250925060018682858560405160008152602001604052604051808581526020018460ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa158015610735573d6000803e3d6000fd5b5050604051601f190151979650505050505050565b600060606107578361079c565b9050600061076485610972565b9050600061077182610b29565b9050866001600160a01b031661078782856106c6565b6001600160a01b031614979650505050505050565b6060808290506060815167ffffffffffffffff811180156107bc57600080fd5b506040519080825280602002602001820160405280156107e6578160200160208202803683370190505b50905060005b825181101561088f57600083828151811061080357fe5b016020015160f81c905060608111156108425760618103600a0183838151811061082957fe5b602002602001019060ff16908160ff1681525050610886565b60428160ff1611156108615760418103600a0183838151811061082957fe5b6030810383838151811061087157fe5b602002602001019060ff16908160ff16815250505b506001016107ec565b5060606001600283518161089f57fe5b040367ffffffffffffffff811180156108b757600080fd5b506040519080825280601f01601f1916602001820160405280156108e2576020820181803683370190505b50905060025b825181101561095a5782816001018151811061090057fe5b602002602001015183828151811061091457fe5b60200260200101516010020160f81b8260028084038161093057fe5b048151811061093b57fe5b60200101906001600160f81b031916908160001a9053506002016108e8565b50949350505050565b6000546001600160a01b031681565b6000816040516020018082805190602001908083835b602083106109a75780518252601f199092019160209182019101610988565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051602081830303815290604052805190602001209050919050565b6109f483838361074a565b610a3a576040805162461bcd60e51b8152602060048201526012602482015271696e76616c6964207369676e61747572652160701b604482015290519081900360640190fd5b6000858152600260205260409020546001600160a01b03848116911614610aa8576040805162461bcd60e51b815260206004820152601c60248201527f796f752068617665206e6f20726967687420746f206f70657261746500000000604482015290519081900360640190fd5b6001600160a01b0380871660008181526001602090815260408083208a8452825291829020805460ff8a1660ff199091168117909155825193845290830189905282820152918516606082015290517ffcce5c8c75861f7903075f9ab2bfe5cbcece18845e9a79a3889f7c85928e89939181900360800190a1505050505050565b604080517f19457468657265756d205369676e6564204d6573736167653a0a333200000000602080830191909152603c8083019490945282518083039094018452605c909101909152815191012090565b60008060008351604114610bd5576040805162461bcd60e51b815260206004820152601860248201527f696e76616c6964207369676e6174757265206c656e6774680000000000000000604482015290519081900360640190fd5b50505060208101516040820151606090920151909260009190911a9056fe596f7520617265206e6f7420746865206f776e6572206f66207468697320636f6e7472616374a2646970667358221220a7fc6b8585dd5fdbea820879036c708b88bc78072dcfbe30106c78407d1253c064736f6c63430007010033";

    public static final String FUNC_GETETHSIGNEDMESSAGEHASH = "getEthSignedMessageHash";

    public static final String FUNC_GETMESSAGEHASH = "getMessageHash";

    public static final String FUNC_HEXSTR2BYTES = "hexStr2bytes";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RECOVER = "recover";

    public static final String FUNC_SETCOURSETEACHER = "setCourseTeacher";

    public static final String FUNC_SETSTUDENTTOCOURSE = "setStudentToCourse";

    public static final String FUNC_VERIFY = "verify";

    public static final Event NEWCOURESSCORE_EVENT = new Event("NewCouresScore",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event NEWCOURESTEACHER_EVENT = new Event("NewCouresTeacher",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected CourseScoreService(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CourseScoreService(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CourseScoreService(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CourseScoreService(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewCouresScoreEventResponse> getNewCouresScoreEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWCOURESSCORE_EVENT, transactionReceipt);
        ArrayList<NewCouresScoreEventResponse> responses = new ArrayList<NewCouresScoreEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewCouresScoreEventResponse typedResponse = new NewCouresScoreEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._courseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._score = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._teacherAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewCouresScoreEventResponse> newCouresScoreEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewCouresScoreEventResponse>() {
            @Override
            public NewCouresScoreEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWCOURESSCORE_EVENT, log);
                NewCouresScoreEventResponse typedResponse = new NewCouresScoreEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._courseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._score = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._teacherAddress = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewCouresScoreEventResponse> newCouresScoreEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCOURESSCORE_EVENT));
        return newCouresScoreEventFlowable(filter);
    }

    public List<NewCouresTeacherEventResponse> getNewCouresTeacherEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWCOURESTEACHER_EVENT, transactionReceipt);
        ArrayList<NewCouresTeacherEventResponse> responses = new ArrayList<NewCouresTeacherEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewCouresTeacherEventResponse typedResponse = new NewCouresTeacherEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._courseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewCouresTeacherEventResponse> newCouresTeacherEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewCouresTeacherEventResponse>() {
            @Override
            public NewCouresTeacherEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWCOURESTEACHER_EVENT, log);
                NewCouresTeacherEventResponse typedResponse = new NewCouresTeacherEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._courseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewCouresTeacherEventResponse> newCouresTeacherEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCOURESTEACHER_EVENT));
        return newCouresTeacherEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> getEthSignedMessageHash(byte[] _messageHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETETHSIGNEDMESSAGEHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_messageHash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> getMessageHash(String _message) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMESSAGEHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_message)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hexStr2bytes(String data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_HEXSTR2BYTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(data)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> recover(byte[] _ethSignegMessageHash, byte[] _sig) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RECOVER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_ethSignegMessageHash),
                        new org.web3j.abi.datatypes.DynamicBytes(_sig)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setCourseTeacher(String _address, BigInteger _courseId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCOURSETEACHER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address),
                        new org.web3j.abi.datatypes.generated.Uint256(_courseId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setStudentToCourse(String _address, BigInteger _courseId, BigInteger _score, String _teacherAddress, String _message, String _sig) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSTUDENTTOCOURSE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address),
                        new org.web3j.abi.datatypes.generated.Uint256(_courseId),
                        new org.web3j.abi.datatypes.generated.Uint8(_score),
                        new org.web3j.abi.datatypes.Address(160, _teacherAddress),
                        new org.web3j.abi.datatypes.Utf8String(_message),
                        new org.web3j.abi.datatypes.Utf8String(_sig)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verify(String _signer, String _message, String _sig) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _signer),
                        new org.web3j.abi.datatypes.Utf8String(_message),
                        new org.web3j.abi.datatypes.Utf8String(_sig)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static CourseScoreService load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CourseScoreService(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CourseScoreService load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CourseScoreService(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CourseScoreService load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CourseScoreService(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CourseScoreService load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CourseScoreService(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CourseScoreService> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CourseScoreService.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CourseScoreService> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CourseScoreService.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CourseScoreService> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CourseScoreService.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CourseScoreService> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CourseScoreService.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewCouresScoreEventResponse extends BaseEventResponse {
        public String _address;

        public BigInteger _courseId;

        public BigInteger _score;

        public String _teacherAddress;
    }

    public static class NewCouresTeacherEventResponse extends BaseEventResponse {
        public String _address;

        public BigInteger _courseId;
    }
}
