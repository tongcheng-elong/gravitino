/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.trino.connector;

import static com.datastrato.gravitino.trino.connector.GravitinoErrorCode.GRAVITINO_MISSING_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.ImmutableMap;
import io.trino.spi.TrinoException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

public class TestGravitinoConfig {

  @BeforeClass
  public static void startup() throws Exception {}

  @AfterClass
  public static void shutdown() throws Exception {}

  @Test
  public void testGravitinoConfig() {
    String gravitinoUrl = "http://127.0.0.1:8000";
    String metalake = "user_001";
    ImmutableMap<String, String> configMap =
        ImmutableMap.of("gravitino.uri", gravitinoUrl, "gravitino.metalake", metalake);

    GravitinoConfig config = new GravitinoConfig(configMap);

    assertEquals(gravitinoUrl, config.getURI());
    assertEquals(metalake, config.getMetalake());
  }

  @Test
  public void testMissingConfig() {
    String gravitinoUrl = "http://127.0.0.1:8000";
    ImmutableMap<String, String> configMap = ImmutableMap.of("gravitino.uri", gravitinoUrl);
    try {
      GravitinoConfig config = new GravitinoConfig(configMap);
      assertEquals(gravitinoUrl, config.getURI());
    } catch (TrinoException e) {
      if (!GRAVITINO_MISSING_CONFIG.toErrorCode().equals(e.getErrorCode())) {
        throw e;
      }
    }
  }
}
