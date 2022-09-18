package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;
import org.web3j.crypto.Sign.SignatureData;

import java.math.BigInteger;
import java.util.Arrays;

@Data
@NoArgsConstructor
public class ContractData {
    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    //检测消息签名
    public  boolean validate(String signature,String message ,String address) {

        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        SignatureData sd = new SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature(
                    (byte) i,
                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                    msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);
                System.out.println(addressRecovered);
                if (addressRecovered.equals(address.toLowerCase())) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }
}
