package com.award.points.service;

import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    Logger logger = LoggerFactory.getLogger(RewardServiceTest.class);

    List<TransactionDetailsDto> transactions = new ArrayList<>();
    @BeforeEach
    void setUp() {

        try {
            logger.debug("Root path {}", new FileSystemResource("").getFile().getAbsolutePath());
            JsonReader reader = new JsonReader(new FileReader(
                    "src/test/resources/transactions.json"));
            Type transactionsListType = new TypeToken<ArrayList<TransactionDetailsDto>>(){}.getType();
            transactions = new Gson().fromJson(reader, transactionsListType);
        } catch (NullPointerException | FileNotFoundException | JsonParseException e) {
            logger.error("Unable to create transaction list from json file {} {}", e.getMessage(), e.getStackTrace());
        }
    }


    @Test
    void testCustomersCount() {
        List<RewardPointsDto> rewardPoints = rewardService.getEachCustomerRewardPoints(transactions);
        logger.info("Reward points {}", new Gson().toJson(rewardPoints));

        assertEquals(rewardPoints.size(), 4);
    }


    @Test
    void testRewardPointsForCustomer() {
        assertEquals(rewardService.calculateRewardPoints(transactions, "fqapbwz").longValue(), 434);
    }

    @Test
    void testRewardPointsOfGivenMonthForCustomer() {
        assertEquals(rewardService.calculateRewardPointsForMonth(transactions, "bxedjxp", "NOVEMBER 2022").longValue(), 298);
        assertEquals(rewardService.calculateRewardPointsForMonth(transactions, "hdgnmtk", "JANUARY 2023").longValue(), 0);
        assertEquals(rewardService.calculateRewardPointsForMonth(transactions, "ijqjsde", "JANUARY 2023").longValue(), 324);
    }
}