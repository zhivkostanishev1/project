package com.example.fetcher.core.util;

import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

public class RlpUtil {

    public static List<String> decodeRlpToListOfHashes(String rlp) {
        byte[] transaction = Numeric.hexStringToByteArray(rlp);
        RlpList rlpList = RlpDecoder.decode(transaction);
        RlpList values = (RlpList) rlpList.getValues().get(0);
        List<String> transactionHashes = new ArrayList<>();

        for (int i = 0; i < values.getValues().size(); i++) {
            transactionHashes.add(new String(((RlpString) values.getValues().get(i)).getBytes()));
        }
        return transactionHashes;
    }

}
