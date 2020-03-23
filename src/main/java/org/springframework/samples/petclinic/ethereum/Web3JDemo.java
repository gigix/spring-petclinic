package org.springframework.samples.petclinic.ethereum;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;

import static java.lang.String.valueOf;
import static java.math.BigInteger.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class Web3JDemo {
    private static Web3jService service = new HttpService("http://localhost:8545");
    private static Web3j web3j = Web3j.build(service);

    public static void main(String[] args) throws Exception {
        System.out.println("Now start:");
        Request<?, EthAccounts> ethAccountsRequest = web3j.ethAccounts();
        EthAccounts ethAccounts = ethAccountsRequest.send();
        System.out.println(ethAccounts.getAccounts().size());

        Function function = new Function("Owners",
            asList(
                new Int(new BigInteger(valueOf(123))),
                new Utf8String("Jeff Xiong"),
                new Utf8String("Tengfei Tower"),
                new Utf8String("Shanghai"),
                new Utf8String("88889999")),
            emptyList());
        Credentials creds = Credentials.create("0xb9fe995fe971e64c7b5a9e2b75a880db8266d6e63829a608632a5de72aed18fb");

        String txData = FunctionEncoder.encode(function);
        TransactionManager txManager = new FastRawTransactionManager(web3j, creds);
        String txAddress = "0x9dA884cB472b234f4EA053189C8d6588bA4C9d23";
        String txHash = txManager.sendTransaction(GAS_PRICE, GAS_LIMIT, txAddress, txData, ZERO).getTransactionHash();
    }
}
