package com.example.fetcher.core.util;

import com.example.fetcher.core.util.RlpUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RlpUtilTest {

    @Test
    public void rlpDecode_sampleData_resultAsExpected() {
        String transactionHash = "0x9b2f6a3c2e1aed2cccf92ba666c22d053ad0d8a5da7aa1fd5477dcd6577b4524";
        String rlpEncodedHash = "f844b842307839623266366133633265316165643263636366393262613636366332326430353361643064" +
                "386135646137616131666435343737646364363537376234353234";

        List<String> hashes = RlpUtil.decodeRlpToListOfHashes(rlpEncodedHash);

        Assertions.assertEquals(transactionHash, hashes.get(0));
    }

}
